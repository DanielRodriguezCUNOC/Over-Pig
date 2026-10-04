package com.piglatin.zetariano.domain.semantic;

import com.piglatin.common.application.dto.CompilationErrorDTO;
import com.piglatin.common.domain.cfg.ControlFlowGraph;
import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.symboltable.SymbolTable;
import com.piglatin.zetariano.domain.types.TypeTable;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class SemanticContext {

    private final SymbolTable symbolTable;
    private final TypeTable typeTable;
    private final ConstantFolder constantFolder;
    private final Map<String, ControlFlowGraph<ASTNode>> methodGraphs;
    private final SemanticErrorReporter errorReporter;
    private final boolean success;

    public SemanticContext(SymbolTable symbolTable,
                           TypeTable typeTable,
                           ConstantFolder constantFolder,
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