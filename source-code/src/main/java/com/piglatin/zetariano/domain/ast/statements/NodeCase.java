package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeCase extends NodeStatement {
    private NodeExpression expression;
    private NodeBlock block;

    public NodeCase(NodeExpression expression, NodeBlock block, int line, int column) {
        super(line, column);
        this.expression = expression;
        this.block = block;
    }

    @Override
    public String toString() {
        return "Case";
    }

    @Override
    public String getTipoNodo() {
        return "Case";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitCase(this);
    }
}
