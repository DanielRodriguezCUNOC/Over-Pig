package com.piglatin.y.domain.semantic;

import com.piglatin.common.domain.cfg.ControlFlowGraph;
import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.principal.NodeProgram;
import com.piglatin.y.domain.cfg.CFGBuilder;
import com.piglatin.y.domain.symboltable.SymbolTable;
import com.piglatin.y.domain.types.TypeTable;

import java.util.Collections;
import java.util.Map;

public class SemanticAnalyzer {

    private final String fileName;

    public SemanticAnalyzer(String fileName) {
        this.fileName = (fileName == null || fileName.isBlank()) ? "<unknown>" : fileName;
    }

    public SemanticAnalyzer() {
        this("<unknown>");
    }

    public SemanticContext analyze(NodeProgram program) {
        SemanticErrorReporter reporter = new SemanticErrorReporter(fileName);
        TypeTable typeTable = new TypeTable();

        if (program == null) {
            return new SemanticContext(
                    new SymbolTable(),
                    typeTable,
                    null,
                    Collections.emptyMap(),
                    reporter);
        }

        SymbolTableBuilder symbolBuilder = new SymbolTableBuilder(typeTable, reporter);
        program.accept(symbolBuilder);

        if (reporter.hasFatalErrors()) {
            return new SemanticContext(
                    symbolBuilder.getSymbolTable(),
                    typeTable,
                    null,
                    Collections.emptyMap(),
                    reporter);
        }

        // Chequeo de Tipos
        TypeChecker typeChecker = new TypeChecker(
                symbolBuilder.getSymbolTable(),
                typeTable,
                reporter);
        program.accept(typeChecker);

        if (reporter.hasFatalErrors()) {
            return new SemanticContext(
                    symbolBuilder.getSymbolTable(),
                    typeTable,
                    null,
                    Collections.emptyMap(),
                    reporter);
        }

        // Generación del Control Flow Graph por cada función
        CFGBuilder cfgBuilder = new CFGBuilder();
        program.accept(cfgBuilder);
        Map<String, ControlFlowGraph<ASTNode>> functionCFGs = cfgBuilder.getFunctionCFGs();

        // Plegado de Constantes (opcional)
        ConstantFolder constantFolder = new ConstantFolder(reporter);
        program.accept(constantFolder);

        return new SemanticContext(
                symbolBuilder.getSymbolTable(),
                typeTable,
                constantFolder,
                functionCFGs,
                reporter);
    }
}