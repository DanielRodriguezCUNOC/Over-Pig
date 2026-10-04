package com.piglatin.zetariano.domain.ast.expressions;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NodeMethodCall extends NodeExpression {
    private NodeLvalue target;
    private String methodName;
    private List<NodeExpression> arguments;

    public NodeMethodCall(NodeLvalue target, String methodName, List<NodeExpression> arguments, int line, int column) {
        super(line, column);
        this.target = target;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "Call: " + methodName;
    }

    @Override
    public String getTipoNodo() {
        return "MethodCall";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitMethodCall(this);
    }
}
