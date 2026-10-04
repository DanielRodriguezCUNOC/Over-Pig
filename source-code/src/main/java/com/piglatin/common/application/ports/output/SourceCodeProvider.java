package com.piglatin.common.application.ports.output;

/**
 * Output gateway for receive the source code
 */

public interface SourceCodeProvider {

    //* Read the file content of the source code
    String readSource (String filePath);

    //* Verify if the file exists
    boolean exists (String filePath);

    //* Get the absolute path for the file in the project
    String resolvePath (String projectRoot, String importPath);
}
