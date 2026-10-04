package com.piglatin.common.domain.exceptions;

import com.piglatin.common.domain.model.SourceLocation;

public class TypeException extends CompilationException{

    public TypeException(String message, SourceLocation location) {
        super(message, location);
    }

    public TypeException(String message, SourceLocation location, Throwable cause) {
        super(message, location, cause);
    }
}
