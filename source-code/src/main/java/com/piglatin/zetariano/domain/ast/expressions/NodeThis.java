package com.piglatin.zetariano.domain.ast.expressions;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.AllArgsConstructor;

public class NodeThis extends NodeLvalue{

    public NodeThis(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return "This";
    }

    @Override
    public String getTipoNodo() {
        return "This";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitThis(this);
    }
}
