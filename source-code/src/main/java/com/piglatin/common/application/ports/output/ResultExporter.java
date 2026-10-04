package com.piglatin.common.application.ports.output;

/**
 * This interface store the result of C3D, C content
 */

public interface ResultExporter {

    void setOutputDirectory(String directory);

    //* Export the 3DC and quatrain
    String export3DC (String code, String fileName);

    //* Export C code generated with gcc
    String exportC (String code, String fileName);

    //* Export combiened codes
    String exportCombined (String c3code, String cCode, String fileName);
}
