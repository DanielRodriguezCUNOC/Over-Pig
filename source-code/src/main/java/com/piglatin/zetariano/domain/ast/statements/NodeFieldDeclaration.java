package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeFieldDeclaration extends NodeStatement {
    private String type;
    private String name;
    private NodeExpression initializer;
    private boolean isArray;
    private int dimensions;

    public NodeFieldDeclaration(String type, String name, NodeExpression initializer, boolean isArray, int dimensions, int line, int column) {
        super(line, column);
        this.type = type;
        this.name = name;
        this.initializer = initializer;
        this.isArray = isArray;
        this.dimensions = dimensions;
    }

    @Override
    public String toString() {
        return "Field: " + type + " " + name;
    }

    @Override
    public String getTipoNodo() {
        return "FieldDeclaration";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitFieldDeclaration(this);
    }
}
