package com.piglatin.common.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeInfoDTO {
    private String typeName;
    private String category;
    private int size;
}