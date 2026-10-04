package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeFor extends NodeStatement {
    private ASTNode initializer;
    private NodeExpression condition;
    private ASTNode update;
    private ASTNode block;

    public NodeFor(ASTNode initializer, NodeExpression condition, ASTNode update, ASTNode block, int line, int column) {
        super(line, column);
        this.initializer = initializer;
        this.condition = condition;
        this.update = update;
        this.block = block;
    }

    @Override
    public String toString() {
        return "For";
    }

    @Override
    public String getTipoNodo() {
        return "For";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitFor(this);
    }
}
