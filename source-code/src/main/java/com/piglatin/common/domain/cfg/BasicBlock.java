package com.piglatin.common.domain.cfg;

import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Basic Block in the Control Flow Graph
 * Now "what a fuck is a Basic Block" you say. Well I don't know XD, nah it's a joke:
 * A Basic Block is a linear sequence of instruction with a single entry point and... yes! a single exit point
 * With this lets go to make this shit
 */
@Getter
@Setter
public class BasicBlock<T> {
    private final int id;
    private final List<T> instructions;
    private final List<BasicBlock<T>> predecessors;
    private final List<BasicBlock<T>> successors;
    private boolean isEntry;
    private boolean isExit;

    public BasicBlock(int id) {
        this.id = id;
        this.instructions = new ArrayList<>();
        this.predecessors = new ArrayList<>();
        this.successors = new ArrayList<>();
        this.isEntry = false;
        this.isExit = false;
    }

    public void addInstruction(T instruction) {
        this.instructions.add(instruction);
    }

    public void addSuccessor(BasicBlock<T> successor) {
        if (!this.successors.contains(successor)) {
            this.successors.add(successor);
            successor.getPredecessors().add(this);
        }
    }

    @Override
    public String toString() {
        return "Block_" + id + (isEntry ? " (ENTRY)" : "") + (isExit ? " (EXIT)" : "");
    }
}
