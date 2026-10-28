package com.piglatin.zetariano.infrastructure.codegen.c3d;

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
import com.piglatin.zetariano.domain.ast.enums.AssignmentOperator;
import com.piglatin.zetariano.domain.ast.expressions.*;
import com.piglatin.zetariano.domain.ast.expressions.literals.*;
import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.principal.NodeProgram;
import com.piglatin.zetariano.domain.ast.statements.*;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import com.piglatin.zetariano.domain.symboltable.ZetarianoSymbolTable;
import com.piglatin.zetariano.domain.types.ZetarianoTypeTable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Generador de C3D elemental para Zetariano.
 *
 * Cada método se genera en dos pasadas: la primera calcula el frame (offsets negativos
 * respecto a fp) y la segunda emite las instrucciones. Cada visit de expresión devuelve
 * el nombre del temporal que contiene su valor.
 *
 *   fp+4 : fp del llamador
 *   fp+0 : ra
 *   fp-4 : this
 *   fp-8 ... : parámetros int, parámetros double, locales int, locales double
 */
public class ZetarianoC3DVisitor implements Visitor<String> {

    private static final String FRAME_PTR = "fp";

    private static final int WORD = 4;
    private static final int DOUBLE_WORD = 8;
    private static final int THIS_SLOT = -4;
    private static final int SAVED_AREA = 2 * WORD;
    private static final int FRAME_ALIGNMENT = 16;
    private static final int MAX_REG_ARGS = 8;
    private static final int ARRAY_LENGTH_OFFSET = 0;
    private static final int OBJECT_ALIGNMENT = 8;
    private static final int CHAR_STRING_BYTES = 8;
    private static final String ENTRY_LABEL = "main";

    private final C3DContext ctx;
    private final C3DEmitter emitter;
    private final ZetarianoSymbolTable symbolTable;
    private final VtableLayout vtables;

    private final Map<String, String> stringLabels = new LinkedHashMap<>();
    private final Map<String, List<String>> vtableLabels = new LinkedHashMap<>();

    private final Deque<Map<String, VarSlot>> scopes = new ArrayDeque<>();
    private final Deque<String> breakLabels = new ArrayDeque<>();
    private final Deque<String> continueLabels = new ArrayDeque<>();

    private String currentClass;
    private FrameLayout frame;
    private Type currentReturnType = Type.VOID;

    public ZetarianoC3DVisitor(C3DContext ctx, ZetarianoSymbolTable symbolTable,
                               ZetarianoTypeTable typeTable) {
        this.ctx = ctx;
        this.emitter = new C3DEmitter(ctx);
        this.symbolTable = symbolTable;
        this.vtables = new VtableLayout(symbolTable, typeTable);
    }

    public void generate(NodeProgram program) {
        if (program != null) program.accept(this);
    }

    /** Vtables por clase: etiquetas de los métodos en orden de slot. */
    public Map<String, List<String>> getVtables() {
        return Collections.unmodifiableMap(vtableLabels);
    }

    /** Literales de cadena: etiqueta -> texto. */
    public Map<String, String> getStringLiterals() {
        Map<String, String> byLabel = new LinkedHashMap<>();
        stringLabels.forEach((text, label) -> byLabel.put(label, text));
        return Collections.unmodifiableMap(byLabel);
    }

    /** instancia la clase objetivo y llama a su main. */
    public void generateEntryPoint(NodeClassDeclaration targetClass) {
        if (targetClass == null) return;
        String className = targetClass.getName();

        boolean hasNoArgCtor = vtables.constructor(className, 0) != null;
        boolean canInstantiate = hasNoArgCtor || !symbolTable.hasAnyConstructor(className);
        if (!canInstantiate) return;

        VtableLayout.MethodInfo main = vtables.resolveMethod(className, "main", 0);
        boolean hasMain = main != null && "void".equals(main.returnType());

        String previous = currentClass;
        currentClass = className;
        emitFunction(ENTRY_LABEL, false, Type.VOID, List.of(), null,
                () -> emitEntryBody(className, hasNoArgCtor, hasMain ? main : null));
        currentClass = previous;
    }

    private void emitEntryBody(String className, boolean callCtor, VtableLayout.MethodInfo main) {
        String obj = allocateObject(className);
        if (callCtor) {
            emitter.emitParam(obj, Type.OBJECT);
            emitter.emitCallVoid(className + "_init_1", 1, 0);
        }
        if (main != null) {
            emitter.emitParam(obj, Type.OBJECT);
            emitter.emitCallVoid(main.label(), 1, 0);
        }
    }

    @Override
    public String visitProgram(NodeProgram n) {
        if (n == null || n.getClasses() == null) return null;
        for (NodeClassDeclaration classDecl : n.getClasses()) {
            classDecl.accept(this);
        }
        return null;
    }

    @Override
    public String visitImport(NodeImport n) {
        return null;
    }

    @Override
    public String visitClassDeclaration(NodeClassDeclaration n) {
        if (n == null) return null;
        String previous = currentClass;
        currentClass = n.getName();
        vtableLabels.put(currentClass, vtables.vtableLabels(currentClass));

        emitFunction(fieldInitLabel(currentClass), true, Type.VOID, List.of(), null,
                () -> emitFieldInitializers(n));

        for (ASTNode member : n.getMembers()) {
            if (member instanceof NodeConstructorDeclaration ctor) {
                ctor.accept(this);
            } else if (member instanceof NodeMethodDeclaration method) {
                method.accept(this);
            }
        }
        currentClass = previous;
        return null;
    }

    @Override
    public String visitFieldDeclaration(NodeFieldDeclaration n) {
        return null;
    }

    @Override
    public String visitMethodDeclaration(NodeMethodDeclaration n) {
        if (n == null) return null;
        List<NodeParameter> params = n.getParameters();
        String label = currentClass + "_" + n.getName() + "_" + (1 + params.size());
        Type returnType = TypeDesc.parse(n.getReturnType()).type();
        emitFunction(label, true, returnType, params, n.getBody(), null);
        return null;
    }

    @Override
    public String visitConstructorDeclaration(NodeConstructorDeclaration n) {
        if (n == null) return null;
        List<NodeParameter> params = n.getParameters();
        String label = currentClass + "_init_" + (1 + params.size());
        emitFunction(label, true, Type.VOID, params, n.getBody(), null);
        return null;
    }

    @Override
    public String visitParameter(NodeParameter n) {
        return null;
    }

    /** Inicializadores de campos: primero los del padre, luego los propios en orden. */
    private void emitFieldInitializers(NodeClassDeclaration n) {
        if (n.getSuperClass() != null) {
            emitter.emitParam(loadThis(), Type.OBJECT);
            emitter.emitCallVoid(fieldInitLabel(n.getSuperClass()), 1, 0);
        }
        String self = null;
        for (ASTNode member : n.getMembers()) {
            if (!(member instanceof NodeFieldDeclaration field) || field.getInitializer() == null) continue;
            if (self == null) self = loadThis();
            VtableLayout.FieldInfo info = vtables.field(currentClass, field.getName());
            Place place = new Place(self, info.offset(), TypeDesc.parse(info.typeName()));
            store(place, evaluateAs(field.getInitializer(), place.desc()));
        }
    }

    private String fieldInitLabel(String className) {
        return className + "_fieldinit_1";
    }

    // ==========================================
    // Funciones: frame, prólogo, epílogo
    // ===========================================

    /**
     * Emite una función completa. prelude se ejecuta tras guardar los argumentos y antes
     * del cuerpo.
     */
    private void emitFunction(String label, boolean hasThis, Type returnType, List<NodeParameter> params,
                              ASTNode body, Runnable prelude) {
        frame = buildFrame(params, body);
        currentReturnType = returnType;
        scopes.clear();

        ctx.emit(new Label(label));
        emitter.emitPrologue(frame.size);
        List<C3DEmitter.ParamSlot> paramSlots = new ArrayList<>();
        for (NodeParameter param : params) {
            VarSlot slot = frame.params.get(param);
            paramSlots.add(new C3DEmitter.ParamSlot(slot.offset(), slot.desc().type() == Type.DOUBLE));
        }
        emitter.spillArguments(hasThis, paramSlots);

        scopes.push(parameterScope(params));
        if (prelude != null) prelude.run();
        if (body != null) body.accept(this);
        scopes.pop();

        emitter.emitEpilogue();
        ctx.emit(new ReturnVoid());
        frame = null;
    }

    private void requireRegister(int index) {
        if (index >= MAX_REG_ARGS) {
            throw new IllegalStateException("Más de " + MAX_REG_ARGS + " argumentos del mismo tipo no está soportado.");
        }
    }

    private Map<String, VarSlot> parameterScope(List<NodeParameter> params) {
        Map<String, VarSlot> scope = new HashMap<>();
        for (NodeParameter param : params) {
            scope.put(param.getName(), frame.params.get(param));
        }
        return scope;
    }

    //* Pasada 1: asignación de offsets

    private record LocalDecl(ASTNode node, TypeDesc desc) {
    }

    private static final class FrameLayout {
        private final Map<NodeParameter, VarSlot> params = new IdentityHashMap<>();
        private final Map<ASTNode, VarSlot> locals = new IdentityHashMap<>();
        private int size;
    }

    private static final class SlotCursor {
        private int used = WORD;

        int allocate(boolean isDouble) {
            used = isDouble ? alignUp(used + DOUBLE_WORD, DOUBLE_WORD) : used + WORD;
            return -used;
        }
    }

    private FrameLayout buildFrame(List<NodeParameter> params, ASTNode body) {
        FrameLayout layout = new FrameLayout();
        SlotCursor cursor = new SlotCursor();

        List<LocalDecl> paramDecls = new ArrayList<>();
        for (NodeParameter param : params) {
            paramDecls.add(new LocalDecl(param, TypeDesc.parse(param.getType())));
        }
        List<LocalDecl> localDecls = new ArrayList<>();
        collectLocals(body, localDecls);

        assignSlots(paramDecls, false, cursor, layout);
        assignSlots(paramDecls, true, cursor, layout);
        assignSlots(localDecls, false, cursor, layout);
        assignSlots(localDecls, true, cursor, layout);

        layout.size = alignUp(SAVED_AREA + cursor.used, FRAME_ALIGNMENT);
        return layout;
    }

    private void assignSlots(List<LocalDecl> decls, boolean doubles, SlotCursor cursor, FrameLayout layout) {
        for (LocalDecl decl : decls) {
            boolean isDouble = decl.desc().type() == Type.DOUBLE;
            if (isDouble != doubles) continue;
            VarSlot slot = new VarSlot(cursor.allocate(isDouble), decl.desc());
            if (decl.node() instanceof NodeParameter param) {
                layout.params.put(param, slot);
            } else {
                layout.locals.put(decl.node(), slot);
            }
        }
    }

    private void collectLocals(ASTNode node, List<LocalDecl> out) {
        if (node instanceof NodeBlock block) {
            for (ASTNode child : block.getInstructions()) collectLocals(child, out);
        } else if (node instanceof NodeVariableDeclaration decl) {
            out.add(new LocalDecl(decl, TypeDesc.parse(decl.getType())));
        } else if (node instanceof NodeArrayDeclaration decl) {
            out.add(new LocalDecl(decl, new TypeDesc(decl.getType(), Math.max(1, decl.getDimensions()))));
        } else if (node instanceof NodeIf stmt) {
            collectLocals(stmt.getThenBlock(), out);
            collectLocals(stmt.getElseBlock(), out);
        } else if (node instanceof NodeWhile stmt) {
            collectLocals(stmt.getBlock(), out);
        } else if (node instanceof NodeDoWhile stmt) {
            collectLocals(stmt.getBlock(), out);
        } else if (node instanceof NodeFor stmt) {
            collectLocals(stmt.getInitializer(), out);
            collectLocals(stmt.getBlock(), out);
        } else if (node instanceof NodeSwitch stmt) {
            for (NodeCase switchCase : stmt.getCases()) collectLocals(switchCase.getBlock(), out);
            collectLocals(stmt.getDefaultBlock(), out);
        }
    }

    @Override
    public String visitBlock(NodeBlock n) {
        if (n == null) return null;
        scopes.push(new HashMap<>());
        for (ASTNode instruction : n.getInstructions()) {
            if (instruction != null) instruction.accept(this);
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
        scopes.peek().put(n.getName(), slot);
        return null;
    }

    @Override
    public String visitArrayDeclaration(NodeArrayDeclaration n) {
        VarSlot slot = localSlot(n);
        String array;
        if (n.getInitializer() != null) {
            array = emitArrayInit(n.getInitializer(), slot.desc().elementType());
        } else if (n.getSize() != null) {
            array = emitArrayAllocation(List.of(n.getSize().accept(this)), 0, slot.desc());
        } else {
            array = ctx.newTemp();
            ctx.emit(new ConstNull(array));
        }
        ctx.emit(new StoreInt(FRAME_PTR, slot.offset(), array));
        scopes.peek().put(n.getName(), slot);
        return null;
    }

    private VarSlot localSlot(ASTNode declaration) {
        VarSlot slot = frame.locals.get(declaration);
        if (slot == null) {
            throw new IllegalStateException("Declaración local sin slot en el frame: " + declaration);
        }
        return slot;
    }

    @Override
    public String visitAssignment(NodeAssignment n) {
        AssignmentOperator op = n.getOperator();
        Place place = resolvePlace(n.getTarget());
        switch (op) {
            case ASSIGN -> store(place, evaluateAs(n.getExpression(), place.desc()));
            case ADD_ASSIGN, SUB_ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN -> emitCompoundAssignment(n, place);
            default -> emitStep(place, op.name().startsWith("INC"));
        }
        return null;
    }

    private void emitCompoundAssignment(NodeAssignment n, Place place) {
        Type targetType = place.desc().type();
        String current = load(place);
        String rhs = n.getExpression().accept(this);

        if (n.getOperator() == AssignmentOperator.ADD_ASSIGN && targetType == Type.STRING) {
            String joined = ctx.newTemp();
            emitter.emitConcat(joined, current, stringOf(rhs, descOf(n.getExpression())));
            store(place, joined);
            return;
        }

        Type rhsType = staticTypeOf(n.getExpression());
        Type opType = (targetType == Type.DOUBLE || rhsType == Type.DOUBLE) ? Type.DOUBLE : Type.INT;
        String result = emitArithmetic(compoundSymbol(n.getOperator()),
                coerce(current, targetType, opType), coerce(rhs, rhsType, opType), opType);
        store(place, coerce(result, opType, targetType));
    }

    private String compoundSymbol(AssignmentOperator op) {
        return switch (op) {
            case ADD_ASSIGN -> "+";
            case SUB_ASSIGN -> "-";
            case MUL_ASSIGN -> "*";
            case DIV_ASSIGN -> "/";
            case MOD_ASSIGN -> "%";
            default -> throw new IllegalArgumentException("Operador no compuesto: " + op);
        };
    }

    /** Suma o resta 1 al lugar y devuelve el valor nuevo. */
    private String emitStep(Place place, boolean increment) {
        Type type = place.desc().type() == Type.DOUBLE ? Type.DOUBLE : Type.INT;
        String current = load(place);
        String one = type == Type.DOUBLE ? newConstDouble(1.0) : newConstInt(1);
        String next = emitArithmetic(increment ? "+" : "-", current, one, type);
        store(place, next);
        return next;
    }

    @Override
    public String visitRead(NodeRead n) {
        String value = ctx.newTemp();
        ctx.emit(new ReadInt(value));
        return value;
    }

    @Override
    public String visitPrint(NodePrint n) {
        for (NodeExpression expr : n.getExpressions()) {
            if (expr == null) continue;
            TypeDesc desc = descOf(expr);
            printValue(expr.accept(this), desc, n.isNewline());
        }
        return null;
    }

    private void printValue(String value, TypeDesc desc, boolean newline) {
        if (desc.type() == Type.CHAR) {
            ctx.emit(new PrintString(charToString(value)));
            if (newline) ctx.emit(new PrintNewline());
        } else if (newline) {
            emitter.emitPrintln(value, desc.type());
        } else {
            emitter.emitPrint(value, desc.type());
        }
    }

    @Override
    public String visitIf(NodeIf n) {
        String cond = n.getCondition().accept(this);
        String lEnd = ctx.newLabel();
        if (n.getElseBlock() == null) {
            ctx.emit(new BranchFalse(cond, lEnd));
            accept(n.getThenBlock());
        } else {
            String lElse = ctx.newLabel();
            ctx.emit(new BranchFalse(cond, lElse));
            accept(n.getThenBlock());
            ctx.emit(new Jump(lEnd));
            ctx.emit(new Label(lElse));
            accept(n.getElseBlock());
        }
        ctx.emit(new Label(lEnd));
        return null;
    }

    @Override
    public String visitSwitch(NodeSwitch n) {
        String value = n.getExpression().accept(this);
        boolean compareStrings = staticTypeOf(n.getExpression()) == Type.STRING;
        List<NodeCase> cases = n.getCases();
        String lDefault = ctx.newLabel();
        String lEnd = ctx.newLabel();

        List<String> caseLabels = new ArrayList<>();
        for (NodeCase switchCase : cases) {
            String lCase = ctx.newLabel();
            caseLabels.add(lCase);
            String caseValue = switchCase.getExpression().accept(this);
            String cmp = ctx.newTemp();
            ctx.emit(compareStrings ? new StrEq(cmp, value, caseValue) : new EqInt(cmp, value, caseValue));
            ctx.emit(new BranchTrue(cmp, lCase));
        }
        ctx.emit(new Jump(lDefault));

        breakLabels.push(lEnd);
        for (int i = 0; i < cases.size(); i++) {
            ctx.emit(new Label(caseLabels.get(i)));
            accept(cases.get(i).getBlock());
        }
        ctx.emit(new Label(lDefault));
        accept(n.getDefaultBlock());
        breakLabels.pop();

        ctx.emit(new Label(lEnd));
        return null;
    }

    @Override
    public String visitCase(NodeCase n) {
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
        accept(n.getInitializer());
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

    @Override
    public String visitReturn(NodeReturn n) {
        if (n.getExpression() == null || currentReturnType == Type.VOID) {
            if (n.getExpression() != null) n.getExpression().accept(this);
            emitter.emitEpilogue();
            ctx.emit(new ReturnVoid());
            return null;
        }
        String value = coerce(n.getExpression().accept(this), staticTypeOf(n.getExpression()), currentReturnType);
        emitter.emitEpilogue();
        ctx.emit(currentReturnType == Type.DOUBLE ? new ReturnDouble(value) : new ReturnInt(value));
        return null;
    }

    private void accept(ASTNode node) {
        if (node != null) node.accept(this);
    }

    @Override
    public String visitTernaryExpression(NodeTernaryExpression n) {
        Type trueType = staticTypeOf(n.getTrueExpression());
        Type falseType = staticTypeOf(n.getFalseExpression());
        Type resultType = (trueType == Type.DOUBLE || falseType == Type.DOUBLE) ? Type.DOUBLE : trueType;

        String cond = n.getCondition().accept(this);
        String result = ctx.newTemp();
        String lElse = ctx.newLabel();
        String lEnd = ctx.newLabel();

        ctx.emit(new BranchFalse(cond, lElse));
        emitMove(result, coerce(n.getTrueExpression().accept(this), trueType, resultType), resultType);
        ctx.emit(new Jump(lEnd));
        ctx.emit(new Label(lElse));
        emitMove(result, coerce(n.getFalseExpression().accept(this), falseType, resultType), resultType);
        ctx.emit(new Label(lEnd));
        return result;
    }

    @Override
    public String visitBinaryExpression(NodeBinaryExpression n) {
        String op = n.getOperator();
        return switch (op) {
            case "&&" -> emitShortCircuit(n, true);
            case "||" -> emitShortCircuit(n, false);
            case "<", "<=", ">", ">=", "==", "!=" -> emitComparisonExpression(n, op);
            default -> emitArithmeticExpression(n, op);
        };
    }

    /**
     * El operando derecho solo se evalúa si hace falta y la
     * combinación final la hace C3DEmitter.
     */
    private String emitShortCircuit(NodeBinaryExpression n, boolean isAnd) {
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

    private String emitComparisonExpression(NodeBinaryExpression n, String op) {
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

    private String emitArithmeticExpression(NodeBinaryExpression n, String op) {
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
    public String visitUnaryExpression(NodeUnaryExpression n) {
        String op = n.getOperator();
        if ("++".equals(op) || "--".equals(op)) {
            return emitStep(resolvePlace((NodeLvalue) n.getExpression()), "++".equals(op));
        }
        String operand = n.getExpression().accept(this);
        switch (op) {
            case "-" -> {
                String result = ctx.newTemp();
                ctx.emit(staticTypeOf(n.getExpression()) == Type.DOUBLE
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

    @Override
    public String visitIdentifier(NodeIdentifier n) {
        return load(resolvePlace(n));
    }

    @Override
    public String visitFieldAccess(NodeFieldAccess n) {
        if (isArrayLength(n)) {
            String result = ctx.newTemp();
            ctx.emit(new LoadInt(result, n.getTarget().accept(this), ARRAY_LENGTH_OFFSET));
            return result;
        }
        return load(resolvePlace(n));
    }

    @Override
    public String visitIndexAccess(NodeIndexAccess n) {
        return load(resolvePlace(n));
    }

    @Override
    public String visitThis(NodeThis n) {
        return loadThis();
    }

    @Override
    public String visitSuper(NodeSuper n) {
        return loadThis();
    }

    // Llamadas y objetos

    @Override
    public String visitMethodCall(NodeMethodCall n) {
        String className = n.getTarget() == null ? currentClass : classNameOf(n.getTarget());
        List<NodeExpression> args = n.getArguments() != null ? n.getArguments() : List.of();
        VtableLayout.MethodInfo method = className == null
                ? null : vtables.resolveMethod(className, n.getMethodName(), args.size());
        if (method == null) {
            throw new IllegalStateException("Método no resuelto: " + className + "." + n.getMethodName());
        }

        String receiver = n.getTarget() == null ? loadThis() : n.getTarget().accept(this);
        int[] counts = emitParams(receiver, args, method.paramTypes());
        return emitMethodCall(method, className, receiver, counts[0], counts[1]);
    }

    private String emitMethodCall(VtableLayout.MethodInfo method, String className, String receiver,
                                  int nInt, int nDouble) {
        Type returnType = TypeDesc.parse(method.returnType()).type();
        int vtableOffset = method.isPrivate()
                ? -1 : vtables.vtableOffset(className, method.name(), method.paramTypes().size());
        boolean direct = vtableOffset < 0;
        String label = method.label();

        if (returnType == Type.VOID) {
            if (direct) {
                emitter.emitCallVoid(label, nInt, nDouble);
            } else {
                emitVirtualCallVoid(receiver, vtableOffset, nInt, nDouble);
            }
            return null;
        }
        String result = ctx.newTemp();
        if (returnType == Type.DOUBLE) {
            if (direct) {
                emitter.emitCallDouble(result, label, nInt, nDouble);
            } else {
                emitter.emitVirtualCallDouble(result, receiver, vtableOffset, nInt, nDouble);
            }
        } else if (direct) {
            emitter.emitCallInt(result, label, nInt, nDouble);
        } else {
            emitter.emitVirtualCallInt(result, receiver, vtableOffset, nInt, nDouble);
        }
        return result;
    }

    /**
     * Evalúa los argumentos y emite los param. Devuelve
     * {nInt, nDouble}; this cuenta como entero.
     */
    private int[] emitParams(String receiver, List<NodeExpression> args, List<String> paramTypes) {
        List<String> values = new ArrayList<>();
        List<Type> types = new ArrayList<>();
        for (int i = 0; i < args.size(); i++) {
            TypeDesc target = TypeDesc.parse(paramTypes.get(i));
            values.add(evaluateAs(args.get(i), target));
            types.add(target.type());
        }

        int nInt = 1;
        int nDouble = 0;
        emitter.emitParam(receiver, Type.OBJECT);
        for (int i = 0; i < values.size(); i++) {
            emitter.emitParam(values.get(i), types.get(i));
            if (types.get(i) == Type.DOUBLE) nDouble++;
            else nInt++;
        }
        return new int[]{nInt, nDouble};
    }

    @Override
    public String visitNewObject(NodeNewObject n) {
        String className = n.getClassName();
        List<NodeExpression> args = n.getArguments() != null ? n.getArguments() : List.of();
        String obj = allocateObject(className);

        VtableLayout.MethodInfo ctor = vtables.constructor(className, args.size());
        if (ctor != null) {
            int[] counts = emitParams(obj, args, ctor.paramTypes());
            emitter.emitCallVoid(ctor.label(), counts[0], counts[1]);
        }
        return obj;
    }

    /** Reserva el objeto, escribe la vtable y ejecuta los inicializadores de campos. */
    private String allocateObject(String className) {
        String obj = ctx.newTemp();
        emitter.emitAllocObject(obj, vtables.objectSize(className), vtables.vtableLabel(className));
        emitter.emitParam(obj, Type.OBJECT);
        emitter.emitCallVoid(fieldInitLabel(className), 1, 0);
        return obj;
    }

    private void emitVirtualCallVoid(String obj, int vtableOffset, int nInt, int nDouble) {
        ctx.emit(new CallIndirect(loadVirtualTarget(obj, vtableOffset), nInt, nDouble));
    }

    private String loadVirtualTarget(String obj, int vtableOffset) {
        String vtable = ctx.newTemp();
        ctx.emit(new LoadInt(vtable, obj, 0));
        String method = ctx.newTemp();
        ctx.emit(new LoadInt(method, vtable, vtableOffset));
        return method;
    }

    // Arrays
    // Layout: longitud (4 bytes) en +0; elementos desde +4, o desde +8 si son double.

    @Override
    public String visitNewArray(NodeNewArray n) {
        List<String> sizes = new ArrayList<>();
        for (NodeExpression dimension : n.getDimensions()) {
            sizes.add(dimension.accept(this));
        }
        return emitArrayAllocation(sizes, 0, new TypeDesc(n.getType(), sizes.size()));
    }

    @Override
    public String visitArrayInitializer(NodeArrayInitializer n) {
        List<NodeExpression> elements = n.getElements();
        TypeDesc element = elements.isEmpty() ? TypeDesc.INT : descOf(elements.get(0));
        return emitArrayInit(n, element);
    }

    private String emitArrayInit(NodeArrayInitializer init, TypeDesc element) {
        List<NodeExpression> elements = init.getElements();
        int header = arrayHeader(element);
        int elementSize = element.slotSize();
        String array = allocateArray(newConstInt(elements.size()), elementSize, header);

        for (int i = 0; i < elements.size(); i++) {
            NodeExpression expr = elements.get(i);
            String value = (expr instanceof NodeArrayInitializer nested && element.isArray())
                    ? emitArrayInit(nested, element.elementType())
                    : evaluateAs(expr, element);
            store(new Place(array, header + i * elementSize, element), value);
        }
        return array;
    }

    /** Reserva un array de elementos; los niveles internos se crean en bucle. */
    private String emitArrayAllocation(List<String> sizes, int level, TypeDesc arrayDesc) {
        TypeDesc element = arrayDesc.elementType();
        String length = sizes.get(level);
        String array = allocateArray(length, element.slotSize(), arrayHeader(element));
        if (level == sizes.size() - 1) return array;

        String index = newConstInt(0);
        String one = newConstInt(1);
        String lStart = ctx.newLabel();
        String lEnd = ctx.newLabel();

        ctx.emit(new Label(lStart));
        String cmp = ctx.newTemp();
        ctx.emit(new LtInt(cmp, index, length));
        ctx.emit(new BranchFalse(cmp, lEnd));

        String inner = emitArrayAllocation(sizes, level + 1, element);
        String scaled = ctx.newTemp();
        ctx.emit(new MulInt(scaled, index, newConstInt(WORD)));
        String slotAddr = ctx.newTemp();
        ctx.emit(new AddInt(slotAddr, array, scaled));
        ctx.emit(new StoreInt(slotAddr, arrayHeader(element), inner));

        ctx.emit(new AddInt(index, index, one));
        ctx.emit(new Jump(lStart));
        ctx.emit(new Label(lEnd));
        return array;
    }

    /** Alloc de (cabecera + longitud * tamaño) redondeado a 8, y escribe la longitud. */
    private String allocateArray(String length, int elementSize, int header) {
        String body = ctx.newTemp();
        ctx.emit(new MulInt(body, length, newConstInt(elementSize)));
        String padded = ctx.newTemp();
        ctx.emit(new AddInt(padded, body, newConstInt(header + OBJECT_ALIGNMENT - 1)));
        String eight = newConstInt(OBJECT_ALIGNMENT);
        String blocks = ctx.newTemp();
        ctx.emit(new DivInt(blocks, padded, eight));
        String size = ctx.newTemp();
        ctx.emit(new MulInt(size, blocks, eight));

        String array = ctx.newTemp();
        ctx.emit(new Alloc(array, size));
        ctx.emit(new StoreInt(array, ARRAY_LENGTH_OFFSET, length));
        return array;
    }

    private int arrayHeader(TypeDesc element) {
        return element.type() == Type.DOUBLE ? DOUBLE_WORD : WORD;
    }

    private boolean isArrayLength(NodeFieldAccess n) {
        return "length".equals(n.getFieldName()) && descOf(n.getTarget()).isArray();
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
        return newConstString(n.getValue() != null ? n.getValue() : "");
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

    // Lugares de memoria (lvalues), carga y almacenamiento

    /** Dirección base + offset donde vive un valor, y su tipo. */
    private record Place(String base, int offset, TypeDesc desc) {
    }

    private Place resolvePlace(NodeLvalue lvalue) {
        if (lvalue instanceof NodeIdentifier id) return identifierPlace(id);
        if (lvalue instanceof NodeFieldAccess access) return fieldPlace(access);
        if (lvalue instanceof NodeIndexAccess access) return indexPlace(access);
        throw new IllegalArgumentException("No es un lvalue: " + lvalue);
    }

    private Place identifierPlace(NodeIdentifier id) {
        VarSlot slot = findLocal(id.getName());
        if (slot != null) {
            return new Place(FRAME_PTR, slot.offset(), slot.desc());
        }
        VtableLayout.FieldInfo field = vtables.field(currentClass, id.getName());
        if (field == null) {
            throw new IllegalStateException("Identificador no resuelto: " + id.getName());
        }
        return new Place(loadThis(), field.offset(), TypeDesc.parse(field.typeName()));
    }

    private Place fieldPlace(NodeFieldAccess access) {
        String base = access.getTarget().accept(this);
        VtableLayout.FieldInfo field = vtables.field(classNameOf(access.getTarget()), access.getFieldName());
        if (field == null) {
            throw new IllegalStateException("Campo no resuelto: " + access.getFieldName());
        }
        return new Place(base, field.offset(), TypeDesc.parse(field.typeName()));
    }

    private Place indexPlace(NodeIndexAccess access) {
        TypeDesc element = descOf(access.getArrayTarget()).elementType();
        String base = access.getArrayTarget().accept(this);
        String index = access.getIndex().accept(this);

        String scaled = ctx.newTemp();
        ctx.emit(new MulInt(scaled, index, newConstInt(element.slotSize())));
        String address = ctx.newTemp();
        ctx.emit(new AddInt(address, base, scaled));
        return new Place(address, arrayHeader(element), element);
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

    private String loadThis() {
        String result = ctx.newTemp();
        ctx.emit(new LoadInt(result, FRAME_PTR, THIS_SLOT));
        return result;
    }

    private VarSlot findLocal(String name) {
        for (Map<String, VarSlot> scope : scopes) {
            VarSlot slot = scope.get(name);
            if (slot != null) return slot;
        }
        return null;
    }


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

    private void emitMove(String result, String source, Type type) {
        ctx.emit(type == Type.DOUBLE ? new MoveDouble(result, source) : new MoveInt(result, source));
    }

    /** Evalúa la expresión y la convierte al tipo destino. */
    private String evaluateAs(NodeExpression expr, TypeDesc target) {
        String value = expr instanceof NodeArrayInitializer init && target.isArray()
                ? emitArrayInit(init, target.elementType())
                : expr.accept(this);
        return coerce(value, staticTypeOf(expr), target.type());
    }

    /** Conversión implícita int -> double (y double -> int para asignaciones compuestas). */
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

    /** Convierte un valor a String para la concatenación. */
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
        return stringLabels.computeIfAbsent(text, key -> "str_" + stringLabels.size());
    }

    // Tipos estáticos

    /** Tipo estático de una expresión. Los arrays se representan como OBJECT (puntero). */
    private Type staticTypeOf(ASTNode node) {
        return descOf(node).type();
    }

    /** Nombre de la clase cuando el tipo estático es OBJECT y no es un array; si no, null. */
    private String classNameOf(ASTNode node) {
        TypeDesc desc = descOf(node);
        return desc.type() == Type.OBJECT && !desc.isArray() ? desc.name() : null;
    }

    private TypeDesc descOf(ASTNode node) {
        if (node instanceof NodeThis) return new TypeDesc(currentClass, 0);
        if (node instanceof NodeIdentifier id) return identifierDesc(id);
        if (node instanceof NodeFieldAccess access) return fieldAccessDesc(access);
        if (node instanceof NodeIndexAccess access) return descOf(access.getArrayTarget()).elementType();
        if (node instanceof NodeMethodCall call) return methodCallDesc(call);
        if (node instanceof NodeNewObject created) return new TypeDesc(created.getClassName(), 0);
        if (node instanceof NodeNewArray created) return new TypeDesc(created.getType(), created.getDimensions().size());
        if (node instanceof NodeArrayInitializer init) return arrayInitializerDesc(init);
        if (node instanceof NodeIntegerLiteral) return TypeDesc.INT;
        if (node instanceof NodeDecimalLiteral) return TypeDesc.DOUBLE;
        if (node instanceof NodeCharLiteral) return new TypeDesc("char", 0);
        if (node instanceof NodeStringLiteral) return new TypeDesc("String", 0);
        if (node instanceof NodeBooleanLiteral) return TypeDesc.BOOLEAN;
        if (node instanceof NodeNullLiteral) return new TypeDesc("null", 0);
        if (node instanceof NodeBinaryExpression binary) return binaryDesc(binary);
        if (node instanceof NodeUnaryExpression unary) {
            return "!".equals(unary.getOperator()) ? TypeDesc.BOOLEAN : descOf(unary.getExpression());
        }
        if (node instanceof NodeTernaryExpression ternary) {
            return promote(descOf(ternary.getTrueExpression()), descOf(ternary.getFalseExpression()));
        }
        if (node instanceof NodeRead) return TypeDesc.INT;
        return TypeDesc.VOID;
    }

    private TypeDesc identifierDesc(NodeIdentifier id) {
        VarSlot slot = findLocal(id.getName());
        if (slot != null) return slot.desc();
        VtableLayout.FieldInfo field = vtables.field(currentClass, id.getName());
        return field != null ? TypeDesc.parse(field.typeName()) : TypeDesc.VOID;
    }

    private TypeDesc fieldAccessDesc(NodeFieldAccess access) {
        if (isArrayLength(access)) return TypeDesc.INT;
        String className = classNameOf(access.getTarget());
        VtableLayout.FieldInfo field = className != null ? vtables.field(className, access.getFieldName()) : null;
        return field != null ? TypeDesc.parse(field.typeName()) : TypeDesc.VOID;
    }

    private TypeDesc methodCallDesc(NodeMethodCall call) {
        String className = call.getTarget() == null ? currentClass : classNameOf(call.getTarget());
        int paramCount = call.getArguments() != null ? call.getArguments().size() : 0;
        VtableLayout.MethodInfo method = className != null
                ? vtables.resolveMethod(className, call.getMethodName(), paramCount) : null;
        return method != null ? TypeDesc.parse(method.returnType()) : TypeDesc.VOID;
    }

    private TypeDesc arrayInitializerDesc(NodeArrayInitializer init) {
        TypeDesc element = init.getElements().isEmpty() ? TypeDesc.INT : descOf(init.getElements().get(0));
        return new TypeDesc(element.name(), element.dims() + 1);
    }

    private TypeDesc binaryDesc(NodeBinaryExpression binary) {
        TypeDesc left = descOf(binary.getLeft());
        TypeDesc right = descOf(binary.getRight());
        return switch (binary.getOperator()) {
            case "+" -> (left.type() == Type.STRING || right.type() == Type.STRING)
                    ? new TypeDesc("String", 0) : promote(left, right);
            case "-", "*", "/", "%" -> promote(left, right);
            default -> TypeDesc.BOOLEAN;
        };
    }

    private TypeDesc promote(TypeDesc a, TypeDesc b) {
        if (a.type() == Type.DOUBLE || b.type() == Type.DOUBLE) return TypeDesc.DOUBLE;
        return a.type() == Type.NULL || a.type() == Type.VOID ? b : a;
    }

    /** nombre base y dimensiones de array. */
    private record TypeDesc(String name, int dims) {
        static final TypeDesc INT = new TypeDesc("int", 0);
        static final TypeDesc DOUBLE = new TypeDesc("double", 0);
        static final TypeDesc BOOLEAN = new TypeDesc("boolean", 0);
        static final TypeDesc VOID = new TypeDesc("void", 0);

        static TypeDesc parse(String typeName) {
            String base = typeName;
            int dimensions = 0;
            while (base.endsWith("[]")) {
                base = base.substring(0, base.length() - 2);
                dimensions++;
            }
            return new TypeDesc(base, dimensions);
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