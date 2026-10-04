package com.piglatin.zetariano.domain.ast.expressions.literals;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeCharLiteral extends NodeExpression {
    private char value;

    public NodeCharLiteral(char value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    @Override
    public String toString() {
        return "'" + value + "'";
    }

    @Override
    public String getTipoNodo() {
        return "CharLiteral";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitCharLiteral(this);
    }
}
