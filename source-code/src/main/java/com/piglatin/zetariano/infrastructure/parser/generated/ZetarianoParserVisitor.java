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
	 * Visit a parse tree produced by {@link ZetarianoParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(ZetarianoParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#classDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDefinition(ZetarianoParser.ClassDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#globalDeclarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalDeclarations(ZetarianoParser.GlobalDeclarationsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GlobalField}
	 * labeled alternative in {@link ZetarianoParser#globalDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalField(ZetarianoParser.GlobalFieldContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GlobalMethod}
	 * labeled alternative in {@link ZetarianoParser#globalDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalMethod(ZetarianoParser.GlobalMethodContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GlobalConstructor}
	 * labeled alternative in {@link ZetarianoParser#globalDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalConstructor(ZetarianoParser.GlobalConstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(ZetarianoParser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(ZetarianoParser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclaration(ZetarianoParser.ConstructorDeclarationContext ctx);
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
	 * Visit a parse tree produced by {@link ZetarianoParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ZetarianoParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#mainInstructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainInstructions(ZetarianoParser.MainInstructionsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionAssignment}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionAssignment(ZetarianoParser.InstructionAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionRead}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionRead(ZetarianoParser.InstructionReadContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionPrint}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionPrint(ZetarianoParser.InstructionPrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionIf}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionIf(ZetarianoParser.InstructionIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionSwitch}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionSwitch(ZetarianoParser.InstructionSwitchContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionWhile}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionWhile(ZetarianoParser.InstructionWhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionDoWhile}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionDoWhile(ZetarianoParser.InstructionDoWhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionFor}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionFor(ZetarianoParser.InstructionForContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionJump}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionJump(ZetarianoParser.InstructionJumpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionDeclaration}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionDeclaration(ZetarianoParser.InstructionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionArrayDeclaration}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionArrayDeclaration(ZetarianoParser.InstructionArrayDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionExpression}
	 * labeled alternative in {@link ZetarianoParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionExpression(ZetarianoParser.InstructionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(ZetarianoParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#arrayDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayDeclaration(ZetarianoParser.ArrayDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#arrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitializer(ZetarianoParser.ArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(ZetarianoParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#readStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadStatement(ZetarianoParser.ReadStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#printStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStatement(ZetarianoParser.PrintStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(ZetarianoParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#switchStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchStatement(ZetarianoParser.SwitchStatementContext ctx);
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
	 * Visit a parse tree produced by the {@code JumpBreak}
	 * labeled alternative in {@link ZetarianoParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpBreak(ZetarianoParser.JumpBreakContext ctx);
	/**
	 * Visit a parse tree produced by the {@code JumpContinue}
	 * labeled alternative in {@link ZetarianoParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpContinue(ZetarianoParser.JumpContinueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code JumpReturn}
	 * labeled alternative in {@link ZetarianoParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpReturn(ZetarianoParser.JumpReturnContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#lvalue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLvalue(ZetarianoParser.LvalueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(ZetarianoParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprPrimary}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprPrimary(ZetarianoParser.ExprPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprAnd}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprAnd(ZetarianoParser.ExprAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprTernary}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprTernary(ZetarianoParser.ExprTernaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprParen}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprParen(ZetarianoParser.ExprParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprOr}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprOr(ZetarianoParser.ExprOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprMultiplicative}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprMultiplicative(ZetarianoParser.ExprMultiplicativeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprUnary}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprUnary(ZetarianoParser.ExprUnaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprRelational}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprRelational(ZetarianoParser.ExprRelationalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprEquality}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprEquality(ZetarianoParser.ExprEqualityContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprAdditive}
	 * labeled alternative in {@link ZetarianoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprAdditive(ZetarianoParser.ExprAdditiveContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimaryLiteral}
	 * labeled alternative in {@link ZetarianoParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryLiteral(ZetarianoParser.PrimaryLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimaryNewObject}
	 * labeled alternative in {@link ZetarianoParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNewObject(ZetarianoParser.PrimaryNewObjectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimaryNewArray}
	 * labeled alternative in {@link ZetarianoParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNewArray(ZetarianoParser.PrimaryNewArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimaryLvalueOrCall}
	 * labeled alternative in {@link ZetarianoParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryLvalueOrCall(ZetarianoParser.PrimaryLvalueOrCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ZetarianoParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ZetarianoParser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(ZetarianoParser.ArgumentListContext ctx);
}