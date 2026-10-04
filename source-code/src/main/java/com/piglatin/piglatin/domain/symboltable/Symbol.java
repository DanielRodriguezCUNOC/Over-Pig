package com.piglatin.piglatin.domain.symboltable;

import lombok.Getter;
import lombok.Setter;

/**
 * Base class for all symbols registered in the symbol table
 */
@Getter
@Setter
public abstract class Symbol {

    protected String name;
    protected String type;
    protected int line;
    protected int column;
    protected Object constantValue;

    public Symbol(String name, String type, int line, int column) {
        this.name = name;
        this.type = type;
        this.line = line;
        this.column = column;
    }

    public String getId() {
        return name;
    }

    public String getScope() {
        return "global";
    }

    //* For error messages
    public abstract String getCategory();

    @Override
    public String toString() {
        return getCategory() + " '" +
                name + "' : " + type +
                " (line" + line + ", column " +  column + ")";
    }
}