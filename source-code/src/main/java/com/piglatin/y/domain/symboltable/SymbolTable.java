package com.piglatin.y.domain.symboltable;

import java.util.*;

public class SymbolTable {
    private final Deque<Map<String, Symbol>> scopes = new ArrayDeque<>();
    private final Deque<String> scopeNames = new ArrayDeque<>();
    private final Map<String, FunctionSymbol> functions = new HashMap<>();
    private final Map<String, Map<String, Symbol>> structFields = new HashMap<>();
    private final List<Symbol> allDeclaredSymbols = new ArrayList<>();

    public void pushScope(String name) {
        scopes.push(new HashMap<>());
        scopeNames.push(name != null ? name : "");
    }

    public void popScope() {
        if (!scopes.isEmpty()) scopes.pop();
        if (!scopeNames.isEmpty()) scopeNames.pop();
    }

    public boolean declare(String name, Symbol s) {
        if (scopes.isEmpty()) return false;
        boolean inserted = scopes.peek().putIfAbsent(name, s) == null;
        if (inserted) allDeclaredSymbols.add(s);
        return inserted;
    }

    public boolean exists(String name) {
        for (Map<String, Symbol> scope : scopes) {
            if (scope.containsKey(name)) return true;
        }
        return false;
    }

    public Symbol lookup(String name) {
        for (Map<String, Symbol> scope : scopes) {
            if (scope.containsKey(name)) return scope.get(name);
        }
        return null;
    }

    public boolean declareFunction(FunctionSymbol f) {
        String key = f.getName() + "/" + f.getParameterTypes().size();
        return functions.putIfAbsent(key, f) == null;
    }

    public boolean existsFunction(String name) {
        return functions.keySet().stream().anyMatch(k -> k.startsWith(name + "/"));
    }

    public FunctionSymbol lookupFunction(String name, int arity) {
        return functions.get(name + "/" + arity);
    }

    public void declareField(String structName, String fieldName, Symbol s) {
        structFields.computeIfAbsent(structName, k -> new HashMap<>()).put(fieldName, s);
    }

    public Symbol lookupField(String structName, String fieldName) {
        Map<String, Symbol> fields = structFields.get(structName);

        if (fields == null) return null;
        return fields.get(fieldName);
    }

    public List<Symbol> getAllSymbols() {
        return allDeclaredSymbols;
    }

}