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
import com.piglatin.piglatin.domain.types.TypeTable;
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
 *  - Verify that function call arguments match the signature.
 *  - Validate that REDDERE expressions match the current function's return type.
 *  - Validate that control-flow conditions are boolean.
 *
 * This class reconstructs its own Symbol Table while traversing the AST,
 * allowing identifier types to be resolved at any nesting level.
 */
@Getter
@Setter
public class TypeChecker implements Visitor<String> {

    private final SymbolTable symbolTable;
    private final TypeTable typeTable;
    private final SemanticErrorReporter errorReporter;
    private final List<String> functionReturnStack;
    private final ConstantFolder constantFolder;
    private int loopDepth = 0;
    private boolean hasImports = false;

    public TypeChecker(TypeTable typeTable, SemanticErrorReporter errorReporter, SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        this.typeTable = typeTable;
        this.errorReporter = errorReporter;
        this.functionReturnStack = new ArrayList<>();
        this.constantFolder = new ConstantFolder(errorReporter, symbolTable);
    }

    // ============================================================
    // PROGRAM
    // ============================================================

    @Override
    public String visitProgram(NodeProgram n) {
        this.hasImports = n.getImports() != null && !n.getImports().isEmpty();
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

    // ============================================================
    // STATEMENTS
    // ============================================================

    @Override
    public String visitVariableDeclaration(NodeVariableDeclaration n) {
        String declaredType = normalizeType(n.getType());

        String exprType = n.getInitializer() != null ? n.getInitializer().accept(this) : null;
        if (exprType != null) exprType = normalizeType(exprType);

        if (declaredType == null && exprType != null) {
            n.setType(exprType);
            declaredType = exprType;
        } else {
            n.setType(declaredType);
        }

        if (declaredType != null && exprType != null) {
            if (!areTypesCompatible(declaredType, exprType)) {
                errorReporter.reportError("Incompatible type in declaration: expected '" +
                        declaredType + "' but found '" + exprType + "'", n.getLine(), n.getColumn());
            }
        }

        Object constantVal = n.getInitializer() != null ? constantFolder.evaluate(n.getInitializer()) : null;
        VariableSymbol symbol = new VariableSymbol(n.getIdentifier(), n.getType(), n.getLine(), n.getColumn());
        if (constantVal != null) symbol.setConstantValue(constantVal);

        symbolTable.declare(n.getIdentifier(), symbol);
        return null;
    }

    @Override
    public String visitArrayDeclaration(NodeArrayDeclaration n) {
        String elementType = normalizeType(n.getElementType());
        n.setElementType(elementType);

        if (n.getSizeExpression() != null) {
            String sizeType = n.getSizeExpression().accept(this);
            if (!"NUMERUS".equals(sizeType)) {
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
                String valType = valNode.accept(this);
                if (valType != null && !areTypesCompatible(elementType, valType)) {
                    errorReporter.reportError("Incompatible array element type: expected '" +
                            elementType + "' but found '" + valType + "'",
                            valNode.getLine(), valNode.getColumn());
                }
            }
        }

        ArraySymbol symbol = new ArraySymbol(n.getIdentifier(), "SERIES_" + elementType,
                n.getSize(), elementType, n.getLine(), n.getColumn());
        symbolTable.declare(n.getIdentifier(), symbol);
        return null;
    }


    // ============================================================
    // INSTRUCTIONS
    // ============================================================

    @Override
    public String visitAssignment(NodeAssignment n) {
        String rightType = n.getExpression() != null ? n.getExpression().accept(this) : null;
        String leftType = n.getLvalue() != null ? n.getLvalue().accept(this) : null;

        if (leftType != null && rightType != null && !"ERROR".equals(leftType) && !"ERROR".equals(rightType)) {
            if (!areTypesCompatible(leftType, rightType)) {
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
        if (n.getCondition() != null) {
            String condType = n.getCondition().accept(this);
            if (!isBooleanType(condType)) {
                errorReporter.reportError("'if' condition must be boolean, found '" +
                        condType + "'", n.getLine(), n.getColumn());
            }
        }
        if (n.getThenBlock() != null) n.getThenBlock().accept(this);
        if (n.getElseBlock() != null) n.getElseBlock().accept(this);
        return null;
    }

    @Override
    public String visitWhile(NodeWhile n) {
        if (n.getCondition() != null) {
            String condType = n.getCondition().accept(this);
            if (!isBooleanType(condType)) {
                errorReporter.reportError("'while' condition must be boolean, found '" +
                        condType + "'", n.getLine(), n.getColumn());
            }
        }
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

        if (n.getCondition() != null) {
            String condType = n.getCondition().accept(this);
            if (!isBooleanType(condType)) {
                errorReporter.reportError("'do-while' condition must be boolean, found '" +
                        condType + "'", n.getLine(), n.getColumn());
            }
        }
        return null;
    }

    @Override
    public String visitFor(NodeFor n) {
        symbolTable.pushScope("for_loop");
        if (n.getInitialization() != null) n.getInitialization().accept(this);
        if (n.getCondition() != null) {
            String condType = n.getCondition().accept(this);
            if (!isBooleanType(condType)) {
                errorReporter.reportError("'for' condition must be boolean, found '" +
                        condType + "'", n.getLine(), n.getColumn());
            }
        }
        if (n.getUpdate() != null) n.getUpdate().accept(this);

        loopDepth++;
        if (n.getBlock() != null) n.getBlock().accept(this);
        loopDepth--;

        symbolTable.popScope();
        return null;
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
        String exprType = n.getExpression() != null ? n.getExpression().accept(this) : "VOID";

        if (expectedType != null && !areTypesCompatible(expectedType, exprType)) {
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

    @Override
    public String visitNewInstance(NodeNewInstance n) {
        if (n.getArguments() != null) {
            for (ASTNode arg : n.getArguments()) {
                arg.accept(this);
            }
        }
        return n.getClassName();
    }

    // ============================================================
    // LVALUE
    // ============================================================

    @Override
    public String visitLvalue(NodeLvalue n) {
        return null;
    }

    @Override
    public String visitImport(NodeImport n) {
        this.hasImports = true;
        return null;
    }

    @Override
    public String visitFieldAccess(NodeFieldAccess n) {
        return null;
    }

    @Override
    public String visitIndexAccess(NodeIndexAccess n) {
        String arrayType = n.getIndexExpression().accept(this);

        if (arrayType != null && !arrayType.startsWith("SERIES_") && !"ERROR".equals(arrayType)) {
            errorReporter.reportError("Cannot index a non-array type '" +
                    arrayType + "'", n.getLine(), n.getColumn());
            return "ERROR";
        }

        String indexType = n.getIndexExpression().accept(this);
        if (!"NUMERUS".equals(indexType) && !"ERROR".equals(indexType)) {
            errorReporter.reportError("Array index must be NUMERUS, found '" + indexType +
                    "'", n.getLine(), n.getColumn());
        }

        return typeTable.getArrayElementType(arrayType);
    }

    // ============================================================
    // EXPRESSIONS
    // ============================================================

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
    public String visitIdentifier(NodeIdentifier n) {
        Symbol sym = symbolTable.lookup(n.getId());
        return sym != null ? sym.getType() : "ERROR";
    }

    @Override
    public String visitFunctionCall(NodeFunctionCall n) {
        if (n.getCurrentNode() != null) {
            String targetType = n.getCurrentNode().accept(this);

            if (n.getArguments() != null) {
                for (ASTNode arg : n.getArguments()) {
                    arg.accept(this);
                }
            }

            if ("ERROR".equals(targetType)) {
                return "ERROR";
            }

            if ("toString".equals(n.getFunctionName())) {
                return "TEXTUM";
            }

            return "VOID";
        }

        Symbol sym = symbolTable.lookup(n.getFunctionName());
        if (!(sym instanceof FunctionSymbol funcSym)) {

            if (hasImports){
                if (n.getArguments() != null) {
                    for (ASTNode arg : n.getArguments()) {
                        arg.accept(this);
                    }
                }
                return "VOID";
            }

            errorReporter.reportError("Function '" + n.getFunctionName() +
                    "' is not declared", n.getLine(), n.getColumn());
            return "ERROR";
        }

        List<ASTNode> args = n.getArguments();
        List<VariableSymbol> params = funcSym.getParameters();

        if (params != null && !params.isEmpty()) {
            if (args.size() != params.size()) {
                errorReporter.reportError("Function '" + n.getFunctionName() +
                        "' expects " + params.size() + " arguments, but got " +
                        args.size(), n.getLine(), n.getColumn());
                return funcSym.getReturnType();
            }

            for (int i = 0; i < args.size(); i++) {
                String argType = args.get(i).accept(this);
                String paramType = params.get(i).getType();

                if (argType != null && paramType != null && !areTypesCompatible(paramType, argType)) {
                    errorReporter.reportError("Argument " + (i + 1) +
                            " in function call '" + n.getFunctionName() + "' expected '" + paramType +
                            "' but found '" + argType + "'", n.getLine(), n.getColumn());
                }
            }
        } else {
            if (args != null) {
                for (ASTNode arg : args) {
                    arg.accept(this);
                }
            }
        }

        return funcSym.getReturnType() != null ? funcSym.getReturnType() : "VOID";
    }

    @Override
    public String visitBinaryOperation(NodeBinaryOperation n) {
        String left = n.getLeft().accept(this);
        String right = n.getRight().accept(this);
        String op = n.getOperator();

        if ("+".equals(op) || "-".equals(op) || "*".equals(op) || "/".equals(op)) {
            if ("TEXTUM".equals(left) || "TEXTUM".equals(right)) {
                if ("+".equals(op)) return "TEXTUM";
                errorReporter.reportError("Invalid operator '" + op + "' for TEXTUM",
                        n.getLine(), n.getColumn());
                return "ERROR";
            }
            if ("DECIMALIS".equals(left) || "DECIMALIS".equals(right)) return "DECIMALIS";
            return "NUMERUS";
        }

        if ("==".equals(op) || "!=".equals(op) || "<".equals(op) || ">".equals(op) ||
                "<=".equals(op) || ">=".equals(op)) {
            if (!areTypesCompatible(left, right)) {
                errorReporter.reportError("Cannot compare incompatible types '" +
                        left + "' and '" + right + "'", n.getLine(), n.getColumn());
            }
            return "BOOL";
        }

        if ("&&".equals(op) || "||".equals(op)) {
            if (!isBooleanType(left) || !isBooleanType(right)) {
                errorReporter.reportError("Logical operator '" + op +
                        "' requires boolean operands", n.getLine(), n.getColumn());
            }
            return "BOOL";
        }

        return "ERROR";
    }

    @Override
    public String visitUnaryOperation(NodeUnaryOperation n) {
        String type = n.getOperand().accept(this);
        String op = n.getOperator();

        if ("!".equals(op) && isBooleanType(type)) return "BOOL";
        if (("+".equals(op) || "-".equals(op)) && isNumericType(type)) return type;

        errorReporter.reportError("Invalid unary operator '" + op +
                "' for type '" + type + "'", n.getLine(), n.getColumn());
        return "ERROR";
    }

    @Override
    public String visitIncrementDecrement(NodeIncrementDecrement n) {
        String operandType = n.getOperand().accept(this);

        if (!isNumericType(operandType) && !"ERROR".equals(operandType)) {
            errorReporter.reportError("Increment/Decrement operator requires a numeric operand, found '" +
                    operandType + "'", n.getLine(), n.getColumn());
            return "ERROR";
        }

        return operandType;
    }

    @Override
    public String visitStructLiteral(NodeStructLiteral n) {
        return null;
    }

    @Override
    public String visitArrayLiteral(NodeArrayLiteral n) {
        return null;
    }

    // ============================================================
    // AUXILIARY METHODS
    // ============================================================

    private String normalizeType(String type) {
        if ("bool".equals(type) || "BOOLEAN".equals(type)) return "BOOL";
        return type;
    }

    private boolean isBooleanType(String type) {
        return "BOOL".equals(type) || "BOOLEAN".equals(type) ||
                "bool".equals(type) || "VERUM".equals(type) || "FALSUS".equals(type);
    }

    private boolean isNumericType(String type) {
        return "NUMERUS".equals(type) || "DECIMALIS".equals(type) || "LITTERA".equals(type);
    }

    /**
     * Checks if two types are compatible for assignment/comparison.
     * Rules:
     *  - Same type -> compatible.
     *  - NUMERUS and DECIMALIS -> compatible -> implicit promotion.
     */
    private boolean areTypesCompatible(String expected, String actual) {
        if (expected == null || actual == null) return true;
        if (expected.equals(actual)) return true;

        //* Numerical promotion
        if (("NUMERUS".equals(expected) && "DECIMALIS".equals(actual)) ||
                ("DECIMALIS".equals(expected) && "NUMERUS".equals(actual))) {
            return true;
        }

        //* Booleans: all boolean types are compatible.
        if (isBooleanType(expected) && isBooleanType(actual)) {
            return true;
        }

        //* Arrays: compatible if the elements are compatible.
        if (expected.startsWith("SERIES_") && actual.startsWith("SERIES_")) {
            String expectedElem = expected.substring(7);
            String actualElem = actual.substring(7);
            return areTypesCompatible(expectedElem, actualElem);
        }

        return false;
    }

    private String buildSignature(String name, List<String> paramTypes) {
        return name + "(" + String.join(",", paramTypes) + ")";
    }

    // Stack operations using List
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
