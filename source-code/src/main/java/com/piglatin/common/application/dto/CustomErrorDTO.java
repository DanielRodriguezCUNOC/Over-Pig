package com.piglatin.common.application.dto;

public record CustomErrorDTO(
        int line,
        int column,
        String message
) {}
