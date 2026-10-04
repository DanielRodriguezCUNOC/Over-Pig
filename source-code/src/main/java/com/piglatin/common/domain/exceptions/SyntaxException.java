package com.piglatin.common.domain.exceptions;

import com.piglatin.common.domain.model.SourceLocation;

/**
 * Lexical errors
 */
public class SyntaxException extends CompilationException{

    public SyntaxException(String message, SourceLocation location) {
        super(message, location);
    }

    public SyntaxException(String message, SourceLocation location, Throwable cause) {
        super(message, location, cause);
    }
}
