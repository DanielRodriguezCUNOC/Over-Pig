package com.piglatin.piglatin.application.services;

import com.piglatin.common.application.dto.CustomErrorDTO;
import com.piglatin.piglatin.domain.ast.principal.NodeProgram;
import com.piglatin.piglatin.domain.semantic.SymbolTableBuilder;
import com.piglatin.piglatin.domain.semantic.TypeChecker;
import com.piglatin.piglatin.domain.symboltable.SymbolTable;
import com.piglatin.piglatin.domain.types.TypeTable;
import com.piglatin.piglatin.domain.semantic.SemanticErrorReporter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PigLatinSemanticAnalyzer {

    private SymbolTable symbolTable;
    private TypeTable  typeTable;

    public List<CustomErrorDTO> analyze (NodeProgram ast) {
        if (ast == null) return List.of();
        symbolTable = new SymbolTable();
        typeTable = new TypeTable();
        SemanticErrorReporter errorReporter = new SemanticErrorReporter();

        //* Fisrt pass: symbol table, sequentiality, etc
        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder(typeTable, errorReporter);
        ast.accept(symbolTableBuilder);

        /**
         * Exctract the symbol table built
         * this is necessary for that TypeChecker needs the symbolTable built
        */
        symbolTable = symbolTableBuilder.getSymbolTable();

        //* Second pass: type checker, resole expressions
        TypeChecker typeChecker = new TypeChecker(typeTable, errorReporter, symbolTable);
        ast.accept(typeChecker);
        return errorReporter.getErrors();
    }
}
