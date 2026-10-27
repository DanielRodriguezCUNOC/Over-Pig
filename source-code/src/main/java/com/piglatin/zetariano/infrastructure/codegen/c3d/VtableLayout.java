package com.piglatin.zetariano.infrastructure.codegen.c3d;

import com.piglatin.zetariano.domain.ast.enums.AccessModifier;
import com.piglatin.zetariano.domain.symboltable.ArraySymbol;
import com.piglatin.zetariano.domain.symboltable.MethodSymbol;
import com.piglatin.zetariano.domain.symboltable.Symbol;
import com.piglatin.zetariano.domain.symboltable.VariableSymbol;
import com.piglatin.zetariano.domain.symboltable.ZetarianoSymbolTable;
import com.piglatin.zetariano.domain.types.ZetarianoTypeTable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Layout de objetos y vtables de Zetariano.
 *
 * <p>Se deriva de la tabla de símbolos y de la jerarquía de la tabla de tipos, de modo que no
 * depende del orden en que el visitor recorra las clases. El orden de declaración se recupera
 * con la posición (línea, columna) de cada símbolo.
 *
 * <p>Reglas:
 * <ul>
 *   <li>{@code Heap[obj+0]} guarda el puntero a la vtable.</li>
 *   <li>Los campos empiezan en {@code +4}, en orden de declaración, con los heredados primero.
 *       Un {@code double} ocupa 8 bytes y se alinea a 8; el resto ocupa 4.</li>
 *   <li>El tamaño del objeto se redondea a múltiplo de 8.</li>
 *   <li>La vtable de una clase hereda la de su padre. Un método que repite nombre y aridad
 *       ocupa el mismo slot (sobrescritura); los nuevos se añaden al final. Los métodos
 *       privados y los constructores no entran en la vtable. Cada slot ocupa 4 bytes.</li>
 * </ul>
 */
public class VtableLayout {

    public static final int OBJECT_HEADER = 4;
    public static final int VTABLE_SLOT_SIZE = 4;
    private static final int WORD = 4;
    private static final int DOUBLE_WORD = 8;

    /** Campo de instancia con su offset dentro del objeto. */
    public record FieldInfo(String name, String typeName, int offset) {
    }

    /** Método resuelto. {@code arity()} cuenta {@code this}, como en las etiquetas generadas. */
    public record MethodInfo(String declaringClass, String name, String returnType,
                             List<String> paramTypes, boolean isPrivate) {

        public int arity() {
            return paramTypes.size() + 1;
        }

        public String label() {
            return declaringClass + "_" + name + "_" + arity();
        }
    }

    private static final class ClassInfo {
        private final Map<String, FieldInfo> fields = new LinkedHashMap<>();
        private final List<MethodInfo> vtable = new ArrayList<>();
        private final Map<String, Integer> slots = new HashMap<>();
        private int fieldsEnd = OBJECT_HEADER;
    }

    private static final Comparator<Symbol> BY_POSITION =
            Comparator.comparingInt(Symbol::getLine).thenComparingInt(Symbol::getColumn);

    private final ZetarianoSymbolTable symbolTable;
    private final ZetarianoTypeTable typeTable;
    private final Map<String, ClassInfo> classes = new HashMap<>();
    private final Set<String> building = new HashSet<>();

    public VtableLayout(ZetarianoSymbolTable symbolTable, ZetarianoTypeTable typeTable) {
        this.symbolTable = symbolTable;
        this.typeTable = typeTable;
    }

    public String superOf(String className) {
        return typeTable.getInheritanceMap().get(className);
    }

    public String vtableLabel(String className) {
        return "vtable_" + className;
    }

    /** Tamaño en bytes de una instancia, redondeado a múltiplo de 8. */
    public int objectSize(String className) {
        return alignUp(classInfo(className).fieldsEnd, DOUBLE_WORD);
    }

    /** Campo (propio o heredado) o {@code null} si no existe. */
    public FieldInfo field(String className, String fieldName) {
        return classInfo(className).fields.get(fieldName);
    }

    /** Etiquetas de los métodos de la vtable, en orden de slot. */
    public List<String> vtableLabels(String className) {
        List<String> labels = new ArrayList<>();
        for (MethodInfo method : classInfo(className).vtable) {
            labels.add(method.label());
        }
        return labels;
    }

    /** Offset en bytes del método dentro de la vtable, o -1 si no es virtual. */
    public int vtableOffset(String className, String methodName, int paramCount) {
        Integer slot = classInfo(className).slots.get(slotKey(methodName, paramCount));
        return slot == null ? -1 : slot * VTABLE_SLOT_SIZE;
    }

    /** Busca el método en la clase y sus ancestros. {@code paramCount} excluye {@code this}. */
    public MethodInfo resolveMethod(String className, String methodName, int paramCount) {
        Set<String> visited = new HashSet<>();
        String current = className;
        while (current != null && visited.add(current)) {
            MethodSymbol symbol = symbolTable.lookupMethod(current, methodName, paramCount);
            if (symbol != null) {
                return toMethodInfo(current, symbol);
            }
            current = superOf(current);
        }
        return null;
    }

    /** Constructor declarado en la propia clase con esa cantidad de parámetros, o {@code null}. */
    public MethodInfo constructor(String className, int paramCount) {
        MethodSymbol symbol = symbolTable.lookupMethod(className, className, paramCount);
        return symbol == null ? null : toMethodInfo(className, symbol);
    }

    private ClassInfo classInfo(String className) {
        ClassInfo cached = classes.get(className);
        if (cached != null) {
            return cached;
        }
        if (!building.add(className)) {
            throw new IllegalStateException("VtableLayout: herencia cíclica en '" + className + "'.");
        }
        ClassInfo info = buildClassInfo(className);
        building.remove(className);
        classes.put(className, info);
        return info;
    }

    private ClassInfo buildClassInfo(String className) {
        ClassInfo info = new ClassInfo();
        String superName = superOf(className);
        if (superName != null) {
            ClassInfo parent = classInfo(superName);
            info.fields.putAll(parent.fields);
            info.vtable.addAll(parent.vtable);
            info.slots.putAll(parent.slots);
            info.fieldsEnd = parent.fieldsEnd;
        }
        layoutOwnFields(className, info);
        layoutOwnMethods(className, info);
        return info;
    }

    private void layoutOwnFields(String className, ClassInfo info) {
        Map<String, Symbol> own = symbolTable.getClassFields(className);
        if (own == null) {
            return;
        }
        own.values().stream()
                .filter(symbol -> symbol instanceof VariableSymbol || symbol instanceof ArraySymbol)
                .sorted(BY_POSITION)
                .forEach(symbol -> addField(info, symbol));
    }

    private void addField(ClassInfo info, Symbol symbol) {
        String typeName = fieldTypeName(symbol);
        boolean isDouble = "double".equals(typeName);
        int offset = isDouble ? alignUp(info.fieldsEnd, DOUBLE_WORD) : info.fieldsEnd;
        info.fieldsEnd = offset + (isDouble ? DOUBLE_WORD : WORD);
        info.fields.put(symbol.getName(), new FieldInfo(symbol.getName(), typeName, offset));
    }

    private String fieldTypeName(Symbol symbol) {
        if (symbol instanceof ArraySymbol array) {
            return array.getElementType() + "[]".repeat(array.getDimensions());
        }
        return ((VariableSymbol) symbol).getType();
    }

    private void layoutOwnMethods(String className, ClassInfo info) {
        Map<String, MethodSymbol> own = symbolTable.getClassMethods(className);
        if (own == null) {
            return;
        }
        own.values().stream()
                .filter(method -> !method.getName().equals(className))
                .filter(method -> !isPrivate(method))
                .sorted(BY_POSITION)
                .forEach(method -> addVirtualMethod(className, info, method));
    }

    private void addVirtualMethod(String className, ClassInfo info, MethodSymbol method) {
        MethodInfo methodInfo = toMethodInfo(className, method);
        String key = slotKey(method.getName(), method.getParameterTypes().size());
        Integer existing = info.slots.get(key);
        if (existing != null) {
            info.vtable.set(existing, methodInfo);
            return;
        }
        info.slots.put(key, info.vtable.size());
        info.vtable.add(methodInfo);
    }

    private MethodInfo toMethodInfo(String declaringClass, MethodSymbol symbol) {
        return new MethodInfo(declaringClass, symbol.getName(), symbol.getReturnType(),
                symbol.getParameterTypes(), isPrivate(symbol));
    }

    private boolean isPrivate(MethodSymbol symbol) {
        return symbol.getModifier() == AccessModifier.PRIVATE;
    }

    private String slotKey(String methodName, int paramCount) {
        return methodName + "/" + paramCount;
    }

    private static int alignUp(int value, int alignment) {
        return (value + alignment - 1) / alignment * alignment;
    }
}