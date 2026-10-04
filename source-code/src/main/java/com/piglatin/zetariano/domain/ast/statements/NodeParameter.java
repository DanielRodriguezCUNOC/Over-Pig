package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeParameter extends ASTNode {
    private String type;
    private String name;
    private boolean isArray;

    public NodeParameter(String type, String name, boolean isArray, int line, int column) {
        super(line, column);
        this.type = type;
        this.name = name;
        this.isArray = isArray;
    }

    @Override
    public String toString() {
        return type + (isArray ? "[] " : " ") + name;
    }

    @Override
    public String getTipoNodo() {
        return "Parameter";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitParameter(this);
    }
}
