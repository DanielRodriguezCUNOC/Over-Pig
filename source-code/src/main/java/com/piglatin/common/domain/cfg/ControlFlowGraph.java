package com.piglatin.common.domain.cfg;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the complete Directed Control Flow Graph.
 */
@Getter
@Setter
public class ControlFlowGraph<T> {

    private final BasicBlock<T> entryBlock;
    private final BasicBlock<T> exitBlock;
    private final List<BasicBlock<T>> blocks;

    public ControlFlowGraph(BasicBlock<T> entryBlock, BasicBlock<T> exitBlock) {
        this.entryBlock = entryBlock;
        this.exitBlock = exitBlock;
        this.blocks = new ArrayList<>();

        this.blocks.add(entryBlock);
        this.blocks.add(exitBlock);
    }

    public void addBlock(BasicBlock<T> block) {
        if (!blocks.contains(block)) {
            blocks.add(block);
        }
    }
}