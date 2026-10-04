package com.piglatin.zetariano.domain.ast.expressions;

import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class NodeExpression extends ASTNode {
    public NodeExpression(int line, int column) {
        super(line, column);
    }
}
