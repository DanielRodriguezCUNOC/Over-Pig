package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeDoWhile extends ASTNode {

    private NodeBlock block;
    private ASTNode condition;

    public NodeDoWhile() {
        this(null, null, 0, 0);
    }

    public NodeDoWhile(int line, int column) {
        this(null, null, line, column);
    }

    public NodeDoWhile(NodeBlock block, ASTNode condition, int line, int column) {
        super(line, column);
        this.block = block;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "hacer:\n" + (block != null ? block.toString() : "") + "mientras (" + condition.toString() + ")";
    }

    @Override
    public String getTipoNodo() {
        return "Do While";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitDoWhile(this);
    }
}
