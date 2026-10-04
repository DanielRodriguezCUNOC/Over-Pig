package com.piglatin.piglatin.application.dto;

import com.piglatin.common.application.dto.CustomErrorDTO;
import org.antlr.v4.runtime.tree.ParseTree;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for encapsulation of syntactic/lexic analysis result for ANTLR
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParserResultDTO {

    private ParseTree parseTree;
    private List<CustomErrorDTO> errorsList = new ArrayList<>();
    private Object stackStateDTO;

    public ParserResultDTO(ParseTree parseTree, List<CustomErrorDTO> errorsList) {
        this.parseTree = parseTree;
        this.errorsList = errorsList != null ? errorsList : new ArrayList<>();
        this.stackStateDTO = null;
    }
}