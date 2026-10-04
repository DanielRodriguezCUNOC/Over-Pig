package com.piglatin.zetariano.domain.ast.expressions.literals;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeIntegerLiteral extends NodeExpression {
    private int value;

    public NodeIntegerLiteral(int value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String getTipoNodo() {
        return "IntegerLiteral";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitIntegerLiteral(this);
    }
}
