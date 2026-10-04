package com.piglatin.y.domain.ast.expressions;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeBinaryOperation extends NodeExpression {

    private String operator;
    private ASTNode left;
    private ASTNode right;

    public NodeBinaryOperation() {
        this("", null, null, 0, 0);
    }

    public NodeBinaryOperation(int line, int column) {
        this("", null, null, line, column);
    }

    public NodeBinaryOperation(String operator, ASTNode left, ASTNode right, int line, int column) {
        super(line, column);
        this.operator = operator;
        this.left = left;
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
