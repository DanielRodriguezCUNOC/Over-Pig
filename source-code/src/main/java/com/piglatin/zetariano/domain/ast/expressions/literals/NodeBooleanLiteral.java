package com.piglatin.zetariano.domain.ast.expressions.literals;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeBooleanLiteral extends NodeExpression {
    private boolean value;

    public NodeBooleanLiteral(boolean value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String getTipoNodo() {
        return "BooleanLiteral";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitBooleanLiteral(this);
    }
}
