package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.expressions.NodeExpression;
import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeIf extends NodeStatement {
    private NodeExpression condition;
    private ASTNode thenBlock;
    private ASTNode elseBlock;

    public NodeIf(NodeExpression condition, ASTNode thenBlock, ASTNode elseBlock, int line, int column) {
        super(line, column);
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public String toString() {
        return "If";
    }

    @Override
    public String getTipoNodo() {
        return "If";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitIf(this);
    }
}
