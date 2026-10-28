package com.piglatin.piglatin.application.services;

import com.piglatin.common.application.dto.CustomErrorDTO;
import com.piglatin.piglatin.domain.ast.principal.NodeProgram;
import com.piglatin.piglatin.domain.semantic.ImportedSymbols;
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

    /** Without imports: no external class, struct or function is visible. */
    public List<CustomErrorDTO> analyze (NodeProgram ast) {
        return analyze(ast, ImportedSymbols.NONE);
    }

    public List<CustomErrorDTO> analyze (NodeProgram ast, ImportedSymbols imported) {
        if (ast == null) return List.of();
        ImportedSymbols visible = imported != null ? imported : ImportedSymbols.NONE;

        symbolTable = new PigLatinSymbolTable();
        typeTable = new PigLatinTypeTable();
        visible.classNames().forEach(typeTable::registerType);
        visible.structNames().forEach(typeTable::registerType);
        visible.inheritance().forEach(typeTable::registerInheritance);
        SemanticErrorReporter errorReporter = new SemanticErrorReporter();

        //* Fisrt pass: symbol table, sequentiality, etc
        PigLatinSymbolTableBuilder symbolTableBuilder =
                new PigLatinSymbolTableBuilder(typeTable, errorReporter, visible);
        ast.accept(symbolTableBuilder);

        symbolTable = symbolTableBuilder.getSymbolTable();

        //* Second pass: type checker, resole expressions
        PigLatinTypeChecker typeChecker =
                new PigLatinTypeChecker(typeTable, errorReporter, symbolTable, visible);
        ast.accept(typeChecker);
        return errorReporter.getErrors();
    }
}