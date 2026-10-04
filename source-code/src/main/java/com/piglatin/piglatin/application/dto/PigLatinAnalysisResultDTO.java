package com.piglatin.piglatin.application.dto;

import com.piglatin.common.application.dto.CustomErrorDTO;
import com.piglatin.common.domain.cfg.ControlFlowGraph;
import com.piglatin.piglatin.domain.symboltable.SymbolTable;
import com.piglatin.piglatin.domain.types.TypeTable;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class PigLatinAnalysisResultDTO {
    private final boolean isValid;
    private final List<CustomErrorDTO> errorsList;
    private final SymbolTable symbolTable;
    private final TypeTable typeTable;
    private final ControlFlowGraph cfg;

    public PigLatinAnalysisResultDTO(boolean isValid,
                                     List<CustomErrorDTO> errorsList,
                                     SymbolTable symbolTable, TypeTable typeTable,
                                     ControlFlowGraph cfg) {
        this.isValid = isValid;
        this.errorsList = errorsList !=  null ? errorsList : Collections.emptyList();
        this.symbolTable = symbolTable;
        this.typeTable = typeTable;
        this.cfg = cfg;
    }

}
