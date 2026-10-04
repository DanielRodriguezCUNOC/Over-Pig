package com.piglatin.y.domain.ast.expressions;

import com.piglatin.y.domain.ast.principal.ASTNode;

public abstract class NodeExpression extends ASTNode {

    public NodeExpression() {
        super();
    }

    public NodeExpression(int line, int column) {
        super(line, column);
    }
}
