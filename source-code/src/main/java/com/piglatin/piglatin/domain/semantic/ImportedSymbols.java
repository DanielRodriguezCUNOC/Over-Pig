package com.piglatin.piglatin.domain.semantic;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**

 * Interfaz de dominio sin dependencias de Zetariano ni de Y: el adaptador que la
 * implementa traduce los tipos de cada lenguaje al vocabulario de
 * PigLatin (NUMERUS, DECIMALIS, TEXTUM, LITTERA, BOOL, VOID, SERIES_X, nombre de clase).
 * Todos los metodos devuelven null cuando el simbolo no existe.
 */
public interface ImportedSymbols {

    Set<String> classNames();

    Set<String> structNames();

    Map<String, String> inheritance();

    default boolean isClass(String name) {
        return name != null && classNames().contains(name);
    }

    default boolean isStruct(String name) {
        return name != null && structNames().contains(name);
    }

    /** Metodo de una clase (incluye heredados) resuelto por nombre y aridad. */
    Signature resolveMethod(String className, String methodName, int arity);

    /** Constructor de una clase por aridad; null si la clase no declara uno con esa aridad. */
    Signature resolveConstructor(String className, int arity);

    /** Funcion libre de un .y resuelta por nombre y aridad. */
    Signature resolveFunction(String name, int arity);

    /** Tipo del campo de una clase de Zetariano. */
    String fieldType(String className, String fieldName);

    record Field(String name, String type, boolean isArray) {
    }

    /** Lista de campos de un struct en orden de declaracion; null o vacio si no existe. */
    List<Field> structFields(String structName);

    record Signature(String name, List<String> paramTypes, String returnType) {
    }
    ImportedSymbols NONE = new ImportedSymbols() {
        @Override public Set<String> classNames() { return Set.of(); }
        @Override public Set<String> structNames() { return Set.of(); }
        @Override public Map<String, String> inheritance() { return Map.of(); }
        @Override public Signature resolveMethod(String c, String m, int a) { return null; }
        @Override public Signature resolveConstructor(String c, int a) { return null; }
        @Override public Signature resolveFunction(String n, int a) { return null; }
        @Override public String fieldType(String c, String f) { return null; }
        @Override public List<Field> structFields(String s) { return List.of(); }
    };
}