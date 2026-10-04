package com.piglatin.piglatin.domain.ast.nodes.instruction;

import com.piglatin.piglatin.domain.ast.principal.ASTNode;
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
