package com.piglatin.y.domain.symboltable;

import lombok.Getter;

@Getter
public class VariableSymbol extends Symbol {

    private final String type;
    private final boolean isArray;
    private final boolean isStruct;

    public VariableSymbol(String name, String type, boolean isArray, boolean isStruct, int line, int column) {
        super(name, line, column);
        this.type = type;
        this.isArray = isArray;
        this.isStruct = isStruct;
    }

}