package com.piglatin.y.domain.semantic;

import com.piglatin.common.domain.cfg.ControlFlowGraph;
import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.ast.principal.NodeProgram;
import com.piglatin.y.domain.cfg.YCFGBuilder;
import com.piglatin.y.domain.symboltable.YSymbolTable;
import com.piglatin.y.domain.types.YTypeTable;

import java.util.Collections;
import java.util.Map;

public class YSemanticAnalyzer {

    private final String fileName;

    public YSemanticAnalyzer(String fileName) {
        this.fileName = (fileName == null || fileName.isBlank()) ? "<unknown>" : fileName;
    }

    public YSemanticAnalyzer() {
        this("<unknown>");
    }

    public SemanticContext analyze(NodeProgram program) {
        SemanticErrorReporter reporter = new SemanticErrorReporter(fileName);
        YTypeTable typeTable = new YTypeTable();

        if (program == null) {
            return new SemanticContext(
                    new YSymbolTable(),
                    typeTable,
                    null,
                    Collections.emptyMap(),
                    reporter);
        }

        YSymbolTableBuilder symbolBuilder = new YSymbolTableBuilder(typeTable, reporter);
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
        YTypeChecker typeChecker = new YTypeChecker(
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
        YCFGBuilder cfgBuilder = new YCFGBuilder();
        program.accept(cfgBuilder);
        Map<String, ControlFlowGraph<ASTNode>> functionCFGs = cfgBuilder.getFunctionCFGs();

        // Plegado de Constantes (opcional)
        YConstantFolder constantFolder = new YConstantFolder(reporter);
        program.accept(constantFolder);

        return new SemanticContext(
                symbolBuilder.getSymbolTable(),
                typeTable,
                constantFolder,
                functionCFGs,
                reporter);
    }
}