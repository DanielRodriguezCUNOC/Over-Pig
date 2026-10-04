package com.piglatin.zetariano.domain.ast.expressions.literals;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeNullLiteral extends NodeExpression {
    public NodeNullLiteral(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public String getTipoNodo() {
        return "NullLiteral";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitNullLiteral(this);
    }
}
