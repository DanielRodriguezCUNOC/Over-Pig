package com.piglatin.common.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.print.attribute.standard.Severity;

/**
 * Shows a message error and the location
 */
@Getter
@EqualsAndHashCode
@ToString
public class CompilationError {

    private final String message;
    private final SourceLocation location;
    private final Severity severity;


    public CompilationError(Severity severity, String message, SourceLocation location) {
        validation(severity, message);
        this.message = message;
        this.location = location;
        this.severity = severity;
    }

    private void validation (Severity severity, String message) {

        if (severity == null)
            throw new IllegalArgumentException("Severity cannot be null");
        if (message == null || message.isBlank())
            throw new IllegalArgumentException("Message cannot be void");
    }

    public boolean isFatal() {
        return severity == Severity.ERROR;
    }

}
