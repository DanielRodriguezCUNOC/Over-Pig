package com.piglatin.zetariano.domain.ast.expressions;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeIndexAccess extends NodeLvalue {
    private NodeLvalue target;
    private NodeExpression index;

    public NodeIndexAccess(NodeLvalue target, NodeExpression index, int line, int column) {
        super(line, column);
        this.target = target;
        this.index = index;
    }

    @Override
    public String toString() {
        return "IndexAccess";
    }

    @Override
    public String getTipoNodo() {
        return "IndexAccess";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitIndexAccess(this);
    }
}
