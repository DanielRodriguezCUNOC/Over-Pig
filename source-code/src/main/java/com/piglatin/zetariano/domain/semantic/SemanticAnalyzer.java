package com.piglatin.zetariano.domain.semantic;

import com.piglatin.common.domain.cfg.ControlFlowGraph;
import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.principal.NodeProgram;
import com.piglatin.zetariano.domain.cfg.CFGBuilder;
import com.piglatin.zetariano.domain.symboltable.SymbolTable;
import com.piglatin.zetariano.domain.types.TypeTable;

import java.util.Collections;
import java.util.Map;

public class SemanticAnalyzer {

    private final String fileName;

    public SemanticAnalyzer(String fileName) {
        this.fileName = (fileName == null || fileName.isBlank()) ? "<unknown>" : fileName;
    }

    public SemanticContext analyze(NodeProgram program) {
        return analyze(program, new TypeTable(), new SymbolTable());
    }

    public SemanticContext analyze(NodeProgram program, TypeTable typeTable, SymbolTable symbolTable) {

        SemanticErrorReporter reporter = new SemanticErrorReporter(fileName);

        SymbolTableBuilder symbolBuilder = new SymbolTableBuilder(symbolTable, typeTable, reporter);
        program.accept(symbolBuilder);

        if (reporter.hasFatalErrors()) {
            return new SemanticContext(
                    symbolBuilder.getSymbolTable(),
                    typeTable,
                    null,
                    Collections.emptyMap(),
                    reporter);
        }

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

        CFGBuilder cfgBuilder = new CFGBuilder();
        Map<String, ControlFlowGraph<ASTNode>> cfgs = cfgBuilder.build(program);

        ConstantFolder constantFolder = new ConstantFolder(reporter);

        return new SemanticContext(
                symbolBuilder.getSymbolTable(),
                typeTable,
                constantFolder,
                cfgs,
                reporter);
    }
}