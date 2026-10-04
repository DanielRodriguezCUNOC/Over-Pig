package com.piglatin.piglatin.domain.symboltable;


import lombok.Getter;
import lombok.Setter;

/**
 * Represents a parameter in a function declaration
 * Extends of VariableSymbol beacuse need the flag for initialization
 */

@Getter
@Setter
public class ParameterSymbol extends  VariableSymbol {

    //* This show better info in the symbol table
    private final boolean isParameter;


    public ParameterSymbol(String name, String type, int line, int column) {
        super(name, type, true, line, column);
        //* Indicate that param was initialized
        this.isParameter = true;
    }

    @Override
    public String getCategory() {
        return "parameter";
    }
}
