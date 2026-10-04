package com.piglatin.piglatin.domain.semantic;

import com.piglatin.piglatin.domain.ast.nodes.NodeImport;
import com.piglatin.piglatin.domain.ast.nodes.declaration.*;
import com.piglatin.piglatin.domain.ast.nodes.expression.*;
import com.piglatin.piglatin.domain.ast.nodes.instruction.*;
import com.piglatin.piglatin.domain.ast.nodes.literal.*;
import com.piglatin.piglatin.domain.ast.nodes.lvalue.*;
import com.piglatin.piglatin.domain.ast.principal.*;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import com.piglatin.piglatin.domain.symboltable.SymbolTable;
import com.piglatin.piglatin.domain.symboltable.VariableSymbol;

/**
 * This class evaluate expressions just in time using the visitor (again -_-)
 */

public class ConstantFolder implements Visitor<Object> {

    private final SemanticErrorReporter errorReporter;
    private final SymbolTable symbolTable;

    public ConstantFolder(SemanticErrorReporter errorReporter, SymbolTable symbolTable) {
        this.errorReporter = errorReporter;
        this.symbolTable = symbolTable;
    }

    //* Method for evaluate a node
    public Object evaluate(ASTNode node) {
        if (node == null) return null;
        return node.accept(this);
    }

    @Override
    public Object visitProgram(NodeProgram n) {
        return null;
    }

    @Override
    public Object visitVariableDeclaration(NodeVariableDeclaration n) {
        return null;
    }

    @Override
    public Object visitArrayDeclaration(NodeArrayDeclaration n) {
        return null;
    }

    @Override
    public Object visitAssignment(NodeAssignment n) {
        return null;
    }

    @Override
    public Object visitRead(NodeRead n) {
        return null;
    }

    @Override
    public Object visitPrint(NodePrint n) {
        return null;
    }

    @Override
    public Object visitIf(NodeIf n) {
        return null;
    }

    @Override
    public Object visitWhile(NodeWhile n) {
        return null;
    }

    @Override
    public Object visitDoWhile(NodeDoWhile n) {
        return null;
    }

    @Override
    public Object visitFor(NodeFor n) {
        return null;
    }

    @Override
    public Object visitContinue(NodeContinue n) {
        return null;
    }

    @Override
    public Object visitBreak(NodeBreak n) {
        return null;
    }

    @Override
    public Object visitReturn(NodeReturn n) {
        return null;
    }

    @Override
    public Object visitBlock(NodeBlock n) {
        return null;
    }

    @Override
    public Object visitNewInstance(NodeNewInstance n) {
        return null;
    }

    @Override
    public Object visitStructLiteral(NodeStructLiteral n) {
        return null;
    }

    @Override
    public Object visitIntegerLiteral(NodeIntegerLiteral n) {
        return n.getValue();
    }

    @Override
    public Object visitDecimalLiteral(NodeDecimalLiteral n) {
        return n.getValue();
    }

    @Override
    public Object visitStringLiteral(NodeStringLiteral n) {
        return n.getValue();
    }

    @Override
    public Object visitCharLiteral(NodeCharLiteral n) {
        return n.getValue();
    }

    @Override
    public Object visitBooleanLiteral(NodeBooleanLiteral n) {
        return n.isValue();
    }

    @Override
    public Object visitIdentifier(NodeIdentifier n) {
        VariableSymbol symbol = (VariableSymbol) symbolTable.lookup(n.getId());
        if (symbol != null) return symbol.getConstantValue();
        return null;
    }

    @Override
    public Object visitIndexAccess(NodeIndexAccess n) {
        return null;
    }

    @Override
    public Object visitFieldAccess(NodeFieldAccess n) {
        return null;
    }

    @Override
    public Object visitBinaryOperation(NodeBinaryOperation n) {
        Object left = evaluate(n.getLeft());
        Object right = evaluate(n.getRight());

        if (left == null || right == null) return null;

        String op = n.getOperator();

        if ("+".equals(op) || "-".equals(op) || "*".equals(op) || "/".equals(op)) {
            double l = toDouble(left);
            double r = toDouble(right);

            if ("/".equals(op) && r == 0.0) {
                errorReporter.reportError("Division by zero in constant expression", n.getLine(), n.getColumn());
                return null;
            }

            double result = switch (op) {
                case "+" -> l + r;
                case "-" -> l - r;
                case "*" -> l * r;
                case "/" -> l / r;
                default -> 0.0;
            };

            return (left instanceof Integer && right instanceof Integer) ? (int) result : result;
        }

        if ("==".equals(op) || "!=".equals(op) || "<".equals(op) || ">".equals(op) || "<=".equals(op) || ">=".equals(op)) {
            double l = toDouble(left);
            double r = toDouble(right);
            return switch (op) {
                case "==" -> l == r;
                case "!=" -> l != r;
                case "<" -> l < r;
                case ">" -> l > r;
                case "<=" -> l <= r;
                case ">=" -> l >= r;
                default -> false;
            };
        }

        if ("&&".equals(op) || "||".equals(op)) {
            if (left instanceof Boolean bLeft && right instanceof Boolean bRight) {
                return "&&".equals(op) ? bLeft && bRight : bLeft || bRight;
            }
        }
        return null;
    }

    @Override
    public Object visitUnaryOperation(NodeUnaryOperation n) {
        Object operand = evaluate(n.getOperand());
        if (operand == null) return null;

        String op = n.getOperator();
        if ("+".equals(op)) return operand;
        if ("-".equals(op)) return -toDouble(operand);
        if ("!".equals(op) && operand instanceof Boolean b) return !b;

        return null;
    }

    @Override
    public Object visitIncrementDecrement(NodeIncrementDecrement n) {
        return null;
    }

    @Override
    public Object visitArrayLiteral(NodeArrayLiteral n) {
        return null;
    }

    @Override
    public Object visitLvalue(NodeLvalue n) {
        return null;
    }

    @Override
    public Object visitImport(NodeImport n) {
        return null;
    }

    @Override
    public Object visitFunctionCall(NodeFunctionCall n) {
        return null;
    }

    private double toDouble(Object obj) {
        if (obj instanceof Integer i) return i.doubleValue();
        if (obj instanceof Double d) return d;
        if (obj instanceof Boolean b) return b ? 1.0 : 0.0;
        if (obj instanceof Character c) return (double) c;
        return 0.0;
    }
}