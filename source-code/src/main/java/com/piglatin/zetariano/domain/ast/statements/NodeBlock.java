package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeBlock extends NodeStatement {
    private List<ASTNode> instructions;

    public NodeBlock(List<ASTNode> instructions, int line, int column) {
        super(line, column);
        this.instructions = instructions != null ? instructions : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Block";
    }

    @Override
    public String getTipoNodo() {
        return "Block";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitBlock(this);
    }
}
