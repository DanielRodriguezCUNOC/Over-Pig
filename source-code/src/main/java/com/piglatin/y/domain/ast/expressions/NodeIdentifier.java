package com.piglatin.y.domain.ast.expressions;

import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeIdentifier extends NodeExpression {

    private String id;

    public NodeIdentifier() {
        this("", 0, 0);
    }

    public NodeIdentifier(int line, int column) {
        this("", line, column);
    }

    public NodeIdentifier(String id, int line, int column) {
        super(line, column);
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
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
