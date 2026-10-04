package com.piglatin.common.domain.exceptions;

import com.piglatin.common.domain.model.SourceLocation;

public class SemanticException extends CompilationException{

    public SemanticException(String message, SourceLocation location) {
        super(message, location);
    }

    public SemanticException(String message, SourceLocation location, Throwable cause) {
        super(message, location, cause);
    }
}
