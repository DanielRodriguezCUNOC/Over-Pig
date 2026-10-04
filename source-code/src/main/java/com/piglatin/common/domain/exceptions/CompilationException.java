package com.piglatin.common.domain.exceptions;

import com.piglatin.common.domain.model.SourceLocation;

/**
 * Base exception for compilation errors
 */
public abstract class CompilationException extends RuntimeException {

    private final SourceLocation location;

    protected CompilationException(String message, SourceLocation location) {
        super(formatMessage(message, location));
        this.location = location;
    }

    protected  CompilationException(String message, SourceLocation location, Throwable cause ) {
        super(formatMessage (message, location), cause);
        this.location = location;
    }

    private static String formatMessage(String message, SourceLocation location) {
        if (location == null) return message;

        return String.format("[%s:%d:%d] %s", location.getFileName(), location.getPosition().getLine(),
                location.getPosition().getColumn(), message);
    }
}
