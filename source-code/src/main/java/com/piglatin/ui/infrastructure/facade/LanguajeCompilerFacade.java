package com.piglatin.ui.infrastructure.facade;

import com.piglatin.common.application.dto.CompilationMode;
import com.piglatin.ui.infrastructure.facade.dto.CompilationResultDTO;

public interface LanguajeCompilerFacade {

    CompilationResultDTO compileCode(String sourceCode, String fileExtension);

    CompilationResultDTO compileCode(
            String sourceCode,
            String fileExtension,
            String projectDirectory,
            String currentFileName,
            CompilationMode mode
    );

    CompilationResultDTO generateCode(
            String sourceCode,
            String fileExtension,
            String projectDirectory,
            String currentFileName
    );
}
