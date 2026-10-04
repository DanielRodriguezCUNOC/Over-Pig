package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeDoWhile extends NodeStatement {
    private ASTNode block;
    private NodeExpression condition;

    public NodeDoWhile(ASTNode block, NodeExpression condition, int line, int column) {
        super(line, column);
        this.block = block;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "DoWhile";
    }

    @Override
    public String getTipoNodo() {
        return "DoWhile";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitDoWhile(this);
    }
}
