package com.piglatin.common.application.ports.output;

/**
 * This interface store the result of C3D, RISC-V content
 */

public interface ResultExporter {

    void setOutputDirectory(String directory);

    //* Export the 3DC and quatrain
    String export3DC (String code, String fileName);

    //* Export the RISC-V assembly as fileName.asm
    String exportAsm (String code, String fileName);

    String exportRuntime ();

}
