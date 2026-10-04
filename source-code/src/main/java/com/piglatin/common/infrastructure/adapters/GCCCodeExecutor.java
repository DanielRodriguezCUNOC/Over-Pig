package com.piglatin.common.infrastructure.adapters;

import com.piglatin.common.application.dto.ExecutionResponse;
import com.piglatin.common.application.ports.input.ExecutionCCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class GCCCodeExecutor implements ExecutionCCode {

    @Override
    public ExecutionResponse execute(String cCode, String entryPoint) {

        long startTime = System.currentTimeMillis();

        Path tempDirectory = null;

        try {
            tempDirectory = Files.createTempDirectory("piglatin-");

            Path cFile = tempDirectory.resolve("program.c");
            Path executable = tempDirectory.resolve("program");

            /*
             * Write generated C code
             */
            Files.writeString(cFile, cCode);

            /*
             * Compile C code using GCC
             */
            Process compileProcess = new ProcessBuilder(
                    "gcc",
                    cFile.toString(),
                    "-o",
                    executable.toString()
            )
                    .redirectErrorStream(false)
                    .start();

            boolean compileFinished = compileProcess.waitFor(10, TimeUnit.SECONDS);

            String compileOutput =
                    new String(compileProcess.getInputStream().readAllBytes());

            String compileError =
                    new String(compileProcess.getErrorStream().readAllBytes());

            int compileExitCode;
            if (!compileFinished) {
                compileProcess.destroyForcibly();
                compileExitCode = -1;
            } else {
                compileExitCode = compileProcess.exitValue();
            }

            /*
             * GCC compilation failed
             */
            if (compileExitCode != 0) {
                return ExecutionResponse.builder()
                        .exitCode(compileExitCode)
                        .standardOutput(compileOutput)
                        .standardError(compileError)
                        .executionTimeMs(
                                System.currentTimeMillis() - startTime
                        )
                        .build();
            }

            /*
             * Execute generated binary
             */
            Process executionProcess =
                    new ProcessBuilder(executable.toString())
                            .redirectErrorStream(false)
                            .start();

            executionProcess.getOutputStream().close();

            boolean executionFinished = executionProcess.waitFor(3, TimeUnit.SECONDS);

            int exitCode;
            String timeoutError = "";
            if (!executionFinished) {
                executionProcess.destroyForcibly();
                timeoutError = "\n[Tiempo de ejecución excedido: 3s]";
                exitCode = -1;
            } else {
                exitCode = executionProcess.exitValue();
            }

            String standardOutput =
                    new String(executionProcess.getInputStream().readAllBytes());

            String standardError =
                    new String(executionProcess.getErrorStream().readAllBytes());

            if (!timeoutError.isEmpty()) {
                standardError += timeoutError;
            }

            return ExecutionResponse.builder()
                    .exitCode(exitCode)
                    .standardOutput(standardOutput)
                    .standardError(
                            compileError + standardError
                    )
                    .executionTimeMs(
                            System.currentTimeMillis() - startTime
                    )
                    .build();

        } catch (IOException e) {

            return ExecutionResponse.builder()
                    .exitCode(-1)
                    .standardOutput("")
                    .standardError(
                            "Error executing GCC: " + e.getMessage()
                    )
                    .executionTimeMs(
                            System.currentTimeMillis() - startTime
                    )
                    .build();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            return ExecutionResponse.builder()
                    .exitCode(-1)
                    .standardOutput("")
                    .standardError(
                            "Execution interrupted: " + e.getMessage()
                    )
                    .executionTimeMs(
                            System.currentTimeMillis() - startTime
                    )
                    .build();
        }
    }

    @Override
    public Process startInteractive(String cCode, String entryPoint, String outputDirectory) throws IOException {
        Path outputDir;
        if (outputDirectory != null && !outputDirectory.isBlank()) {
            outputDir = Path.of(outputDirectory);
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
            }
        } else {
            outputDir = Files.createTempDirectory("piglatin-");
        }

        String baseName = "program";
        Path cFile = outputDir.resolve(baseName + ".c");
        Path executable = outputDir.resolve(baseName + (isWindows() ? ".exe" : ""));

        Files.writeString(cFile, cCode);

        Process compileProcess = new ProcessBuilder(
                "gcc",
                "-std=gnu99",
                cFile.toString(),
                "-o",
                executable.toString()
        ).redirectErrorStream(true).start();

        String compileOut = new String(compileProcess.getInputStream().readAllBytes());

        boolean finished;
        try {
            finished = compileProcess.waitFor(15, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Compilación interrumpida");
        }

        if (!finished) {
            compileProcess.destroyForcibly();
            throw new IOException("Compilación GCC timeout (15s)");
        }

        if (compileProcess.exitValue() != 0) {
            throw new IOException("GCC falló:\n" + compileOut);
        }

        return new ProcessBuilder(executable.toString())
                .redirectErrorStream(false)
                .start();
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }


}
