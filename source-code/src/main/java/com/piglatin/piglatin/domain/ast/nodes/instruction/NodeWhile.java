package com.piglatin.piglatin.domain.ast.nodes.instruction;


import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a while statement production:
 * DUM (booleanExpression) block FINIS ;
 */
@Getter
@Setter
public class NodeWhile extends ASTNode {

    //* Condition that determines wether to continue looping
    private ASTNode condition;

    //* Block of instruction to execute in each iteration
    private NodeBlock block;

    public NodeWhile() {
        this(null, null, 0, 0);
    }

    public NodeWhile(int line, int column) {
        this(null, null, line, column);
    }

    public NodeWhile(ASTNode condition,
                     NodeBlock block,
                     int line, int column) {
        super(line, column);
        this.condition = condition;
        this.block = block;
    }

    @Override
    public String toString() {
        return "dum (" + condition.toString() + ") " + block.toString() + "finis ;";
    }

    @Override
    public String getTipoNodo() {
        return "While Statement";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitWhile(this);
    }
}
