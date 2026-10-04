// Generated from YParser.g4 by ANTLR 4.13.2
package com.piglatin.y.infrastructure.parser.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link YParser}.
 */
public interface YParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link YParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(YParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(YParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GlobalStructure}
	 * labeled alternative in {@link YParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGlobalStructure(YParser.GlobalStructureContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GlobalStructure}
	 * labeled alternative in {@link YParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGlobalStructure(YParser.GlobalStructureContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#structBlock}.
	 * @param ctx the parse tree
	 */
	void enterStructBlock(YParser.StructBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#structBlock}.
	 * @param ctx the parse tree
	 */
	void exitStructBlock(YParser.StructBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FieldSimpleOrArray}
	 * labeled alternative in {@link YParser#structField}.
	 * @param ctx the parse tree
	 */
	void enterFieldSimpleOrArray(YParser.FieldSimpleOrArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FieldSimpleOrArray}
	 * labeled alternative in {@link YParser#structField}.
	 * @param ctx the parse tree
	 */
	void exitFieldSimpleOrArray(YParser.FieldSimpleOrArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#mainInstructions}.
	 * @param ctx the parse tree
	 */
	void enterMainInstructions(YParser.MainInstructionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#mainInstructions}.
	 * @param ctx the parse tree
	 */
	void exitMainInstructions(YParser.MainInstructionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(YParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(YParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(YParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(YParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParamSimple}
	 * labeled alternative in {@link YParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParamSimple(YParser.ParamSimpleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParamSimple}
	 * labeled alternative in {@link YParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParamSimple(YParser.ParamSimpleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParamArray}
	 * labeled alternative in {@link YParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParamArray(YParser.ParamArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParamArray}
	 * labeled alternative in {@link YParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParamArray(YParser.ParamArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParamStruct}
	 * labeled alternative in {@link YParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParamStruct(YParser.ParamStructContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParamStruct}
	 * labeled alternative in {@link YParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParamStruct(YParser.ParamStructContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(YParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(YParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionAssignment}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionAssignment(YParser.InstructionAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionAssignment}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionAssignment(YParser.InstructionAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionPrint}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionPrint(YParser.InstructionPrintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionPrint}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionPrint(YParser.InstructionPrintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionIf}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionIf(YParser.InstructionIfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionIf}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionIf(YParser.InstructionIfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionChoose}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionChoose(YParser.InstructionChooseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionChoose}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionChoose(YParser.InstructionChooseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionWhile}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionWhile(YParser.InstructionWhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionWhile}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionWhile(YParser.InstructionWhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionDoWhile}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionDoWhile(YParser.InstructionDoWhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionDoWhile}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionDoWhile(YParser.InstructionDoWhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionFor}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionFor(YParser.InstructionForContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionFor}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionFor(YParser.InstructionForContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionJump}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionJump(YParser.InstructionJumpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionJump}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionJump(YParser.InstructionJumpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionDeclaration}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionDeclaration(YParser.InstructionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionDeclaration}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionDeclaration(YParser.InstructionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionArrayDeclaration}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionArrayDeclaration(YParser.InstructionArrayDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionArrayDeclaration}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionArrayDeclaration(YParser.InstructionArrayDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionExpression}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionExpression(YParser.InstructionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionExpression}
	 * labeled alternative in {@link YParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionExpression(YParser.InstructionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(YParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(YParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#arrayDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterArrayDeclaration(YParser.ArrayDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#arrayDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitArrayDeclaration(YParser.ArrayDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#arrayLiteral}.
	 * @param ctx the parse tree
	 */
	void enterArrayLiteral(YParser.ArrayLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#arrayLiteral}.
	 * @param ctx the parse tree
	 */
	void exitArrayLiteral(YParser.ArrayLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssigmentNormal}
	 * labeled alternative in {@link YParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssigmentNormal(YParser.AssigmentNormalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssigmentNormal}
	 * labeled alternative in {@link YParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssigmentNormal(YParser.AssigmentNormalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssigmentIncDec}
	 * labeled alternative in {@link YParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssigmentIncDec(YParser.AssigmentIncDecContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssigmentIncDec}
	 * labeled alternative in {@link YParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssigmentIncDec(YParser.AssigmentIncDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(YParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(YParser.PrintStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(YParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(YParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#chooseStatement}.
	 * @param ctx the parse tree
	 */
	void enterChooseStatement(YParser.ChooseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#chooseStatement}.
	 * @param ctx the parse tree
	 */
	void exitChooseStatement(YParser.ChooseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(YParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(YParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#doWhileStatement}.
	 * @param ctx the parse tree
	 */
	void enterDoWhileStatement(YParser.DoWhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#doWhileStatement}.
	 * @param ctx the parse tree
	 */
	void exitDoWhileStatement(YParser.DoWhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(YParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(YParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code JumpBreak}
	 * labeled alternative in {@link YParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpBreak(YParser.JumpBreakContext ctx);
	/**
	 * Exit a parse tree produced by the {@code JumpBreak}
	 * labeled alternative in {@link YParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpBreak(YParser.JumpBreakContext ctx);
	/**
	 * Enter a parse tree produced by the {@code JumpContinue}
	 * labeled alternative in {@link YParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpContinue(YParser.JumpContinueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code JumpContinue}
	 * labeled alternative in {@link YParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpContinue(YParser.JumpContinueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code JumpReturn}
	 * labeled alternative in {@link YParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpReturn(YParser.JumpReturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code JumpReturn}
	 * labeled alternative in {@link YParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpReturn(YParser.JumpReturnContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void enterLvalue(YParser.LvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void exitLvalue(YParser.LvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(YParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(YParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprPrimary}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprPrimary(YParser.ExprPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprPrimary}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprPrimary(YParser.ExprPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprLogical}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprLogical(YParser.ExprLogicalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprLogical}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprLogical(YParser.ExprLogicalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprMultiplicative}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprMultiplicative(YParser.ExprMultiplicativeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprMultiplicative}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprMultiplicative(YParser.ExprMultiplicativeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprUnary}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprUnary(YParser.ExprUnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprUnary}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprUnary(YParser.ExprUnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprArrayLiteral}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprArrayLiteral(YParser.ExprArrayLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprArrayLiteral}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprArrayLiteral(YParser.ExprArrayLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprRelational}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprRelational(YParser.ExprRelationalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprRelational}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprRelational(YParser.ExprRelationalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprAdditive}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprAdditive(YParser.ExprAdditiveContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprAdditive}
	 * labeled alternative in {@link YParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprAdditive(YParser.ExprAdditiveContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrimaryLiteral}
	 * labeled alternative in {@link YParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryLiteral(YParser.PrimaryLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrimaryLiteral}
	 * labeled alternative in {@link YParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryLiteral(YParser.PrimaryLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrimaryLvalue}
	 * labeled alternative in {@link YParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryLvalue(YParser.PrimaryLvalueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrimaryLvalue}
	 * labeled alternative in {@link YParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryLvalue(YParser.PrimaryLvalueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrimaryFunctionCall}
	 * labeled alternative in {@link YParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryFunctionCall(YParser.PrimaryFunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrimaryFunctionCall}
	 * labeled alternative in {@link YParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryFunctionCall(YParser.PrimaryFunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrimaryRead}
	 * labeled alternative in {@link YParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryRead(YParser.PrimaryReadContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrimaryRead}
	 * labeled alternative in {@link YParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryRead(YParser.PrimaryReadContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrimaryParen}
	 * labeled alternative in {@link YParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryParen(YParser.PrimaryParenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrimaryParen}
	 * labeled alternative in {@link YParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryParen(YParser.PrimaryParenContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(YParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(YParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link YParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(YParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link YParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(YParser.ArgumentListContext ctx);
}