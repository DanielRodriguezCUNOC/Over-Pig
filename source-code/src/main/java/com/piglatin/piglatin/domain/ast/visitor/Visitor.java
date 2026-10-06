package com.piglatin.piglatin.domain.ast.visitor;

import com.piglatin.piglatin.domain.ast.nodes.NodeImport;
import com.piglatin.piglatin.domain.ast.nodes.declaration.*;
import com.piglatin.piglatin.domain.ast.nodes.lvalue.*;
import com.piglatin.piglatin.domain.ast.nodes.expression.*;
import com.piglatin.piglatin.domain.ast.nodes.instruction.*;
import com.piglatin.piglatin.domain.ast.nodes.literal.*;
import com.piglatin.piglatin.domain.ast.principal.NodeProgram;

public interface Visitor<T> {

        T visitProgram(NodeProgram n);
        T visitVariableDeclaration(NodeVariableDeclaration n);
        T visitArrayDeclaration(NodeArrayDeclaration n);
        T visitAssignment(NodeAssignment n);
        T visitRead(NodeRead n);
        T visitPrint(NodePrint n);
        T visitIf(NodeIf n);
        T visitWhile(NodeWhile n);
        T visitDoWhile(NodeDoWhile n);
        T visitFor(NodeFor n);
        T visitContinue(NodeContinue n);
        T visitBreak(NodeBreak n);
        T visitReturn(NodeReturn n);
        T visitBlock(NodeBlock n);
        T visitNewInstance(NodeNewInstance n);
        T visitStructLiteral(NodeStructLiteral n);
        T visitIntegerLiteral(NodeIntegerLiteral n);
        T visitDecimalLiteral(NodeDecimalLiteral n);
        T visitStringLiteral(NodeStringLiteral n);
        T visitCharLiteral(NodeCharLiteral n);
        T visitBooleanLiteral(NodeBooleanLiteral n);
        T visitIdentifier(NodeIdentifier n);
        T visitIndexAccess(NodeIndexAccess n);
        T visitFieldAccess (NodeFieldAccess n);
        T visitBinaryOperation(NodeBinaryOperation n);
        T visitUnaryOperation(NodeUnaryOperation n);
        T visitIncrementDecrement(NodeIncrementDecrement n);
        T visitArrayLiteral(NodeArrayLiteral n);
        T visitLvalue(NodeLvalue n);
        T visitImport(NodeImport n);
        T visitFunctionCall (NodeFunctionCall n);
        T visitNullLiteral(NodeNullLiteral n);
        T visitMethodCall(NodeMethodCall n);
}
