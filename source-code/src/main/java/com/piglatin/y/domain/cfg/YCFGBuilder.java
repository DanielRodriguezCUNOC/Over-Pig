package com.piglatin.y.domain.cfg;

import com.piglatin.common.domain.cfg.BasicBlock;
import com.piglatin.common.domain.cfg.ControlFlowGraph;
import com.piglatin.y.domain.ast.expressions.*;
import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.principal.NodeProgram;
import com.piglatin.y.domain.ast.statements.*;
import com.piglatin.y.domain.ast.visitor.Visitor;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


@Getter
public class YCFGBuilder implements Visitor<Void> {

    private final Map<String, ControlFlowGraph<ASTNode>> functionCFGs = new HashMap<>();

    private ControlFlowGraph<ASTNode> currentCFG;
    private BasicBlock<ASTNode> currentBlock;
    private BasicBlock<ASTNode> exitBlock;
    private final Stack<BasicBlock<ASTNode>> loopHeaderStack = new Stack<>();
    private final Stack<BasicBlock<ASTNode>> loopExitStack = new Stack<>();
    private final Stack<BasicBlock<ASTNode>> breakTargetStack = new Stack<>();
    private int blockIdCounter = 0;

    private BasicBlock<ASTNode> createBlock() {
        BasicBlock<ASTNode> block = new BasicBlock<>(blockIdCounter++);
        if (currentCFG != null) {
            currentCFG.addBlock(block);
        }
        return block;
    }

    @Override
    public Void visitProgram(NodeProgram n) {
        if (n == null) return null;

        if (n.getFunctions() != null) {
            for (NodeFunctionDefinition func : n.getFunctions()) {
                if (func != null) {
                    func.accept(this);
                }
            }
        }
        return null;
    }

    @Override
    public Void visitFunctionDefinition(NodeFunctionDefinition n) {
        if (n == null) return null;

        blockIdCounter = 0;

        BasicBlock<ASTNode> entry = new BasicBlock<>(blockIdCounter++);
        entry.setEntry(true);

        BasicBlock<ASTNode> exit = new BasicBlock<>(blockIdCounter++);
        exit.setExit(true);

        this.exitBlock = exit;
        this.currentCFG = new ControlFlowGraph<>(entry, exit);
        this.currentBlock = entry;

        if (n.getBlock() != null) {
            n.getBlock().accept(this);
        }

        if (currentBlock != null) {
            currentBlock.addSuccessor(exitBlock);
        }

        functionCFGs.put(n.getName(), currentCFG);
        return null;
    }

    @Override
    public Void visitBlock(NodeBlock n) {
        if (n == null || n.getInstructions() == null) return null;

        for (ASTNode instruction : n.getInstructions()) {
            if (instruction != null) {
                instruction.accept(this);
            }
        }
        return null;
    }

    @Override
    public Void visitVariableDeclaration(NodeVariableDeclaration n) {
        if (currentBlock != null) {
            currentBlock.addInstruction(n);
        }
        return null;
    }

    @Override
    public Void visitArrayDeclaration(NodeArrayDeclaration n) {
        if (currentBlock != null) {
            currentBlock.addInstruction(n);
        }
        return null;
    }

    @Override
    public Void visitAssignment(NodeAssignment n) {
        if (currentBlock != null) {
            currentBlock.addInstruction(n);
        }
        return null;
    }

    @Override
    public Void visitPrint(NodePrint n) {
        if (currentBlock != null) {
            currentBlock.addInstruction(n);
        }
        return null;
    }

    @Override
    public Void visitRead(NodeRead n) {
        if (currentBlock != null) {
            currentBlock.addInstruction(n);
        }
        return null;
    }

    @Override
    public Void visitStructureDefinition(NodeStructureDefinition n) {
        if (currentBlock != null) {
            currentBlock.addInstruction(n);
        }
        return null;
    }


    @Override
    public Void visitIf(NodeIf n) {
        if (n == null || currentBlock == null) return null;

        BasicBlock<ASTNode> condBB = currentBlock;
        if (n.getCondition() != null) {
            condBB.addInstruction(n.getCondition());
        }

        BasicBlock<ASTNode> joinBlock = createBlock();

        // Rama 'entonces'
        BasicBlock<ASTNode> thenBB = createBlock();
        condBB.addSuccessor(thenBB);

        currentBlock = thenBB;
        if (n.getThenBlock() != null) {
            n.getThenBlock().accept(this);
        }
        if (currentBlock != null) {
            currentBlock.addSuccessor(joinBlock);
        }

        BasicBlock<ASTNode> prevCondBB = condBB;

        if (n.getElseIfClauses() != null && !n.getElseIfClauses().isEmpty()) {
            for (ElseIfClause clause : n.getElseIfClauses()) {
                if (clause == null) continue;

                BasicBlock<ASTNode> elseIfCondBB = createBlock();
                prevCondBB.addSuccessor(elseIfCondBB);

                if (clause.getCondition() != null) {
                    elseIfCondBB.addInstruction(clause.getCondition());
                }

                BasicBlock<ASTNode> elseIfThenBB = createBlock();
                elseIfCondBB.addSuccessor(elseIfThenBB);

                currentBlock = elseIfThenBB;
                if (clause.getBlock() != null) {
                    clause.getBlock().accept(this);
                }
                if (currentBlock != null) {
                    currentBlock.addSuccessor(joinBlock);
                }

                prevCondBB = elseIfCondBB;
            }
        }
        if (n.getElseBlock() != null) {
            BasicBlock<ASTNode> elseBB = createBlock();
            prevCondBB.addSuccessor(elseBB);

            currentBlock = elseBB;
            n.getElseBlock().accept(this);

            if (currentBlock != null) {
                currentBlock.addSuccessor(joinBlock);
            }
        } else {
            prevCondBB.addSuccessor(joinBlock);
        }

        currentBlock = joinBlock;
        return null;
    }

    @Override
    public Void visitChoose(NodeChoose n) {
        if (n == null || currentBlock == null) return null;

        BasicBlock<ASTNode> chooseExprBB = currentBlock;
        if (n.getExpression() != null) {
            chooseExprBB.addInstruction(n.getExpression());
        }

        BasicBlock<ASTNode> joinBlock = createBlock();
        breakTargetStack.push(joinBlock);

        BasicBlock<ASTNode> prevCondBB = chooseExprBB;

        if (n.getCases() != null) {
            for (NodeChooseCase c : n.getCases()) {
                if (c == null) continue;

                BasicBlock<ASTNode> caseCondBB = createBlock();
                prevCondBB.addSuccessor(caseCondBB);

                if (c.getValue() != null) {
                    caseCondBB.addInstruction(c.getValue());
                }

                BasicBlock<ASTNode> caseBodyBB = createBlock();
                caseCondBB.addSuccessor(caseBodyBB);

                currentBlock = caseBodyBB;
                if (c.getBlock() != null) {
                    c.getBlock().accept(this);
                }

                if (currentBlock != null) {
                    currentBlock.addSuccessor(joinBlock);
                }

                prevCondBB = caseCondBB;
            }
        }

        if (n.getDefaultCase() != null) {
            BasicBlock<ASTNode> defaultBB = createBlock();
            prevCondBB.addSuccessor(defaultBB);

            currentBlock = defaultBB;
            if (n.getDefaultCase().getBlock() != null) {
                n.getDefaultCase().getBlock().accept(this);
            }

            if (currentBlock != null) {
                currentBlock.addSuccessor(joinBlock);
            }
        } else {
            prevCondBB.addSuccessor(joinBlock);
        }

        breakTargetStack.pop();
        currentBlock = joinBlock;
        return null;
    }

    @Override
    public Void visitChooseCase(NodeChooseCase n) {
        if (n == null) return null;
        if (n.getValue() != null) n.getValue().accept(this);
        if (n.getBlock() != null) n.getBlock().accept(this);
        return null;
    }


    @Override
    public Void visitWhile(NodeWhile n) {
        if (n == null || currentBlock == null) return null;

        BasicBlock<ASTNode> condBB = createBlock();
        currentBlock.addSuccessor(condBB);

        if (n.getCondition() != null) {
            condBB.addInstruction(n.getCondition());
        }

        BasicBlock<ASTNode> bodyBB = createBlock();
        BasicBlock<ASTNode> exitBB = createBlock();

        condBB.addSuccessor(bodyBB);
        condBB.addSuccessor(exitBB);

        loopHeaderStack.push(condBB);
        loopExitStack.push(exitBB);
        breakTargetStack.push(exitBB);

        currentBlock = bodyBB;
        if (n.getBlock() != null) {
            n.getBlock().accept(this);
        }

        if (currentBlock != null) {
            currentBlock.addSuccessor(condBB);
        }

        loopHeaderStack.pop();
        loopExitStack.pop();
        breakTargetStack.pop();

        currentBlock = exitBB;
        return null;
    }

    @Override
    public Void visitDoWhile(NodeDoWhile n) {
        if (n == null || currentBlock == null) return null;

        BasicBlock<ASTNode> bodyBB = createBlock();
        BasicBlock<ASTNode> condBB = createBlock();
        BasicBlock<ASTNode> exitBB = createBlock();

        currentBlock.addSuccessor(bodyBB);

        loopHeaderStack.push(condBB);
        loopExitStack.push(exitBB);
        breakTargetStack.push(exitBB);

        currentBlock = bodyBB;
        if (n.getBlock() != null) {
            n.getBlock().accept(this);
        }

        if (currentBlock != null) {
            currentBlock.addSuccessor(condBB);
        }

        if (n.getCondition() != null) {
            condBB.addInstruction(n.getCondition());
        }

        condBB.addSuccessor(bodyBB);
        condBB.addSuccessor(exitBB);

        loopHeaderStack.pop();
        loopExitStack.pop();
        breakTargetStack.pop();

        currentBlock = exitBB;
        return null;
    }

    @Override
    public Void visitFor(NodeFor n) {
        if (n == null || currentBlock == null) return null;

        if (n.getInit() != null) {
            currentBlock.addInstruction(n.getInit());
        }

        BasicBlock<ASTNode> condBB = createBlock();
        currentBlock.addSuccessor(condBB);

        if (n.getCondition() != null) {
            condBB.addInstruction(n.getCondition());
        }

        BasicBlock<ASTNode> bodyBB = createBlock();
        BasicBlock<ASTNode> updateBB = createBlock();
        BasicBlock<ASTNode> exitBB = createBlock();

        condBB.addSuccessor(bodyBB);
        condBB.addSuccessor(exitBB);

        loopHeaderStack.push(updateBB);
        loopExitStack.push(exitBB);
        breakTargetStack.push(exitBB);

        currentBlock = bodyBB;
        if (n.getBlock() != null) {
            n.getBlock().accept(this);
        }

        if (currentBlock != null) {
            currentBlock.addSuccessor(updateBB);
        }

        if (n.getUpdate() != null) {
            updateBB.addInstruction(n.getUpdate());
        }
        updateBB.addSuccessor(condBB);

        loopHeaderStack.pop();
        loopExitStack.pop();
        breakTargetStack.pop();

        currentBlock = exitBB;
        return null;
    }

    @Override
    public Void visitBreak(NodeBreak n) {
        if (currentBlock != null) {
            currentBlock.addInstruction(n);
            if (!breakTargetStack.isEmpty()) {
                currentBlock.addSuccessor(breakTargetStack.peek());
            }
            currentBlock = null;
        }
        return null;
    }

    @Override
    public Void visitContinue(NodeContinue n) {
        if (currentBlock != null) {
            currentBlock.addInstruction(n);
            if (!loopHeaderStack.isEmpty()) {
                currentBlock.addSuccessor(loopHeaderStack.peek());
            }
            currentBlock = null;
        }
        return null;
    }

    @Override
    public Void visitReturn(NodeReturn n) {
        if (currentBlock != null) {
            currentBlock.addInstruction(n);
            if (exitBlock != null) {
                currentBlock.addSuccessor(exitBlock);
            }
            currentBlock = null;
        }
        return null;
    }


    @Override public Void visitFieldDeclaration(NodeFieldDeclaration n) { return null; }
    @Override public Void visitParameter(NodeParameter n)              { return null; }
    @Override public Void visitBinaryOperation(NodeBinaryOperation n)   { return null; }
    @Override public Void visitUnaryOperation(NodeUnaryOperation n)     { return null; }
    @Override public Void visitIdentifier(NodeIdentifier n)             { return null; }
    @Override public Void visitIntegerLiteral(NodeIntegerLiteral n)     { return null; }
    @Override public Void visitFloatLiteral(NodeFloatLiteral n)         { return null; }
    @Override public Void visitCharLiteral(NodeCharLiteral n)           { return null; }
    @Override public Void visitStringLiteral(NodeStringLiteral n)       { return null; }
    @Override public Void visitBooleanLiteral(NodeBooleanLiteral n)     { return null; }
    @Override public Void visitArrayLiteral(NodeArrayLiteral n)         { return null; }
    @Override public Void visitLvalue(NodeLvalue n)                     { return null; }
    @Override public Void visitFieldAccess(NodeFieldAccess n)           { return null; }
    @Override public Void visitIndexAccess(NodeIndexAccess n)           { return null; }
    @Override public Void visitFunctionCall(NodeFunctionCall n)        { return null; }
}