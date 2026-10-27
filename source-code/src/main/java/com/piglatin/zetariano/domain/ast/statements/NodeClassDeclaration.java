package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.enums.AccessModifier;
import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeClassDeclaration extends NodeStatement {
    private AccessModifier modifier;
    private String name;
    private List<ASTNode> members;
    private String superClass;

    public NodeClassDeclaration(AccessModifier modifier, String name, String superClass, List<ASTNode> members, int line, int column) {
        super(line, column);
        this.modifier = modifier;
        this.name = name;
        this.superClass = superClass;
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
