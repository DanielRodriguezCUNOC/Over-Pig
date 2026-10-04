package com.piglatin.y.domain.ast.expressions;

import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeIntegerLiteral extends NodeExpression {

    private int value;

    public NodeIntegerLiteral() {
        this(0, 0, 0);
    }

    public NodeIntegerLiteral(int line, int column) {
        this(0, line, column);
    }

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
        return "Integer Literal";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitIntegerLiteral(this);
    }
}
