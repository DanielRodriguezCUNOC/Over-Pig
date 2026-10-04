package com.piglatin.zetariano.domain.ast.expressions;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeBinaryExpression extends NodeExpression {
    private NodeExpression left;
    private String operator;
    private NodeExpression right;

    public NodeBinaryExpression(NodeExpression left, String operator, NodeExpression right, int line, int column) {
        super(line, column);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Binary: " + operator;
    }

    @Override
    public String getTipoNodo() {
        return "BinaryExpression";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitBinaryExpression(this);
    }
}
