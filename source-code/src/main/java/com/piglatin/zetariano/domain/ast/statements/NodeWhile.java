package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeWhile extends NodeStatement {
    private NodeExpression condition;
    private ASTNode block;

    public NodeWhile(NodeExpression condition, ASTNode block, int line, int column) {
        super(line, column);
        this.condition = condition;
        this.block = block;
    }

    @Override
    public String toString() {
        return "While";
    }

    @Override
    public String getTipoNodo() {
        return "While";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitWhile(this);
    }
}
