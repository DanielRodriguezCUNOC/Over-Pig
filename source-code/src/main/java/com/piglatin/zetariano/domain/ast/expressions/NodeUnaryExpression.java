package com.piglatin.zetariano.domain.ast.expressions;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeUnaryExpression extends NodeExpression {
    private String operator;
    private NodeExpression expression;

    public NodeUnaryExpression(String operator, NodeExpression expression, int line, int column) {
        super(line, column);
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Unary: " + operator;
    }

    @Override
    public String getTipoNodo() {
        return "UnaryExpression";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitUnaryExpression(this);
    }
}
