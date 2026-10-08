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

    private List<NodeClassDeclaration> classes;

    public NodeProgram(int line, int column) {
        super(line, column);
        this.classes = new ArrayList<>();
    }

    public NodeProgram(List<NodeClassDeclaration> classes, int line, int column) {
        super(line, column);
        this.classes = classes;

    }

    @Override
    public String toString() {
        return "Program: " + classes.size() + " classes";
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
