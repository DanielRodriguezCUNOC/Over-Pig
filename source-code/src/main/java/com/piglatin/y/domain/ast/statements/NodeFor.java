package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeFor extends ASTNode {

    private ASTNode init;
    private ASTNode condition;
    private ASTNode update;
    private NodeBlock block;

    public NodeFor() {
        this(null, null, null, null, 0, 0);
    }

    public NodeFor(int line, int column) {
        this(null, null, null, null, line, column);
    }

    public NodeFor(ASTNode init, ASTNode condition, ASTNode update, NodeBlock block, int line, int column) {
        super(line, column);
        this.init = init;
        this.condition = condition;
        this.update = update;
        this.block = block;
    }

    @Override
    public String toString() {
        return "para (" + (init != null ? init.toString() : "") + "; " + 
               (condition != null ? condition.toString() : "") + "; " + 
               (update != null ? update.toString() : "") + "):\n" + 
               (block != null ? block.toString() : "");
    }

    @Override
    public String getTipoNodo() {
        return "For";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitFor(this);
    }
}
