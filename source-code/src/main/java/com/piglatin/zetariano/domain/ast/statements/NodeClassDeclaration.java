package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeClassDeclaration extends NodeStatement {
    private boolean isPublic;
    private String name;
    private List<ASTNode> members;

    public NodeClassDeclaration(boolean isPublic, String name, List<ASTNode> members, int line, int column) {
        super(line, column);
        this.isPublic = isPublic;
        this.name = name;
        this.members = members != null ? members : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Class: " + name;
    }

    @Override
    public String getTipoNodo() {
        return "ClassDeclaration";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitClassDeclaration(this);
    }
}
