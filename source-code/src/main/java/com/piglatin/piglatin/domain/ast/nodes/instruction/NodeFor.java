package com.piglatin.piglatin.domain.ast.nodes.instruction;


import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a for statement production:
 * PER (forInit; forCondition; forUpdate?) block
 */

@Getter
@Setter
public class NodeFor extends ASTNode {

    //* Initialization part of the for loop
    private ASTNode initialization;

    //* The condition that determines whether to continue looping
    private ASTNode condition;

    //* Update expression executed after each iteration
    private ASTNode update;

    //* Block of instructions to execute in each iteration
    private NodeBlock block;

    public NodeFor() {
        this(null, null, null, null, 0, 0);
    }

    public NodeFor(int line, int column) {
        this(null, null, null, null, line, column);
    }

    public NodeFor(ASTNode initialization,
                   ASTNode condition,
                   ASTNode update, NodeBlock block,
                   int line, int column) {
        super(line, column);
        this.initialization = initialization;
        this.condition = condition;
        this.update = update;
        this.block = block;
    }

    public boolean hasUpdate(){
        return update != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("per (");

        //* Initialization
        if (initialization != null) sb.append(initialization);

        sb.append("; ");

        //* Condition
        if(condition != null) sb.append(condition);
        sb.append("; ");

        //* Update
        if(update != null) sb.append(update);
        sb.append(") ");

        //* Block
        if(block != null) sb.append(block);

        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "For Statement";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitFor(this);
    }
}
