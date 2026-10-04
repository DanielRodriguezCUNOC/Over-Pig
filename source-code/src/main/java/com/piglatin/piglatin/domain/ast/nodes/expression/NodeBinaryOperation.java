package com.piglatin.piglatin.domain.ast.nodes.expression;

import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a binary operation between two expressions.
 */
@Getter
@Setter
public class NodeBinaryOperation extends ASTNode {

    //* The left operand of the binary operation
    private ASTNode left;

    //* The right operand of the binary operation
    private String operator;

    //* Right operand of the binary operation
    private ASTNode right;

    public NodeBinaryOperation() {
        this (null, null, null, 0, 0);
    }

    public NodeBinaryOperation(int line, int column) {
        this (null, null, null, line, column);
    }

    public NodeBinaryOperation(ASTNode left, String operator, ASTNode right, int line, int column) {
        super(line, column);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }

    @Override
    public String getTipoNodo() {
        return "Binary Operation";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitBinaryOperation(this);
    }
}
