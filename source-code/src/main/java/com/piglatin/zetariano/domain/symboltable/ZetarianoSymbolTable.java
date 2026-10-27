package com.piglatin.zetariano.domain.symboltable;

import com.piglatin.zetariano.domain.types.ZetarianoTypeTable;
import lombok.Setter;

import java.util.*;
@Setter
public class ZetarianoSymbolTable {


    private ZetarianoTypeTable typeTable;
    private final Deque<Map<String, Symbol>> scopes = new ArrayDeque<>();
    private final Map<String, Map<String, MethodSymbol>> classMethods = new HashMap<>();

    private final Deque<String> scopeNames = new ArrayDeque<>();
    private final Map<String, Map<String, Symbol>> classFields = new HashMap<>();
    private final List<Symbol> allDeclaredSymbols = new ArrayList<>();

    public ZetarianoSymbolTable() {
        this.scopes.push(new HashMap<>());
    }

    public void pushScope(String name) {
        scopes.push(new HashMap<>());
        scopeNames.push(name != null ? name : "");
    }

    public void popScope() {
        if (scopes.size() > 1) {
            scopes.pop();
            if (!scopeNames.isEmpty()) {
                //! Tronarse the scope name XD
                scopeNames.pop();
            }
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
        //* Search in the scope local stack
        for (Map<String, Symbol> scope : scopes) {
            if (scope.containsKey(name)) return scope.get(name);
        }

        //* If isn't local, search in the class fields of the current class scope
        for (String sName : scopeNames) {
            if (sName != null && sName.startsWith(("class_"))) {
                String className = sName.substring("class_".length());
                Symbol field = lookupField(className, name);
                if (field != null) return field;
            }
        }
        return null;
    }

    public boolean declareMethod(String className, MethodSymbol m) {
        return classMethods
                .computeIfAbsent(className, k -> new HashMap<>())
                .putIfAbsent(m.getName() + "/" + m.getParameterTypes().size(), m) == null;
    }

    public boolean existsMethod(String className, String methodName) {
        String current = className;
        Set<String> visited = new HashSet<>();
        while (current != null && visited.add(current)) {
            Map<String, MethodSymbol> methods = classMethods.get(current);
            if (methods != null) {
                if (methods.keySet().stream().anyMatch(k -> k.startsWith(methodName + "/"))) {
                    return true;
                }
            }
            if (typeTable != null) {
                current = typeTable.getInheritanceMap().get(current);
            } else {
                break;
            }
        }
        return false;
    }

    public MethodSymbol lookupMethod(String className, String methodName, int arity) {
        String current = className;
        Set<String> visited = new HashSet<>();
        while (current != null && visited.add(current)) {
            Map<String, MethodSymbol> methods = classMethods.get(current);
            if (methods != null) {
                MethodSymbol m = methods.get(methodName + "/" + arity);
                if (m != null) return m;
            }
            if (typeTable != null) {
                current = typeTable.getInheritanceMap().get(current);
            } else {
                break;
            }
        }
        return null;
    }

    public void declareField(String className, String fieldName, Symbol s) {
        classFields.computeIfAbsent(className, k -> new HashMap<>()).put(fieldName, s);
    }

    public Symbol lookupField(String className, String fieldName) {
        String current = className;
        Set<String> visited = new HashSet<>();
        while (current != null && visited.add(current)) {
            Map<String, Symbol> fields = classFields.get(current);
            if(fields != null && fields.containsKey(fieldName)) {
                return fields.get(fieldName);
            }
            if (typeTable != null) {
                current = typeTable.getInheritanceMap().get(current);
            } else {
                break;
            }
        }
        return null;
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
