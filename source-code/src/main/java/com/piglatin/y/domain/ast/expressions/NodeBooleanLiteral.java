package com.piglatin.y.domain.ast.expressions;

import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeBooleanLiteral extends NodeExpression {

    private boolean value;

    public NodeBooleanLiteral() {
        this(false, 0, 0);
    }

    public NodeBooleanLiteral(int line, int column) {
        this(false, line, column);
    }

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
        return "Boolean Literal";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitBooleanLiteral(this);
    }
}
