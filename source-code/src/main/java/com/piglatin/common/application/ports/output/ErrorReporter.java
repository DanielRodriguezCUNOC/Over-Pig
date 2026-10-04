package com.piglatin.common.application.ports.output;

import com.piglatin.common.application.dto.CompilationErrorDTO;

import java.util.List;

/**
 * This interface report generic errors in compilation time
 */

public interface ErrorReporter {

    //* Report one error only
    void report (CompilationErrorDTO error);

    //* Report list of errors acumulated
    void reportAll (List<CompilationErrorDTO> errors);

    //* Get all errors has to the momento
    List<CompilationErrorDTO> getErrors();

    //* Verify has errors
    boolean hasFatalErrors();

    //* Clean the satate of reporter
    void clear();
}
