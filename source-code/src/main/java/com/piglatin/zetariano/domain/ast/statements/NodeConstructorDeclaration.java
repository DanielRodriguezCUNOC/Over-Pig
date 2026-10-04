package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeConstructorDeclaration extends NodeStatement {
    private boolean isPublic;
    private String name;
    private List<NodeParameter> parameters;
    private NodeBlock body;

    public NodeConstructorDeclaration(boolean isPublic, String name, List<NodeParameter> parameters, NodeBlock body, int line, int column) {
        super(line, column);
        this.isPublic = isPublic;
        this.name = name;
        this.parameters = parameters != null ? parameters : new ArrayList<>();
        this.body = body;
    }

    @Override
    public String toString() {
        return "Constructor: " + name;
    }

    @Override
    public String getTipoNodo() {
        return "ConstructorDeclaration";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitConstructorDeclaration(this);
    }
}
