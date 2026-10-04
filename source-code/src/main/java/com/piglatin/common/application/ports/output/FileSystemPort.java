package com.piglatin.common.application.ports.output;

import com.piglatin.common.application.dto.ProjectNodeDTO;

import java.io.IOException;

/**
 * Output port for operations in the file system
 */
public interface FileSystemPort {

    //* Read one directory and build the tree nodes
    ProjectNodeDTO readDirectory (String path);

    //* Write content of an archive
    void writeFile (String path, String content);

    //* Read the contentn of one file
    String readFile (String path);

    //* Create a void directory
    void createDirectory (String path);

    //* Delete a directory or file
    void delete(String path);

    //* Create a Zip
    byte[] compressDirectory (String rootPath);

    //* Verify if the path exists
    boolean exists (String path);
}
