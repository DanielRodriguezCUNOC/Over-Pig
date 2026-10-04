package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeVariableDeclaration extends NodeStatement {
    private String type;
    private String name;
    private NodeExpression initializer;

    public NodeVariableDeclaration(String type, String name, NodeExpression initializer, int line, int column) {
        super(line, column);
        this.type = type;
        this.name = name;
        this.initializer = initializer;
    }

    @Override
    public String toString() {
        return "VarDecl: " + type + " " + name;
    }

    @Override
    public String getTipoNodo() {
        return "VariableDeclaration";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitVariableDeclaration(this);
    }
}
