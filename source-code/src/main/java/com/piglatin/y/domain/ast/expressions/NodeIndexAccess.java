package com.piglatin.y.domain.ast.expressions;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeIndexAccess extends ASTNode {

    private ASTNode indexExpression;
    private ASTNode currentNode;

    public NodeIndexAccess() {
        this(null, null, 0, 0);
    }

    public NodeIndexAccess(int line, int column) {
        this(null, null, line, column);
    }

    public NodeIndexAccess(ASTNode currentNode, ASTNode indexExpression, int line, int column) {
        super(line, column);
        this.currentNode = currentNode;
        this.indexExpression = indexExpression;
    }

    @Override
    public String toString() {
        return "[" + indexExpression.toString() + "]";
    }

    @Override
    public String getTipoNodo() {
        return "Index Access";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitIndexAccess(this);
    }
}
