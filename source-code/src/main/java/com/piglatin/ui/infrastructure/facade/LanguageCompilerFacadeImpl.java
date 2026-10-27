package com.piglatin.ui.infrastructure.facade;

import com.piglatin.common.application.dto.*;
import com.piglatin.common.application.ports.input.CompilerUseCase;
import com.piglatin.common.application.ports.input.ExecutionCCode;
import com.piglatin.ui.infrastructure.facade.dto.*;

import java.util.ArrayList;
import java.util.List;

public class LanguageCompilerFacadeImpl implements LanguajeCompilerFacade {

    private final CompilerUseCase compilerUseCase;
    // Aqui deberia de ir la interfaz de ejecucion de RISC-V XD

    public LanguageCompilerFacadeImpl(CompilerUseCase compilerUseCase) {
        this.compilerUseCase = compilerUseCase;
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

        GeneretedCodeDTO generated = response.getGeneratedCode();

        return new CompilationResultDTO(
                response.isSuccess(),
                symbolDTOs,
                typeDTOs,
                scopeDTOs,
                errorDTOs,
                response.getExecutionOutput(),
                generated != null ? generated.getC3dFilePath() : null,
                generated != null ? generated.getAsmFilePath() : null
        );
    }

    @Override
    public CompilationResultDTO generateCode(
            String sourceCode,
            String fileExtension,
            String projectDirectory,
            String currentFileName
    ) {
        return compileCode(sourceCode, fileExtension, projectDirectory, currentFileName, CompilationMode.FULL);
    }
}
