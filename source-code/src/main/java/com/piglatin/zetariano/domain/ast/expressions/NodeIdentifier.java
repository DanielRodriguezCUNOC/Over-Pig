package com.piglatin.zetariano.domain.ast.expressions;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeIdentifier extends NodeLvalue {
    private String name;

    public NodeIdentifier(String name, int line, int column) {
        super(line, column);
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID: " + name;
    }

    @Override
    public String getTipoNodo() {
        return "Identifier";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitIdentifier(this);
    }
}
