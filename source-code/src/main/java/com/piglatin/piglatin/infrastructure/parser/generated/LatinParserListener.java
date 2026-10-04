// Generated from LatinParser.g4 by ANTLR 4.13.2
package com.piglatin.piglatin.infrastructure.parser.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LatinParser}.
 */
public interface LatinParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LatinParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(LatinParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(LatinParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#importList}.
	 * @param ctx the parse tree
	 */
	void enterImportList(LatinParser.ImportListContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#importList}.
	 * @param ctx the parse tree
	 */
	void exitImportList(LatinParser.ImportListContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void enterImportStatement(LatinParser.ImportStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void exitImportStatement(LatinParser.ImportStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#globalDeclarations}.
	 * @param ctx the parse tree
	 */
	void enterGlobalDeclarations(LatinParser.GlobalDeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#globalDeclarations}.
	 * @param ctx the parse tree
	 */
	void exitGlobalDeclarations(LatinParser.GlobalDeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GlobalDeclarationVariable}
	 * labeled alternative in {@link LatinParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGlobalDeclarationVariable(LatinParser.GlobalDeclarationVariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GlobalDeclarationVariable}
	 * labeled alternative in {@link LatinParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGlobalDeclarationVariable(LatinParser.GlobalDeclarationVariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GlobalDeclarationArray}
	 * labeled alternative in {@link LatinParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGlobalDeclarationArray(LatinParser.GlobalDeclarationArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GlobalDeclarationArray}
	 * labeled alternative in {@link LatinParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGlobalDeclarationArray(LatinParser.GlobalDeclarationArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#mainInstructions}.
	 * @param ctx the parse tree
	 */
	void enterMainInstructions(LatinParser.MainInstructionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#mainInstructions}.
	 * @param ctx the parse tree
	 */
	void exitMainInstructions(LatinParser.MainInstructionsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionAssignment}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionAssignment(LatinParser.InstructionAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionAssignment}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionAssignment(LatinParser.InstructionAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionRead}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionRead(LatinParser.InstructionReadContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionRead}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionRead(LatinParser.InstructionReadContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionPrint}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionPrint(LatinParser.InstructionPrintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionPrint}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionPrint(LatinParser.InstructionPrintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionIf}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionIf(LatinParser.InstructionIfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionIf}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionIf(LatinParser.InstructionIfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionWhile}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionWhile(LatinParser.InstructionWhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionWhile}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionWhile(LatinParser.InstructionWhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionDoWhile}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionDoWhile(LatinParser.InstructionDoWhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionDoWhile}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionDoWhile(LatinParser.InstructionDoWhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionFor}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionFor(LatinParser.InstructionForContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionFor}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionFor(LatinParser.InstructionForContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionJump}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionJump(LatinParser.InstructionJumpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionJump}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionJump(LatinParser.InstructionJumpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionDeclaration}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionDeclaration(LatinParser.InstructionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionDeclaration}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionDeclaration(LatinParser.InstructionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionArrayDeclaration}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionArrayDeclaration(LatinParser.InstructionArrayDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionArrayDeclaration}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionArrayDeclaration(LatinParser.InstructionArrayDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InstructionExpression}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstructionExpression(LatinParser.InstructionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InstructionExpression}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstructionExpression(LatinParser.InstructionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(LatinParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(LatinParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#arrayDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterArrayDeclaration(LatinParser.ArrayDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#arrayDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitArrayDeclaration(LatinParser.ArrayDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#arrayLiteral}.
	 * @param ctx the parse tree
	 */
	void enterArrayLiteral(LatinParser.ArrayLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#arrayLiteral}.
	 * @param ctx the parse tree
	 */
	void exitArrayLiteral(LatinParser.ArrayLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(LatinParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(LatinParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(LatinParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(LatinParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#elseIfClause}.
	 * @param ctx the parse tree
	 */
	void enterElseIfClause(LatinParser.ElseIfClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#elseIfClause}.
	 * @param ctx the parse tree
	 */
	void exitElseIfClause(LatinParser.ElseIfClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void enterElseClause(LatinParser.ElseClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void exitElseClause(LatinParser.ElseClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(LatinParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(LatinParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#doWhileStatement}.
	 * @param ctx the parse tree
	 */
	void enterDoWhileStatement(LatinParser.DoWhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#doWhileStatement}.
	 * @param ctx the parse tree
	 */
	void exitDoWhileStatement(LatinParser.DoWhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(LatinParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(LatinParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForInitDeclaration}
	 * labeled alternative in {@link LatinParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInitDeclaration(LatinParser.ForInitDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForInitDeclaration}
	 * labeled alternative in {@link LatinParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInitDeclaration(LatinParser.ForInitDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForInitAssignment}
	 * labeled alternative in {@link LatinParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInitAssignment(LatinParser.ForInitAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForInitAssignment}
	 * labeled alternative in {@link LatinParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInitAssignment(LatinParser.ForInitAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForInitEmpty}
	 * labeled alternative in {@link LatinParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInitEmpty(LatinParser.ForInitEmptyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForInitEmpty}
	 * labeled alternative in {@link LatinParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInitEmpty(LatinParser.ForInitEmptyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForUpdateAssignment}
	 * labeled alternative in {@link LatinParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void enterForUpdateAssignment(LatinParser.ForUpdateAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForUpdateAssignment}
	 * labeled alternative in {@link LatinParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void exitForUpdateAssignment(LatinParser.ForUpdateAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForUpdateExpression}
	 * labeled alternative in {@link LatinParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void enterForUpdateExpression(LatinParser.ForUpdateExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForUpdateExpression}
	 * labeled alternative in {@link LatinParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void exitForUpdateExpression(LatinParser.ForUpdateExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code JumpContinue}
	 * labeled alternative in {@link LatinParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpContinue(LatinParser.JumpContinueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code JumpContinue}
	 * labeled alternative in {@link LatinParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpContinue(LatinParser.JumpContinueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code JumpBreak}
	 * labeled alternative in {@link LatinParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpBreak(LatinParser.JumpBreakContext ctx);
	/**
	 * Exit a parse tree produced by the {@code JumpBreak}
	 * labeled alternative in {@link LatinParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpBreak(LatinParser.JumpBreakContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#newInstance}.
	 * @param ctx the parse tree
	 */
	void enterNewInstance(LatinParser.NewInstanceContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#newInstance}.
	 * @param ctx the parse tree
	 */
	void exitNewInstance(LatinParser.NewInstanceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReadSimple}
	 * labeled alternative in {@link LatinParser#readStatement}.
	 * @param ctx the parse tree
	 */
	void enterReadSimple(LatinParser.ReadSimpleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReadSimple}
	 * labeled alternative in {@link LatinParser#readStatement}.
	 * @param ctx the parse tree
	 */
	void exitReadSimple(LatinParser.ReadSimpleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReadLvalue}
	 * labeled alternative in {@link LatinParser#readStatement}.
	 * @param ctx the parse tree
	 */
	void enterReadLvalue(LatinParser.ReadLvalueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReadLvalue}
	 * labeled alternative in {@link LatinParser#readStatement}.
	 * @param ctx the parse tree
	 */
	void exitReadLvalue(LatinParser.ReadLvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(LatinParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(LatinParser.PrintStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrintString}
	 * labeled alternative in {@link LatinParser#printItem}.
	 * @param ctx the parse tree
	 */
	void enterPrintString(LatinParser.PrintStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrintString}
	 * labeled alternative in {@link LatinParser#printItem}.
	 * @param ctx the parse tree
	 */
	void exitPrintString(LatinParser.PrintStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrintExpression}
	 * labeled alternative in {@link LatinParser#printItem}.
	 * @param ctx the parse tree
	 */
	void enterPrintExpression(LatinParser.PrintExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrintExpression}
	 * labeled alternative in {@link LatinParser#printItem}.
	 * @param ctx the parse tree
	 */
	void exitPrintExpression(LatinParser.PrintExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void enterLvalue(LatinParser.LvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void exitLvalue(LatinParser.LvalueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FieldAccess}
	 * labeled alternative in {@link LatinParser#lvalueSuffix}.
	 * @param ctx the parse tree
	 */
	void enterFieldAccess(LatinParser.FieldAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FieldAccess}
	 * labeled alternative in {@link LatinParser#lvalueSuffix}.
	 * @param ctx the parse tree
	 */
	void exitFieldAccess(LatinParser.FieldAccessContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IndexAccess}
	 * labeled alternative in {@link LatinParser#lvalueSuffix}.
	 * @param ctx the parse tree
	 */
	void enterIndexAccess(LatinParser.IndexAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IndexAccess}
	 * labeled alternative in {@link LatinParser#lvalueSuffix}.
	 * @param ctx the parse tree
	 */
	void exitIndexAccess(LatinParser.IndexAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(LatinParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(LatinParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TypeNumerus}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 */
	void enterTypeNumerus(LatinParser.TypeNumerusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TypeNumerus}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 */
	void exitTypeNumerus(LatinParser.TypeNumerusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TypeTextum}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 */
	void enterTypeTextum(LatinParser.TypeTextumContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TypeTextum}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 */
	void exitTypeTextum(LatinParser.TypeTextumContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TypeDecimalis}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 */
	void enterTypeDecimalis(LatinParser.TypeDecimalisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TypeDecimalis}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 */
	void exitTypeDecimalis(LatinParser.TypeDecimalisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TypeLittera}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 */
	void enterTypeLittera(LatinParser.TypeLitteraContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TypeLittera}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 */
	void exitTypeLittera(LatinParser.TypeLitteraContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TypeBool}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 */
	void enterTypeBool(LatinParser.TypeBoolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TypeBool}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 */
	void exitTypeBool(LatinParser.TypeBoolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TypeID}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 */
	void enterTypeID(LatinParser.TypeIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TypeID}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 */
	void exitTypeID(LatinParser.TypeIDContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(LatinParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(LatinParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprBoolean}
	 * labeled alternative in {@link LatinParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprBoolean(LatinParser.ExprBooleanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprBoolean}
	 * labeled alternative in {@link LatinParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprBoolean(LatinParser.ExprBooleanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprNumeric}
	 * labeled alternative in {@link LatinParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprNumeric(LatinParser.ExprNumericContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprNumeric}
	 * labeled alternative in {@link LatinParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprNumeric(LatinParser.ExprNumericContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprString}
	 * labeled alternative in {@link LatinParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprString(LatinParser.ExprStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprString}
	 * labeled alternative in {@link LatinParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprString(LatinParser.ExprStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprArrayLiteral}
	 * labeled alternative in {@link LatinParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprArrayLiteral(LatinParser.ExprArrayLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprArrayLiteral}
	 * labeled alternative in {@link LatinParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprArrayLiteral(LatinParser.ExprArrayLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprNewInstance}
	 * labeled alternative in {@link LatinParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprNewInstance(LatinParser.ExprNewInstanceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprNewInstance}
	 * labeled alternative in {@link LatinParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprNewInstance(LatinParser.ExprNewInstanceContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExpression(LatinParser.BooleanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExpression(LatinParser.BooleanExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#booleanOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterBooleanOrExpression(LatinParser.BooleanOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#booleanOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitBooleanOrExpression(LatinParser.BooleanOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#booleanAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterBooleanAndExpression(LatinParser.BooleanAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#booleanAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitBooleanAndExpression(LatinParser.BooleanAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompNot}
	 * labeled alternative in {@link LatinParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void enterCompNot(LatinParser.CompNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompNot}
	 * labeled alternative in {@link LatinParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void exitCompNot(LatinParser.CompNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompCompare}
	 * labeled alternative in {@link LatinParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void enterCompCompare(LatinParser.CompCompareContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompCompare}
	 * labeled alternative in {@link LatinParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void exitCompCompare(LatinParser.CompCompareContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompParen}
	 * labeled alternative in {@link LatinParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void enterCompParen(LatinParser.CompParenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompParen}
	 * labeled alternative in {@link LatinParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void exitCompParen(LatinParser.CompParenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompNumeric}
	 * labeled alternative in {@link LatinParser#comparisonOperand}.
	 * @param ctx the parse tree
	 */
	void enterCompNumeric(LatinParser.CompNumericContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompNumeric}
	 * labeled alternative in {@link LatinParser#comparisonOperand}.
	 * @param ctx the parse tree
	 */
	void exitCompNumeric(LatinParser.CompNumericContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompBoolean}
	 * labeled alternative in {@link LatinParser#comparisonOperand}.
	 * @param ctx the parse tree
	 */
	void enterCompBoolean(LatinParser.CompBooleanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompBoolean}
	 * labeled alternative in {@link LatinParser#comparisonOperand}.
	 * @param ctx the parse tree
	 */
	void exitCompBoolean(LatinParser.CompBooleanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompString}
	 * labeled alternative in {@link LatinParser#comparisonOperand}.
	 * @param ctx the parse tree
	 */
	void enterCompString(LatinParser.CompStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompString}
	 * labeled alternative in {@link LatinParser#comparisonOperand}.
	 * @param ctx the parse tree
	 */
	void exitCompString(LatinParser.CompStringContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#relationalOp}.
	 * @param ctx the parse tree
	 */
	void enterRelationalOp(LatinParser.RelationalOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#relationalOp}.
	 * @param ctx the parse tree
	 */
	void exitRelationalOp(LatinParser.RelationalOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(LatinParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(LatinParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#numericExpression}.
	 * @param ctx the parse tree
	 */
	void enterNumericExpression(LatinParser.NumericExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#numericExpression}.
	 * @param ctx the parse tree
	 */
	void exitNumericExpression(LatinParser.NumericExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(LatinParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(LatinParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(LatinParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(LatinParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryPrefix}
	 * labeled alternative in {@link LatinParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryPrefix(LatinParser.UnaryPrefixContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryPrefix}
	 * labeled alternative in {@link LatinParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryPrefix(LatinParser.UnaryPrefixContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryPrimary}
	 * labeled alternative in {@link LatinParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryPrimary(LatinParser.UnaryPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryPrimary}
	 * labeled alternative in {@link LatinParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryPrimary(LatinParser.UnaryPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrimaryNumLiteral}
	 * labeled alternative in {@link LatinParser#primaryNumeric}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryNumLiteral(LatinParser.PrimaryNumLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrimaryNumLiteral}
	 * labeled alternative in {@link LatinParser#primaryNumeric}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryNumLiteral(LatinParser.PrimaryNumLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrimaryNumId}
	 * labeled alternative in {@link LatinParser#primaryNumeric}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryNumId(LatinParser.PrimaryNumIdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrimaryNumId}
	 * labeled alternative in {@link LatinParser#primaryNumeric}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryNumId(LatinParser.PrimaryNumIdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrimaryNumParen}
	 * labeled alternative in {@link LatinParser#primaryNumeric}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryNumParen(LatinParser.PrimaryNumParenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrimaryNumParen}
	 * labeled alternative in {@link LatinParser#primaryNumeric}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryNumParen(LatinParser.PrimaryNumParenContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#numericLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNumericLiteral(LatinParser.NumericLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#numericLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNumericLiteral(LatinParser.NumericLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#stringExpression}.
	 * @param ctx the parse tree
	 */
	void enterStringExpression(LatinParser.StringExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#stringExpression}.
	 * @param ctx the parse tree
	 */
	void exitStringExpression(LatinParser.StringExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LatinParser#stringAdditiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterStringAdditiveExpression(LatinParser.StringAdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LatinParser#stringAdditiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitStringAdditiveExpression(LatinParser.StringAdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringItemPrimary}
	 * labeled alternative in {@link LatinParser#stringAdditiveItem}.
	 * @param ctx the parse tree
	 */
	void enterStringItemPrimary(LatinParser.StringItemPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringItemPrimary}
	 * labeled alternative in {@link LatinParser#stringAdditiveItem}.
	 * @param ctx the parse tree
	 */
	void exitStringItemPrimary(LatinParser.StringItemPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringItemNumeric}
	 * labeled alternative in {@link LatinParser#stringAdditiveItem}.
	 * @param ctx the parse tree
	 */
	void enterStringItemNumeric(LatinParser.StringItemNumericContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringItemNumeric}
	 * labeled alternative in {@link LatinParser#stringAdditiveItem}.
	 * @param ctx the parse tree
	 */
	void exitStringItemNumeric(LatinParser.StringItemNumericContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringItemBoolean}
	 * labeled alternative in {@link LatinParser#stringAdditiveItem}.
	 * @param ctx the parse tree
	 */
	void enterStringItemBoolean(LatinParser.StringItemBooleanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringItemBoolean}
	 * labeled alternative in {@link LatinParser#stringAdditiveItem}.
	 * @param ctx the parse tree
	 */
	void exitStringItemBoolean(LatinParser.StringItemBooleanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringPrimString}
	 * labeled alternative in {@link LatinParser#stringPrimary}.
	 * @param ctx the parse tree
	 */
	void enterStringPrimString(LatinParser.StringPrimStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringPrimString}
	 * labeled alternative in {@link LatinParser#stringPrimary}.
	 * @param ctx the parse tree
	 */
	void exitStringPrimString(LatinParser.StringPrimStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringPrimChar}
	 * labeled alternative in {@link LatinParser#stringPrimary}.
	 * @param ctx the parse tree
	 */
	void enterStringPrimChar(LatinParser.StringPrimCharContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringPrimChar}
	 * labeled alternative in {@link LatinParser#stringPrimary}.
	 * @param ctx the parse tree
	 */
	void exitStringPrimChar(LatinParser.StringPrimCharContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringPrimId}
	 * labeled alternative in {@link LatinParser#stringPrimary}.
	 * @param ctx the parse tree
	 */
	void enterStringPrimId(LatinParser.StringPrimIdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringPrimId}
	 * labeled alternative in {@link LatinParser#stringPrimary}.
	 * @param ctx the parse tree
	 */
	void exitStringPrimId(LatinParser.StringPrimIdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AttrField}
	 * labeled alternative in {@link LatinParser#attributeAccess}.
	 * @param ctx the parse tree
	 */
	void enterAttrField(LatinParser.AttrFieldContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AttrField}
	 * labeled alternative in {@link LatinParser#attributeAccess}.
	 * @param ctx the parse tree
	 */
	void exitAttrField(LatinParser.AttrFieldContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AttrIndex}
	 * labeled alternative in {@link LatinParser#attributeAccess}.
	 * @param ctx the parse tree
	 */
	void enterAttrIndex(LatinParser.AttrIndexContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AttrIndex}
	 * labeled alternative in {@link LatinParser#attributeAccess}.
	 * @param ctx the parse tree
	 */
	void exitAttrIndex(LatinParser.AttrIndexContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AttrCall}
	 * labeled alternative in {@link LatinParser#attributeAccess}.
	 * @param ctx the parse tree
	 */
	void enterAttrCall(LatinParser.AttrCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AttrCall}
	 * labeled alternative in {@link LatinParser#attributeAccess}.
	 * @param ctx the parse tree
	 */
	void exitAttrCall(LatinParser.AttrCallContext ctx);
}