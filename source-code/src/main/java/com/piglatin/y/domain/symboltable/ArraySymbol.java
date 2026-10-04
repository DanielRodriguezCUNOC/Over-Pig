package com.piglatin.y.domain.symboltable;

import lombok.Getter;

@Getter
public class ArraySymbol extends Symbol {

    private final String elementType;
    private final int dimensions;

    public ArraySymbol(String name, String elementType, int dimensions, int line, int column) {
        super(name, line, column);
        this.elementType = elementType;
        this.dimensions = dimensions;
    }

}