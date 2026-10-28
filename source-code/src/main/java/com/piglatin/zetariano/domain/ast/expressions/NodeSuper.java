package com.piglatin.zetariano.domain.ast.expressions;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeSuper extends NodeLvalue {

    public NodeSuper(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return "Super";
    }

    @Override
    public String getTipoNodo() {
        return "Super";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitSuper(this);
    }
}
