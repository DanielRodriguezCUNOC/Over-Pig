package com.piglatin.y.domain.ast.statements;

import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeBlock extends ASTNode {

    private List<ASTNode> instructions;

    public NodeBlock() {
        this(new ArrayList<>(), 0, 0);
    }

    public NodeBlock(int line, int column) {
        this(new ArrayList<>(), line, column);
    }

    public NodeBlock(List<ASTNode> instructions, int line, int column) {
        super(line, column);
        this.instructions = instructions != null ? instructions : new ArrayList<>();
    }

    public void addInstruction(ASTNode instruction) {
        if (instructions == null) {
            this.instructions = new ArrayList<>();
        }
        this.instructions.add(instruction);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ASTNode instruction : instructions) {
            sb.append("    ").append(instruction.toString()).append("\n");
        }
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
