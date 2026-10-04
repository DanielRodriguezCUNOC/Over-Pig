package com.piglatin.piglatin.domain.ast.nodes.instruction;


import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a do-while statement poduction:
 * FACERE block DUM (booleanExpression) ;
 */
@Getter
@Setter
public class NodeDoWhile extends ASTNode {

    //* Block of instructions to execute in each iteration
    private NodeBlock block;

    //* Condition that determines whether to continue looping
    private ASTNode condition;

    public NodeDoWhile() {
        this(null, null, 0, 0);
    }

    public NodeDoWhile(int line, int column) {
        this(null, null, line, column);
    }

    public NodeDoWhile(NodeBlock block,
                       ASTNode condition,
                       int line, int column) {
        super(line, column);
        this.block = block;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "facere " + block.toString() + "dum (" +  condition.toString() + ");";
    }

    @Override
    public String getTipoNodo() {
        return "Do-While Statement";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitDoWhile(this);
    }
}
