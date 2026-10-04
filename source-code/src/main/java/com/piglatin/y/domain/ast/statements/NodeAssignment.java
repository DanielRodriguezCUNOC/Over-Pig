package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeAssignment extends ASTNode {

    private ASTNode lvalue;
    private String operator; // =, ++, --
    private ASTNode expression; // can be null for ++ and --

    public NodeAssignment() {
        this(null, "", null, 0, 0);
    }

    public NodeAssignment(int line, int column) {
        this(null, "", null, line, column);
    }

    public NodeAssignment(ASTNode lvalue, String operator, ASTNode expression, int line, int column) {
        super(line, column);
        this.lvalue = lvalue;
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public String toString() {
        if (expression == null) {
            return lvalue.toString() + operator;
        }
        return lvalue.toString() + " " + operator + " " + expression.toString();
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
