package com.piglatin.zetariano.domain.ast.visitor;

import com.piglatin.zetariano.domain.ast.principal.*;
import com.piglatin.zetariano.domain.ast.statements.*;
import com.piglatin.zetariano.domain.ast.expressions.*;
import com.piglatin.zetariano.domain.ast.expressions.literals.*;

public interface Visitor<T> {
    T visitProgram(NodeProgram n);
    T visitImport(NodeImport n);
    T visitClassDeclaration(NodeClassDeclaration n);
    T visitFieldDeclaration(NodeFieldDeclaration n);
    T visitMethodDeclaration(NodeMethodDeclaration n);
    T visitConstructorDeclaration(NodeConstructorDeclaration n);
    T visitParameter(NodeParameter n);
    T visitBlock(NodeBlock n);
    T visitVariableDeclaration(NodeVariableDeclaration n);
    T visitArrayDeclaration(NodeArrayDeclaration n);
    T visitArrayInitializer(NodeArrayInitializer n);
    T visitAssignment(NodeAssignment n);
    T visitRead(NodeRead n);
    T visitPrint(NodePrint n);
    T visitIf(NodeIf n);
    T visitSwitch(NodeSwitch n);
    T visitCase(NodeCase n);
    T visitWhile(NodeWhile n);
    T visitDoWhile(NodeDoWhile n);
    T visitFor(NodeFor n);
    T visitBreak(NodeBreak n);
    T visitContinue(NodeContinue n);
    T visitReturn(NodeReturn n);
    T visitTernaryExpression(NodeTernaryExpression n);
    T visitBinaryExpression(NodeBinaryExpression n);
    T visitUnaryExpression(NodeUnaryExpression n);
    T visitIdentifier(NodeIdentifier n);
    T visitFieldAccess(NodeFieldAccess n);
    T visitIndexAccess(NodeIndexAccess n);
    T visitMethodCall(NodeMethodCall n);
    T visitNewObject(NodeNewObject n);
    T visitNewArray(NodeNewArray n);
    T visitIntegerLiteral(NodeIntegerLiteral n);
    T visitDecimalLiteral(NodeDecimalLiteral n);
    T visitCharLiteral(NodeCharLiteral n);
    T visitStringLiteral(NodeStringLiteral n);
    T visitBooleanLiteral(NodeBooleanLiteral n);
    T visitNullLiteral(NodeNullLiteral n);
    T visitThis(NodeThis n);
}
