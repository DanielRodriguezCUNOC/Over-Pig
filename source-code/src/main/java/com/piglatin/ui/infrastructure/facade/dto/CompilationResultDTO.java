package com.piglatin.ui.infrastructure.facade.dto;

import java.util.List;

/**
 * Data Transfer Object to pass analysis results from Backend to UI.
 */
public record CompilationResultDTO(
        boolean isSuccessful,
        List<SymbolDTO> symbols,
        List<TypeDTO> types,
        List<ScopeDTO> scopes,
        List<ErrorDTO> errors,
        String executionOutput
) {}