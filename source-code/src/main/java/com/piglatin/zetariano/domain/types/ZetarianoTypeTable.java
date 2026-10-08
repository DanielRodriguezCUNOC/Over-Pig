package com.piglatin.zetariano.domain.types;

import lombok.Getter;

import java.util.*;

/**
 * Global type table for Zetariano.
 *
 * Responsibilities:
 *  - Know the built-in primitive types.
 *  - Know the user-defined class types (registered by SymbolTableBuilder).
 *  - Answer queries used by the SemanticAnalyzer and TypeChecker.
 */
@Getter
public class ZetarianoTypeTable {

    /** Built-in primitive types. */
    private final Set<String> primitiveTypes;

    /** Class types declared by the user in the source file. */
    private final Set<String> userDefinedTypes;

    /** Map of class inheritance */
    private final Map<String, String> inheritanceMap;

    public ZetarianoTypeTable() {
        this.primitiveTypes = new HashSet<>();
        this.userDefinedTypes = new HashSet<>();
        this.inheritanceMap = new HashMap<>();
        preloadPrimitives();
    }

    /**
     * Preloads the Zetariano primitive types.
     */
    private void preloadPrimitives() {
        primitiveTypes.add("int");
        primitiveTypes.add("double");
        primitiveTypes.add("char");
        primitiveTypes.add("boolean");
        primitiveTypes.add("String");
        primitiveTypes.add("void");
    }

    /**
     * Registers a user-defined class with its superclass name in the type table.
     */
    public void registerClass(String className, String superClass) {
        if (className != null && !className.isBlank()) {
            userDefinedTypes.add(className);
            if (superClass != null && !superClass.isBlank()) {
                inheritanceMap.put(className, superClass);
            }
        }
    }

    /**
     * Returns true if the given type name is either primitive or user-defined.
     */
    public boolean exists(String name) {
        if (name == null) return false;
        return primitiveTypes.contains(name) || userDefinedTypes.contains(name);
    }

    /**
     * Checks if subType is equal to or inherits from superType.
     */
    public boolean isSubtypeOf(String subType, String superType) {
        if (subType == null || superType == null) return false;
        if (subType.equals(superType)) return true;

        String current = inheritanceMap.get(subType);
        Set<String> visited = new HashSet<>();

        while (current != null) {
            if (current.equals(superType)) {
                return true;
            }
            if (!visited.add(current)) {
                // Previene bucles infinitos en herencias cíclicas erróneas
                break;
            }
            current = inheritanceMap.get(current);
        }

        return false;
    }

    public boolean isPrimitive(String typeName) {
        return typeName != null && primitiveTypes.contains(typeName);
    }

    public boolean isUserDefined(String typeName) {
        return typeName != null && userDefinedTypes.contains(typeName);
    }

    public boolean isVoid(String typeName) {
        return "void".equals(typeName);
    }

    public boolean isNumeric(String typeName) {
        return "int".equals(typeName) || "double".equals(typeName) || "char".equals(typeName);
    }

    public boolean isBoolean(String typeName) {
        return "boolean".equals(typeName);
    }

    public boolean isString(String typeName) {
        return "String".equals(typeName);
    }

    /**
     * Returns an immutable view of the registered class types.
     */
    public Set<String> getPrimitiveTypesView() {
        return Collections.unmodifiableSet(primitiveTypes);
    }

    public Set<String> getUserDefinedTypesView() {
        return Collections.unmodifiableSet(userDefinedTypes);
    }
}