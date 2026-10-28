package com.piglatin.zetariano.domain.ast.expressions;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeRead extends NodeExpression {

    public NodeRead(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return "Read";
    }

    @Override
    public String getTipoNodo() {
        return "Read";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitRead(this);
    }
}
