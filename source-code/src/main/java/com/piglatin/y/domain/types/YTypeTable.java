package com.piglatin.y.domain.types;

import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Global type table for Y.
 *
 * Responsibilities:
 *  - Know the built-in primitive types.
 *  - Know the user-defined struct types
 */
@Getter
public class YTypeTable {

    private final Set<String> primitiveTypes;
    private final Set<String> userDefinedTypes;

    public YTypeTable() {
        this.primitiveTypes = new HashSet<>();
        this.userDefinedTypes = new HashSet<>();
        preloadPrimitives();
    }

    private void preloadPrimitives() {
        primitiveTypes.add("entero");
        primitiveTypes.add("flotante");
        primitiveTypes.add("caracter");
        primitiveTypes.add("cadena");
        primitiveTypes.add("bool");
        primitiveTypes.add("void");
    }

    public void registerStruct(String structName) {
        if (structName != null && !structName.isBlank()) {
            userDefinedTypes.add(structName);
        }
    }

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
        return "entero".equals(typeName) || "flotante".equals(typeName);
    }

    public boolean isBoolean(String typeName) {
        return "bool".equals(typeName);
    }

    public boolean isString(String typeName) {
        return "cadena".equals(typeName);
    }

    public Set<String> getPrimitiveTypesView() {
        return Collections.unmodifiableSet(primitiveTypes);
    }

    public Set<String> getUserDefinedTypesView() {
        return Collections.unmodifiableSet(userDefinedTypes);
    }
}