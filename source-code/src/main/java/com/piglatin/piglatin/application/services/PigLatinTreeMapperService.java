package com.piglatin.piglatin.application.services;

import com.piglatin.piglatin.domain.ast.principal.NodeProgram;
import com.piglatin.piglatin.domain.ast.visitor.ASTBuilder;
import com.piglatin.piglatin.application.dto.ParserResultDTO;

public class PigLatinTreeMapperService {

    /**
     * Builde the specific AST of PigLatin from ANTLR ParseTree
     */
    public NodeProgram buildAST(ParserResultDTO parserResult) {
        if (parserResult == null || parserResult.getParseTree() == null) {
            return null;
        }
        ASTBuilder builder = new ASTBuilder();
        return (NodeProgram) builder.visit(parserResult.getParseTree());
    }
}
