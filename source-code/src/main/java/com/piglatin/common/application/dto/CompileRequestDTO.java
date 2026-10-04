package com.piglatin.common.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This interface transport the entry of some language use case for compilation
 */
@Getter
@Setter
public class CompileRequestDTO {

    private String sourceCode;
    private String fileName;
    private LanguageType languageType;
    private CompilationMode mode;
    private String projectRootPath;
    private List<ProjectNodeDTO> projectFiles;
    private String projectDirectory;
    private String currentFileName;

    public CompileRequestDTO() {
    }

    public CompileRequestDTO(String sourceCode, String fileName, LanguageType languageType, CompilationMode mode, String projectRootPath, List<ProjectNodeDTO> projectFiles) {
        this.sourceCode = sourceCode;
        this.fileName = fileName;
        this.languageType = languageType;
        this.mode = mode;
        this.projectRootPath = projectRootPath;
        this.projectFiles = projectFiles;
    }
}
