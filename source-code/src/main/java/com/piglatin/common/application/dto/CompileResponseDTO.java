package com.piglatin.common.application.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class have the compilation result
 * Contains list of errors, etc
 */
@Getter
@Setter
public class CompileResponseDTO {
    private boolean success;
    private List<CompilationErrorDTO> errors;
    private GeneretedCodeDTO generatedCode;
    private String executionOutput;
    private long compilationTimeMs;
    private List<SymbolInfoDTO> symbols;
    private List<ScopeInfoDTO> scopes;
    private List<TypeInfoDTO> types;

    public CompileResponseDTO(boolean success, List<CompilationErrorDTO> errors, GeneretedCodeDTO generatedCode, String executionOutput, long compilationTimeMs) {
        this.success = success;
        this.errors = errors != null ? errors : new ArrayList<>();
        this.generatedCode = generatedCode;
        this.executionOutput = executionOutput;
        this.compilationTimeMs = compilationTimeMs;
    }

    public CompileResponseDTO() {
        this.errors = new ArrayList<>();
    }
}
