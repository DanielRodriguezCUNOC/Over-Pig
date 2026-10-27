package com.piglatin.common.application.services;

import com.piglatin.common.application.dto.*;
import com.piglatin.common.application.ports.input.CompilerUseCase;
import com.piglatin.common.application.ports.input.ExecutionCCode;
import com.piglatin.common.application.ports.output.ErrorReporter;
import com.piglatin.common.application.ports.output.Logger;
import com.piglatin.common.application.ports.output.ResultExporter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class coordinates the output ports.
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

            List<CompilationErrorDTO> merged = mergeErrors(responseDTO.getErrors(), errorReporter.getErrors());

            logger.warn("Compilation failed. Errors detected: " + merged.size());
            responseDTO.setSuccess(false);
            responseDTO.setErrors(merged);
            responseDTO.setCompilationTimeMs(System.currentTimeMillis() - startTime);
            return responseDTO;
        }

        if (shouldExport(request.getMode())) exportResult(responseDTO, request);

        responseDTO.setCompilationTimeMs(System.currentTimeMillis() - startTime);
        logger.info("Finished compilation for the language: " + request.getLanguageType() +
                " in " + responseDTO.getCompilationTimeMs() + " ms");
        return responseDTO;
    }

    private CompilerUseCase resolveCompiler(LanguageType languageType) {
        return languajeCompilers.get(languageType);
    }

    private boolean shouldExport (CompilationMode mode) {
        return mode != null && mode != CompilationMode.VALIDATE_ONLY;
    }

    private List<CompilationErrorDTO> mergeErrors(List<CompilationErrorDTO> fromResponse,
                                                  List<CompilationErrorDTO> fromReporter) {
        Map<String, CompilationErrorDTO> unique = new LinkedHashMap<>();
        addUnique(unique, fromResponse);
        addUnique(unique, fromReporter);
        return new ArrayList<>(unique.values());
    }

    private void addUnique(Map<String, CompilationErrorDTO> target, List<CompilationErrorDTO> source) {
        if (source == null) return;
        for (CompilationErrorDTO e : source) {
            String key = e.getStage() + "|" + e.getLine() + "|" + e.getColumn() + "|" + e.getMessage();
            target.putIfAbsent(key, e);
        }
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
                generated.setC3dFilePath(c3dPath);
                logger.info("3DC exported to: " + c3dPath);
            }

            if (generated.getAsmCode() != null && !generated.getAsmCode().isBlank()) {
                String asmPath = resultExporter.exportAsm(generated.getAsmCode(), baseName);
                generated.setAsmFilePath(asmPath);
                logger.info("RISC-V exported to: " + asmPath);

                String runtimePath = resultExporter.exportRuntime();
                if (runtimePath != null) {
                    logger.info("Runtime available at: " + runtimePath);
                } else {
                    logger.warn("runtime.asm not found. Put it in src/main/resources/riscv/runtime.asm "
                            + "or next to the generated .asm, otherwise RARS will fail on .include");
                }
            }
        } catch (Exception e) {
            logger.error("Error exporting results", e);
            List<CompilationErrorDTO> errors = new ArrayList<>();
            if (response.getErrors() != null) errors.addAll(response.getErrors());
            errors.add(new CompilationErrorDTO(
                    CompilationStage.C3D_GENERATION,
                    "Could not write the generated files: " + e.getMessage(),
                    0, 0, request.getFileName()));
            response.setErrors(errors);
            response.setSuccess(false);
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
