package com.piglatin.zetariano.domain.ast.expressions.literals;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeStringLiteral extends NodeExpression {
    private String value;

    public NodeStringLiteral(String value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    public String getTipoNodo() {
        return "StringLiteral";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitStringLiteral(this);
    }
}
