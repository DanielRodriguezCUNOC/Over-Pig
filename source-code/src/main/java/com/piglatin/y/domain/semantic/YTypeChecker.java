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
import java.util.List;

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

    public YTypeChecker(YSymbolTable symbolTable, YTypeTable typeTable, SemanticErrorReporter errorReporter) {
        this.symbolTable = symbolTable;
        this.typeTable = typeTable;
        this.errorReporter = errorReporter;
        this.constantFolder = new YConstantFolder(errorReporter);
        this.currentFunctionReturnType = null;
    }

    // ============================================================
    // PROGRAM & STRUCTS
    // ============================================================

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
        //* Local struct (declared inside a function body): pass 1 already
        //* validated its name/fields; here we only need its field map so
        //* that field access later in the same scope can resolve types.
        if (n == null) return null;
        registerStructureFields(n);
        return null;
    }

    @Override
    public String visitFieldDeclaration(NodeFieldDeclaration n) {
        //* Fields are processed in bulk by registerStructureFields().
        return null;
    }

    // ============================================================
    // FUNCTIONS
    // ============================================================

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
        //* Parameter is already declared in declareParameters().
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

                //* Array sizes must be constant expressions.
                Object constantValue = constantFolder.evaluate(size);
                if (constantValue == null) {
                    errorReporter.reportError(
                            "Array size must be a constant expression.",
                            size.getLine(), size.getColumn());
                } else if (constantValue instanceof Integer i && i <= 0) {
                    errorReporter.reportError(
                            "Array size must be a positive integer.",
                            size.getLine(), size.getColumn());
                }
            }
        }

        if (n.getInitializer() != null) {
            String initType = n.getInitializer().accept(this);
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

        String rightType = n.getExpression() != null ? n.getExpression().accept(this) : null;
        if (leftType != null && rightType != null && !isAssignable(leftType, rightType)) {
            errorReporter.reportError(
                    "Cannot assign '" + rightType + "' to '" + leftType + "'.",
                    n.getLine(), n.getColumn());
        }
        return null;
    }

    @Override
    public String visitRead(NodeRead n) {
        //* leer() always yields a string; the caller may assign/coerce it.
        return "cadena";
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
        //* Loop-depth validity already checked in pass 1.
        return null;
    }

    @Override
    public String visitContinue(NodeContinue n) {
        //* Loop-depth validity already checked in pass 1.
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
            String returnExprType = n.getExpression().accept(this);
            if (returnExprType != null && !isAssignable(currentFunctionReturnType, returnExprType)) {
                errorReporter.reportError(
                        "Incompatible return type: expected '" + currentFunctionReturnType + "', found '"
                                + returnExprType + "'.",
                        n.getLine(), n.getColumn());
            }
        }
        return null;
    }

    // ============================================================
    // EXPRESSIONS
    // ============================================================

    @Override
    public String visitBinaryOperation(NodeBinaryOperation n) {
        if (n == null) return null;

        String leftType = n.getLeft() != null ? n.getLeft().accept(this) : null;
        String rightType = n.getRight() != null ? n.getRight().accept(this) : null;

        if (leftType == null || rightType == null) return null;

        String op = n.getOperator();
        switch (op) {
            case "+" -> {
                if ("cadena".equals(leftType) && "cadena".equals(rightType)) {
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
            case "<", ">" -> {
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
        //* Abstract lvalue node: concrete subtypes (identifier, field access,
        //* index access) dispatch to their own visit method via accept().
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
        List<String> argTypes = new ArrayList<>();
        for (ASTNode arg : args) {
            argTypes.add(arg != null ? arg.accept(this) : null);
        }

        int arity = args.size();
        FunctionSymbol function = symbolTable.lookupFunction(n.getFunctionName(), arity);
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

        List<ParameterType> paramTypes = function.getParameterTypes();

        if (paramTypes.size() != arity) {
            errorReporter.reportError(
                    "Function '" + n.getFunctionName() + "' expects " + paramTypes.size() + " arguments, got " + arity + ".",
                    n.getLine(), n.getColumn());
            return function.getReturnType();
        }

        int paramCount = paramTypes.size();
        int loopCount = Math.min(arity, paramCount);

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

    @Override
    public String visitArrayLiteral(NodeArrayLiteral n) {
        if (n == null) return null;

        List<ASTNode> elements = n.getElements();
        if (elements == null || elements.isEmpty()) {
            return "empty_array";
        }

        String firstType = elements.get(0) != null ? elements.get(0).accept(this) : null;
        for (int i = 1; i < elements.size(); i++) {
            ASTNode element = elements.get(i);
            String elemType = element != null ? element.accept(this) : null;
            if (firstType != null && elemType != null
                    && !isAssignable(firstType, elemType) && !isAssignable(elemType, firstType)) {
                errorReporter.reportError(
                        "Incompatible types in array literal: '" + firstType + "' and '" + elemType + "'.",
                        element.getLine(), element.getColumn());
            }
        }

        return firstType != null ? firstType + "[]" : null;
    }

    // ============================================================
    // LITERALS
    // ============================================================

    @Override public String visitIntegerLiteral(NodeIntegerLiteral n) { return "entero"; }
    @Override public String visitFloatLiteral(NodeFloatLiteral n)     { return "flotante"; }
    @Override public String visitCharLiteral(NodeCharLiteral n)       { return "caracter"; }
    @Override public String visitStringLiteral(NodeStringLiteral n)   { return "cadena"; }
    @Override public String visitBooleanLiteral(NodeBooleanLiteral n) { return "bool"; }

    // ============================================================
    // PRIVATE HELPERS - STRUCTS
    // ============================================================

    /**
     * Populates the persistent struct-field registry so that field access
     * (p1.promedio) can later be resolved by struct name + field name.
     * Duplicate names / undefined types were already reported in pass 1;
     * here every field is registered best-effort regardless, so a single
     * earlier mistake does not cascade into spurious "field not declared"
     * errors for the rest of the program.
     */
    private void registerStructureFields(NodeStructureDefinition n) {
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

    // ============================================================
    // PRIVATE HELPERS - FUNCTIONS
    // ============================================================

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

    private void checkBooleanCondition(ASTNode condition, String context) {
        if (condition == null) return;
        String condType = condition.accept(this);
        if (condType != null && !"bool".equals(condType)) {
            errorReporter.reportError(
                    "Condition in " + context + " must be 'bool', found '" + condType + "'.",
                    condition.getLine(), condition.getColumn());
        }
    }

    // ============================================================
    // TYPE SYSTEM HELPERS
    // ============================================================

    private boolean isNumeric(String type) {
        return "entero".equals(type) || "flotante".equals(type);
    }

    private String promotedNumericType(String leftType, String rightType) {
        return ("flotante".equals(leftType) || "flotante".equals(rightType)) ? "flotante" : "entero";
    }

    private boolean isAssignable(String targetType, String sourceType) {
        if (targetType == null || sourceType == null) return true;
        if (targetType.equals(sourceType)) return true;

        //* Implicit widening: entero -> flotante.
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