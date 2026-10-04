package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeMethodDeclaration extends NodeStatement {
    private boolean isPublic;
    private String returnType;
    private String name;
    private List<NodeParameter> parameters;
    private NodeBlock body;

    public NodeMethodDeclaration(boolean isPublic, String returnType, String name, List<NodeParameter> parameters, NodeBlock body, int line, int column) {
        super(line, column);
        this.isPublic = isPublic;
        this.returnType = returnType;
        this.name = name;
        this.parameters = parameters != null ? parameters : new ArrayList<>();
        this.body = body;
    }

    @Override
    public String toString() {
        return "Method: " + returnType + " " + name;
    }

    @Override
    public String getTipoNodo() {
        return "MethodDeclaration";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitMethodDeclaration(this);
    }
}
