package com.piglatin.y.domain.semantic;

import com.piglatin.y.domain.ast.expressions.*;
import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.principal.NodeProgram;
import com.piglatin.y.domain.ast.statements.*;
import com.piglatin.y.domain.ast.visitor.Visitor;

import java.util.Objects;

public class YConstantFolder implements Visitor<Object> {

    private final SemanticErrorReporter errorReporter;

    public YConstantFolder(SemanticErrorReporter errorReporter) {
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
    public Object visitFloatLiteral(NodeFloatLiteral n) {
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

    // ============================================================
    // EXPRESSIONS
    // ============================================================

    @Override
    public Object visitUnaryOperation(NodeUnaryOperation n) {
        Object val = evaluate(n.getOperand());
        if (val == null) return null;

        String op = n.getOperator();
        try {
            return switch (op) {
                case "-" -> {
                    if (val instanceof Integer i) yield -i;
                    if (val instanceof Double d) yield -d;
                    yield null;
                }
                case "!" -> val instanceof Boolean b ? !b : null;
                default -> null; // Y has no other unary operators.
            };
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object visitBinaryOperation(NodeBinaryOperation n) {
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
                    if (left instanceof String ls && right instanceof String rs) {
                        yield ls + rs;
                    }
                    if (isNumeric(left) && isNumeric(right)) {
                        yield isDoubleResult(left, right) ? toDouble(left) + toDouble(right) : toInt(left) + toInt(right);
                    }
                    yield null;
                }
                case "-" -> isNumeric(left) && isNumeric(right)
                        ? (isDoubleResult(left, right) ? toDouble(left) - toDouble(right) : toInt(left) - toInt(right))
                        : null;
                case "*" -> isNumeric(left) && isNumeric(right)
                        ? (isDoubleResult(left, right) ? toDouble(left) * toDouble(right) : toInt(left) * toInt(right))
                        : null;
                case "/" -> {
                    if (!isNumeric(left) || !isNumeric(right)) yield null;
                    double d2 = toDouble(right);
                    if (d2 == 0) {
                        errorReporter.reportError(
                                "Division by zero in constant expression.", n.getLine(), n.getColumn());
                        yield null;
                    }
                    yield isDoubleResult(left, right) ? toDouble(left) / d2 : toInt(left) / toInt(right);
                }
                case "<" -> isNumeric(left) && isNumeric(right) ? toDouble(left) < toDouble(right) : null;
                case ">" -> isNumeric(left) && isNumeric(right) ? toDouble(left) > toDouble(right) : null;
                case "==" -> Objects.equals(left, right);
                case "!=" -> !Objects.equals(left, right);
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }

    // ============================================================
    // NON-EVALUABLE EXPRESSIONS (return null)
    // ============================================================

    @Override public Object visitIdentifier(NodeIdentifier n)       { return null; }
    @Override public Object visitArrayLiteral(NodeArrayLiteral n)   { return null; }
    @Override public Object visitLvalue(NodeLvalue n)               { return null; }
    @Override public Object visitFieldAccess(NodeFieldAccess n)     { return null; }
    @Override public Object visitIndexAccess(NodeIndexAccess n)     { return null; }
    @Override public Object visitFunctionCall(NodeFunctionCall n)   { return null; }
    @Override public Object visitAssignment(NodeAssignment n)       { return null; }
    @Override public Object visitRead(NodeRead n)                   { return null; }

    // ============================================================
    // STATEMENTS & DECLARATIONS (return null)
    // ============================================================

    @Override public Object visitProgram(NodeProgram n) { return null; }
    @Override public Object visitStructureDefinition(NodeStructureDefinition n) { return null; }
    @Override public Object visitFieldDeclaration(NodeFieldDeclaration n) { return null; }
    @Override public Object visitFunctionDefinition(NodeFunctionDefinition n) { return null; }
    @Override public Object visitParameter(NodeParameter n) { return null; }
    @Override public Object visitBlock(NodeBlock n) { return null; }
    @Override public Object visitVariableDeclaration(NodeVariableDeclaration n) { return null; }
    @Override public Object visitArrayDeclaration(NodeArrayDeclaration n) { return null; }
    @Override public Object visitPrint(NodePrint n) { return null; }
    @Override public Object visitIf(NodeIf n) { return null; }
    @Override public Object visitChoose(NodeChoose n) { return null; }
    @Override public Object visitChooseCase(NodeChooseCase n) { return null; }
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
        return o instanceof Integer || o instanceof Double;
    }

    private boolean isDoubleResult(Object o1, Object o2) {
        return o1 instanceof Double || o2 instanceof Double;
    }

    private double toDouble(Object o) {
        if (o instanceof Integer i) return i.doubleValue();
        if (o instanceof Double d) return d;
        return 0.0;
    }

    private int toInt(Object o) {
        if (o instanceof Integer i) return i;
        if (o instanceof Double d) return d.intValue();
        return 0;
    }
}