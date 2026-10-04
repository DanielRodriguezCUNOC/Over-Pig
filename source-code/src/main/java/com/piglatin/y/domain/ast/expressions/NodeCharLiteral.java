package com.piglatin.y.domain.ast.expressions;

import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeCharLiteral extends NodeExpression {

    private char value;

    public NodeCharLiteral() {
        this('\0', 0, 0);
    }

    public NodeCharLiteral(int line, int column) {
        this('\0', line, column);
    }

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
        return "Char Literal";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitCharLiteral(this);
    }
}
