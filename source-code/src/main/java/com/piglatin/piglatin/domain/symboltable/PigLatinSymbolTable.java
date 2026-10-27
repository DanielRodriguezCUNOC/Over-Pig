package com.piglatin.piglatin.domain.symboltable;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Hierarchical symbol table that manages a stack of scopes
 *The global scope is created automatically when this class is instantiated
 */
@Getter
@Setter
public class PigLatinSymbolTable {

    //! Stack for scopes control while semantic analysis
    private Stack<Scope> activeScopes;

    //! Stores history of all created scopes.
    private List<Scope> allCreatedScopes;

    //! ID for each scope
    private int scopeCounter;

    public PigLatinSymbolTable() {
        this.activeScopes = new Stack<>();
        this.allCreatedScopes = new ArrayList<>();
        this.scopeCounter = 0;

        //Initial global scope
        pushScope("global");
    }

    /**
     * Create and push new child scope form current scope
     * Upon entering a function or block
     */
    public void pushScope(String name) {
        Scope parentScope = activeScopes.isEmpty() ? null : activeScopes.peek();
        Scope newScope = new Scope(scopeCounter++, name, parentScope);
        activeScopes.push(newScope);
        allCreatedScopes.add(newScope);
    }

    /**
     * Delete current scope to the stack
     * Upon exiting a function or block
     */
    public void popScope() {
        if (activeScopes.size() <= 1) {
            throw new IllegalStateException("Can't delete the global scope");
        }
        activeScopes.pop();
    }

    /**
     * Declare one symbol in the current scope
     * register variables, arrays, functions in the current scope
     * @return true if was registered
     * false if was already exists in the current scope
     */
    public boolean declare(String name, Symbol symbol) {
        if (activeScopes.isEmpty()) throw new IllegalStateException("None an active scope");
        return activeScopes.peek().declare(name, symbol);
    }

    /**
     * Serach a symbol by name in the current scope and its ancestors
     * Resolve identifiers
     */
    public Symbol lookup(String name) {
        if (activeScopes.isEmpty()) return null;
        return activeScopes.peek().lookup(name);
    }

    /**
     * Search for a symbol only in the current scope
     */
    public Symbol lookupCurrent(String name) {
        if (activeScopes.isEmpty()) return null;
        return activeScopes.peek().lookupLocal(name);
    }

    /**
     * Search a symbol in the global scope
     * Util for resolve function call's from any level
     */
    public Symbol lookupGlobal(String name){
        if (activeScopes.isEmpty()) return null;

        //* Use firstElement beacause the global scope is the oldest in the stack
        Scope global = activeScopes.firstElement();
        return global != null ? global.lookupLocal(name) : null;
    }


    public Scope getCurrentScope(){
        return activeScopes.isEmpty() ? null : activeScopes.peek();
    }

    public int getScopeDepth(){
        return  activeScopes.size();
    }

    public boolean isGlobalScope(){
        return activeScopes.size() == 1;
    }

    public void reset(){
        activeScopes.clear();
        pushScope("global");
    }

    /**
     * Return all current scopes in the stack for the GUI .
     */
    public List<Scope> getAllScopes() {
        return allCreatedScopes;
    }

    /**
     * Search a symbol from current scope to global scope
     * @param name
     * @return true if exists, false where not
     */
    public boolean exists(String name){
        for (int i = activeScopes.size() - 1 ; i>=0; i--) {
            if (activeScopes.get(i).containsSymbol(name)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Symbol Table ===\n");

        int level = activeScopes.size() - 1;
        for (Scope scope : activeScopes) {
            sb.append(" [").append(level).append("] ").append(scope.toString()).append("\n");

            for (Symbol sym : scope.getSymbols().values()) {
                sb.append("    ").append(sym.toString()).append("\n");
            }
            level--;
        }
        return sb.toString();
    }

    public List<Symbol> getAllSymbols() {
        List<Symbol> allSymbols = new ArrayList<>();
        if (allCreatedScopes != null) {
            for (Scope scope : allCreatedScopes) {
                if (scope.getSymbols() != null) {
                    allSymbols.addAll(scope.getSymbols().values());
                }
            }
        }
        return allSymbols;
    }
}

