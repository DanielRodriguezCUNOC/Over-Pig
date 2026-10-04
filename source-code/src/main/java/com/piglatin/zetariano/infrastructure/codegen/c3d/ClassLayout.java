package com.piglatin.zetariano.infrastructure.codegen.c3d;

import com.piglatin.zetariano.domain.ast.principal.ASTNode;
import com.piglatin.zetariano.domain.ast.statements.NodeClassDeclaration;
import com.piglatin.zetariano.domain.ast.statements.NodeFieldDeclaration;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;


public class ClassLayout {

    private final Map<String, Map<String, Integer>> fieldOffsets = new LinkedHashMap<>();
    private final Map<String, Map<String, Boolean>> arrayFields = new LinkedHashMap<>();
    @Getter
    private final Map<String, Integer> classTags = new LinkedHashMap<>();

    private int nextTag = 1;

    public void registerClass(NodeClassDeclaration classDecl) {
        if (classDecl == null) return;
        String className = classDecl.getName();
        if (fieldOffsets.containsKey(className)) return;

        Map<String, Integer> offsets = new LinkedHashMap<>();
        Map<String, Boolean> arrays = new LinkedHashMap<>();

        int offset = 1;
        if (classDecl.getMembers() != null) {
            for (ASTNode member : classDecl.getMembers()) {
                if (member instanceof NodeFieldDeclaration f) {
                    offsets.put(f.getName(), offset);
                    arrays.put(f.getName(), f.isArray());
                    offset++;
                }
            }
        }

        fieldOffsets.put(className, offsets);
        arrayFields.put(className, arrays);
        classTags.put(className, nextTag++);
    }

    public boolean hasClass(String className) {
        return className != null && fieldOffsets.containsKey(className);
    }

    public int totalSize(String className) {
        Map<String, Integer> offsets = fieldOffsets.get(className);
        if (offsets == null) {
            throw new IllegalStateException(
                    "ClassLayout: clase '" + className + "' no registrada.");
        }
        return 1 + offsets.size();
    }

    public int offsetOf(String className, String fieldName) {
        Map<String, Integer> offsets = fieldOffsets.get(className);
        if (offsets == null || !offsets.containsKey(fieldName)) {
            throw new IllegalStateException(
                    "ClassLayout: campo '" + fieldName + "' no encontrado en clase '" + className + "'.");
        }
        return offsets.get(fieldName);
    }

    public boolean isArrayField(String className, String fieldName) {
        Map<String, Boolean> arrays = arrayFields.get(className);
        return arrays != null && Boolean.TRUE.equals(arrays.get(fieldName));
    }

    public int tagOf(String className) {
        Integer tag = classTags.get(className);
        if (tag == null) {
            throw new IllegalStateException(
                    "ClassLayout: clase '" + className + "' no registrada (tag).");
        }
        return tag;
    }

}