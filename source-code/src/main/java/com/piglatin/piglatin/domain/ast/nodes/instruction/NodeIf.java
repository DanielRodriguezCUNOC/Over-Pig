package com.piglatin.piglatin.domain.ast.nodes.instruction;


import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an if statement production:
 * SI (booleanExpression) block (elseIfClause)* (elseIfClause)? FINIS ;
 */

@Getter
@Setter
public class NodeIf extends ASTNode {

    //* Condition expression that determines wich branch to execute
    private ASTNode condition;

    //* Block of instructions to execute when is true
    private NodeBlock thenBlock;

    //* List of else-if clauses
    private List<ElseIfClause> elseIfClauses;

    //* The else block to execute if no condition is true
    private NodeBlock elseBlock;

    public NodeIf(){
        this(null, null, new ArrayList<>(), null, 0, 0);
    }

    public NodeIf(int line, int column){
        this(null, null, new ArrayList<>(), null, line, column);
    }

    public void addElseIfClause(ASTNode condition,
                                NodeBlock block){
        if(elseIfClauses == null) this.elseIfClauses = new ArrayList<>();
        this.elseIfClauses.add(new ElseIfClause(condition, block));
    }

    public boolean hasElseIfClauses(){
        return elseIfClauses != null && !elseIfClauses.isEmpty();
    }

    public boolean hasElseBlock(){
        return elseBlock != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("si (").append(condition.toString()).append(") ");
        sb.append(thenBlock.toString());

        // Else-if clauses
        if(elseIfClauses != null){
            for (ElseIfClause elseIf : elseIfClauses){
                sb.append(" aliter (").append(elseIf.getCondition().toString()).append(") ");
                sb.append(elseIf.getBlock().toString());
            }
        }
        // Else block
        if (elseBlock != null) {
            sb.append(" aliter ").append(elseBlock.toString());
        }

        sb.append(" finis ;");
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "If Statement";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitIf(this);
    }

    public NodeIf(
            ASTNode condition, NodeBlock thenBlock,
            List<ElseIfClause> elseIfClauses, NodeBlock elseBlock,
            int line, int column){
        super(line, column);
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseIfClauses = elseIfClauses != null ? elseIfClauses : new ArrayList<>();
        this.elseBlock = elseBlock;
    }
}
