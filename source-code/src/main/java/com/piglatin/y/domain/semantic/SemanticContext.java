package com.piglatin.y.domain.semantic;

import com.piglatin.common.domain.cfg.ControlFlowGraph;
import com.piglatin.y.domain.ast.principal.ASTNode;
import com.piglatin.y.domain.symboltable.YSymbolTable;
import com.piglatin.y.domain.types.YTypeTable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class SemanticContext {
    private final YSymbolTable symbolTable;
    private final YTypeTable typeTable;
    private final YConstantFolder constantFolder;
    private final Map<String, ControlFlowGraph<ASTNode>> functionCFGs;
    private final SemanticErrorReporter errorReporter;

    public boolean hasErrors() {
        return errorReporter != null && errorReporter.hasFatalErrors();
    }
}