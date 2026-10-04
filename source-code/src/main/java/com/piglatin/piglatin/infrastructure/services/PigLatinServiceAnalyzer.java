package com.piglatin.piglatin.infrastructure.services;

import com.piglatin.common.application.dto.CustomErrorDTO;
import com.piglatin.piglatin.infrastructure.parser.generated.LatinLexer;
import com.piglatin.piglatin.infrastructure.parser.generated.LatinParser;
import com.piglatin.piglatin.application.dto.ParserResultDTO;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public class PigLatinServiceAnalyzer {

    public ParserResultDTO executeAnalysis(String sourceCode) {
        List<CustomErrorDTO> errorsList = new ArrayList<>();

        CharStream input = CharStreams.fromString(sourceCode != null ? sourceCode : "");

        //* Lexer
        LatinLexer lexer = new LatinLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg, RecognitionException e) {
                errorsList.add(new CustomErrorDTO(line, charPositionInLine, "Lexical Error: " + msg));
            }
        });

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        //* Parser
        LatinParser parser = new LatinParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg, RecognitionException e) {
                errorsList.add(new CustomErrorDTO( line, charPositionInLine, "Syntax Error: " + msg));
            }
        });

        //* Generate ParseTree
        ParseTree parseTree = null;
        try {
            parseTree = parser.program();
        } catch (Exception e) {
            errorsList.add(new CustomErrorDTO( 0, 0, "Fatal Error: " + e.getMessage()));
        }

        return new ParserResultDTO(parseTree, errorsList, null);
    }
}