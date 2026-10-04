package com.piglatin.common.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO is used for the execution of C code
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionResponse {
    private int exitCode;
    private String standardOutput;
    private String standardError;
    private long executionTimeMs; // Innecessary but fancy hahaha

    public boolean isSuccess() {
        return exitCode == 0;
    }
}
