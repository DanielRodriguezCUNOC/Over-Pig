package com.piglatin.piglatin.domain.symboltable;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an array declared with 'SERIES'
 */
@Getter
@Setter
public class ArraySymbol extends Symbol{

    //* Size of array
    private int size;
    //* Type element for storage inside array
    private String elementType;

    public ArraySymbol(String name, String type, int size, String elementType, int line, int column) {
        super(name, type, line, column);
        this.size = size;
        this.elementType = elementType;
    }

    @Override
    public String getCategory() {
        return "array";
    }

    @Override
    public String toString() {
        return getCategory() + " '" + name + "' [" + size + "] : " + elementType + " (linea " + line + ", column " + column + ")";
    }
}
