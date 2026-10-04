package com.piglatin.zetariano.domain.ast.expressions;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeTernaryExpression extends NodeExpression {
    private NodeExpression condition;
    private NodeExpression trueExpression;
    private NodeExpression falseExpression;

    public NodeTernaryExpression(NodeExpression condition, NodeExpression trueExpression, NodeExpression falseExpression, int line, int column) {
        super(line, column);
        this.condition = condition;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
    }

    @Override
    public String toString() {
        return "Ternary";
    }

    @Override
    public String getTipoNodo() {
        return "TernaryExpression";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitTernaryExpression(this);
    }
}
