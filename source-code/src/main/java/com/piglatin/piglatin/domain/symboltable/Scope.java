package com.piglatin.piglatin.domain.symboltable;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a scope in the symbol table.
 * Each scope is a LinkedHashMap that preserves insertion order.
 * It maintains a reference to its parent scope to allow for upward lookup.
 *
 * Shadowing is allowed. A symbol may be redeclared in a child scope,
 * but not in the same scope >:o.
 */
@Getter
@Setter
public class Scope {


    //* This list is where the symbols will be stored
    private final Map<String, Symbol> symbols;
    //* Means the scope parent to which it belongs
    private final Scope parent;
    //* Symbol identifier
    private final String name;
    //* ID number for the scope
    private int id;

    public Scope(int id) {
        this.id = id;
        this.symbols = new HashMap<>();
        this.parent = null;
        this.name = null;
    }

    public Scope(String name, Scope parent) {
        this.parent = parent;
        this.name = name;
        this.symbols = new LinkedHashMap<>();
    }

    public Scope(int id, String name, Scope parent){
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.symbols = new LinkedHashMap<>();
    }


    /**
     * These methos are very important because:
     * @param name represent the symbol identifier
     * @param symbol represent the symbol for registerer
     * @return true if the symbol was registered with exit
     * or false if the symbol was already exists in the current scope
     */

    public boolean declare(String name,  Symbol symbol) {

        /**
         * This condition search in the Map of symbols if already exitst
         *  one same name that is to be stored in the current scope
         */
        if (symbols.containsKey(name)) return false;
        symbols.put(name, symbol);
        return true;
    }

    /**
     * Search a symbol by its name
     * Search in the current scope if not found, move up recursively
     * through the chain of parent scopes
     */

    public Symbol lookup(String name) {

        //* Search in the Map of symbols the symbol with the name
        Symbol symbol = symbols.get(name);

        //* Condition where symbol was found in the Map
        if (symbol != null) return symbol;

        /**
         * Condition where the symbol was not founded in the current scope
         * So search has to found the symbol
         */
        if(parent != null) return parent.lookup(name);

        return null;
    }

    /**
     * Search a symbol only in the current scope
     * without going up to the parent
     * This allow detects local redeclaration's
     */
    public Symbol lookupLocal(String name) {
        return symbols.get(name);
    }

    public boolean isGlobal(){
        return parent == null;
    }

    public boolean containsSymbol(String name) {
        return symbols.containsKey(name);
    }

    @Override
    public String toString() {
        return "Scope '" +  name + "' [" + symbols.size() + " symbols]";
    }


}
