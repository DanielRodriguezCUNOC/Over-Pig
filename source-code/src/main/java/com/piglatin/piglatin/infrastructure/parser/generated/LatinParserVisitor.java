// Generated from LatinParser.g4 by ANTLR 4.13.2
package com.piglatin.piglatin.infrastructure.parser.generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LatinParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LatinParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LatinParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(LatinParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#importList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportList(LatinParser.ImportListContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#importStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStatement(LatinParser.ImportStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#globalDeclarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalDeclarations(LatinParser.GlobalDeclarationsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GlobalDeclarationVariable}
	 * labeled alternative in {@link LatinParser#globalDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalDeclarationVariable(LatinParser.GlobalDeclarationVariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GlobalDeclarationArray}
	 * labeled alternative in {@link LatinParser#globalDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalDeclarationArray(LatinParser.GlobalDeclarationArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#mainInstructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainInstructions(LatinParser.MainInstructionsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionAssignment}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionAssignment(LatinParser.InstructionAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionRead}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionRead(LatinParser.InstructionReadContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionPrint}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionPrint(LatinParser.InstructionPrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionIf}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionIf(LatinParser.InstructionIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionWhile}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionWhile(LatinParser.InstructionWhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionDoWhile}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionDoWhile(LatinParser.InstructionDoWhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionFor}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionFor(LatinParser.InstructionForContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionJump}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionJump(LatinParser.InstructionJumpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionDeclaration}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionDeclaration(LatinParser.InstructionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionArrayDeclaration}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionArrayDeclaration(LatinParser.InstructionArrayDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstructionExpression}
	 * labeled alternative in {@link LatinParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructionExpression(LatinParser.InstructionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(LatinParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#arrayDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayDeclaration(LatinParser.ArrayDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#arrayLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayLiteral(LatinParser.ArrayLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(LatinParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(LatinParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#elseIfClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseIfClause(LatinParser.ElseIfClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#elseClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseClause(LatinParser.ElseClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(LatinParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#doWhileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileStatement(LatinParser.DoWhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(LatinParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForInitDeclaration}
	 * labeled alternative in {@link LatinParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInitDeclaration(LatinParser.ForInitDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForInitAssignment}
	 * labeled alternative in {@link LatinParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInitAssignment(LatinParser.ForInitAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForInitEmpty}
	 * labeled alternative in {@link LatinParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInitEmpty(LatinParser.ForInitEmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForUpdateAssignment}
	 * labeled alternative in {@link LatinParser#forUpdate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForUpdateAssignment(LatinParser.ForUpdateAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForUpdateExpression}
	 * labeled alternative in {@link LatinParser#forUpdate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForUpdateExpression(LatinParser.ForUpdateExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code JumpContinue}
	 * labeled alternative in {@link LatinParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpContinue(LatinParser.JumpContinueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code JumpBreak}
	 * labeled alternative in {@link LatinParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpBreak(LatinParser.JumpBreakContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#newInstance}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewInstance(LatinParser.NewInstanceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReadSimple}
	 * labeled alternative in {@link LatinParser#readStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadSimple(LatinParser.ReadSimpleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReadLvalue}
	 * labeled alternative in {@link LatinParser#readStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadLvalue(LatinParser.ReadLvalueContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#printStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStatement(LatinParser.PrintStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrintString}
	 * labeled alternative in {@link LatinParser#printItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintString(LatinParser.PrintStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrintExpression}
	 * labeled alternative in {@link LatinParser#printItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintExpression(LatinParser.PrintExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#lvalue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLvalue(LatinParser.LvalueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FieldAccess}
	 * labeled alternative in {@link LatinParser#lvalueSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldAccess(LatinParser.FieldAccessContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IndexAccess}
	 * labeled alternative in {@link LatinParser#lvalueSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexAccess(LatinParser.IndexAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(LatinParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypeNumerus}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeNumerus(LatinParser.TypeNumerusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypeTextum}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeTextum(LatinParser.TypeTextumContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypeDecimalis}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDecimalis(LatinParser.TypeDecimalisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypeLittera}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeLittera(LatinParser.TypeLitteraContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypeBool}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeBool(LatinParser.TypeBoolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypeID}
	 * labeled alternative in {@link LatinParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeID(LatinParser.TypeIDContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(LatinParser.ArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprBoolean}
	 * labeled alternative in {@link LatinParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprBoolean(LatinParser.ExprBooleanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprNumeric}
	 * labeled alternative in {@link LatinParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprNumeric(LatinParser.ExprNumericContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprString}
	 * labeled alternative in {@link LatinParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprString(LatinParser.ExprStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprArrayLiteral}
	 * labeled alternative in {@link LatinParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprArrayLiteral(LatinParser.ExprArrayLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprNewInstance}
	 * labeled alternative in {@link LatinParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprNewInstance(LatinParser.ExprNewInstanceContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#booleanExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanExpression(LatinParser.BooleanExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#booleanOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanOrExpression(LatinParser.BooleanOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#booleanAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanAndExpression(LatinParser.BooleanAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompNot}
	 * labeled alternative in {@link LatinParser#comparisonExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompNot(LatinParser.CompNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompCompare}
	 * labeled alternative in {@link LatinParser#comparisonExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompCompare(LatinParser.CompCompareContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompParen}
	 * labeled alternative in {@link LatinParser#comparisonExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompParen(LatinParser.CompParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompNumeric}
	 * labeled alternative in {@link LatinParser#comparisonOperand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompNumeric(LatinParser.CompNumericContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompBoolean}
	 * labeled alternative in {@link LatinParser#comparisonOperand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompBoolean(LatinParser.CompBooleanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompString}
	 * labeled alternative in {@link LatinParser#comparisonOperand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompString(LatinParser.CompStringContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#relationalOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalOp(LatinParser.RelationalOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(LatinParser.BooleanLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#numericExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericExpression(LatinParser.NumericExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(LatinParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(LatinParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryPrefix}
	 * labeled alternative in {@link LatinParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryPrefix(LatinParser.UnaryPrefixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryPrimary}
	 * labeled alternative in {@link LatinParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryPrimary(LatinParser.UnaryPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimaryNumLiteral}
	 * labeled alternative in {@link LatinParser#primaryNumeric}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNumLiteral(LatinParser.PrimaryNumLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimaryNumId}
	 * labeled alternative in {@link LatinParser#primaryNumeric}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNumId(LatinParser.PrimaryNumIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrimaryNumParen}
	 * labeled alternative in {@link LatinParser#primaryNumeric}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNumParen(LatinParser.PrimaryNumParenContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#numericLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericLiteral(LatinParser.NumericLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#stringExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringExpression(LatinParser.StringExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LatinParser#stringAdditiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringAdditiveExpression(LatinParser.StringAdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringItemPrimary}
	 * labeled alternative in {@link LatinParser#stringAdditiveItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringItemPrimary(LatinParser.StringItemPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringItemNumeric}
	 * labeled alternative in {@link LatinParser#stringAdditiveItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringItemNumeric(LatinParser.StringItemNumericContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringItemBoolean}
	 * labeled alternative in {@link LatinParser#stringAdditiveItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringItemBoolean(LatinParser.StringItemBooleanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringPrimString}
	 * labeled alternative in {@link LatinParser#stringPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringPrimString(LatinParser.StringPrimStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringPrimChar}
	 * labeled alternative in {@link LatinParser#stringPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringPrimChar(LatinParser.StringPrimCharContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringPrimId}
	 * labeled alternative in {@link LatinParser#stringPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringPrimId(LatinParser.StringPrimIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AttrField}
	 * labeled alternative in {@link LatinParser#attributeAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttrField(LatinParser.AttrFieldContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AttrIndex}
	 * labeled alternative in {@link LatinParser#attributeAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttrIndex(LatinParser.AttrIndexContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AttrCall}
	 * labeled alternative in {@link LatinParser#attributeAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttrCall(LatinParser.AttrCallContext ctx);
}