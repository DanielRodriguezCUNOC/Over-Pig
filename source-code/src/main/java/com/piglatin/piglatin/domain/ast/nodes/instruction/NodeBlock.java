package com.piglatin.piglatin.domain.ast.nodes.instruction;

import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a block of instructions production:
 * LEFT_BRACE instruction* RIGHT_BRACE
 */
@Getter
@Setter
public class NodeBlock extends ASTNode {

    //* List of instructions contained in this block
    private List<ASTNode> instructions;

    public NodeBlock() {
        this(new ArrayList<>(), 0, 0);
    }

    public NodeBlock(int line, int column) {
        this(new ArrayList<>(), line, column);
    }

    public NodeBlock(List<ASTNode> instructions, int line, int column) {
        super(line, column);
        this.instructions = instructions !=  null ? instructions : new ArrayList<>();
    }

    public void addInstruction(ASTNode instruction) {
        if (instruction == null) this.instructions = new ArrayList<>();
        this.instructions.add(instruction);
    }

    public boolean hasInstructions() {
        return instructions != null && !instructions.isEmpty();
    }

    public int getInstructionCount(){
        return instructions != null ? instructions.size() : 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        if (instructions != null && !instructions.isEmpty()) {
            for (ASTNode instruction : instructions) {
                sb.append(instruction.toString());
                //* add space between instructions best UX :)
                sb.append(" ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String getTipoNodo() {
        return "Block";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitBlock(this);
    }
}
