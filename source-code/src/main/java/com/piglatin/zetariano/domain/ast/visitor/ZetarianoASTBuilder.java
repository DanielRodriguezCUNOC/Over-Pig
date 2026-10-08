package com.piglatin.zetariano.domain.ast.visitor;

import com.piglatin.zetariano.domain.ast.enums.AccessModifier;
import com.piglatin.zetariano.domain.ast.enums.AssignmentOperator;
import com.piglatin.zetariano.domain.ast.principal.*;
import com.piglatin.zetariano.domain.ast.statements.*;
import com.piglatin.zetariano.domain.ast.expressions.*;
import com.piglatin.zetariano.domain.ast.expressions.literals.*;
import com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoParser;
import com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoParserBaseVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;

public class ZetarianoASTBuilder extends ZetarianoParserBaseVisitor<ASTNode> {


    @Override
    public ASTNode visitCompilationUnit(ZetarianoParser.CompilationUnitContext ctx) {
        if (ctx == null) return null;
        List<NodeClassDeclaration> classes = new ArrayList<>();
        if (ctx.classDeclaration() != null) {
            for (ZetarianoParser.ClassDeclarationContext classCtx : ctx.classDeclaration()) {
                ASTNode node = visit(classCtx);
                if (node instanceof NodeClassDeclaration classDecl) classes.add(classDecl);
            }
        }
        return new NodeProgram(classes, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitClassDeclaration(ZetarianoParser.ClassDeclarationContext ctx) {
        if (ctx == null) return null;
        AccessModifier modifier = parseAccessModifier(ctx.accessModifier());
        String name = ctx.IDENTIFIER(0).getText();

        String superClass = (ctx.EXTENDS() != null && ctx.IDENTIFIER().size() > 1) ? ctx.IDENTIFIER(1).getText() : null;
        List<ASTNode> members = new ArrayList<>();
        if (ctx.classBodyMember() != null){
            for (ZetarianoParser.ClassBodyMemberContext memberCtx : ctx.classBodyMember()){
                members.add(visit(memberCtx));
            }
        }
        return new NodeClassDeclaration(modifier, name, superClass, members, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitFieldDeclarationClassBodyMember(ZetarianoParser.FieldDeclarationClassBodyMemberContext ctx) {
        return visit(ctx.fieldDeclaration());
    }

    @Override
    public ASTNode visitConstructorDeclarationClassBodyMember(ZetarianoParser.ConstructorDeclarationClassBodyMemberContext ctx) {
        return visit(ctx.constructorDeclaration());
    }

    @Override
    public ASTNode visitMethodDeclarationClassBodyMember(ZetarianoParser.MethodDeclarationClassBodyMemberContext ctx) {
        return visit(ctx.methodDeclaration());
    }

    @Override
    public ASTNode visitFieldDeclaration(ZetarianoParser.FieldDeclarationContext ctx) {
        if (ctx == null) return null;
        AccessModifier modifier = parseAccessModifier(ctx.accessModifier());
        String type = ctx.type().getText();
        String name = ctx.IDENTIFIER().getText();
        NodeExpression initializer = ctx.expression() != null ? asExpression(visit(ctx.expression())) : null;
        boolean isArray = ctx.type().LBRACK() != null && !ctx.type().LBRACK().isEmpty();
        int dimensions = isArray ? ctx.type().LBRACK().size() : 0;

        return new NodeFieldDeclaration(modifier, type, name, initializer, isArray, dimensions, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitMethodDeclaration(ZetarianoParser.MethodDeclarationContext ctx) {
        if (ctx == null) return null;
        boolean isOverride = (ctx.OVERRIDE() != null);
        AccessModifier modifier = parseAccessModifier(ctx.accessModifier());
        String returnType = ctx.typeOrVoid().getText();
        String name = ctx.IDENTIFIER().getText();
        List<NodeParameter> parameters = buildParameters(ctx.parameterList());
        NodeBlock body = asBlock(visit(ctx.block()));

        return new NodeMethodDeclaration(isOverride, modifier, returnType, name, parameters, body, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitConstructorDeclaration(ZetarianoParser.ConstructorDeclarationContext ctx) {
        if (ctx == null) return null;
        AccessModifier modifier = parseAccessModifier(ctx.accessModifier());
        String name = ctx.IDENTIFIER().getText();
        List<NodeParameter> parameters = buildParameters(ctx.parameterList());
        NodeBlock body = asBlock(visit(ctx.block()));

        return new NodeConstructorDeclaration(modifier, name, parameters, body, getLine(ctx), getColumn(ctx));
    }


    @Override
    public ASTNode visitThisPrimary(ZetarianoParser.ThisPrimaryContext ctx) {
        return new NodeThis(getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitTarget (ZetarianoParser.TargetContext ctx) {
        NodeLvalue current;
        int idIdx = 0;

        //* Handle the root of the target if are 'IDENTIFIER' or 'this'
        if (ctx.THIS() != null) {
            current = new NodeThis(getLine(ctx), getColumn(ctx));
        } else {
            current = new NodeIdentifier(ctx.IDENTIFIER(idIdx++).getText(), getLine(ctx), getColumn(ctx));
        }

        int exprIdx = 0;

        for (int i = 0; i < ctx.getChildCount(); i++) {
            String text = ctx.getChild(i).getText();
            if (text.equals("[")) {
                NodeExpression idx = asExpression(visit(ctx.expression(exprIdx++)));
                current = new NodeIndexAccess(current, idx, getLine(ctx), getColumn(ctx));
            } else if (text.equals(".")) {
                String fieldName = ctx.IDENTIFIER(idIdx++).getText();
                current = new NodeFieldAccess(current, fieldName, getLine(ctx), getColumn(ctx));
            }
        }
        return current;
    }

    @Override
    public ASTNode visitParameter(ZetarianoParser.ParameterContext ctx) {
        if (ctx == null) return null;
        String type = ctx.type().getText();
        String name = ctx.IDENTIFIER().getText();
        boolean isArray = ctx.type().LBRACK() != null && !ctx.type().LBRACK().isEmpty();
        return new NodeParameter(type, name, isArray, getLine(ctx), getColumn(ctx));
    }


    @Override
    public ASTNode visitBlock(ZetarianoParser.BlockContext ctx) {
        if (ctx == null) return null;
        List<ASTNode> instructions = new ArrayList<>();
        if (ctx.statement() != null) {
            for (ZetarianoParser.StatementContext stCtx : ctx.statement()) {
                ASTNode node = visit(stCtx);
                if (node != null) instructions.add(node);
            }
        }
        return new NodeBlock(instructions, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitStatementBlock(ZetarianoParser.StatementBlockContext ctx) {
        return visit(ctx.block());
    }

    @Override
    public ASTNode visitLocalVariableDeclarationStatement(ZetarianoParser.LocalVariableDeclarationStatementContext ctx) {
        return visit(ctx.localVariableDeclaration());
    }

    @Override
    public ASTNode visitLocalVariableDeclaration(ZetarianoParser.LocalVariableDeclarationContext ctx) {
        String type = ctx.type().getText();
        String name = ctx.IDENTIFIER().getText();
        NodeExpression initializer = ctx.expression() != null ? asExpression(visit(ctx.expression())) : null;
        return new NodeVariableDeclaration(type, name, initializer, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitStatementAssignment(ZetarianoParser.StatementAssignmentContext ctx) {
        return visit(ctx.assignmentStatement());
    }

    @Override
    public ASTNode visitAssignmentStatement(ZetarianoParser.AssignmentStatementContext ctx) {
        NodeLvalue target = asLvalue(visit(ctx.target()));
        AssignmentOperator op = parseAssignmentOperator(ctx.assignmentOperator());
        NodeExpression expr = asExpression(visit(ctx.expression()));
        return new NodeAssignment(target, op, expr, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitStatementIf(ZetarianoParser.StatementIfContext ctx) {
        return visit(ctx.ifStatement());
    }

    @Override
    public ASTNode visitIfStatement(ZetarianoParser.IfStatementContext ctx) {
        NodeExpression condition = asExpression(visit(ctx.expression()));
        ASTNode thenBlock = visit(ctx.statement(0));
        ASTNode elseBlock = ctx.statement().size() > 1 ? visit(ctx.statement(1)) : null;
        return new NodeIf(condition, thenBlock, elseBlock, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitStatementWhile(ZetarianoParser.StatementWhileContext ctx) {
        return visit(ctx.whileStatement());
    }

    @Override
    public ASTNode visitWhileStatement(ZetarianoParser.WhileStatementContext ctx) {
        NodeExpression condition = asExpression(visit(ctx.expression()));
        ASTNode block = visit(ctx.statement());
        return new NodeWhile(condition, block, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitStatementDoWhile(ZetarianoParser.StatementDoWhileContext ctx) {
        return visit(ctx.doWhileStatement());
    }

    @Override
    public ASTNode visitDoWhileStatement(ZetarianoParser.DoWhileStatementContext ctx) {
        ASTNode block = visit(ctx.statement());
        NodeExpression condition = asExpression(visit(ctx.expression()));
        return new NodeDoWhile(block, condition, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitStatementFor(ZetarianoParser.StatementForContext ctx) {
        return visit(ctx.forStatement());
    }

    @Override
    public ASTNode visitForStatement(ZetarianoParser.ForStatementContext ctx) {
        ASTNode init = ctx.forInit() != null ? visit(ctx.forInit()) : null;
        NodeExpression condition = ctx.expression() != null ? asExpression(visit(ctx.expression())) : null;
        ASTNode update = ctx.forUpdate() != null ? visit(ctx.forUpdate()) : null;
        ASTNode block = visit(ctx.statement());

        return new NodeFor(init, condition, update, block, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitStatementReturn(ZetarianoParser.StatementReturnContext ctx) {
        return visit(ctx.returnStatement());
    }

    @Override
    public ASTNode visitReturnStatement(ZetarianoParser.ReturnStatementContext ctx) {
        NodeExpression expr = ctx.expression() != null ? asExpression(visit(ctx.expression())) : null;
        return new NodeReturn(expr, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitStatementBreak(ZetarianoParser.StatementBreakContext ctx) {
        return new NodeBreak(getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitStatementContinue(ZetarianoParser.StatementContinueContext ctx) {
        return new NodeContinue(getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitPrimary(ZetarianoParser.PrimaryContext ctx) {
        ASTNode current = visit(ctx.primaryCore());

        for (ZetarianoParser.PrimarySuffixContext suffixCtx : ctx.primarySuffix()) {
            int line = getLine(suffixCtx);
            int column = getColumn(suffixCtx);

            if (suffixCtx instanceof ZetarianoParser.MethodCallSuffixContext mCtx) {
                String methodName = mCtx.IDENTIFIER().getText();
                List<NodeExpression> args = parseExpressionList(mCtx.expressionList());
                current = new NodeMethodCall((NodeLvalue) current, methodName, args, line, column);

            } else if (suffixCtx instanceof ZetarianoParser.FieldAccessSuffixContext fCtx) {
                String fieldName = fCtx.IDENTIFIER().getText();
                current = new NodeFieldAccess((NodeLvalue) current, fieldName, line, column);

            } else if (suffixCtx instanceof ZetarianoParser.ArrayAccessSuffixContext aCtx) {
                NodeExpression indexExpr = asExpression(visit(aCtx.expression()));
                current = new NodeIndexAccess((NodeLvalue) current, indexExpr, line, column);
            }
        }
        return current;
    }

    @Override
    public ASTNode visitParenthesizedExpression(ZetarianoParser.ParenthesizedExpressionContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public ASTNode visitLiteralPrimary(ZetarianoParser.LiteralPrimaryContext ctx) {
        return visit(ctx.literal());
    }

    @Override
    public ASTNode visitAllocationExpressionPrimary(ZetarianoParser.AllocationExpressionPrimaryContext ctx) {
        return visit(ctx.allocationExpression());
    }

    @Override
    public ASTNode visitDirectMethodCall(ZetarianoParser.DirectMethodCallContext ctx) {
        int line = getLine(ctx);
        int col = getColumn(ctx);
        String methodName = ctx.IDENTIFIER().getText();
        List<NodeExpression> args = parseExpressionList(ctx.expressionList());

        return new NodeMethodCall(null, methodName, args, line, col);
    }

    @Override
    public ASTNode visitVariableOrField(ZetarianoParser.VariableOrFieldContext ctx) {
        return new NodeIdentifier(ctx.IDENTIFIER().getText(), getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitAllocationExpressionArray(ZetarianoParser.AllocationExpressionArrayContext ctx) {
        String type = ctx.primitiveType().getText();
        List<NodeExpression> dimensions = mapExpressions(ctx.arrayDimensions().expression());
        return new NodeNewArray(type, dimensions, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitAllocationExpressionObject(ZetarianoParser.AllocationExpressionObjectContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        if (ctx.arrayDimensions() != null) {
            List<NodeExpression> dimensions = mapExpressions(ctx.arrayDimensions().expression());
            return new NodeNewArray(name, dimensions, getLine(ctx), getColumn(ctx));
        } else {
            List<NodeExpression> args = parseExpressionList(ctx.expressionList());
            return new NodeNewObject(name, args, getLine(ctx), getColumn(ctx));
        }
    }

    @Override
    public ASTNode visitLiteral(ZetarianoParser.LiteralContext ctx) {
        if (ctx == null) return null;
        if (ctx.INT_LITERAL() != null) return new NodeIntegerLiteral(Integer.parseInt(ctx.INT_LITERAL().getText()), getLine(ctx), getColumn(ctx));
        if (ctx.CHAR_LITERAL() != null) return new NodeCharLiteral(parseChar(ctx.CHAR_LITERAL().getText()), getLine(ctx), getColumn(ctx));
        if (ctx.STRING_LITERAL() != null) return new NodeStringLiteral(parseString(ctx.STRING_LITERAL().getText()), getLine(ctx), getColumn(ctx));
        if (ctx.TRUE() != null) return new NodeBooleanLiteral(true, getLine(ctx), getColumn(ctx));
        if (ctx.FALSE() != null) return new NodeBooleanLiteral(false, getLine(ctx), getColumn(ctx));
        if (ctx.NULL() != null) return new NodeNullLiteral(getLine(ctx), getColumn(ctx));
        return null;
    }

    private String parseString(String text) {
        if (text.length() >= 2 && text.charAt(0) == '"' && text.charAt(text.length() - 1) == '"') {
            text = text.substring(1, text.length() - 1);
        }
        return text;
    }

    private char parseChar(String text) {
        if (text.length() >= 2 && text.charAt(0) == '\'' && text.charAt(text.length() - 1) == '\'') {
            text = text.substring(1, text.length() - 1);
        }
        if (text.isEmpty()) return 0;
        return text.charAt(0);
    }

    private List<NodeParameter> buildParameters(ZetarianoParser.ParameterListContext ctx) {
        List<NodeParameter> parameters = new ArrayList<>();
        if (ctx == null || ctx.parameter() == null) return parameters;
        for (ZetarianoParser.ParameterContext pCtx : ctx.parameter()) {
            ASTNode p = visit(pCtx);
            if (p instanceof NodeParameter nodeParam) parameters.add(nodeParam);
        }
        return parameters;
    }

    private List<NodeExpression> mapExpressions(List<ZetarianoParser.ExpressionContext> exprContexts) {
        List<NodeExpression> result = new ArrayList<>();
        if (exprContexts == null) return result;
        for (ZetarianoParser.ExpressionContext exprCtx : exprContexts) {
            NodeExpression expr = asExpression(visit(exprCtx));
            if (expr != null) result.add(expr);
        }
        return result;
    }

    private List<NodeExpression> parseExpressionList(ZetarianoParser.ExpressionListContext ctx) {
        List<NodeExpression> args = new ArrayList<>();
        if (ctx != null && ctx.expression() != null) {
            for (ZetarianoParser.ExpressionContext exprCtx : ctx.expression()) {
                args.add(asExpression(visit(exprCtx)));
            }
        }
        return args;
    }

    private AssignmentOperator parseAssignmentOperator(ZetarianoParser.AssignmentOperatorContext ctx) {
        if (ctx instanceof ZetarianoParser.AssignmentOperatorAddAssignContext) return AssignmentOperator.ADD_ASSIGN;
        if (ctx instanceof ZetarianoParser.AssignmentOperatorSubAssignContext) return AssignmentOperator.SUB_ASSIGN;
        if (ctx instanceof ZetarianoParser.AssignmentOperatorMulAssignContext) return AssignmentOperator.MUL_ASSIGN;
        if (ctx instanceof ZetarianoParser.AssignmentOperatorDivAssignContext) return AssignmentOperator.DIV_ASSIGN;
        if (ctx instanceof ZetarianoParser.AssignmentOperatorModAssignContext) return AssignmentOperator.MOD_ASSIGN;
        return AssignmentOperator.ASSIGN;
    }

    private NodeExpression asExpression(ASTNode node) {
        return (node instanceof NodeExpression expr) ? expr : null;
    }

    private NodeLvalue asLvalue(ASTNode node) {
        return (node instanceof NodeLvalue lval) ? lval : null;
    }

    private NodeBlock asBlock(ASTNode node) {
        return (node instanceof NodeBlock block) ? block : null;
    }

    private int getLine(ParserRuleContext ctx) {
        return (ctx != null && ctx.getStart() != null) ? ctx.getStart().getLine() : 0;
    }

    private int getColumn(ParserRuleContext ctx) {
        return (ctx != null && ctx.getStart() != null) ? ctx.getStart().getCharPositionInLine() : 0;
    }

    private AccessModifier parseAccessModifier(ZetarianoParser.AccessModifierContext ctx) {
        if (ctx == null) return AccessModifier.DEFAULT;
        if (ctx.PUBLIC() != null) return AccessModifier.PUBLIC;
        if (ctx.PRIVATE() != null) return AccessModifier.PRIVATE;
        if (ctx.PROTECTED() != null) return AccessModifier.PROTECTED;
        return AccessModifier.DEFAULT;
    }
}