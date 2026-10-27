package com.piglatin.piglatin.domain.types;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Global type's table
 */
@Getter
@Setter
public class PigLatinTypeTable {

    private final Set<String> primitiveTypes;
    private final Set<String> customTypes;

    /** Herencia de clases importadas de Zetariano: clase -> superclase. */
    private final Map<String, String> inheritanceMap;

    public PigLatinTypeTable() {
        this.primitiveTypes = new HashSet<>();
        this.customTypes = new HashSet<>();
        this.inheritanceMap = new HashMap<>();
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
        return primitiveTypes.contains(resolved) || customTypes.contains(resolved);
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

    /** Registra la relación de herencia (hijo -> padre) de una clase importada. */
    public void registerInheritance(String subType, String superType) {
        if (subType == null || superType == null || subType.isBlank() || superType.isBlank()) return;
        inheritanceMap.put(subType, superType);
    }

    /** true si subType es igual a superType o hereda de él (directa o indirectamente). */
    public boolean isSubtypeOf(String subType, String superType) {
        if (subType == null || superType == null) return false;
        if (subType.equals(superType)) return true;
        Set<String> visited = new HashSet<>();
        String current = inheritanceMap.get(subType);
        while (current != null && visited.add(current)) {
            if (current.equals(superType)) return true;
            current = inheritanceMap.get(current);
        }
        return false;
    }

}
