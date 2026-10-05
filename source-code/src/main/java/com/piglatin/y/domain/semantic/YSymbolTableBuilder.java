package com.piglatin.y.domain.semantic;

import com.piglatin.y.domain.ast.expressions.*;
import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.principal.NodeProgram;
import com.piglatin.y.domain.ast.statements.*;
import com.piglatin.y.domain.ast.visitor.Visitor;
import com.piglatin.y.domain.symboltable.*;
import com.piglatin.y.domain.types.YTypeTable;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Semantic analysis pass 1.
 *
 * Traversal: Top-down.
 * Functions:
 * - Build the Symbol Table.
 * - Build the Type Table.
 * - Validate strict sequencing: every identifier used must be previously
 *   declared in the same scope or an ancestor scope.
 * - Register function signatures before analyzing their bodies.
 *
 * !EYE!!: A local variable may shadow one from a parent scope.
 */
@Getter
@Setter
public class YSymbolTableBuilder implements Visitor<Void> {

    private final YSymbolTable symbolTable;
    private final YTypeTable typeTable;
    private final SemanticErrorReporter errorReporter;
    private int loopDepth = 0;
    private String currentFunctionReturnType = null;

    public YSymbolTableBuilder(YTypeTable typeTable, SemanticErrorReporter errorReporter) {
        this.symbolTable = new YSymbolTable();
        this.typeTable = typeTable;
        this.errorReporter = errorReporter;
    }

    //*********************
    //?     PROGRAM
    //*********************

    @Override
    public Void visitProgram(NodeProgram n) {
        if (n == null) return null;

        symbolTable.pushScope("global");

        if (n.getStructures() != null) {
            registerGlobalStructures(n.getStructures());
        }

        if (n.getFunctions() != null) {
            registerFunctionSignatures(n.getFunctions());
            for (NodeFunctionDefinition f : n.getFunctions()) {
                if (f != null) analyzeFunctionBody(f);
            }
        }

        symbolTable.popScope();
        return null;
    }

    //*********************
    //?     STRUCTS
    //*********************

    @Override
    public Void visitStructureDefinition(NodeStructureDefinition n) {
        //* Global structs: go to registerGlobalStructures().
        //* Local structs (declared inside a function block) are registered
        //* here, in the current (already open) scope.
        if (n == null) return null;
        registerLocalStructure(n);
        return null;
    }

    @Override
    public Void visitFieldDeclaration(NodeFieldDeclaration n) {
        //* Reached only defensively; fields are normally validated in bulk
        //* by validateStructFields(), which also checks duplicate names.
        if (n == null) return null;

        if (!typeTable.exists(n.getType()) || typeTable.isVoid(n.getType())) {
            errorReporter.reportError(
                    "Type '" + n.getType() + "' is not defined.",
                    n.getLine(), n.getColumn());
        }
        return null;
    }

    //*********************
    //?     FUNCTIONS
    //*********************

    @Override
    public Void visitFunctionDefinition(NodeFunctionDefinition n) {
        //* Signature: go to registerFunctionSignatures().
        //* Body: go to analyzeFunctionBody().
        return null;
    }

    @Override
    public Void visitParameter(NodeParameter n) {
        //* Parameter is already declared in declareParameters().
        return null;
    }

    //**********************8*********
    //?     BLOCKS/INSTRUCTIONS
    //********************************

    @Override
    public Void visitBlock(NodeBlock n) {
        if (n == null) return null;
        symbolTable.pushScope("block");
        if (n.getInstructions() != null) {
            for (ASTNode instruction : n.getInstructions()) {
                if (instruction != null) instruction.accept(this);
            }
        }
        symbolTable.popScope();
        return null;
    }

    //**********************8*********
    //?     DECLARATIONS/ASSIGNMENTS
    //********************************

    @Override
    public Void visitVariableDeclaration(NodeVariableDeclaration n) {
        if (n == null) return null;

        //* Visit the initializer BEFORE declaring, so "entero x = x" reports
        //* use-before-declaration.
        if (n.getInitializer() != null) {
            n.getInitializer().accept(this);
        }

        if (!typeTable.exists(n.getType()) || typeTable.isVoid(n.getType())) {
            errorReporter.reportError(
                    "Type '" + n.getType() + "' is not defined.",
                    n.getLine(), n.getColumn());
        }

        VariableSymbol variableSymbol = new VariableSymbol(
                n.getName(), n.getType(), false, false, n.getLine(), n.getColumn());

        if (!symbolTable.declare(n.getName(), variableSymbol)) {
            errorReporter.reportError(
                    "Variable '" + n.getName() + "' is already defined in this scope.",
                    n.getLine(), n.getColumn());
        }
        return null;
    }

    @Override
    public Void visitArrayDeclaration(NodeArrayDeclaration n) {
        if (n == null) return null;

        if (n.getDimensions() != null) {
            for (ASTNode size : n.getDimensions()) {
                if (size != null) size.accept(this);
            }
        }

        if (!typeTable.exists(n.getType()) || typeTable.isVoid(n.getType())) {
            errorReporter.reportError(
                    "Type '" + n.getType() + "' is not defined.",
                    n.getLine(), n.getColumn());
        }

        if (n.getInitializer() != null) {
            n.getInitializer().accept(this);
        }

        int dimensions = n.getDimensions() != null ? n.getDimensions().size() : 0;
        ArraySymbol arraySymbol = new ArraySymbol(
                n.getName(), n.getType(), dimensions, n.getLine(), n.getColumn());

        if (!symbolTable.declare(n.getName(), arraySymbol)) {
            errorReporter.reportError(
                    "Array '" + n.getName() + "' is already declared in this scope.",
                    n.getLine(), n.getColumn());
        }
        return null;
    }

    @Override
    public Void visitAssignment(NodeAssignment n) {
        if (n == null) return null;
        //* lvalue with DOT/index: only the base identifier's existence is
        //* verified here (field existence is the TypeChecker's job).
        if (n.getLvalue() != null) n.getLvalue().accept(this);
        if (n.getExpression() != null) n.getExpression().accept(this);
        return null;
    }

    //**********************8*********
    //?     CONTROL STRUCTURES
    //********************************

    @Override
    public Void visitRead(NodeRead n) {
        return null;
    }

    @Override
    public Void visitPrint(NodePrint n) {
        if (n == null) return null;
        if (n.getExpressions() != null) {
            for (ASTNode expr : n.getExpressions()) {
                if (expr != null) expr.accept(this);
            }
        }
        return null;
    }

    @Override
    public Void visitIf(NodeIf n) {
        if (n == null) return null;
        if (n.getCondition() != null) n.getCondition().accept(this);
        if (n.getThenBlock() != null) n.getThenBlock().accept(this);

        if (n.getElseIfClauses() != null) {
            for (ElseIfClause clause : n.getElseIfClauses()) {
                if (clause == null) continue;
                if (clause.getCondition() != null) clause.getCondition().accept(this);
                if (clause.getBlock() != null) clause.getBlock().accept(this);
            }
        }

        if (n.getElseBlock() != null) n.getElseBlock().accept(this);
        return null;
    }

    @Override
    public Void visitChoose(NodeChoose n) {
        if (n == null) return null;
        if (n.getExpression() != null) n.getExpression().accept(this);

        //* 'elegir' does not open an extra scope per case; each case's
        //* block already opens its own via visitBlock().
        if (n.getCases() != null) {
            for (NodeChooseCase c : n.getCases()) {
                if (c != null) c.accept(this);
            }
        }

        if (n.getDefaultCase() != null) {
            n.getDefaultCase().accept(this);
        }
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
        if (n == null) return null;
        if (n.getCondition() != null) n.getCondition().accept(this);

        loopDepth++;
        if (n.getBlock() != null) n.getBlock().accept(this);
        loopDepth--;
        return null;
    }

    @Override
    public Void visitDoWhile(NodeDoWhile n) {
        if (n == null) return null;

        loopDepth++;
        if (n.getBlock() != null) n.getBlock().accept(this);
        loopDepth--;

        if (n.getCondition() != null) n.getCondition().accept(this);
        return null;
    }

    @Override
    public Void visitFor(NodeFor n) {
        if (n == null) return null;

        symbolTable.pushScope("for");

        if (n.getInit() != null) n.getInit().accept(this);
        if (n.getCondition() != null) n.getCondition().accept(this);
        if (n.getUpdate() != null) n.getUpdate().accept(this);

        loopDepth++;
        if (n.getBlock() != null) n.getBlock().accept(this);
        loopDepth--;

        symbolTable.popScope();
        return null;
    }

    //**********************8*********
    //?     JUMP STATEMENTS
    //********************************

    @Override
    public Void visitBreak(NodeBreak n) {
        if (loopDepth <= 0) {
            errorReporter.reportError(
                    "'break' can only be used inside a loop.",
                    n.getLine(), n.getColumn());
        }
        return null;
    }

    @Override
    public Void visitContinue(NodeContinue n) {
        if (loopDepth <= 0) {
            errorReporter.reportError(
                    "'continue' can only be used inside a loop.",
                    n.getLine(), n.getColumn());
        }
        return null;
    }

    @Override
    public Void visitReturn(NodeReturn n) {
        if (n == null) return null;

        if (currentFunctionReturnType == null) {
            errorReporter.reportError(
                    "'return' can only be used inside a function.",
                    n.getLine(), n.getColumn());
            return null;
        }

        boolean returnsValue = n.getExpression() != null;

        if (currentFunctionReturnType.equals("void") && returnsValue) {
            errorReporter.reportError(
                    "Function returns void; cannot return a value.",
                    n.getLine(), n.getColumn());
        } else if (!currentFunctionReturnType.equals("void") && !returnsValue) {
            errorReporter.reportError(
                    "Function must return a value of type '" + currentFunctionReturnType + "'.",
                    n.getLine(), n.getColumn());
        }

        if (returnsValue) n.getExpression().accept(this);
        return null;
    }

    //**********************8*********
    //?     EXPRESSIONS
    //********************************

    @Override
    public Void visitBinaryOperation(NodeBinaryOperation n) {
        if (n == null) return null;
        if (n.getLeft() != null) n.getLeft().accept(this);
        if (n.getRight() != null) n.getRight().accept(this);
        return null;
    }

    @Override
    public Void visitUnaryOperation(NodeUnaryOperation n) {
        if (n == null) return null;
        if (n.getOperand() != null) n.getOperand().accept(this);
        return null;
    }

    @Override
    public Void visitIdentifier(NodeIdentifier n) {
        if (n == null) return null;
        if (!symbolTable.exists(n.getId())) {
            errorReporter.reportError(
                    "Variable '" + n.getId() + "' is used before declaration.",
                    n.getLine(), n.getColumn());
        }
        return null;
    }

    @Override
    public Void visitLvalue(NodeLvalue n) {
        return null;
    }

    @Override
    public Void visitFieldAccess(NodeFieldAccess n) {
        if (n == null) return null;
        if (n.getCurrentNode() != null) n.getCurrentNode().accept(this);
        return null;
    }

    @Override
    public Void visitIndexAccess(NodeIndexAccess n) {
        if (n == null) return null;
        if (n.getCurrentNode() != null) n.getCurrentNode().accept(this);
        if (n.getIndexExpression() != null) n.getIndexExpression().accept(this);
        return null;
    }

    @Override
    public Void visitFunctionCall(NodeFunctionCall n) {
        if (n == null) return null;

        if (!symbolTable.existsFunction(n.getFunctionName())) {
            errorReporter.reportError(
                    "Function '" + n.getFunctionName() + "' is not declared.",
                    n.getLine(), n.getColumn());
        }

        if (n.getArguments() != null) {
            for (ASTNode arg : n.getArguments()) {
                if (arg != null) arg.accept(this);
            }
        }
        return null;
    }

    @Override
    public Void visitArrayLiteral(NodeArrayLiteral n) {
        if (n == null) return null;
        if (n.getElements() != null) {
            for (ASTNode element : n.getElements()) {
                if (element != null) element.accept(this);
            }
        }
        return null;
    }

    //**********************8*********
    //?     LITERALS
    //********************************

    @Override public Void visitIntegerLiteral(NodeIntegerLiteral n) { return null; }
    @Override public Void visitFloatLiteral(NodeFloatLiteral n)     { return null; }
    @Override public Void visitCharLiteral(NodeCharLiteral n)       { return null; }
    @Override public Void visitStringLiteral(NodeStringLiteral n)   { return null; }
    @Override public Void visitBooleanLiteral(NodeBooleanLiteral n) { return null; }

    //**********************8*********
    //?     PRIVATE HELPERS - STRUCTS
    //********************************

    private void registerGlobalStructures(List<NodeStructureDefinition> structures) {
        for (NodeStructureDefinition s : structures) {
            if (s == null) continue;
            StructureSymbol structSymbol = new StructureSymbol(s.getName(), s.getLine(), s.getColumn());
            if (!symbolTable.declare(s.getName(), structSymbol)) {
                errorReporter.reportError(
                        "Struct '" + s.getName() + "' is already declared.",
                        s.getLine(), s.getColumn());
            } else {
                typeTable.registerStruct(s.getName());
            }
        }

        for (NodeStructureDefinition s : structures) {
            if (s != null) validateStructFields(s);
        }
    }

    private void registerLocalStructure(NodeStructureDefinition n) {
        StructureSymbol structSymbol = new StructureSymbol(n.getName(), n.getLine(), n.getColumn());
        if (!symbolTable.declare(n.getName(), structSymbol)) {
            errorReporter.reportError(
                    "Struct '" + n.getName() + "' is already declared in this scope.",
                    n.getLine(), n.getColumn());
        } else {
            typeTable.registerStruct(n.getName());
        }
        validateStructFields(n);
    }

    private void validateStructFields(NodeStructureDefinition n) {
        if (n.getFields() == null) return;

        Set<String> seenFields = new HashSet<>();
        for (NodeFieldDeclaration field : n.getFields()) {
            if (field == null) continue;

            if (!seenFields.add(field.getFieldName())) {
                errorReporter.reportError(
                        "Field '" + field.getFieldName() + "' is already declared in struct '" + n.getName() + "'.",
                        field.getLine(), field.getColumn());
                continue;
            }

            if (!typeTable.exists(field.getType()) || typeTable.isVoid(field.getType())) {
                errorReporter.reportError(
                        "Type '" + field.getType() + "' is not defined.",
                        field.getLine(), field.getColumn());
                continue;
            }

            if (!field.isArray() && field.getType().equals(n.getName())) {
                errorReporter.reportError(
                        "Struct '" + n.getName() + "' cannot contain itself by value.",
                        field.getLine(), field.getColumn());
            }
        }
    }

    //**********************8*********
    //?
    //********************************

    private void registerFunctionSignatures(List<NodeFunctionDefinition> functions) {
        for (NodeFunctionDefinition f : functions) {
            if (f == null) continue;
            FunctionSymbol function = new FunctionSymbol(
                    f.getName(), f.getReturnType(), buildParameterTypes(f.getParameters()),
                    f.getLine(), f.getColumn());

            if (!symbolTable.declareFunction(function)) {
                errorReporter.reportError(
                        "Function '" + f.getName() + "' is already declared.",
                        f.getLine(), f.getColumn());
            }
        }
    }

    private void analyzeFunctionBody(NodeFunctionDefinition f) {
        symbolTable.pushScope("func_" + f.getName());
        String savedReturnType = currentFunctionReturnType;
        currentFunctionReturnType = f.getReturnType();

        declareParameters(f.getParameters());

        if (f.getBlock() != null) f.getBlock().accept(this);

        currentFunctionReturnType = savedReturnType;
        symbolTable.popScope();
    }

    private void declareParameters(List<NodeParameter> params) {
        if (params == null) return;
        for (NodeParameter p : params) {
            if (p == null) continue;

            if (!typeTable.exists(p.getType()) || typeTable.isVoid(p.getType())) {
                errorReporter.reportError(
                        "Type '" + p.getType() + "' is not defined.",
                        p.getLine(), p.getColumn());
                continue;
            }

            VariableSymbol param = new VariableSymbol(
                    p.getName(), p.getType(), p.isArray(), p.isStruct(), p.getLine(), p.getColumn());

            if (!symbolTable.declare(p.getName(), param)) {
                errorReporter.reportError(
                        "Parameter '" + p.getName() + "' is already declared.",
                        p.getLine(), p.getColumn());
            }
        }
    }

    private List<ParameterType> buildParameterTypes(List<NodeParameter> params) {
        List<ParameterType> types = new ArrayList<>();
        if (params != null) {
            for (NodeParameter p : params) {
                if (p != null) types.add(new ParameterType(p.getType(), p.isArray(), p.isStruct()));
            }
        }
        return types;
    }
}