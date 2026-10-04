package com.piglatin.common.application.ports.output;

import com.piglatin.common.application.dto.ExecutionResponse;

/**
 * output gateway for execute extranl process
 */
public interface ProcessRunner {

    //* Ececute a command of the operative system
    ExecutionResponse run (String[] command, long timeoutMs);

    //* Compile C code qith gcc an return the binary
    ExecutionResponse compileC (String cFilePath, String outputPath);
}
