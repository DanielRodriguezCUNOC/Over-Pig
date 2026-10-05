package com.piglatin.zetariano.domain.semantic;

import com.piglatin.zetariano.domain.ast.expressions.*;
import com.piglatin.zetariano.domain.ast.expressions.literals.*;
import com.piglatin.zetariano.domain.ast.principal.*;
import com.piglatin.zetariano.domain.ast.statements.*;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import com.piglatin.zetariano.domain.symboltable.*;
import com.piglatin.zetariano.domain.types.ZetarianoTypeTable;
import lombok.Getter;

import java.util.List;

@Getter
public class ZetarianoSymbolTableBuilder implements Visitor<Void> {

    private final ZetarianoSymbolTable symbolTable;
    private final ZetarianoTypeTable typeTable;
    private final SemanticErrorReporter errorReporter;
    private int loopDepth = 0;
    private String currentClassName = null;
    private String currentMethodReturnType = null;

    public ZetarianoSymbolTableBuilder(ZetarianoTypeTable typeTable, SemanticErrorReporter errorReporter) {
        this.symbolTable = new ZetarianoSymbolTable();
        this.typeTable = typeTable;
        this.errorReporter = errorReporter;
    }

    public ZetarianoSymbolTableBuilder(ZetarianoSymbolTable symbolTable, ZetarianoTypeTable typeTable, SemanticErrorReporter errorReporter) {
        this.symbolTable = symbolTable;
        this.typeTable = typeTable;
        this.errorReporter = errorReporter;
    }

    @Override
    public Void visitProgram(NodeProgram n) {
        if (n == null) return null;

        if (n.getClassDeclaration() != null) {
            n.getClassDeclaration().accept(this);
        }
        return null;
    }

    @Override
    public Void visitImport(NodeImport n) {
        return null;
    }

    @Override
    public Void visitClassDeclaration(NodeClassDeclaration n) {
        if (n == null) return null;

        String className = n.getName();

        ClassSymbol classSymbol = new ClassSymbol(className, n.getLine(), n.getColumn());
        if (!symbolTable.declare(className, classSymbol)) {
            errorReporter.reportError(
                    "Class '" + className + "' is already declared.",
                    n.getLine(), n.getColumn());
        }else{
            typeTable.registerClass(className);
        }

        symbolTable.pushScope("class_" + className);
        this.currentClassName = className;

        for (ASTNode member : n.getMembers()) {
            if (member instanceof NodeMethodDeclaration m) {
                registerMethodSignature(m);
            } else if (member instanceof NodeConstructorDeclaration c) {
                registerConstructorSignature(c);
            } else if (member instanceof NodeFieldDeclaration f) {
                registerField(f);
            }
        }

        for (ASTNode member : n.getMembers()) {
            if (member instanceof NodeMethodDeclaration m) {
                analyzeMethodBody(m);
            } else if (member instanceof NodeConstructorDeclaration c) {
                analyzeConstructorBody(c);
            }
            // Los campos ya fueron registrados.
        }

        symbolTable.popScope();
        this.currentClassName = null;
        return null;
    }

    @Override
    public Void visitFieldDeclaration(NodeFieldDeclaration n) {

        if (!typeTable.exists(n.getType())) {
            errorReporter.reportError(
                    "Type '" + n.getType() + "' is not defined.",
                    n.getLine(), n.getColumn());
        }

        if (n.getInitializer() != null) {
            n.getInitializer().accept(this);
        }
        return null;
    }

    @Override
    public Void visitMethodDeclaration(NodeMethodDeclaration n) {
        //* Go to visitClassDeclaration.
        //* Go to analyzeMethodBody.
        return null;
    }

    @Override
    public Void visitConstructorDeclaration(NodeConstructorDeclaration n) {
        return null;
    }

    @Override
    public Void visitParameter(NodeParameter n) {
        //* Parameter is already declared in analyzeMethodBody or analyzeConstructorBody
        return null;
    }

    @Override
    public Void visitBlock(NodeBlock n) {
        if (n == null) return null;
        symbolTable.pushScope("block");
        for (ASTNode instruction : n.getInstructions()) {
            if (instruction != null) instruction.accept(this);
        }
        symbolTable.popScope();
        return null;
    }

    @Override
    public Void visitVariableDeclaration(NodeVariableDeclaration n) {
        if (n == null) return null;


        if (n.getInitializer() != null) {
            n.getInitializer().accept(this);
        }

        if (!typeTable.exists(n.getType())) {
            errorReporter.reportError(
                    "Type '" + n.getType() + "' is not defined.",
                    n.getLine(), n.getColumn());
        }

        VariableSymbol variableSymbol = new VariableSymbol(
                n.getName(), n.getType(), n.getLine(), n.getColumn());

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

        if (n.getSize() != null) {
            n.getSize().accept(this);
        }

        if (!typeTable.exists(n.getType())) {
            errorReporter.reportError(
                    "Type '" + n.getType() + "' is not defined.",
                    n.getLine(), n.getColumn());
        }

        if (n.getInitializer() != null) {
            n.getInitializer().accept(this);
        }

        String arrayType = n.getType() + "[]".repeat(n.getDimensions());

        ArraySymbol arraySymbol = new ArraySymbol(
                n.getName(), "[]" + n.getType(),
                n.getDimensions(), n.getType(), n.getLine(), n.getColumn());

        if (!symbolTable.declare(n.getName(), arraySymbol)) {
            errorReporter.reportError(
                    "Array '" + n.getName() + "' is already declared in this scope.",
                    n.getLine(), n.getColumn());
        }
        return null;
    }

    @Override
    public Void visitArrayInitializer(NodeArrayInitializer n) {
        if (n == null) return null;
        for (ASTNode element : n.getElements()) {
            if (element != null) element.accept(this);
        }
        return null;
    }

    @Override
    public Void visitAssignment(NodeAssignment n) {
        if (n == null) return null;
        if (n.getTarget() != null) n.getTarget().accept(this);
        if (n.getExpression() != null) n.getExpression().accept(this);
        return null;
    }

    @Override
    public Void visitRead(NodeRead n) {
        return null;
    }

    @Override
    public Void visitPrint(NodePrint n) {
        if (n == null) return null;
        for (ASTNode item : n.getExpressions()) {
            if (item != null) item.accept(this);
        }
        return null;
    }

    @Override
    public Void visitIf(NodeIf n) {
        if (n == null) return null;
        if (n.getCondition() != null) n.getCondition().accept(this);
        if (n.getThenBlock() != null) n.getThenBlock().accept(this);
        if (n.getElseBlock() != null) n.getElseBlock().accept(this);
        return null;
    }

    @Override
    public Void visitSwitch(NodeSwitch n) {
        if (n == null) return null;
        if (n.getExpression() != null) n.getExpression().accept(this);

        for (NodeCase c : n.getCases()) {
            if (c != null) c.accept(this);
        }
        if (n.getDefaultBlock() != null) n.getDefaultBlock().accept(this);
        return null;
    }

    @Override
    public Void visitCase(NodeCase n) {
        if (n == null) return null;
        if (n.getExpression() != null) n.getExpression().accept(this);
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

        if (n.getInitializer() != null) n.getInitializer().accept(this);
        if (n.getCondition() != null)      n.getCondition().accept(this);
        if (n.getUpdate() != null)         n.getUpdate().accept(this);

        loopDepth++;
        if (n.getBlock() != null) n.getBlock().accept(this);
        loopDepth--;

        symbolTable.popScope();
        return null;
    }

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

        if (currentMethodReturnType == null) {
            errorReporter.reportError(
                    "'return' can only be used inside a method.",
                    n.getLine(), n.getColumn());
            return null;
        }

        boolean returnsValue = n.getExpression() != null;

        if (currentMethodReturnType.equals("void") && returnsValue) {
            errorReporter.reportError(
                    "Method returns void; cannot return a value.",
                    n.getLine(), n.getColumn());
        } else if (!currentMethodReturnType.equals("void") && !returnsValue) {
            errorReporter.reportError(
                    "Method must return a value of type '" + currentMethodReturnType + "'.",
                    n.getLine(), n.getColumn());
        }

        if (returnsValue) n.getExpression().accept(this);
        return null;
    }


    @Override
    public Void visitTernaryExpression(NodeTernaryExpression n) {
        if (n == null) return null;
        n.getCondition().accept(this);
        n.getTrueExpression().accept(this);
        n.getFalseExpression().accept(this);
        return null;
    }

    @Override
    public Void visitBinaryExpression(NodeBinaryExpression n) {
        if (n == null) return null;
        if (n.getLeft() != null)  n.getLeft().accept(this);
        if (n.getRight() != null) n.getRight().accept(this);
        return null;
    }

    @Override
    public Void visitUnaryExpression(NodeUnaryExpression n) {
        if (n == null) return null;
        if (n.getExpression() != null) n.getExpression().accept(this);
        return null;
    }

    @Override
    public Void visitIdentifier(NodeIdentifier n) {
        if ("this".equals(n.getName())) {
            return null; // 'this' is always available within a class
        }
        if (!symbolTable.exists(n.getName())) {
            errorReporter.reportError(
                    "Variable '" + n.getName() + "' is used before declaration.",
                    n.getLine(), n.getColumn());
        }
        return null;
    }

    @Override
    public Void visitFieldAccess(NodeFieldAccess n) {
        if (n == null) return null;
        if (n.getTarget() != null) n.getTarget().accept(this);
        return null;
    }

    @Override
    public Void visitIndexAccess(NodeIndexAccess n) {
        if (n == null) return null;
        if (n.getArrayTarget() != null) n.getArrayTarget().accept(this);
        if (n.getIndex() != null) n.getIndex().accept(this);
        return null;
    }

    @Override
    public Void visitMethodCall(NodeMethodCall n) {
        if (n == null) return null;

        if (n.getTarget() == null) {
            if (currentClassName == null) {
                errorReporter.reportError(
                        "Method call outside of a class context.",
                        n.getLine(), n.getColumn());
            } else if (!symbolTable.existsMethod(currentClassName, n.getMethodName())) {
                errorReporter.reportError(
                        "Method '" + n.getMethodName() + "' is not declared in class '"
                                + currentClassName + "'.",
                        n.getLine(), n.getColumn());
            }
        } else {
            n.getTarget().accept(this);

        }

        if (n.getArguments() != null) {
            for (ASTNode arg : n.getArguments()) {
                if (arg != null) arg.accept(this);
            }
        }
        return null;
    }

    @Override
    public Void visitNewObject(NodeNewObject n) {
        if (n == null) return null;

        if (!symbolTable.exists(n.getClassName())) {
            errorReporter.reportError(
                    "Class '" + n.getClassName() + "' is not defined.",
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
    public Void visitNewArray(NodeNewArray n) {
        if (n == null) return null;

        if (!typeTable.exists(n.getType())) {
            errorReporter.reportError(
                    "Type '" + n.getType() + "' is not defined.",
                    n.getLine(), n.getColumn());
        }

        for (ASTNode dim : n.getDimensions()) {
            if (dim != null) dim.accept(this);
        }
        return null;
    }


    @Override public Void visitIntegerLiteral(NodeIntegerLiteral n) { return null; }
    @Override public Void visitDecimalLiteral(NodeDecimalLiteral n) { return null; }
    @Override public Void visitCharLiteral(NodeCharLiteral n)       { return null; }
    @Override public Void visitStringLiteral(NodeStringLiteral n)   { return null; }
    @Override public Void visitBooleanLiteral(NodeBooleanLiteral n) { return null; }
    @Override public Void visitNullLiteral(NodeNullLiteral n)       { return null; }


    private void registerField(NodeFieldDeclaration f) {
        if (!typeTable.exists(f.getType()) || typeTable.isVoid(f.getType())) {
            errorReporter.reportError(
                    "Invalid field type '" + f.getType() + "'.",
                    f.getLine(), f.getColumn());
            // NO return — seguimos declarando para evitar cascada
        }
        VariableSymbol field = new VariableSymbol(
                f.getName(), f.getType(), f.getLine(), f.getColumn());
        if (!symbolTable.declare(f.getName(), field)) {
            errorReporter.reportError(
                    "Field '" + f.getName() + "' is already declared.",
                    f.getLine(), f.getColumn());
        }
    }

    private void registerMethodSignature(NodeMethodDeclaration m) {
        MethodSymbol method = new MethodSymbol(
                m.getName(), m.getReturnType(), buildParamTypes(m.getParameters()),
                m.getLine(), m.getColumn());

        if (!symbolTable.declareMethod(currentClassName, method)) {
            errorReporter.reportError(
                    "Method '" + m.getName() + "' is already declared in class '"
                            + currentClassName + "'.",
                    m.getLine(), m.getColumn());
        }
    }

    private void registerConstructorSignature(NodeConstructorDeclaration c) {
        MethodSymbol ctor = new MethodSymbol(
                c.getName(), "void", buildParamTypes(c.getParameters()),
                c.getLine(), c.getColumn());

        if (!symbolTable.declareMethod(currentClassName, ctor)) {
            errorReporter.reportError(
                    "Constructor '" + c.getName() + "' is already declared in class '"
                            + currentClassName + "'.",
                    c.getLine(), c.getColumn());
        }
    }

    private void analyzeMethodBody(NodeMethodDeclaration m) {
        symbolTable.pushScope("method_" + m.getName());
        String savedReturn = currentMethodReturnType;
        currentMethodReturnType = m.getReturnType();

        declareParameters(m.getParameters());

        if (m.getBody() != null) m.getBody().accept(this);

        currentMethodReturnType = savedReturn;
        symbolTable.popScope();
    }

    private void analyzeConstructorBody(NodeConstructorDeclaration c) {
        symbolTable.pushScope("ctor_" + c.getName());
        String savedReturn = currentMethodReturnType;
        currentMethodReturnType = "void";

        declareParameters(c.getParameters());

        if (c.getBody() != null) c.getBody().accept(this);

        currentMethodReturnType = savedReturn;
        symbolTable.popScope();
    }

    private void declareParameters(java.util.List<NodeParameter> params) {
        if (params == null) return;
        for (NodeParameter p : params) {
            if (!typeTable.exists(p.getType())) {
                errorReporter.reportError(
                        "Type '" + p.getType() + "' is not defined.",
                        p.getLine(), p.getColumn());
                continue;
            }
            VariableSymbol param = new VariableSymbol(
                    p.getName(), p.getType(), p.getLine(), p.getColumn());
            if (!symbolTable.declare(p.getName(), param)) {
                errorReporter.reportError(
                        "Parameter '" + p.getName() + "' is already declared.",
                        p.getLine(), p.getColumn());
            }
        }
    }

    private List<String> buildParamTypes(java.util.List<NodeParameter> params) {
        java.util.List<String> types = new java.util.ArrayList<>();
        if (params != null) {
            for (NodeParameter p : params) types.add(p.getType());
        }
        return types;
    }
}