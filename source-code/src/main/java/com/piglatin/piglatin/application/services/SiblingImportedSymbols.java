package com.piglatin.piglatin.application.services;

import com.piglatin.piglatin.domain.semantic.ImportedSymbols;
import com.piglatin.y.domain.symboltable.FunctionSymbol;
import com.piglatin.y.domain.symboltable.ParameterType;
import com.piglatin.y.domain.symboltable.YSymbolTable;
import com.piglatin.y.domain.types.YTypeTable;
import com.piglatin.y.infrastructure.codegen.c3d.StructLayout;
import com.piglatin.zetariano.domain.types.ZetarianoTypeTable;
import com.piglatin.zetariano.infrastructure.codegen.c3d.VtableLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Adapter: exposes the loaded .z / .y siblings to the PigLatin semantic analysis.
 *
 * It answers with the same tables the C3D visitor uses later (VtableLayout for classes,
 * YSymbolTable for free functions), so a call that passes the type checker is a call that
 * the code generator can resolve. Zetariano and Y type names are translated to the
 * PigLatin vocabulary here, so the domain never sees them.
 */
public class SiblingImportedSymbols implements ImportedSymbols {

    private final VtableLayout zLayout;
    private final ZetarianoTypeTable zTypeTable;
    private final YSymbolTable ySymbolTable;
    private final YTypeTable yTypeTable;
    private final StructLayout yStructLayout;

    public SiblingImportedSymbols(VtableLayout zLayout,
                                  ZetarianoTypeTable zTypeTable,
                                  YSymbolTable ySymbolTable,
                                  YTypeTable yTypeTable,
                                  StructLayout yStructLayout) {
        this.zLayout = zLayout;
        this.zTypeTable = zTypeTable;
        this.ySymbolTable = ySymbolTable;
        this.yTypeTable = yTypeTable;
        this.yStructLayout = yStructLayout;
    }

    @Override
    public Set<String> classNames() {
        Set<String> names = new HashSet<>();
        if (zTypeTable != null && zTypeTable.getUserDefinedTypesView() != null) {
            for (String name : zTypeTable.getUserDefinedTypesView()) names.add(name);
        }
        return names;
    }

    @Override
    public Set<String> structNames() {
        Set<String> names = new HashSet<>();
        if (yTypeTable != null && yTypeTable.getUserDefinedTypesView() != null) {
            for (String name : yTypeTable.getUserDefinedTypesView()) names.add(name);
        }
        return names;
    }

    @Override
    public Map<String, String> inheritance() {
        return zTypeTable != null ? zTypeTable.getInheritanceMap() : Map.of();
    }

    @Override
    public boolean isStruct(String name) {
        return name != null && (structNames().contains(name)
                || (yStructLayout != null && yStructLayout.hasStruct(name)));
    }

    @Override
    public Signature resolveMethod(String className, String methodName, int arity) {
        if (zLayout == null || !isClass(className)) return null;
        VtableLayout.MethodInfo method = zLayout.resolveMethod(className, methodName, arity);
        return method == null ? null
                : new Signature(method.name(), paramTypes(method), toPigType(method.returnType()));
    }

    @Override
    public Signature resolveConstructor(String className, int arity) {
        if (zLayout == null || !isClass(className)) return null;
        VtableLayout.MethodInfo ctor = zLayout.constructor(className, arity);
        return ctor == null ? null : new Signature(className, paramTypes(ctor), "VOID");
    }

    @Override
    public Signature resolveFunction(String name, int arity) {
        if (ySymbolTable == null) return null;
        FunctionSymbol function = ySymbolTable.lookupFunction(name, arity);
        if (function == null) return null;

        List<String> params = new ArrayList<>();
        if (function.getParameterTypes() != null) {
            for (ParameterType param : function.getParameterTypes()) {
                String base = toPigType(param.getType());
                params.add(param.isArray() ? "SERIES_" + base : base);
            }
        }
        return new Signature(name, params, toPigType(function.getReturnType()));
    }

    @Override
    public String fieldType(String className, String fieldName) {
        if (zLayout == null || !isClass(className)) return null;
        VtableLayout.FieldInfo field = zLayout.field(className, fieldName);
        return field == null ? null : toPigType(field.typeName());
    }

    private List<String> paramTypes(VtableLayout.MethodInfo method) {
        List<String> params = new ArrayList<>();
        if (method.paramTypes() != null) {
            for (String type : method.paramTypes()) params.add(toPigType(type));
        }
        return params;
    }

    static String toPigType(String typeName) {
        if (typeName == null || typeName.isBlank()) return "VOID";

        String base = typeName.trim();
        int dimensions = 0;
        while (base.endsWith("[]")) {
            base = base.substring(0, base.length() - 2);
            dimensions++;
        }
        while (base.startsWith("SERIES_")) {
            base = base.substring("SERIES_".length());
            dimensions++;
        }

        String pig = switch (base) {
            case "int", "entero", "NUMERUS" -> "NUMERUS";
            case "double", "flotante", "DECIMALIS" -> "DECIMALIS";
            case "String", "cadena", "TEXTUM" -> "TEXTUM";
            case "char", "caracter", "LITTERA" -> "LITTERA";
            case "boolean", "bool", "BOOL", "BOOLEAN" -> "BOOL";
            case "void", "VOID" -> "VOID";
            case "null" -> "null";
            default -> base;
        };
        return "SERIES_".repeat(dimensions) + pig;
    }
}