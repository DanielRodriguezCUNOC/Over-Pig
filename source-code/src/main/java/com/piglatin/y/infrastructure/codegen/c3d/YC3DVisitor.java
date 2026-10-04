package com.piglatin.y.infrastructure.codegen.c3d;

import com.piglatin.common.infrastructure.codegen.C3DContext;
import com.piglatin.y.domain.ast.expressions.*;
import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.principal.NodeProgram;
import com.piglatin.y.domain.ast.statements.*;
import com.piglatin.y.domain.ast.visitor.Visitor;
import com.piglatin.y.domain.symboltable.*;
import com.piglatin.y.domain.types.TypeTable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class YC3DVisitor implements Visitor<String> {

    private final C3DContext ctx;
    private final StructLayout structLayout;
    private final SymbolTable symbolTable;
    private final TypeTable typeTable;

    // nombre -> tipo (variables locales/parámetros)
    private final Map<String, String> locals = new HashMap<>();
    // nombre -> temp/place
    private final Map<String, String> localPlaces = new HashMap<>();
    // nombre -> tamaño (para structs)
    private final Map<String, Integer> localSizes = new HashMap<>();

    // Parámetros de la función actual: nombre -> Stack[(int)(FB - K)]
    private Map<String, String> paramPlaces = new HashMap<>();
    private int currentArity;

    private String currentReturnType;

    private final Deque<String> breakLabels = new ArrayDeque<>();
    private final Deque<String> continueLabels = new ArrayDeque<>();

    public YC3DVisitor(C3DContext ctx,
                       StructLayout structLayout,
                       SymbolTable symbolTable,
                       TypeTable typeTable) {
        this.ctx = ctx;
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

        // Registrar structs
        if (n.getStructures() != null) {
            for (NodeStructureDefinition s : n.getStructures()) {
                if (s != null) structLayout.registerStruct(s);
            }
        }

        // Emitir funciones
        if (n.getFunctions() != null) {
            for (NodeFunctionDefinition f : n.getFunctions()) {
                if (f != null) f.accept(this);
            }
        }
        return null;
    }

    @Override
    public String visitStructureDefinition(NodeStructureDefinition n) {
        if (n == null) return null;
        structLayout.registerStruct(n);
        return null;
    }

    @Override
    public String visitFieldDeclaration(NodeFieldDeclaration n) {
        return null;
    }

    // ============================================================
    // FUNCIÓN
    // ============================================================

    @Override
    public String visitFunctionDefinition(NodeFunctionDefinition n) {
        if (n == null) return null;

        int nParams = n.getParameters() != null ? n.getParameters().size() : 0;
        currentArity = nParams;
        currentReturnType = n.getReturnType();

        // Reset estado de locales
        locals.clear();
        localPlaces.clear();
        localSizes.clear();
        paramPlaces = new HashMap<>();

        // Mapear params a Stack[(int)(FB - 2 - N + i)]
        for (int i = 0; i < nParams; i++) {
            NodeParameter p = n.getParameters().get(i);
            String pType = p.getType();
            locals.put(p.getName(), pType);
            int k = 2 + currentArity - i;
            paramPlaces.put(p.getName(), "Stack[(int)(FB - " + k + ")]");
        }

        String fname = n.getName() + "_" + nParams;
        ctx.emitLabel(fname);

        if (n.getBlock() != null) n.getBlock().accept(this);

        ctx.emit("RET", null, null, null);

        // Limpiar estado
        locals.clear();
        localPlaces.clear();
        localSizes.clear();
        paramPlaces = new HashMap<>();
        currentArity = 0;
        currentReturnType = null;
        return null;
    }

    @Override
    public String visitParameter(NodeParameter n) {
        return null;
    }

    @Override
    public String visitBlock(NodeBlock n) {
        if (n == null) return null;
        if (n.getInstructions() != null) {
            for (ASTNode inst : n.getInstructions()) {
                if (inst != null) inst.accept(this);
            }
        }
        return null;
    }

    // ============================================================
    // DECLARACIONES
    // ============================================================

    @Override
    public String visitVariableDeclaration(NodeVariableDeclaration n) {
        if (n == null) return null;

        String type = n.getType();
        boolean isStruct = typeTable.isUserDefined(type);
        locals.put(n.getName(), type);

        if (isStruct) {
            // Struct: reservar en Heap, tag, guardar dirección base en un temp
            int size = structLayout.hasStruct(type) ? structLayout.totalSize(type) : 1;
            String basePtr = ctx.newTemp();
            ctx.emit("=", "H", null, basePtr);
            ctx.emit("+", "H", String.valueOf(size), "H");
            int tag = structLayout.hasStruct(type) ? structLayout.tagOf(type) : 0;
            ctx.emit("=", String.valueOf(tag), null, "Heap[(int)(" + basePtr + ")]");
            localPlaces.put(n.getName(), basePtr);
            localSizes.put(n.getName(), size);

            // Inicializar campos desde el initializer si es un struct literal (aplanado)
            if (n.getInitializer() != null) {
                List<ASTNode> values = flattenStructValues(n.getInitializer());
                for (int i = 0; i < values.size() && i < size - 1; i++) {
                    String v = values.get(i).accept(this);
                    ctx.emit("=", v, null, "Heap[(int)(" + basePtr + "+" + (i + 1) + ")]");
                }
            }
        } else {
            // Primitivo: temp normal
            String t = ctx.newTemp();
            localPlaces.put(n.getName(), t);
            localSizes.put(n.getName(), 1);
            if (n.getInitializer() != null) {
                String v = n.getInitializer().accept(this);
                ctx.emit("=", v, null, t);
            }
        }
        return null;
    }

    private List<ASTNode> flattenStructValues(ASTNode init) {
        List<ASTNode> out = new ArrayList<>();
        if (init instanceof NodeArrayLiteral lit) {
            if (lit.getElements() != null) {
                for (ASTNode e : lit.getElements()) out.add(e);
            }
        } else if (init != null) {
            out.add(init);
        }
        return out;
    }

    @Override
    public String visitArrayDeclaration(NodeArrayDeclaration n) {
        if (n == null) return null;

        // Tamaño total = producto de dimensiones constantes
        int total = 1;
        if (n.getDimensions() != null) {
            for (ASTNode d : n.getDimensions()) {
                Integer v = evalConstInt(d);
                total *= (v != null && v > 0) ? v : 1;
            }
        }

        String basePtr = ctx.newTemp();
        ctx.emit("=", "H", null, basePtr);
        ctx.emit("+", "H", String.valueOf(total), "H");

        locals.put(n.getName(), n.getType() + "[]");
        localPlaces.put(n.getName(), basePtr);
        localSizes.put(n.getName(), total);

        if (n.getInitializer() != null) {
            List<ASTNode> flat = new ArrayList<>();
            flattenArrayInit(n.getInitializer(), flat);
            for (int i = 0; i < flat.size() && i < total; i++) {
                String v = flat.get(i).accept(this);
                ctx.emit("=", v, null, "Heap[(int)(" + basePtr + "+" + i + ")]");
            }
        }
        return null;
    }

    private void flattenArrayInit(ASTNode node, List<ASTNode> out) {
        if (node instanceof NodeArrayLiteral lit) {
            if (lit.getElements() != null) {
                for (ASTNode e : lit.getElements()) flattenArrayInit(e, out);
            }
        } else if (node != null) {
            out.add(node);
        }
    }

    private Integer evalConstInt(ASTNode node) {
        if (node instanceof NodeIntegerLiteral i) return i.getValue();
        return null;
    }

    // ============================================================
    // ASIGNACIÓN
    // ============================================================

    @Override
    public String visitAssignment(NodeAssignment n) {
        if (n == null) return null;
        String op = n.getOperator();
        String addr = resolvePlace(n.getLvalue());

        if ("++".equals(op) || "--".equals(op)) {
            String cop = "++".equals(op) ? "+" : "-";
            ctx.emit(cop, addr, "1", addr);
            return null;
        }

        // Struct copy slot por slot
        String lvType = typeOfPlace(n.getLvalue());
        if (lvType != null && typeTable.isUserDefined(lvType)) {
            String src = n.getExpression() != null ? n.getExpression().accept(this) : "0";
            int size = structLayout.hasStruct(lvType) ? structLayout.totalSize(lvType) : 1;
            for (int i = 0; i < size; i++) {
                String s = "Heap[(int)(" + src + "+" + i + ")]";
                String d = "Heap[(int)(" + addr + "+" + i + ")]";
                ctx.emit("=", s, null, d);
            }
            return null;
        }

        String rhs = n.getExpression() != null ? n.getExpression().accept(this) : "0";
        ctx.emit("=", rhs, null, addr);
        return null;
    }

    // ============================================================
    // I/O
    // ============================================================

    @Override
    public String visitRead(NodeRead n) {
        String t = ctx.newTemp();
        ctx.emit("READ", null, null, t);
        return t;
    }

    @Override
    public String visitPrint(NodePrint n) {
        if (n == null) return null;
        if (n.getExpressions() == null) return null;
        for (ASTNode expr : n.getExpressions()) {
            if (expr == null) continue;
            String type = staticTypeOf(expr);
            String val = expr.accept(this);
            if ("cadena".equals(type) || "String".equals(type)) {
                ctx.emit("PRINTSLN", val, null, null);
            } else {
                ctx.emit("PRINTLN", val, type != null ? type : "flotante", null);
            }
        }
        return null;
    }

    // ============================================================
    // CONTROL DE FLUJO
    // ============================================================

    @Override
    public String visitIf(NodeIf n) {
        if (n == null) return null;
        String lEnd = ctx.newLabel();
        String lNext = ctx.newLabel();

        // then
        String cond = n.getCondition() != null ? n.getCondition().accept(this) : "1";
        String lThen = ctx.newLabel();
        ctx.emitIfTrue(cond, lThen);
        ctx.emitGoto(lNext);

        ctx.emitLabel(lThen);
        if (n.getThenBlock() != null) n.getThenBlock().accept(this);
        ctx.emitGoto(lEnd);

        // else-if
        ctx.emitLabel(lNext);
        if (n.getElseIfClauses() != null) {
            for (ElseIfClause clause : n.getElseIfClauses()) {
                String lElseIfNext = ctx.newLabel();
                String lElseIfThen = ctx.newLabel();
                String c = clause.getCondition() != null
                        ? clause.getCondition().accept(this) : "0";
                ctx.emitIfTrue(c, lElseIfThen);
                ctx.emitGoto(lElseIfNext);

                ctx.emitLabel(lElseIfThen);
                if (clause.getBlock() != null) clause.getBlock().accept(this);
                ctx.emitGoto(lEnd);

                ctx.emitLabel(lElseIfNext);
            }
        }

        // else
        if (n.getElseBlock() != null) n.getElseBlock().accept(this);

        ctx.emitLabel(lEnd);
        return null;
    }

    @Override
    public String visitChoose(NodeChoose n) {
        if (n == null) return null;
        String switchVal = n.getExpression() != null ? n.getExpression().accept(this) : "0";

        List<NodeChooseCase> cases = n.getCases() != null ? n.getCases() : Collections.emptyList();
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < cases.size(); i++) labels.add(ctx.newLabel());
        String lDefault = ctx.newLabel();
        String lEnd = ctx.newLabel();

        for (int i = 0; i < cases.size(); i++) {
            NodeChooseCase c = cases.get(i);
            String caseVal = c.getValue() != null ? c.getValue().accept(this) : "0";
            String cmp = ctx.newTemp();
            ctx.emit("==", switchVal, caseVal, cmp);
            ctx.emitIfTrue(cmp, labels.get(i));
        }
        ctx.emitGoto(lDefault);

        breakLabels.push(lEnd);
        for (int i = 0; i < cases.size(); i++) {
            ctx.emitLabel(labels.get(i));
            NodeChooseCase c = cases.get(i);
            if (c.getBlock() != null) c.getBlock().accept(this);
            ctx.emitGoto(lEnd);
        }
        breakLabels.pop();

        ctx.emitLabel(lDefault);
        if (n.getDefaultCase() != null && n.getDefaultCase().getBlock() != null) {
            breakLabels.push(lEnd);
            n.getDefaultCase().getBlock().accept(this);
            breakLabels.pop();
        }

        ctx.emitLabel(lEnd);
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

        ctx.emitLabel(lStart);
        String cond = n.getCondition() != null ? n.getCondition().accept(this) : "1";
        ctx.emitIfFalse(cond, lEnd);

        breakLabels.push(lEnd);
        continueLabels.push(lStart);
        if (n.getBlock() != null) n.getBlock().accept(this);
        breakLabels.pop();
        continueLabels.pop();

        ctx.emitGoto(lStart);
        ctx.emitLabel(lEnd);
        return null;
    }

    @Override
    public String visitDoWhile(NodeDoWhile n) {
        if (n == null) return null;
        String lBody = ctx.newLabel();
        String lCond = ctx.newLabel();
        String lEnd = ctx.newLabel();

        ctx.emitLabel(lBody);
        breakLabels.push(lEnd);
        continueLabels.push(lCond);
        if (n.getBlock() != null) n.getBlock().accept(this);
        breakLabels.pop();
        continueLabels.pop();

        ctx.emitLabel(lCond);
        String cond = n.getCondition() != null ? n.getCondition().accept(this) : "0";
        ctx.emitIfTrue(cond, lBody);

        ctx.emitLabel(lEnd);
        return null;
    }

    @Override
    public String visitFor(NodeFor n) {
        if (n == null) return null;
        if (n.getInit() != null) n.getInit().accept(this);

        String lStart = ctx.newLabel();
        String lUpdate = ctx.newLabel();
        String lEnd = ctx.newLabel();

        ctx.emitLabel(lStart);
        if (n.getCondition() != null) {
            String cond = n.getCondition().accept(this);
            ctx.emitIfFalse(cond, lEnd);
        }

        breakLabels.push(lEnd);
        continueLabels.push(lUpdate);
        if (n.getBlock() != null) n.getBlock().accept(this);
        breakLabels.pop();
        continueLabels.pop();

        ctx.emitLabel(lUpdate);
        if (n.getUpdate() != null) n.getUpdate().accept(this);
        ctx.emitGoto(lStart);

        ctx.emitLabel(lEnd);
        return null;
    }

    @Override
    public String visitBreak(NodeBreak n) {
        if (!breakLabels.isEmpty()) ctx.emitGoto(breakLabels.peek());
        return null;
    }

    @Override
    public String visitContinue(NodeContinue n) {
        if (!continueLabels.isEmpty()) ctx.emitGoto(continueLabels.peek());
        return null;
    }

    @Override
    public String visitReturn(NodeReturn n) {
        if (n == null) return null;
        if (n.getExpression() != null) {
            String v = n.getExpression().accept(this);
            ctx.emit("RET", v, null, null);
        } else {
            ctx.emit("RET", null, null, null);
        }
        return null;
    }


    @Override
    public String visitBinaryOperation(NodeBinaryOperation n) {
        if (n == null) return null;
        String op = n.getOperator();

        if ("+".equals(op)) {
            String lt = staticTypeOf(n.getLeft());
            String rt = staticTypeOf(n.getRight());
            if ("cadena".equals(lt) || "cadena".equals(rt)
                    || "String".equals(lt) || "String".equals(rt)) {
                String l = n.getLeft() != null ? n.getLeft().accept(this) : "0";
                String r = n.getRight() != null ? n.getRight().accept(this) : "0";
                String lStr = ("cadena".equals(lt) || "String".equals(lt)) ? l : toStr(l, lt);
                String rStr = ("cadena".equals(rt) || "String".equals(rt)) ? r : toStr(r, rt);
                String t = ctx.newTemp();
                ctx.emit("CONCAT", lStr, rStr, t);
                return t;
            }
        }

        String l = n.getLeft() != null ? n.getLeft().accept(this) : "0";
        String r = n.getRight() != null ? n.getRight().accept(this) : "0";
        String t = ctx.newTemp();
        ctx.emit(op, l, r, t);
        return t;
    }

    @Override
    public String visitUnaryOperation(NodeUnaryOperation n) {
        if (n == null) return null;
        String v = n.getOperand() != null ? n.getOperand().accept(this) : "0";
        String t = ctx.newTemp();
        ctx.emit(n.getOperator(), v, null, t);
        return t;
    }

    @Override
    public String visitFunctionCall(NodeFunctionCall n) {
        if (n == null) return null;

        List<String> argPlaces = new ArrayList<>();
        if (n.getArguments() != null) {
            for (ASTNode arg : n.getArguments()) {
                argPlaces.add(arg != null ? arg.accept(this) : "0");
            }
        }

        int nArgs = argPlaces.size();
        String fname = n.getFunctionName() + "_" + nArgs;

        FunctionSymbol fn = symbolTable.lookupFunction(n.getFunctionName(), nArgs);
        String returnType = fn != null ? fn.getReturnType() : null;
        boolean isVoid = returnType == null || "void".equals(returnType);

        for (String a : argPlaces) {
            ctx.emit("PARAM", a, null, null);
        }
        String lret = ctx.newLabel();
        ctx.emit("CALL", fname, String.valueOf(nArgs), lret);
        ctx.emitLabel(lret);
        String retTemp = ctx.newTemp();
        ctx.emit("LOAD_RET", null, null, retTemp);

        if (isVoid) {
            return "0";
        }
        return retTemp;
    }

    @Override
    public String visitIntegerLiteral(NodeIntegerLiteral n) {
        return n == null ? "0" : String.valueOf(n.getValue());
    }

    @Override
    public String visitFloatLiteral(NodeFloatLiteral n) {
        return n == null ? "0" : String.valueOf(n.getValue());
    }

    @Override
    public String visitCharLiteral(NodeCharLiteral n) {
        if (n == null) return "0";
        char c = n.getValue();
        if (c == '\'' || c == '\\') return "'\\" + c + "'";
        return "'" + c + "'";
    }

    @Override
    public String visitBooleanLiteral(NodeBooleanLiteral n) {
        return n == null ? "0" : (n.isValue() ? "1" : "0");
    }

    @Override
    public String visitStringLiteral(NodeStringLiteral n) {
        if (n == null) return "0";
        String value = n.getValue() != null ? n.getValue() : "";

        // Desescapar secuencias
        value = value.replace("\\n", "\n")
                .replace("\\t", "\t")
                .replace("\\r", "\r")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");

        String start = ctx.newTemp();
        ctx.emit("=", "H", null, start);
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            String lit = (c == '\'' || c == '\\') ? "'\\" + c + "'" : "'" + c + "'";
            ctx.emit("=", lit, null, "Heap[(int)H]");
            ctx.emit("+", "H", "1", "H");
        }
        ctx.emit("=", "'\\0'", null, "Heap[(int)H]");
        ctx.emit("+", "H", "1", "H");
        return start;
    }

    @Override
    public String visitArrayLiteral(NodeArrayLiteral n) {
        if (n == null) return "0";
        List<ASTNode> elems = n.getElements() != null ? n.getElements() : Collections.emptyList();
        String basePtr = ctx.newTemp();
        ctx.emit("=", "H", null, basePtr);
        ctx.emit("+", "H", String.valueOf(elems.size()), "H");
        for (int i = 0; i < elems.size(); i++) {
            String v = elems.get(i).accept(this);
            ctx.emit("=", v, null, "Heap[(int)(" + basePtr + "+" + i + ")]");
        }
        return basePtr;
    }


    @Override
    public String visitIdentifier(NodeIdentifier n) {
        if (n == null) return "0";
        return resolveIdentifier(n.getId());
    }

    @Override
    public String visitLvalue(NodeLvalue n) {

        return "0";
    }

    @Override
    public String visitFieldAccess(NodeFieldAccess n) {
        if (n == null) return "0";
        String basePlace = n.getCurrentNode() != null ? n.getCurrentNode().accept(this) : "0";
        String baseType = staticTypeOf(n.getCurrentNode());
        if (baseType == null || !structLayout.hasStruct(baseType)) {
            return "0";
        }
        int off = structLayout.offsetOf(baseType, n.getFieldName());
        return "Heap[(int)(" + basePlace + "+" + off + ")]";
    }

    @Override
    public String visitIndexAccess(NodeIndexAccess n) {
        if (n == null) return "0";
        String basePlace = n.getCurrentNode() != null ? n.getCurrentNode().accept(this) : "0";
        String idx = n.getIndexExpression() != null ? n.getIndexExpression().accept(this) : "0";
        return "Heap[(int)(" + basePlace + "+" + idx + ")]";
    }


    private String resolveIdentifier(String name) {
        if (name == null) return "0";
        if (paramPlaces != null && paramPlaces.containsKey(name)) {
            return paramPlaces.get(name);
        }
        if (localPlaces.containsKey(name)) {
            return localPlaces.get(name);
        }
        return "0";
    }

    private String resolvePlace(ASTNode lvalue) {
        if (lvalue == null) return "0";
        if (lvalue instanceof NodeIdentifier id) return resolveIdentifier(id.getId());
        if (lvalue instanceof NodeFieldAccess fa) return visitFieldAccess(fa);
        if (lvalue instanceof NodeIndexAccess ia) return visitIndexAccess(ia);
        return lvalue.accept(this);
    }

    private String typeOfPlace(ASTNode lvalue) {
        if (lvalue == null) return null;
        if (lvalue instanceof NodeIdentifier id) return locals.get(id.getId());
        return staticTypeOf(lvalue);
    }

    private String toStr(String place, String type) {
        String t = ctx.newTemp();
        ctx.emit("TO_STR", place, type != null ? type : "flotante", t);
        return t;
    }

    private String staticTypeOf(ASTNode node) {
        if (node == null) return null;

        if (node instanceof NodeStringLiteral) return "cadena";
        if (node instanceof NodeIntegerLiteral) return "entero";
        if (node instanceof NodeFloatLiteral) return "flotante";
        if (node instanceof NodeCharLiteral) return "caracter";
        if (node instanceof NodeBooleanLiteral) return "bool";
        if (node instanceof NodeArrayLiteral) return "array";

        if (node instanceof NodeIdentifier id) {
            return locals.get(id.getId());
        }

        if (node instanceof NodeFieldAccess fa) {
            String baseType = staticTypeOf(fa.getCurrentNode());
            if (baseType == null || !structLayout.hasStruct(baseType)) return null;
            Symbol fieldSym = symbolTable.lookupField(baseType, fa.getFieldName());
            if (fieldSym instanceof VariableSymbol vs) return vs.getType();
            if (fieldSym instanceof ArraySymbol as) return as.getElementType() + "[]";
            return null;
        }

        if (node instanceof NodeIndexAccess ia) {
            String baseType = staticTypeOf(ia.getCurrentNode());
            if (baseType != null && baseType.endsWith("[]")) {
                return baseType.substring(0, baseType.length() - 2);
            }
            return null;
        }

        if (node instanceof NodeBinaryOperation be) {
            String op = be.getOperator();
            String lt = staticTypeOf(be.getLeft());
            String rt = staticTypeOf(be.getRight());
            if ("+".equals(op)) {
                if ("cadena".equals(lt) || "cadena".equals(rt)) return "cadena";
                if ("flotante".equals(lt) || "flotante".equals(rt)) return "flotante";
                return "entero";
            }
            if ("-".equals(op) || "*".equals(op) || "/".equals(op)) {
                if ("flotante".equals(lt) || "flotante".equals(rt)) return "flotante";
                return "entero";
            }
            if ("%".equals(op)) return "entero";
            return "bool";
        }

        if (node instanceof NodeUnaryOperation ue) {
            if ("!".equals(ue.getOperator())) return "bool";
            return staticTypeOf(ue.getOperand());
        }

        if (node instanceof NodeFunctionCall fc) {
            int arity = fc.getArguments() != null ? fc.getArguments().size() : 0;
            FunctionSymbol fn = symbolTable.lookupFunction(fc.getFunctionName(), arity);
            return fn != null ? fn.getReturnType() : null;
        }

        if (node instanceof NodeRead) return "flotante";

        return null;
    }
}