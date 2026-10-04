package com.piglatin.y.infrastructure.codegen.c3d;

import com.piglatin.y.domain.ast.statements.NodeFieldDeclaration;
import com.piglatin.y.domain.ast.statements.NodeStructureDefinition;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;


public class StructLayout {

    private final Map<String, Map<String, Integer>> fieldOffsets = new LinkedHashMap<>();
    private final Map<String, Map<String, Boolean>> arrayFields = new LinkedHashMap<>();
    @Getter
    private final Map<String, Integer> structTags = new LinkedHashMap<>();

    private int nextTag = 1;

    public void registerStruct(NodeStructureDefinition def) {
        if (def == null) return;
        String name = def.getName();
        if (fieldOffsets.containsKey(name)) return;

        Map<String, Integer> offsets = new LinkedHashMap<>();
        Map<String, Boolean> arrays = new LinkedHashMap<>();

        int offset = 1; // slot 0 = tag
        if (def.getFields() != null) {
            for (NodeFieldDeclaration f : def.getFields()) {
                if (f == null) continue;
                offsets.put(f.getFieldName(), offset);
                arrays.put(f.getFieldName(), f.isArray());
                offset++;
            }
        }

        fieldOffsets.put(name, offsets);
        arrayFields.put(name, arrays);
        structTags.put(name, nextTag++);
    }

    public boolean hasStruct(String name) {
        return name != null && fieldOffsets.containsKey(name);
    }

    public int totalSize(String structName) {
        Map<String, Integer> offsets = fieldOffsets.get(structName);
        if (offsets == null) {
            throw new IllegalStateException(
                    "StructLayout: estructura '" + structName + "' no registrada.");
        }
        return 1 + offsets.size();
    }

    public int offsetOf(String structName, String fieldName) {
        Map<String, Integer> offsets = fieldOffsets.get(structName);
        if (offsets == null || !offsets.containsKey(fieldName)) {
            throw new IllegalStateException(
                    "StructLayout: campo '" + fieldName + "' no encontrado en estructura '" + structName + "'.");
        }
        return offsets.get(fieldName);
    }

    public boolean isArrayField(String structName, String fieldName) {
        Map<String, Boolean> arrays = arrayFields.get(structName);
        return arrays != null && Boolean.TRUE.equals(arrays.get(fieldName));
    }

    public int tagOf(String structName) {
        Integer tag = structTags.get(structName);
        if (tag == null) {
            throw new IllegalStateException(
                    "StructLayout: estructura '" + structName + "' no registrada (tag).");
        }
        return tag;
    }
}