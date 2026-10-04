package com.piglatin.ui.infrastructure.facade;

import com.piglatin.common.application.dto.CompilationMode;
import com.piglatin.ui.infrastructure.facade.dto.CompilationResultDTO;

import java.io.IOException;

public interface LanguajeCompilerFacade {

    CompilationResultDTO compileCode(String sourceCode, String fileExtension);

    CompilationResultDTO compileCode(
            String sourceCode,
            String fileExtension,
            String projectDirectory,
            String currentFileName,
            CompilationMode mode
    );

    Process startInteractiveExecution(
            String sourceCode,
            String fileExtension,
            String projectDirectory,
            String currentFileName
    ) throws IOException;
}