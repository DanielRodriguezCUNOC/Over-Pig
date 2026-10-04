package com.piglatin.common.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a node of the project tree
 * Allows open and save files
 */
@Getter
@Setter
public class ProjectNodeDTO {

    private String name;
    private String path;
    private NodeType nodeType;
    private String content;
    private LanguageType languageType;
    private List<ProjectNodeDTO> children;

    public ProjectNodeDTO(String name, String path, NodeType nodeType, String content, LanguageType languageType, List<ProjectNodeDTO> children) {
        this.name = name;
        this.path = path;
        this.nodeType = nodeType;
        this.content = content;
        this.languageType = languageType;
        this.children = children;
    }

    public ProjectNodeDTO() {
        children = new ArrayList<>();
    }

    public void addChild(ProjectNodeDTO projectNodeDTO) {
        children.add(projectNodeDTO);
    }
}
