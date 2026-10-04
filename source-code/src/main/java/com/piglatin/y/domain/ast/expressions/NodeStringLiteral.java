package com.piglatin.y.domain.ast.expressions;

import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeStringLiteral extends NodeExpression {

    private String value;

    public NodeStringLiteral() {
        this("", 0, 0);
    }

    public NodeStringLiteral(int line, int column) {
        this("", line, column);
    }

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
        return "String Literal";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitStringLiteral(this);
    }
}
