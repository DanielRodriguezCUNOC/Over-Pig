package com.piglatin.common.application.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * This clas send a compilation error from any language to UI.
 */
@Getter
@Setter
public class CompilationErrorDTO {

    private CompilationStage stage;
    private String message;
    private int line;
    private int column;
    private String fileName;

    public CompilationErrorDTO() {
    }

    public CompilationErrorDTO(CompilationStage stage, String message, int line, int column, String fileName) {
        this.stage = stage;
        this.message = message;
        this.line = line;
        this.column = column;
        this.fileName = fileName;
    }
}
