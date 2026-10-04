package com.piglatin.y.domain.ast.expressions;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeFunctionCall extends NodeExpression {

    private String functionName;
    private List<ASTNode> arguments;

    public NodeFunctionCall() {
        this("", new ArrayList<>(), 0, 0);
    }

    public NodeFunctionCall(int line, int column) {
        this("", new ArrayList<>(), line, column);
    }

    public NodeFunctionCall(String functionName, List<ASTNode> arguments, int line, int column) {
        super(line, column);
        this.functionName = functionName;
        this.arguments = arguments != null ? arguments : new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(functionName).append("(");
        for (int i = 0; i < arguments.size(); i++) {
            sb.append(arguments.get(i).toString());
            if (i < arguments.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Function Call";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitFunctionCall(this);
    }
}
