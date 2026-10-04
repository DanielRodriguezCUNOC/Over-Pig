package com.piglatin.zetariano.domain.ast.principal;

import com.piglatin.zetariano.domain.ast.statements.NodeImport;
import com.piglatin.zetariano.domain.ast.statements.NodeClassDeclaration;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeProgram extends ASTNode {

    private NodeClassDeclaration classDeclaration;

    public NodeProgram(int line, int column) {
        super(line, column);
    }

    public NodeProgram(NodeClassDeclaration classDeclaration, int line, int column) {
        super(line, column);
        this.classDeclaration = classDeclaration;
    }

    @Override
    public String toString() {
        return "Program: " + (classDeclaration != null ? classDeclaration.getName() : "null");
    }

    @Override
    public String getTipoNodo() {
        return "Program";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitProgram(this);
    }
}
