package com.piglatin.y.infrastructure.codegen.c3d;

import com.piglatin.common.infrastructure.codegen.ir.C3DContext;
import com.piglatin.common.infrastructure.codegen.ir.C3DEmitter;
import com.piglatin.common.infrastructure.codegen.ir.Type;
import com.piglatin.common.infrastructure.codegen.ir.instructions.arithmetic.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.calls.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.comparisons.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.constants.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.controlflow.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.conversions.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.memory.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.runtime.*;
import com.piglatin.y.domain.ast.expressions.*;
import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.principal.NodeProgram;
import com.piglatin.y.domain.ast.statements.*;
import com.piglatin.y.domain.ast.visitor.Visitor;
import com.piglatin.y.domain.symboltable.FunctionSymbol;
import com.piglatin.y.domain.symboltable.ParameterType;
import com.piglatin.y.domain.symboltable.YSymbolTable;
import com.piglatin.y.domain.types.YTypeTable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Generador de C3D elemental para Y (AST -> Instruction).
 *
 * Modelo de memoria:
 *  - Locales y parámetros viven en el frame con offsets negativos desde fp
 *    (primer parámetro en fp-4). Se calculan en una primera pasada por función.
 *  - Arreglos: Heap[a+0] = longitud; elementos desde +4 (+8 si son flotante);
 *    tamaño total redondeado a 8. Arreglos multidimensionales = arreglo de punteros.
 *  - Structs: campos desde +0 con offsets en bytes (ver StructLayout).
 *  - Cadenas: bytes en heap terminados en \0.
 *  - Funciones: etiqueta nombre_N (N = aridad).
 */
public class YC3DVisitor implements Visitor<String> {

    private static final String FP = C3DEmitter.FP;

    private static final String T_INT = "entero";
    private static final String T_DOUBLE = "flotante";
    private static final String T_CHAR = "caracter";
    private static final String T_STRING = "cadena";
    private static final String T_BOOL = "bool";
    private static final String T_VOID = "void";

    /** Posición de una variable en el frame: offset negativo desde fp. */
    private record Slot(int offset, String type) {}

    /** Dirección efectiva: base (registro/temporal) + offset en bytes. */
    private record Address(String base, int offset, String type) {}

    private final C3DContext ctx;
    private final C3DEmitter emitter;
    private final StructLayout structLayout;
    private final YSymbolTable symbolTable;
    private final YTypeTable typeTable;


    private final Map<ASTNode, Slot> declSlots = new IdentityHashMap<>();
    private final Deque<Map<String, Slot>> scopes = new ArrayDeque<>();
    private int frameUsed;
    private String currentReturnType = T_VOID;

    private final Deque<String> breakLabels = new ArrayDeque<>();
    private final Deque<String> continueLabels = new ArrayDeque<>();

    /** Tipo que produjo cada leer(). */
    private final Map<ASTNode, String> readTypes = new IdentityHashMap<>();

    /** Tipo esperado por el contexto */
    private String expectedType;

    public YC3DVisitor(C3DContext ctx,
                       StructLayout structLayout,
                       YSymbolTable symbolTable,
                       YTypeTable typeTable) {
        this.ctx = ctx;
        this.emitter = new C3DEmitter(ctx);
        this.structLayout = structLayout;
        this.symbolTable = symbolTable;
        this.typeTable = typeTable;
    }

    public String generate(NodeProgram program) {
        return program.accept(this);
    }

    @Override
    public String visitProgram(NodeProgram n) {
        if (n == null) return null;

        if (n.getStructures() != null) {
            for (NodeStructureDefinition s : n.getStructures()) {
                if (s != null) structLayout.registerStruct(s);
            }
        }
        if (n.getFunctions() != null) {
            for (NodeFunctionDefinition f : n.getFunctions()) {
                if (f != null) f.accept(this);
            }
        }
        return null;
    }

    @Override
    public String visitStructureDefinition(NodeStructureDefinition n) {
        if (n != null) structLayout.registerStruct(n);
        return null;
    }

    @Override
    public String visitFieldDeclaration(NodeFieldDeclaration n) {
        return null;
    }

    @Override
    public String visitFunctionDefinition(NodeFunctionDefinition n) {
        if (n == null) return null;

        declSlots.clear();
        scopes.clear();
        breakLabels.clear();
        continueLabels.clear();
        frameUsed = 0;
        currentReturnType = n.getReturnType() == null || n.getReturnType().isEmpty()
                ? T_VOID : n.getReturnType();

        //? Pasada 1: parámetros y locales -> offsets en el frame.
        List<NodeParameter> params = n.getParameters() != null ? n.getParameters() : Collections.emptyList();
        List<C3DEmitter.ParamSlot> spill = new ArrayList<>();
        Map<String, Slot> paramScope = new HashMap<>();

        for (NodeParameter p : params) {
            String type = p.isArray() ? p.getType() + "[]" : p.getType();
            Slot slot = allocSlot(type);
            declSlots.put(p, slot);
            paramScope.put(p.getName(), slot);
            spill.add(new C3DEmitter.ParamSlot(slot.offset(), isDouble(type)));
        }
        collectLocals(n.getBlock());

        int frameSize = StructLayout.alignUp(C3DEmitter.SAVED_AREA + frameUsed, C3DEmitter.FRAME_ALIGNMENT);

        //? Pasada 2: emisión.
        ctx.emit(new Label(functionLabel(n.getName(), params.size())));
        emitter.emitPrologue(frameSize);
        emitter.spillArguments(false, spill);

        scopes.push(paramScope);
        if (n.getBlock() != null) n.getBlock().accept(this);
        scopes.pop();

        //? Retorno implícito al final de la función.
        emitter.emitEpilogue();
        ctx.emit(new ReturnVoid());

        declSlots.clear();
        return null;
    }

    @Override
    public String visitParameter(NodeParameter n) {
        return null;
    }

    private String functionLabel(String name, int arity) {
        return name + "_" + arity;
    }

    private Slot allocSlot(String type) {
        if (isDouble(type)) {
            frameUsed = StructLayout.alignUp(frameUsed + 8, 8);
        } else {
            frameUsed += 4;
        }
        return new Slot(-frameUsed, type);
    }

    /** Pasada 1: reserva un slot por cada declaración local de la función. */
    private void collectLocals(ASTNode node) {
        if (node == null) return;

        if (node instanceof NodeBlock b) {
            if (b.getInstructions() != null) {
                for (ASTNode i : b.getInstructions()) collectLocals(i);
            }
        } else if (node instanceof NodeVariableDeclaration d) {
            declSlots.put(d, allocSlot(d.getType()));
        } else if (node instanceof NodeArrayDeclaration a) {
            declSlots.put(a, allocSlot(arrayType(a)));
        } else if (node instanceof NodeIf i) {
            collectLocals(i.getThenBlock());
            if (i.getElseIfClauses() != null) {
                for (ElseIfClause c : i.getElseIfClauses()) collectLocals(c.getBlock());
            }
            collectLocals(i.getElseBlock());
        } else if (node instanceof NodeChoose c) {
            if (c.getCases() != null) {
                for (NodeChooseCase cc : c.getCases()) collectLocals(cc.getBlock());
            }
            if (c.getDefaultCase() != null) collectLocals(c.getDefaultCase().getBlock());
        } else if (node instanceof NodeWhile w) {
            collectLocals(w.getBlock());
        } else if (node instanceof NodeDoWhile dw) {
            collectLocals(dw.getBlock());
        } else if (node instanceof NodeFor f) {
            collectLocals(f.getInit());
            collectLocals(f.getBlock());
        }
    }

    private String arrayType(NodeArrayDeclaration n) {
        int dims = n.getDimensions() != null ? Math.max(n.getDimensions().size(), 1) : 1;
        return n.getType() + "[]".repeat(dims);
    }

    @Override
    public String visitBlock(NodeBlock n) {
        if (n == null) return null;

        scopes.push(new HashMap<>());
        if (n.getInstructions() != null) {
            for (ASTNode inst : n.getInstructions()) {
                if (inst != null) inst.accept(this);
            }
        }
        scopes.pop();
        return null;
    }

    @Override
    public String visitVariableDeclaration(NodeVariableDeclaration n) {
        if (n == null) return null;

        Slot slot = declSlots.get(n);
        String type = n.getType();
        ASTNode init = n.getInitializer();

        if (isStruct(type)) {
            // Valor-struct: objeto propio en heap, la variable guarda el puntero.
            String obj = allocStruct(type, new HashSet<>());
            storeValue(new Address(FP, slot.offset(), type), obj);

            if (init instanceof NodeArrayLiteral lit) {
                fillStruct(obj, type, lit.getElements());
            } else if (init != null) {
                String src = genExpr(init, type);
                copyStruct(obj, src, type);
            }
        } else if (init != null) {
            String v = genExpr(init, type);
            v = convert(v, typeOf(init), type);
            storeValue(new Address(FP, slot.offset(), type), v);
        }

        scopes.peek().put(n.getName(), slot);
        return null;
    }

    @Override
    public String visitArrayDeclaration(NodeArrayDeclaration n) {
        if (n == null) return null;

        Slot slot = declSlots.get(n);
        String arrType = arrayType(n);
        ASTNode init = n.getInitializer();
        List<ASTNode> dims = n.getDimensions() != null ? n.getDimensions() : Collections.emptyList();

        String arr;
        if (init != null && !(init instanceof NodeArrayLiteral)) {
            // Inicializador que ya es un arreglo (variable, llamada): se comparte el puntero.
            arr = genExpr(init, arrType);
        } else if (dims.isEmpty()) {
            arr = init != null ? genExpr(init, arrType) : allocArray(n.getType(), constIntTemp(0), 0);
        } else {
            arr = allocArrayByDims(n.getType(), dims, 0);
            if (init instanceof NodeArrayLiteral lit) {
                String elemType = arrType.substring(0, arrType.length() - 2);
                Integer limit = constInt(dims.get(0));
                List<ASTNode> els = lit.getElements();
                for (int i = 0; i < els.size(); i++) {
                    if (limit != null && i >= limit) break;
                    ASTNode e = els.get(i);
                    String v = genExpr(e, elemType);
                    v = convert(v, typeOf(e), elemType);
                    storeValue(elementAddress(arr, elemType, null, i), v);
                }
            }
        }

        storeValue(new Address(FP, slot.offset(), arrType), arr);
        scopes.peek().put(n.getName(), slot);
        return null;
    }

    @Override
    public String visitAssignment(NodeAssignment n) {
        if (n == null) return null;

        Address target = resolveAddress(n.getLvalue());
        String type = target.type();
        String op = n.getOperator();

        if ("++".equals(op) || "--".equals(op)) {
            String cur = loadValue(target);
            String one = ctx.newTemp();
            String res = ctx.newTemp();
            boolean inc = "++".equals(op);
            if (isDouble(type)) {
                ctx.emit(new ConstDouble(one, 1.0));
                ctx.emit(inc ? new AddDouble(res, cur, one) : new SubDouble(res, cur, one));
            } else {
                ctx.emit(new ConstInt(one, 1));
                ctx.emit(inc ? new AddInt(res, cur, one) : new SubInt(res, cur, one));
            }
            storeValue(target, res);
            return null;
        }

        ASTNode rhs = n.getExpression();
        if (rhs == null) return null;

        if (isStruct(type)) {
            // Asignación de struct: copia campo a campo hacia el objeto de la variable.
            String dst = loadValue(target);
            String src = genExpr(rhs, type);
            copyStruct(dst, src, type);
        } else {
            String v = genExpr(rhs, type);
            v = convert(v, typeOf(rhs), type);
            storeValue(target, v);
        }
        return null;
    }

    @Override
    public String visitRead(NodeRead n) {
        String t = ctx.newTemp();
        String hint = expectedType;

        if (T_DOUBLE.equals(hint)) {
            ctx.emit(new ReadDouble(t));
            readTypes.put(n, T_DOUBLE);
        } else if (hint == null || T_INT.equals(hint) || T_CHAR.equals(hint) || T_BOOL.equals(hint)) {
            ctx.emit(new ReadInt(t));
            readTypes.put(n, T_INT);
        } else {
            throw new UnsupportedOperationException(
                    "leer() solo puede asignarse a entero o flotante: no existe instrucción para leer cadenas.");
        }
        return t;
    }

    @Override
    public String visitPrint(NodePrint n) {
        if (n == null || n.getExpressions() == null) return null;

        //* Los elementos de imprimir(a + b ...) salen en la misma línea; un solo salto al final.
        for (ASTNode expr : n.getExpressions()) {
            if (expr == null) continue;
            String v = genExpr(expr, null);
            printValue(v, typeOf(expr));
        }
        ctx.emit(new PrintNewline());
        return null;
    }

    private void printValue(String value, String type) {
        if (T_CHAR.equals(type)) {
            //* emitPrint imprimiría el caracter como número: se arma una cadena de 1 byte.
            String s = ctx.newTemp();
            ctx.emit(new Alloc(s, constIntTemp(8)));
            ctx.emit(new StoreByte(s, 0, value));
            ctx.emit(new StoreByte(s, 1, constIntTemp(0)));
            ctx.emit(new PrintString(s));
            return;
        }
        emitter.emitPrint(value, irType(type));
    }

    @Override
    public String visitIf(NodeIf n) {
        if (n == null) return null;

        String lEnd = ctx.newLabel();
        String lNext = ctx.newLabel();

        String cond = genExpr(n.getCondition(), null);
        ctx.emit(new BranchFalse(cond, lNext));
        if (n.getThenBlock() != null) n.getThenBlock().accept(this);
        ctx.emit(new Jump(lEnd));
        ctx.emit(new Label(lNext));

        if (n.getElseIfClauses() != null) {
            for (ElseIfClause clause : n.getElseIfClauses()) {
                String lClauseNext = ctx.newLabel();
                String c = genExpr(clause.getCondition(), null);
                ctx.emit(new BranchFalse(c, lClauseNext));
                if (clause.getBlock() != null) clause.getBlock().accept(this);
                ctx.emit(new Jump(lEnd));
                ctx.emit(new Label(lClauseNext));
            }
        }

        if (n.getElseBlock() != null) n.getElseBlock().accept(this);
        ctx.emit(new Label(lEnd));
        return null;
    }

    @Override
    public String visitChoose(NodeChoose n) {
        if (n == null) return null;

        String sv = genExpr(n.getExpression(), null);
        boolean isString = T_STRING.equals(typeOf(n.getExpression()));

        List<NodeChooseCase> cases = n.getCases() != null ? n.getCases() : Collections.emptyList();
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < cases.size(); i++) labels.add(ctx.newLabel());
        String lDefault = ctx.newLabel();
        String lEnd = ctx.newLabel();

        for (int i = 0; i < cases.size(); i++) {
            String cv = genExpr(cases.get(i).getValue(), null);
            String cmp = ctx.newTemp();
            if (isString) {
                emitter.emitStreq(cmp, sv, cv);
            } else {
                ctx.emit(new EqInt(cmp, sv, cv));
            }
            ctx.emit(new BranchTrue(cmp, labels.get(i)));
        }
        ctx.emit(new Jump(lDefault));

        breakLabels.push(lEnd);
        for (int i = 0; i < cases.size(); i++) {
            ctx.emit(new Label(labels.get(i)));
            NodeBlock b = cases.get(i).getBlock();
            if (b != null) b.accept(this);
            ctx.emit(new Jump(lEnd));
        }
        ctx.emit(new Label(lDefault));
        if (n.getDefaultCase() != null && n.getDefaultCase().getBlock() != null) {
            n.getDefaultCase().getBlock().accept(this);
        }
        breakLabels.pop();

        ctx.emit(new Label(lEnd));
        return null;
    }

    @Override
    public String visitChooseCase(NodeChooseCase n) {
        return null;
    }

    @Override
    public String visitWhile(NodeWhile n) {
        if (n == null) return null;

        String lStart = ctx.newLabel();
        String lEnd = ctx.newLabel();

        ctx.emit(new Label(lStart));
        String cond = genExpr(n.getCondition(), null);
        ctx.emit(new BranchFalse(cond, lEnd));

        breakLabels.push(lEnd);
        continueLabels.push(lStart);
        if (n.getBlock() != null) n.getBlock().accept(this);
        breakLabels.pop();
        continueLabels.pop();

        ctx.emit(new Jump(lStart));
        ctx.emit(new Label(lEnd));
        return null;
    }

    @Override
    public String visitDoWhile(NodeDoWhile n) {
        if (n == null) return null;

        String lBody = ctx.newLabel();
        String lCond = ctx.newLabel();
        String lEnd = ctx.newLabel();

        ctx.emit(new Label(lBody));
        breakLabels.push(lEnd);
        continueLabels.push(lCond);
        if (n.getBlock() != null) n.getBlock().accept(this);
        breakLabels.pop();
        continueLabels.pop();

        ctx.emit(new Label(lCond));
        String cond = genExpr(n.getCondition(), null);
        ctx.emit(new BranchTrue(cond, lBody));
        ctx.emit(new Label(lEnd));
        return null;
    }

    @Override
    public String visitFor(NodeFor n) {
        if (n == null) return null;

        scopes.push(new HashMap<>());
        if (n.getInit() != null) n.getInit().accept(this);

        String lStart = ctx.newLabel();
        String lUpdate = ctx.newLabel();
        String lEnd = ctx.newLabel();

        ctx.emit(new Label(lStart));
        if (n.getCondition() != null) {
            String cond = genExpr(n.getCondition(), null);
            ctx.emit(new BranchFalse(cond, lEnd));
        }

        breakLabels.push(lEnd);
        continueLabels.push(lUpdate);
        if (n.getBlock() != null) n.getBlock().accept(this);
        breakLabels.pop();
        continueLabels.pop();

        ctx.emit(new Label(lUpdate));
        if (n.getUpdate() != null) n.getUpdate().accept(this);
        ctx.emit(new Jump(lStart));
        ctx.emit(new Label(lEnd));

        scopes.pop();
        return null;
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
        if (n == null) return null;

        if (n.getExpression() == null) {
            emitter.emitEpilogue();
            ctx.emit(new ReturnVoid());
            return null;
        }

        String v = genExpr(n.getExpression(), currentReturnType);
        v = convert(v, typeOf(n.getExpression()), currentReturnType);
        emitter.emitEpilogue();
        ctx.emit(isDouble(currentReturnType) ? new ReturnDouble(v) : new ReturnInt(v));
        return null;
    }

    @Override
    public String visitBinaryOperation(NodeBinaryOperation n) {
        if (n == null) return null;
        String op = n.getOperator();

        if ("&&".equals(op) || "||".equals(op)) {
            return genShortCircuit(n, "&&".equals(op));
        }

        String l = genExpr(n.getLeft(), null);
        String r = genExpr(n.getRight(), null);
        String lt = typeOf(n.getLeft());
        String rt = typeOf(n.getRight());
        String res = ctx.newTemp();

        //* Concatenación: cadena + cadena, y cadena con entero/flotante/caracter/bool en cualquier orden.
        if ("+".equals(op) && (T_STRING.equals(lt) || T_STRING.equals(rt))) {
            emitter.emitConcat(res, toStringValue(l, lt), toStringValue(r, rt));
            return res;
        }

        if ("==".equals(op) || "!=".equals(op)) {
            boolean eq = "==".equals(op);
            if (T_STRING.equals(lt) && T_STRING.equals(rt)) {
                if (eq) {
                    emitter.emitStreq(res, l, r);
                } else {
                    String same = ctx.newTemp();
                    emitter.emitStreq(same, l, r);
                    emitter.emitLogicalNot(res, same);
                }
                return res;
            }
            boolean dbl = isDouble(lt) || isDouble(rt);
            if (dbl) {
                l = convert(l, lt, T_DOUBLE);
                r = convert(r, rt, T_DOUBLE);
                ctx.emit(eq ? new EqDouble(res, l, r) : new NeDouble(res, l, r));
            } else {
                ctx.emit(eq ? new EqInt(res, l, r) : new NeInt(res, l, r));
            }
            return res;
        }

        boolean dbl = isDouble(lt) || isDouble(rt);
        if (dbl) {
            l = convert(l, lt, T_DOUBLE);
            r = convert(r, rt, T_DOUBLE);
        }

        switch (op) {
            case "+" -> ctx.emit(dbl ? new AddDouble(res, l, r) : new AddInt(res, l, r));
            case "-" -> ctx.emit(dbl ? new SubDouble(res, l, r) : new SubInt(res, l, r));
            case "*" -> ctx.emit(dbl ? new MulDouble(res, l, r) : new MulInt(res, l, r));
            case "/" -> ctx.emit(dbl ? new DivDouble(res, l, r) : new DivInt(res, l, r));
            case "<" -> ctx.emit(dbl ? new LtDouble(res, l, r) : new LtInt(res, l, r));
            case ">" -> ctx.emit(dbl ? new GtDouble(res, l, r) : new GtInt(res, l, r));
            default -> throw new IllegalStateException("Operador binario no soportado: " + op);
        }
        return res;
    }

    /** Convierte un valor de Y a cadena para concatenarlo (las cadenas pasan sin cambio). */
    private String toStringValue(String value, String type) {
        if (T_STRING.equals(type)) return value;
        if (T_DOUBLE.equals(type)) {
            String out = ctx.newTemp();
            emitter.emitToStr(out, value, Type.DOUBLE);
            return out;
        }
        if (T_CHAR.equals(type)) {
            String out = ctx.newTemp();
            ctx.emit(new Alloc(out, constIntTemp(8)));
            ctx.emit(new StoreByte(out, 0, value));
            ctx.emit(new StoreByte(out, 1, constIntTemp(0)));
            return out;
        }
        String out = ctx.newTemp();
        emitter.emitToStr(out, value, Type.INT);
        return out;
    }

    /** && y || con cortocircuito real: el lado derecho solo se evalúa si hace falta. */
    private String genShortCircuit(NodeBinaryOperation n, boolean isAnd) {
        String res = ctx.newTemp();
        String lShort = ctx.newLabel();
        String lEnd = ctx.newLabel();

        String l = genExpr(n.getLeft(), null);
        ctx.emit(isAnd ? new BranchFalse(l, lShort) : new BranchTrue(l, lShort));

        String r = genExpr(n.getRight(), null);
        ctx.emit(new MoveInt(res, r));
        ctx.emit(new Jump(lEnd));

        ctx.emit(new Label(lShort));
        ctx.emit(new ConstInt(res, isAnd ? 0 : 1));
        ctx.emit(new Label(lEnd));
        return res;
    }

    @Override
    public String visitUnaryOperation(NodeUnaryOperation n) {
        if (n == null) return null;

        String v = genExpr(n.getOperand(), null);
        String res = ctx.newTemp();

        if ("!".equals(n.getOperator())) {
            emitter.emitLogicalNot(res, v);
        } else if ("-".equals(n.getOperator())) {
            ctx.emit(isDouble(typeOf(n.getOperand())) ? new NegDouble(res, v) : new NegInt(res, v));
        } else {
            throw new IllegalStateException("Operador unario no soportado: " + n.getOperator());
        }
        return res;
    }

    @Override
    public String visitFunctionCall(NodeFunctionCall n) {
        if (n == null) return null;

        List<ASTNode> args = n.getArguments() != null ? n.getArguments() : Collections.emptyList();
        FunctionSymbol fn = symbolTable.lookupFunction(n.getFunctionName(), args.size());
        if (fn == null) {
            throw new IllegalStateException("Función no declarada: " + n.getFunctionName() + "/" + args.size());
        }
        List<ParameterType> paramTypes = fn.getParameterTypes();

        /**
         * Primero se evalúan todos los argumentos; recién después se emiten los Param,
         * así una llamada anidada no intercala sus Param con los de esta.
         */
        List<String> values = new ArrayList<>();
        List<Boolean> doubles = new ArrayList<>();
        for (int i = 0; i < args.size(); i++) {
            ASTNode arg = args.get(i);
            String pt = null;
            if (paramTypes != null && i < paramTypes.size() && paramTypes.get(i) != null) {
                ParameterType p = paramTypes.get(i);
                pt = p.isArray() ? p.getType() + "[]" : p.getType();
            }
            String v = genExpr(arg, pt);
            String at = typeOf(arg);
            if (pt != null) v = convert(v, at, pt);
            values.add(v);
            doubles.add(isDouble(pt != null ? pt : at));
        }

        int nInt = 0;
        int nDouble = 0;
        for (int i = 0; i < values.size(); i++) {
            if (doubles.get(i)) {
                emitter.emitParam(values.get(i), Type.DOUBLE);
                nDouble++;
            } else {
                emitter.emitParam(values.get(i), Type.INT);
                nInt++;
            }
        }

        String label = functionLabel(n.getFunctionName(), args.size());
        String ret = fn.getReturnType();

        if (ret == null || T_VOID.equals(ret)) {
            emitter.emitCallVoid(label, nInt, nDouble);
            return null;
        }
        String res = ctx.newTemp();
        if (isDouble(ret)) {
            emitter.emitCallDouble(res, label, nInt, nDouble);
        } else {
            emitter.emitCallInt(res, label, nInt, nDouble);
        }
        return res;
    }

    @Override
    public String visitIntegerLiteral(NodeIntegerLiteral n) {
        return constIntTemp(n.getValue());
    }

    @Override
    public String visitFloatLiteral(NodeFloatLiteral n) {
        String t = ctx.newTemp();
        ctx.emit(new ConstDouble(t, n.getValue()));
        return t;
    }

    @Override
    public String visitCharLiteral(NodeCharLiteral n) {
        return constIntTemp(n.getValue());
    }

    @Override
    public String visitBooleanLiteral(NodeBooleanLiteral n) {
        String t = ctx.newTemp();
        ctx.emit(new ConstBool(t, n.isValue()));
        return t;
    }

    @Override
    public String visitStringLiteral(NodeStringLiteral n) {
        String value = unescape(n.getValue() != null ? n.getValue() : "");
        int len = value.length();

        String res = ctx.newTemp();
        ctx.emit(new Alloc(res, constIntTemp(StructLayout.alignUp(len + 1, 8))));

        Map<Integer, String> codes = new HashMap<>();
        for (int i = 0; i < len; i++) {
            int code = value.charAt(i) & 0xFF;
            String c = codes.computeIfAbsent(code, this::constIntTemp);
            ctx.emit(new StoreByte(res, i, c));
        }
        ctx.emit(new StoreByte(res, len, codes.computeIfAbsent(0, this::constIntTemp)));
        return res;
    }

    private String unescape(String s) {
        return s.replace("\\n", "\n")
                .replace("\\t", "\t")
                .replace("\\r", "\r")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
    }

    @Override
    public String visitArrayLiteral(NodeArrayLiteral n) {
        if (n == null) return null;

        String hint = expectedType;
        List<ASTNode> els = n.getElements() != null ? n.getElements() : Collections.emptyList();

        //* Literal usado como valor de un struct: {campo0, campo1, ...}
        if (hint != null && !hint.endsWith("[]") && isStruct(hint)) {
            String obj = allocStruct(hint, new HashSet<>());
            fillStruct(obj, hint, els);
            return obj;
        }

        String arrType = hint != null && hint.endsWith("[]") ? hint : typeOf(n);
        if (arrType == null || !arrType.endsWith("[]")) arrType = T_INT + "[]";
        String elemType = arrType.substring(0, arrType.length() - 2);

        String arr = allocArray(elemType, constIntTemp(els.size()), els.size());
        for (int i = 0; i < els.size(); i++) {
            ASTNode e = els.get(i);
            String v = genExpr(e, elemType);
            v = convert(v, typeOf(e), elemType);
            storeValue(elementAddress(arr, elemType, null, i), v);
        }
        return arr;
    }

    @Override
    public String visitIdentifier(NodeIdentifier n) {
        return loadValue(resolveAddress(n));
    }

    @Override
    public String visitFieldAccess(NodeFieldAccess n) {
        return loadValue(resolveAddress(n));
    }

    @Override
    public String visitIndexAccess(NodeIndexAccess n) {
        return loadValue(resolveAddress(n));
    }

    @Override
    public String visitLvalue(NodeLvalue n) {
        throw new IllegalStateException("NodeLvalue no se genera en el AST de Y.");
    }

    private Address resolveAddress(ASTNode lvalue) {
        if (lvalue instanceof NodeIdentifier id) {
            Slot s = lookupSlot(id.getId());
            if (s == null) {
                throw new IllegalStateException("Variable no declarada: " + id.getId());
            }
            return new Address(FP, s.offset(), s.type());
        }

        if (lvalue instanceof NodeFieldAccess fa) {
            String structName = typeOf(fa.getCurrentNode());
            String base = genExpr(fa.getCurrentNode(), null);
            return new Address(base,
                    structLayout.offsetOf(structName, fa.getFieldName()),
                    structLayout.fieldType(structName, fa.getFieldName()));
        }

        if (lvalue instanceof NodeIndexAccess ia) {
            String arrType = typeOf(ia.getCurrentNode());
            String elemType = arrType.substring(0, arrType.length() - 2);
            String base = genExpr(ia.getCurrentNode(), null);
            Integer ci = constInt(ia.getIndexExpression());
            String idx = ci == null ? genExpr(ia.getIndexExpression(), T_INT) : null;
            return elementAddress(base, elemType, idx, ci);
        }

        throw new IllegalStateException("Lvalue no soportado: " + lvalue);
    }

    private Slot lookupSlot(String name) {
        for (Map<String, Slot> scope : scopes) {
            Slot s = scope.get(name);
            if (s != null) return s;
        }
        return null;
    }

    /** Dirección del elemento: base + cabecera + idx*tamaño. Con índice constante queda solo el offset. */
    private Address elementAddress(String base, String elemType, String idxTemp, Integer constIdx) {
        int hdr = arrayHeader(elemType);
        int es = elementSize(elemType);

        if (constIdx != null) {
            return new Address(base, hdr + constIdx * es, elemType);
        }
        String scaled = ctx.newTemp();
        ctx.emit(new MulInt(scaled, idxTemp, constIntTemp(es)));
        String addr = ctx.newTemp();
        ctx.emit(new AddInt(addr, base, scaled));
        return new Address(addr, hdr, elemType);
    }

    private String loadValue(Address a) {
        String t = ctx.newTemp();
        ctx.emit(isDouble(a.type())
                ? new LoadDouble(t, a.base(), a.offset())
                : new LoadInt(t, a.base(), a.offset()));
        return t;
    }

    private void storeValue(Address a, String value) {
        ctx.emit(isDouble(a.type())
                ? new StoreDouble(a.base(), a.offset(), value)
                : new StoreInt(a.base(), a.offset(), value));
    }

    private int arrayHeader(String elemType) {
        return isDouble(elemType) ? 8 : 4;
    }

    private int elementSize(String elemType) {
        return isDouble(elemType) ? 8 : 4;
    }

    /** Reserva un arreglo de 1 dimensión y guarda su longitud en +0. */
    private String allocArray(String elemType, String countTemp, Integer constCount) {
        int hdr = arrayHeader(elemType);
        int es = elementSize(elemType);

        String size;
        if (constCount != null) {
            size = constIntTemp(StructLayout.alignUp(hdr + constCount * es, 8));
        } else {
            // size = ((hdr + count*es + 7) / 8) * 8
            String bytes = ctx.newTemp();
            ctx.emit(new MulInt(bytes, countTemp, constIntTemp(es)));
            String padded = ctx.newTemp();
            ctx.emit(new AddInt(padded, bytes, constIntTemp(hdr + 7)));
            String eight = constIntTemp(8);
            String blocks = ctx.newTemp();
            ctx.emit(new DivInt(blocks, padded, eight));
            size = ctx.newTemp();
            ctx.emit(new MulInt(size, blocks, eight));
        }

        String res = ctx.newTemp();
        ctx.emit(new Alloc(res, size));
        ctx.emit(new StoreInt(res, C3DEmitter.ARRAY_LENGTH_OFFSET, countTemp));
        return res;
    }

    /** Arreglo declarado con dimensiones: multidimensional = arreglo de punteros a filas. */
    private String allocArrayByDims(String baseType, List<ASTNode> dims, int level) {
        boolean last = level == dims.size() - 1;
        String elemType = last ? baseType : baseType + "[]".repeat(dims.size() - level - 1);

        ASTNode d = dims.get(level);
        Integer c = constInt(d);
        String count = c != null ? constIntTemp(c) : genExpr(d, T_INT);
        String arr = allocArray(elemType, count, c);

        if (!last) {
            emitCountedLoop(count, i -> {
                String row = allocArrayByDims(baseType, dims, level + 1);
                storeValue(elementAddress(arr, elemType, i, null), row);
            });
        } else if (isStruct(baseType)) {
            emitCountedLoop(count, i -> {
                String obj = allocStruct(baseType, new HashSet<>());
                storeValue(elementAddress(arr, elemType, i, null), obj);
            });
        }
        return arr;
    }

    private void emitCountedLoop(String countTemp, Consumer<String> body) {
        String i = ctx.newTemp();
        ctx.emit(new ConstInt(i, 0));
        String one = constIntTemp(1);
        String lStart = ctx.newLabel();
        String lEnd = ctx.newLabel();

        ctx.emit(new Label(lStart));
        String cond = ctx.newTemp();
        ctx.emit(new LtInt(cond, i, countTemp));
        ctx.emit(new BranchFalse(cond, lEnd));

        body.accept(i);

        ctx.emit(new AddInt(i, i, one));
        ctx.emit(new Jump(lStart));
        ctx.emit(new Label(lEnd));
    }

    // ============================================================
    // STRUCTS
    // ============================================================

    /** Reserva un struct; structs y arreglos anidados se reservan también (semántica de valor). */
    private String allocStruct(String name, Set<String> inProgress) {
        String res = ctx.newTemp();
        ctx.emit(new Alloc(res, constIntTemp(structLayout.totalSize(name))));

        inProgress.add(name);
        for (StructLayout.FieldInfo f : structLayout.fieldsOf(name)) {
            if (f.isArray()) {
                if (f.arraySize() > 0) {
                    String count = constIntTemp(f.arraySize());
                    String arr = allocArray(f.type(), count, f.arraySize());
                    if (isStruct(f.type()) && !inProgress.contains(f.type())) {
                        emitCountedLoop(count, i -> {
                            String obj = allocStruct(f.type(), inProgress);
                            storeValue(elementAddress(arr, f.type(), i, null), obj);
                        });
                    }
                    storeValue(new Address(res, f.offset(), f.yType()), arr);
                }
            } else if (isStruct(f.type()) && !inProgress.contains(f.type())) {
                String nested = allocStruct(f.type(), inProgress);
                storeValue(new Address(res, f.offset(), f.type()), nested);
            }
        }
        inProgress.remove(name);
        return res;
    }

    /** Copia campo a campo; los structs anidados se copian en profundidad, los arreglos se comparten. */
    private void copyStruct(String dst, String src, String name) {
        copyStruct(dst, src, name, new HashSet<>());
    }

    private void copyStruct(String dst, String src, String name, Set<String> inProgress) {
        inProgress.add(name);
        for (StructLayout.FieldInfo f : structLayout.fieldsOf(name)) {
            Address from = new Address(src, f.offset(), f.yType());
            Address to = new Address(dst, f.offset(), f.yType());

            if (!f.isArray() && isStruct(f.type()) && !inProgress.contains(f.type())) {
                copyStruct(loadValue(to), loadValue(from), f.type(), inProgress);
            } else {
                storeValue(to, loadValue(from));
            }
        }
        inProgress.remove(name);
    }

    /** Inicializa un struct con un literal posicional {v0, v1, ...}. */
    private void fillStruct(String ptr, String name, List<ASTNode> elems) {
        List<StructLayout.FieldInfo> fields = new ArrayList<>(structLayout.fieldsOf(name));

        for (int i = 0; i < fields.size() && i < elems.size(); i++) {
            StructLayout.FieldInfo f = fields.get(i);
            ASTNode e = elems.get(i);
            Address slot = new Address(ptr, f.offset(), f.yType());

            if (!f.isArray() && isStruct(f.type())) {
                String nested = loadValue(slot);
                if (e instanceof NodeArrayLiteral lit) {
                    fillStruct(nested, f.type(), lit.getElements());
                } else {
                    copyStruct(nested, genExpr(e, f.type()), f.type());
                }
            } else {
                String v = genExpr(e, f.yType());
                v = convert(v, typeOf(e), f.yType());
                storeValue(slot, v);
            }
        }
    }

    /** Evalúa un nodo pasando el tipo esperado por el contexto (solo lo usan leer() y los literales). */
    private String genExpr(ASTNode node, String expected) {
        String saved = expectedType;
        expectedType = expected;
        try {
            return node.accept(this);
        } finally {
            expectedType = saved;
        }
    }

    /** Promoción entero -> flotante; en cualquier otro caso devuelve el mismo valor. */
    private String convert(String place, String from, String to) {
        if (T_DOUBLE.equals(to) && T_INT.equals(from)) {
            String t = ctx.newTemp();
            ctx.emit(new IntToDouble(t, place));
            return t;
        }
        return place;
    }

    private String constIntTemp(int value) {
        String t = ctx.newTemp();
        ctx.emit(new ConstInt(t, value));
        return t;
    }

    /** Evalúa expresiones enteras constantes simples (literales y + - * / unario -); null si no lo son. */
    private Integer constInt(ASTNode node) {
        if (node instanceof NodeIntegerLiteral i) return i.getValue();

        if (node instanceof NodeUnaryOperation u && "-".equals(u.getOperator())) {
            Integer v = constInt(u.getOperand());
            return v == null ? null : -v;
        }

        if (node instanceof NodeBinaryOperation b) {
            Integer l = constInt(b.getLeft());
            Integer r = constInt(b.getRight());
            if (l == null || r == null) return null;
            return switch (b.getOperator()) {
                case "+" -> l + r;
                case "-" -> l - r;
                case "*" -> l * r;
                case "/" -> r == 0 ? null : l / r;
                default -> null;
            };
        }
        return null;
    }

    private boolean isDouble(String type) {
        return T_DOUBLE.equals(type);
    }

    private boolean isStruct(String type) {
        return type != null && !type.endsWith("[]")
                && (typeTable.isUserDefined(type) || structLayout.hasStruct(type));
    }

    private Type irType(String type) {
        if (type == null) return Type.INT;
        if (type.endsWith("[]")) return Type.OBJECT;
        return switch (type) {
            case T_INT -> Type.INT;
            case T_DOUBLE -> Type.DOUBLE;
            case T_CHAR -> Type.CHAR;
            case T_STRING -> Type.STRING;
            case T_BOOL -> Type.BOOL;
            case T_VOID -> Type.VOID;
            default -> Type.OBJECT;
        };
    }

    /** Tipo estático de una expresión (misma lógica que el type checker). */
    private String typeOf(ASTNode node) {
        if (node == null) return null;

        if (node instanceof NodeIntegerLiteral) return T_INT;
        if (node instanceof NodeFloatLiteral) return T_DOUBLE;
        if (node instanceof NodeCharLiteral) return T_CHAR;
        if (node instanceof NodeStringLiteral) return T_STRING;
        if (node instanceof NodeBooleanLiteral) return T_BOOL;

        if (node instanceof NodeRead) return readTypes.getOrDefault(node, T_INT);

        if (node instanceof NodeIdentifier id) {
            Slot s = lookupSlot(id.getId());
            return s != null ? s.type() : null;
        }

        if (node instanceof NodeFieldAccess fa) {
            String base = typeOf(fa.getCurrentNode());
            if (base == null || !structLayout.hasStruct(base)) return null;
            return structLayout.fieldType(base, fa.getFieldName());
        }

        if (node instanceof NodeIndexAccess ia) {
            String base = typeOf(ia.getCurrentNode());
            if (base == null || !base.endsWith("[]")) return null;
            return base.substring(0, base.length() - 2);
        }

        if (node instanceof NodeFunctionCall fc) {
            int arity = fc.getArguments() != null ? fc.getArguments().size() : 0;
            FunctionSymbol fn = symbolTable.lookupFunction(fc.getFunctionName(), arity);
            return fn != null ? fn.getReturnType() : null;
        }

        if (node instanceof NodeBinaryOperation b) {
            switch (b.getOperator()) {
                case "+", "-", "*", "/" -> {
                    String lt = typeOf(b.getLeft());
                    String rt = typeOf(b.getRight());
                    if ("+".equals(b.getOperator()) && (T_STRING.equals(lt) || T_STRING.equals(rt))) return T_STRING;
                    return isDouble(lt) || isDouble(rt) ? T_DOUBLE : T_INT;
                }
                default -> {
                    return T_BOOL;
                }
            }
        }

        if (node instanceof NodeUnaryOperation u) {
            return "!".equals(u.getOperator()) ? T_BOOL : typeOf(u.getOperand());
        }

        if (node instanceof NodeArrayLiteral lit) {
            if (lit.getElements() == null || lit.getElements().isEmpty()) return "empty_array";
            String first = typeOf(lit.getElements().get(0));
            return first != null ? first + "[]" : null;
        }

        return null;
    }
}