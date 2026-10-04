package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NodePrint extends NodeStatement {
    private List<NodeExpression> expressions;
    private boolean newline;

    public NodePrint(List<NodeExpression> expressions, boolean newline, int line, int column) {
        super(line, column);
        this.expressions = expressions;
        this.newline = newline;
    }

    @Override
    public String toString() {
        return newline ? "Println" : "Print";
    }

    @Override
    public String getTipoNodo() {
        return "Print";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitPrint(this);
    }
}
