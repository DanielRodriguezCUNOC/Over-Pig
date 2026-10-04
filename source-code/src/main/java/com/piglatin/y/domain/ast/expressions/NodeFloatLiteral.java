package com.piglatin.y.domain.ast.expressions;

import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeFloatLiteral extends NodeExpression {

    private double value;

    public NodeFloatLiteral() {
        this(0.0, 0, 0);
    }

    public NodeFloatLiteral(int line, int column) {
        this(0.0, line, column);
    }

    public NodeFloatLiteral(double value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String getTipoNodo() {
        return "Float Literal";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitFloatLiteral(this);
    }
}
