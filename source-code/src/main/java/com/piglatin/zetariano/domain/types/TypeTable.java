package com.piglatin.zetariano.domain.types;

import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Global type table for Zetariano.
 *
 * Responsibilities:
 *  - Know the built-in primitive types.
 *  - Know the user-defined class types (registered by SymbolTableBuilder).
 *  - Answer queries used by the SemanticAnalyzer and TypeChecker.
 */
@Getter
public class TypeTable {

    /** Built-in primitive types. */
    private final Set<String> primitiveTypes;

    /** Class types declared by the user in the source file. */
    private final Set<String> userDefinedTypes;

    public TypeTable() {
        this.primitiveTypes = new HashSet<>();
        this.userDefinedTypes = new HashSet<>();
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
     * Registers a user-defined class name in the type table.
     */
    public void registerClass(String className) {
        if (className != null && !className.isBlank()) {
            userDefinedTypes.add(className);
        }
    }

    /**
     * Returns true if the given type name is either primitive or user-defined.
     */
    public boolean exists(String name) {
        if (name == null) return false;
        return primitiveTypes.contains(name) || userDefinedTypes.contains(name);
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