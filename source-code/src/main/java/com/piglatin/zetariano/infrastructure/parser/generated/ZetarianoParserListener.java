// Generated from ZetarianoParser.g4 by ANTLR 4.13.2
package com.piglatin.zetariano.infrastructure.parser.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ZetarianoParser}.
 */
public interface ZetarianoParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(ZetarianoParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(ZetarianoParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#classDefinition}.
	 * @param ctx the parse tree
	 */
	void enterClassDefinition(ZetarianoParser.ClassDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#classDefinition}.
	 * @param ctx the parse tree
	 */
	void exitClassDefinition(ZetarianoParser.ClassDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#globalDeclarations}.
	 * @param ctx the parse tree
	 */
	void enterGlobalDeclarations(ZetarianoParser.GlobalDeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#globalDeclarations}.
	 * @param ctx the parse tree
	 */
	void exitGlobalDeclarations(ZetarianoParser.GlobalDeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GlobalField}
	 * labeled alternative in {@link ZetarianoParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGlobalField(ZetarianoParser.GlobalFieldContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GlobalField}
	 * labeled alternative in {@link ZetarianoParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGlobalField(ZetarianoParser.GlobalFieldContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GlobalMethod}
	 * labeled alternative in {@link ZetarianoParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGlobalMethod(ZetarianoParser.GlobalMethodContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GlobalMethod}
	 * labeled alternative in {@link ZetarianoParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGlobalMethod(ZetarianoParser.GlobalMethodContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GlobalConstructor}
	 * labeled alternative in {@link ZetarianoParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGlobalConstructor(ZetarianoParser.GlobalConstructorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GlobalConstructor}
	 * labeled alternative in {@link ZetarianoParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGlobalConstructor(ZetarianoParser.GlobalConstructorContext ctx);
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
	 * Enter a parse tree produced by {@link ZetarianoParser#mainInstructions}.
	 * @param ctx the parse tree
	 */
	void enterMainInstructions(ZetarianoParser.MainInstructionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#mainInstructions}.
	 * @param ctx the parse tree
	 */
	void exitMainInstructions(ZetarianoParser.MainInstructionsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionAssignment}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionAssignment(ZetarianoParser.InstructionAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionAssignment}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionAssignment(ZetarianoParser.InstructionAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionRead}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionRead(ZetarianoParser.InstructionReadContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionRead}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionRead(ZetarianoParser.InstructionReadContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionPrint}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionPrint(ZetarianoParser.InstructionPrintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionPrint}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionPrint(ZetarianoParser.InstructionPrintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionIf}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionIf(ZetarianoParser.InstructionIfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionIf}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionIf(ZetarianoParser.InstructionIfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionSwitch}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionSwitch(ZetarianoParser.InstructionSwitchContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionSwitch}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionSwitch(ZetarianoParser.InstructionSwitchContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionWhile}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionWhile(ZetarianoParser.InstructionWhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionWhile}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionWhile(ZetarianoParser.InstructionWhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionDoWhile}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionDoWhile(ZetarianoParser.InstructionDoWhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionDoWhile}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionDoWhile(ZetarianoParser.InstructionDoWhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionFor}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionFor(ZetarianoParser.InstructionForContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionFor}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionFor(ZetarianoParser.InstructionForContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionJump}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionJump(ZetarianoParser.InstructionJumpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionJump}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionJump(ZetarianoParser.InstructionJumpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionDeclaration}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionDeclaration(ZetarianoParser.InstructionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionDeclaration}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionDeclaration(ZetarianoParser.InstructionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionArrayDeclaration}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionArrayDeclaration(ZetarianoParser.InstructionArrayDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionArrayDeclaration}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionArrayDeclaration(ZetarianoParser.InstructionArrayDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionExpression}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionExpression(ZetarianoParser.InstructionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionExpression}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionExpression(ZetarianoParser.InstructionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(ZetarianoParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(ZetarianoParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#arrayDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterArrayDeclaration(ZetarianoParser.ArrayDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#arrayDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitArrayDeclaration(ZetarianoParser.ArrayDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitializer(ZetarianoParser.ArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitializer(ZetarianoParser.ArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(ZetarianoParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(ZetarianoParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#readStatement}.
	 * @param ctx the parse tree
	 */
	void enterReadStatement(ZetarianoParser.ReadStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#readStatement}.
	 * @param ctx the parse tree
	 */
	void exitReadStatement(ZetarianoParser.ReadStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(ZetarianoParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(ZetarianoParser.PrintStatementContext ctx);
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
	 * Enter a parse tree produced by the {@code JumpBreak}
	 * labeled alternative in {@link ZetarianoParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpBreak(ZetarianoParser.JumpBreakContext ctx);
	/**
	 * Exit a parse tree produced by the {@code JumpBreak}
	 * labeled alternative in {@link ZetarianoParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpBreak(ZetarianoParser.JumpBreakContext ctx);
	/**
	 * Enter a parse tree produced by the {@code JumpContinue}
	 * labeled alternative in {@link ZetarianoParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpContinue(ZetarianoParser.JumpContinueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code JumpContinue}
	 * labeled alternative in {@link ZetarianoParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpContinue(ZetarianoParser.JumpContinueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code JumpReturn}
	 * labeled alternative in {@link ZetarianoParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpReturn(ZetarianoParser.JumpReturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code JumpReturn}
	 * labeled alternative in {@link ZetarianoParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpReturn(ZetarianoParser.JumpReturnContext ctx);
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void enterLvalue(ZetarianoParser.LvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void exitLvalue(ZetarianoParser.LvalueContext ctx);
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
	 * Enter a parse tree produced by the {@code ExprPrimary}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprPrimary(ZetarianoParser.ExprPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprPrimary}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprPrimary(ZetarianoParser.ExprPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprAnd}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprAnd(ZetarianoParser.ExprAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprAnd}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprAnd(ZetarianoParser.ExprAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprTernary}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprTernary(ZetarianoParser.ExprTernaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprTernary}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprTernary(ZetarianoParser.ExprTernaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprParen}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprParen(ZetarianoParser.ExprParenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprParen}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprParen(ZetarianoParser.ExprParenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprOr}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprOr(ZetarianoParser.ExprOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprOr}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprOr(ZetarianoParser.ExprOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprMultiplicative}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprMultiplicative(ZetarianoParser.ExprMultiplicativeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprMultiplicative}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprMultiplicative(ZetarianoParser.ExprMultiplicativeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprUnary}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprUnary(ZetarianoParser.ExprUnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprUnary}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprUnary(ZetarianoParser.ExprUnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprRelational}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprRelational(ZetarianoParser.ExprRelationalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprRelational}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprRelational(ZetarianoParser.ExprRelationalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprEquality}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprEquality(ZetarianoParser.ExprEqualityContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprEquality}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprEquality(ZetarianoParser.ExprEqualityContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprAdditive}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprAdditive(ZetarianoParser.ExprAdditiveContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprAdditive}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprAdditive(ZetarianoParser.ExprAdditiveContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrimaryLiteral}
	 * labeled alternative in {@link ZetarianoParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryLiteral(ZetarianoParser.PrimaryLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrimaryLiteral}
	 * labeled alternative in {@link ZetarianoParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryLiteral(ZetarianoParser.PrimaryLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrimaryNewObject}
	 * labeled alternative in {@link ZetarianoParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryNewObject(ZetarianoParser.PrimaryNewObjectContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrimaryNewObject}
	 * labeled alternative in {@link ZetarianoParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryNewObject(ZetarianoParser.PrimaryNewObjectContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrimaryNewArray}
	 * labeled alternative in {@link ZetarianoParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryNewArray(ZetarianoParser.PrimaryNewArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrimaryNewArray}
	 * labeled alternative in {@link ZetarianoParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryNewArray(ZetarianoParser.PrimaryNewArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrimaryLvalueOrCall}
	 * labeled alternative in {@link ZetarianoParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryLvalueOrCall(ZetarianoParser.PrimaryLvalueOrCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrimaryLvalueOrCall}
	 * labeled alternative in {@link ZetarianoParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryLvalueOrCall(ZetarianoParser.PrimaryLvalueOrCallContext ctx);
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
	/**
	 * Enter a parse tree produced by {@link ZetarianoParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(ZetarianoParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ZetarianoParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(ZetarianoParser.ArgumentListContext ctx);
}