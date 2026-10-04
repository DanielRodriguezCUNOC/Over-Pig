package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeWhile extends ASTNode {

    private ASTNode condition;
    private NodeBlock block;

    public NodeWhile() {
        this(null, null, 0, 0);
    }

    public NodeWhile(int line, int column) {
        this(null, null, line, column);
    }

    public NodeWhile(ASTNode condition, NodeBlock block, int line, int column) {
        super(line, column);
        this.condition = condition;
        this.block = block;
    }

    @Override
    public String toString() {
        return "mientras (" + condition.toString() + ") hacer\n" + (block != null ? block.toString() : "");
    }

    @Override
    public String getTipoNodo() {
        return "While";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitWhile(this);
    }
}
