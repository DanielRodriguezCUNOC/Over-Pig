package com.piglatin.y.domain.ast.visitor;

import com.piglatin.y.domain.ast.principal.NodeProgram;
import com.piglatin.y.domain.ast.expressions.*;
import com.piglatin.y.domain.ast.statements.*;

public interface Visitor<T> {
    T visitProgram(NodeProgram n);
    
    // Expressions
    T visitBinaryOperation(NodeBinaryOperation n);
    T visitUnaryOperation(NodeUnaryOperation n);
    T visitIdentifier(NodeIdentifier n);
    T visitIntegerLiteral(NodeIntegerLiteral n);
    T visitFloatLiteral(NodeFloatLiteral n);
    T visitCharLiteral(NodeCharLiteral n);
    T visitStringLiteral(NodeStringLiteral n);
    T visitBooleanLiteral(NodeBooleanLiteral n);
    T visitArrayLiteral(NodeArrayLiteral n);
    T visitLvalue(NodeLvalue n);
    T visitFieldAccess(NodeFieldAccess n);
    T visitIndexAccess(NodeIndexAccess n);
    T visitFunctionCall(NodeFunctionCall n);
    
    // Statements
    T visitStructureDefinition(NodeStructureDefinition n);
    T visitFieldDeclaration(NodeFieldDeclaration n);
    T visitFunctionDefinition(NodeFunctionDefinition n);
    T visitParameter(NodeParameter n);
    T visitBlock(NodeBlock n);
    T visitAssignment(NodeAssignment n);
    T visitRead(NodeRead n);
    T visitPrint(NodePrint n);
    T visitIf(NodeIf n);
    T visitChoose(NodeChoose n);
    T visitChooseCase(NodeChooseCase n);
    T visitWhile(NodeWhile n);
    T visitDoWhile(NodeDoWhile n);
    T visitFor(NodeFor n);
    T visitBreak(NodeBreak n);
    T visitContinue(NodeContinue n);
    T visitReturn(NodeReturn n);
    T visitVariableDeclaration(NodeVariableDeclaration n);
    T visitArrayDeclaration(NodeArrayDeclaration n);
}
