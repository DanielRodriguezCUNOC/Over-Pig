// Generated from ZetarianoParser.g4 by ANTLR 4.13.2
package com.piglatin.zetariano.infrastructure.parser.generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ZetarianoParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ZetarianoParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#compilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompilationUnit(ZetarianoParser.CompilationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(ZetarianoParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FieldDeclarationClassBodyMember}
	 * labeled alternative in {@link ZetarianoParser#classBodyMember}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclarationClassBodyMember(ZetarianoParser.FieldDeclarationClassBodyMemberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConstructorDeclarationClassBodyMember}
	 * labeled alternative in {@link ZetarianoParser#classBodyMember}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclarationClassBodyMember(ZetarianoParser.ConstructorDeclarationClassBodyMemberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MethodDeclarationClassBodyMember}
	 * labeled alternative in {@link ZetarianoParser#classBodyMember}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclarationClassBodyMember(ZetarianoParser.MethodDeclarationClassBodyMemberContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(ZetarianoParser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclaration(ZetarianoParser.ConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(ZetarianoParser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(ZetarianoParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(ZetarianoParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#typeOrVoid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeOrVoid(ZetarianoParser.TypeOrVoidContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(ZetarianoParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(ZetarianoParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ZetarianoParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StatementBlock}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementBlock(ZetarianoParser.StatementBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LocalVariableDeclarationStatement}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableDeclarationStatement(ZetarianoParser.LocalVariableDeclarationStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StatementAssignment}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementAssignment(ZetarianoParser.StatementAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StatementExpression}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementExpression(ZetarianoParser.StatementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StatementIf}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementIf(ZetarianoParser.StatementIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StatementWhile}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementWhile(ZetarianoParser.StatementWhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StatementDoWhile}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementDoWhile(ZetarianoParser.StatementDoWhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StatementFor}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementFor(ZetarianoParser.StatementForContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StatementSwitch}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementSwitch(ZetarianoParser.StatementSwitchContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StatementReturn}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementReturn(ZetarianoParser.StatementReturnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StatementBreak}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementBreak(ZetarianoParser.StatementBreakContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StatementContinue}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementContinue(ZetarianoParser.StatementContinueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StatementEmpty}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementEmpty(ZetarianoParser.StatementEmptyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableDeclaration(ZetarianoParser.LocalVariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#assignmentStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStatement(ZetarianoParser.AssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignmentOperatorAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperatorAssign(ZetarianoParser.AssignmentOperatorAssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignmentOperatorAddAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperatorAddAssign(ZetarianoParser.AssignmentOperatorAddAssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignmentOperatorSubAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperatorSubAssign(ZetarianoParser.AssignmentOperatorSubAssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignmentOperatorMulAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperatorMulAssign(ZetarianoParser.AssignmentOperatorMulAssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignmentOperatorDivAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperatorDivAssign(ZetarianoParser.AssignmentOperatorDivAssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignmentOperatorModAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperatorModAssign(ZetarianoParser.AssignmentOperatorModAssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#target}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTarget(ZetarianoParser.TargetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(ZetarianoParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(ZetarianoParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(ZetarianoParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#doWhileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileStatement(ZetarianoParser.DoWhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(ZetarianoParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(ZetarianoParser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#forUpdate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForUpdate(ZetarianoParser.ForUpdateContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#switchStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchStatement(ZetarianoParser.SwitchStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchBlockStatementGroup(ZetarianoParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#switchLabel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchLabel(ZetarianoParser.SwitchLabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(ZetarianoParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(ZetarianoParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(ZetarianoParser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ZetarianoParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#ternaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernaryExpression(ZetarianoParser.TernaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOrExpression(ZetarianoParser.LogicalOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAndExpression(ZetarianoParser.LogicalAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(ZetarianoParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(ZetarianoParser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(ZetarianoParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(ZetarianoParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryPlus}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryPlus(ZetarianoParser.UnaryPlusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryMinus}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMinus(ZetarianoParser.UnaryMinusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryNot}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryNot(ZetarianoParser.UnaryNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryPreIncrement}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryPreIncrement(ZetarianoParser.UnaryPreIncrementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryPreDecrement}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryPreDecrement(ZetarianoParser.UnaryPreDecrementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryPostfix}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryPostfix(ZetarianoParser.UnaryPostfixContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#postfixExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpression(ZetarianoParser.PostfixExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(ZetarianoParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenthesizedExpression}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedExpression(ZetarianoParser.ParenthesizedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralPrimary}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralPrimary(ZetarianoParser.LiteralPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AllocationExpressionPrimary}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllocationExpressionPrimary(ZetarianoParser.AllocationExpressionPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DirectMethodCall}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDirectMethodCall(ZetarianoParser.DirectMethodCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableOrField}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableOrField(ZetarianoParser.VariableOrFieldContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ThisPrimary}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThisPrimary(ZetarianoParser.ThisPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MethodCallSuffix}
	 * labeled alternative in {@link ZetarianoParser#primarySuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCallSuffix(ZetarianoParser.MethodCallSuffixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FieldAccessSuffix}
	 * labeled alternative in {@link ZetarianoParser#primarySuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldAccessSuffix(ZetarianoParser.FieldAccessSuffixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayAccessSuffix}
	 * labeled alternative in {@link ZetarianoParser#primarySuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccessSuffix(ZetarianoParser.ArrayAccessSuffixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AllocationExpressionArray}
	 * labeled alternative in {@link ZetarianoParser#allocationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllocationExpressionArray(ZetarianoParser.AllocationExpressionArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AllocationExpressionObject}
	 * labeled alternative in {@link ZetarianoParser#allocationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllocationExpressionObject(ZetarianoParser.AllocationExpressionObjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#arrayDimensions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayDimensions(ZetarianoParser.ArrayDimensionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(ZetarianoParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ZetarianoParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#accessModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessModifier(ZetarianoParser.AccessModifierContext ctx);
}