package com.piglatin.piglatin.domain.ast.nodes.instruction;


import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a return statement production:
 * REDDERE expression ;
 */

@Getter
@Setter
public class NodeReturn extends ASTNode {

    //* Expression whose value will be returne
    private ASTNode expression;

    public NodeReturn() {
        this(null, 0, 0);
    }

    public NodeReturn(int line, int column) {
        this(null, line, column);
    }

    public  NodeReturn(ASTNode expression, int line, int column) {
        super(line, column);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "reddere " + expression.toString() + ";";
    }

    @Override
    public String getTipoNodo() {
        return "Return";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitReturn(this);
    }
}
