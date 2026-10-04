package com.piglatin.zetariano.domain.ast.expressions;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeFieldAccess extends NodeLvalue {
    private NodeLvalue target;
    private String fieldName;

    public NodeFieldAccess(NodeLvalue target, String fieldName, int line, int column) {
        super(line, column);
        this.target = target;
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "FieldAccess: ." + fieldName;
    }

    @Override
    public String getTipoNodo() {
        return "FieldAccess";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitFieldAccess(this);
    }
}
