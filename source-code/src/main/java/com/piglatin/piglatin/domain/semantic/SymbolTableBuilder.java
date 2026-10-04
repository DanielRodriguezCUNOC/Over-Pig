package com.piglatin.piglatin.domain.semantic;

import com.piglatin.piglatin.domain.ast.nodes.NodeImport;
import com.piglatin.piglatin.domain.ast.nodes.declaration.NodeArrayDeclaration;
import com.piglatin.piglatin.domain.ast.nodes.declaration.NodeDeclaration;
import com.piglatin.piglatin.domain.ast.nodes.declaration.NodeVariableDeclaration;
import com.piglatin.piglatin.domain.ast.nodes.expression.*;
import com.piglatin.piglatin.domain.ast.nodes.instruction.*;
import com.piglatin.piglatin.domain.ast.nodes.literal.*;
import com.piglatin.piglatin.domain.ast.nodes.lvalue.NodeFieldAccess;
import com.piglatin.piglatin.domain.ast.nodes.lvalue.NodeIndexAccess;
import com.piglatin.piglatin.domain.ast.nodes.lvalue.NodeLvalue;
import com.piglatin.piglatin.domain.ast.principal.ASTNode;
import com.piglatin.piglatin.domain.ast.principal.NodeProgram;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import com.piglatin.piglatin.domain.symboltable.ArraySymbol;
import com.piglatin.piglatin.domain.symboltable.FunctionSymbol;
import com.piglatin.piglatin.domain.symboltable.SymbolTable;
import com.piglatin.piglatin.domain.symboltable.VariableSymbol;
import com.piglatin.piglatin.domain.types.TypeTable;
import lombok.Getter;
import lombok.Setter;

/**
 * Semantic analysis pass 1.
 *
 * Traversal: Top-down.
 * Functions:
 * - Build the Symbol Table.
 * - Build the Type Table.
 * - Validate strict sequencing: every identifier used must be previously
 * declared
 * in the same scope or an ancestor scope.
 * - Register function signatures before analyzing their bodies.
 *
 * !EYE!!: A local variable may shadow one from a parent scope.
 */
@Getter
@Setter
public class SymbolTableBuilder implements Visitor<Void> {

private final SymbolTable symbolTable;
private final TypeTable typeTable;
private final SemanticErrorReporter errorReporter;
private int loopDepth = 0;
private boolean hasImports = false;

    public SymbolTableBuilder(TypeTable typeTable, SemanticErrorReporter errorReporter) {
        this.symbolTable = new SymbolTable();
        this.typeTable = typeTable;
        this.errorReporter = errorReporter;
    }

    @Override
    public Void visitProgram(NodeProgram n) {

        if (n.getImports() != null) {
            for (NodeImport imp : n.getImports()) {
                imp.accept(this);
            }
        }

        //* Visit Global Declarations
        for (NodeDeclaration decl : n.getGlobalDeclarations()){
            decl.accept(this);
        }

        //* Visit main instructions
        for (ASTNode instruction : n.getMainInstructions()){
            instruction.accept(this);
        }

        return null;
    }

    @Override
    public Void visitVariableDeclaration(NodeVariableDeclaration n) {
        if (n.getInitializer() != null) n.getInitializer().accept(this);

        if (n.getType() != null && !typeTable.exists(n.getType())) {
            errorReporter.reportError(
                    "Type " + n.getType() + " is not defined.", n.getLine(), n.getColumn()
            );
        }

        //* Declare in the current scope
        VariableSymbol variableSymbol = new VariableSymbol(n.getIdentifier(), n.getType(), n.getLine(), n.getColumn());
        boolean success = symbolTable.declare(n.getIdentifier(), variableSymbol);

        if (!success)
            errorReporter.reportError("Variable '" + n.getIdentifier() +
                    "' is already defined in this scope", n.getLine(), n.getColumn());
        return null;
    }

    @Override
    public Void visitArrayDeclaration(NodeArrayDeclaration n) {
        //* Visit the size expression
        if (n.getSizeExpression() != null) n.getSizeExpression().accept(this);

        //* Validate array element type
        if (!typeTable.exists(n.getElementType()))
            errorReporter.reportError("Type " + n.getElementType() + " is not defined." + n.getLine(),  n.getLine(), n.getColumn());

        //* Visit initial values if present
        if (n.getInitialValues() != null){
            for (ASTNode val : n.getInitialValues()){
                val.accept(this);
            }
        }

        ArraySymbol arraySymbol = new ArraySymbol(n.getIdentifier(), "SERIES_" + n.getElementType(), n.getSize(),
                n.getElementType(), n.getLine(), n.getColumn());
        boolean success = symbolTable.declare(n.getIdentifier(), arraySymbol);

        if (!success)
            errorReporter.reportError("Array '" + n.getIdentifier() +
                    "' is already declared in this scope.", n.getLine(), n.getColumn());


        return null;
    }

    @Override
    public Void visitAssignment(NodeAssignment n) {

        //* Check if left/right side exists
        n.getLvalue().accept(this);
        n.getExpression().accept(this);
        return null;
    }

    @Override
    public Void visitRead(NodeRead n) {
        if (n.getTarget() != null) n.getTarget().accept(this);
        return null;
    }

    @Override
    public Void visitPrint(NodePrint n) {
        if (n.getPrintItems() != null){
            for (ASTNode item: n.getPrintItems()){
                item.accept(this);
            }
        }
        return null;
    }

    @Override
    public Void visitIf(NodeIf n) {

        if (n.getCondition() != null) n.getCondition().accept(this);

        if (n.getThenBlock() != null) n.getThenBlock().accept(this);

        if (n.getElseIfClauses() != null) {
            for (ElseIfClause elseIfClause : n.getElseIfClauses()) {
                if (elseIfClause.getCondition() != null) elseIfClause.getCondition().accept(this);
                if (elseIfClause.getBlock() != null) elseIfClause.getBlock().accept(this);
            }
        }

        if (n.getElseBlock() != null) n.getElseBlock().accept(this);
        return null;
    }

    @Override
    public Void visitWhile(NodeWhile n) {
        n.getCondition().accept(this);
        loopDepth++;
        if (n.getBlock() != null) n.getBlock().accept(this);
        loopDepth--;
        return null;
    }

    @Override
    public Void visitDoWhile(NodeDoWhile n) {
        loopDepth++; // Enter loop
        if (n.getBlock() != null) n.getBlock().accept(this);
        loopDepth--; // Exit loop

        n.getCondition().accept(this);
        return null;
    }

    @Override
    public Void visitFor(NodeFor n) {
        symbolTable.pushScope("for_loop");

        if (n.getInitialization() != null) n.getInitialization().accept(this);
        if (n.getCondition() != null) n.getCondition().accept(this);
        if (n.getUpdate() != null) n.getUpdate().accept(this);

        loopDepth++;
        if (n.getBlock() != null) {
            n.getBlock().accept(this);
        }
        loopDepth--;

        symbolTable.popScope();
        return null;
    }

    @Override
    public Void visitContinue(NodeContinue n) {
        if (loopDepth <= 0) {
            errorReporter.reportError("'continue' statement can only be used inside a loop.", n.getLine(), n.getColumn());
        }
        return null;
    }

    @Override
    public Void visitBreak(NodeBreak n) {
        if (loopDepth <= 0) {
            errorReporter.reportError("'break' statement can only be used inside a loop.", n.getLine(), n.getColumn());
        }
        return null;
    }

    @Override
    public Void visitReturn(NodeReturn n) {
        if (n.getExpression() != null) n.getExpression().accept(this);
        return null;
    }

    @Override
    public Void visitBlock(NodeBlock n) {
        //* Create new scope
        symbolTable.pushScope("block");
        for (ASTNode instruction : n.getInstructions()){
            instruction.accept(this);
        }
        symbolTable.popScope();
        return null;
    }

    @Override
    public Void visitNewInstance(NodeNewInstance n) {
        if (n.getArguments() != null){
            for (ASTNode arg : n.getArguments()){
                arg.accept(this);
            }
        }
        return null;
    }

    @Override
    public Void visitStructLiteral(NodeStructLiteral n) {
        if (n.getValues() != null){
            for (ASTNode val : n.getValues()){
                val.accept(this);
            }
        }
        return null;
    }

    @Override
    public Void visitIntegerLiteral(NodeIntegerLiteral n) {
        return null;
    }

    @Override
    public Void visitDecimalLiteral(NodeDecimalLiteral n) {
        return null;
    }

    @Override
    public Void visitStringLiteral(NodeStringLiteral n) {
        return null;
    }

    @Override
    public Void visitCharLiteral(NodeCharLiteral n) {
        return null;
    }

    @Override
    public Void visitBooleanLiteral(NodeBooleanLiteral n) {
        return null;
    }

    @Override
    public Void visitIdentifier(NodeIdentifier n) {
        //* Verify if exissts before to use
        if (!symbolTable.exists(n.getId()))
            errorReporter.reportError("Variable '" + n.getId() +
                    "' is used before declaration.", n.getLine(), n.getColumn());
        return null;
    }

    @Override
    public Void visitIndexAccess(NodeIndexAccess n) {
        if (n.getCurrentNode() != null) n.getCurrentNode().accept(this);
        if (n.getIndexExpression() != null) n.getIndexExpression().accept(this);
        return null;
    }

    @Override
    public Void visitFieldAccess(NodeFieldAccess n) {
        if (n.getCurrentNode() != null) n.getCurrentNode().accept(this);
        return null;
    }

    @Override
    public Void visitBinaryOperation(NodeBinaryOperation n) {
        if (n.getLeft() !=null) n.getLeft().accept(this);
        if (n.getRight() != null) n.getRight().accept(this);
        return null;
    }

    @Override
    public Void visitUnaryOperation(NodeUnaryOperation n) {
        if (n.getOperand() != null) n.getOperand().accept(this);
        return null;
    }

    @Override
    public Void visitIncrementDecrement(NodeIncrementDecrement n) {
        if (n.getOperand() != null) n.getOperand().accept(this);
        return null;
    }

    @Override
    public Void visitArrayLiteral(NodeArrayLiteral n) {
        if (n.getValues() != null){
            for (ASTNode val: n.getValues()) {
                val.accept(this);
            }
        }
        return null;
    }

    @Override
    public Void visitLvalue(NodeLvalue n) {
        return null;
    }

    @Override
    public Void visitImport(NodeImport n) {
        this.hasImports = true;
        if (n != null && n.getPath() != null && !n.getPath().isEmpty()) {
            String lastSegment = n.getPath().get(n.getPath().size() - 1);
            if (typeTable != null) {
                typeTable.registerType(lastSegment);
            }
        }
        return null;
    }

    @Override
    public Void visitFunctionCall(NodeFunctionCall n) {
        if (n.getCurrentNode() != null) {
            n.getCurrentNode().accept(this);
            if (n.getArguments() != null) {
                for (ASTNode argument : n.getArguments()) {
                    argument.accept(this);
                }
            }
        } else {
            if (!symbolTable.exists(n.getFunctionName())) {
                if (hasImports) {
                    symbolTable.declare(
                            n.getFunctionName(),
                            new FunctionSymbol(n.getFunctionName(), "FUNCTION", n.getLine(), n.getColumn(), "VOID")
                    );
                } else {
                    errorReporter.reportError("Function '" + n.getFunctionName() +
                            "' is not declared.", n.getLine(), n.getColumn());
                }
            }

            if (n.getArguments() != null) {
                for (ASTNode argument : n.getArguments()) {
                    argument.accept(this);
                }
            }
        }

        return null;
    }
}
