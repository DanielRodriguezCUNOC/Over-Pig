package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeStructureDefinition extends ASTNode {

    private String name;
    private List<NodeFieldDeclaration> fields;

    public NodeStructureDefinition() {
        this("", new ArrayList<>(), 0, 0);
    }

    public NodeStructureDefinition(int line, int column) {
        this("", new ArrayList<>(), line, column);
    }

    public NodeStructureDefinition(String name, List<NodeFieldDeclaration> fields, int line, int column) {
        super(line, column);
        this.name = name;
        this.fields = fields != null ? fields : new ArrayList<>();
    }

    public void addField (NodeFieldDeclaration field) {
        if (field != null) this.fields.add(field);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("estructura " + name + " {\n");

        for (NodeFieldDeclaration field : fields) {
            sb.append("    ").append(field.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Structure Definition";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitStructureDefinition(this);
    }
}
