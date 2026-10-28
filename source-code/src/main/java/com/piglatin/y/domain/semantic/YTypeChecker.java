package com.piglatin.y.domain.semantic;

import com.piglatin.y.domain.ast.expressions.*;
import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.principal.NodeProgram;
import com.piglatin.y.domain.ast.statements.*;
import com.piglatin.y.domain.ast.visitor.Visitor;
import com.piglatin.y.domain.symboltable.*;
import com.piglatin.y.domain.types.YTypeTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Semantic analysis pass 2.
 *
 * Traversal: Top-down. Reuses the SymbolTable/TypeTable built by
 * SymbolTableBuilder (pass 1); scopes are re-opened here since they were
 * closed at the end of pass 1 (only the persistent registries - functions,
 * struct types, struct fields - survive across passes).
 *
 * Functions:
 * - Infer/validate the type of every expression.
 * - Validate assignment, argument and return-type compatibility.
 * - Resolve function-call overloads by arity.
 * - Resolve field access (p1.promedio) against the struct's field map.
 * - Fold constant expressions where the language requires a constant
 *   (array sizes) via ConstantFolder.
 *
 * !EYE!!: Type errors are reported and analysis continues (never throws).
 */
public class YTypeChecker implements Visitor<String> {

    private final YSymbolTable symbolTable;
    private final YTypeTable typeTable;
    private final SemanticErrorReporter errorReporter;
    private final YConstantFolder constantFolder;
    private String currentFunctionReturnType;
    private final Map<String, NodeStructureDefinition> structDefs = new HashMap<>();

    public YTypeChecker(YSymbolTable symbolTable, YTypeTable typeTable, SemanticErrorReporter errorReporter) {
        this.symbolTable = symbolTable;
        this.typeTable = typeTable;
        this.errorReporter = errorReporter;
        this.constantFolder = new YConstantFolder(errorReporter);
        this.currentFunctionReturnType = null;
    }

    @Override
    public String visitProgram(NodeProgram n) {
        if (n == null) return null;

        symbolTable.pushScope("global");

        if (n.getStructures() != null) {
            for (NodeStructureDefinition s : n.getStructures()) {
                if (s != null) registerStructureFields(s);
            }
        }

        if (n.getFunctions() != null) {
            for (NodeFunctionDefinition f : n.getFunctions()) {
                if (f != null) f.accept(this);
            }
        }

        symbolTable.popScope();
        return null;
    }

    @Override
    public String visitStructureDefinition(NodeStructureDefinition n) {
        if (n == null) return null;
        registerStructureFields(n);
        return null;
    }

    @Override
    public String visitFieldDeclaration(NodeFieldDeclaration n) {
        return null;
    }


    @Override
    public String visitFunctionDefinition(NodeFunctionDefinition n) {
        if (n == null) return null;

        symbolTable.pushScope("func_" + n.getName());
        String savedReturnType = currentFunctionReturnType;
        currentFunctionReturnType = n.getReturnType();

        declareParameters(n.getParameters());

        if (n.getBlock() != null) n.getBlock().accept(this);

        currentFunctionReturnType = savedReturnType;
        symbolTable.popScope();
        return null;
    }

    @Override
    public String visitParameter(NodeParameter n) {
        return null;
    }


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
            String initType = valueTypeFor(declaredType, n.getInitializer());
            if (initType != null && !isAssignable(declaredType, initType)) {
                errorReporter.reportError(
                        "Cannot assign '" + initType + "' to '" + declaredType + "'.",
                        n.getLine(), n.getColumn());
            }
        }

        boolean isStructType = typeTable.isUserDefined(declaredType);
        symbolTable.declare(n.getName(), new VariableSymbol(
                n.getName(), declaredType, false, isStructType, n.getLine(), n.getColumn()));
        return null;
    }

    @Override
    public String visitArrayDeclaration(NodeArrayDeclaration n) {
        if (n == null) return null;

        int dimensions = n.getDimensions() != null ? Math.max(n.getDimensions().size(), 1) : 1;
        String arrayType = n.getType() + "[]".repeat(dimensions);

        if (n.getDimensions() != null) {
            for (ASTNode size : n.getDimensions()) {
                if (size == null) continue;

                String sizeType = size.accept(this);
                if (sizeType != null && !"entero".equals(sizeType)) {
                    errorReporter.reportError(
                            "Array size must be 'entero', found '" + sizeType + "'.",
                            size.getLine(), size.getColumn());
                }

                Object constantValue = constantFolder.evaluate(size);
                if (constantValue instanceof Integer i && i <= 0) {
                    errorReporter.reportError(
                            "Array size must be a positive integer.",
                            size.getLine(), size.getColumn());
                }
            }
        }

        if (n.getInitializer() != null) {
            String initType = valueTypeFor(arrayType, n.getInitializer());
            if (initType != null && !isAssignable(arrayType, initType)) {
                errorReporter.reportError(
                        "Cannot assign '" + initType + "' to array of type '" + arrayType + "'.",
                        n.getLine(), n.getColumn());
            }
        }

        symbolTable.declare(n.getName(), new ArraySymbol(
                n.getName(), n.getType(), dimensions, n.getLine(), n.getColumn()));
        return null;
    }

    @Override
    public String visitAssignment(NodeAssignment n) {
        if (n == null) return null;

        String leftType = n.getLvalue() != null ? n.getLvalue().accept(this) : null;
        String op = n.getOperator();

        if ("++".equals(op) || "--".equals(op)) {
            if (leftType != null && !isNumeric(leftType)) {
                errorReporter.reportError(
                        "Operator '" + op + "' requires a numeric operand, found '" + leftType + "'.",
                        n.getLine(), n.getColumn());
            }
            return null;
        }

        String rightType = n.getExpression() != null ? valueTypeFor(leftType, n.getExpression()) : null;
        if (leftType != null && rightType != null && !isAssignable(leftType, rightType)) {
            errorReporter.reportError(
                    "Cannot assign '" + rightType + "' to '" + leftType + "'.",
                    n.getLine(), n.getColumn());
        }
        return null;
    }

    @Override
    public String visitRead(NodeRead n) {
        return "entero";
    }

    @Override
    public String visitPrint(NodePrint n) {
        if (n == null) return null;

        if (n.getExpressions() != null) {
            for (ASTNode expr : n.getExpressions()) {
                if (expr == null) continue;
                String t = expr.accept(this);
                if ("void".equals(t)) {
                    errorReporter.reportError(
                            "Cannot print an expression of type 'void'.", expr.getLine(), expr.getColumn());
                }
            }
        }
        return null;
    }

    @Override
    public String visitIf(NodeIf n) {
        if (n == null) return null;

        checkBooleanCondition(n.getCondition(), "'si'");
        if (n.getThenBlock() != null) n.getThenBlock().accept(this);

        if (n.getElseIfClauses() != null) {
            for (ElseIfClause clause : n.getElseIfClauses()) {
                if (clause == null) continue;
                checkBooleanCondition(clause.getCondition(), "'sino'");
                if (clause.getBlock() != null) clause.getBlock().accept(this);
            }
        }

        if (n.getElseBlock() != null) n.getElseBlock().accept(this);
        return null;
    }

    @Override
    public String visitChoose(NodeChoose n) {
        if (n == null) return null;

        String switchType = n.getExpression() != null ? n.getExpression().accept(this) : null;
        if (switchType != null && !isSwitchableType(switchType)) {
            errorReporter.reportError(
                    "'elegir' expression must be 'entero', 'caracter' or 'cadena', found '" + switchType + "'.",
                    n.getLine(), n.getColumn());
        }

        if (n.getCases() != null) {
            for (NodeChooseCase c : n.getCases()) {
                if (c == null) continue;

                if (c.getValue() != null) {
                    String caseType = c.getValue().accept(this);
                    if (caseType != null && switchType != null && !caseType.equals(switchType)) {
                        errorReporter.reportError(
                                "Case expression type '" + caseType + "' is incompatible with 'elegir' expression type '"
                                        + switchType + "'.",
                                c.getLine(), c.getColumn());
                    }
                }
                if (c.getBlock() != null) c.getBlock().accept(this);
            }
        }

        if (n.getDefaultCase() != null && n.getDefaultCase().getBlock() != null) {
            n.getDefaultCase().getBlock().accept(this);
        }
        return null;
    }

    @Override
    public String visitChooseCase(NodeChooseCase n) {
        //* Cases are driven from visitChoose() so the switch type is
        //* available for compatibility checks; kept here for interface
        //* completeness / defensive direct calls.
        if (n == null) return null;
        if (n.getValue() != null) n.getValue().accept(this);
        if (n.getBlock() != null) n.getBlock().accept(this);
        return null;
    }

    @Override
    public String visitWhile(NodeWhile n) {
        if (n == null) return null;
        checkBooleanCondition(n.getCondition(), "'mientras'");
        if (n.getBlock() != null) n.getBlock().accept(this);
        return null;
    }

    @Override
    public String visitDoWhile(NodeDoWhile n) {
        if (n == null) return null;
        if (n.getBlock() != null) n.getBlock().accept(this);
        checkBooleanCondition(n.getCondition(), "'hacer/mientras'");
        return null;
    }

    @Override
    public String visitFor(NodeFor n) {
        if (n == null) return null;

        symbolTable.pushScope("for");

        if (n.getInit() != null) n.getInit().accept(this);
        checkBooleanCondition(n.getCondition(), "'para'");
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
        if (currentFunctionReturnType == null) return null;

        boolean hasExpr = n.getExpression() != null;

        if ("void".equals(currentFunctionReturnType)) {
            if (hasExpr) {
                errorReporter.reportError(
                        "Cannot return a value from a function with 'void' return type.",
                        n.getLine(), n.getColumn());
            }
        } else if (!hasExpr) {
            errorReporter.reportError(
                    "Function must return a value of type '" + currentFunctionReturnType + "'.",
                    n.getLine(), n.getColumn());
        } else {
            String returnExprType = valueTypeFor(currentFunctionReturnType, n.getExpression());
            if (returnExprType != null && !isAssignable(currentFunctionReturnType, returnExprType)) {
                errorReporter.reportError(
                        "Incompatible return type: expected '" + currentFunctionReturnType + "', found '"
                                + returnExprType + "'.",
                        n.getLine(), n.getColumn());
            }
        }
        return null;
    }

    @Override
    public String visitBinaryOperation(NodeBinaryOperation n) {
        if (n == null) return null;

        String leftType = n.getLeft() != null ? n.getLeft().accept(this) : null;
        String rightType = n.getRight() != null ? n.getRight().accept(this) : null;

        if (leftType == null || rightType == null) return null;

        String op = n.getOperator();
        switch (op) {
            case "+" -> {
                //* Concatenation: cadena with cadena/entero/flotante/bool/caracter, either order.
                if (("cadena".equals(leftType) && isConcatenable(rightType))
                        || ("cadena".equals(rightType) && isConcatenable(leftType))) {
                    return "cadena";
                }
                if (isNumeric(leftType) && isNumeric(rightType)) {
                    return promotedNumericType(leftType, rightType);
                }
                errorReporter.reportError(
                        "Operator '+' cannot be applied to '" + leftType + "' and '" + rightType + "'.",
                        n.getLine(), n.getColumn());
                return null;
            }
            case "-", "*", "/" -> {
                if (isNumeric(leftType) && isNumeric(rightType)) {
                    return promotedNumericType(leftType, rightType);
                }
                errorReporter.reportError(
                        "Operator '" + op + "' requires numeric operands, found '" + leftType + "' and '"
                                + rightType + "'.",
                        n.getLine(), n.getColumn());
                return null;
            }
            case "<", "<=", ">", ">=" -> {
                if (isNumeric(leftType) && isNumeric(rightType)) {
                    return "bool";
                }
                errorReporter.reportError(
                        "Relational operator '" + op + "' requires numeric operands, found '" + leftType + "' and '"
                                + rightType + "'.",
                        n.getLine(), n.getColumn());
                return "bool";
            }
            case "==", "!=" -> {
                if (areEqualityComparable(leftType, rightType)) {
                    return "bool";
                }
                errorReporter.reportError(
                        "Operator '" + op + "' cannot be applied to '" + leftType + "' and '" + rightType + "'.",
                        n.getLine(), n.getColumn());
                return "bool";
            }
            case "&&", "||" -> {
                if (!"bool".equals(leftType) || !"bool".equals(rightType)) {
                    errorReporter.reportError(
                            "Logical operator '" + op + "' requires 'bool' operands, found '" + leftType + "' and '"
                                    + rightType + "'.",
                            n.getLine(), n.getColumn());
                }
                return "bool";
            }
            default -> {
                errorReporter.reportError("Unknown binary operator '" + op + "'.", n.getLine(), n.getColumn());
                return null;
            }
        }
    }

    @Override
    public String visitUnaryOperation(NodeUnaryOperation n) {
        if (n == null) return null;

        String operandType = n.getOperand() != null ? n.getOperand().accept(this) : null;
        if (operandType == null) return null;

        String op = n.getOperator();
        switch (op) {
            case "!" -> {
                if (!"bool".equals(operandType)) {
                    errorReporter.reportError(
                            "Operator '!' requires a 'bool' operand, found '" + operandType + "'.",
                            n.getLine(), n.getColumn());
                }
                return "bool";
            }
            case "-" -> {
                if (!isNumeric(operandType)) {
                    errorReporter.reportError(
                            "Unary operator '-' requires a numeric operand, found '" + operandType + "'.",
                            n.getLine(), n.getColumn());
                    return null;
                }
                return operandType;
            }
            default -> {
                errorReporter.reportError("Unknown unary operator '" + op + "'.", n.getLine(), n.getColumn());
                return null;
            }
        }
    }

    @Override
    public String visitIdentifier(NodeIdentifier n) {
        if (n == null) return null;

        Symbol sym = symbolTable.lookup(n.getId());
        if (sym == null) {
            errorReporter.reportError("Variable '" + n.getId() + "' is not declared.", n.getLine(), n.getColumn());
            return null;
        }

        if (sym instanceof VariableSymbol vs) {
            return vs.getType();
        } else if (sym instanceof ArraySymbol as) {
            return as.getElementType() + "[]".repeat(as.getDimensions());
        } else if (sym instanceof StructureSymbol ss) {
            return ss.getName();
        }
        return null;
    }

    @Override
    public String visitLvalue(NodeLvalue n) {
        return null;
    }

    @Override
    public String visitFieldAccess(NodeFieldAccess n) {
        if (n == null) return null;

        String targetType = n.getCurrentNode() != null ? n.getCurrentNode().accept(this) : null;
        if (targetType == null) return null;

        if (!typeTable.isUserDefined(targetType)) {
            errorReporter.reportError(
                    "Cannot access field '" + n.getFieldName() + "' on non-struct type '" + targetType + "'.",
                    n.getLine(), n.getColumn());
            return null;
        }

        Symbol fieldSym = symbolTable.lookupField(targetType, n.getFieldName());
        if (fieldSym == null) {
            errorReporter.reportError(
                    "Field '" + n.getFieldName() + "' is not declared in struct '" + targetType + "'.",
                    n.getLine(), n.getColumn());
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

        String targetType = n.getCurrentNode() != null ? n.getCurrentNode().accept(this) : null;
        String indexType = n.getIndexExpression() != null ? n.getIndexExpression().accept(this) : null;

        if (indexType != null && !"entero".equals(indexType)) {
            errorReporter.reportError(
                    "Array index must be of type 'entero', found '" + indexType + "'.",
                    n.getIndexExpression().getLine(), n.getIndexExpression().getColumn());
        }

        if (targetType == null) return null;

        if (!targetType.endsWith("[]")) {
            errorReporter.reportError(
                    "Cannot index non-array type '" + targetType + "'.", n.getLine(), n.getColumn());
            return null;
        }

        return targetType.substring(0, targetType.length() - 2);
    }

    @Override
    public String visitFunctionCall(NodeFunctionCall n) {
        if (n == null) return null;

        List<ASTNode> args = n.getArguments() != null ? n.getArguments() : Collections.emptyList();
        int arity = args.size();
        FunctionSymbol function = symbolTable.lookupFunction(n.getFunctionName(), arity);

        List<String> argTypes = evaluateArgs(args, function != null ? function.getParameterTypes() : null);

        if (function == null) {
            if (symbolTable.existsFunction(n.getFunctionName())) {
                errorReporter.reportError(
                        "Function '" + n.getFunctionName() + "' expects a different number of arguments, got " + arity + ".",
                        n.getLine(), n.getColumn());
            } else {
                errorReporter.reportError(
                        "Function '" + n.getFunctionName() + "' is not declared.", n.getLine(), n.getColumn());
            }
            return null;
        }

        List<ParameterType> paramTypes = function.getParameterTypes() != null
                ? function.getParameterTypes() : Collections.emptyList();

        if (paramTypes.size() != arity) {
            errorReporter.reportError(
                    "Function '" + n.getFunctionName() + "' expects " + paramTypes.size() + " arguments, got " + arity + ".",
                    n.getLine(), n.getColumn());
            return function.getReturnType();
        }

        int loopCount = Math.min(arity, paramTypes.size());
        for (int i = 0; i < loopCount; i++) {
            ParameterType expected = paramTypes.get(i);
            String actual = argTypes.get(i);
            if (expected == null || actual == null) continue;

            String expectedType = expected.getType() + (expected.isArray() ? "[]" : "");
            if (!isAssignable(expectedType, actual)) {
                errorReporter.reportError(
                        "Argument " + (i + 1) + " of function '" + n.getFunctionName() + "': cannot assign '" + actual
                                + "' to '" + expectedType + "'.",
                        n.getLine(), n.getColumn());
            }
        }

        return function.getReturnType();
    }

    private List<String> evaluateArgs(List<ASTNode> args, List<ParameterType> paramTypes) {
        if (args == null) return Collections.emptyList();

        List<String> types = new ArrayList<>();
        for (int i = 0; i < args.size(); i++) {
            ASTNode arg = args.get(i);
            if (arg == null) {
                types.add(null);
                continue;
            }
            if (paramTypes != null && i < paramTypes.size()) {
                ParameterType param = paramTypes.get(i);
                String expectedType = param != null
                        ? param.getType() + (param.isArray() ? "[]" : "")
                        : null;
                types.add(expectedType != null ? valueTypeFor(expectedType, arg) : arg.accept(this));
            } else {
                types.add(arg.accept(this));
            }
        }
        return types;
    }

    @Override
    public String visitArrayLiteral(NodeArrayLiteral n) {
        if (n == null) return null;

        List<ASTNode> elements = n.getElements();
        if (elements == null || elements.isEmpty()) {
            return "empty_array";
        }

        String inferredType = elements.get(0) != null ? elements.get(0).accept(this) : null;
        for (int i = 1; i < elements.size(); i++) {
            ASTNode element = elements.get(i);
            String elemType = element != null ? element.accept(this) : null;
            if (inferredType != null && elemType != null) {
                if (!isAssignable(inferredType, elemType) && !isAssignable(elemType, inferredType)) {
                    errorReporter.reportError(
                            "Incompatible types in array literal: '" + inferredType + "' and '" + elemType + "'.",
                            element.getLine(), element.getColumn());
                } else if ("entero".equals(inferredType) && "flotante".equals(elemType)) {
                    inferredType = "flotante";
                }
            } else if (inferredType == null && elemType != null) {
                inferredType = elemType;
            }
        }

        return inferredType != null ? inferredType + "[]" : null;
    }


    @Override public String visitIntegerLiteral(NodeIntegerLiteral n) { return "entero"; }
    @Override public String visitFloatLiteral(NodeFloatLiteral n)     { return "flotante"; }
    @Override public String visitCharLiteral(NodeCharLiteral n)       { return "caracter"; }
    @Override public String visitStringLiteral(NodeStringLiteral n)   { return "cadena"; }
    @Override public String visitBooleanLiteral(NodeBooleanLiteral n) { return "bool"; }

    private void registerStructureFields(NodeStructureDefinition n) {
        structDefs.put(n.getName(), n);
        if (n.getFields() == null) return;

        for (NodeFieldDeclaration field : n.getFields()) {
            if (field == null) continue;

            if (field.isArray()) {
                ArraySymbol fieldSymbol = new ArraySymbol(
                        field.getFieldName(), field.getType(), 1, field.getLine(), field.getColumn());
                symbolTable.declareField(n.getName(), field.getFieldName(), fieldSymbol);
            } else {
                boolean isStructType = typeTable.isUserDefined(field.getType());
                VariableSymbol fieldSymbol = new VariableSymbol(
                        field.getFieldName(), field.getType(), false, isStructType, field.getLine(), field.getColumn());
                symbolTable.declareField(n.getName(), field.getFieldName(), fieldSymbol);
            }
        }
    }

    private void declareParameters(List<NodeParameter> params) {
        if (params == null) return;
        for (NodeParameter p : params) {
            if (p == null) continue;

            if (p.isArray()) {
                symbolTable.declare(p.getName(), new ArraySymbol(
                        p.getName(), p.getType(), 1, p.getLine(), p.getColumn()));
            } else {
                boolean isStructType = p.isStruct() || typeTable.isUserDefined(p.getType());
                symbolTable.declare(p.getName(), new VariableSymbol(
                        p.getName(), p.getType(), false, isStructType, p.getLine(), p.getColumn()));
            }
        }
    }

    // ============================================================
    // PRIVATE HELPERS - GENERAL
    // ============================================================

    /**
     * Type of 'value' as seen from a context that expects 'targetType'.
     *
     * Most expressions simply return their own type. Two cases depend on the
     * target, so they are resolved here instead of in the plain visit methods:
     *
     *  - Array literal used as an initializer. It is checked element by element
     *    against the element type ("flotante a[3] = {1, 2, 3}" is fine because
     *    entero -> flotante), or field by field, in declaration order, against
     *    a struct ("Punto p = {10, 20, 85.5}"). Mismatches are reported on the
     *    offending element and the target type is returned so the caller does
     *    not report a second, whole-literal error.
     *  - leer(), which can feed entero, flotante, caracter or bool.
     *
     * Returns null when the value is unresolved or its error was already reported.
     */
    private String valueTypeFor(String targetType, ASTNode value) {
        if (value == null) return null;

        if (value instanceof NodeRead) {
            if (targetType == null || "entero".equals(targetType) || "flotante".equals(targetType)
                    || "caracter".equals(targetType) || "bool".equals(targetType)) {
                return targetType != null ? targetType : "entero";
            }
            errorReporter.reportError(
                    "'leer()' can only be assigned to 'entero', 'flotante', 'caracter' or 'bool', not to '"
                            + targetType + "'.",
                    value.getLine(), value.getColumn());
            return null;
        }

        if (value instanceof NodeArrayLiteral literal && targetType != null) {
            if (targetType.endsWith("[]")) {
                checkArrayLiteral(targetType, literal);
                return targetType;
            }
            if (typeTable.isUserDefined(targetType)) {
                checkStructLiteral(targetType, literal);
                return targetType;
            }
        }

        return value.accept(this);
    }

    private void checkArrayLiteral(String arrayType, NodeArrayLiteral literal) {
        if (literal.getElements() == null) return;
        String elementType = arrayType.substring(0, arrayType.length() - 2);

        for (ASTNode element : literal.getElements()) {
            if (element == null) continue;
            reportIfNotAssignable(elementType, element);
        }
    }

    private void checkStructLiteral(String structName, NodeArrayLiteral literal) {
        List<ASTNode> elements = literal.getElements() != null ? literal.getElements() : Collections.emptyList();
        NodeStructureDefinition def = structDefs.get(structName);
        if (def == null || def.getFields() == null) {
            //* Unknown layout (already reported elsewhere): just visit the elements.
            for (ASTNode element : elements) {
                if (element != null) element.accept(this);
            }
            return;
        }

        List<NodeFieldDeclaration> fields = def.getFields();
        if (elements.size() > fields.size()) {
            errorReporter.reportError(
                    "Too many initializers for struct '" + structName + "': it has " + fields.size()
                            + " fields, got " + elements.size() + ".",
                    literal.getLine(), literal.getColumn());
        }

        for (int i = 0; i < elements.size(); i++) {
            ASTNode element = elements.get(i);
            if (element == null) continue;

            if (i >= fields.size() || fields.get(i) == null) {
                element.accept(this);
                continue;
            }
            NodeFieldDeclaration field = fields.get(i);
            String fieldType = field.isArray() ? field.getType() + "[]" : field.getType();
            reportIfNotAssignable(fieldType, element);
        }
    }

    private void reportIfNotAssignable(String targetType, ASTNode element) {
        String actual = valueTypeFor(targetType, element);
        if (actual != null && !isAssignable(targetType, actual)) {
            errorReporter.reportError(
                    "Cannot assign '" + actual + "' to '" + targetType + "'.",
                    element.getLine(), element.getColumn());
        }
    }

    private void checkBooleanCondition(ASTNode condition, String context) {
        if (condition == null) return;
        String condType = condition.accept(this);
        if (condType != null && !"bool".equals(condType)) {
            errorReporter.reportError(
                    "Condition in " + context + " must be 'bool', found '" + condType + "'.",
                    condition.getLine(), condition.getColumn());
        }
    }

    private boolean isConcatenable(String type) {
        return "cadena".equals(type) || "entero".equals(type) || "flotante".equals(type)
                || "bool".equals(type) || "caracter".equals(type);
    }

    private boolean isNumeric(String type) {
        return "entero".equals(type) || "flotante".equals(type);
    }

    private String promotedNumericType(String leftType, String rightType) {
        return ("flotante".equals(leftType) || "flotante".equals(rightType)) ? "flotante" : "entero";
    }

    private boolean isAssignable(String targetType, String sourceType) {
        if (targetType == null || sourceType == null) return true;
        if (targetType.equals(sourceType)) return true;

        if ("flotante".equals(targetType) && "entero".equals(sourceType)) {
            return true;
        }
        if ("empty_array".equals(sourceType)) {
            return targetType.endsWith("[]");
        }
        return false;
    }

    private boolean isSwitchableType(String type) {
        return "entero".equals(type) || "caracter".equals(type) || "cadena".equals(type);
    }

    private boolean areEqualityComparable(String leftType, String rightType) {
        if (leftType == null || rightType == null) return true;
        if (leftType.equals(rightType)) return true;
        return isNumeric(leftType) && isNumeric(rightType);
    }
}