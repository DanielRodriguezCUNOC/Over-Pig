package com.piglatin.y.infrastructure.codegen.c3d;

import com.piglatin.y.domain.ast.statements.NodeFieldDeclaration;
import com.piglatin.y.domain.ast.statements.NodeStructureDefinition;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Layout en bytes de los structs de Y.
 *
 * Reglas:
 *  - El campo 0 vive en Heap[struct + 0].
 *  - flotante: alineado a 8, ocupa 8 bytes.
 *  - Cualquier otro campo: 4 bytes
 *  - los structs y arreglos anidados son punteros al heap.
 *  - totalSize = tamaño redondeado a 8.
 */
public class StructLayout {

    public record FieldInfo(String name, String type, boolean isArray, int arraySize, int offset) {
        /** Tipo de Y del campo: "entero", "Punto", "entero[]", ... */
        public String yType() {
            return isArray ? type + "[]" : type;
        }
    }

    private final Map<String, Map<String, FieldInfo>> structs = new LinkedHashMap<>();
    private final Map<String, Integer> sizes = new LinkedHashMap<>();

    public static int alignUp(int value, int alignment) {
        return (value + alignment - 1) / alignment * alignment;
    }

    public void registerStruct(NodeStructureDefinition def) {
        if (def == null) return;
        String name = def.getName();
        if (structs.containsKey(name)) return;

        Map<String, FieldInfo> fields = new LinkedHashMap<>();
        int offset = 0;

        if (def.getFields() != null) {
            for (NodeFieldDeclaration f : def.getFields()) {
                if (f == null) continue;
                boolean isDouble = !f.isArray() && "flotante".equals(f.getType());
                int size = isDouble ? 8 : 4;
                if (isDouble) offset = alignUp(offset, 8);
                fields.put(f.getFieldName(),
                        new FieldInfo(f.getFieldName(), f.getType(), f.isArray(), f.getArraySize(), offset));
                offset += size;
            }
        }

        structs.put(name, fields);
        sizes.put(name, alignUp(offset, 8));
    }

    public boolean hasStruct(String name) {
        return name != null && structs.containsKey(name);
    }

    /** Tamaño en bytes, redondeado a 8. */
    public int totalSize(String name) {
        Integer size = sizes.get(name);
        if (size == null) {
            throw new IllegalStateException("StructLayout: estructura '" + name + "' no registrada.");
        }
        return size;
    }

    /** Offset en bytes del campo dentro del struct. */
    public int offsetOf(String name, String fieldName) {
        return field(name, fieldName).offset();
    }

    /** Tipo de Y del campo (con sufijo [] si es arreglo). */
    public String fieldType(String name, String fieldName) {
        return field(name, fieldName).yType();
    }

    public boolean isArrayField(String name, String fieldName) {
        return field(name, fieldName).isArray();
    }

    /** Campos en orden de declaración. */
    public Collection<FieldInfo> fieldsOf(String name) {
        Map<String, FieldInfo> fields = structs.get(name);
        if (fields == null) {
            throw new IllegalStateException("StructLayout: estructura '" + name + "' no registrada.");
        }
        return Collections.unmodifiableCollection(fields.values());
    }

    private FieldInfo field(String name, String fieldName) {
        Map<String, FieldInfo> fields = structs.get(name);
        if (fields == null || !fields.containsKey(fieldName)) {
            throw new IllegalStateException(
                    "StructLayout: campo '" + fieldName + "' no encontrado en estructura '" + name + "'.");
        }
        return fields.get(fieldName);
    }
}