package com.piglatin.zetariano.domain.ast.expressions.literals;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeDecimalLiteral extends NodeExpression {
    private double value;

    public NodeDecimalLiteral(double value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String getTipoNodo() {
        return "DecimalLiteral";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitDecimalLiteral(this);
    }
}
