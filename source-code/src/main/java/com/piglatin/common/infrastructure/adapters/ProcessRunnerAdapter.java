package com.piglatin.common.infrastructure.adapters;

import com.piglatin.common.application.dto.ExecutionResponse;
import com.piglatin.common.application.ports.output.ProcessRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Execute external process
 */
public class ProcessRunnerAdapter implements ProcessRunner {

    @Override
    public ExecutionResponse run(String[] command, long timeoutMs) {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);

        long start = System.currentTimeMillis();
        try {
            Process process = pb.start();
            boolean finisihed = process.waitFor(timeoutMs, TimeUnit.MILLISECONDS);

            if (!finisihed) {
                process.destroy();
                return ExecutionResponse.builder().exitCode(-1).standardOutput("").
                        standardError("TimeOut: the precess was exceded " + timeoutMs + " ms").
                        executionTimeMs(System.currentTimeMillis() - start).build();
            }
            String output = readStream (process);
            int exitCode = process.exitValue();
            return ExecutionResponse.builder().exitCode(exitCode).standardOutput(exitCode == 0 ? output : "").
                    standardError (exitCode != 0 ? output : "").
                    executionTimeMs(System.currentTimeMillis() - start).build();
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return ExecutionResponse.builder().exitCode(-1).standardOutput("").
                    standardError("Error to execute the process " + e.getMessage()).
                    executionTimeMs(System.currentTimeMillis() - start).build();
        }
    }

@Override
public ExecutionResponse compileC(String cFilePath, String outputPath) {
    return run(new String[]{"gcc", cFilePath, "-o", outputPath}, 30000);
}

private String readStream(Process process) throws IOException {
    StringBuilder sb = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(process.getInputStream()))) {
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
    }
    return sb.toString();
}
}



