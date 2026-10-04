package com.piglatin.y.domain.ast.expressions;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeUnaryOperation extends NodeExpression {

    private String operator;
    private ASTNode operand;

    public NodeUnaryOperation() {
        this("", null, 0, 0);
    }

    public NodeUnaryOperation(int line, int column) {
        this("", null, line, column);
    }

    public NodeUnaryOperation(String operator, ASTNode operand, int line, int column) {
        super(line, column);
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public String toString() {
        return operator + operand.toString();
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
