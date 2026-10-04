package com.piglatin.piglatin.domain.ast.nodes.instruction;

import com.piglatin.piglatin.domain.ast.nodes.lvalue.NodeLvalue;
import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an assigment statement production:
 * lvalue = expression ;
 */
@Getter
@Setter
public class NodeAssignment extends ASTNode {

    /**
     * The left-hand side of the assigment
     * This is the target where the value will be stored
     */
    private NodeLvalue lvalue;

    /**
     * The right-hand side of the assigment
     * This is the expression that produces the value to be stored
     */
    private ASTNode expression;

    public NodeAssignment(){
        this(null, null, 0, 0);
    }

    public NodeAssignment(int line, int column){
        this(null, null, line, column);
    }

    public NodeAssignment(NodeLvalue lvalue, ASTNode expression, int line, int column) {
        super(line, column);
        this.lvalue = lvalue;
        this.expression = expression;
    }


    @Override
    public String toString() {
        return lvalue.toString() + " = " + expression.toString() + ";";
    }

    @Override
    public String getTipoNodo() {
        return "Assignment";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitAssignment(this);
    }
}
