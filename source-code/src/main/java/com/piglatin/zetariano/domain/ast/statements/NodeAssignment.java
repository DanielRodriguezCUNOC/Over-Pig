package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.expressions.NodeLvalue;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeAssignment extends NodeStatement {
    private NodeLvalue lvalue;
    private String operator;
    private NodeExpression expression;

    public NodeAssignment(NodeLvalue lvalue, String operator, NodeExpression expression, int line, int column) {
        super(line, column);
        this.lvalue = lvalue;
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Assignment: " + operator;
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
