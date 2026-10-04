package com.piglatin.common.application.ports.input;

import com.piglatin.common.application.dto.ProjectNodeDTO;
import com.piglatin.common.application.dto.NodeType;

/**
 * Gateway output for the management of the tree wrok
 */
public interface ProjectTreeManager {

    //* Open one project form system files and build the tree nodes
    ProjectNodeDTO openProject(String rootPath);

    //* Save the current state of the project
    void saveProject(ProjectNodeDTO nodeDTO);

    //* Packaged all the project in one bytes array for download
    byte[] downloadProject(ProjectNodeDTO project);

    //* Create new file or directory in the project
    ProjectNodeDTO createNode(String parentPath, String name, NodeType type);

    //* Delete one node in the project
    void deleteNode (String nodePath);
}
