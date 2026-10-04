package com.piglatin.common.infrastructure.adapters;

import com.piglatin.common.application.dto.CompilationErrorDTO;
import com.piglatin.common.application.ports.output.ErrorReporter;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Show the errors by console in real time
 */
@Getter
@Setter
public class ConsoleErrorReporter implements ErrorReporter {

    private final List<CompilationErrorDTO> errors = new ArrayList<>();


    @Override
    public void report(CompilationErrorDTO error) {
        errors.add(error);
        String location = error.getFileName() + ":" + error.getLine() + ":" + error.getColumn();
        System.err.println("ERROR: " + location + " - " + error.getMessage());
    }

    @Override
    public void reportAll(List<CompilationErrorDTO> newErrors) {
        newErrors.forEach(this::report);
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
