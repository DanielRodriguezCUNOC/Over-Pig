package com.piglatin.common.application.dto;

/**
 * With this enum the ui can identifiessd the stage of error
 */
public enum CompilationStage {
    LEXICAL_ANALYSIS,
    SYNTACTIC_ANALYSIS,
    SEMANTIC_ANALYSIS,
    C3D_GENERATION,
    C_CODE_GENERATION
}
