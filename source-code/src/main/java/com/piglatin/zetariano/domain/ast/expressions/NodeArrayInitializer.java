package com.piglatin.zetariano.domain.ast.expressions;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NodeArrayInitializer extends NodeExpression {
    private List<NodeExpression> elements;

    public NodeArrayInitializer(List<NodeExpression> elements, int line, int column) {
        super(line, column);
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "ArrayInitializer";
    }

    @Override
    public String getTipoNodo() {
        return "ArrayInitializer";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitArrayInitializer(this);
    }
}
