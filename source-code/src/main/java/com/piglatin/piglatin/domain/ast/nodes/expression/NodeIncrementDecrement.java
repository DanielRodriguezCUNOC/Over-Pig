package com.piglatin.piglatin.domain.ast.nodes.expression;

import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an increment or decrement operation
 */
@Getter
@Setter
public class NodeIncrementDecrement extends ASTNode {

    //* The operand that is incremented or decremented
    private ASTNode operand;

    //* The type of operation
    private String operation;

    //* if its postfix
    private boolean postfix;

    public NodeIncrementDecrement() {
        this(null, null, false, 0, 0);
    }

    public NodeIncrementDecrement(int line, int column) {
        this(null, null, false, line, column);
    }

    public NodeIncrementDecrement(ASTNode operand, String operation, boolean postfix, int line, int column) {
        super(line, column);
        this.operand = operand;
        this.operation = operation;
        this.postfix = postfix;
    }

    @Override
    public String toString() {
        return operand.toString() + operation;
    }

    @Override
    public String getTipoNodo() {
        return "Increment/Decrement";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitIncrementDecrement(this);
    }
}
