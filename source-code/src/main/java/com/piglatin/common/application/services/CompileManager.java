package com.piglatin.common.application.services;

import com.piglatin.common.application.dto.*;
import com.piglatin.common.application.ports.input.CompilerUseCase;
import com.piglatin.common.application.ports.input.ExecutionCCode;
import com.piglatin.common.application.ports.output.ErrorReporter;
import com.piglatin.common.application.ports.output.Logger;
import com.piglatin.common.application.ports.output.ResultExporter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class coordinates the output ports
 * Execute the generate code
 */
@RequiredArgsConstructor
public class CompileManager implements CompilerUseCase {

    private final Map<LanguageType, CompilerUseCase> languajeCompilers;
    private final ErrorReporter errorReporter;
    private final ResultExporter resultExporter;
    private final ExecutionCCode executionCCode;
    private final Logger logger;

    @Override
    public CompileResponseDTO compile(CompileRequestDTO request) {
        long startTime = System.currentTimeMillis();
        logger.info("Starting compilation for the language: " + request.getLanguageType());

        errorReporter.clear();

        CompilerUseCase compiler = resolveCompiler(request.getLanguageType());
        if (compiler == null) {
            logger.error("Compiler not found for the language: " + request.getLanguageType());
            return buildErrorResponse(request, "Compiler not available for the language");
        }

        CompileResponseDTO responseDTO = compiler.compile(request);

        if (!responseDTO.isSuccess() || errorReporter.hasFatalErrors()) {

            List<CompilationErrorDTO> merged = new ArrayList<>();
            if (responseDTO.getErrors() != null) {
                merged.addAll(responseDTO.getErrors());
            }
            if (errorReporter.getErrors() != null) {
                merged.addAll(errorReporter.getErrors());
            }

            logger.warn("Compilation failed. Errors detected: " + merged.size());
            responseDTO.setSuccess(false);
            responseDTO.setErrors(merged);
            responseDTO.setCompilationTimeMs(System.currentTimeMillis() - startTime);
            return responseDTO;
        }

        if (shouldExport(request.getMode())) exportResult(responseDTO, request);
        if (request.getMode() == CompilationMode.FULL &&
                responseDTO.getGeneratedCode() != null) executeGeneratedCode(responseDTO);

        responseDTO.setCompilationTimeMs(System.currentTimeMillis() - startTime);
        logger.info("Finished compilation for the language: " + request.getLanguageType() +
                " in" + responseDTO.getCompilationTimeMs() + " ms");
        return responseDTO;
    }

    private CompilerUseCase resolveCompiler(LanguageType languageType) {
        return languajeCompilers.get(languageType);
    }

    private boolean shouldExport (CompilationMode mode) {
        return (mode == CompilationMode.C3D_ONLY
                || mode == CompilationMode.C_CODE_ONLY
                || mode == CompilationMode.FULL);
    }

    private void exportResult (CompileResponseDTO response, CompileRequestDTO request) {
        GeneretedCodeDTO generated = response.getGeneratedCode();

        if (generated == null) return;

        String dir = request.getProjectDirectory();
        if (dir != null && !dir.isBlank()) resultExporter.setOutputDirectory(dir);

        String fileName = request.getCurrentFileName() != null ? request.getCurrentFileName() : request.getFileName();
        String baseName = fileName;
        if (baseName != null) {
            int lastDot = baseName.lastIndexOf('.');
            if (lastDot > 0) {
                baseName = baseName.substring(0, lastDot);
            }
        } else {
            baseName = "output";
        }

        try {
            if (generated.getC3dCode() != null && !generated.getC3dCode().isBlank()) {
                String c3dPath = resultExporter.export3DC(generated.getC3dCode(), baseName);
                logger.info("3DC exported to: " + c3dPath);
            }

            if (generated.getCCode() != null && !generated.getCCode().isBlank()) {
                String cPath = resultExporter.exportC(generated.getCCode(), baseName);
                logger.info("C code exported to: " + cPath);
            }

        } catch (Exception e) {
            logger.error("Error exporting results", e);
        }
    }

    private void executeGeneratedCode (CompileResponseDTO response) {
        GeneretedCodeDTO generated = response.getGeneratedCode();
        if (generated == null || generated.getCCode() == null) return;
        try {
           ExecutionResponse exec = executionCCode.execute(
                   generated.getCCode(),
                   generated.getEntryPoint() != null ? generated.getEntryPoint() : "main");

           String combined = exec.getStandardOutput();
           if (exec.getStandardError() != null && !exec.getStandardError().isBlank()) {
               combined = (combined == null ? "" : combined)
                        + "\n--- stderr ---\n" + exec.getStandardError();
           }
           response.setExecutionOutput(combined);

           if (!exec.isSuccess()){
               logger.error("Error executing generated code: " + exec.getStandardError());
           }
        }catch (Exception e) {
            logger.error("Error during execution of generated code", e);
        }
    }

    private CompileResponseDTO buildErrorResponse (CompileRequestDTO request, String message) {
        CompilationErrorDTO errorDTO = new CompilationErrorDTO();
        errorDTO.setStage(CompilationStage.SEMANTIC_ANALYSIS);
        errorDTO.setMessage(message);
        errorDTO.setLine(0);
        errorDTO.setColumn(0);
        errorDTO.setFileName(request.getFileName());

        CompileResponseDTO responseDTO = new CompileResponseDTO();
        responseDTO.setSuccess(false);
        responseDTO.setErrors(List.of(errorDTO));
        responseDTO.setCompilationTimeMs(0L);
        return responseDTO;
    }

}
