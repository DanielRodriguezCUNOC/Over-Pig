package com.piglatin.ui.infrastructure.facade.dto;

public record ErrorDTO(int line, int column, String errorType, String message) {
}
