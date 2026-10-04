package com.piglatin.common.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScopeInfoDTO {
    private String scopeId;
    private String parentScope;
    private String description;
}