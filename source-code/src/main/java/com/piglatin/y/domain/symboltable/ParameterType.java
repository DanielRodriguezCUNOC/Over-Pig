package com.piglatin.y.domain.symboltable;

import lombok.Getter;

@Getter
public class ParameterType {

    private final String type;
    private final boolean isArray;
    private final boolean isStruct;

    public ParameterType(String type, boolean isArray, boolean isStruct) {
        this.type = type;
        this.isArray = isArray;
        this.isStruct = isStruct;
    }

}