package com.piglatin.common.application.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SymbolInfoDTO {
    private String id;
    private String name;
    private String type;
    private String scope;
    private int line;
}