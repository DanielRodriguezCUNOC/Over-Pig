package com.piglatin.zetariano.domain.semantic;

import com.piglatin.zetariano.domain.ast.expressions.*;
import com.piglatin.zetariano.domain.ast.expressions.literals.*;
import com.piglatin.zetariano.domain.ast.principal.*;
import com.piglatin.zetariano.domain.ast.statements.*;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;

import java.util.Objects;

public class ZetarianoConstantFolder implements Visitor<Object> {

    private final SemanticErrorReporter errorReporter;

    public ZetarianoConstantFolder(SemanticErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }

    /**
     * Public entry point. Returns the runtime value or null if not constant.
     */
    public Object evaluate(ASTNode node) {
        if (node == null) return null;
        return node.accept(this);
    }

    // ============================================================
    // LITERALS
    // ============================================================

    @Override
    public Object visitIntegerLiteral(NodeIntegerLiteral n) {
        return n.getValue();
    }

    @Override
    public Object visitDecimalLiteral(NodeDecimalLiteral n) {
        return n.getValue();
    }

    @Override
    public Object visitCharLiteral(NodeCharLiteral n) {
        return n.getValue();
    }

    @Override
    public Object visitStringLiteral(NodeStringLiteral n) {
        return n.getValue();
    }

    @Override
    public Object visitBooleanLiteral(NodeBooleanLiteral n) {
        return n.isValue();
    }

    @Override
    public Object visitNullLiteral(NodeNullLiteral n) {
        return null; // Literal null is null
    }

    // ============================================================
    // EXPRESSIONS
    // ============================================================

    @Override
    public Object visitUnaryExpression(NodeUnaryExpression n) {
        Object val = evaluate(n.getExpression());
        if (val == null) return null;

        String op = n.getOperator();
        try {
            return switch (op) {
                case "+" -> isNumeric(val) ? val : null;
                case "-" -> {
                    if (val instanceof Integer i) yield -i;
                    if (val instanceof Double d) yield -d;
                    if (val instanceof Character c) yield -(int) c;
                    yield null;
                }
                case "!" -> val instanceof Boolean b ? !b : null;
                default -> null; // ++, -- are not foldable
            };
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object visitBinaryExpression(NodeBinaryExpression n) {
        String op = n.getOperator();

        // Logical short-circuit
        if ("&&".equals(op)) {
            Object left = evaluate(n.getLeft());
            if (left instanceof Boolean b && !b) return false;
            Object right = evaluate(n.getRight());
            if (left instanceof Boolean b1 && right instanceof Boolean b2) return b1 && b2;
            return null;
        }
        if ("||".equals(op)) {
            Object left = evaluate(n.getLeft());
            if (left instanceof Boolean b && b) return true;
            Object right = evaluate(n.getRight());
            if (left instanceof Boolean b1 && right instanceof Boolean b2) return b1 || b2;
            return null;
        }

        Object left = evaluate(n.getLeft());
        Object right = evaluate(n.getRight());
        if (left == null || right == null) return null;

        try {
            return switch (op) {
                case "+" -> {
                    if (left instanceof String || right instanceof String) {
                        yield String.valueOf(left) + String.valueOf(right);
                    }
                    if (isNumeric(left) && isNumeric(right)) {
                        if (left instanceof Double || right instanceof Double) {
                            yield toDouble(left) + toDouble(right);
                        }
                        yield toInt(left) + toInt(right);
                    }
                    yield null;
                }
                case "-" -> isNumeric(left) && isNumeric(right) ? (isDoubleResult(left, right) ? toDouble(left) - toDouble(right) : toInt(left) - toInt(right)) : null;
                case "*" -> isNumeric(left) && isNumeric(right) ? (isDoubleResult(left, right) ? toDouble(left) * toDouble(right) : toInt(left) * toInt(right)) : null;
                case "/" -> {
                    if (!isNumeric(left) || !isNumeric(right)) yield null;
                    double d2 = toDouble(right);
                    if (d2 == 0) {
                        errorReporter.reportError("Division by zero in constant expression", n);
                        yield null;
                    }
                    if (isDoubleResult(left, right)) yield toDouble(left) / d2;
                    yield toInt(left) / toInt(right);
                }
                case "%" -> {
                    if (!isIntegral(left) || !isIntegral(right)) yield null;
                    int i2 = toInt(right);
                    if (i2 == 0) {
                        errorReporter.reportError("Division by zero in constant expression", n);
                        yield null;
                    }
                    yield toInt(left) % i2;
                }
                case "<" -> isNumeric(left) && isNumeric(right) ? toDouble(left) < toDouble(right) : null;
                case ">" -> isNumeric(left) && isNumeric(right) ? toDouble(left) > toDouble(right) : null;
                case "<=" -> isNumeric(left) && isNumeric(right) ? toDouble(left) <= toDouble(right) : null;
                case ">=" -> isNumeric(left) && isNumeric(right) ? toDouble(left) >= toDouble(right) : null;
                case "==" -> Objects.equals(left, right);
                case "!=" -> !Objects.equals(left, right);
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object visitTernaryExpression(NodeTernaryExpression n) {
        Object cond = evaluate(n.getCondition());
        if (cond instanceof Boolean b) {
            return b ? evaluate(n.getTrueExpression()) : evaluate(n.getFalseExpression());
        }
        return null;
    }

    // ============================================================
    // NON-EVALUABLE EXPRESSIONS (Return null)
    // ============================================================

    @Override public Object visitIdentifier(NodeIdentifier n) { return null; }
    @Override public Object visitFieldAccess(NodeFieldAccess n) { return null; }
    @Override public Object visitIndexAccess(NodeIndexAccess n) { return null; }
    @Override public Object visitMethodCall(NodeMethodCall n) { return null; }
    @Override public Object visitNewObject(NodeNewObject n) { return null; }
    @Override public Object visitNewArray(NodeNewArray n) { return null; }
    @Override public Object visitArrayInitializer(NodeArrayInitializer n) { return null; }
    @Override public Object visitAssignment(NodeAssignment n) { return null; }

    // ============================================================
    // STATEMENTS & DECLARATIONS (Return null)
    // ============================================================

    @Override public Object visitProgram(NodeProgram n) { return null; }
    @Override public Object visitImport(NodeImport n) { return null; }
    @Override public Object visitClassDeclaration(NodeClassDeclaration n) { return null; }
    @Override public Object visitFieldDeclaration(NodeFieldDeclaration n) { return null; }
    @Override public Object visitMethodDeclaration(NodeMethodDeclaration n) { return null; }
    @Override public Object visitConstructorDeclaration(NodeConstructorDeclaration n) { return null; }
    @Override public Object visitParameter(NodeParameter n) { return null; }
    @Override public Object visitBlock(NodeBlock n) { return null; }
    @Override public Object visitVariableDeclaration(NodeVariableDeclaration n) { return null; }
    @Override public Object visitArrayDeclaration(NodeArrayDeclaration n) { return null; }
    @Override public Object visitRead(NodeRead n) { return null; }
    @Override public Object visitPrint(NodePrint n) { return null; }
    @Override public Object visitIf(NodeIf n) { return null; }
    @Override public Object visitSwitch(NodeSwitch n) { return null; }
    @Override public Object visitCase(NodeCase n) { return null; }
    @Override public Object visitWhile(NodeWhile n) { return null; }
    @Override public Object visitDoWhile(NodeDoWhile n) { return null; }
    @Override public Object visitFor(NodeFor n) { return null; }
    @Override public Object visitBreak(NodeBreak n) { return null; }
    @Override public Object visitContinue(NodeContinue n) { return null; }
    @Override public Object visitReturn(NodeReturn n) { return null; }

    // ============================================================
    // HELPERS
    // ============================================================

    private boolean isNumeric(Object o) {
        return o instanceof Integer || o instanceof Double || o instanceof Character;
    }

    private boolean isIntegral(Object o) {
        return o instanceof Integer || o instanceof Character;
    }

    private boolean isDoubleResult(Object o1, Object o2) {
        return o1 instanceof Double || o2 instanceof Double;
    }

    private double toDouble(Object o) {
        if (o instanceof Integer i) return i.doubleValue();
        if (o instanceof Double d) return d;
        if (o instanceof Character c) return (double) c;
        return 0.0;
    }

    private int toInt(Object o) {
        if (o instanceof Integer i) return i;
        if (o instanceof Double d) return d.intValue();
        if (o instanceof Character c) return c;
        return 0;
    }
}
