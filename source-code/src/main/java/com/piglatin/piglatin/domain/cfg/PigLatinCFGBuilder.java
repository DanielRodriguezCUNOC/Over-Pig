package com.piglatin.piglatin.domain.cfg;

import com.piglatin.common.domain.cfg.BasicBlock;
import com.piglatin.common.domain.cfg.ControlFlowGraph;
import com.piglatin.piglatin.domain.ast.nodes.declaration.*;
import com.piglatin.piglatin.domain.ast.nodes.expression.*;
import com.piglatin.piglatin.domain.ast.nodes.instruction.*;
import com.piglatin.piglatin.domain.ast.nodes.literal.*;
import com.piglatin.piglatin.domain.ast.nodes.lvalue.NodeFieldAccess;
import com.piglatin.piglatin.domain.ast.nodes.lvalue.NodeIndexAccess;
import com.piglatin.piglatin.domain.ast.nodes.lvalue.NodeLvalue;
import com.piglatin.piglatin.domain.ast.nodes.NodeImport;
import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.principal.NodeProgram;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Builds a Control Flow Graph from the AST.
 */
public class PigLatinCFGBuilder implements Visitor<Void> {

    private int blockCounter = 0;
    private BasicBlock<ASTNode> currentBlock;
    private ControlFlowGraph<ASTNode> graph;

    //* Stacks to manage break and continue jumps inside loops
    private final Deque<BasicBlock<ASTNode>> loopHeaderStack = new ArrayDeque<>();
    private final Deque<BasicBlock<ASTNode>> loopExitStack = new ArrayDeque<>();

    public ControlFlowGraph<ASTNode> build(NodeProgram program) {
        BasicBlock<ASTNode> entry = createBlock();
        entry.setEntry(true);

        BasicBlock<ASTNode> exit = createBlock();
        exit.setExit(true);

        this.graph = new ControlFlowGraph<>(entry, exit);
        this.currentBlock = entry;

        program.accept(this);

        //* Connect the last active block to the exit block if not already connected
        if (currentBlock != null) {
            currentBlock.addSuccessor(exit);
        }

        return this.graph;
    }

    private BasicBlock<ASTNode> createBlock() {
        BasicBlock<ASTNode> block = new BasicBlock<>(blockCounter++);
        if (graph != null) {
            graph.addBlock(block);
        }
        return block;
    }

    // ============================================================
    // PROGRAM & BLOCKS
    // ============================================================

    @Override
    public Void visitProgram(NodeProgram n) {
        for (NodeDeclaration decl : n.getGlobalDeclarations()) {
            decl.accept(this);
        }
        for (ASTNode instruction : n.getMainInstructions()) {
            instruction.accept(this);
        }
        return null;
    }

    @Override
    public Void visitBlock(NodeBlock n) {
        for (ASTNode instruction : n.getInstructions()) {
            instruction.accept(this);
        }
        return null;
    }

    // ============================================================
    // CONTROL FLOW BRANCHES
    // ============================================================

    @Override
    public Void visitIf(NodeIf n) {
        // Evaluate condition in current block
        currentBlock.addInstruction(n.getCondition());
        BasicBlock<ASTNode> conditionBlock = currentBlock;

        BasicBlock<ASTNode> thenBlock = createBlock();
        BasicBlock<ASTNode> nextBlock = createBlock();

        //* Path for condition == true
        conditionBlock.addSuccessor(thenBlock);
        currentBlock = thenBlock;
        if (n.getThenBlock() != null) {
            n.getThenBlock().accept(this);
        }
        if (currentBlock != null) {
            currentBlock.addSuccessor(nextBlock);
        }

        // Path for condition == false
        if (n.getElseBlock() != null) {
            BasicBlock<ASTNode> elseBlock = createBlock();
            conditionBlock.addSuccessor(elseBlock);
            currentBlock = elseBlock;
            n.getElseBlock().accept(this);
            if (currentBlock != null) {
                currentBlock.addSuccessor(nextBlock);
            }
        } else {
            //* Jump directly to the continuation block if there is no else
            conditionBlock.addSuccessor(nextBlock);
        }

        currentBlock = nextBlock;
        return null;
    }

    @Override
    public Void visitWhile(NodeWhile n) {
        BasicBlock<ASTNode> headerBlock = createBlock();
        BasicBlock<ASTNode> bodyBlock = createBlock();
        BasicBlock<ASTNode> exitBlock = createBlock();

        //* Jump into loop header
        currentBlock.addSuccessor(headerBlock);

        //* Header block checks the condition
        currentBlock = headerBlock;
        currentBlock.addInstruction(n.getCondition());
        currentBlock.addSuccessor(bodyBlock);
        currentBlock.addSuccessor(exitBlock);

        //* Push loop targets for break/continue
        loopHeaderStack.push(headerBlock);
        loopExitStack.push(exitBlock);

        //* Build loop body
        currentBlock = bodyBlock;
        if (n.getBlock() != null) {
            n.getBlock().accept(this);
        }

        //* Body loops back to header
        if (currentBlock != null) {
            currentBlock.addSuccessor(headerBlock);
        }

        //* Pop loop targets
        loopHeaderStack.pop();
        loopExitStack.pop();

        currentBlock = exitBlock;
        return null;
    }

    @Override
    public Void visitDoWhile(NodeDoWhile n) {
        BasicBlock<ASTNode> bodyBlock = createBlock();
        BasicBlock<ASTNode> conditionBlock = createBlock();
        BasicBlock<ASTNode> exitBlock = createBlock();

        currentBlock.addSuccessor(bodyBlock);

        //* Push loop targets
        loopHeaderStack.push(conditionBlock);
        loopExitStack.push(exitBlock);

        //* Build body
        currentBlock = bodyBlock;
        if (n.getBlock() != null) {
            n.getBlock().accept(this);
        }
        if (currentBlock != null) {
            currentBlock.addSuccessor(conditionBlock);
        }

        //* Condition evaluation
        currentBlock = conditionBlock;
        currentBlock.addInstruction(n.getCondition());
        //* Loop back if true
        currentBlock.addSuccessor(bodyBlock);
        //* Exit if false
        currentBlock.addSuccessor(exitBlock);

        loopHeaderStack.pop();
        loopExitStack.pop();

        currentBlock = exitBlock;
        return null;
    }

    @Override
    public Void visitFor(NodeFor n) {
        if (n.getInitialization() != null) {
            currentBlock.addInstruction(n.getInitialization());
        }

        BasicBlock<ASTNode> conditionBlock = createBlock();
        BasicBlock<ASTNode> bodyBlock = createBlock();
        BasicBlock<ASTNode> updateBlock = createBlock();
        BasicBlock<ASTNode> exitBlock = createBlock();

        currentBlock.addSuccessor(conditionBlock);

        // Condition evaluation
        currentBlock = conditionBlock;
        if (n.getCondition() != null) {
            currentBlock.addInstruction(n.getCondition());
        }
        currentBlock.addSuccessor(bodyBlock);
        currentBlock.addSuccessor(exitBlock);

        //* Target for continue is updateBlock, target for break is exitBlock
        loopHeaderStack.push(updateBlock);
        loopExitStack.push(exitBlock);

        //* Body
        currentBlock = bodyBlock;
        if (n.getBlock() != null) {
            n.getBlock().accept(this);
        }
        if (currentBlock != null) {
            currentBlock.addSuccessor(updateBlock);
        }

        //* Update block
        currentBlock = updateBlock;
        if (n.getUpdate() != null) {
            currentBlock.addInstruction(n.getUpdate());
        }
        currentBlock.addSuccessor(conditionBlock);

        loopHeaderStack.pop();
        loopExitStack.pop();

        currentBlock = exitBlock;
        return null;
    }

    // ============================================================
    // JUMPS & CONTROL FLOW BREAKERS
    // ============================================================

    @Override
    public Void visitBreak(NodeBreak n) {
        if (!loopExitStack.isEmpty()) {
            currentBlock.addInstruction(n);
            currentBlock.addSuccessor(loopExitStack.peek());
            currentBlock = null; // Unreachable code following break
        }
        return null;
    }

    @Override
    public Void visitContinue(NodeContinue n) {
        if (!loopHeaderStack.isEmpty()) {
            currentBlock.addInstruction(n);
            currentBlock.addSuccessor(loopHeaderStack.peek());
            //* Unreachable code following continue
            currentBlock = null;
        }
        return null;
    }

    @Override
    public Void visitReturn(NodeReturn n) {
        currentBlock.addInstruction(n);
        currentBlock.addSuccessor(graph.getExitBlock());
        //* Unreachable code following return
        currentBlock = null;
        return null;
    }

    // ============================================================
    // LINEAR INSTRUCTIONS
    // ============================================================

    @Override
    public Void visitVariableDeclaration(NodeVariableDeclaration n) {
        if (currentBlock != null) currentBlock.addInstruction(n);
        return null;
    }

    @Override
    public Void visitArrayDeclaration(NodeArrayDeclaration n) {
        if (currentBlock != null) currentBlock.addInstruction(n);
        return null;
    }

    @Override
    public Void visitAssignment(NodeAssignment n) {
        if (currentBlock != null) currentBlock.addInstruction(n);
        return null;
    }

    @Override
    public Void visitRead(NodeRead n) {
        if (currentBlock != null) currentBlock.addInstruction(n);
        return null;
    }

    @Override
    public Void visitPrint(NodePrint n) {
        if (currentBlock != null) currentBlock.addInstruction(n);
        return null;
    }

    @Override
    public Void visitFunctionCall(NodeFunctionCall n) {
        if (currentBlock != null) currentBlock.addInstruction(n);
        return null;
    }

    @Override public Void visitNewInstance(NodeNewInstance n) { return null; }
    @Override public Void visitStructLiteral(NodeStructLiteral n) { return null; }
    @Override public Void visitIntegerLiteral(NodeIntegerLiteral n) { return null; }
    @Override public Void visitDecimalLiteral(NodeDecimalLiteral n) { return null; }
    @Override public Void visitStringLiteral(NodeStringLiteral n) { return null; }
    @Override public Void visitCharLiteral(NodeCharLiteral n) { return null; }
    @Override public Void visitBooleanLiteral(NodeBooleanLiteral n) { return null; }
    @Override public Void visitIdentifier(NodeIdentifier n) { return null; }
    @Override public Void visitIndexAccess(NodeIndexAccess n) { return null; }
    @Override public Void visitFieldAccess(NodeFieldAccess n) { return null; }
    @Override public Void visitBinaryOperation(NodeBinaryOperation n) { return null; }
    @Override public Void visitUnaryOperation(NodeUnaryOperation n) { return null; }
    @Override public Void visitIncrementDecrement(NodeIncrementDecrement n) { return null; }
    @Override public Void visitArrayLiteral(NodeArrayLiteral n) { return null; }
    @Override public Void visitLvalue(NodeLvalue n) { return null; }
    @Override public Void visitImport(NodeImport n) { return null; }
}