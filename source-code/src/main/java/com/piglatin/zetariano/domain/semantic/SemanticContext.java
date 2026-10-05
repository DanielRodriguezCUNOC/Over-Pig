package com.piglatin.zetariano.domain.semantic;

import com.piglatin.common.application.dto.CompilationErrorDTO;
import com.piglatin.common.domain.cfg.ControlFlowGraph;
import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.symboltable.ZetarianoSymbolTable;
import com.piglatin.zetariano.domain.types.ZetarianoTypeTable;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class SemanticContext {

    private final ZetarianoSymbolTable symbolTable;
    private final ZetarianoTypeTable typeTable;
    private final ZetarianoConstantFolder constantFolder;
    private final Map<String, ControlFlowGraph<ASTNode>> methodGraphs;
    private final SemanticErrorReporter errorReporter;
    private final boolean success;

    public SemanticContext(ZetarianoSymbolTable symbolTable,
                           ZetarianoTypeTable typeTable,
                           ZetarianoConstantFolder constantFolder,
                           Map<String, ControlFlowGraph<ASTNode>> methodGraphs,
                           SemanticErrorReporter errorReporter) {
        this.symbolTable = symbolTable;
        this.typeTable = typeTable;
        this.constantFolder = constantFolder;
        this.methodGraphs = methodGraphs;
        this.errorReporter = errorReporter;
        this.success = !errorReporter.hasFatalErrors();
    }

    public List<CompilationErrorDTO> getErrors() {
        return errorReporter.getErrors();
    }

    public boolean hasErrors() {
        return errorReporter.hasFatalErrors();
    }
}