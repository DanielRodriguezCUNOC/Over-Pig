package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.expressions.NodeArrayInitializer;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeArrayDeclaration extends NodeStatement {
    private String type;
    private String name;
    private int dimensions;
    private NodeExpression size;
    private NodeArrayInitializer initializer;

    public NodeArrayDeclaration(String type, String name, int dimensions, NodeExpression size, NodeArrayInitializer initializer, int line, int column) {
        super(line, column);
        this.type = type;
        this.name = name;
        this.dimensions = dimensions;
        this.size = size;
        this.initializer = initializer;
    }

    @Override
    public String toString() {
        return "ArrayDecl: " + type + "[] " + name;
    }

    @Override
    public String getTipoNodo() {
        return "ArrayDeclaration";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitArrayDeclaration(this);
    }
}
