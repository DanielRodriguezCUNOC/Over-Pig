// Generated from ZetarianoParser.g4 by ANTLR 4.13.2
package com.piglatin.zetariano.infrastructure.parser.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ZetarianoParser}.
 */
public interface ZetarianoParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(ZetarianoParser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(ZetarianoParser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(ZetarianoParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(ZetarianoParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FieldDeclarationClassBodyMember}
	 * labeled alternative in {@link ZetarianoParser#classBodyMember}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclarationClassBodyMember(ZetarianoParser.FieldDeclarationClassBodyMemberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FieldDeclarationClassBodyMember}
	 * labeled alternative in {@link ZetarianoParser#classBodyMember}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclarationClassBodyMember(ZetarianoParser.FieldDeclarationClassBodyMemberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConstructorDeclarationClassBodyMember}
	 * labeled alternative in {@link ZetarianoParser#classBodyMember}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDeclarationClassBodyMember(ZetarianoParser.ConstructorDeclarationClassBodyMemberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConstructorDeclarationClassBodyMember}
	 * labeled alternative in {@link ZetarianoParser#classBodyMember}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDeclarationClassBodyMember(ZetarianoParser.ConstructorDeclarationClassBodyMemberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MethodDeclarationClassBodyMember}
	 * labeled alternative in {@link ZetarianoParser#classBodyMember}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclarationClassBodyMember(ZetarianoParser.MethodDeclarationClassBodyMemberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MethodDeclarationClassBodyMember}
	 * labeled alternative in {@link ZetarianoParser#classBodyMember}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclarationClassBodyMember(ZetarianoParser.MethodDeclarationClassBodyMemberContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclaration(ZetarianoParser.FieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclaration(ZetarianoParser.FieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDeclaration(ZetarianoParser.ConstructorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDeclaration(ZetarianoParser.ConstructorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(ZetarianoParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(ZetarianoParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(ZetarianoParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(ZetarianoParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(ZetarianoParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(ZetarianoParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#typeOrVoid}.
	 * @param ctx the parse tree
	 */
	void enterTypeOrVoid(ZetarianoParser.TypeOrVoidContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#typeOrVoid}.
	 * @param ctx the parse tree
	 */
	void exitTypeOrVoid(ZetarianoParser.TypeOrVoidContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(ZetarianoParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(ZetarianoParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(ZetarianoParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(ZetarianoParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ZetarianoParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ZetarianoParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StatementBlock}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementBlock(ZetarianoParser.StatementBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StatementBlock}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementBlock(ZetarianoParser.StatementBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LocalVariableDeclarationStatement}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableDeclarationStatement(ZetarianoParser.LocalVariableDeclarationStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LocalVariableDeclarationStatement}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableDeclarationStatement(ZetarianoParser.LocalVariableDeclarationStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StatementAssignment}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementAssignment(ZetarianoParser.StatementAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StatementAssignment}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementAssignment(ZetarianoParser.StatementAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StatementExpression}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementExpression(ZetarianoParser.StatementExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StatementExpression}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementExpression(ZetarianoParser.StatementExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StatementIf}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementIf(ZetarianoParser.StatementIfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StatementIf}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementIf(ZetarianoParser.StatementIfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StatementWhile}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementWhile(ZetarianoParser.StatementWhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StatementWhile}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementWhile(ZetarianoParser.StatementWhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StatementDoWhile}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementDoWhile(ZetarianoParser.StatementDoWhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StatementDoWhile}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementDoWhile(ZetarianoParser.StatementDoWhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StatementFor}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementFor(ZetarianoParser.StatementForContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StatementFor}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementFor(ZetarianoParser.StatementForContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StatementSwitch}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementSwitch(ZetarianoParser.StatementSwitchContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StatementSwitch}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementSwitch(ZetarianoParser.StatementSwitchContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StatementReturn}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementReturn(ZetarianoParser.StatementReturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StatementReturn}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementReturn(ZetarianoParser.StatementReturnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StatementBreak}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementBreak(ZetarianoParser.StatementBreakContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StatementBreak}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementBreak(ZetarianoParser.StatementBreakContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StatementContinue}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementContinue(ZetarianoParser.StatementContinueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StatementContinue}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementContinue(ZetarianoParser.StatementContinueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StatementEmpty}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementEmpty(ZetarianoParser.StatementEmptyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StatementEmpty}
	 * labeled alternative in {@link ZetarianoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementEmpty(ZetarianoParser.StatementEmptyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableDeclaration(ZetarianoParser.LocalVariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableDeclaration(ZetarianoParser.LocalVariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(ZetarianoParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(ZetarianoParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentOperatorAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperatorAssign(ZetarianoParser.AssignmentOperatorAssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentOperatorAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperatorAssign(ZetarianoParser.AssignmentOperatorAssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentOperatorAddAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperatorAddAssign(ZetarianoParser.AssignmentOperatorAddAssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentOperatorAddAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperatorAddAssign(ZetarianoParser.AssignmentOperatorAddAssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentOperatorSubAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperatorSubAssign(ZetarianoParser.AssignmentOperatorSubAssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentOperatorSubAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperatorSubAssign(ZetarianoParser.AssignmentOperatorSubAssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentOperatorMulAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperatorMulAssign(ZetarianoParser.AssignmentOperatorMulAssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentOperatorMulAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperatorMulAssign(ZetarianoParser.AssignmentOperatorMulAssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentOperatorDivAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperatorDivAssign(ZetarianoParser.AssignmentOperatorDivAssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentOperatorDivAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperatorDivAssign(ZetarianoParser.AssignmentOperatorDivAssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentOperatorModAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperatorModAssign(ZetarianoParser.AssignmentOperatorModAssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentOperatorModAssign}
	 * labeled alternative in {@link ZetarianoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperatorModAssign(ZetarianoParser.AssignmentOperatorModAssignContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#target}.
	 * @param ctx the parse tree
	 */
	void enterTarget(ZetarianoParser.TargetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#target}.
	 * @param ctx the parse tree
	 */
	void exitTarget(ZetarianoParser.TargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(ZetarianoParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(ZetarianoParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(ZetarianoParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(ZetarianoParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(ZetarianoParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(ZetarianoParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#doWhileStatement}.
	 * @param ctx the parse tree
	 */
	void enterDoWhileStatement(ZetarianoParser.DoWhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#doWhileStatement}.
	 * @param ctx the parse tree
	 */
	void exitDoWhileStatement(ZetarianoParser.DoWhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(ZetarianoParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(ZetarianoParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(ZetarianoParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(ZetarianoParser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void enterForUpdate(ZetarianoParser.ForUpdateContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void exitForUpdate(ZetarianoParser.ForUpdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void enterSwitchStatement(ZetarianoParser.SwitchStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void exitSwitchStatement(ZetarianoParser.SwitchStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void enterSwitchBlockStatementGroup(ZetarianoParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void exitSwitchBlockStatementGroup(ZetarianoParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void enterSwitchLabel(ZetarianoParser.SwitchLabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void exitSwitchLabel(ZetarianoParser.SwitchLabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(ZetarianoParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(ZetarianoParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(ZetarianoParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(ZetarianoParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(ZetarianoParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(ZetarianoParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ZetarianoParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ZetarianoParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#ternaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterTernaryExpression(ZetarianoParser.TernaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#ternaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitTernaryExpression(ZetarianoParser.TernaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpression(ZetarianoParser.LogicalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpression(ZetarianoParser.LogicalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpression(ZetarianoParser.LogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpression(ZetarianoParser.LogicalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(ZetarianoParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(ZetarianoParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(ZetarianoParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(ZetarianoParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(ZetarianoParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(ZetarianoParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(ZetarianoParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(ZetarianoParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryPlus}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryPlus(ZetarianoParser.UnaryPlusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryPlus}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryPlus(ZetarianoParser.UnaryPlusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryMinus}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinus(ZetarianoParser.UnaryMinusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryMinus}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinus(ZetarianoParser.UnaryMinusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryNot}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryNot(ZetarianoParser.UnaryNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryNot}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryNot(ZetarianoParser.UnaryNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryPreIncrement}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryPreIncrement(ZetarianoParser.UnaryPreIncrementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryPreIncrement}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryPreIncrement(ZetarianoParser.UnaryPreIncrementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryPreDecrement}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryPreDecrement(ZetarianoParser.UnaryPreDecrementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryPreDecrement}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryPreDecrement(ZetarianoParser.UnaryPreDecrementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryPostfix}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryPostfix(ZetarianoParser.UnaryPostfixContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryPostfix}
	 * labeled alternative in {@link ZetarianoParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryPostfix(ZetarianoParser.UnaryPostfixContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(ZetarianoParser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(ZetarianoParser.PostfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(ZetarianoParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(ZetarianoParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenthesizedExpression}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedExpression(ZetarianoParser.ParenthesizedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenthesizedExpression}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedExpression(ZetarianoParser.ParenthesizedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralPrimary}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 */
	void enterLiteralPrimary(ZetarianoParser.LiteralPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralPrimary}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 */
	void exitLiteralPrimary(ZetarianoParser.LiteralPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AllocationExpressionPrimary}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 */
	void enterAllocationExpressionPrimary(ZetarianoParser.AllocationExpressionPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AllocationExpressionPrimary}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 */
	void exitAllocationExpressionPrimary(ZetarianoParser.AllocationExpressionPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DirectMethodCall}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 */
	void enterDirectMethodCall(ZetarianoParser.DirectMethodCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DirectMethodCall}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 */
	void exitDirectMethodCall(ZetarianoParser.DirectMethodCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VariableOrField}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 */
	void enterVariableOrField(ZetarianoParser.VariableOrFieldContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VariableOrField}
	 * labeled alternative in {@link ZetarianoParser#primaryCore}.
	 * @param ctx the parse tree
	 */
	void exitVariableOrField(ZetarianoParser.VariableOrFieldContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MethodCallSuffix}
	 * labeled alternative in {@link ZetarianoParser#primarySuffix}.
	 * @param ctx the parse tree
	 */
	void enterMethodCallSuffix(ZetarianoParser.MethodCallSuffixContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MethodCallSuffix}
	 * labeled alternative in {@link ZetarianoParser#primarySuffix}.
	 * @param ctx the parse tree
	 */
	void exitMethodCallSuffix(ZetarianoParser.MethodCallSuffixContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FieldAccessSuffix}
	 * labeled alternative in {@link ZetarianoParser#primarySuffix}.
	 * @param ctx the parse tree
	 */
	void enterFieldAccessSuffix(ZetarianoParser.FieldAccessSuffixContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FieldAccessSuffix}
	 * labeled alternative in {@link ZetarianoParser#primarySuffix}.
	 * @param ctx the parse tree
	 */
	void exitFieldAccessSuffix(ZetarianoParser.FieldAccessSuffixContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayAccessSuffix}
	 * labeled alternative in {@link ZetarianoParser#primarySuffix}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccessSuffix(ZetarianoParser.ArrayAccessSuffixContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayAccessSuffix}
	 * labeled alternative in {@link ZetarianoParser#primarySuffix}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccessSuffix(ZetarianoParser.ArrayAccessSuffixContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AllocationExpressionArray}
	 * labeled alternative in {@link ZetarianoParser#allocationExpression}.
	 * @param ctx the parse tree
	 */
	void enterAllocationExpressionArray(ZetarianoParser.AllocationExpressionArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AllocationExpressionArray}
	 * labeled alternative in {@link ZetarianoParser#allocationExpression}.
	 * @param ctx the parse tree
	 */
	void exitAllocationExpressionArray(ZetarianoParser.AllocationExpressionArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AllocationExpressionObject}
	 * labeled alternative in {@link ZetarianoParser#allocationExpression}.
	 * @param ctx the parse tree
	 */
	void enterAllocationExpressionObject(ZetarianoParser.AllocationExpressionObjectContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AllocationExpressionObject}
	 * labeled alternative in {@link ZetarianoParser#allocationExpression}.
	 * @param ctx the parse tree
	 */
	void exitAllocationExpressionObject(ZetarianoParser.AllocationExpressionObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#arrayDimensions}.
	 * @param ctx the parse tree
	 */
	void enterArrayDimensions(ZetarianoParser.ArrayDimensionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#arrayDimensions}.
	 * @param ctx the parse tree
	 */
	void exitArrayDimensions(ZetarianoParser.ArrayDimensionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(ZetarianoParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(ZetarianoParser.MethodCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayAccessing}
	 * labeled alternative in {@link ZetarianoParser#fieldAccess}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccessing(ZetarianoParser.ArrayAccessingContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayAccessing}
	 * labeled alternative in {@link ZetarianoParser#fieldAccess}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccessing(ZetarianoParser.ArrayAccessingContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FieldAccessing}
	 * labeled alternative in {@link ZetarianoParser#fieldAccess}.
	 * @param ctx the parse tree
	 */
	void enterFieldAccessing(ZetarianoParser.FieldAccessingContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FieldAccessing}
	 * labeled alternative in {@link ZetarianoParser#fieldAccess}.
	 * @param ctx the parse tree
	 */
	void exitFieldAccessing(ZetarianoParser.FieldAccessingContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(ZetarianoParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(ZetarianoParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(ZetarianoParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(ZetarianoParser.LiteralContext ctx);
}