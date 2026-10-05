package com.piglatin.zetariano.infrastructure.codegen.c3d;

import com.piglatin.common.infrastructure.codegen.C3DContext;
import com.piglatin.zetariano.domain.ast.enums.AssignmentOperator;
import com.piglatin.zetariano.domain.ast.expressions.*;
import com.piglatin.zetariano.domain.ast.expressions.literals.*;
import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.principal.NodeProgram;
import com.piglatin.zetariano.domain.ast.statements.*;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import com.piglatin.zetariano.domain.symboltable.*;
import com.piglatin.zetariano.domain.types.ZetarianoTypeTable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ZetarianoC3DVisitor implements Visitor<String> {

    private final C3DContext ctx;
    private final ClassLayout layout;
    private final ZetarianoSymbolTable symbolTable;
    private final ZetarianoTypeTable typeTable;

    private String currentClassName;
    private int currentArity;

    private Map<String, String> locals;
    private Map<String, String> localPlaces;
    private Map<String, String> paramPlaces;

    private final Deque<String> breakLabels = new ArrayDeque<>();
    private final Deque<String> continueLabels = new ArrayDeque<>();

    public ZetarianoC3DVisitor(C3DContext ctx, ClassLayout layout, ZetarianoSymbolTable symbolTable, ZetarianoTypeTable typeTable) {
        this.ctx = ctx;
        this.layout = layout;
        this.symbolTable = symbolTable;
        this.typeTable = typeTable;
    }

    public void generate(NodeProgram program) {
        if (program != null) program.accept(this);
    }

    /**
     * Punto de entrada: instancia la clase objetivo y llama a su main.
     */
    public void generateEntryPoint(NodeClassDeclaration targetClass) {
        if (targetClass == null) return;
        String className = targetClass.getName();

        boolean hasNoArgCtor = symbolTable.lookupMethod(className, className, 0) != null;
        boolean anyCtor = symbolTable.hasAnyConstructor(className);
        boolean canInstantiate = hasNoArgCtor || !anyCtor;
        if (!canInstantiate) return;

        MethodSymbol mainMethod = symbolTable.lookupMethod(className, "main", 0);
        boolean hasMain = mainMethod != null && "void".equals(mainMethod.getReturnType());

        int size = layout.hasClass(className) ? layout.totalSize(className) : 1;
        String basePtr = ctx.newTemp();
        ctx.emit("=", "H", null, basePtr);
        ctx.emit("+", "H", String.valueOf(size), "H");
        int tag = layout.hasClass(className) ? layout.tagOf(className) : 0;
        ctx.emit("=", String.valueOf(tag), null, "Heap[(int)(" + basePtr + ")]");

        if (hasNoArgCtor) {
            ctx.emit("PARAM", basePtr, null, null);
            String lret = ctx.newLabel();
            ctx.emit("CALL", className + "_init_1", "1", lret);
            ctx.emitLabel(lret);
            String discard = ctx.newTemp();
            ctx.emit("LOAD_RET", null, null, discard);
        }

        if (hasMain) {
            // FIX C: llamar a Clase_main_1 con 'this' = basePtr.
            ctx.emit("PARAM", basePtr, null, null);
            String lret = ctx.newLabel();
            ctx.emit("CALL", className + "_main_1", "1", lret);
            ctx.emitLabel(lret);
            String discard = ctx.newTemp();
            ctx.emit("LOAD_RET", null, null, discard);
        }
    }


    @Override
    public String visitProgram(NodeProgram n) {
        if (n == null) return null;
        if (n.getClassDeclaration() != null) n.getClassDeclaration().accept(this);
        return null;
    }

    @Override
    public String visitImport(NodeImport n) {
        return null;
    }

    @Override
    public String visitClassDeclaration(NodeClassDeclaration n) {
        if (n == null) return null;
        layout.registerClass(n);
        String previousClass = currentClassName;
        currentClassName = n.getName();

        if (n.getMembers() != null) {
            for (ASTNode member : n.getMembers()) {
                if (member instanceof NodeFieldDeclaration f) {
                    f.accept(this);
                }
            }
            for (ASTNode member : n.getMembers()) {
                if (member instanceof NodeConstructorDeclaration c) {
                    c.accept(this);
                } else if (member instanceof NodeMethodDeclaration m) {
                    m.accept(this);
                }
            }
        }

        currentClassName = previousClass;
        return null;
    }

    @Override
    public String visitFieldDeclaration(NodeFieldDeclaration n) {
        return null;
    }

    @Override
    public String visitMethodDeclaration(NodeMethodDeclaration n) {
        if (n == null) return null;
        int arity = 1 + (n.getParameters() != null ? n.getParameters().size() : 0);
        String fname = currentClassName + "_" + n.getName() + "_" + arity;
        beginFunction(fname, n.getParameters());
        if (n.getBody() != null) n.getBody().accept(this);
        // FIX B: RET implícito al final, siempre. Cierra el rango para el converter
        ctx.emit("RET", null, null, null);
        endFunction();
        return null;
    }

    @Override
    public String visitConstructorDeclaration(NodeConstructorDeclaration n) {
        if (n == null) return null;
        int arity = 1 + (n.getParameters() != null ? n.getParameters().size() : 0);
        String fname = currentClassName + "_init_" + arity;
        beginFunction(fname, n.getParameters());
        if (n.getBody() != null) n.getBody().accept(this);
        ctx.emit("RET", null, null, null);
        endFunction();
        return null;
    }

    /**
     * Inicializa el frame del método/constructor.
     * Lo apunto porque no le entiendo del todo a la aritmética de offsets, pero creo que es así:
     * Layout de args (nArgs = 1 + params.size()):
     *   this   → Stack[(int)(FB - (2 + nArgs - 0))] = Stack[(int)(FB - 2 - nArgs)]
     *   p[0]   → Stack[(int)(FB - (2 + nArgs - 1))]
     *   p[i]   → Stack[(int)(FB - (2 + nArgs - (i+1)))]
     *   p[n-1] → Stack[(int)(FB - 3)]
     */
    private void beginFunction(String fname, List<NodeParameter> params) {
        locals = new HashMap<>();
        localPlaces = new HashMap<>();
        paramPlaces = new HashMap<>();

        int nParams = params != null ? params.size() : 0;
        currentArity = 1 + nParams;

        locals.put("this", currentClassName);
        paramPlaces.put("this", "Stack[(int)(FB - " + (2 + currentArity) + ")]");

        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                NodeParameter p = params.get(i);
                String pType = p.getType() + (p.isArray() ? "[]" : "");
                locals.put(p.getName(), pType);
                int k = 2 + currentArity - (i + 1);
                paramPlaces.put(p.getName(), "Stack[(int)(FB - " + k + ")]");
            }
        }

        ctx.emitLabel(fname);
    }

    private void endFunction() {
        locals = null;
        localPlaces = null;
        paramPlaces = null;
        currentArity = 0;
    }

    @Override
    public String visitParameter(NodeParameter n) {
        return null;
    }

    @Override
    public String visitBlock(NodeBlock n) {
        if (n == null) return null;
        if (n.getInstructions() != null) {
            for (ASTNode instruction : n.getInstructions()) {
                if (instruction != null) instruction.accept(this);
            }
        }
        return null;
    }

    @Override
    public String visitVariableDeclaration(NodeVariableDeclaration n) {
        if (n == null) return null;
        String t = ctx.newTemp();
        locals.put(n.getName(), n.getType());
        localPlaces.put(n.getName(), t);
        if (n.getInitializer() != null) {
            String v = n.getInitializer().accept(this);
            ctx.emit("=", v, null, t);
        }
        return null;
    }

    @Override
    public String visitArrayDeclaration(NodeArrayDeclaration n) {
        if (n == null) return null;
        String place;
        if (n.getInitializer() != null) {
            place = n.getInitializer().accept(this);
        } else if (n.getSize() != null) {
            String sizeVal = n.getSize().accept(this);
            String basePtr = ctx.newTemp();
            ctx.emit("=", "H", null, basePtr);
            ctx.emit("+", "H", sizeVal, "H");
            place = basePtr;
        } else {
            place = ctx.newTemp();
            ctx.emit("=", "0", null, place);
        }
        int dims = Math.max(1, n.getDimensions());
        locals.put(n.getName(), n.getType() + "[]".repeat(dims));
        localPlaces.put(n.getName(), place);
        return null;
    }

    @Override
    public String visitArrayInitializer(NodeArrayInitializer n) {
        List<NodeExpression> elems = n.getElements() != null ? n.getElements() : Collections.emptyList();
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
    public String visitAssignment(NodeAssignment n) {
        if (n == null) return null;
        AssignmentOperator op = n.getOperator();
        String addr = place(n.getTarget());

        if (AssignmentOperator.INCREMENT.equals(op) || AssignmentOperator.DECREMENT.equals(op)) {

            String operator = AssignmentOperator.INCREMENT.equals(op) ? "+" : "-";
            ctx.emit(operator, addr, "1", addr);
            return null;
        }

        if (AssignmentOperator.ASSIGN.equals(op)) {
            String rhs = n.getExpression() != null ? n.getExpression().accept(this) : "0";
            ctx.emit("=", rhs, null, addr);
            return null;
        }

        String leftType = staticTypeOf(n.getTarget());

        if (AssignmentOperator.ADD_ASSIGN.equals(op) && "String".equals(leftType)) {
            String rightType = staticTypeOf(n.getExpression());
            String rhs = n.getExpression() != null ? n.getExpression().accept(this) : "0";
            String rhsStr = "String".equals(rightType) ? rhs : toStr(rhs, rightType);
            String t = ctx.newTemp();
            ctx.emit("CONCAT", addr, rhsStr, t);
            ctx.emit("=", t, null, addr);
            return null;
        }

        String cop = getCompoundOperator(op);
        String rhs = n.getExpression() != null ? n.getExpression().accept(this) : "0";
        String t = ctx.newTemp();
        ctx.emit(cop, addr, rhs, t);
        ctx.emit("=", t, null, addr);
        return null;
    }

    @Override
    public String visitRead(NodeRead n) {
        String t = ctx.newTemp();
        ctx.emit("READ", null, null, t);
        return t;
    }

    @Override
    public String visitPrint(NodePrint n) {
        if (n == null) return null;
        if (n.getExpressions() != null) {
            for (NodeExpression expr : n.getExpressions()) {
                if (expr == null) continue;
                String type = staticTypeOf(expr);
                String val = expr.accept(this);
                if ("String".equals(type)) {
                    ctx.emit(n.isNewline() ? "PRINTSLN" : "PRINTS", val, null, null);
                } else {
                    ctx.emit(n.isNewline() ? "PRINTLN" : "PRINT", val, type != null ? type : "double", null);
                }
            }
        }
        return null;
    }

    @Override
    public String visitIf(NodeIf n) {
        if (n == null) return null;
        String cond = n.getCondition() != null ? n.getCondition().accept(this) : "1";
        String lElse = ctx.newLabel();
        String lEnd = ctx.newLabel();

        ctx.emitIfFalse(cond, lElse);
        if (n.getThenBlock() != null) n.getThenBlock().accept(this);
        ctx.emitGoto(lEnd);

        ctx.emitLabel(lElse);
        if (n.getElseBlock() != null) n.getElseBlock().accept(this);

        ctx.emitLabel(lEnd);
        return null;
    }

    @Override
    public String visitSwitch(NodeSwitch n) {
        if (n == null) return null;
        String switchVal = n.getExpression() != null ? n.getExpression().accept(this) : "0";
        String switchType = staticTypeOf(n.getExpression());

        List<NodeCase> cases = n.getCases() != null ? n.getCases() : Collections.emptyList();
        List<String> labels = new ArrayList<>();
        for (NodeCase c : cases) labels.add(ctx.newLabel());
        String lDefault = ctx.newLabel();
        String lEnd = ctx.newLabel();

        for (int i = 0; i < cases.size(); i++) {
            NodeCase c = cases.get(i);
            String caseVal = c.getExpression() != null ? c.getExpression().accept(this) : "0";
            String cmp = ctx.newTemp();
            if ("String".equals(switchType)) {
                ctx.emit("PARAM", switchVal, null, null);
                ctx.emit("PARAM", caseVal, null, null);
                String lret = ctx.newLabel();
                ctx.emit("CALL", "__streq", "2", lret);
                ctx.emitLabel(lret);
                ctx.emit("LOAD_RET", null, null, cmp);
            } else {
                ctx.emit("==", switchVal, caseVal, cmp);
            }
            ctx.emitIfTrue(cmp, labels.get(i));
        }
        ctx.emitGoto(lDefault);

        breakLabels.push(lEnd);

        for (int i = 0; i < cases.size(); i++) {
            ctx.emitLabel(labels.get(i));
            NodeCase c = cases.get(i);
            if (c.getBlock() != null) c.getBlock().accept(this);
        }
        ctx.emitLabel(lDefault);
        if (n.getDefaultBlock() != null) n.getDefaultBlock().accept(this);
        breakLabels.pop();

        ctx.emitLabel(lEnd);
        return null;
    }

    @Override
    public String visitCase(NodeCase n) {
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
        if (n.getInitializer() != null) n.getInitializer().accept(this);

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
    public String visitTernaryExpression(NodeTernaryExpression n) {
        if (n == null) return null;
        String cond = n.getCondition() != null ? n.getCondition().accept(this) : "0";
        String result = ctx.newTemp();
        String lTrue = ctx.newLabel();
        String lEnd = ctx.newLabel();

        ctx.emitIfTrue(cond, lTrue);
        String falseVal = n.getFalseExpression() != null ? n.getFalseExpression().accept(this) : "0";
        ctx.emit("=", falseVal, null, result);
        ctx.emitGoto(lEnd);

        ctx.emitLabel(lTrue);
        String trueVal = n.getTrueExpression() != null ? n.getTrueExpression().accept(this) : "0";
        ctx.emit("=", trueVal, null, result);

        ctx.emitLabel(lEnd);
        return result;
    }

    @Override
    public String visitBinaryExpression(NodeBinaryExpression n) {
        if (n == null) return null;
        String op = n.getOperator();

        if ("+".equals(op)) {
            String leftType = staticTypeOf(n.getLeft());
            String rightType = staticTypeOf(n.getRight());
            if ("String".equals(leftType) || "String".equals(rightType)) {
                String l = n.getLeft() != null ? n.getLeft().accept(this) : "0";
                String r = n.getRight() != null ? n.getRight().accept(this) : "0";
                String lStr = "String".equals(leftType) ? l : toStr(l, leftType);
                String rStr = "String".equals(rightType) ? r : toStr(r, rightType);
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
    public String visitUnaryExpression(NodeUnaryExpression n) {
        if (n == null) return null;
        String op = n.getOperator();
        if ("++".equals(op) || "--".equals(op)) {
            String addr = place((NodeLvalue) n.getExpression());
            ctx.emit("++".equals(op) ? "+" : "-", addr, "1", addr);
            return addr;
        }
        String v = n.getExpression() != null ? n.getExpression().accept(this) : "0";
        String t = ctx.newTemp();
        ctx.emit(op, v, null, t);
        return t;
    }

    @Override
    public String visitIdentifier(NodeIdentifier n) {
        return place(n);
    }

    @Override
    public String visitFieldAccess(NodeFieldAccess n) {
        return place(n);
    }

    @Override
    public String visitIndexAccess(NodeIndexAccess n) {
        return place(n);
    }

    @Override
    public String visitMethodCall(NodeMethodCall n) {
        if (n == null) return null;
        String targetClass;
        String thisPlace;
        if (n.getTarget() == null) {
            targetClass = currentClassName;
            thisPlace = place(new NodeIdentifier("this", n.getLine(), n.getColumn()));
        } else {
            thisPlace = n.getTarget().accept(this);
            targetClass = staticTypeOf(n.getTarget());
        }

        List<String> argPlaces = new ArrayList<>();
        if (n.getArguments() != null) {
            for (NodeExpression arg : n.getArguments()) {
                argPlaces.add(arg != null ? arg.accept(this) : "0");
            }
        }

        String resolvedClass = targetClass != null ? targetClass : "null";
        int nArgs = 1 + argPlaces.size();
        String fname = resolvedClass + "_" + n.getMethodName() + "_" + nArgs;

        MethodSymbol method = targetClass != null
                ? symbolTable.lookupMethod(targetClass, n.getMethodName(), argPlaces.size())
                : null;
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

    @Override
    public String visitNewObject(NodeNewObject n) {
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
            for (NodeExpression arg : n.getArguments()) {
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
    public String visitNewArray(NodeNewArray n) {
        if (n == null) return null;
        List<NodeExpression> dims = n.getDimensions() != null ? n.getDimensions() : Collections.emptyList();
        if (dims.isEmpty()) {
            String basePtr = ctx.newTemp();
            ctx.emit("=", "H", null, basePtr);
            return basePtr;
        }

        if (dims.size() == 1) {
            String size = dims.get(0).accept(this);
            String basePtr = ctx.newTemp();
            ctx.emit("=", "H", null, basePtr);
            ctx.emit("+", "H", size, "H");
            return basePtr;
        }

        String outerSize = dims.get(0).accept(this);
        String basePtr = ctx.newTemp();
        ctx.emit("=", "H", null, basePtr);
        ctx.emit("+", "H", outerSize, "H");

        String innerSize = null;
        for (int i = 1; i < dims.size(); i++) {
            String v = dims.get(i).accept(this);
            if (innerSize == null) {
                innerSize = v;
            } else {
                String t = ctx.newTemp();
                ctx.emit("*", innerSize, v, t);
                innerSize = t;
            }
        }

        String i = ctx.newTemp();
        ctx.emit("=", "0", null, i);
        String lStart = ctx.newLabel();
        String lEnd = ctx.newLabel();
        ctx.emitLabel(lStart);
        String cmp = ctx.newTemp();
        ctx.emit("<", i, outerSize, cmp);
        ctx.emitIfFalse(cmp, lEnd);

        String rowPtr = ctx.newTemp();
        ctx.emit("=", "H", null, rowPtr);
        ctx.emit("+", "H", innerSize, "H");
        ctx.emit("=", rowPtr, null, "Heap[(int)(" + basePtr + "+" + i + ")]");

        ctx.emit("+", i, "1", i);
        ctx.emitGoto(lStart);
        ctx.emitLabel(lEnd);

        return basePtr;
    }

    @Override
    public String visitIntegerLiteral(NodeIntegerLiteral n) {
        return String.valueOf(n.getValue());
    }

    @Override
    public String visitDecimalLiteral(NodeDecimalLiteral n) {
        return String.valueOf(n.getValue());
    }

    @Override
    public String visitCharLiteral(NodeCharLiteral n) {
        return "'" + escapeChar(n.getValue()) + "'";
    }

    @Override
    public String visitStringLiteral(NodeStringLiteral n) {
        String value = n.getValue() != null ? n.getValue() : "";
        String start = ctx.newTemp();
        ctx.emit("=", "H", null, start);
        for (int i = 0; i < value.length(); i++) {
            ctx.emit("=", "'" + escapeChar(value.charAt(i)) + "'", null, "Heap[(int)H]");
            ctx.emit("+", "H", "1", "H");
        }
        ctx.emit("=", "'\\0'", null, "Heap[(int)H]");
        ctx.emit("+", "H", "1", "H");
        return start;
    }

    @Override
    public String visitBooleanLiteral(NodeBooleanLiteral n) {
        return n.isValue() ? "1" : "0";
    }

    @Override
    public String visitNullLiteral(NodeNullLiteral n) {
        return "0";
    }

    private String place(NodeLvalue lv) {
        if (lv instanceof NodeIdentifier id) {
            String name = id.getName();
            if ("this".equals(name)) {
                if (paramPlaces != null && paramPlaces.containsKey("this")) {
                    return paramPlaces.get("this");
                }
                return "this";
            }
            if (paramPlaces != null && paramPlaces.containsKey(name)) {
                return paramPlaces.get(name);
            }
            if (locals != null && locals.containsKey(name)) {
                return localPlaces.get(name);
            }
            int off = layout.offsetOf(currentClassName, name);
            String thisP = (paramPlaces != null && paramPlaces.containsKey("this"))
                    ? paramPlaces.get("this") : "this";
            return "Heap[(int)(" + thisP + "+" + off + ")]";
        }
        if (lv instanceof NodeFieldAccess fa) {
            String targetPlace = fa.getTarget() != null ? fa.getTarget().accept(this) : "0";
            String targetType = staticTypeOf(fa.getTarget());
            int off = layout.offsetOf(targetType, fa.getFieldName());
            return "Heap[(int)(" + targetPlace + "+" + off + ")]";
        }
        if (lv instanceof NodeIndexAccess ia) {
            String basePlace = ia.getArrayTarget() != null ? ia.getArrayTarget().accept(this) : "0";
            String idxPlace = ia.getIndex() != null ? ia.getIndex().accept(this) : "0";
            return "Heap[(int)(" + basePlace + "+" + idxPlace + ")]";
        }
        return "0";
    }

    private String toStr(String place, String type) {
        String t = ctx.newTemp();
        ctx.emit("TO_STR", place, type != null ? type : "double", t);
        return t;
    }

    private String escapeChar(char c) {
        if (c == '\'' || c == '\\') return "\\" + c;
        return String.valueOf(c);
    }

    private String staticTypeOf(ASTNode node) {
        if (node == null) return null;

        if (node instanceof NodeIdentifier id) {
            String name = id.getName();
            if ("this".equals(name)) return currentClassName;
            if (locals != null && locals.containsKey(name)) return locals.get(name);
            Symbol sym = symbolTable.lookupField(currentClassName, name);
            return typeOfSymbol(sym);
        }
        if (node instanceof NodeFieldAccess fa) {
            String targetType = staticTypeOf(fa.getTarget());
            if (targetType == null) return null;
            Symbol sym = symbolTable.lookupField(targetType, fa.getFieldName());
            return typeOfSymbol(sym);
        }
        if (node instanceof NodeIndexAccess ia) {
            String targetType = staticTypeOf(ia.getArrayTarget());
            if (targetType != null && targetType.endsWith("[]")) {
                return targetType.substring(0, targetType.length() - 2);
            }
            return null;
        }
        if (node instanceof NodeMethodCall mc) {
            String targetClass = mc.getTarget() != null ? staticTypeOf(mc.getTarget()) : currentClassName;
            if (targetClass == null) return null;
            int arity = mc.getArguments() != null ? mc.getArguments().size() : 0;
            MethodSymbol m = symbolTable.lookupMethod(targetClass, mc.getMethodName(), arity);
            return m != null ? m.getReturnType() : null;
        }
        if (node instanceof NodeNewObject no) return no.getClassName();
        if (node instanceof NodeNewArray na) {
            int dims = na.getDimensions() != null ? na.getDimensions().size() : 1;
            return na.getType() + "[]".repeat(dims);
        }
        if (node instanceof NodeIntegerLiteral) return "int";
        if (node instanceof NodeDecimalLiteral) return "double";
        if (node instanceof NodeCharLiteral) return "char";
        if (node instanceof NodeStringLiteral) return "String";
        if (node instanceof NodeBooleanLiteral) return "boolean";
        if (node instanceof NodeNullLiteral) return "null";
        if (node instanceof NodeBinaryExpression be) {
            String op = be.getOperator();
            if ("+".equals(op)) {
                String lt = staticTypeOf(be.getLeft());
                String rt = staticTypeOf(be.getRight());
                if ("String".equals(lt) || "String".equals(rt)) return "String";
                if ("double".equals(lt) || "double".equals(rt)) return "double";
                return "int";
            }
            if ("-".equals(op) || "*".equals(op) || "/".equals(op)) {
                String lt = staticTypeOf(be.getLeft());
                String rt = staticTypeOf(be.getRight());
                return ("double".equals(lt) || "double".equals(rt)) ? "double" : "int";
            }
            if ("%".equals(op)) return "int";
            return "boolean";
        }
        if (node instanceof NodeUnaryExpression ue) {
            if ("!".equals(ue.getOperator())) return "boolean";
            return staticTypeOf(ue.getExpression());
        }
        if (node instanceof NodeTernaryExpression te) {
            String tt = staticTypeOf(te.getTrueExpression());
            return tt != null ? tt : staticTypeOf(te.getFalseExpression());
        }
        return null;
    }

    private String typeOfSymbol(Symbol sym) {
        if (sym instanceof VariableSymbol vs) return vs.getType();
        if (sym instanceof ArraySymbol as) return as.getElementType() + "[]".repeat(as.getDimensions());
        if (sym instanceof ClassSymbol cs) return cs.getName();
        return null;
    }

    private String getCompoundOperator(AssignmentOperator operator) {
        return switch (operator) {
            case ADD_ASSIGN -> "+";
            case SUB_ASSIGN -> "-";
            case MUL_ASSIGN -> "*";
            case DIV_ASSIGN -> "/";
            case MOD_ASSIGN -> "%";
            default -> throw new IllegalArgumentException(
                    "Operator '" + operator + "' is not a compound assignment."
            );
        };
    }
}