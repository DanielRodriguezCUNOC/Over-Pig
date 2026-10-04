package com.piglatin.zetariano.domain.symboltable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArraySymbol extends Symbol{

    private final String seriesType;
    private final int dimensions;
    private final String elementType;

    public ArraySymbol(String name, String seriesType, int dimensions, String elementType, int line, int column) {
        super(name, line, column);
        this.seriesType = seriesType;
        this.dimensions = dimensions;
        this.elementType = elementType;
    }
}
