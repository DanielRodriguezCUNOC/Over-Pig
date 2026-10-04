package com.piglatin.piglatin.domain.symboltable;


import lombok.Getter;
import lombok.Setter;

/**
 * Represents variable declared with 'ESTO' or a param of function
 */

@Getter
@Setter
public class VariableSymbol extends Symbol {

    //* Indicate if the variable was yet initialized
    private boolean initialized;
    //* Posicion en el stack
    private int offset;

    public VariableSymbol(String name, String type, int line, int column) {
        super(name, type, line, column);
    }

    public VariableSymbol(String name, String type, boolean initialized, int line, int column) {
        super(name, type, line, column);
        this.initialized = initialized;
    }

    @Override
    public String getCategory() {
        return "variable";
    }
}
