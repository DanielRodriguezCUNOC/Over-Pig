package com.piglatin.common.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Output text with list or errors and exit or bad compilation
 */
@Getter
@Setter
public class CompilationResult {

    boolean isOkay;
    List<CompilationError> errors;

    public CompilationResult(boolean isOkay, List<CompilationError> errors) {
        this.isOkay = isOkay;
        this.errors = errors;
    }
}
