package com.piglatin.common.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Thiss class source text and source location
 */
@Getter
@Setter
public class SourceCode {
    String sourceCode;
    SourceLocation location;

    public SourceCode(String sourceCode, SourceLocation location) {
        this.sourceCode = sourceCode;
        this.location = location;
    }
}
