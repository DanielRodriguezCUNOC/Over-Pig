package com.piglatin.ui.infrastructure.facade;

import com.piglatin.common.application.dto.*;
import com.piglatin.common.application.ports.input.CompilerUseCase;
import com.piglatin.common.application.ports.input.ExecutionCCode;
import com.piglatin.ui.infrastructure.facade.dto.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LanguageCompilerFacadeImpl implements LanguajeCompilerFacade {

    private final CompilerUseCase compilerUseCase;
    private final ExecutionCCode executionCCode;

    public LanguageCompilerFacadeImpl(CompilerUseCase compilerUseCase, ExecutionCCode executionCCode) {
        this.compilerUseCase = compilerUseCase;
        this.executionCCode = executionCCode;
    }

    @Override
    public CompilationResultDTO compileCode(String sourceCode, String fileExtension) {
        return compileCode(sourceCode, fileExtension, null, "file." + fileExtension, CompilationMode.VALIDATE_ONLY);
    }

    @Override
    public CompilationResultDTO compileCode(
            String sourceCode,
            String fileExtension,
            String projectDirectory,
            String currentFileName,
            CompilationMode mode
    ) {
        String ext = fileExtension;
        if (ext.startsWith(".")) {
            ext = ext.substring(1);
        }
        LanguageType languageType = LanguageType.fromExtension(ext);

        CompileRequestDTO request = new CompileRequestDTO();
        request.setSourceCode(sourceCode);
        request.setFileName(currentFileName);
        request.setCurrentFileName(currentFileName);
        request.setProjectDirectory(projectDirectory);
        request.setLanguageType(languageType);
        request.setMode(mode);

        CompileResponseDTO response = compilerUseCase.compile(request);

        // Mapear Errores
        List<ErrorDTO> errorDTOs = new ArrayList<>();
        if (response.getErrors() != null) {
            for (CompilationErrorDTO err : response.getErrors()) {
                errorDTOs.add(new ErrorDTO(
                        err.getLine(),
                        err.getColumn(),
                        err.getStage() != null ? err.getStage().name() : "ERROR",
                        err.getMessage()
                ));
            }
        }

        // Mapear Símbolos
        List<SymbolDTO> symbolDTOs = new ArrayList<>();
        if (response.getSymbols() != null) {
            for (SymbolInfoDTO s : response.getSymbols()) {
                symbolDTOs.add(new SymbolDTO(
                        s.getId(),
                        s.getName(),
                        s.getType(),
                        s.getScope(),
                        s.getLine()
                ));
            }
        }

        // Mapear Tipos
        List<TypeDTO> typeDTOs = new ArrayList<>();
        if (response.getTypes() != null) {
            for (TypeInfoDTO t : response.getTypes()) {
                typeDTOs.add(new TypeDTO(
                        t.getTypeName(),
                        t.getCategory(),
                        t.getSize()
                ));
            }
        }

        // Mapear Ámbitos
        List<ScopeDTO> scopeDTOs = new ArrayList<>();
        if (response.getScopes() != null) {
            for (ScopeInfoDTO sc : response.getScopes()) {
                scopeDTOs.add(new ScopeDTO(
                        sc.getScopeId(),
                        sc.getParentScope(),
                        sc.getDescription()
                ));
            }
        }

        return new CompilationResultDTO(
                response.isSuccess(),
                symbolDTOs,
                typeDTOs,
                scopeDTOs,
                errorDTOs,
                response.getExecutionOutput()
        );
    }

    @Override
    public Process startInteractiveExecution(
            String sourceCode,
            String fileExtension,
            String projectDirectory,
            String currentFileName
    ) throws IOException {

        CompilationResultDTO result = compileCode(
                sourceCode, fileExtension, projectDirectory, currentFileName,
                CompilationMode.C_CODE_ONLY);

        if (!result.isSuccessful()) {
            throw new IOException("Compilación falló. Revisa la tabla de errores.");
        }

        String baseName = currentFileName;
        if (baseName.contains(".")) {
            baseName = baseName.substring(0, baseName.lastIndexOf('.'));
        }
        Path cFile = Path.of(projectDirectory, baseName + ".c");
        if (!Files.exists(cFile)) {
            throw new IOException("No se generó " + cFile);
        }
        String cCode = Files.readString(cFile);

        return executionCCode.startInteractive(cCode, "main", projectDirectory);
    }
}
