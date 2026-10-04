package com.piglatin.common.domain.model;

import lombok.*;

/**
 * Give the linea and column inside the source code
 */
@Getter
@EqualsAndHashCode
@ToString
public class SourcePosition {

    int line;
    int column;

    public SourcePosition(int line, int column) {

        validate(line, column);
        this.line = line;
        this.column = column;
    }

    public static SourcePosition unknown() {
        return new SourcePosition(-1, -1);
    }

    private void validate (int line, int column) {
        if (line < 0 || column < 0) throw new IllegalArgumentException("Line and column must to mayor to 0");
    }

}
