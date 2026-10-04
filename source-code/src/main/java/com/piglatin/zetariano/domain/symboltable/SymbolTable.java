package com.piglatin.zetariano.domain.symboltable;

import java.util.*;

public class SymbolTable {
    private final Deque<Map<String, Symbol>> scopes = new ArrayDeque<>();
    private final Map<String, Map<String, MethodSymbol>> classMethods = new HashMap<>();

    private final Deque<String> scopeNames = new ArrayDeque<>();
    private final Map<String, Map<String, Symbol>> classFields = new HashMap<>();
    private final List<Symbol> allDeclaredSymbols = new ArrayList<>();

    public SymbolTable() {
        this.scopes.push(new HashMap<>());
    }

    public void pushScope(String name) {
        scopes.push(new HashMap<>());
        scopeNames.push(name != null ? name : "");
    }

    public void popScope() {
        if (scopes.size() > 1) {
            scopes.pop();
        }
    }

    public boolean declare(String name, Symbol s) {
        if (scopes.isEmpty()) return false;
        System.err.println("[DEBUG declare] name=" + name + " scopeName=" + (scopeNames.isEmpty() ? "<empty>" : scopeNames.peek()));
        if (!scopeNames.isEmpty() && scopeNames.peek().startsWith("class_")) {
            String className = scopeNames.peek().substring("class_".length());
            declareField(className, name, s);
        }
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

    public boolean declareMethod(String className, MethodSymbol m) {
        return classMethods
                .computeIfAbsent(className, k -> new HashMap<>())
                .putIfAbsent(m.getName() + "/" + m.getParameterTypes().size(), m) == null;
    }

    public boolean existsMethod(String className, String methodName) {
        Map<String, MethodSymbol> methods = classMethods.get(className);
        if (methods == null) return false;
        return methods.keySet().stream().anyMatch(k -> k.startsWith(methodName + "/"));
    }

    public MethodSymbol lookupMethod(String className, String methodName, int arity) {
        Map<String, MethodSymbol> methods = classMethods.get(className);
        if (methods == null) return null;
        return methods.get(methodName + "/" + arity);
    }

    public void declareField(String className, String fieldName, Symbol s) {
        classFields.computeIfAbsent(className, k -> new HashMap<>()).put(fieldName, s);
    }

    public Symbol lookupField(String className, String fieldName) {
        Map<String, Symbol> fields = classFields.get(className);
        if (fields == null) return null;
        return fields.get(fieldName);
    }

    public boolean hasAnyConstructor(String className) {
        Map<String, MethodSymbol> methods = classMethods.get(className);
        if (methods == null) return false;
        return methods.keySet().stream().anyMatch(k -> k.startsWith(className + "/"));
    }

    public List<Symbol> getAllSymbols() {
        return allDeclaredSymbols;
    }

    public Map<String, MethodSymbol> getClassMethods(String className) {
        return classMethods.get(className);
    }

    public Map<String, Symbol> getClassFields(String className) {
        return classFields.get(className);
    }
}
