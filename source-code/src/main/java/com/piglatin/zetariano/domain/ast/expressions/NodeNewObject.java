package com.piglatin.zetariano.domain.ast.expressions;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NodeNewObject extends NodeExpression {
    private String className;
    private List<NodeExpression> arguments;

    public NodeNewObject(String className, List<NodeExpression> arguments, int line, int column) {
        super(line, column);
        this.className = className;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "New: " + className;
    }

    @Override
    public String getTipoNodo() {
        return "NewObject";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitNewObject(this);
    }
}
