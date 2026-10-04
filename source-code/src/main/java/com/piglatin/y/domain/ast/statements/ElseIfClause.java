package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElseIfClause {

    private ASTNode condition;
    private NodeBlock block;

    public ElseIfClause(ASTNode condition, NodeBlock block) {
        this.condition = condition;
        this.block = block;
    }
}
