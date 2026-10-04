package com.piglatin.common.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.transform.Source;

/**
 * Shows the ubication in the code with te attributes line and column
 * Use SourcePosition for get the line and column
 */
@Getter
@EqualsAndHashCode
@ToString
public class SourceLocation {

    String fileName;
    SourcePosition position;

    public SourceLocation(String fileName, SourcePosition position) {
        validate(fileName, position);
        this.fileName = fileName;
        this.position = position;
    }

    public SourceLocation(String fileName, int line, int column) {
        this(fileName, new SourcePosition(line, column));
    }

    public static SourceLocation unknown (String fileName) {
        return new SourceLocation(fileName, SourcePosition.unknown());
    }

    private void validate(String fileName, SourcePosition position) {
        if (fileName == null || fileName.isBlank())
            throw new IllegalArgumentException("File name cannot be void");
        if (position == null)
            throw new IllegalArgumentException("Source position cannot be void");
    }
}
