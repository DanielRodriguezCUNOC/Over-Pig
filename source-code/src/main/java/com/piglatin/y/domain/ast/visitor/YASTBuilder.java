package com.piglatin.y.domain.ast.visitor;

import com.piglatin.y.domain.ast.expressions.*;
import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.principal.NodeProgram;
import com.piglatin.y.domain.ast.statements.*;
import com.piglatin.y.infrastructure.parser.generated.YParser;
import com.piglatin.y.infrastructure.parser.generated.YParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;

public class YASTBuilder extends YParserBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitProgram(YParser.ProgramContext ctx) {

        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        NodeProgram program = new NodeProgram(line, column);

        //* Visit all global declarations and add them to the program

        if (ctx.globalDeclaration() != null) {
            for (YParser.GlobalDeclarationContext gCtx : ctx.globalDeclaration()){
                program.addStructure((NodeStructureDefinition) visit(gCtx));
            }
        }

        if (ctx.functionDeclaration() != null) {
            for (YParser.FunctionDeclarationContext fCtx : ctx.functionDeclaration()){
                program.addFunction((NodeFunctionDefinition) visit(fCtx));
            }
        }
        return program;
    }

    //*********************
    //?     STRUCTS
    //*********************

    @Override
    public ASTNode visitGlobalStructure(YParser.GlobalStructureContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        String structName = ctx.ID().getText();

        List<NodeFieldDeclaration> fields = new ArrayList<>();

        if (ctx.structBlock() != null && ctx.structBlock().structField() != null) {
            for (YParser.StructFieldContext fCtx : ctx.structBlock().getRuleContexts(YParser.StructFieldContext.class)) {
                fields.add((NodeFieldDeclaration) visit(fCtx));
            }
        }
        return  new NodeStructureDefinition(structName, fields, line, column);
    }

    @Override
    public ASTNode visitFieldSimpleOrArray(YParser.FieldSimpleOrArrayContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String type = ctx.type().getText();
        String name = ctx.ID().getText();
        boolean isArray = ctx.LEFT_BRACKET() != null;
        Integer size = (isArray && ctx.INTEGER() != null) ? Integer.parseInt(ctx.INTEGER().getText()) : null;

        return new NodeFieldDeclaration(type, name, isArray, size, line, col);
    }

    //*********************
    //?     FUNCTIONS
    //*********************

    @Override
    public ASTNode visitFunctionDeclaration(YParser.FunctionDeclarationContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String funcName = ctx.ID().getText();

        List<NodeParameter> parameters = new ArrayList<>();
        if (ctx.parameterList() != null) {
            for (YParser.ParameterContext pCtx : ctx.parameterList().parameter()) {
                parameters.add((NodeParameter) visit(pCtx));
            }
        }

        String returnType = ctx.type() != null ? ctx.type().getText() : "void";
        NodeBlock body = (NodeBlock) visit(ctx.block());

        return new NodeFunctionDefinition(funcName, parameters, returnType, body, line, col);
    }

    @Override
    public ASTNode visitParamSimple(YParser.ParamSimpleContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new NodeParameter(ctx.type().getText(), ctx.ID().getText(), false, false, line, col);
    }

    @Override
    public ASTNode visitParamArray(YParser.ParamArrayContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new NodeParameter(ctx.type().getText(), ctx.ID().getText(), true, false, line, col);
    }

    @Override
    public ASTNode visitParamStruct(YParser.ParamStructContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new NodeParameter(ctx.type().getText(), ctx.ID().getText(), false, true, line, col);
    }

    //**********************8*********
    //?     BLOCKS/INSTRUCTIONS
    //********************************

    @Override
    public ASTNode visitBlock(YParser.BlockContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        NodeBlock block = new NodeBlock(line, col);

        if (ctx.instruction() != null) {
            for (YParser.InstructionContext iCtx : ctx.instruction()) {
                ASTNode instNode = visit(iCtx);
                if (instNode != null) {
                    block.addInstruction(instNode);
                }
            }
        }
        return block;
    }

    @Override
    public ASTNode visitInstructionAssignment(YParser.InstructionAssignmentContext ctx) {
        return visit(ctx.assignment());
    }

    @Override
    public ASTNode visitInstructionPrint(YParser.InstructionPrintContext ctx) {
        return visit(ctx.printStatement());
    }

    @Override
    public ASTNode visitInstructionIf(YParser.InstructionIfContext ctx) {
        return visit(ctx.ifStatement());
    }

    @Override
    public ASTNode visitInstructionChoose(YParser.InstructionChooseContext ctx) {
        return visit(ctx.chooseStatement());
    }

    @Override
    public ASTNode visitInstructionWhile(YParser.InstructionWhileContext ctx) {
        return visit(ctx.whileStatement());
    }

    @Override
    public ASTNode visitInstructionDoWhile(YParser.InstructionDoWhileContext ctx) {
        return visit(ctx.doWhileStatement());
    }

    @Override
    public ASTNode visitInstructionFor(YParser.InstructionForContext ctx) {
        return visit(ctx.forStatement());
    }

    @Override
    public ASTNode visitInstructionJump(YParser.InstructionJumpContext ctx) {
        return visit(ctx.jumpStatement());
    }

    @Override
    public ASTNode visitInstructionDeclaration(YParser.InstructionDeclarationContext ctx) {
        return visit(ctx.variableDeclaration());
    }

    @Override
    public ASTNode visitInstructionArrayDeclaration(YParser.InstructionArrayDeclarationContext ctx) {
        return visit(ctx.arrayDeclaration());
    }

    @Override
    public ASTNode visitInstructionExpression(YParser.InstructionExpressionContext ctx) {
        return visit(ctx.expression());
    }

    //**********************8*********
    //?     DECLARATIONS/ASSIGNMENTS
    //********************************

    @Override
    public ASTNode visitVariableDeclaration(YParser.VariableDeclarationContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String type = ctx.type().getText();
        String name = ctx.ID().getText();
        ASTNode initializer = ctx.expression() != null ? visit(ctx.expression()) : null;

        return new NodeVariableDeclaration(type, name, initializer, line, col);
    }

    @Override
    public ASTNode visitArrayDeclaration(YParser.ArrayDeclarationContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String type = ctx.type().getText();
        String name = ctx.ID().getText();
        List<ASTNode> dimensions = new ArrayList<>();
        ASTNode initializer = null;

        if (ctx.ASSIGN() !=null) {
            int lastIndex = ctx.expression().size() - 1;
            for (int i = 0; i < lastIndex; i++) {
                dimensions.add(visit(ctx.expression(i)));
            }
            initializer = visit(ctx.expression(lastIndex));
        }else{

            for (YParser.ExpressionContext eCtx : ctx.expression()) {
                dimensions.add(visit(eCtx));
            }
        }
        return new NodeArrayDeclaration(type, name, dimensions, initializer, line, col);
    }

    @Override
    public ASTNode visitAssigmentNormal(YParser.AssigmentNormalContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ASTNode lvalue = visit(ctx.lvalue());
        ASTNode value = visit(ctx.expression());

        return new NodeAssignment(lvalue, "=", value, line, col);
    }

    @Override
    public ASTNode visitAssigmentIncDec(YParser.AssigmentIncDecContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ASTNode lvalue = visit(ctx.lvalue());
        String operator = ctx.INC() != null ? "++" : "--";

        return new NodeAssignment(lvalue, operator, null, line, col);
    }


    //**********************8*********
    //?     CONTROL STRUCTURES
    //********************************

    @Override
    public ASTNode visitPrintStatement(YParser.PrintStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        List<ASTNode> expressions = new ArrayList<>();

        for (YParser.ExpressionContext eCtx : ctx.expression()) {
            expressions.add(visit(eCtx));
        }

        return new NodePrint(expressions, line, col);
    }

    @Override
    public ASTNode visitIfStatement(YParser.IfStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        // Condición y bloque principal del IF
        ASTNode condition = (ctx.expression() != null && !ctx.expression().isEmpty())
                ? visit(ctx.expression(0)) : null;
        NodeBlock thenBlock = (ctx.block() != null && !ctx.block().isEmpty())
                ? (NodeBlock) visit(ctx.block(0)) : null;

        List<ElseIfClause> elseIfClauses = new ArrayList<>();
        int sinoCount = ctx.SINO() != null ? ctx.SINO().size() : 0;

        // Validar el tamaño máximo de expresiones disponibles para los 'sino'
        int maxExprs = ctx.expression() != null ? ctx.expression().size() : 0;
        int maxBlocks = ctx.block() != null ? ctx.block().size() : 0;

        for (int i = 0; i < sinoCount; i++) {
            int exprIdx = i + 1;
            int blockIdx = i + 1;

            ASTNode sinoCond = (exprIdx < maxExprs) ? visit(ctx.expression(exprIdx)) : null;
            NodeBlock sinoBlock = (blockIdx < maxBlocks) ? (NodeBlock) visit(ctx.block(blockIdx)) : null;

            if (sinoCond != null || sinoBlock != null) {
                elseIfClauses.add(new ElseIfClause(sinoCond, sinoBlock));
            }
        }

        NodeBlock elseBlock = null;
        if (ctx.CONTRARIO() != null && maxBlocks > 0) {
            elseBlock = (NodeBlock) visit(ctx.block(maxBlocks - 1));
        }

        return new NodeIf(condition, thenBlock, elseIfClauses, elseBlock, line, col);
    }

    @Override
    public ASTNode visitChooseStatement(YParser.ChooseStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        int maxExprs = ctx.expression() != null ? ctx.expression().size() : 0;
        int maxBlocks = ctx.block() != null ? ctx.block().size() : 0;

        ASTNode targetExpr = maxExprs > 0 ? visit(ctx.expression(0)) : null;
        List<NodeChooseCase> cases = new ArrayList<>();

        int exprIdx = 1;
        int blockIdx = 0;
        int casoCount = ctx.CASO() != null ? ctx.CASO().size() : 0;

        for (int i = 0; i < casoCount; i++) {
            ASTNode caseExpr = (exprIdx < maxExprs) ? visit(ctx.expression(exprIdx++)) : null;
            NodeBlock caseBlock = (blockIdx < maxBlocks) ? (NodeBlock) visit(ctx.block(blockIdx++)) : null;
            cases.add(new NodeChooseCase(caseExpr, caseBlock, false, line, col));
        }

        NodeChooseCase defaultCase = null;
        if (ctx.SIEMPRE() != null && blockIdx < maxBlocks) {
            NodeBlock defaultBlock = (NodeBlock) visit(ctx.block(blockIdx));
            defaultCase = new NodeChooseCase(null, defaultBlock, true, line, col);
        }

        return new NodeChoose(targetExpr, cases, defaultCase, line, col);
    }

    @Override
    public ASTNode visitWhileStatement(YParser.WhileStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ASTNode condition = visit(ctx.expression());
        NodeBlock body = (NodeBlock) visit(ctx.block());

        return new NodeWhile(condition, body, line, col);
    }

    @Override
    public ASTNode visitDoWhileStatement(YParser.DoWhileStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        NodeBlock body = (NodeBlock) visit(ctx.block());
        ASTNode condition = visit(ctx.expression());

        return new NodeDoWhile(body, condition, line, col);
    }

    @Override
    public ASTNode visitForStatement(YParser.ForStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ASTNode init = null;
        if (ctx.variableDeclaration() != null) {
            init = visit(ctx.variableDeclaration());
        } else if (ctx.assignment(0) != null) {
            init = visit(ctx.assignment(0));
        }

        ASTNode condition = ctx.expression() != null ? visit(ctx.expression()) : null;

        ASTNode increment = null;
        if (ctx.variableDeclaration() != null && ctx.assignment(0) != null) {
            increment = visit(ctx.assignment(0));
        } else if (ctx.variableDeclaration() == null && ctx.assignment().size() > 1) {
            increment = visit(ctx.assignment(1));
        }

        NodeBlock body = (NodeBlock) visit(ctx.block());

        return new NodeFor(init, condition, increment, body, line, col);
    }


    //**********************8*********
    //?     JUMP STATEMENTS
    //********************************


    @Override
    public ASTNode visitJumpBreak(YParser.JumpBreakContext ctx) {
        return new NodeBreak(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
    }

    @Override
    public ASTNode visitJumpContinue(YParser.JumpContinueContext ctx) {
        return new NodeContinue(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
    }

    @Override
    public ASTNode visitJumpReturn(YParser.JumpReturnContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ASTNode returnExpr = ctx.expression() != null ? visit(ctx.expression()) : null;

        return new NodeReturn(returnExpr, line, col);
    }

    //**********************8*********
    //?     JUMP STATEMENTS
    //********************************

    @Override
    public ASTNode visitLvalue(YParser.LvalueContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        if (ctx.DOT() != null) {
            ASTNode target = visit(ctx.lvalue());
            String fieldName = ctx.ID().getText();
            return new NodeFieldAccess(target, fieldName, line, col);
        } else if (ctx.LEFT_BRACKET() != null) {
            ASTNode target = visit(ctx.lvalue());
            ASTNode index = visit(ctx.expression());
            return new NodeIndexAccess(target, index, line, col);
        } else {
            return new NodeIdentifier(ctx.ID().getText(), line, col);
        }
    }

    @Override
    public ASTNode visitExprLogical(YParser.ExprLogicalContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ASTNode left = visit(ctx.expression(0));
        ASTNode right = visit(ctx.expression(1));
        String op = ctx.getChild(1).getText();

        return new NodeBinaryOperation(op, left, right, line, col);
    }

    @Override
    public ASTNode visitExprRelational(YParser.ExprRelationalContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ASTNode left = visit(ctx.expression(0));
        ASTNode right = visit(ctx.expression(1));
        String op = ctx.getChild(1).getText();

        return new NodeBinaryOperation(op, left, right, line, col);
    }

    @Override
    public ASTNode visitExprAdditive(YParser.ExprAdditiveContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ASTNode left = visit(ctx.expression(0));
        ASTNode right = visit(ctx.expression(1));
        String op = ctx.getChild(1).getText();

        return new NodeBinaryOperation(op, left, right, line, col);
    }

    @Override
    public ASTNode visitExprMultiplicative(YParser.ExprMultiplicativeContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ASTNode left = visit(ctx.expression(0));
        ASTNode right = visit(ctx.expression(1));
        String op = ctx.getChild(1).getText();

        return new NodeBinaryOperation(op, left, right, line, col);
    }

    @Override
    public ASTNode visitExprUnary(YParser.ExprUnaryContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String op = ctx.getChild(0).getText();
        ASTNode operand = visit(ctx.expression());

        return new NodeUnaryOperation(op, operand, line, col);
    }

    @Override
    public ASTNode visitExprPrimary(YParser.ExprPrimaryContext ctx) {
        return visit(ctx.primary());
    }

    @Override
    public ASTNode visitExprArrayLiteral(YParser.ExprArrayLiteralContext ctx) {
        return visit(ctx.arrayLiteral());
    }

    @Override
    public ASTNode visitArrayLiteral(YParser.ArrayLiteralContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        List<ASTNode> elements = new ArrayList<>();

        for (YParser.ExpressionContext eCtx : ctx.expression()) {
            elements.add(visit(eCtx));
        }

        return new NodeArrayLiteral(elements, line, col);
    }


    //**********************8*********
    //?     PRIMARY ELEMENTS/LITERALS
    //********************************

    @Override
    public ASTNode visitPrimaryLiteral(YParser.PrimaryLiteralContext ctx) {
        return visit(ctx.literal());
    }

    @Override
    public ASTNode visitPrimaryLvalue(YParser.PrimaryLvalueContext ctx) {
        return visit(ctx.lvalue());
    }

    @Override
    public ASTNode visitPrimaryFunctionCall(YParser.PrimaryFunctionCallContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String funcName = ctx.ID().getText();
        List<ASTNode> arguments = new ArrayList<>();

        if (ctx.argumentList() != null) {
            for (YParser.ExpressionContext eCtx : ctx.argumentList().expression()) {
                arguments.add(visit(eCtx));
            }
        }

        return new NodeFunctionCall(funcName, arguments, line, col);
    }

    @Override
    public ASTNode visitPrimaryRead(YParser.PrimaryReadContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new NodeRead(line, col);
    }

    @Override
    public ASTNode visitPrimaryParen(YParser.PrimaryParenContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public ASTNode visitLiteral(YParser.LiteralContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        if (ctx.INTEGER() != null) {
            return new NodeIntegerLiteral(Integer.parseInt(ctx.INTEGER().getText()), line, col);
        } else if (ctx.FLOAT() != null) {
            return new NodeFloatLiteral(Double.parseDouble(ctx.FLOAT().getText()), line, col);
        } else if (ctx.CHAR() != null) {
            String raw = ctx.CHAR().getText();
            char charVal = raw.length() >= 3 ? raw.charAt(1) : '\0';
            return new NodeCharLiteral(charVal, line, col);
        } else if (ctx.STRING() != null) {
            String raw = ctx.STRING().getText();
            String strVal = raw.substring(1, raw.length() - 1);
            return new NodeStringLiteral(strVal, line, col);
        } else if (ctx.VERDADERO() != null) {
            return new NodeBooleanLiteral(true, line, col);
        } else if (ctx.FALSO() != null) {
            return new NodeBooleanLiteral(false, line, col);
        }

        return null;
    }
}
