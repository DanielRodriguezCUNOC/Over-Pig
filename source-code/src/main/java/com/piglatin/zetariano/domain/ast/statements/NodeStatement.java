package com.piglatin.zetariano.domain.ast.statements;

import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class NodeStatement extends ASTNode {
    public NodeStatement(int line, int column) {
        super(line, column);
    }
}
