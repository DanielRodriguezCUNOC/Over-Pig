// Generated from YParser.g4 by ANTLR 4.13.2
package com.piglatin.y.infrastructure.parser.generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link YParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface YParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link YParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(YParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GlobalStructure}
	 * labeled alternative in {@link YParser#globalDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalStructure(YParser.GlobalStructureContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#structBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructBlock(YParser.StructBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FieldSimpleOrArray}
	 * labeled alternative in {@link YParser#structField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldSimpleOrArray(YParser.FieldSimpleOrArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#mainInstructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainInstructions(YParser.MainInstructionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(YParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(YParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParamSimple}
	 * labeled alternative in {@link YParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamSimple(YParser.ParamSimpleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParamArray}
	 * labeled alternative in {@link YParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamArray(YParser.ParamArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParamStruct}
	 * labeled alternative in {@link YParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamStruct(YParser.ParamStructContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(YParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionAssignment}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionAssignment(YParser.InstructionAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionPrint}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionPrint(YParser.InstructionPrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionIf}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionIf(YParser.InstructionIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionChoose}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionChoose(YParser.InstructionChooseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionWhile}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionWhile(YParser.InstructionWhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionDoWhile}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionDoWhile(YParser.InstructionDoWhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionFor}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionFor(YParser.InstructionForContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionJump}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionJump(YParser.InstructionJumpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionDeclaration}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionDeclaration(YParser.InstructionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionArrayDeclaration}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionArrayDeclaration(YParser.InstructionArrayDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionExpression}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionExpression(YParser.InstructionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(YParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#arrayDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayDeclaration(YParser.ArrayDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#arrayLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayLiteral(YParser.ArrayLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssigmentNormal}
	 * labeled alternative in {@link YParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssigmentNormal(YParser.AssigmentNormalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssigmentIncDec}
	 * labeled alternative in {@link YParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssigmentIncDec(YParser.AssigmentIncDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#printStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStatement(YParser.PrintStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(YParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#chooseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChooseStatement(YParser.ChooseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(YParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#doWhileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileStatement(YParser.DoWhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(YParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code JumpBreak}
	 * labeled alternative in {@link YParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpBreak(YParser.JumpBreakContext ctx);
	/**
	 * Visit a parse tree produced by the {@code JumpContinue}
	 * labeled alternative in {@link YParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpContinue(YParser.JumpContinueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code JumpReturn}
	 * labeled alternative in {@link YParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpReturn(YParser.JumpReturnContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#lvalue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLvalue(YParser.LvalueContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(YParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprPrimary}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprPrimary(YParser.ExprPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprLogical}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprLogical(YParser.ExprLogicalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprMultiplicative}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprMultiplicative(YParser.ExprMultiplicativeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprUnary}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprUnary(YParser.ExprUnaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprArrayLiteral}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprArrayLiteral(YParser.ExprArrayLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprRelational}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprRelational(YParser.ExprRelationalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprAdditive}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprAdditive(YParser.ExprAdditiveContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimaryLiteral}
	 * labeled alternative in {@link YParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryLiteral(YParser.PrimaryLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimaryLvalue}
	 * labeled alternative in {@link YParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryLvalue(YParser.PrimaryLvalueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimaryFunctionCall}
	 * labeled alternative in {@link YParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryFunctionCall(YParser.PrimaryFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimaryRead}
	 * labeled alternative in {@link YParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryRead(YParser.PrimaryReadContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimaryParen}
	 * labeled alternative in {@link YParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryParen(YParser.PrimaryParenContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(YParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link YParser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(YParser.ArgumentListContext ctx);
}