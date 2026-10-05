package com.piglatin.piglatin.application.services;

import com.piglatin.common.application.dto.CustomErrorDTO;
import com.piglatin.piglatin.domain.ast.principal.NodeProgram;
import com.piglatin.piglatin.domain.semantic.PigLatinSymbolTableBuilder;
import com.piglatin.piglatin.domain.semantic.PigLatinTypeChecker;
import com.piglatin.piglatin.domain.symboltable.PigLatinSymbolTable;
import com.piglatin.piglatin.domain.types.PigLatinTypeTable;
import com.piglatin.piglatin.domain.semantic.SemanticErrorReporter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PigLatinSemanticAnalyzer {

    private PigLatinSymbolTable symbolTable;
    private PigLatinTypeTable typeTable;

    public List<CustomErrorDTO> analyze (NodeProgram ast) {
        if (ast == null) return List.of();
        symbolTable = new PigLatinSymbolTable();
        typeTable = new PigLatinTypeTable();
        SemanticErrorReporter errorReporter = new SemanticErrorReporter();

        //* Fisrt pass: symbol table, sequentiality, etc
        PigLatinSymbolTableBuilder symbolTableBuilder = new PigLatinSymbolTableBuilder(typeTable, errorReporter);
        ast.accept(symbolTableBuilder);

        /**
         * Exctract the symbol table built
         * this is necessary for that TypeChecker needs the symbolTable built
        */
        symbolTable = symbolTableBuilder.getSymbolTable();

        //* Second pass: type checker, resole expressions
        PigLatinTypeChecker typeChecker = new PigLatinTypeChecker(typeTable, errorReporter, symbolTable);
        ast.accept(typeChecker);
        return errorReporter.getErrors();
    }
}
