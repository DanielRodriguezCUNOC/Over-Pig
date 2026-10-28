package com.piglatin.piglatin.domain.semantic;

import com.piglatin.piglatin.domain.ast.nodes.NodeImport;
import com.piglatin.piglatin.domain.ast.nodes.declaration.*;
import com.piglatin.piglatin.domain.ast.nodes.expression.*;
import com.piglatin.piglatin.domain.ast.nodes.instruction.*;
import com.piglatin.piglatin.domain.ast.nodes.literal.*;
import com.piglatin.piglatin.domain.ast.nodes.lvalue.*;
import com.piglatin.piglatin.domain.ast.principal.*;
import com.piglatin.piglatin.domain.ast.visitor.Visitor;
import com.piglatin.piglatin.domain.symboltable.*;
import com.piglatin.piglatin.domain.types.PigLatinTypeTable;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Semantic analysis pass 2.
 *
 * Traversal: Bottom-up (post-order, my weakness T-T ).
 * Functions:
 *  - Infer and return the type of each expression.
 *  - Validate operand compatibility with operators.
 *  - Verify that calls, constructors and field accesses match the imported .z / .y files
 *  - Validate that control-flow conditions are boolean.
 *
 * Type vocabulary: NUMERUS, DECIMALIS, TEXTUM, LITTERA, BOOL, VOID, SERIES_X, class / struct
 * names, plus two internal markers:
 *  - "null":  the type of the null literal. Assignable to every reference type (TEXTUM,
 *             classes, structs, arrays) and never to NUMERUS, DECIMALIS, LITTERA or BOOL.
 *  - "ERROR": the expression is already wrong (or unknown) and the error was reported.
 *             It is compatible with everything so one mistake does not cascade into more.
 *
 * This class reconstructs its own Symbol Table while traversing the AST,
 * allowing identifier types to be resolved at any nesting level.
 */
@Getter
@Setter
public class PigLatinTypeChecker implements Visitor<String> {

    private static final String ERROR = "ERROR";
    private static final String NULL_TYPE = "null";
    private static final String VOID_TYPE = "VOID";
    private static final String ARRAY_PREFIX = "SERIES_";

    private final PigLatinSymbolTable symbolTable;
    private final PigLatinTypeTable typeTable;
    private final SemanticErrorReporter errorReporter;
    private final ImportedSymbols imported;
    private final List<String> functionReturnStack;
    private final PigLatinConstantFolder constantFolder;
    private int loopDepth = 0;

    public PigLatinTypeChecker(PigLatinTypeTable typeTable, SemanticErrorReporter errorReporter, PigLatinSymbolTable symbolTable) {
        this(typeTable, errorReporter, symbolTable, ImportedSymbols.NONE);
    }

    public PigLatinTypeChecker(PigLatinTypeTable typeTable, SemanticErrorReporter errorReporter,
                               PigLatinSymbolTable symbolTable, ImportedSymbols imported) {
        this.symbolTable = symbolTable;
        this.typeTable = typeTable;
        this.errorReporter = errorReporter;
        this.imported = imported != null ? imported : ImportedSymbols.NONE;
        this.functionReturnStack = new ArrayList<>();
        this.constantFolder = new PigLatinConstantFolder(errorReporter, symbolTable);
    }

    // Program and declarations

    @Override
    public String visitProgram(NodeProgram n) {
        if (n.getImports() != null) {
            for (NodeImport imp : n.getImports()) {
                imp.accept(this);
            }
        }
        for (NodeDeclaration decl : n.getGlobalDeclarations()) {
            decl.accept(this);
        }
        for (ASTNode inst : n.getMainInstructions()) {
            inst.accept(this);
        }
        return null;
    }

    @Override
    public String visitVariableDeclaration(NodeVariableDeclaration n) {
        String declaredType = normalizeType(n.getType());

        String exprType = n.getInitializer() != null
                ? evaluateWithExpectedType(n.getInitializer(), declaredType)
                : null;
        if (exprType != null) exprType = normalizeType(exprType);

        if (declaredType == null) {
            //* No explicit type: it is inferred from the initializer
            declaredType = inferDeclaredType(n, exprType);
            n.setType(declaredType);
        } else {
            n.setType(declaredType);
            if (exprType != null && !isFieldCompatible(declaredType, exprType)) {
                errorReporter.reportError("Incompatible type in declaration: expected '" +
                        declaredType + "' but found '" + exprType + "'", n.getLine(), n.getColumn());
            }
        }

        Object constantVal = n.getInitializer() != null ? constantFolder.evaluate(n.getInitializer()) : null;
        VariableSymbol symbol = new VariableSymbol(n.getIdentifier(), n.getType(), n.getLine(), n.getColumn());
        if (constantVal != null) symbol.setConstantValue(constantVal);

        boolean declared = symbolTable.declare(n.getIdentifier(), symbol);
        if (!declared) {
            //* Pass 1 already registered it (duplicates were reported there); only complete a missing type
            Symbol existing = symbolTable.lookupCurrent(n.getIdentifier());
            if (existing instanceof VariableSymbol variable && isError(variable.getType()) && n.getType() != null) {
                variable.setType(n.getType());
            }
        }
        return null;
    }

    /** Type of a declaration written without an explicit type. */
    private String inferDeclaredType(NodeVariableDeclaration n, String exprType) {
        if (exprType == null) return null;
        if (ERROR.equals(exprType)) return ERROR;
        if (VOID_TYPE.equals(exprType)) {
            errorReporter.reportError("Cannot initialize '" + n.getIdentifier() +
                    "' with an expression that returns no value", n.getLine(), n.getColumn());
            return ERROR;
        }
        if (NULL_TYPE.equals(exprType)) {
            errorReporter.reportError("Cannot infer the type of '" + n.getIdentifier() +
                    "' from null; write 'esto " + n.getIdentifier() + ": null : <type>'", n.getLine(), n.getColumn());
            return ERROR;
        }
        return exprType;
    }

    @Override
    public String visitArrayDeclaration(NodeArrayDeclaration n) {
        String elementType = normalizeType(n.getElementType());
        n.setElementType(elementType);

        if (n.getSizeExpression() != null) {
            String sizeType = n.getSizeExpression().accept(this);
            if (!isError(sizeType) && !"NUMERUS".equals(sizeType)) {
                errorReporter.reportError("Array size must be NUMERUS, found '" +
                        sizeType + "'", n.getLine(), n.getColumn());
            }

            Object sizeValue = constantFolder.evaluate(n.getSizeExpression());
            if (sizeValue instanceof Integer sizeInt) {
                n.setSize(sizeInt);
            }
        }

        if (n.getInitialValues() != null) {
            for (ASTNode valNode : n.getInitialValues()) {
                String valType = evaluateWithExpectedType(valNode, elementType);
                if (valType != null && !isFieldCompatible(elementType, valType)) {
                    errorReporter.reportError("Incompatible array element type: expected '" +
                                    elementType + "' but found '" + valType + "'",
                            valNode.getLine(), valNode.getColumn());
                }
            }
        }

        ArraySymbol symbol = new ArraySymbol(n.getIdentifier(), ARRAY_PREFIX + elementType,
                n.getSize(), elementType, n.getLine(), n.getColumn());
        symbolTable.declare(n.getIdentifier(), symbol);
        return null;
    }

    // Statements

    @Override
    public String visitAssignment(NodeAssignment n) {
        String leftType = n.getLvalue() != null ? n.getLvalue().accept(this) : null;
        if (leftType != null) leftType = normalizeType(leftType);

        if (isError(leftType)) {
            if (n.getExpression() != null) n.getExpression().accept(this);
            return null;
        }

        String rightType = n.getExpression() != null
                ? evaluateWithExpectedType(n.getExpression(), leftType)
                : null;
        if (rightType != null) rightType = normalizeType(rightType);

        if (!isError(rightType)) {
            if (!isFieldCompatible(leftType, rightType)) {
                errorReporter.reportError("Cannot assign '" + rightType + "' to '" +
                        leftType + "'", n.getLine(), n.getColumn());
            }
        }
        return null;
    }

    @Override
    public String visitRead(NodeRead n) {
        if (n.getTarget() != null) n.getTarget().accept(this);
        return null;
    }

    @Override
    public String visitPrint(NodePrint n) {
        for (ASTNode item : n.getPrintItems()) {
            item.accept(this);
        }
        return null;
    }

    @Override
    public String visitIf(NodeIf n) {
        checkCondition("if", n.getCondition(), n.getLine(), n.getColumn());
        if (n.getThenBlock() != null) n.getThenBlock().accept(this);

        if (n.getElseIfClauses() != null) {
            for (ElseIfClause clause : n.getElseIfClauses()) {
                ASTNode condition = clause.getCondition();
                if (condition != null) {
                    checkCondition("else if", condition, condition.getLine(), condition.getColumn());
                }
                if (clause.getBlock() != null) clause.getBlock().accept(this);
            }
        }

        if (n.getElseBlock() != null) n.getElseBlock().accept(this);
        return null;
    }

    @Override
    public String visitWhile(NodeWhile n) {
        checkCondition("while", n.getCondition(), n.getLine(), n.getColumn());
        loopDepth++;
        if (n.getBlock() != null) n.getBlock().accept(this);
        loopDepth--;
        return null;
    }

    @Override
    public String visitDoWhile(NodeDoWhile n) {
        loopDepth++;
        if (n.getBlock() != null) n.getBlock().accept(this);
        loopDepth--;

        checkCondition("do-while", n.getCondition(), n.getLine(), n.getColumn());
        return null;
    }

    @Override
    public String visitFor(NodeFor n) {
        symbolTable.pushScope("for_loop");
        if (n.getInitialization() != null) n.getInitialization().accept(this);
        checkCondition("for", n.getCondition(), n.getLine(), n.getColumn());
        if (n.getUpdate() != null) n.getUpdate().accept(this);

        loopDepth++;
        if (n.getBlock() != null) n.getBlock().accept(this);
        loopDepth--;

        symbolTable.popScope();
        return null;
    }

    /** A control-flow condition must be boolean; an expression already marked as wrong is not reported again. */
    private void checkCondition(String statement, ASTNode condition, int line, int column) {
        if (condition == null) return;
        String condType = condition.accept(this);
        if (!isError(condType) && !isBooleanType(condType)) {
            errorReporter.reportError("'" + statement + "' condition must be boolean, found '" +
                    condType + "'", line, column);
        }
    }

    @Override
    public String visitContinue(NodeContinue n) {
        return null;
    }

    @Override
    public String visitBreak(NodeBreak n) {
        return null;
    }

    @Override
    public String visitReturn(NodeReturn n) {
        String expectedType = peekReturn();
        String exprType = n.getExpression() != null
                ? evaluateWithExpectedType(n.getExpression(), expectedType)
                : VOID_TYPE;
        if (exprType != null) exprType = normalizeType(exprType);

        if (expectedType != null && !isFieldCompatible(expectedType, exprType)) {
            errorReporter.reportError("Incompatible return type: expected '" +
                    expectedType + "' but found '" + exprType + "'", n.getLine(), n.getColumn());
        }
        return null;
    }

    @Override
    public String visitBlock(NodeBlock n) {
        symbolTable.pushScope("block");
        for (ASTNode inst : n.getInstructions()) {
            inst.accept(this);
        }
        symbolTable.popScope();
        return null;
    }

    // Objects, calls and accesses

    @Override
    public String visitNewInstance(NodeNewInstance n) {
        List<ASTNode> args = argumentsOf(n.getArguments());
        String className = n.getClassName();

        if (!imported.isClass(className)) {
            typesOf(args);
            errorReporter.reportError("Class '" + className +
                    "' is not defined in any imported .z file", n.getLine(), n.getColumn());
            return ERROR;
        }

        ImportedSymbols.Signature constructor = imported.resolveConstructor(className, args.size());
        List<String> argTypes = constructor != null
                ? evaluateArgs(args, constructor.paramTypes())
                : typesOf(args);

        if (constructor == null) {
            //* A class without a declared constructor only accepts 'novus X()'
            if (!args.isEmpty()) {
                errorReporter.reportError("Class '" + className + "' has no constructor that takes " +
                        args.size() + " argument(s)", n.getLine(), n.getColumn());
            }
        } else {
            checkArguments("constructor of '" + className + "'", constructor, args, argTypes);
        }
        return className;
    }

    @Override
    public String visitMethodCall(NodeMethodCall n) {
        List<ASTNode> args = argumentsOf(n.getArguments());
        ASTNode target = n.getTarget();

        //* name(args): free function of a .y file
        if (target instanceof NodeIdentifier id) {
            return checkFreeCall(n, id.getId(), args);
        }

        //* receiver.name(args): method of an object of a .z class
        if (target instanceof NodeFieldAccess access && access.getCurrentNode() != null) {
            String receiverType = access.getCurrentNode().accept(this);
            return checkObjectCall(n, receiverType, access.getFieldName(), args);
        }

        typesOf(args);
        errorReporter.reportError("Unsupported call target; expected 'function(...)' or 'object.method(...)'",
                n.getLine(), n.getColumn());
        return ERROR;
    }

    @Override
    public String visitFunctionCall(NodeFunctionCall n) {
        List<ASTNode> args = argumentsOf(n.getArguments());

        if (n.getFunctionName() == null) {
            typesOf(args);
            return ERROR;
        }

        if (n.getCurrentNode() != null) {
            String receiverType = n.getCurrentNode().accept(this);
            return checkObjectCall(n, receiverType, n.getFunctionName(), args);
        }

        return checkFreeCall(n, n.getFunctionName(), args);
    }

    private String checkFreeCall(ASTNode node, String name, List<ASTNode> args) {
        ImportedSymbols.Signature function = imported.resolveFunction(name, args.size());
        if (function == null) {
            typesOf(args);
            errorReporter.reportError("Function '" + name + "' that takes " + args.size() +
                    " argument(s) is not defined in any imported .y file", node.getLine(), node.getColumn());
            return ERROR;
        }
        List<String> argTypes = evaluateArgs(args, function.paramTypes());
        checkArguments("function '" + name + "'", function, args, argTypes);
        return function.returnType() != null ? function.returnType() : VOID_TYPE;
    }

    private String checkObjectCall(ASTNode node, String receiverType, String methodName,
                                   List<ASTNode> args) {
        if (isError(receiverType)) {
            typesOf(args);
            return ERROR;
        }

        if (!imported.isClass(receiverType)) {
            typesOf(args);
            errorReporter.reportError("Cannot call method '" + methodName + "' on a value of type '" +
                            receiverType + "'; only objects of imported .z classes have methods",
                    node.getLine(), node.getColumn());
            return ERROR;
        }

        ImportedSymbols.Signature method = imported.resolveMethod(receiverType, methodName, args.size());
        if (method == null) {
            typesOf(args);
            errorReporter.reportError("Class '" + receiverType + "' has no method '" + methodName +
                    "' that takes " + args.size() + " argument(s)", node.getLine(), node.getColumn());
            return ERROR;
        }
        List<String> argTypes = evaluateArgs(args, method.paramTypes());
        checkArguments("method '" + receiverType + "." + methodName + "'", method, args, argTypes);
        return method.returnType() != null ? method.returnType() : VOID_TYPE;
    }

    private void checkArguments(String what, ImportedSymbols.Signature signature,
                                List<ASTNode> args, List<String> argTypes) {
        List<String> params = signature.paramTypes();
        if (params == null) return;
        int count = Math.min(params.size(), args.size());
        for (int i = 0; i < count; i++) {
            String paramType = params.get(i);
            String argType = argTypes.get(i);
            if (!isFieldCompatible(paramType, argType)) {
                ASTNode arg = args.get(i);
                errorReporter.reportError("Argument " + (i + 1) + " of " + what + " expects '" +
                        paramType + "' but found '" + argType + "'", arg.getLine(), arg.getColumn());
            }
        }
    }

    private List<String> evaluateArgs(List<ASTNode> args, List<String> paramTypes) {
        if (args == null) return List.of();
        if (paramTypes == null) return typesOf(args);

        List<String> types = new ArrayList<>();
        for (int i = 0; i < args.size(); i++) {
            ASTNode arg = args.get(i);
            if (arg == null) {
                types.add(ERROR);
                continue;
            }
            if (i < paramTypes.size()) {
                String expected = paramTypes.get(i);
                types.add(evaluateWithExpectedType(arg, expected));
            } else {
                types.add(arg.accept(this));
            }
        }
        return types;
    }

    private List<ASTNode> argumentsOf(List<ASTNode> arguments) {
        return arguments != null ? arguments : List.of();
    }

    private List<String> typesOf(List<ASTNode> args) {
        List<String> types = new ArrayList<>();
        for (ASTNode arg : args) {
            types.add(arg != null ? arg.accept(this) : ERROR);
        }
        return types;
    }

    @Override
    public String visitLvalue(NodeLvalue n) {
        return ERROR;
    }

    @Override
    public String visitImport(NodeImport n) {
        return null;
    }

    @Override
    public String visitFieldAccess(NodeFieldAccess n) {
        if (n.getCurrentNode() == null) return ERROR;
        String baseType = n.getCurrentNode().accept(this);
        if (isError(baseType)) return ERROR;

        String field = n.getFieldName();

        if (isArray(baseType)) {
            if ("length".equals(field)) return "NUMERUS";
            errorReporter.reportError("Arrays only have the field 'length', found '" + field + "'",
                    n.getLine(), n.getColumn());
            return ERROR;
        }

        if (imported.isClass(baseType)) {
            String fieldType = imported.fieldType(baseType, field);
            if (fieldType == null) {
                errorReporter.reportError("Class '" + baseType + "' has no field '" + field + "'",
                        n.getLine(), n.getColumn());
                return ERROR;
            }
            return fieldType;
        }

        if (imported.isStruct(baseType)) {
            errorReporter.reportError("Cannot access field '" + field + "' of struct '" + baseType +
                            "': PigLatin only reads fields of objects of imported .z classes",
                    n.getLine(), n.getColumn());
            return ERROR;
        }

        errorReporter.reportError("Cannot access field '" + field + "' on a value of type '" +
                baseType + "'", n.getLine(), n.getColumn());
        return ERROR;
    }

    @Override
    public String visitIndexAccess(NodeIndexAccess n) {
        String arrayType = n.getCurrentNode() != null ? n.getCurrentNode().accept(this) : ERROR;
        String indexType = n.getIndexExpression() != null ? n.getIndexExpression().accept(this) : ERROR;

        if (!isError(indexType) && !"NUMERUS".equals(indexType)) {
            errorReporter.reportError("Array index must be NUMERUS, found '" + indexType +
                    "'", n.getLine(), n.getColumn());
        }

        if (isError(arrayType)) return ERROR;

        if (!isArray(arrayType)) {
            errorReporter.reportError("Cannot index a non-array type '" +
                    arrayType + "'", n.getLine(), n.getColumn());
            return ERROR;
        }

        String elementType = typeTable.getArrayElementType(arrayType);
        return elementType != null ? elementType : ERROR;
    }

    // Literals and identifiers

    @Override
    public String visitIntegerLiteral(NodeIntegerLiteral n) {
        return "NUMERUS";
    }

    @Override
    public String visitDecimalLiteral(NodeDecimalLiteral n) {
        return "DECIMALIS";
    }

    @Override
    public String visitStringLiteral(NodeStringLiteral n) {
        return "TEXTUM";
    }

    @Override
    public String visitCharLiteral(NodeCharLiteral n) {
        return "LITTERA";
    }

    @Override
    public String visitBooleanLiteral(NodeBooleanLiteral n) {
        return "BOOL";
    }

    @Override
    public String visitNullLiteral(NodeNullLiteral n) {
        return NULL_TYPE;
    }

    @Override
    public String visitIdentifier(NodeIdentifier n) {
        Symbol sym = symbolTable.lookup(n.getId());
        //* An undeclared identifier was already reported in pass 1
        return sym != null && sym.getType() != null ? sym.getType() : ERROR;
    }

    @Override
    public String visitStructLiteral(NodeStructLiteral n) {
        if (n.getStructName() != null && isStructType(n.getStructName())) {
            return checkStructLiteral(n.getStructName(), n.getValues(), n.getLine(), n.getColumn());
        }
        if (n.getValues() != null) {
            for (ASTNode value : n.getValues()) {
                if (value != null) value.accept(this);
            }
        }
        return ERROR;
    }

    private boolean isStructType(String type) {
        return type != null && !isArray(type) && imported.isStruct(type);
    }

    private boolean isFieldCompatible(String expected, String actual) {
        if (isError(expected) || isError(actual)) return true;
        if (isArray(expected) && isArray(actual)) {
            return isFieldCompatible(expected.substring(ARRAY_PREFIX.length()),
                    actual.substring(ARRAY_PREFIX.length()));
        }
        if ("NUMERUS".equals(expected) && "DECIMALIS".equals(actual)) {
            return false;
        }
        return areTypesCompatible(expected, actual);
    }

    private String evaluateWithExpectedType(ASTNode expr, String expectedType) {
        if (expr == null) return null;
        if (expectedType == null) return expr.accept(this);

        String normalizedExpected = normalizeType(expectedType);
        if (isStructType(normalizedExpected)) {
            if (expr instanceof NodeArrayLiteral lit) {
                return checkStructLiteral(normalizedExpected, lit.getValues(), lit.getLine(), lit.getColumn());
            }
            if (expr instanceof NodeStructLiteral lit) {
                return checkStructLiteral(normalizedExpected, lit.getValues(), lit.getLine(), lit.getColumn());
            }
        } else if (isArray(normalizedExpected)) {
            if (expr instanceof NodeArrayLiteral lit) {
                return checkArrayLiteralWithExpected(normalizedExpected, lit);
            }
        }
        return expr.accept(this);
    }

    private String checkArrayLiteralWithExpected(String expectedArrayType, NodeArrayLiteral literal) {
        String elementType = expectedArrayType.substring(ARRAY_PREFIX.length());
        if (literal.getValues() != null) {
            for (ASTNode elem : literal.getValues()) {
                if (elem == null) continue;
                String elemType = evaluateWithExpectedType(elem, elementType);
                if (!isError(elemType) && !isFieldCompatible(elementType, elemType)) {
                    errorReporter.reportError("Array element expects '" + elementType +
                            "' but found '" + elemType + "'", elem.getLine(), elem.getColumn());
                }
            }
        }
        return expectedArrayType;
    }

    private String checkStructLiteral(String structType, List<ASTNode> values, int line, int column) {
        if (!isStructType(structType)) {
            return ERROR;
        }

        List<ImportedSymbols.Field> fields = imported.structFields(structType);
        if (fields == null) fields = List.of();

        List<ASTNode> inits = values != null ? values : List.of();

        if (inits.size() != fields.size()) {
            errorReporter.reportError("Struct '" + structType + "' expects " + fields.size() +
                    " field(s) but found " + inits.size(), line, column);
        }

        int common = Math.min(inits.size(), fields.size());
        for (int i = 0; i < common; i++) {
            ASTNode value = inits.get(i);
            ImportedSymbols.Field field = fields.get(i);
            if (value == null) continue;

            String actualType = evaluateWithExpectedType(value, field.type());
            if (!isError(actualType) && !isFieldCompatible(field.type(), actualType)) {
                errorReporter.reportError("Field '" + field.name() + "' of struct '" + structType +
                        "' expects '" + field.type() + "' but found '" + actualType + "'",
                        value.getLine(), value.getColumn());
            }
        }

        for (int i = common; i < inits.size(); i++) {
            ASTNode extra = inits.get(i);
            if (extra != null) extra.accept(this);
        }

        return structType;
    }

    @Override
    public String visitArrayLiteral(NodeArrayLiteral n) {
        String elementType = null;
        if (n.getValues() != null) {
            for (ASTNode value : n.getValues()) {
                if (value == null) continue;
                String type = value.accept(this);
                if (isError(type)) {
                    elementType = elementType == null ? ERROR : elementType;
                    continue;
                }
                if (elementType == null || isError(elementType) || NULL_TYPE.equals(elementType)) {
                    elementType = type;
                } else if ("NUMERUS".equals(elementType) && "DECIMALIS".equals(type)) {
                    elementType = "DECIMALIS";
                } else if (!areTypesCompatible(elementType, type) && !areTypesCompatible(type, elementType)) {
                    errorReporter.reportError("Array literal elements must share one type: found '" +
                            elementType + "' and '" + type + "'", value.getLine(), value.getColumn());
                }
            }
        }
        if (elementType == null || isError(elementType) || NULL_TYPE.equals(elementType)) return ERROR;
        return ARRAY_PREFIX + elementType;
    }

    // Operators

    @Override
    public String visitBinaryOperation(NodeBinaryOperation n) {
        String left = n.getLeft().accept(this);
        String right = n.getRight().accept(this);
        String op = n.getOperator();

        if ("+".equals(op) || "-".equals(op) || "*".equals(op) || "/".equals(op)) {
            return checkArithmetic(n, op, left, right);
        }

        if ("==".equals(op) || "!=".equals(op)) {
            if (!areComparable(left, right)) {
                errorReporter.reportError("Cannot compare incompatible types '" +
                        left + "' and '" + right + "'", n.getLine(), n.getColumn());
            }
            return "BOOL";
        }

        if ("<".equals(op) || ">".equals(op) || "<=".equals(op) || ">=".equals(op)) {
            boolean known = !isError(left) && !isError(right);
            if (known && !(isNumericType(left) && isNumericType(right))) {
                errorReporter.reportError("Operator '" + op + "' requires numeric operands, found '" +
                        left + "' and '" + right + "'", n.getLine(), n.getColumn());
            }
            return "BOOL";
        }

        if ("&&".equals(op) || "||".equals(op)) {
            boolean leftBad = !isError(left) && !isBooleanType(left);
            boolean rightBad = !isError(right) && !isBooleanType(right);
            if (leftBad || rightBad) {
                errorReporter.reportError("Logical operator '" + op + "' requires boolean operands, found '" +
                        left + "' and '" + right + "'", n.getLine(), n.getColumn());
            }
            return "BOOL";
        }

        return ERROR;
    }

    private String checkArithmetic(NodeBinaryOperation n, String op, String left, String right) {
        boolean textLeft = "TEXTUM".equals(left);
        boolean textRight = "TEXTUM".equals(right);

        if (textLeft || textRight) {
            if ("+".equals(op)) return "TEXTUM";
            errorReporter.reportError("Invalid operator '" + op + "' for TEXTUM",
                    n.getLine(), n.getColumn());
            return ERROR;
        }

        if (isError(left) || isError(right)) return ERROR;

        if (VOID_TYPE.equals(left) || VOID_TYPE.equals(right)
                || NULL_TYPE.equals(left) || NULL_TYPE.equals(right)) {
            errorReporter.reportError("Operator '" + op + "' cannot be applied to '" +
                    left + "' and '" + right + "'", n.getLine(), n.getColumn());
            return ERROR;
        }

        if ("DECIMALIS".equals(left) || "DECIMALIS".equals(right)) return "DECIMALIS";
        return "NUMERUS";
    }

    @Override
    public String visitUnaryOperation(NodeUnaryOperation n) {
        String type = n.getOperand().accept(this);
        String op = n.getOperator();

        if ("++".equals(op) || "--".equals(op)) {
            return checkIncrementDecrement(n.getOperand(), op, type, n);
        }

        if ("!".equals(op)) {
            if (!isError(type) && !isBooleanType(type)) {
                errorReporter.reportError("Invalid unary operator '!' for type '" + type + "'",
                        n.getLine(), n.getColumn());
            }
            return "BOOL";
        }

        if ("+".equals(op) || "-".equals(op)) {
            if (isError(type)) return ERROR;
            if (isNumericType(type)) return type;
        }

        errorReporter.reportError("Invalid unary operator '" + op +
                "' for type '" + type + "'", n.getLine(), n.getColumn());
        return ERROR;
    }

    @Override
    public String visitIncrementDecrement(NodeIncrementDecrement n) {
        String operandType = n.getOperand().accept(this);
        String op = n.getOperation() != null ? n.getOperation() : "++";
        return checkIncrementDecrement(n.getOperand(), op, operandType, n);
    }

    /** ++ and -- apply to a numeric variable, field or array element (pre or postfix). */
    private String checkIncrementDecrement(ASTNode operand, String op, String operandType, ASTNode node) {
        boolean validTarget = operand instanceof NodeIdentifier
                || operand instanceof NodeFieldAccess
                || operand instanceof NodeIndexAccess;

        if (!validTarget) {
            errorReporter.reportError("Operand of '" + op + "' must be a variable, field or array element",
                    node.getLine(), node.getColumn());
            return ERROR;
        }
        if (isError(operandType)) return ERROR;

        if (!isNumericType(operandType)) {
            errorReporter.reportError("Increment/Decrement operator requires a numeric operand, found '" +
                    operandType + "'", node.getLine(), node.getColumn());
            return ERROR;
        }
        return operandType;
    }

    // Type helpers

    private String normalizeType(String type) {
        if ("bool".equals(type) || "BOOLEAN".equals(type)) return "BOOL";
        return type;
    }

    /** null (unknown) and "ERROR" both mean: already reported or unknowable, do not report again. */
    private boolean isError(String type) {
        return type == null || ERROR.equals(type);
    }

    private boolean isArray(String type) {
        return type != null && type.startsWith(ARRAY_PREFIX);
    }

    private boolean isBooleanType(String type) {
        return "BOOL".equals(type) || "BOOLEAN".equals(type) ||
                "bool".equals(type) || "VERUM".equals(type) || "FALSUS".equals(type);
    }

    private boolean isNumericType(String type) {
        return "NUMERUS".equals(type) || "DECIMALIS".equals(type) || "LITTERA".equals(type);
    }

    /** true for TEXTUM, arrays, classes and structs: the types that can hold null. */
    private boolean isNullable(String type) {
        if (isError(type)) return true;
        if (isNumericType(type) || isBooleanType(type)) return false;
        return !VOID_TYPE.equals(type) && !NULL_TYPE.equals(type);
    }

    /** Can a value of type 'actual' be stored where 'expected' is required? */
    private boolean areTypesCompatible(String expected, String actual) {
        if (isError(expected) || isError(actual)) return true;
        if (expected.equals(actual)) return true;

        if (NULL_TYPE.equals(actual)) return isNullable(expected);
        if (NULL_TYPE.equals(expected) || VOID_TYPE.equals(expected) || VOID_TYPE.equals(actual)) return false;

        if (("NUMERUS".equals(expected) && "DECIMALIS".equals(actual)) ||
                ("DECIMALIS".equals(expected) && "NUMERUS".equals(actual))) {
            return true;
        }

        if (isBooleanType(expected) && isBooleanType(actual)) {
            return true;
        }

        //* Polimorfismo con clases de Zetariano: una subclase es asignable a su superclase
        if (typeTable.isSubtypeOf(actual, expected)) {
            return true;
        }

        if (isArray(expected) && isArray(actual)) {
            return areTypesCompatible(expected.substring(ARRAY_PREFIX.length()),
                    actual.substring(ARRAY_PREFIX.length()));
        }

        return false;
    }

    /** == and != accept numbers, same-type values, null against any reference, and sub/super class pairs. */
    private boolean areComparable(String left, String right) {
        if (isError(left) || isError(right)) return true;
        if (isNumericType(left) && isNumericType(right)) return true;
        if (NULL_TYPE.equals(left) && NULL_TYPE.equals(right)) return true;
        return areTypesCompatible(left, right) || areTypesCompatible(right, left);
    }

    private void pushReturn(String type) {
        functionReturnStack.add(type);
    }

    private String popReturn() {
        if (functionReturnStack.isEmpty()) return null;
        return functionReturnStack.remove(functionReturnStack.size() - 1);
    }

    private String peekReturn() {
        if (functionReturnStack.isEmpty()) return null;
        return functionReturnStack.get(functionReturnStack.size() - 1);
    }

}