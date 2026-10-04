package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeIf extends ASTNode {

    private ASTNode condition;
    private NodeBlock thenBlock;
    private List<ElseIfClause> elseIfClauses;
    private NodeBlock elseBlock;

    public NodeIf() {
        this(null, null, new ArrayList<>(), null, 0, 0);
    }

    public NodeIf(int line, int column) {
        this(null, null, new ArrayList<>(), null, line, column);
    }

    public NodeIf(ASTNode condition, NodeBlock thenBlock, List<ElseIfClause> elseIfClauses, NodeBlock elseBlock, int line, int column) {
        super(line, column);
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseIfClauses = elseIfClauses != null ? elseIfClauses : new ArrayList<>();
        this.elseBlock = elseBlock;
    }

    public void addElseIfClause(ASTNode condition, NodeBlock block) {
        if (elseIfClauses == null) {
            this.elseIfClauses = new ArrayList<>();
        }
        this.elseIfClauses.add(new ElseIfClause(condition, block));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("si (").append(condition.toString()).append(") entonces\n").append(thenBlock.toString());
        if (elseIfClauses != null) {
            for (ElseIfClause clause : elseIfClauses) {
                sb.append("sino (").append(clause.getCondition().toString()).append(") entonces\n").append(clause.getBlock().toString());
            }
        }
        if (elseBlock != null) {
            sb.append("contrario\n").append(elseBlock.toString());
        }
        return sb.toString();
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
