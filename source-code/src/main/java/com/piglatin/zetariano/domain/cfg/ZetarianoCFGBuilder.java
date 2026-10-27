package com.piglatin.zetariano.domain.cfg;

import com.piglatin.common.domain.cfg.BasicBlock;
import com.piglatin.common.domain.cfg.ControlFlowGraph;
import com.piglatin.zetariano.domain.ast.expressions.*;
import com.piglatin.zetariano.domain.ast.expressions.literals.*;
import com.piglatin.zetariano.domain.ast.principal.*;
import com.piglatin.zetariano.domain.ast.statements.*;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class ZetarianoCFGBuilder implements Visitor<Void> {

    private int blockCounter;
    private BasicBlock<ASTNode> currentBlock;
    private ControlFlowGraph<ASTNode> graph;
    private final Deque<BasicBlock<ASTNode>> breakTargets = new ArrayDeque<>();
    private final Deque<BasicBlock<ASTNode>> continueTargets = new ArrayDeque<>();
    private final Map<String, ControlFlowGraph<ASTNode>> methodGraphs = new LinkedHashMap<>();

    public Map<String, ControlFlowGraph<ASTNode>> build(NodeProgram program) {
        methodGraphs.clear();
        if (program == null) return methodGraphs;

        List<NodeClassDeclaration> classes = new ArrayList<>();
        if (program.getClasses() != null && !program.getClasses().isEmpty()) {
            classes.addAll(program.getClasses());
        }
        for (NodeClassDeclaration cls: classes){
            processClass(cls);
        }
        return methodGraphs;
    }

    // ============================================================
    // PER-METHOD SETUP
    // ============================================================

    private String signature(String name, List<NodeParameter> params) {
        int arity = (params == null) ? 0 : params.size();
        return name + "/" + arity;
    }

    private ControlFlowGraph<ASTNode> buildMethodGraph(NodeBlock body) {
        // Reset per-method state.
        blockCounter = 0;
        breakTargets.clear();
        continueTargets.clear();

        BasicBlock<ASTNode> entry = new BasicBlock(blockCounter++);
        entry.setEntry(true);
        BasicBlock<ASTNode> exit = new BasicBlock(blockCounter++);
        exit.setExit(true);

        this.graph = new ControlFlowGraph<>(entry, exit);
        this.currentBlock = entry;

        if (body != null) body.accept(this);

        // Connect dangling block to exit (only if reachable).
        if (currentBlock != null) {
            currentBlock.addSuccessor(exit);
        }
        return this.graph;
    }

    private BasicBlock<ASTNode> createBlock() {
        BasicBlock<ASTNode> block = new BasicBlock<>(blockCounter++);
        graph.addBlock(block);
        return block;
    }

    // ============================================================
    // TOP-LEVEL DECLARATIONS
    // ============================================================

    @Override public Void visitProgram(NodeProgram n) {
        build(n);
        return null;
    }

    @Override
    public Void visitImport(NodeImport n) {
        return null;
    }

    @Override public Void visitClassDeclaration(NodeClassDeclaration n) { return null; }
    @Override public Void visitFieldDeclaration(NodeFieldDeclaration n) { return null; }
    @Override public Void visitMethodDeclaration(NodeMethodDeclaration n) { return null; }
    @Override public Void visitConstructorDeclaration(NodeConstructorDeclaration n) { return null; }
    @Override public Void visitParameter(NodeParameter n) { return null; }

    // ============================================================
    // BLOCKS
    // ============================================================

    @Override
    public Void visitBlock(NodeBlock n) {
        if (n == null || currentBlock == null) return null;
        for (ASTNode instruction : n.getInstructions()) {
            if (currentBlock == null) break; // unreachable after break/return/continue
            instruction.accept(this);
        }
        return null;
    }

    // ============================================================
    // BRANCHING STATEMENTS
    // ============================================================

    @Override
    public Void visitIf(NodeIf n) {
        if (n == null || currentBlock == null) return null;

        // Evaluate condition in current block.
        if (n.getCondition() != null) currentBlock.addInstruction(n.getCondition());
        BasicBlock<ASTNode> conditionBlock = currentBlock;

        BasicBlock<ASTNode> thenBlock = createBlock();
        BasicBlock<ASTNode> nextBlock = createBlock();

        // True branch
        conditionBlock.addSuccessor(thenBlock);
        currentBlock = thenBlock;
        if (n.getThenBlock() != null) n.getThenBlock().accept(this);
        if (currentBlock != null) currentBlock.addSuccessor(nextBlock);

        // False branch
        if (n.getElseBlock() != null) {
            BasicBlock<ASTNode> elseBlock = createBlock();
            conditionBlock.addSuccessor(elseBlock);
            currentBlock = elseBlock;
            n.getElseBlock().accept(this);
            if (currentBlock != null) currentBlock.addSuccessor(nextBlock);
        } else {
            conditionBlock.addSuccessor(nextBlock);
        }

        currentBlock = nextBlock;
        return null;
    }

    @Override
    public Void visitSwitch(NodeSwitch n) {
        if (n == null || currentBlock == null) return null;

        // Evaluate switch expression in current block.
        if (n.getExpression() != null) currentBlock.addInstruction(n.getExpression());
        BasicBlock<ASTNode> dispatchBlock = currentBlock;

        BasicBlock<ASTNode> afterSwitch = createBlock();

        List<NodeCase> cases = n.getCases();
        int numCases = (cases != null) ? cases.size() : 0;

        // One block per case.
        List<BasicBlock<ASTNode>> caseBlocks = new ArrayList<>(numCases);
        for (int i = 0; i < numCases; i++) {
            BasicBlock<ASTNode> caseBlock = createBlock();
            caseBlocks.add(caseBlock);
            dispatchBlock.addSuccessor(caseBlock);
        }

        // Default block (or fallback to afterSwitch when absent).
        BasicBlock<ASTNode> defaultBlock = null;
        if (n.getDefaultBlock() != null) {
            defaultBlock = createBlock();
            dispatchBlock.addSuccessor(defaultBlock);
        } else {
            dispatchBlock.addSuccessor(afterSwitch);
        }

        // 'break' inside switch jumps to afterSwitch.
        breakTargets.push(afterSwitch);

        // Case bodies with fallthrough semantics (Java-like).
        for (int i = 0; i < numCases; i++) {
            NodeCase c = cases.get(i);
            currentBlock = caseBlocks.get(i);

            if (c.getBlock() != null) c.getBlock().accept(this);

            if (currentBlock != null) {
                BasicBlock<ASTNode> fallthrough =
                        (i < numCases - 1) ? caseBlocks.get(i + 1)
                                : (defaultBlock != null) ? defaultBlock
                                : afterSwitch;
                currentBlock.addSuccessor(fallthrough);
            }
        }

        // Default body.
        if (defaultBlock != null) {
            currentBlock = defaultBlock;
            n.getDefaultBlock().accept(this);
            if (currentBlock != null) currentBlock.addSuccessor(afterSwitch);
        }

        breakTargets.pop();
        currentBlock = afterSwitch;
        return null;
    }

    @Override
    public Void visitCase(NodeCase n) {
        return null;
    }

    // ============================================================
    // LOOPS
    // ============================================================

    @Override
    public Void visitWhile(NodeWhile n) {
        if (n == null || currentBlock == null) return null;

        BasicBlock<ASTNode> headerBlock = createBlock();
        BasicBlock<ASTNode> bodyBlock = createBlock();
        BasicBlock<ASTNode> exitBlock = createBlock();

        currentBlock.addSuccessor(headerBlock);

        currentBlock = headerBlock;
        if (n.getCondition() != null) currentBlock.addInstruction(n.getCondition());
        currentBlock.addSuccessor(bodyBlock);
        currentBlock.addSuccessor(exitBlock);

        breakTargets.push(exitBlock);
        continueTargets.push(headerBlock);

        currentBlock = bodyBlock;
        if (n.getBlock() != null) n.getBlock().accept(this);
        if (currentBlock != null) currentBlock.addSuccessor(headerBlock);

        breakTargets.pop();
        continueTargets.pop();

        currentBlock = exitBlock;
        return null;
    }

    @Override
    public Void visitDoWhile(NodeDoWhile n) {
        if (n == null || currentBlock == null) return null;

        BasicBlock<ASTNode> bodyBlock = createBlock();
        BasicBlock<ASTNode> conditionBlock = createBlock();
        BasicBlock<ASTNode> exitBlock = createBlock();

        currentBlock.addSuccessor(bodyBlock);

        breakTargets.push(exitBlock);
        continueTargets.push(conditionBlock);

        currentBlock = bodyBlock;
        if (n.getBlock() != null) n.getBlock().accept(this);
        if (currentBlock != null) currentBlock.addSuccessor(conditionBlock);

        currentBlock = conditionBlock;
        if (n.getCondition() != null) currentBlock.addInstruction(n.getCondition());
        currentBlock.addSuccessor(bodyBlock);
        currentBlock.addSuccessor(exitBlock);

        breakTargets.pop();
        continueTargets.pop();

        currentBlock = exitBlock;
        return null;
    }

    @Override
    public Void visitFor(NodeFor n) {
        if (n == null || currentBlock == null) return null;

        if (n.getInitializer() != null) currentBlock.addInstruction(n.getInitializer());

        BasicBlock<ASTNode> conditionBlock = createBlock();
        BasicBlock<ASTNode> bodyBlock = createBlock();
        BasicBlock<ASTNode> updateBlock = createBlock();
        BasicBlock<ASTNode> exitBlock = createBlock();

        currentBlock.addSuccessor(conditionBlock);

        currentBlock = conditionBlock;
        if (n.getCondition() != null) currentBlock.addInstruction(n.getCondition());
        currentBlock.addSuccessor(bodyBlock);
        currentBlock.addSuccessor(exitBlock);

        breakTargets.push(exitBlock);
        continueTargets.push(updateBlock);

        currentBlock = bodyBlock;
        if (n.getBlock() != null) n.getBlock().accept(this);
        if (currentBlock != null) currentBlock.addSuccessor(updateBlock);

        currentBlock = updateBlock;
        if (n.getUpdate() != null) currentBlock.addInstruction(n.getUpdate());
        currentBlock.addSuccessor(conditionBlock);

        breakTargets.pop();
        continueTargets.pop();

        currentBlock = exitBlock;
        return null;
    }

    // ============================================================
    // JUMPS
    // ============================================================

    @Override
    public Void visitBreak(NodeBreak n) {
        if (currentBlock == null) return null;
        if (!breakTargets.isEmpty()) {
            currentBlock.addInstruction(n);
            currentBlock.addSuccessor(breakTargets.peek());
            currentBlock = null; // unreachable code after break
        }
        return null;
    }

    @Override
    public Void visitContinue(NodeContinue n) {
        if (currentBlock == null) return null;
        if (!continueTargets.isEmpty()) {
            currentBlock.addInstruction(n);
            currentBlock.addSuccessor(continueTargets.peek());
            currentBlock = null; // unreachable code after continue
        }
        return null;
    }

    @Override
    public Void visitReturn(NodeReturn n) {
        if (currentBlock == null) return null;
        currentBlock.addInstruction(n);
        currentBlock.addSuccessor(graph.getExitBlock());
        currentBlock = null;
        return null;
    }

    // ============================================================
    // LINEAR STATEMENTS
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
    public Void visitArrayInitializer(NodeArrayInitializer n) {
        return null;
    }

    // ============================================================
    // EXPRESSIONS AS STATEMENTS
    // ============================================================
    // Only reachable when the AST contains a bare expression as an instruction
    // (expression statements). Statements above never call accept() on sub-expressions,
    // so these methods are only triggered from visitBlock's instruction loop.

    @Override public Void visitMethodCall(NodeMethodCall n) {
        if (currentBlock != null) currentBlock.addInstruction(n);
        return null;
    }

    @Override public Void visitNewObject(NodeNewObject n) {
        if (currentBlock != null) currentBlock.addInstruction(n);
        return null;
    }

    @Override public Void visitNewArray(NodeNewArray n) {
        if (currentBlock != null) currentBlock.addInstruction(n);
        return null;
    }

    // ============================================================
    // EXPRESSIONS (NO-OP for CFG purposes)
    // ============================================================

    @Override public Void visitTernaryExpression(NodeTernaryExpression n) { return null; }
    @Override public Void visitBinaryExpression(NodeBinaryExpression n)   { return null; }
    @Override public Void visitUnaryExpression(NodeUnaryExpression n)     { return null; }
    @Override public Void visitIdentifier(NodeIdentifier n)               { return null; }
    @Override public Void visitFieldAccess(NodeFieldAccess n)             { return null; }
    @Override public Void visitIndexAccess(NodeIndexAccess n)             { return null; }

    // ============================================================
    // LITERALS
    // ============================================================

    @Override public Void visitIntegerLiteral(NodeIntegerLiteral n) { return null; }
    @Override public Void visitDecimalLiteral(NodeDecimalLiteral n) { return null; }
    @Override public Void visitCharLiteral(NodeCharLiteral n)       { return null; }
    @Override public Void visitStringLiteral(NodeStringLiteral n)   { return null; }
    @Override public Void visitBooleanLiteral(NodeBooleanLiteral n) { return null; }
    @Override public Void visitNullLiteral(NodeNullLiteral n)       { return null; }

    @Override
    public Void visitThis(NodeThis n) {
        return null;
    }

    private void processClass(NodeClassDeclaration cls) {
        if (cls == null || cls.getMembers() == null) return;

        for (ASTNode member : cls.getMembers()) {
            if (member instanceof NodeMethodDeclaration m) {
                methodGraphs.put(
                        signature(cls.getName(), m.getName(), m.getParameters()),
                        buildMethodGraph(m.getBody())
                );
            } else if (member instanceof NodeConstructorDeclaration c) {
                methodGraphs.put(
                        signature(cls.getName(), "<init>", c.getParameters()),
                        buildMethodGraph(c.getBody())
                );
            }
        }
    }

    private String signature(String className, String name, List<NodeParameter> params) {
        int arity = (params == null) ? 0 : params.size();

        return className + "#" + name + "/" + arity;
    }
}