package com.piglatin.zetariano.domain.semantic;

import com.piglatin.zetariano.domain.ast.expressions.*;
import com.piglatin.zetariano.domain.ast.expressions.literals.*;
import com.piglatin.zetariano.domain.ast.principal.*;
import com.piglatin.zetariano.domain.ast.statements.*;
import com.piglatin.zetariano.domain.ast.visitor.Visitor;
import com.piglatin.zetariano.domain.symboltable.*;
import com.piglatin.zetariano.domain.types.TypeTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TypeChecker implements Visitor<String> {

    private final SymbolTable symbolTable;
    private final TypeTable typeTable;
    private final SemanticErrorReporter errorReporter;
    private final ConstantFolder constantFolder;
    private String currentClassName;
    private String currentMethodReturnType;

    public TypeChecker(SymbolTable symbolTable, TypeTable typeTable, SemanticErrorReporter errorReporter) {
        this.symbolTable = symbolTable;
        this.typeTable = typeTable;
        this.errorReporter = errorReporter;
        this.constantFolder = new ConstantFolder(errorReporter);
        this.currentClassName = null;
        this.currentMethodReturnType = null;
    }

    // ============================================================
    // PROGRAM & DECLARATIONS
    // ============================================================

    @Override
    public String visitProgram(NodeProgram n) {
        if (n == null) return null;

        symbolTable.pushScope("global");
        if (n.getClassDeclaration() != null) {
            n.getClassDeclaration().accept(this);
        }
        symbolTable.popScope();
        return null;
    }

    @Override
    public String visitImport(NodeImport n) {
        return null;
    }

    @Override
    public String visitClassDeclaration(NodeClassDeclaration n) {
        if (n == null) return null;

        this.currentClassName = n.getName();
        symbolTable.pushScope("class_" + currentClassName);

        // Pre-register all fields into current scope and classFields map

        for (ASTNode member : n.getMembers()) {
            if (member instanceof NodeFieldDeclaration f) {
                int dims = f.isArray() ? Math.max(1, f.getDimensions()) : 0;
                String fType = f.getType() + (f.isArray() ? "[]".repeat(dims) : "");
                if (f.isArray()) {
                    ArraySymbol as = new ArraySymbol(f.getName(), "SERIES_" + f.getType(),
                            dims, f.getType(), f.getLine(), f.getColumn());
                    symbolTable.declare(f.getName(), as);
                    symbolTable.declareField(currentClassName, f.getName(), as);
                } else {
                    VariableSymbol vs = new VariableSymbol(f.getName(), fType, f.getLine(), f.getColumn());
                    symbolTable.declare(f.getName(), vs);
                    symbolTable.declareField(currentClassName, f.getName(), vs);
                }
            }
        }

        // Validate field initializers
        for (ASTNode member : n.getMembers()) {
            if (member instanceof NodeFieldDeclaration f) {
                f.accept(this);
            }
        }

        // Validate constructors and methods
        for (ASTNode member : n.getMembers()) {
            if (member instanceof NodeConstructorDeclaration c) {
                c.accept(this);
            } else if (member instanceof NodeMethodDeclaration m) {
                m.accept(this);
            }
        }

        symbolTable.popScope();
        this.currentClassName = null;
        return null;
    }

    @Override
    public String visitFieldDeclaration(NodeFieldDeclaration n) {
        if (n == null) return null;

        if (n.getInitializer() != null) {
            String initType = n.getInitializer().accept(this);
            int dims = n.isArray() ? Math.max(1, n.getDimensions()) : 0;
            String fieldType = n.getType() + (n.isArray() ? "[]".repeat(dims) : "");
            if (initType != null && !isAssignable(fieldType, initType)) {
                errorReporter.reportError(
                        "Cannot assign '" + initType + "' to field '" + n.getName() + "' of type '" + fieldType + "'.",
                        n);
            }

            Object constantValue = constantFolder.evaluate(n.getInitializer());
            if (constantValue !=null) {
                Symbol fieldSym = symbolTable.lookup(n.getName());
                if (fieldSym instanceof VariableSymbol vs)
                    vs.setConstantValue(constantValue);
            }
        }
        return null;
    }

    @Override
    public String visitMethodDeclaration(NodeMethodDeclaration n) {
        if (n == null) return null;

        this.currentMethodReturnType = n.getReturnType();
        symbolTable.pushScope("method_" + n.getName());

        if (n.getParameters() != null) {
            for (NodeParameter p : n.getParameters()) {
                if (p == null) continue;
                String pType = p.getType() + (p.isArray() ? "[]" : "");
                if (p.isArray()) {
                    symbolTable.declare(p.getName(), new ArraySymbol(
                            p.getName(), "SERIES_" + p.getType(), 1, p.getType(), p.getLine(), p.getColumn()));
                } else {
                    symbolTable.declare(p.getName(), new VariableSymbol(
                            p.getName(), pType, p.getLine(), p.getColumn()));
                }
            }
        }

        if (n.getBody() != null) {
            n.getBody().accept(this);
        }

        symbolTable.popScope();
        this.currentMethodReturnType = null;
        return null;
    }

    @Override
    public String visitConstructorDeclaration(NodeConstructorDeclaration n) {
        if (n == null) return null;

        this.currentMethodReturnType = "void";
        symbolTable.pushScope("ctor_" + n.getName());

        if (n.getParameters() != null) {
            for (NodeParameter p : n.getParameters()) {
                if (p == null) continue;
                String pType = p.getType() + (p.isArray() ? "[]" : "");
                if (p.isArray()) {
                    symbolTable.declare(p.getName(), new ArraySymbol(
                            p.getName(), "SERIES_" + p.getType(), 1, p.getType(), p.getLine(), p.getColumn()));
                } else {
                    symbolTable.declare(p.getName(), new VariableSymbol(
                            p.getName(), pType, p.getLine(), p.getColumn()));
                }
            }
        }

        if (n.getBody() != null) {
            n.getBody().accept(this);
        }

        symbolTable.popScope();
        this.currentMethodReturnType = null;
        return null;
    }

    @Override
    public String visitParameter(NodeParameter n) {
        return null;
    }

    // ============================================================
    // STATEMENTS
    // ============================================================

    @Override
    public String visitBlock(NodeBlock n) {
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

    @Override
    public String visitVariableDeclaration(NodeVariableDeclaration n) {
        if (n == null) return null;

        String declaredType = n.getType();
        if (n.getInitializer() != null) {
            String initType = n.getInitializer().accept(this);
            if (initType != null && !isAssignable(declaredType, initType)) {
                errorReporter.reportError(
                        "Cannot assign '" + initType + "' to '" + declaredType + "'.", n);
            }
        }

        VariableSymbol vs = new VariableSymbol(n.getName(), declaredType, n.getLine(), n.getColumn());

        if (n.getInitializer() != null) {
            Object constantValue = constantFolder.evaluate(n.getInitializer());
            if (constantValue != null)
                vs.setConstantValue(constantValue);

        }

        symbolTable.declare(n.getName(), vs);
        return null;
    }

    @Override
    public String visitArrayDeclaration(NodeArrayDeclaration n) {
        if (n == null) return null;

        int dims = Math.max(1, n.getDimensions());
        String arrayType = n.getType() + "[]".repeat(dims);

        if (n.getSize() != null) {
            String exprType = n.getSize().accept(this);
            if (exprType != null && !"int".equals(exprType)) {
                errorReporter.reportError(
                        "Array size must be 'int', found '" + exprType + "'.", n);
            }

            constantFolder.evaluate(n.getSize());
        }

        if (n.getInitializer() != null) {
            String initType = n.getInitializer().accept(this);
            if (initType != null && !isAssignable(arrayType, initType)) {
                errorReporter.reportError(
                        "Cannot assign '" + initType + "' to array of type '" + arrayType + "'.", n);
            }
        }

        symbolTable.declare(n.getName(), new ArraySymbol(
                n.getName(), "SERIES_" + n.getType(), n.getDimensions(), n.getType(), n.getLine(), n.getColumn()));
        return null;
    }

    @Override
    public String visitArrayInitializer(NodeArrayInitializer n) {
        if (n == null) return null;

        List<NodeExpression> elements = n.getElements();
        if (elements == null || elements.isEmpty()) {
            return "empty_array";
        }

        String firstType = elements.get(0).accept(this);
        for (int i = 1; i < elements.size(); i++) {
            String elemType = elements.get(i).accept(this);
            if (firstType != null && elemType != null && !isAssignable(firstType, elemType) && !isAssignable(elemType, firstType)) {
                errorReporter.reportError(
                        "Incompatible types in array initializer: '" + firstType + "' and '" + elemType + "'.",
                        elements.get(i));
            }
        }

        if (firstType == null) return null;
        return firstType + "[]";
    }

    @Override
    public String visitAssignment(NodeAssignment n) {
        if (n == null) return null;

        String leftType = n.getLvalue() != null ? n.getLvalue().accept(this) : null;
        String rightType = n.getExpression() != null ? n.getExpression().accept(this) : null;

        if (leftType == null || rightType == null) return null;

        String op = n.getOperator();
        switch (op) {
            case "=" -> {
                if (!isAssignable(leftType, rightType)) {
                    errorReporter.reportError(
                            "Cannot assign '" + rightType + "' to '" + leftType + "'.", n);
                }
            }
            case "+=" -> {
                if ("String".equals(leftType)) {
                    if (!isStringConcatenable(rightType)) {
                        errorReporter.reportError("Cannot append '" + rightType + "' to 'String'.", n);
                    }
                } else if (isNumeric(leftType) && isNumeric(rightType)) {
                    if ("int".equals(leftType) && "double".equals(rightType)) {
                        errorReporter.reportError("Cannot assign 'double' to 'int' in compound assignment.", n);
                    }
                } else {
                    errorReporter.reportError(
                            "Operator '+=' cannot be applied to '" + leftType + "' and '" + rightType + "'.", n);
                }
            }
            case "-=", "*=" -> {
                if (isNumeric(leftType) && isNumeric(rightType)) {
                    if ("int".equals(leftType) && "double".equals(rightType)) {
                        errorReporter.reportError("Cannot assign 'double' to 'int' in compound assignment.", n);
                    }
                } else {
                    errorReporter.reportError(
                            "Operator '" + op + "' cannot be applied to '" + leftType + "' and '" + rightType + "'.", n);
                }
            }
            case "++", "--" -> {
                if (!isNumeric(leftType)) {
                    errorReporter.reportError(
                            "Operator '" + op + "' requires numeric operand, found '" + leftType + "'.", n);
                }
            }
            default -> errorReporter.reportError("Unknown assignment operator '" + op + "'.", n);
        }
        return null;
    }

    @Override
    public String visitRead(NodeRead n) {
        return null;
    }

    @Override
    public String visitPrint(NodePrint n) {
        if (n == null) return null;

        if (n.getExpressions() != null) {
            for (NodeExpression expr : n.getExpressions()) {
                if (expr != null) {
                    String t = expr.accept(this);
                    if ("void".equals(t)) {
                        errorReporter.reportError("Cannot print expression of type 'void'.", expr);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String visitIf(NodeIf n) {
        if (n == null) return null;

        if (n.getCondition() != null) {
            String condType = n.getCondition().accept(this);
            if (condType != null && !"boolean".equals(condType)) {
                errorReporter.reportError(
                        "Condition in 'if' must be 'boolean', found '" + condType + "'.", n.getCondition());
            }
        }

        if (n.getThenBlock() != null) n.getThenBlock().accept(this);
        if (n.getElseBlock() != null) n.getElseBlock().accept(this);
        return null;
    }

    @Override
    public String visitSwitch(NodeSwitch n) {
        if (n == null) return null;

        String switchType = n.getExpression() != null ? n.getExpression().accept(this) : null;
        if (switchType != null && !isSwitchableType(switchType)) {
            errorReporter.reportError(
                    "Switch expression must be of type 'int', 'char', or 'String', found '" + switchType + "'.",
                    n.getExpression());
        }

        if (n.getCases() != null) {
            for (NodeCase c : n.getCases()) {
                if (c == null) continue;
                if (c.getExpression() != null) {
                    String caseType = c.getExpression().accept(this);
                    if (caseType != null && switchType != null
                            && !isAssignable(switchType, caseType) && !switchType.equals(caseType)) {
                        errorReporter.reportError(
                                "Case expression type '" + caseType + "' is incompatible with switch expression type '"
                                        + switchType + "'.",
                                c.getExpression());
                    }
                }
                if (c.getBlock() != null) c.getBlock().accept(this);
            }
        }

        if (n.getDefaultBlock() != null) n.getDefaultBlock().accept(this);
        return null;
    }

    @Override
    public String visitCase(NodeCase n) {
        if (n == null) return null;
        if (n.getExpression() != null) n.getExpression().accept(this);
        if (n.getBlock() != null) n.getBlock().accept(this);
        return null;
    }

    @Override
    public String visitWhile(NodeWhile n) {
        if (n == null) return null;

        if (n.getCondition() != null) {
            String condType = n.getCondition().accept(this);
            if (condType != null && !"boolean".equals(condType)) {
                errorReporter.reportError(
                        "Condition in 'while' must be 'boolean', found '" + condType + "'.", n.getCondition());
            }
        }

        if (n.getBlock() != null) n.getBlock().accept(this);
        return null;
    }

    @Override
    public String visitDoWhile(NodeDoWhile n) {
        if (n == null) return null;

        if (n.getBlock() != null) n.getBlock().accept(this);

        if (n.getCondition() != null) {
            String condType = n.getCondition().accept(this);
            if (condType != null && !"boolean".equals(condType)) {
                errorReporter.reportError(
                        "Condition in 'do-while' must be 'boolean', found '" + condType + "'.", n.getCondition());
            }
        }
        return null;
    }

    @Override
    public String visitFor(NodeFor n) {
        if (n == null) return null;

        symbolTable.pushScope("for");

        if (n.getInitializer() != null) n.getInitializer().accept(this);

        if (n.getCondition() != null) {
            String condType = n.getCondition().accept(this);
            if (condType != null && !"boolean".equals(condType)) {
                errorReporter.reportError(
                        "Condition in 'for' must be 'boolean', found '" + condType + "'.", n.getCondition());
            }
        }

        if (n.getUpdate() != null) n.getUpdate().accept(this);
        if (n.getBlock() != null) n.getBlock().accept(this);

        symbolTable.popScope();
        return null;
    }

    @Override
    public String visitBreak(NodeBreak n) {
        return null;
    }

    @Override
    public String visitContinue(NodeContinue n) {
        return null;
    }

    @Override
    public String visitReturn(NodeReturn n) {
        if (n == null) return null;

        if (currentMethodReturnType == null) return null;

        boolean hasExpr = n.getExpression() != null;
        if ("void".equals(currentMethodReturnType)) {
            if (hasExpr) {
                errorReporter.reportError("Cannot return a value from a method with void return type.", n);
            }
        } else {
            if (!hasExpr) {
                errorReporter.reportError(
                        "Method must return a value of type '" + currentMethodReturnType + "'.", n);
            } else {
                String returnExprType = n.getExpression().accept(this);
                if (returnExprType != null && !isAssignable(currentMethodReturnType, returnExprType)) {
                    errorReporter.reportError(
                            "Incompatible return type: expected '" + currentMethodReturnType + "', found '"
                                    + returnExprType + "'.",
                            n);
                }
            }
        }
        return null;
    }

    // ============================================================
    // EXPRESSIONS
    // ============================================================

    @Override
    public String visitTernaryExpression(NodeTernaryExpression n) {
        if (n == null) return null;

        if (n.getCondition() != null) {
            String condType = n.getCondition().accept(this);
            if (condType != null && !"boolean".equals(condType)) {
                errorReporter.reportError(
                        "Ternary condition must be 'boolean', found '" + condType + "'.", n.getCondition());
            }
        }

        String trueType = n.getTrueExpression() != null ? n.getTrueExpression().accept(this) : null;
        String falseType = n.getFalseExpression() != null ? n.getFalseExpression().accept(this) : null;

        if (trueType == null || falseType == null) return null;

        if (trueType.equals(falseType)) return trueType;

        if (isNumeric(trueType) && isNumeric(falseType)) {
            return ("double".equals(trueType) || "double".equals(falseType)) ? "double" : "int";
        }

        if ("null".equals(trueType) && isReferenceType(falseType)) return falseType;
        if ("null".equals(falseType) && isReferenceType(trueType)) return trueType;

        errorReporter.reportError(
                "Incompatible types in ternary expression: '" + trueType + "' and '" + falseType + "'.", n);
        return null;
    }

    @Override
    public String visitBinaryExpression(NodeBinaryExpression n) {
        if (n == null) return null;

        String leftType = n.getLeft() != null ? n.getLeft().accept(this) : null;
        String rightType = n.getRight() != null ? n.getRight().accept(this) : null;

        if (leftType == null || rightType == null) return null;

        String op = n.getOperator();
        switch (op) {
            case "+" -> {
                if ("String".equals(leftType) || "String".equals(rightType)) {
                    if ("String".equals(leftType) && !isStringConcatenable(rightType)) {
                        errorReporter.reportError("Cannot concatenate 'String' with '" + rightType + "'.", n);
                    } else if ("String".equals(rightType) && !isStringConcatenable(leftType)) {
                        errorReporter.reportError("Cannot concatenate '" + leftType + "' with 'String'.", n);
                    }
                    return "String";
                }
                if (isNumeric(leftType) && isNumeric(rightType)) {
                    return ("double".equals(leftType) || "double".equals(rightType)) ? "double" : "int";
                }
                errorReporter.reportError(
                        "Operator '+' cannot be applied to '" + leftType + "' and '" + rightType + "'.", n);
                return null;
            }
            case "-", "*", "/" -> {
                if (isNumeric(leftType) && isNumeric(rightType)) {
                    return ("double".equals(leftType) || "double".equals(rightType)) ? "double" : "int";
                }
                errorReporter.reportError(
                        "Operator '" + op + "' requires numeric operands, found '" + leftType + "' and '"
                                + rightType + "'.",
                        n);
                return null;
            }
            case "%" -> {
                if (("int".equals(leftType) || "char".equals(leftType))
                        && ("int".equals(rightType) || "char".equals(rightType))) {
                    return "int";
                }
                errorReporter.reportError(
                        "Operator '%' requires 'int' or 'char' operands, found '" + leftType + "' and '"
                                + rightType + "'.",
                        n);
                return null;
            }
            case "<", ">", "<=", ">=" -> {
                if (isNumeric(leftType) && isNumeric(rightType)) {
                    return "boolean";
                }
                errorReporter.reportError(
                        "Relational operator '" + op + "' requires numeric operands, found '" + leftType + "' and '"
                                + rightType + "'.",
                        n);
                return "boolean";
            }
            case "==", "!=" -> {
                if (areEqualityComparable(leftType, rightType)) {
                    return "boolean";
                }
                errorReporter.reportError(
                        "Operator '" + op + "' cannot be applied to '" + leftType + "' and '" + rightType + "'.", n);
                return "boolean";
            }
            case "&&", "||" -> {
                if (!"boolean".equals(leftType) || !"boolean".equals(rightType)) {
                    errorReporter.reportError(
                            "Logical operator '" + op + "' requires boolean operands, found '" + leftType + "' and '"
                                    + rightType + "'.",
                            n);
                }
                return "boolean";
            }
            default -> {
                errorReporter.reportError("Unknown binary operator '" + op + "'.", n);
                return null;
            }
        }
    }

    @Override
    public String visitUnaryExpression(NodeUnaryExpression n) {
        if (n == null) return null;

        String exprType = n.getExpression() != null ? n.getExpression().accept(this) : null;
        if (exprType == null) return null;

        String op = n.getOperator();
        switch (op) {
            case "!" -> {
                if (!"boolean".equals(exprType)) {
                    errorReporter.reportError(
                            "Operator '!' requires boolean operand, found '" + exprType + "'.", n);
                }
                return "boolean";
            }
            case "+", "-" -> {
                if (!isNumeric(exprType)) {
                    errorReporter.reportError(
                            "Unary operator '" + op + "' requires numeric operand, found '" + exprType + "'.", n);
                    return null;
                }
                return exprType;
            }
            case "++", "--" -> {
                if (!isNumeric(exprType)) {
                    errorReporter.reportError(
                            "Operator '" + op + "' requires numeric operand, found '" + exprType + "'.", n);
                    return null;
                }
                return exprType;
            }
            default -> {
                errorReporter.reportError("Unknown unary operator '" + op + "'.", n);
                return null;
            }
        }
    }

    @Override
    public String visitIdentifier(NodeIdentifier n) {
        if (n == null) return null;

        if ("this".equals(n.getName()) && currentClassName != null) {
            return currentClassName;
        }

        Symbol sym = symbolTable.lookup(n.getName());
        if (sym == null) {
            errorReporter.reportError("Variable '" + n.getName() + "' is not declared.", n);
            return null;
        }

        if (sym instanceof VariableSymbol vs) {
            return vs.getType();
        } else if (sym instanceof ArraySymbol as) {
            return as.getElementType() + "[]".repeat(as.getDimensions());
        } else if (sym instanceof ClassSymbol cs) {
            return cs.getName();
        }
        return null;
    }

    @Override
    public String visitFieldAccess(NodeFieldAccess n) {
        if (n == null) return null;

        String targetType = n.getTarget() != null ? n.getTarget().accept(this) : null;
        if (targetType == null) return null;

        if (!typeTable.isUserDefined(targetType)) {
            errorReporter.reportError(
                    "Cannot access field '" + n.getFieldName() + "' on non-object type '" + targetType + "'.", n);
            return null;
        }

        Symbol fieldSym = symbolTable.lookupField(targetType, n.getFieldName());
        if (fieldSym == null) {
            errorReporter.reportError(
                    "Field '" + n.getFieldName() + "' is not declared in class '" + targetType + "'.", n);
            return null;
        }

        if (fieldSym instanceof VariableSymbol vs) {
            return vs.getType();
        } else if (fieldSym instanceof ArraySymbol as) {
            return as.getElementType() + "[]".repeat(as.getDimensions());
        }
        return null;
    }

    @Override
    public String visitIndexAccess(NodeIndexAccess n) {
        if (n == null) return null;

        String targetType = n.getTarget() != null ? n.getTarget().accept(this) : null;
        String indexType = n.getIndex() != null ? n.getIndex().accept(this) : null;

        if (indexType != null && !"int".equals(indexType)) {
            errorReporter.reportError("Array index must be of type 'int', found '" + indexType + "'.", n.getIndex());
        }

        if (targetType == null) return null;

        if (!targetType.endsWith("[]")) {
            errorReporter.reportError("Cannot index non-array type '" + targetType + "'.", n);
            return null;
        }

        return targetType.substring(0, targetType.length() - 2);
    }

    @Override
    public String visitMethodCall(NodeMethodCall n) {
        if (n == null) return null;

        String targetClass;
        if (n.getTarget() == null) {
            targetClass = this.currentClassName;
            if (targetClass == null) {
                errorReporter.reportError("Method call outside of a class context.", n);
                return null;
            }
        } else {
            targetClass = n.getTarget().accept(this);
            if (targetClass == null) return null;
            if (!typeTable.isUserDefined(targetClass)) {
                errorReporter.reportError(
                        "Cannot invoke method '" + n.getMethodName() + "' on non-object type '" + targetClass + "'.",
                        n);
                return null;
            }
        }

        List<NodeExpression> args = n.getArguments() != null ? n.getArguments() : Collections.emptyList();
        List<String> argTypes = new ArrayList<>();
        for (NodeExpression arg : args) {
            argTypes.add(arg != null ? arg.accept(this) : null);
        }

        int arity = args.size();
        MethodSymbol method = symbolTable.lookupMethod(targetClass, n.getMethodName(), arity);
        if (method == null) {
            if (symbolTable.existsMethod(targetClass, n.getMethodName())) {
                errorReporter.reportError(
                        "Method '" + n.getMethodName() + "' in class '" + targetClass
                                + "' expects different number of arguments, got " + arity + ".",
                        n);
            } else {
                errorReporter.reportError(
                        "Method '" + n.getMethodName() + "' is not declared in class '" + targetClass + "'.", n);
            }
            return null;
        }

        List<String> paramTypes = method.getParameterTypes();
        for (int i = 0; i < arity; i++) {
            String expected = paramTypes.get(i);
            String actual = argTypes.get(i);
            if (expected != null && actual != null && !isAssignable(expected, actual)) {
                errorReporter.reportError(
                        "Argument " + (i + 1) + " of method '" + n.getMethodName() + "': cannot assign '" + actual
                                + "' to '" + expected + "'.",
                        args.get(i));
            }
        }

        return method.getReturnType();
    }

    @Override
    public String visitNewObject(NodeNewObject n) {
        if (n == null) return null;

        if (!typeTable.exists(n.getClassName())) {
            errorReporter.reportError("Class '" + n.getClassName() + "' is not defined.", n);
            return null;
        }

        List<NodeExpression> args = n.getArguments() != null ? n.getArguments() : Collections.emptyList();
        List<String> argTypes = new ArrayList<>();
        for (NodeExpression arg : args) {
            argTypes.add(arg != null ? arg.accept(this) : null);
        }

        int arity = args.size();
        MethodSymbol ctor = symbolTable.lookupMethod(n.getClassName(), n.getClassName(), arity);
        if (ctor == null) {
            if (arity == 0 && !symbolTable.hasAnyConstructor(n.getClassName())) {
                return n.getClassName();
            }
            errorReporter.reportError(
                    "Constructor '" + n.getClassName() + "' with " + arity + " arguments is not defined.", n);
            return n.getClassName();
        }

        List<String> paramTypes = ctor.getParameterTypes();
        for (int i = 0; i < arity; i++) {
            String expected = paramTypes.get(i);
            String actual = argTypes.get(i);
            if (expected != null && actual != null && !isAssignable(expected, actual)) {
                errorReporter.reportError(
                        "Argument " + (i + 1) + " of constructor '" + n.getClassName() + "': cannot assign '"
                                + actual + "' to '" + expected + "'.",
                        args.get(i));
            }
        }

        return n.getClassName();
    }

    @Override
    public String visitNewArray(NodeNewArray n) {
        if (n == null) return null;

        if (!typeTable.exists(n.getType())) {
            errorReporter.reportError("Type '" + n.getType() + "' is not defined.", n);
        }

        if (n.getDimensions() != null) {
            for (NodeExpression dim : n.getDimensions()) {
                if (dim != null) {
                    String dimType = dim.accept(this);
                    if (dimType != null && !"int".equals(dimType)) {
                        errorReporter.reportError(
                                "Array dimension must be 'int', found '" + dimType + "'.", dim);
                    }
                }
            }
        }

        int dims = n.getDimensions() != null ? n.getDimensions().size() : 1;
        return n.getType() + "[]".repeat(dims);
    }

    // ============================================================
    // LITERALS
    // ============================================================

    @Override
    public String visitIntegerLiteral(NodeIntegerLiteral n) {
        return "int";
    }

    @Override
    public String visitDecimalLiteral(NodeDecimalLiteral n) {
        return "double";
    }

    @Override
    public String visitCharLiteral(NodeCharLiteral n) {
        return "char";
    }

    @Override
    public String visitStringLiteral(NodeStringLiteral n) {
        return "String";
    }

    @Override
    public String visitBooleanLiteral(NodeBooleanLiteral n) {
        return "boolean";
    }

    @Override
    public String visitNullLiteral(NodeNullLiteral n) {
        return "null";
    }

    // ============================================================
    // TYPE SYSTEM HELPERS
    // ============================================================

    private boolean isNumeric(String type) {
        return "int".equals(type) || "double".equals(type) || "char".equals(type);
    }

    private boolean isAssignable(String targetType, String sourceType) {
        if (targetType == null || sourceType == null) return true;
        if (targetType.equals(sourceType)) return true;

        if ("double".equals(targetType)) {
            return "int".equals(sourceType) || "char".equals(sourceType);
        }
        if ("int".equals(targetType)) {
            return "char".equals(sourceType);
        }
        if ("null".equals(sourceType)) {
            return isReferenceType(targetType);
        }
        if ("empty_array".equals(sourceType)) {
            return targetType.endsWith("[]");
        }
        return false;
    }

    private boolean isReferenceType(String type) {
        if (type == null) return false;
        return typeTable.isUserDefined(type) || type.endsWith("[]") || "String".equals(type);
    }

    private boolean isStringConcatenable(String type) {
        if (type == null || "void".equals(type)) return false;
        return "int".equals(type) || "double".equals(type) || "char".equals(type)
                || "boolean".equals(type) || "String".equals(type) || "null".equals(type);
    }

    private boolean isSwitchableType(String type) {
        return "int".equals(type) || "char".equals(type) || "String".equals(type);
    }

    private boolean areEqualityComparable(String leftType, String rightType) {
        if (leftType == null || rightType == null) return true;
        if (leftType.equals(rightType)) return true;
        if (isNumeric(leftType) && isNumeric(rightType)) return true;
        if ("null".equals(leftType) && isReferenceType(rightType)) return true;
        if ("null".equals(rightType) && isReferenceType(leftType)) return true;
        if (isReferenceType(leftType) && isReferenceType(rightType)) return true;
        return false;
    }
}
