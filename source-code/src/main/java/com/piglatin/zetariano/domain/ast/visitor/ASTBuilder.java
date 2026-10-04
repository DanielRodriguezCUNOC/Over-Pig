package com.piglatin.zetariano.domain.ast.visitor;

import com.piglatin.zetariano.domain.ast.principal.*;
import com.piglatin.zetariano.domain.ast.statements.*;
import com.piglatin.zetariano.domain.ast.expressions.*;
import com.piglatin.zetariano.domain.ast.expressions.literals.*;
import com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoParser;
import com.piglatin.zetariano.infrastructure.parser.generated.ZetarianoParserBaseVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts ANTLR CST into Zetariano AST nodes.
 */
public class ASTBuilder extends ZetarianoParserBaseVisitor<ASTNode> {

    // ============================================================
    // PROGRAM & IMPORTS
    // ============================================================

    @Override
    public ASTNode visitProgram(ZetarianoParser.ProgramContext ctx) {
        if (ctx == null) return null;
        NodeClassDeclaration classDecl = ctx.classDefinition() != null 
                ? (NodeClassDeclaration) visit(ctx.classDefinition()) 
                : null;
        return new NodeProgram(classDecl, getLine(ctx), getColumn(ctx));
    }

    // ============================================================
    // CLASS & GLOBAL DECLARATIONS
    // ============================================================

    @Override
    public ASTNode visitClassDefinition(ZetarianoParser.ClassDefinitionContext ctx) {
        if (ctx == null) return null;
        boolean isPublic = (ctx.PUBLIC() != null);
        String name = ctx.ID().getText();
        List<ASTNode> members = buildGlobalDeclarations(ctx.globalDeclarations());
        return new NodeClassDeclaration(isPublic, name, members, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitGlobalDeclarations(ZetarianoParser.GlobalDeclarationsContext ctx) {
        return null;
    }

    @Override
    public ASTNode visitGlobalField(ZetarianoParser.GlobalFieldContext ctx) {
        return visit(ctx.fieldDeclaration());
    }

    @Override
    public ASTNode visitGlobalMethod(ZetarianoParser.GlobalMethodContext ctx) {
        return visit(ctx.methodDeclaration());
    }

    @Override
    public ASTNode visitGlobalConstructor(ZetarianoParser.GlobalConstructorContext ctx) {
        return visit(ctx.constructorDeclaration());
    }

    @Override
    public ASTNode visitFieldDeclaration(ZetarianoParser.FieldDeclarationContext ctx) {
        if (ctx == null) return null;
        String type = extractType(ctx.type());
        String name = ctx.ID().getText();

        boolean isArray = ctx.LEFT_BRACKET() != null && !ctx.LEFT_BRACKET().isEmpty();
        int dimensions = ctx.LEFT_BRACKET() != null ? ctx.LEFT_BRACKET().size() : 0;

        NodeExpression initializer = null;
        if (ctx.expression() != null) {
            initializer = asExpression(visit(ctx.expression()));
        } else if (ctx.arrayInitializer() != null) {
            initializer = asExpression(visit(ctx.arrayInitializer()));
        }

        return new NodeFieldDeclaration(type, name, initializer, isArray, dimensions, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitMethodDeclaration(ZetarianoParser.MethodDeclarationContext ctx) {
        if (ctx == null) return null;
        boolean isPublic = (ctx.PUBLIC() != null);
        String returnType = ctx.VOID() != null ? "void" : extractType(ctx.type());
        String name = ctx.ID().getText();
        List<NodeParameter> parameters = buildParameters(ctx.parameterList());
        NodeBlock body = asBlock(visit(ctx.block()));

        return new NodeMethodDeclaration(isPublic, returnType, name, parameters, body, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitConstructorDeclaration(ZetarianoParser.ConstructorDeclarationContext ctx) {
        if (ctx == null) return null;
        boolean isPublic = (ctx.PUBLIC() != null);
        String name = ctx.ID().getText();
        List<NodeParameter> parameters = buildParameters(ctx.parameterList());
        NodeBlock body = asBlock(visit(ctx.block()));

        return new NodeConstructorDeclaration(isPublic, name, parameters, body, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitParameterList(ZetarianoParser.ParameterListContext ctx) {
        return null;
    }

    @Override
    public ASTNode visitParameter(ZetarianoParser.ParameterContext ctx) {
        if (ctx == null) return null;
        String type = extractType(ctx.type());
        String name = ctx.ID().getText();
        boolean isArray = !ctx.LEFT_BRACKET().isEmpty();
        return new NodeParameter(type, name, isArray, getLine(ctx), getColumn(ctx));
    }

    // ============================================================
    // BLOCKS & INSTRUCTIONS
    // ============================================================

    @Override
    public ASTNode visitBlock(ZetarianoParser.BlockContext ctx) {
        if (ctx == null) return null;
        List<ASTNode> instructions = new ArrayList<>();
        if (ctx.mainInstructions() != null) {
            instructions.addAll(mapInstructions(ctx.mainInstructions().instruction()));
        } else if (ctx.instruction() != null) {
            ASTNode inst = visit(ctx.instruction());
            if (inst != null) instructions.add(inst);
        }
        return new NodeBlock(instructions, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitMainInstructions(ZetarianoParser.MainInstructionsContext ctx) {
        return null;
    }

    @Override
    public ASTNode visitInstructionAssignment(ZetarianoParser.InstructionAssignmentContext ctx) {
        return visit(ctx.assignment());
    }

    @Override
    public ASTNode visitInstructionRead(ZetarianoParser.InstructionReadContext ctx) {
        return visit(ctx.readStatement());
    }

    @Override
    public ASTNode visitInstructionPrint(ZetarianoParser.InstructionPrintContext ctx) {
        return visit(ctx.printStatement());
    }

    @Override
    public ASTNode visitInstructionIf(ZetarianoParser.InstructionIfContext ctx) {
        return visit(ctx.ifStatement());
    }

    @Override
    public ASTNode visitInstructionSwitch(ZetarianoParser.InstructionSwitchContext ctx) {
        return visit(ctx.switchStatement());
    }

    @Override
    public ASTNode visitInstructionWhile(ZetarianoParser.InstructionWhileContext ctx) {
        return visit(ctx.whileStatement());
    }

    @Override
    public ASTNode visitInstructionDoWhile(ZetarianoParser.InstructionDoWhileContext ctx) {
        return visit(ctx.doWhileStatement());
    }

    @Override
    public ASTNode visitInstructionFor(ZetarianoParser.InstructionForContext ctx) {
        return visit(ctx.forStatement());
    }

    @Override
    public ASTNode visitInstructionJump(ZetarianoParser.InstructionJumpContext ctx) {
        return visit(ctx.jumpStatement());
    }

    @Override
    public ASTNode visitInstructionDeclaration(ZetarianoParser.InstructionDeclarationContext ctx) {
        return visit(ctx.variableDeclaration());
    }

    @Override
    public ASTNode visitInstructionArrayDeclaration(ZetarianoParser.InstructionArrayDeclarationContext ctx) {
        return visit(ctx.arrayDeclaration());
    }

    @Override
    public ASTNode visitInstructionExpression(ZetarianoParser.InstructionExpressionContext ctx) {
        return visit(ctx.expression());
    }

    // ============================================================
    // STATEMENTS IMPLEMENTATION
    // ============================================================

    @Override
    public ASTNode visitVariableDeclaration(ZetarianoParser.VariableDeclarationContext ctx) {
        if (ctx == null) return null;
        String type = extractType(ctx.type());
        String name = ctx.ID().getText();
        NodeExpression initializer = ctx.expression() != null ? asExpression(visit(ctx.expression())) : null;
        return new NodeVariableDeclaration(type, name, initializer, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitArrayDeclaration(ZetarianoParser.ArrayDeclarationContext ctx) {
        if (ctx == null) return null;
        String type = extractType(ctx.type());
        String name = ctx.ID().getText();
        int dimensions = ctx.LEFT_BRACKET() != null && !ctx.LEFT_BRACKET().isEmpty() ? ctx.LEFT_BRACKET().size() : 1;

        NodeExpression size = null;
        NodeArrayInitializer initializer = null;

        if (ctx.expression() != null) {
            size = asExpression(visit(ctx.expression()));
        }
        if (ctx.arrayInitializer() != null) {
            initializer = (NodeArrayInitializer) visit(ctx.arrayInitializer());
        }
        return new NodeArrayDeclaration(type, name, dimensions, size, initializer, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitArrayInitializer(ZetarianoParser.ArrayInitializerContext ctx) {
        if (ctx == null) return null;
        List<NodeExpression> elements = new ArrayList<>();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof ZetarianoParser.ExpressionContext e) {
                elements.add(asExpression(visit(e)));
            } else if (ctx.getChild(i) instanceof ZetarianoParser.ArrayInitializerContext ai) {
                elements.add((NodeArrayInitializer) visit(ai));
            }
        }
        return new NodeArrayInitializer(elements, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitAssignment(ZetarianoParser.AssignmentContext ctx) {
        if (ctx == null) return null;
        NodeLvalue target = asLvalue(visit(ctx.lvalue()));
        if (ctx.expression() != null) {
            String op = ctx.getChild(1).getText();
            NodeExpression expr = asExpression(visit(ctx.expression()));
            return new NodeAssignment(target, op, expr, getLine(ctx), getColumn(ctx));
        } else {
            String op = ctx.INC() != null ? "++" : "--";
            NodeExpression one = new NodeIntegerLiteral(1, getLine(ctx), getColumn(ctx));
            return new NodeAssignment(target, op, one, getLine(ctx), getColumn(ctx));
        }
    }

    @Override
    public ASTNode visitReadStatement(ZetarianoParser.ReadStatementContext ctx) {
        if (ctx == null) return null;
        return new NodeRead(getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitPrintStatement(ZetarianoParser.PrintStatementContext ctx) {
        if (ctx == null) return null;
        boolean newline = (ctx.PRINTLN() != null);
        List<NodeExpression> expressions = new ArrayList<>();
        if (ctx.expression() != null) {
            NodeExpression expr = asExpression(visit(ctx.expression()));
            if (expr != null) expressions.add(expr);
        }
        return new NodePrint(expressions, newline, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitIfStatement(ZetarianoParser.IfStatementContext ctx) {
        if (ctx == null) return null;
        NodeExpression condition = asExpression(visit(ctx.expression()));
        ASTNode thenBlock = visit(ctx.block(0));
        ASTNode elseBlock = (ctx.block().size() > 1) ? visit(ctx.block(1)) : null;
        return new NodeIf(condition, thenBlock, elseBlock, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitSwitchStatement(ZetarianoParser.SwitchStatementContext ctx) {
        if (ctx == null) return null;
        NodeExpression expr = asExpression(visit(ctx.expression(0)));
        List<NodeCase> cases = buildCases(ctx);
        NodeBlock defaultBlock = buildDefaultBlock(ctx);
        return new NodeSwitch(expr, cases, defaultBlock, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitWhileStatement(ZetarianoParser.WhileStatementContext ctx) {
        if (ctx == null) return null;
        NodeExpression condition = asExpression(visit(ctx.expression()));
        ASTNode block = visit(ctx.block());
        return new NodeWhile(condition, block, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitDoWhileStatement(ZetarianoParser.DoWhileStatementContext ctx) {
        if (ctx == null) return null;
        ASTNode block = visit(ctx.block());
        NodeExpression condition = asExpression(visit(ctx.expression()));
        return new NodeDoWhile(block, condition, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitForStatement(ZetarianoParser.ForStatementContext ctx) {
        if (ctx == null) return null;
        ASTNode init = buildForInit(ctx);
        NodeExpression condition = buildForCondition(ctx);
        ASTNode update = buildForUpdate(ctx);
        ASTNode block = visit(ctx.block());

        return new NodeFor(init, condition, update, block, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitJumpBreak(ZetarianoParser.JumpBreakContext ctx) {
        return new NodeBreak(getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitJumpContinue(ZetarianoParser.JumpContinueContext ctx) {
        return new NodeContinue(getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitJumpReturn(ZetarianoParser.JumpReturnContext ctx) {
        NodeExpression expr = ctx.expression() != null ? asExpression(visit(ctx.expression())) : null;
        return new NodeReturn(expr, getLine(ctx), getColumn(ctx));
    }

    // ============================================================
    // EXPRESSIONS IMPLEMENTATION
    // ============================================================

    @Override
    public ASTNode visitExprParen(ZetarianoParser.ExprParenContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public ASTNode visitExprTernary(ZetarianoParser.ExprTernaryContext ctx) {
        NodeExpression cond = asExpression(visit(ctx.expression(0)));
        NodeExpression trueExpr = asExpression(visit(ctx.expression(1)));
        NodeExpression falseExpr = asExpression(visit(ctx.expression(2)));
        return new NodeTernaryExpression(cond, trueExpr, falseExpr, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitExprAnd(ZetarianoParser.ExprAndContext ctx) {
        return createBinary(ctx.expression(0), "&&", ctx.expression(1), ctx);
    }

    @Override
    public ASTNode visitExprOr(ZetarianoParser.ExprOrContext ctx) {
        return createBinary(ctx.expression(0), "||", ctx.expression(1), ctx);
    }

    @Override
    public ASTNode visitExprEquality(ZetarianoParser.ExprEqualityContext ctx) {
        return createBinary(ctx.expression(0), ctx.getChild(1).getText(), ctx.expression(1), ctx);
    }

    @Override
    public ASTNode visitExprRelational(ZetarianoParser.ExprRelationalContext ctx) {
        return createBinary(ctx.expression(0), ctx.getChild(1).getText(), ctx.expression(1), ctx);
    }

    @Override
    public ASTNode visitExprAdditive(ZetarianoParser.ExprAdditiveContext ctx) {
        return createBinary(ctx.expression(0), ctx.getChild(1).getText(), ctx.expression(1), ctx);
    }

    @Override
    public ASTNode visitExprMultiplicative(ZetarianoParser.ExprMultiplicativeContext ctx) {
        return createBinary(ctx.expression(0), ctx.getChild(1).getText(), ctx.expression(1), ctx);
    }

    @Override
    public ASTNode visitExprUnary(ZetarianoParser.ExprUnaryContext ctx) {
        String op = ctx.getChild(0).getText();
        NodeExpression expr = asExpression(visit(ctx.expression()));
        return new NodeUnaryExpression(op, expr, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitExprPrimary(ZetarianoParser.ExprPrimaryContext ctx) {
        return visit(ctx.primary());
    }

    @Override
    public ASTNode visitPrimaryLiteral(ZetarianoParser.PrimaryLiteralContext ctx) {
        return visit(ctx.literal());
    }

    @Override
    public ASTNode visitPrimaryLvalueOrCall(ZetarianoParser.PrimaryLvalueOrCallContext ctx) {
        if (ctx == null) return null;
        NodeLvalue fullLvalue = asLvalue(visit(ctx.lvalue()));

        if (ctx.LEFT_PAREN() != null) {
            List<NodeExpression> args = buildArgumentList(ctx.argumentList());
            return createMethodCallNode(fullLvalue, args, getLine(ctx), getColumn(ctx));
        }
        return fullLvalue;
    }

    @Override
    public ASTNode visitPrimaryNewObject(ZetarianoParser.PrimaryNewObjectContext ctx) {
        String className = ctx.ID().getText();
        List<NodeExpression> args = buildArgumentList(ctx.argumentList());
        return new NodeNewObject(className, args, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitPrimaryNewArray(ZetarianoParser.PrimaryNewArrayContext ctx) {
        String type = extractType(ctx.type());
        List<NodeExpression> dimensions = mapExpressions(ctx.expression());
        return new NodeNewArray(type, dimensions, getLine(ctx), getColumn(ctx));
    }

    @Override
    public ASTNode visitLvalue(ZetarianoParser.LvalueContext ctx) {
        if (ctx == null) return null;
        if (ctx.lvalue() == null) {
            return new NodeIdentifier(ctx.ID().getText(), getLine(ctx), getColumn(ctx));
        }
        NodeLvalue base = asLvalue(visit(ctx.lvalue()));
        if (ctx.DOT() != null) {
            String field = ctx.ID().getText();
            return new NodeFieldAccess(base, field, getLine(ctx), getColumn(ctx));
        } else {
            NodeExpression idx = asExpression(visit(ctx.expression()));
            return new NodeIndexAccess(base, idx, getLine(ctx), getColumn(ctx));
        }
    }

    @Override
    public ASTNode visitLiteral(ZetarianoParser.LiteralContext ctx) {
        if (ctx == null) return null;
        if (ctx.INTEGER() != null) return new NodeIntegerLiteral(Integer.parseInt(ctx.INTEGER().getText()), getLine(ctx), getColumn(ctx));
        if (ctx.DECIMAL() != null) return new NodeDecimalLiteral(Double.parseDouble(ctx.DECIMAL().getText()), getLine(ctx), getColumn(ctx));
        if (ctx.CHAR() != null) return new NodeCharLiteral(parseChar(ctx.CHAR().getText()), getLine(ctx), getColumn(ctx));
        if (ctx.STRING() != null) return new NodeStringLiteral(parseString(ctx.STRING().getText()), getLine(ctx), getColumn(ctx));
        if (ctx.TRUE() != null) return new NodeBooleanLiteral(true, getLine(ctx), getColumn(ctx));
        if (ctx.FALSE() != null) return new NodeBooleanLiteral(false, getLine(ctx), getColumn(ctx));
        if (ctx.NULL() != null) return new NodeNullLiteral(getLine(ctx), getColumn(ctx));
        return null;
    }

    @Override
    public ASTNode visitType(ZetarianoParser.TypeContext ctx) {
        return null;
    }

    @Override
    public ASTNode visitArgumentList(ZetarianoParser.ArgumentListContext ctx) {
        return null;
    }

    // ============================================================
    // HELPER METHODS FOR MODULARITY & LOW COMPLEXITY
    // ============================================================

    private List<ASTNode> buildGlobalDeclarations(ZetarianoParser.GlobalDeclarationsContext ctx) {
        List<ASTNode> members = new ArrayList<>();
        if (ctx == null || ctx.globalDeclaration() == null) return members;
        for (ZetarianoParser.GlobalDeclarationContext declCtx : ctx.globalDeclaration()) {
            ASTNode member = visit(declCtx);
            if (member != null) members.add(member);
        }
        return members;
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

    private List<ASTNode> mapInstructions(List<ZetarianoParser.InstructionContext> instContexts) {
        List<ASTNode> result = new ArrayList<>();
        if (instContexts == null) return result;
        for (ZetarianoParser.InstructionContext instCtx : instContexts) {
            ASTNode inst = visit(instCtx);
            if (inst != null) result.add(inst);
        }
        return result;
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

    private List<NodeExpression> buildArgumentList(ZetarianoParser.ArgumentListContext ctx) {
        if (ctx == null) return new ArrayList<>();
        return mapExpressions(ctx.expression());
    }

    private List<NodeCase> buildCases(ZetarianoParser.SwitchStatementContext ctx) {
        List<NodeCase> cases = new ArrayList<>();
        if (ctx == null || ctx.CASE() == null || ctx.CASE().isEmpty()) return cases;
        int numCases = ctx.CASE().size();
        for (int i = 0; i < numCases; i++) {
            NodeExpression caseExpr = asExpression(visit(ctx.expression(i + 1)));
            ZetarianoParser.MainInstructionsContext instCtx = ctx.mainInstructions(i);
            List<ASTNode> insts = instCtx != null ? mapInstructions(instCtx.instruction()) : new ArrayList<>();
            NodeBlock block = new NodeBlock(insts, getLine(ctx), getColumn(ctx));
            cases.add(new NodeCase(caseExpr, block, getLine(ctx), getColumn(ctx)));
        }
        return cases;
    }

    private NodeBlock buildDefaultBlock(ZetarianoParser.SwitchStatementContext ctx) {
        if (ctx == null || ctx.DEFAULT() == null) return null;
        int defaultIdx = ctx.CASE() != null ? ctx.CASE().size() : 0;
        if (ctx.mainInstructions().size() > defaultIdx) {
            ZetarianoParser.MainInstructionsContext instCtx = ctx.mainInstructions(defaultIdx);
            List<ASTNode> insts = mapInstructions(instCtx.instruction());
            return new NodeBlock(insts, getLine(ctx), getColumn(ctx));
        }
        return new NodeBlock(new ArrayList<>(), getLine(ctx), getColumn(ctx));
    }

    private ASTNode buildForInit(ZetarianoParser.ForStatementContext ctx) {
        if (ctx.variableDeclaration() != null) {
            return visit(ctx.variableDeclaration());
        }
        if (ctx.assignment() != null && !ctx.assignment().isEmpty()) {
            int semiIndex1 = ctx.SEMICOLON(0).getSymbol().getTokenIndex();
            int assignIndex = ctx.assignment(0).getStart().getTokenIndex();
            if (assignIndex < semiIndex1) {
                return visit(ctx.assignment(0));
            }
        }
        return null;
    }

    private NodeExpression buildForCondition(ZetarianoParser.ForStatementContext ctx) {
        if (ctx.expression() != null && !ctx.expression().isEmpty()) {
            int semi1 = ctx.SEMICOLON(0).getSymbol().getTokenIndex();
            int semi2 = ctx.SEMICOLON(1).getSymbol().getTokenIndex();
            for (ZetarianoParser.ExpressionContext eCtx : ctx.expression()) {
                int tokenIdx = eCtx.getStart().getTokenIndex();
                if (tokenIdx > semi1 && tokenIdx < semi2) {
                    return asExpression(visit(eCtx));
                }
            }
        }
        return null;
    }

    private ASTNode buildForUpdate(ZetarianoParser.ForStatementContext ctx) {
        int semi2 = ctx.SEMICOLON(1).getSymbol().getTokenIndex();
        if (ctx.assignment() != null) {
            for (ZetarianoParser.AssignmentContext aCtx : ctx.assignment()) {
                if (aCtx.getStart().getTokenIndex() > semi2) {
                    return visit(aCtx);
                }
            }
        }
        if (ctx.expression() != null) {
            for (ZetarianoParser.ExpressionContext eCtx : ctx.expression()) {
                if (eCtx.getStart().getTokenIndex() > semi2) {
                    return visit(eCtx);
                }
            }
        }
        return null;
    }

    private ASTNode createBinary(ZetarianoParser.ExpressionContext leftCtx, String op, ZetarianoParser.ExpressionContext rightCtx, ParserRuleContext parent) {
        NodeExpression left = asExpression(visit(leftCtx));
        NodeExpression right = asExpression(visit(rightCtx));
        return new NodeBinaryExpression(left, op, right, getLine(parent), getColumn(parent));
    }

    private ASTNode createMethodCallNode(NodeLvalue fullLvalue, List<NodeExpression> args, int line, int column) {
        if (fullLvalue instanceof NodeIdentifier id) {
            return new NodeMethodCall(null, id.getName(), args, line, column);
        } else if (fullLvalue instanceof NodeFieldAccess fa) {
            return new NodeMethodCall(fa.getTarget(), fa.getFieldName(), args, line, column);
        }
        return new NodeMethodCall(fullLvalue, "", args, line, column);
    }

    private String extractType(ZetarianoParser.TypeContext ctx) {
        if (ctx == null) return "void";
        return ctx.getText();
    }

    private String parseString(String text) {
        if (text == null) return "";
        if (text.startsWith("\"") && text.endsWith("\"") && text.length() >= 2) {
            text = text.substring(1, text.length() - 1);
        }
        return text.replace("\\n", "\n").replace("\\t", "\t").replace("\\\"", "\"").replace("\\\\", "\\");
    }

    private char parseChar(String text) {
        if (text == null || text.length() < 3) return '\0';
        String inner = text.substring(1, text.length() - 1);
        if (inner.startsWith("\\") && inner.length() > 1) {
            return switch (inner.charAt(1)) {
                case 'n' -> '\n';
                case 't' -> '\t';
                case 'r' -> '\r';
                case '\'' -> '\'';
                case '\\' -> '\\';
                default -> inner.charAt(1);
            };
        }
        return inner.charAt(0);
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
}
