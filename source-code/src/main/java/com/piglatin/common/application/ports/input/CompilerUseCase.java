package com.piglatin.common.application.ports.input;

import com.piglatin.common.application.dto.CompileRequestDTO;
import com.piglatin.common.application.dto.CompileResponseDTO;

/**
 * This interface receive CompileRequestDTO and return an CompileResponseDTO
 */
public interface CompilerUseCase {

    CompileResponseDTO compile(CompileRequestDTO compileRequestDTO);
}
