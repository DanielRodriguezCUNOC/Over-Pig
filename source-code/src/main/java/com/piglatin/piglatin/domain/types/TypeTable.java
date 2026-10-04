package com.piglatin.piglatin.domain.types;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Global type's table
 */
@Getter
@Setter
public class TypeTable {

    private final Set<String> primitiveTypes;
    private final Set<String> customTypes;

    public TypeTable() {
        this.primitiveTypes = new HashSet<>();
        this.customTypes = new HashSet<>();
        preloadPrimitives();
    }

    /**
     * Precharge primitives primitiveTypes
     */

    private void preloadPrimitives(){
        primitiveTypes.add("NUMERUS");
        primitiveTypes.add("DECIMALIS");
        primitiveTypes.add("TEXTUM");
        primitiveTypes.add("LITTERA");
        primitiveTypes.add("BOOL");
    }

    public String resolveType(String typeName){
        if(typeName == null) return null;
        if(typeName.equals("bool")) return "BOOL";
        if(typeName.equals("BOOLEAN")) return "BOOL";
        return typeName;
    }

    public boolean exists(String name){

        if (name == null) return false;
        String resolved = resolveType(name);
        if (resolved.startsWith("SERIES_")){
            String base = resolved.substring(7);
            return exists(base);
        }
        return primitiveTypes.contains(resolved);
    }

    public boolean isPrimitive(String typeName) {
        if (typeName == null) return false;
        String resolved = resolveType(typeName);
        return primitiveTypes.contains(resolved);
    }

    public String getArrayElementType(String arrayType) {
        if (arrayType == null || !arrayType.startsWith("SERIES_")) return null;
        return resolveType(arrayType.substring(7));
    }

    public void registerType(String typeName) {
        if (typeName != null && !typeName.isEmpty()) {
            customTypes.add(typeName);
        }
    }

}

