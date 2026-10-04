package com.piglatin.piglatin.domain.ast.nodes.expression;

import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a unary operation on a single expression
 */

@Getter
@Setter
public class NodeUnaryOperation extends ASTNode {

    //* The operator of the unary operation
    private String operator;

    //* The operand of the unary expression
    private ASTNode operand;

    public NodeUnaryOperation() {
        this(null, null, 0, 0);
    }

    public NodeUnaryOperation(int line, int column) {
        this(null, null, line, column);
    }

    public NodeUnaryOperation(String operator, ASTNode operand, int line, int column) {
        super(line, column);
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public String toString() {
        return operator + " " + operand.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Unary Operation";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitUnaryOperation(this);
    }
}
