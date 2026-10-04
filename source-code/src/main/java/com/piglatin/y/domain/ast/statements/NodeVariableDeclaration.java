package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeVariableDeclaration extends ASTNode {

    private String type;
    private String name;
    private ASTNode initializer;

    public NodeVariableDeclaration() {
        this("", "", null, 0, 0);
    }

    public NodeVariableDeclaration(int line, int column) {
        this("", "", null, line, column);
    }

    public NodeVariableDeclaration(String type, String name, ASTNode initializer, int line, int column) {
        super(line, column);
        this.type = type;
        this.name = name;
        this.initializer = initializer;
    }

    @Override
    public String toString() {
        return type + " " + name + (initializer != null ? " = " + initializer.toString() : "");
    }

    @Override
    public String getTipoNodo() {
        return "Variable Declaration";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitVariableDeclaration(this);
    }
}
