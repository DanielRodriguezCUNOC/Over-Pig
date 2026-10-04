package com.piglatin.common.infrastructure.adapters;

import com.piglatin.common.application.dto.CompilationErrorDTO;
import com.piglatin.common.application.ports.output.ErrorReporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Accumulate errors of compilation
 */
public class ErrorCollector implements ErrorReporter {

    private final List<CompilationErrorDTO> errors =  new ArrayList<>();

    @Override
    public void report(CompilationErrorDTO error) {
        errors.add(error);
    }

    @Override
    public void reportAll(List<CompilationErrorDTO> newErrors) {
        errors.addAll(newErrors);
    }

    @Override
    public List<CompilationErrorDTO> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    @Override
    public boolean hasFatalErrors() {
        return !errors.isEmpty();
    }

    @Override
    public void clear() {
        errors.clear();
    }
}
