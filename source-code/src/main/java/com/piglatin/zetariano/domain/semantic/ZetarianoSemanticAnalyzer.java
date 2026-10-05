package com.piglatin.zetariano.domain.semantic;

import com.piglatin.common.domain.cfg.ControlFlowGraph;
import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.principal.NodeProgram;
import com.piglatin.zetariano.domain.cfg.ZetarianoCFGBuilder;
import com.piglatin.zetariano.domain.symboltable.ZetarianoSymbolTable;
import com.piglatin.zetariano.domain.types.ZetarianoTypeTable;

import java.util.Collections;
import java.util.Map;

public class ZetarianoSemanticAnalyzer {

    private final String fileName;

    public ZetarianoSemanticAnalyzer(String fileName) {
        this.fileName = (fileName == null || fileName.isBlank()) ? "<unknown>" : fileName;
    }

    public SemanticContext analyze(NodeProgram program) {
        return analyze(program, new ZetarianoTypeTable(), new ZetarianoSymbolTable());
    }

    public SemanticContext analyze(NodeProgram program, ZetarianoTypeTable typeTable, ZetarianoSymbolTable symbolTable) {

        SemanticErrorReporter reporter = new SemanticErrorReporter(fileName);

        ZetarianoSymbolTableBuilder symbolBuilder = new ZetarianoSymbolTableBuilder(symbolTable, typeTable, reporter);
        program.accept(symbolBuilder);

        if (reporter.hasFatalErrors()) {
            return new SemanticContext(
                    symbolBuilder.getSymbolTable(),
                    typeTable,
                    null,
                    Collections.emptyMap(),
                    reporter);
        }

        ZetarianoTypeChecker typeChecker = new ZetarianoTypeChecker(
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

        ZetarianoCFGBuilder cfgBuilder = new ZetarianoCFGBuilder();
        Map<String, ControlFlowGraph<ASTNode>> cfgs = cfgBuilder.build(program);

        ZetarianoConstantFolder constantFolder = new ZetarianoConstantFolder(reporter);

        return new SemanticContext(
                symbolBuilder.getSymbolTable(),
                typeTable,
                constantFolder,
                cfgs,
                reporter);
    }
}