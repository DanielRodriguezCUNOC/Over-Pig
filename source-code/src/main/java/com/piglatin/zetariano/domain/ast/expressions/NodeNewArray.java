package com.piglatin.zetariano.domain.ast.expressions;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NodeNewArray extends NodeExpression {
    private String type;
    private List<NodeExpression> dimensions;

    public NodeNewArray(String type, List<NodeExpression> dimensions, int line, int column) {
        super(line, column);
        this.type = type;
        this.dimensions = dimensions;
    }

    @Override
    public String toString() {
        return "NewArray: " + type;
    }

    @Override
    public String getTipoNodo() {
        return "NewArray";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitNewArray(this);
    }
}
