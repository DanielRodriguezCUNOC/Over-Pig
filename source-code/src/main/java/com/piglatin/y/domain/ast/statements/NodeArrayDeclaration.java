package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeArrayDeclaration extends ASTNode {

    private String type;
    private String name;
    private List<ASTNode> dimensions;
    private ASTNode initializer;

    public NodeArrayDeclaration() {
        this("", "", new ArrayList<>(), null, 0, 0);
    }

    public NodeArrayDeclaration(int line, int column) {
        this("", "", new ArrayList<>(), null, line, column);
    }

    public NodeArrayDeclaration(String type, String name, List<ASTNode> dimensions, ASTNode initializer, int line, int column) {
        super(line, column);
        this.type = type;
        this.name = name;
        this.dimensions = dimensions != null ? dimensions : new ArrayList<>();
        this.initializer = initializer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type).append(" ").append(name);
        for (ASTNode dim : dimensions) {
            sb.append("[").append(dim.toString()).append("]");
        }
        if (initializer != null) {
            sb.append(" = ").append(initializer.toString());
        }
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Array Declaration";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitArrayDeclaration(this);
    }
}
