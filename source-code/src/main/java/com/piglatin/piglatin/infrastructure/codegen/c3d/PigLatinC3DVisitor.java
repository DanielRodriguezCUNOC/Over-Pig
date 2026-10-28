package com.piglatin.piglatin.infrastructure.codegen.c3d;

import com.piglatin.common.infrastructure.codegen.ir.C3DContext;
import com.piglatin.common.infrastructure.codegen.ir.C3DEmitter;
import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.Type;
import com.piglatin.common.infrastructure.codegen.ir.instructions.arithmetic.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.calls.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.comparisons.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.constants.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.controlflow.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.conversions.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.memory.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.runtime.*;
import com.piglatin.piglatin.domain.ast.nodes.NodeImport;
import com.piglatin.piglatin.domain.ast.nodes.declaration.NodeArrayDeclaration;
import com.piglatin.piglatin.domain.ast.nodes.declaration.NodeVariableDeclaration;
import com.piglatin.piglatin.domain.ast.nodes.expression.*;
import com.piglatin.piglatin.domain.ast.nodes.instruction.*;
import com.piglatin.piglatin.domain.ast.nodes.literal.*;
import com.piglatin.piglatin.domain.ast.nodes.lvalue.NodeFieldAccess;
import com.piglatin.piglatin.domain.ast.nodes.lvalue.NodeIndexAccess;
import com.piglatin.piglatin.domain.ast.nodes.lvalue.NodeLvalue;
import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.principal.NodeProgram;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import com.piglatin.y.domain.symboltable.FunctionSymbol;
import com.piglatin.y.domain.symboltable.ParameterType;
import com.piglatin.y.domain.symboltable.YSymbolTable;
import com.piglatin.zetariano.domain.symboltable.ZetarianoSymbolTable;
import com.piglatin.zetariano.domain.types.ZetarianoTypeTable;
import com.piglatin.zetariano.infrastructure.codegen.c3d.VtableLayout;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class PigLatinC3DVisitor implements Visitor<String> {

    private static final String FRAME_PTR = C3DEmitter.FP;
    private static final int WORD = C3DEmitter.WORD;
    private static final int DOUBLE_WORD = C3DEmitter.DOUBLE_WORD;
    private static final int CHAR_STRING_BYTES = 8;
    private static final String MAIN_LABEL = "main_0";
    private static final String STRING_PREFIX = "plstr_";

    private final C3DContext ctx;
    private final C3DEmitter emitter;
    private final VtableLayout zLayout;
    private final ZetarianoSymbolTable zSymbolTable;
    private final ZetarianoTypeTable zTypeTable;
    private final YSymbolTable ySymbolTable;

    /// Layout de los structs de Y
    private com.piglatin.y.infrastructure.codegen.c3d.StructLayout structLayout;

    private final Map<String, String> stringLabels = new LinkedHashMap<>();

    private final Deque<Map<String, VarSlot>> scopes = new ArrayDeque<>();
    private final Deque<String> breakLabels = new ArrayDeque<>();
    private final Deque<String> continueLabels = new ArrayDeque<>();

    private FrameLayout frame;

    public PigLatinC3DVisitor(C3DContext ctx,
                              VtableLayout zLayout,
                              ZetarianoSymbolTable zSymbolTable,
                              ZetarianoTypeTable zTypeTable,
                              YSymbolTable ySymbolTable) {
        this.ctx = ctx;
        this.emitter = new C3DEmitter(ctx);
        this.zLayout = zLayout;
        this.zSymbolTable = zSymbolTable;
        this.zTypeTable = zTypeTable;
        this.ySymbolTable = ySymbolTable;
    }

    public PigLatinC3DVisitor(C3DContext ctx,
                              VtableLayout zLayout,
                              ZetarianoSymbolTable zSymbolTable,
                              ZetarianoTypeTable zTypeTable,
                              YSymbolTable ySymbolTable,
                              com.piglatin.y.infrastructure.codegen.c3d.StructLayout structLayout) {
        this(ctx, zLayout, zSymbolTable, zTypeTable, ySymbolTable);
        this.structLayout = structLayout;
    }

    //* Literales de cadena: etiqueta  plstr_N -> texto. */
    public Map<String, String> getStringLiterals() {
        Map<String, String> byLabel = new LinkedHashMap<>();
        stringLabels.forEach((text, label) -> byLabel.put(label, text));
        return Collections.unmodifiableMap(byLabel);
    }

    // Programa: main_0

    @Override
    public String visitProgram(NodeProgram n) {
        if (n == null) return null;

        scopes.clear();
        breakLabels.clear();
        continueLabels.clear();
        frame = buildFrame(n);

        ctx.emit(new Label(MAIN_LABEL));
        emitter.emitPrologue(frame.size);

        scopes.push(new HashMap<>());
        // Las variables de VARIABILES> son locales de main_0: se inicializan antes del cuerpo.
        for (ASTNode declaration : n.getGlobalDeclarations()) {
            accept(declaration);
        }
        for (ASTNode instruction : n.getMainInstructions()) {
            accept(instruction);
        }
        scopes.pop();

        emitter.emitEpilogue();
        ctx.emit(new ReturnVoid());
        frame = null;
        return null;
    }

    @Override
    public String visitImport(NodeImport n) {
        return null;
    }

    // asignación de offsets

    private record LocalDecl(ASTNode node, TypeDesc desc) {
    }

    private static final class FrameLayout {
        private final Map<ASTNode, VarSlot> locals = new IdentityHashMap<>();
        private int size;
    }

    //* Offsets negativos respecto a fp. Sin slot de this: el primero es fp-4. */
    private static final class SlotCursor {
        private int used = 0;

        int allocate(boolean isDouble) {
            used = isDouble ? alignUp(used + DOUBLE_WORD, DOUBLE_WORD) : used + WORD;
            return -used;
        }
    }

    private FrameLayout buildFrame(NodeProgram program) {
        FrameLayout layout = new FrameLayout();
        SlotCursor cursor = new SlotCursor();

        List<LocalDecl> decls = new ArrayList<>();
        for (ASTNode declaration : program.getGlobalDeclarations()) collectLocals(declaration, decls);
        for (ASTNode instruction : program.getMainInstructions()) collectLocals(instruction, decls);

        assignSlots(decls, false, cursor, layout);
        assignSlots(decls, true, cursor, layout);

        layout.size = alignUp(C3DEmitter.SAVED_AREA + cursor.used, C3DEmitter.FRAME_ALIGNMENT);
        return layout;
    }

    private void assignSlots(List<LocalDecl> decls, boolean doubles, SlotCursor cursor, FrameLayout layout) {
        for (LocalDecl decl : decls) {
            boolean isDouble = decl.desc().type() == Type.DOUBLE;
            if (isDouble != doubles) continue;
            layout.locals.put(decl.node(), new VarSlot(cursor.allocate(isDouble), decl.desc()));
        }
    }

    private void collectLocals(ASTNode node, List<LocalDecl> out) {
        if (node == null) return;
        if (node instanceof NodeBlock block) {
            for (ASTNode child : block.getInstructions()) collectLocals(child, out);
        } else if (node instanceof NodeVariableDeclaration decl) {
            out.add(new LocalDecl(decl, declaredDesc(decl)));
        } else if (node instanceof NodeArrayDeclaration decl) {
            out.add(new LocalDecl(decl, arrayDeclDesc(decl)));
        } else if (node instanceof NodeIf stmt) {
            collectLocals(stmt.getThenBlock(), out);
            if (stmt.getElseIfClauses() != null) {
                for (ElseIfClause clause : stmt.getElseIfClauses()) collectLocals(clause.getBlock(), out);
            }
            collectLocals(stmt.getElseBlock(), out);
        } else if (node instanceof NodeWhile stmt) {
            collectLocals(stmt.getBlock(), out);
        } else if (node instanceof NodeDoWhile stmt) {
            collectLocals(stmt.getBlock(), out);
        } else if (node instanceof NodeFor stmt) {
            collectLocals(stmt.getInitialization(), out);
            collectLocals(stmt.getBlock(), out);
        }
    }

    /**
     * Tipo de una variable. El type checker rellena type} cuando falta; este respaldo
     * solo cubre los casos triviales (sin scopes aún, porque se llama antes de emitir).
     */
    private TypeDesc declaredDesc(NodeVariableDeclaration decl) {
        if (decl.getType() != null) return TypeDesc.parse(decl.getType());
        ASTNode init = decl.getInitializer();
        if (init instanceof NodeNewInstance created) return new TypeDesc(created.getClassName(), 0);
        if (init instanceof NodeStringLiteral) return TypeDesc.STRING;
        if (init instanceof NodeDecimalLiteral) return TypeDesc.DOUBLE;
        if (init instanceof NodeBooleanLiteral) return TypeDesc.BOOLEAN;
        if (init instanceof NodeCharLiteral) return TypeDesc.CHAR;
        return TypeDesc.INT;
    }

    private TypeDesc arrayDeclDesc(NodeArrayDeclaration decl) {
        TypeDesc element = decl.getElementType() != null ? TypeDesc.parse(decl.getElementType()) : TypeDesc.INT;
        return new TypeDesc(element.name(), element.dims() + 1);
    }

    // Sentencias

    @Override
    public String visitBlock(NodeBlock n) {
        if (n == null) return null;
        scopes.push(new HashMap<>());
        for (ASTNode instruction : n.getInstructions()) {
            accept(instruction);
        }
        scopes.pop();
        return null;
    }

    @Override
    public String visitVariableDeclaration(NodeVariableDeclaration n) {
        VarSlot slot = localSlot(n);
        if (n.getInitializer() != null) {
            store(new Place(FRAME_PTR, slot.offset(), slot.desc()), evaluateAs(n.getInitializer(), slot.desc()));
        }
        scopes.peek().put(n.getIdentifier(), slot);
        return null;
    }

    /**
     * series a[N] : Con valores iniciales la longitud es N si N es un
     * literal entero mayor o igual que la cantidad de valores; si no, la cantidad de valores.
     */
    @Override
    public String visitArrayDeclaration(NodeArrayDeclaration n) {
        VarSlot slot = localSlot(n);
        TypeDesc element = slot.desc().elementType();
        List<ASTNode> initial = n.getInitialValues();
        String array;

        if (initial != null && !initial.isEmpty()) {
            int length = initial.size();
            if (n.getSizeExpression() instanceof NodeIntegerLiteral literal && literal.getValue() > length) {
                length = literal.getValue();
            } else if (n.getSizeExpression() == null && n.getSize() > length) {
                length = n.getSize();
            }
            array = emitArrayInit(initial, element, length);
        } else if (n.getSizeExpression() != null) {
            String size = coerce(n.getSizeExpression().accept(this), staticTypeOf(n.getSizeExpression()), Type.INT);
            array = allocateArray(size, element.slotSize(), arrayHeader(element));
        } else if (n.getSize() > 0) {
            array = allocateArray(newConstInt(n.getSize()), element.slotSize(), arrayHeader(element));
        } else {
            array = ctx.newTemp();
            ctx.emit(new ConstNull(array));
        }
        ctx.emit(new StoreInt(FRAME_PTR, slot.offset(), array));
        scopes.peek().put(n.getIdentifier(), slot);
        return null;
    }

    private VarSlot localSlot(ASTNode declaration) {
        VarSlot slot = frame == null ? null : frame.locals.get(declaration);
        if (slot == null) {
            throw new IllegalStateException("Declaración local sin slot en el frame: " + declaration);
        }
        return slot;
    }

    @Override
    public String visitAssignment(NodeAssignment n) {
        if (n.getLvalue() == null) {
            throw new IllegalStateException("Asignación sin lvalue en línea " + n.getLine());
        }
        Place place = resolvePlace(n.getLvalue());
        store(place, evaluateAs(n.getExpression(), place.desc()));
        return null;
    }

    /**
     * lvalue leere: lee un entero o decimal y lo guarda en el lugar. Sin lvalue
     *  leere solo lee un entero y lo descarta.
     *
     * TODO: leer TEXTUM no está soportado (no existe instrucción de lectura de cadenas).
     */
    @Override
    public String visitRead(NodeRead n) {
        if (n.getTarget() == null) {
            String value = ctx.newTemp();
            ctx.emit(new ReadInt(value));
            return value;
        }
        Place place = resolvePlace(n.getTarget());
        Type type = place.desc().type();
        if (type == Type.DOUBLE) {
            emitter.emitReadInto(place.base(), place.offset(), Type.DOUBLE);
        } else if (type == Type.INT || type == Type.CHAR || type == Type.BOOL) {
            emitter.emitReadInto(place.base(), place.offset(), Type.INT);
        } else {
            throw new IllegalStateException("No se puede leer un valor de tipo " + type
                    + " (línea " + n.getLine() + ")");
        }
        return null;
    }

    //* Cada elemento de la instrucción se imprime con salto de línea. */
    @Override
    public String visitPrint(NodePrint n) {
        if (n.getPrintItems() == null) return null;
        for (ASTNode item : n.getPrintItems()) {
            if (item == null) continue;
            TypeDesc desc = descOf(item);
            String value = item.accept(this);
            if (value == null) continue;
            printValue(value, desc);
        }
        ctx.emit(new PrintNewline());
        return null;
    }

    private void printValue(String value, TypeDesc desc) {
        if (desc.type() == Type.CHAR) {
            ctx.emit(new PrintString(charToString(value)));
        } else {
            emitter.emitPrint(value, desc.type());
        }
    }

    @Override
    public String visitIf(NodeIf n) {
        String lEnd = ctx.newLabel();

        String lNext = ctx.newLabel();
        ctx.emit(new BranchFalse(n.getCondition().accept(this), lNext));
        accept(n.getThenBlock());
        ctx.emit(new Jump(lEnd));
        ctx.emit(new Label(lNext));

        if (n.getElseIfClauses() != null) {
            for (ElseIfClause clause : n.getElseIfClauses()) {
                String lClauseNext = ctx.newLabel();
                ctx.emit(new BranchFalse(clause.getCondition().accept(this), lClauseNext));
                accept(clause.getBlock());
                ctx.emit(new Jump(lEnd));
                ctx.emit(new Label(lClauseNext));
            }
        }

        accept(n.getElseBlock());
        ctx.emit(new Label(lEnd));
        return null;
    }

    @Override
    public String visitWhile(NodeWhile n) {
        String lStart = ctx.newLabel();
        String lEnd = ctx.newLabel();

        ctx.emit(new Label(lStart));
        ctx.emit(new BranchFalse(n.getCondition().accept(this), lEnd));
        emitLoopBody(n.getBlock(), lEnd, lStart);
        ctx.emit(new Jump(lStart));
        ctx.emit(new Label(lEnd));
        return null;
    }

    @Override
    public String visitDoWhile(NodeDoWhile n) {
        String lBody = ctx.newLabel();
        String lCond = ctx.newLabel();
        String lEnd = ctx.newLabel();

        ctx.emit(new Label(lBody));
        emitLoopBody(n.getBlock(), lEnd, lCond);
        ctx.emit(new Label(lCond));
        ctx.emit(new BranchTrue(n.getCondition().accept(this), lBody));
        ctx.emit(new Label(lEnd));
        return null;
    }

    @Override
    public String visitFor(NodeFor n) {
        String lStart = ctx.newLabel();
        String lUpdate = ctx.newLabel();
        String lEnd = ctx.newLabel();

        scopes.push(new HashMap<>());
        accept(n.getInitialization());
        ctx.emit(new Label(lStart));
        if (n.getCondition() != null) {
            ctx.emit(new BranchFalse(n.getCondition().accept(this), lEnd));
        }
        emitLoopBody(n.getBlock(), lEnd, lUpdate);
        ctx.emit(new Label(lUpdate));
        accept(n.getUpdate());
        ctx.emit(new Jump(lStart));
        ctx.emit(new Label(lEnd));
        scopes.pop();
        return null;
    }

    private void emitLoopBody(ASTNode body, String breakLabel, String continueLabel) {
        breakLabels.push(breakLabel);
        continueLabels.push(continueLabel);
        accept(body);
        breakLabels.pop();
        continueLabels.pop();
    }

    @Override
    public String visitBreak(NodeBreak n) {
        if (!breakLabels.isEmpty()) ctx.emit(new Jump(breakLabels.peek()));
        return null;
    }

    @Override
    public String visitContinue(NodeContinue n) {
        if (!continueLabels.isEmpty()) ctx.emit(new Jump(continueLabels.peek()));
        return null;
    }

    //* main_0 es void: reddere termina el programa (la expresión se evalúa y se descarta). */
    @Override
    public String visitReturn(NodeReturn n) {
        if (n.getExpression() != null) n.getExpression().accept(this);
        emitter.emitEpilogue();
        ctx.emit(new ReturnVoid());
        return null;
    }

    private void accept(ASTNode node) {
        if (node != null) node.accept(this);
    }

    // Expresiones

    @Override
    public String visitBinaryOperation(NodeBinaryOperation n) {
        String op = n.getOperator();
        return switch (op) {
            case "&&" -> emitShortCircuit(n, true);
            case "||" -> emitShortCircuit(n, false);
            case "<", "<=", ">", ">=", "==", "!=" -> emitComparisonExpression(n, op);
            default -> emitArithmeticExpression(n, op);
        };
    }

    /**
     * Emite el código para una expresión de cortocircuito.
     * El operando derecho solo se evalúa si hace falta (guarda con BranchFalse/BranchTrue) y la
     * combinación final la hace C3DEmitter.
     */
    private String emitShortCircuit(NodeBinaryOperation n, boolean isAnd) {
        String left = n.getLeft().accept(this);
        String right = ctx.newTemp();
        String lSkip = ctx.newLabel();

        ctx.emit(new ConstInt(right, 0));
        ctx.emit(isAnd ? new BranchFalse(left, lSkip) : new BranchTrue(left, lSkip));
        ctx.emit(new MoveInt(right, n.getRight().accept(this)));
        ctx.emit(new Label(lSkip));

        String result = ctx.newTemp();
        if (isAnd) {
            emitter.emitLogicalAnd(result, left, right);
        } else {
            emitter.emitLogicalOr(result, left, right);
        }
        return result;
    }

    private String emitComparisonExpression(NodeBinaryOperation n, String op) {
        Type leftType = staticTypeOf(n.getLeft());
        Type rightType = staticTypeOf(n.getRight());
        String left = n.getLeft().accept(this);
        String right = n.getRight().accept(this);

        if (leftType == Type.STRING && rightType == Type.STRING && ("==".equals(op) || "!=".equals(op))) {
            String equal = ctx.newTemp();
            ctx.emit(new StrEq(equal, left, right));
            if ("==".equals(op)) return equal;
            String different = ctx.newTemp();
            emitter.emitLogicalNot(different, equal);
            return different;
        }

        Type opType = (leftType == Type.DOUBLE || rightType == Type.DOUBLE) ? Type.DOUBLE : Type.INT;
        return emitComparison(op, coerce(left, leftType, opType), coerce(right, rightType, opType), opType);
    }

    private String emitArithmeticExpression(NodeBinaryOperation n, String op) {
        Type leftType = staticTypeOf(n.getLeft());
        Type rightType = staticTypeOf(n.getRight());
        String left = n.getLeft().accept(this);
        String right = n.getRight().accept(this);

        if ("+".equals(op) && (leftType == Type.STRING || rightType == Type.STRING)) {
            String joined = ctx.newTemp();
            emitter.emitConcat(joined, stringOf(left, descOf(n.getLeft())), stringOf(right, descOf(n.getRight())));
            return joined;
        }

        Type opType = (leftType == Type.DOUBLE || rightType == Type.DOUBLE) ? Type.DOUBLE : Type.INT;
        return emitArithmetic(op, coerce(left, leftType, opType), coerce(right, rightType, opType), opType);
    }

    @Override
    public String visitUnaryOperation(NodeUnaryOperation n) {
        String op = n.getOperator();
        if ("++".equals(op) || "--".equals(op)) {
            return emitStep(resolvePlace(n.getOperand()), "++".equals(op));
        }
        String operand = n.getOperand().accept(this);
        switch (op) {
            case "-" -> {
                String result = ctx.newTemp();
                ctx.emit(staticTypeOf(n.getOperand()) == Type.DOUBLE
                        ? new NegDouble(result, operand) : new NegInt(result, operand));
                return result;
            }
            case "!" -> {
                String result = ctx.newTemp();
                emitter.emitLogicalNot(result, operand);
                return result;
            }
            default -> {
                return operand;
            }
        }
    }

    //* x++}, ++x}, x--}, --x}: siempre devuelven el valor nuevo. */
    @Override
    public String visitIncrementDecrement(NodeIncrementDecrement n) {
        return emitStep(resolvePlace(n.getOperand()), "++".equals(n.getOperation()));
    }

    //* Suma o resta 1 al lugar y devuelve el valor nuevo. */
    private String emitStep(Place place, boolean increment) {
        Type type = place.desc().type() == Type.DOUBLE ? Type.DOUBLE : Type.INT;
        String current = load(place);
        String one = type == Type.DOUBLE ? newConstDouble(1.0) : newConstInt(1);
        String next = emitArithmetic(increment ? "+" : "-", current, one, type);
        store(place, next);
        return next;
    }

    @Override
    public String visitIdentifier(NodeIdentifier n) {
        return load(resolvePlace(n));
    }

    @Override
    public String visitFieldAccess(NodeFieldAccess n) {
        if (isArrayLength(n)) {
            String result = ctx.newTemp();
            ctx.emit(new LoadInt(result, n.getCurrentNode().accept(this), C3DEmitter.ARRAY_LENGTH_OFFSET));
            return result;
        }
        return load(resolvePlace(n));
    }

    @Override
    public String visitIndexAccess(NodeIndexAccess n) {
        return load(resolvePlace(n));
    }

    //* Respaldo: el builder actual no crea NodeLvalue. */
    @Override
    public String visitLvalue(NodeLvalue n) {
        return load(resolvePlace(n));
    }

    // Llamadas y objetos

    /**
     * Forma canónica de toda llamada. target = Identifier: función libre de Y;
     * target = FieldAccess(receptor, método): método de un objeto de Zetariano.
     */
    @Override
    public String visitMethodCall(NodeMethodCall n) {
        List<ASTNode> args = n.getArguments() != null ? n.getArguments() : List.of();
        ASTNode target = n.getTarget();

        if (target instanceof NodeIdentifier id) {
            return emitFreeCall(id.getId(), args);
        }
        if (target instanceof NodeFieldAccess access && access.getCurrentNode() != null) {
            return emitObjectCall(access.getCurrentNode(), access.getFieldName(), args);
        }
        throw new IllegalStateException("Llamada con destino no soportado en línea " + n.getLine()
                + ": " + (target == null ? "null" : target.getTipoNodo()));
    }

    //* Respaldo por si el builder vuelve a generar NodeFunctionCall. */
    @Override
    public String visitFunctionCall(NodeFunctionCall n) {
        List<ASTNode> args = n.getArguments() != null ? n.getArguments() : List.of();
        if (n.getFunctionName() == null) {
            throw new IllegalStateException("Llamada sin nombre de función en línea " + n.getLine());
        }
        if (n.getCurrentNode() != null) {
            return emitObjectCall(n.getCurrentNode(), n.getFunctionName(), args);
        }
        return emitFreeCall(n.getFunctionName(), args);
    }

    //* Llamada a un método de un objeto de Zetariano: el receptor es el primer argumento. */
    private String emitObjectCall(ASTNode receiverNode, String methodName, List<ASTNode> args) {
        String className = resolveReceiverType(receiverNode);
        if (className == null) {
            throw new IllegalStateException("El receptor de '" + methodName
                    + "' no es un objeto de Zetariano (línea " + receiverNode.getLine() + ")");
        }
        VtableLayout.MethodInfo method = zLayout.resolveMethod(className, methodName, args.size());
        if (method == null) {
            throw new IllegalStateException("Método no resuelto: " + className + "." + methodName
                    + "/" + args.size());
        }

        String receiver = receiverNode.accept(this);
        List<TypeDesc> targets = new ArrayList<>();
        for (String paramType : method.paramTypes()) targets.add(TypeDesc.parse(paramType));
        int[] counts = emitParams(receiver, args, targets);
        return emitMethodCall(method, className, receiver, counts[0], counts[1]);
    }

    private String emitMethodCall(VtableLayout.MethodInfo method, String className, String receiver,
                                  int nInt, int nDouble) {
        TypeDesc returnDesc = TypeDesc.parse(method.returnType());
        int vtableOffset = method.isPrivate()
                ? -1 : zLayout.vtableOffset(className, method.name(), method.paramTypes().size());

        if (vtableOffset < 0) {
            return emitDirectCall(method.label(), returnDesc, nInt, nDouble);
        }
        Type returnType = returnDesc.type();
        if (returnType == Type.VOID) {
            emitter.emitVirtualCallVoid(receiver, vtableOffset, nInt, nDouble);
            return null;
        }
        String result = ctx.newTemp();
        if (returnType == Type.DOUBLE) {
            emitter.emitVirtualCallDouble(result, receiver, vtableOffset, nInt, nDouble);
        } else {
            emitter.emitVirtualCallInt(result, receiver, vtableOffset, nInt, nDouble);
        }
        return result;
    }

    //* Función libre de Y. Etiqueta nombre_aridad, sin receptor. */
    private String emitFreeCall(String name, List<ASTNode> args) {
        FunctionSymbol function = ySymbolTable == null ? null : ySymbolTable.lookupFunction(name, args.size());
        if (function == null) {
            throw new IllegalStateException("Función libre no resuelta: " + name + "/" + args.size()
                    + " (¿falta el archivo .y o su import?)");
        }
        List<ParameterType> params = function.getParameterTypes();
        List<TypeDesc> targets = new ArrayList<>();
        for (int i = 0; i < args.size(); i++) {
            ParameterType param = params != null && i < params.size() ? params.get(i) : null;
            targets.add(param != null ? yParamDesc(param) : descOf(args.get(i)));
        }
        int[] counts = emitParams(null, args, targets);
        return emitDirectCall(name + "_" + args.size(), TypeDesc.parse(function.getReturnType()),
                counts[0], counts[1]);
    }

    private TypeDesc yParamDesc(ParameterType param) {
        TypeDesc base = TypeDesc.parse(param.getType());
        return param.isArray() ? new TypeDesc(base.name(), base.dims() + 1) : base;
    }

    private String emitDirectCall(String label, TypeDesc returnDesc, int nInt, int nDouble) {
        Type returnType = returnDesc.type();
        if (returnType == Type.VOID) {
            emitter.emitCallVoid(label, nInt, nDouble);
            return null;
        }
        String result = ctx.newTemp();
        if (returnType == Type.DOUBLE) {
            emitter.emitCallDouble(result, label, nInt, nDouble);
        } else {
            emitter.emitCallInt(result, label, nInt, nDouble);
        }
        return result;
    }

    /**
     * Evalúa los argumentos (convertidos al tipo del parámetro) y recién después emite los
     * param, para que una llamada anidada no intercale los suyos. Devuelve {nInt, nDouble};
     * el receptor cuenta como entero.
     */
    private int[] emitParams(String receiver, List<ASTNode> args, List<TypeDesc> targets) {
        List<String> values = new ArrayList<>();
        List<Type> types = new ArrayList<>();
        for (int i = 0; i < args.size(); i++) {
            values.add(evaluateAs(args.get(i), targets.get(i)));
            types.add(targets.get(i).type());
        }

        int nInt = 0;
        int nDouble = 0;
        if (receiver != null) {
            emitter.emitParam(receiver, Type.OBJECT);
            nInt++;
        }
        for (int i = 0; i < values.size(); i++) {
            emitter.emitParam(values.get(i), types.get(i));
            if (types.get(i) == Type.DOUBLE) nDouble++;
            else nInt++;
        }
        return new int[]{nInt, nDouble};
    }

    @Override
    public String visitNewInstance(NodeNewInstance n) {
        String className = n.getClassName();
        List<ASTNode> args = n.getArguments() != null ? n.getArguments() : List.of();
        String obj = allocateObject(className);

        VtableLayout.MethodInfo ctor = zLayout.constructor(className, args.size());
        if (ctor == null) {
            if (!args.isEmpty()) {
                throw new IllegalStateException("La clase " + className + " no tiene un constructor de "
                        + args.size() + " parámetro(s) (línea " + n.getLine() + ")");
            }
            return obj;
        }
        List<TypeDesc> targets = new ArrayList<>();
        for (String paramType : ctor.paramTypes()) targets.add(TypeDesc.parse(paramType));
        int[] counts = emitParams(obj, args, targets);
        emitter.emitCallVoid(constructorLabel(className, args.size()), counts[0], counts[1]);
        return obj;
    }

    //* Reserva el objeto, escribe la vtable y ejecuta los inicializadores de campos. */
    private String allocateObject(String className) {
        if (zTypeTable == null || !zTypeTable.isUserDefined(className)) {
            throw new IllegalStateException("Clase no definida: " + className
                    + " (¿falta el archivo .z o su import?)");
        }
        String obj = ctx.newTemp();
        emitter.emitAllocObject(obj, zLayout.objectSize(className), zLayout.vtableLabel(className));
        emitter.emitParam(obj, Type.OBJECT);
        emitter.emitCallVoid(fieldInitLabel(className), 1, 0);
        return obj;
    }

    //* Etiqueta del constructor emitido por Zetariano; ver toda la clase. */
    private String constructorLabel(String className, int paramCount) {
        return className + "_init_" + (1 + paramCount);
    }

    //* El inicializador de campos solo recibe el objeto. */
    private String fieldInitLabel(String className) {
        return className + "_fieldinit_1";
    }

    // Arrays
    // Layout: longitud (4 bytes) en +0; elementos desde +4, o desde +8 si son double.

    @Override
    public String visitArrayLiteral(NodeArrayLiteral n) {
        return emitLiteralArray(n.getValues());
    }

    //* Un struct literal con nombre conocido usa el layout de Y; si no, se trata como un array. */
    @Override
    public String visitStructLiteral(NodeStructLiteral n) {
        if (structLayout != null && structLayout.hasStruct(n.getStructName())) {
            return emitStructInit(n.getValues(), n.getStructName());
        }
        return emitLiteralArray(n.getValues());
    }

    private boolean isStructTarget(TypeDesc target) {
        return structLayout != null && !target.isArray() && structLayout.hasStruct(target.name());
    }

    /**
     * Construye un struct de Y en el heap con el layout que usa YC3DVisitor:
     * campos escalares en su offset (flotante alineado a 8), campos arreglo/struct como
     * puntero de 4 bytes, tamaño total redondeado a 8. No lleva cabecera de longitud.
     */
    private String emitStructInit(List<ASTNode> values, String structName) {
        List<ASTNode> inits = values != null ? values : List.of();
        String struct = ctx.newTemp();
        ctx.emit(new Alloc(struct, newConstInt(structLayout.totalSize(structName))));

        int i = 0;
        for (com.piglatin.y.infrastructure.codegen.c3d.StructLayout.FieldInfo field
                : structLayout.fieldsOf(structName)) {
            if (i >= inits.size()) break;
            ASTNode expr = inits.get(i++);
            TypeDesc fieldDesc = TypeDesc.parse(field.yType());
            String value;
            if (fieldDesc.isArray() && expr instanceof NodeArrayLiteral literal) {
                value = emitArrayInit(literal.getValues(), fieldDesc.elementType(), literal.getValues().size());
            } else {
                value = evaluateAs(expr, fieldDesc);
            }
            store(new Place(struct, field.offset(), fieldDesc), value);
        }
        return struct;
    }

    private String emitLiteralArray(List<ASTNode> values) {
        List<ASTNode> elements = values != null ? values : List.of();
        TypeDesc element = elements.isEmpty() ? TypeDesc.INT : descOf(elements.get(0));
        return emitArrayInit(elements, element, elements.size());
    }

    private String emitArrayInit(List<ASTNode> elements, TypeDesc element, int length) {
        int header = arrayHeader(element);
        int elementSize = element.slotSize();
        String array = allocateArray(newConstInt(length), elementSize, header);

        for (int i = 0; i < elements.size(); i++) {
            ASTNode expr = elements.get(i);
            String value = (expr instanceof NodeArrayLiteral nested && element.isArray())
                    ? emitArrayInit(nested.getValues(), element.elementType(), nested.getValues().size())
                    : evaluateAs(expr, element);
            store(new Place(array, header + i * elementSize, element), value);
        }
        return array;
    }

    //* Alloc de (cabecera + longitud * tamaño) redondeado a 8, y escribe la longitud. */
    private String allocateArray(String length, int elementSize, int header) {
        String body = ctx.newTemp();
        ctx.emit(new MulInt(body, length, newConstInt(elementSize)));
        String padded = ctx.newTemp();
        ctx.emit(new AddInt(padded, body, newConstInt(header + C3DEmitter.OBJECT_ALIGNMENT - 1)));
        String eight = newConstInt(C3DEmitter.OBJECT_ALIGNMENT);
        String blocks = ctx.newTemp();
        ctx.emit(new DivInt(blocks, padded, eight));
        String size = ctx.newTemp();
        ctx.emit(new MulInt(size, blocks, eight));

        String array = ctx.newTemp();
        ctx.emit(new Alloc(array, size));
        ctx.emit(new StoreInt(array, C3DEmitter.ARRAY_LENGTH_OFFSET, length));
        return array;
    }

    private int arrayHeader(TypeDesc element) {
        return element.type() == Type.DOUBLE ? DOUBLE_WORD : WORD;
    }

    private boolean isArrayLength(NodeFieldAccess n) {
        return "length".equals(n.getFieldName()) && n.getCurrentNode() != null
                && descOf(n.getCurrentNode()).isArray();
    }

    // Literales

    @Override
    public String visitIntegerLiteral(NodeIntegerLiteral n) {
        return newConstInt(n.getValue());
    }

    @Override
    public String visitDecimalLiteral(NodeDecimalLiteral n) {
        return newConstDouble(n.getValue());
    }

    @Override
    public String visitCharLiteral(NodeCharLiteral n) {
        return newConstInt(n.getValue());
    }

    @Override
    public String visitStringLiteral(NodeStringLiteral n) {
        return newConstString(decodeStringLiteral(n.getValue()));
    }

    @Override
    public String visitBooleanLiteral(NodeBooleanLiteral n) {
        String result = ctx.newTemp();
        ctx.emit(new ConstBool(result, n.isValue()));
        return result;
    }

    @Override
    public String visitNullLiteral(NodeNullLiteral n) {
        String result = ctx.newTemp();
        ctx.emit(new ConstNull(result));
        return result;
    }

    /**
     * El builder deja en value} el texto crudo del token, con las comillas dobles.
     * Se quitan y se procesan los escapes (\n, \t, \r, \", \\).
     */
    private String decodeStringLiteral(String raw) {
        String text = raw != null ? raw : "";
        if (text.length() >= 2 && text.startsWith("\"") && text.endsWith("\"")) {
            text = text.substring(1, text.length() - 1);
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c != '\\' || i + 1 >= text.length()) {
                out.append(c);
                continue;
            }
            char escaped = text.charAt(++i);
            switch (escaped) {
                case 'n' -> out.append('\n');
                case 't' -> out.append('\t');
                case 'r' -> out.append('\r');
                case '"' -> out.append('"');
                case '\\' -> out.append('\\');
                default -> out.append('\\').append(escaped);
            }
        }
        return out.toString();
    }

    // Lugares de memoria (lvalues), carga y almacenamiento

    //* Dirección base + offset donde vive un valor, y su tipo. */
    private record Place(String base, int offset, TypeDesc desc) {
    }

    /**
     * El lvalue llega como cadena NodeIdentifier -> NodeFieldAccess -> NodeIndexAccess.
     * NodeLvalue se acepta por si el builder cambia.
     */
    private Place resolvePlace(ASTNode lvalue) {
        if (lvalue instanceof NodeIdentifier id) return identifierPlace(id);
        if (lvalue instanceof NodeFieldAccess access) return fieldPlace(access);
        if (lvalue instanceof NodeIndexAccess access) return indexPlace(access);
        if (lvalue instanceof NodeLvalue legacy) return resolvePlace(chainOf(legacy));
        throw new IllegalArgumentException("No es un lvalue: "
                + (lvalue == null ? "null" : lvalue.getTipoNodo()));
    }

    private Place identifierPlace(NodeIdentifier id) {
        VarSlot slot = findLocal(id.getId());
        if (slot == null) {
            throw new IllegalStateException("Identificador no resuelto: " + id.getId()
                    + " (línea " + id.getLine() + ")");
        }
        return new Place(FRAME_PTR, slot.offset(), slot.desc());
    }

    private Place fieldPlace(NodeFieldAccess access) {
        String className = resolveReceiverType(access.getCurrentNode());
        if (className == null) {
            throw new IllegalStateException("No se puede acceder al campo '" + access.getFieldName()
                    + "': el destino no es un objeto de Zetariano (línea " + access.getLine() + ")");
        }
        VtableLayout.FieldInfo field = zLayout.field(className, access.getFieldName());
        if (field == null) {
            throw new IllegalStateException("Campo no resuelto: " + className + "." + access.getFieldName());
        }
        String base = access.getCurrentNode().accept(this);
        return new Place(base, field.offset(), TypeDesc.parse(field.typeName()));
    }

    private Place indexPlace(NodeIndexAccess access) {
        TypeDesc arrayDesc = descOf(access.getCurrentNode());
        if (!arrayDesc.isArray()) {
            throw new IllegalStateException("Se indexa algo que no es un array (línea " + access.getLine() + ")");
        }
        TypeDesc element = arrayDesc.elementType();
        String base = access.getCurrentNode().accept(this);
        String index = access.getIndexExpression().accept(this);

        String scaled = ctx.newTemp();
        ctx.emit(new MulInt(scaled, index, newConstInt(element.slotSize())));
        String address = ctx.newTemp();
        ctx.emit(new AddInt(address, base, scaled));
        return new Place(address, arrayHeader(element), element);
    }

    private ASTNode chainOf(NodeLvalue lvalue) {
        ASTNode current = new NodeIdentifier(lvalue.getIdentifier(), lvalue.getLine(), lvalue.getColumn());
        if (lvalue.getSuffixes() == null) return current;
        for (ASTNode suffix : lvalue.getSuffixes()) {
            if (suffix instanceof NodeFieldAccess field) {
                current = new NodeFieldAccess(current, field.getFieldName(), field.getLine(), field.getColumn());
            } else if (suffix instanceof NodeIndexAccess index) {
                current = new NodeIndexAccess(current, index.getIndexExpression(), index.getLine(), index.getColumn());
            }
        }
        return current;
    }

    private String load(Place place) {
        String result = ctx.newTemp();
        if (place.desc().type() == Type.DOUBLE) {
            ctx.emit(new LoadDouble(result, place.base(), place.offset()));
        } else {
            ctx.emit(new LoadInt(result, place.base(), place.offset()));
        }
        return result;
    }

    private void store(Place place, String value) {
        if (place.desc().type() == Type.DOUBLE) {
            ctx.emit(new StoreDouble(place.base(), place.offset(), value));
        } else {
            ctx.emit(new StoreInt(place.base(), place.offset(), value));
        }
    }

    private VarSlot findLocal(String name) {
        for (Map<String, VarSlot> scope : scopes) {
            VarSlot slot = scope.get(name);
            if (slot != null) return slot;
        }
        return null;
    }

    // Aritmética, comparaciones, conversiones, strings

    private String emitArithmetic(String op, String left, String right, Type type) {
        boolean isDouble = type == Type.DOUBLE;
        if (isDouble && "%".equals(op)) return emitRemDouble(left, right);

        String result = ctx.newTemp();
        Instruction instruction = switch (op) {
            case "+" -> isDouble ? new AddDouble(result, left, right) : new AddInt(result, left, right);
            case "-" -> isDouble ? new SubDouble(result, left, right) : new SubInt(result, left, right);
            case "*" -> isDouble ? new MulDouble(result, left, right) : new MulInt(result, left, right);
            case "/" -> isDouble ? new DivDouble(result, left, right) : new DivInt(result, left, right);
            case "%" -> new RemInt(result, left, right);
            default -> throw new IllegalArgumentException("Operador aritmético desconocido: " + op);
        };
        ctx.emit(instruction);
        return result;
    }

    //* a % b = a - trunc(a / b) * b, porque no existe RemDouble. */
    private String emitRemDouble(String left, String right) {
        String quotient = ctx.newTemp();
        ctx.emit(new DivDouble(quotient, left, right));
        String truncated = ctx.newTemp();
        ctx.emit(new DoubleToInt(truncated, quotient));
        String whole = ctx.newTemp();
        ctx.emit(new IntToDouble(whole, truncated));
        String product = ctx.newTemp();
        ctx.emit(new MulDouble(product, whole, right));
        String result = ctx.newTemp();
        ctx.emit(new SubDouble(result, left, product));
        return result;
    }

    private String emitComparison(String op, String left, String right, Type type) {
        boolean isDouble = type == Type.DOUBLE;
        String r = ctx.newTemp();
        Instruction instruction = switch (op) {
            case "<" -> isDouble ? new LtDouble(r, left, right) : new LtInt(r, left, right);
            case "<=" -> isDouble ? new LeDouble(r, left, right) : new LeInt(r, left, right);
            case ">" -> isDouble ? new GtDouble(r, left, right) : new GtInt(r, left, right);
            case ">=" -> isDouble ? new GeDouble(r, left, right) : new GeInt(r, left, right);
            case "==" -> isDouble ? new EqDouble(r, left, right) : new EqInt(r, left, right);
            case "!=" -> isDouble ? new NeDouble(r, left, right) : new NeInt(r, left, right);
            default -> throw new IllegalArgumentException("Operador de comparación desconocido: " + op);
        };
        ctx.emit(instruction);
        return r;
    }

    //* Evalúa la expresión y la convierte al tipo destino. */
    private String evaluateAs(ASTNode expr, TypeDesc target) {
        if (isStructTarget(target) && (expr instanceof NodeArrayLiteral || expr instanceof NodeStructLiteral)) {
            return emitStructInit(expr instanceof NodeArrayLiteral a ? a.getValues()
                    : ((NodeStructLiteral) expr).getValues(), target.name());
        }
        String value = expr instanceof NodeArrayLiteral literal && target.isArray()
                ? emitArrayInit(literal.getValues(), target.elementType(), literal.getValues().size())
                : expr.accept(this);
        return coerce(value, staticTypeOf(expr), target.type());
    }

    //* Conversión implícita int -> double (y double -> int al asignar a un entero). */
    private String coerce(String value, Type from, Type to) {
        boolean fromIntLike = from == Type.INT || from == Type.CHAR || from == Type.BOOL;
        if (to == Type.DOUBLE && fromIntLike) {
            String result = ctx.newTemp();
            ctx.emit(new IntToDouble(result, value));
            return result;
        }
        if (from == Type.DOUBLE && (to == Type.INT || to == Type.CHAR)) {
            String result = ctx.newTemp();
            ctx.emit(new DoubleToInt(result, value));
            return result;
        }
        return value;
    }

    //* Convierte un valor a String para la concatenación. */
    private String stringOf(String value, TypeDesc desc) {
        switch (desc.type()) {
            case STRING:
                return value;
            case DOUBLE: {
                String result = ctx.newTemp();
                emitter.emitToStr(result, value, Type.DOUBLE);
                return result;
            }
            case CHAR:
                return charToString(value);
            case BOOL:
                return boolToString(value);
            case NULL:
                return newConstString("null");
            default: {
                String result = ctx.newTemp();
                emitter.emitToStr(result, value, Type.INT);
                return result;
            }
        }
    }

    private String charToString(String value) {
        String text = ctx.newTemp();
        ctx.emit(new Alloc(text, newConstInt(CHAR_STRING_BYTES)));
        ctx.emit(new StoreByte(text, 0, value));
        ctx.emit(new StoreByte(text, 1, newConstInt(0)));
        return text;
    }

    private String boolToString(String value) {
        String result = ctx.newTemp();
        String lFalse = ctx.newLabel();
        String lEnd = ctx.newLabel();

        ctx.emit(new BranchFalse(value, lFalse));
        ctx.emit(new ConstString(result, internString("true")));
        ctx.emit(new Jump(lEnd));
        ctx.emit(new Label(lFalse));
        ctx.emit(new ConstString(result, internString("false")));
        ctx.emit(new Label(lEnd));
        return result;
    }

    private String newConstInt(int value) {
        String result = ctx.newTemp();
        ctx.emit(new ConstInt(result, value));
        return result;
    }

    private String newConstDouble(double value) {
        String result = ctx.newTemp();
        ctx.emit(new ConstDouble(result, value));
        return result;
    }

    private String newConstString(String text) {
        String result = ctx.newTemp();
        ctx.emit(new ConstString(result, internString(text)));
        return result;
    }

    private String internString(String text) {
        String existing = stringLabels.get(text);
        if (existing != null) return existing;
        String label = STRING_PREFIX + stringLabels.size();
        stringLabels.put(text, label);
        return label;
    }

    // Tipos estáticos

    //* Tipo estático de una expresión. Los arrays se representan como OBJECT (puntero). */
    private Type staticTypeOf(ASTNode node) {
        return descOf(node).type();
    }

    /**
     * Clase de Zetariano del receptor de una llamada o de un acceso a campo, o null si
     * el tipo estático no es un objeto. Encadena los tipos:
     *
     *    NodeIdentifier: tipo de la variable local.
     *    NodeFieldAccess: tipo del receptor y luego zLayout.field(clase,
     *       campo).typeName().
     *    NodeMethodCall: tipo de retorno del método.
     *    NodeNewInstance: el nombre de la clase.
     *
     * Todo eso lo resuelve descOf; aquí solo se filtra a "objeto, no array". PigLatin
     * no tiene campos propios, por eso un identificador que no es local no se busca en
     * zLayout.
     */
    private String resolveReceiverType(ASTNode node) {
        TypeDesc desc = descOf(node);
        return desc.type() == Type.OBJECT && !desc.isArray() ? desc.name() : null;
    }

    private TypeDesc descOf(ASTNode node) {
        if (node instanceof NodeIdentifier id) return identifierDesc(id);
        if (node instanceof NodeFieldAccess access) return fieldAccessDesc(access);
        if (node instanceof NodeIndexAccess access) return indexAccessDesc(access);
        if (node instanceof NodeMethodCall call) return methodCallDesc(call);
        if (node instanceof NodeFunctionCall call) return functionCallDesc(call);
        if (node instanceof NodeNewInstance created) return new TypeDesc(created.getClassName(), 0);
        if (node instanceof NodeLvalue legacy) return descOf(chainOf(legacy));
        if (node instanceof NodeArrayLiteral literal) return arrayLiteralDesc(literal.getValues());
        if (node instanceof NodeStructLiteral literal) return arrayLiteralDesc(literal.getValues());
        if (node instanceof NodeIntegerLiteral) return TypeDesc.INT;
        if (node instanceof NodeDecimalLiteral) return TypeDesc.DOUBLE;
        if (node instanceof NodeCharLiteral) return TypeDesc.CHAR;
        if (node instanceof NodeStringLiteral) return TypeDesc.STRING;
        if (node instanceof NodeBooleanLiteral) return TypeDesc.BOOLEAN;
        if (node instanceof NodeNullLiteral) return TypeDesc.NULL;
        if (node instanceof NodeBinaryOperation binary) return binaryDesc(binary);
        if (node instanceof NodeUnaryOperation unary) {
            return "!".equals(unary.getOperator()) ? TypeDesc.BOOLEAN : descOf(unary.getOperand());
        }
        if (node instanceof NodeIncrementDecrement step) return descOf(step.getOperand());
        return TypeDesc.VOID;
    }

    private TypeDesc identifierDesc(NodeIdentifier id) {
        VarSlot slot = findLocal(id.getId());
        return slot != null ? slot.desc() : TypeDesc.VOID;
    }

    private TypeDesc fieldAccessDesc(NodeFieldAccess access) {
        if (access.getCurrentNode() == null) return TypeDesc.VOID;
        if (isArrayLength(access)) return TypeDesc.INT;
        String className = resolveReceiverType(access.getCurrentNode());
        VtableLayout.FieldInfo field = className != null ? zLayout.field(className, access.getFieldName()) : null;
        return field != null ? TypeDesc.parse(field.typeName()) : TypeDesc.VOID;
    }

    private TypeDesc indexAccessDesc(NodeIndexAccess access) {
        TypeDesc array = descOf(access.getCurrentNode());
        return array.isArray() ? array.elementType() : TypeDesc.VOID;
    }

    private TypeDesc methodCallDesc(NodeMethodCall call) {
        int arity = call.getArguments() != null ? call.getArguments().size() : 0;
        ASTNode target = call.getTarget();
        if (target instanceof NodeIdentifier id) return freeCallDesc(id.getId(), arity);
        if (target instanceof NodeFieldAccess access && access.getCurrentNode() != null) {
            return objectCallDesc(access.getCurrentNode(), access.getFieldName(), arity);
        }
        return TypeDesc.VOID;
    }

    private TypeDesc functionCallDesc(NodeFunctionCall call) {
        int arity = call.getArguments() != null ? call.getArguments().size() : 0;
        if (call.getFunctionName() == null) return TypeDesc.VOID;
        if (call.getCurrentNode() != null) return objectCallDesc(call.getCurrentNode(), call.getFunctionName(), arity);
        return freeCallDesc(call.getFunctionName(), arity);
    }

    private TypeDesc objectCallDesc(ASTNode receiver, String methodName, int arity) {
        String className = resolveReceiverType(receiver);
        VtableLayout.MethodInfo method = className != null ? zLayout.resolveMethod(className, methodName, arity) : null;
        return method != null ? TypeDesc.parse(method.returnType()) : TypeDesc.VOID;
    }

    private TypeDesc freeCallDesc(String name, int arity) {
        FunctionSymbol function = ySymbolTable != null ? ySymbolTable.lookupFunction(name, arity) : null;
        return function != null ? TypeDesc.parse(function.getReturnType()) : TypeDesc.VOID;
    }

    private TypeDesc arrayLiteralDesc(List<ASTNode> values) {
        TypeDesc element = values == null || values.isEmpty() ? TypeDesc.INT : descOf(values.get(0));
        return new TypeDesc(element.name(), element.dims() + 1);
    }

    private TypeDesc binaryDesc(NodeBinaryOperation binary) {
        TypeDesc left = descOf(binary.getLeft());
        TypeDesc right = descOf(binary.getRight());
        return switch (binary.getOperator()) {
            case "+" -> (left.type() == Type.STRING || right.type() == Type.STRING)
                    ? TypeDesc.STRING : promote(left, right);
            case "-", "*", "/", "%" -> promote(left, right);
            default -> TypeDesc.BOOLEAN;
        };
    }

    private TypeDesc promote(TypeDesc a, TypeDesc b) {
        if (a.type() == Type.DOUBLE || b.type() == Type.DOUBLE) return TypeDesc.DOUBLE;
        return a.type() == Type.NULL || a.type() == Type.VOID ? b : a;
    }

    /*
     * Tipo estático completo: nombre base canónico y dimensiones de array.
     *
     * parse acepta los tres vocabularios que conviven en un programa PigLatin:
     * PigLatin (NUMERUS, DECIMALIS, TEXTUM, LITTERA, BOOL), Zetariano (int, double, String,
     * char, boolean, void, null) e Y (entero, flotante, cadena, caracter, bool, void). Maneja
     * el sufijo [] (y el prefijo SERIES_ del type checker) como dimensiones.
     * Cualquier otro nombre es una clase.
     */
    private record TypeDesc(String name, int dims) {
        static final TypeDesc INT = new TypeDesc("int", 0);
        static final TypeDesc DOUBLE = new TypeDesc("double", 0);
        static final TypeDesc BOOLEAN = new TypeDesc("boolean", 0);
        static final TypeDesc CHAR = new TypeDesc("char", 0);
        static final TypeDesc STRING = new TypeDesc("String", 0);
        static final TypeDesc VOID = new TypeDesc("void", 0);
        static final TypeDesc NULL = new TypeDesc("null", 0);

        static TypeDesc parse(String typeName) {
            if (typeName == null || typeName.isBlank()) return VOID;
            String base = typeName.trim();
            int dimensions = 0;
            while (base.endsWith("[]")) {
                base = base.substring(0, base.length() - 2);
                dimensions++;
            }
            while (base.startsWith("SERIES_")) {
                base = base.substring("SERIES_".length());
                dimensions++;
            }
            return new TypeDesc(canonical(base), dimensions);
        }

        private static String canonical(String name) {
            return switch (name) {
                case "NUMERUS", "entero", "int" -> "int";
                case "DECIMALIS", "flotante", "double" -> "double";
                case "TEXTUM", "cadena", "String" -> "String";
                case "LITTERA", "caracter", "char" -> "char";
                case "BOOL", "bool", "boolean", "BOOLEAN" -> "boolean";
                case "void", "VOID" -> "void";
                case "null" -> "null";
                default -> name;
            };
        }

        boolean isArray() {
            return dims > 0;
        }

        TypeDesc elementType() {
            return new TypeDesc(name, Math.max(0, dims - 1));
        }

        int slotSize() {
            return type() == Type.DOUBLE ? DOUBLE_WORD : WORD;
        }

        Type type() {
            if (dims > 0) return Type.OBJECT;
            return switch (name) {
                case "int" -> Type.INT;
                case "double" -> Type.DOUBLE;
                case "boolean" -> Type.BOOL;
                case "char" -> Type.CHAR;
                case "String" -> Type.STRING;
                case "void" -> Type.VOID;
                case "null" -> Type.NULL;
                default -> Type.OBJECT;
            };
        }
    }

    private record VarSlot(int offset, TypeDesc desc) {
    }

    private static int alignUp(int value, int alignment) {
        return (value + alignment - 1) / alignment * alignment;
    }
}