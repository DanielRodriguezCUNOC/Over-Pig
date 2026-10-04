package com.piglatin.piglatin.infrastructure.codegen.c3d;

import com.piglatin.common.infrastructure.codegen.C3DContext;
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
import com.piglatin.zetariano.domain.symboltable.MethodSymbol;
import com.piglatin.zetariano.domain.symboltable.SymbolTable;
import com.piglatin.zetariano.domain.types.TypeTable;
import com.piglatin.zetariano.infrastructure.codegen.c3d.ClassLayout;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PigLatinC3DVisitor implements Visitor<String> {

    private final C3DContext ctx;
    private final ClassLayout layout;                        // de Zetariano
    private final SymbolTable symbolTable;                   // de Zetariano
    private final TypeTable typeTable;                       // de Zetariano
    private final com.piglatin.y.domain.symboltable.SymbolTable ySymbolTable; // BRIDGE a Y

    // Variables locales/globales de PigLatin: nombre -> tipo
    private final Map<String, String> locals = new HashMap<>();
    // nombre -> temp/place
    private final Map<String, String> localPlaces = new HashMap<>();

    private final Deque<String> breakLabels = new ArrayDeque<>();
    private final Deque<String> continueLabels = new ArrayDeque<>();

    public PigLatinC3DVisitor(C3DContext ctx,
                              ClassLayout layout,
                              SymbolTable symbolTable,
                              TypeTable typeTable,
                              com.piglatin.y.domain.symboltable.SymbolTable ySymbolTable) {
        this.ctx = ctx;
        this.layout = layout;
        this.symbolTable = symbolTable;
        this.typeTable = typeTable;
        this.ySymbolTable = ySymbolTable;
    }

    @Override
    public String visitProgram(NodeProgram n) {
        if (n == null) return null;

        if (n.getGlobalDeclarations() != null) {
            for (ASTNode decl : n.getGlobalDeclarations()) {
                if (decl != null) decl.accept(this);
            }
        }

        if (n.getMainInstructions() != null) {
            for (ASTNode stmt : n.getMainInstructions()) {
                if (stmt != null) stmt.accept(this);
            }
        }
        return null;
    }

    @Override
    public String visitImport(NodeImport n) {

        return null;
    }

    @Override
    public String visitVariableDeclaration(NodeVariableDeclaration n) {
        if (n == null) return null;

        String type = n.getType();
        if (type == null && n.getInitializer() instanceof NodeNewInstance ni) {
            type = ni.getClassName();
        }
        if (type == null) type = "double";

        locals.put(n.getIdentifier(), type);
        if (n.getInitializer() instanceof NodeNewInstance) {
            String basePtr = n.getInitializer().accept(this);
            localPlaces.put(n.getIdentifier(), basePtr);
            return null;
        }

        String t = ctx.newTemp();
        localPlaces.put(n.getIdentifier(), t);
        if (n.getInitializer() != null) {
            String v = n.getInitializer().accept(this);
            ctx.emit("=", v, null, t);
        }
        return null;
    }

    @Override
    public String visitArrayDeclaration(NodeArrayDeclaration n) {
        if (n == null) return null;
        String basePtr;
        if (!n.getInitialValues().isEmpty()) {
            basePtr = ctx.newTemp();
            ctx.emit("=", "H", null, basePtr);
            ctx.emit("+", "H", String.valueOf(n.getInitialValues().size()), "H");
            for (int i = 0; i < n.getInitialValues().size(); i++) {
                String v = n.getInitialValues().get(i).accept(this);
                ctx.emit("=", v, null, "Heap[(int)(" + basePtr + "+" + i + ")]");
            }
        } else if (n.getSizeExpression() != null) {
            String sizeVal = n.getSizeExpression().accept(this);
            basePtr = ctx.newTemp();
            ctx.emit("=", "H", null, basePtr);
            ctx.emit("+", "H", sizeVal, "H");
        } else if (n.getSize() > 0) {
            basePtr = ctx.newTemp();
            ctx.emit("=", "H", null, basePtr);
            ctx.emit("+", "H", String.valueOf(n.getSize()), "H");
        } else {
            basePtr = ctx.newTemp();
            ctx.emit("=", "0", null, basePtr);
        }

        String elemType = n.getElementType() != null ? n.getElementType() : "double";
        locals.put(n.getIdentifier(), elemType + "[]");
        localPlaces.put(n.getIdentifier(), basePtr);
        return null;
    }

    @Override
    public String visitAssignment(NodeAssignment n) {
        if (n == null) return null;
        String addr = place(n.getLvalue());
        String rhs = n.getExpression() != null ? n.getExpression().accept(this) : "0";
        ctx.emit("=", rhs, null, addr);
        return null;
    }

    @Override
    public String visitRead(NodeRead n) {
        if (n == null) return null;
        String target = n.getTarget() != null ? place(n.getTarget()) : "t0";
        ctx.emit("READ", null, null, target);
        return null;
    }

    @Override
    public String visitPrint(NodePrint n) {
        if (n == null) return null;
        if (n.getPrintItems() == null) return null;
        for (ASTNode item : n.getPrintItems()) {
            if (item == null) continue;
            String type = staticTypeOf(item);
            String val = item.accept(this);
            if ("String".equals(type) || "cadena".equals(type)) {
                ctx.emit("PRINTSLN", val, null, null);
            } else {
                ctx.emit("PRINTLN", val, type != null ? type : "double", null);
            }
        }
        return null;
    }

    @Override
    public String visitIf(NodeIf n) {
        if (n == null) return null;
        String lEnd = ctx.newLabel();
        String lNext = ctx.newLabel();

        String cond = n.getCondition() != null ? n.getCondition().accept(this) : "1";
        String lThen = ctx.newLabel();
        ctx.emitIfTrue(cond, lThen);
        ctx.emitGoto(lNext);

        ctx.emitLabel(lThen);
        if (n.getThenBlock() != null) n.getThenBlock().accept(this);
        ctx.emitGoto(lEnd);

        ctx.emitLabel(lNext);
        if (n.getElseIfClauses() != null) {
            for (ElseIfClause elseIf : n.getElseIfClauses()) {
                String lElseIfNext = ctx.newLabel();
                String lElseIfThen = ctx.newLabel();
                String elseIfCond = elseIf.getCondition() != null
                        ? elseIf.getCondition().accept(this) : "0";
                ctx.emitIfTrue(elseIfCond, lElseIfThen);
                ctx.emitGoto(lElseIfNext);

                ctx.emitLabel(lElseIfThen);
                if (elseIf.getBlock() != null) elseIf.getBlock().accept(this);
                ctx.emitGoto(lEnd);

                ctx.emitLabel(lElseIfNext);
            }
        }

        if (n.getElseBlock() != null) n.getElseBlock().accept(this);

        ctx.emitLabel(lEnd);
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
        if (n.getInitialization() != null) n.getInitialization().accept(this);

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
    public String visitBlock(NodeBlock n) {
        if (n == null) return null;
        if (n.getInstructions() != null) {
            for (ASTNode inst : n.getInstructions()) {
                if (inst != null) inst.accept(this);
            }
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
            if ("String".equals(lt) || "String".equals(rt)
                    || "cadena".equals(lt) || "cadena".equals(rt)) {
                String l = n.getLeft() != null ? n.getLeft().accept(this) : "0";
                String r = n.getRight() != null ? n.getRight().accept(this) : "0";
                String lStr = ("String".equals(lt) || "cadena".equals(lt)) ? l : toStr(l, lt);
                String rStr = ("String".equals(rt) || "cadena".equals(rt)) ? r : toStr(r, rt);
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
    public String visitIncrementDecrement(NodeIncrementDecrement n) {
        if (n == null) return null;
        String addr = n.getOperand() instanceof NodeLvalue lv
                ? place(lv)
                : (n.getOperand() != null ? n.getOperand().accept(this) : "0");
        String op = "++".equals(n.getOperation()) ? "+" : "-";
        ctx.emit(op, addr, "1", addr);
        return addr;
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

        String targetClass = null;
        String thisPlace = null;
        if (n.getCurrentNode() != null) {
            thisPlace = n.getCurrentNode().accept(this);
            targetClass = staticTypeOf(n.getCurrentNode());
        }

        if (targetClass != null && layout != null && layout.hasClass(targetClass)) {
            int nArgs = 1 + argPlaces.size();
            String fname = targetClass + "_" + n.getFunctionName() + "_" + nArgs;

            MethodSymbol method = symbolTable.lookupMethod(targetClass, n.getFunctionName(), argPlaces.size());
            String returnType = method != null ? method.getReturnType() : null;

            ctx.emit("PARAM", thisPlace, null, null);
            for (String a : argPlaces) {
                ctx.emit("PARAM", a, null, null);
            }
            String lret = ctx.newLabel();
            ctx.emit("CALL", fname, String.valueOf(nArgs), lret);
            ctx.emitLabel(lret);
            String retTemp = ctx.newTemp();
            ctx.emit("LOAD_RET", null, null, retTemp);

            if (returnType != null && !"void".equals(returnType)) {
                return retTemp;
            }
            return "0";
        }

        int nArgs = argPlaces.size();
        String fname = n.getFunctionName() + "_" + nArgs;

        if (ySymbolTable != null) {
            com.piglatin.y.domain.symboltable.FunctionSymbol yFn =
                    ySymbolTable.lookupFunction(n.getFunctionName(), nArgs);
            if (yFn != null) {
                String returnType = yFn.getReturnType();
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
        }

        for (String a : argPlaces) {
            ctx.emit("PARAM", a, null, null);
        }
        String lret = ctx.newLabel();
        ctx.emit("CALL", fname, String.valueOf(nArgs), lret);
        ctx.emitLabel(lret);
        String retTemp = ctx.newTemp();
        ctx.emit("LOAD_RET", null, null, retTemp);
        return retTemp;
    }

    @Override
    public String visitNewInstance(NodeNewInstance n) {
        if (n == null) return null;
        String className = n.getClassName();

        int size = layout.hasClass(className) ? layout.totalSize(className) : 1;

        String basePtr = ctx.newTemp();
        ctx.emit("=", "H", null, basePtr);
        ctx.emit("+", "H", String.valueOf(size), "H");

        int tag = layout.hasClass(className) ? layout.tagOf(className) : 0;
        ctx.emit("=", String.valueOf(tag), null, "Heap[(int)(" + basePtr + ")]");

        List<String> argPlaces = new ArrayList<>();
        if (n.getArguments() != null) {
            for (ASTNode arg : n.getArguments()) {
                argPlaces.add(arg != null ? arg.accept(this) : "0");
            }
        }

        int nArgs = 1 + argPlaces.size();
        boolean hasCtor = symbolTable.lookupMethod(className, className, argPlaces.size()) != null;

        if (hasCtor) {
            ctx.emit("PARAM", basePtr, null, null);
            for (String a : argPlaces) {
                ctx.emit("PARAM", a, null, null);
            }
            String lret = ctx.newLabel();
            ctx.emit("CALL", className + "_init_" + nArgs, String.valueOf(nArgs), lret);
            ctx.emitLabel(lret);
            String discard = ctx.newTemp();
            ctx.emit("LOAD_RET", null, null, discard);
        }

        return basePtr;
    }


    @Override
    public String visitIntegerLiteral(NodeIntegerLiteral n) {
        return n == null ? "0" : String.valueOf(n.getValue());
    }

    @Override
    public String visitDecimalLiteral(NodeDecimalLiteral n) {
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
    public String visitStringLiteral(NodeStringLiteral n) {
        if (n == null) return "0";
        String value = n.getValue() != null ? n.getValue() : "";
        if (value.length() >= 2 && value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }
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
    public String visitBooleanLiteral(NodeBooleanLiteral n) {
        return n == null ? "0" : (n.isValue() ? "1" : "0");
    }

    @Override
    public String visitArrayLiteral(NodeArrayLiteral n) {
        if (n == null) return "0";
        List<ASTNode> values = n.getValues() != null ? n.getValues() : Collections.emptyList();
        String basePtr = ctx.newTemp();
        ctx.emit("=", "H", null, basePtr);
        ctx.emit("+", "H", String.valueOf(values.size()), "H");
        for (int i = 0; i < values.size(); i++) {
            String v = values.get(i).accept(this);
            ctx.emit("=", v, null, "Heap[(int)(" + basePtr + "+" + i + ")]");
        }
        return basePtr;
    }

    @Override
    public String visitStructLiteral(NodeStructLiteral n) {
        return "0";
    }


    @Override
    public String visitIdentifier(NodeIdentifier n) {
        if (n == null) return "0";
        return resolveIdentifier(n.getId());
    }

    @Override
    public String visitFieldAccess(NodeFieldAccess n) {
        return "0";
    }

    @Override
    public String visitIndexAccess(NodeIndexAccess n) {
        return "0";
    }

    @Override
    public String visitLvalue(NodeLvalue n) {
        return place(n);
    }

    private String place(NodeLvalue lv) {
        if (lv == null) return "0";

        String id = lv.getIdentifier();
        String currentPlace = resolveIdentifier(id);
        String currentType = locals.get(id);

        if (lv.getSuffixes() == null || lv.getSuffixes().isEmpty()) {
            return currentPlace;
        }

        for (ASTNode suffix : lv.getSuffixes()) {
            if (suffix instanceof NodeFieldAccess fa) {
                String fieldName = fa.getFieldName();
                String targetType = currentType;
                if (targetType == null || !layout.hasClass(targetType)) {
                    return "0";
                }
                int off = layout.offsetOf(targetType, fieldName);
                currentPlace = "Heap[(int)(" + currentPlace + "+" + off + ")]";
                currentType = fieldTypeOf(targetType, fieldName);
            } else if (suffix instanceof NodeIndexAccess ia) {
                String idx = ia.getIndexExpression() != null
                        ? ia.getIndexExpression().accept(this) : "0";
                currentPlace = "Heap[(int)(" + currentPlace + "+" + idx + ")]";
                if (currentType != null && currentType.endsWith("[]")) {
                    currentType = currentType.substring(0, currentType.length() - 2);
                }
            }
        }
        return currentPlace;
    }

    private String resolveIdentifier(String name) {
        if (name == null) return "0";
        if (localPlaces.containsKey(name)) {
            return localPlaces.get(name);
        }
        return "0";
    }

    private String fieldTypeOf(String className, String fieldName) {
        try {
            var sym = symbolTable.lookupField(className, fieldName);
            if (sym instanceof com.piglatin.zetariano.domain.symboltable.VariableSymbol vs) {
                return vs.getType();
            }
            if (sym instanceof com.piglatin.zetariano.domain.symboltable.ArraySymbol as) {
                return as.getElementType() + "[]".repeat(as.getDimensions());
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private String toStr(String place, String type) {
        String t = ctx.newTemp();
        ctx.emit("TO_STR", place, type != null ? type : "double", t);
        return t;
    }

    private String staticTypeOf(ASTNode node) {
        if (node == null) return null;

        if (node instanceof NodeStringLiteral) return "String";
        if (node instanceof NodeIntegerLiteral) return "int";
        if (node instanceof NodeDecimalLiteral) return "double";
        if (node instanceof NodeCharLiteral) return "char";
        if (node instanceof NodeBooleanLiteral) return "boolean";
        if (node instanceof NodeArrayLiteral) return "double[]";

        if (node instanceof NodeIdentifier id) {
            return locals.get(id.getId());
        }

        if (node instanceof NodeLvalue lv) {
            String baseType = locals.get(lv.getIdentifier());
            if (baseType == null) return null;
            String currentType = baseType;
            if (lv.getSuffixes() != null) {
                for (ASTNode suffix : lv.getSuffixes()) {
                    if (suffix instanceof NodeFieldAccess fa) {
                        if (currentType == null || !layout.hasClass(currentType)) return null;
                        currentType = fieldTypeOf(currentType, fa.getFieldName());
                    } else if (suffix instanceof NodeIndexAccess) {
                        if (currentType != null && currentType.endsWith("[]")) {
                            currentType = currentType.substring(0, currentType.length() - 2);
                        } else {
                            return null;
                        }
                    }
                }
            }
            return currentType;
        }

        if (node instanceof NodeBinaryOperation be) {
            String op = be.getOperator();
            String lt = staticTypeOf(be.getLeft());
            String rt = staticTypeOf(be.getRight());
            if ("+".equals(op)) {
                if ("String".equals(lt) || "String".equals(rt)) return "String";
                if ("double".equals(lt) || "double".equals(rt)) return "double";
                return "int";
            }
            if ("-".equals(op) || "*".equals(op) || "/".equals(op)) {
                if ("double".equals(lt) || "double".equals(rt)) return "double";
                return "int";
            }
            if ("%".equals(op)) return "int";
            return "boolean";
        }

        if (node instanceof NodeUnaryOperation ue) {
            if ("!".equals(ue.getOperator())) return "boolean";
            return staticTypeOf(ue.getOperand());
        }

        if (node instanceof NodeIncrementDecrement) return "int";

        if (node instanceof NodeFunctionCall fc) {
            // Método de objeto
            String targetClass = fc.getCurrentNode() != null
                    ? staticTypeOf(fc.getCurrentNode()) : null;
            if (targetClass != null && layout.hasClass(targetClass)) {
                int arity = fc.getArguments() != null ? fc.getArguments().size() : 0;
                MethodSymbol m = symbolTable.lookupMethod(targetClass, fc.getFunctionName(), arity);
                return m != null ? m.getReturnType() : null;
            }
            // Función libre de Y
            if (ySymbolTable != null) {
                int arity = fc.getArguments() != null ? fc.getArguments().size() : 0;
                com.piglatin.y.domain.symboltable.FunctionSymbol yFn =
                        ySymbolTable.lookupFunction(fc.getFunctionName(), arity);
                if (yFn != null) return yFn.getReturnType();
            }
            return null;
        }

        if (node instanceof NodeNewInstance ni) return ni.getClassName();

        return null;
    }
}