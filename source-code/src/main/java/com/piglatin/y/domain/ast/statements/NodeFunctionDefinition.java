package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeFunctionDefinition extends ASTNode {

    private String name;
    private List<NodeParameter> parameters;
    private String returnType;
    private NodeBlock block;

    public NodeFunctionDefinition() {
        this("", new ArrayList<>(), null, null, 0, 0);
    }

    public NodeFunctionDefinition(int line, int column) {
        this("", new ArrayList<>(), null, null, line, column);
    }

    public NodeFunctionDefinition(String name, List<NodeParameter> parameters, String returnType, NodeBlock block, int line, int column) {
        super(line, column);
        this.name = name;
        this.parameters = parameters != null ? parameters : new ArrayList<>();
        this.returnType = returnType;
        this.block = block;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("definir ").append(name).append("(");
        for (int i = 0; i < parameters.size(); i++) {
            sb.append(parameters.get(i).toString());
            if (i < parameters.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        if (returnType != null && !returnType.isEmpty()) {
            sb.append(" -> ").append(returnType);
        }
        sb.append(":\n").append(block != null ? block.toString() : "");
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Function Definition";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitFunctionDefinition(this);
    }
}
