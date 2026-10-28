package com.piglatin.common.infrastructure.codegen.riscv;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.instructions.arithmetic.AddInt;
import com.piglatin.common.infrastructure.codegen.ir.instructions.arithmetic.DivInt;
import com.piglatin.common.infrastructure.codegen.ir.instructions.arithmetic.MulInt;
import com.piglatin.common.infrastructure.codegen.ir.instructions.arithmetic.NegInt;
import com.piglatin.common.infrastructure.codegen.ir.instructions.arithmetic.SubInt;
import com.piglatin.common.infrastructure.codegen.ir.instructions.calls.Call;
import com.piglatin.common.infrastructure.codegen.ir.instructions.constants.ConstInt;
import com.piglatin.common.infrastructure.codegen.ir.instructions.controlflow.BranchFalse;
import com.piglatin.common.infrastructure.codegen.ir.instructions.controlflow.BranchTrue;
import com.piglatin.common.infrastructure.codegen.ir.instructions.controlflow.Jump;
import com.piglatin.common.infrastructure.codegen.ir.instructions.controlflow.Label;
import com.piglatin.common.infrastructure.codegen.ir.instructions.conversions.MoveInt;
import com.piglatin.common.infrastructure.codegen.ir.instructions.runtime.Alloc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Estimador estático del tamaño del bloque de heap que se reserva en .data.
 *
 * El heap es un bump allocator sin free, así que el tamaño necesario es la suma de todo lo que el
 * programa puede pedir. Como no se puede saber en compilación cuántas veces se ejecuta cada Alloc,
 * se estima así:
 *
 *   1. Suma estática: cada Alloc con tamaño conocido suma ese tamaño (alineado a 8); si el tamaño
 *      no se puede deducir, suma UNKNOWN_ALLOC_BYTES. Las llamadas al runtime que reservan heap
 *      suman una estimación fija (__concat, __int_to_str, __double_to_str).
 *      El tamaño "conocido" incluye propagación de constantes: new int[100] se calcula con
 *      MulInt/AddInt/DivInt sobre ConstInt, y se resuelve a su valor real.
 *   2. Bucles: una instrucción está "en bucle" si queda entre un Label y el último salto hacia atrás
 *      que apunta a ese Label. Lo que reserva dentro de un bucle se multiplica por MAX_LOOP_ITERATIONS.
 *      No se multiplica por anidamiento (un bucle dentro de otro cuenta 1000x, no 1000000x).
 *   3. Margen: se suman SAFETY_MARGIN bytes para imprevistos.
 *   4. Se redondea a PAGE_SIZE y se impone MIN_HEAP_SIZE como mínimo.
 *   5. fitToDataSegment: el resultado nunca puede pasar de lo que cabe en el segmento .data de RARS
 *      (menos el resto de .data). Si la estimación es mayor, se recorta al máximo disponible y se
 *      avisa por stderr: el multiplicador de bucles es deliberadamente pesimista (no se conoce el
 *      número de iteraciones) y rechazar el programa por eso sería peor que dejar que el bounds
 *      check de ejecución decida.
 *
 * Limitaciones conocidas (cubiertas solo por SAFETY_MARGIN):
 *   - Lo que reserva una función llamada desde un bucle o recursiva se cuenta una sola vez.
 *   - Un bucle que itera más de MAX_LOOP_ITERATIONS veces puede agotar el heap: en ese caso el
 *     programa aborta en ejecución con "Out of memory".
 *   - ConstString no suma: los literales viven en .data y no consumen heap.
 */
public final class HeapEstimator {

    public static final int MAX_LOOP_ITERATIONS = 1000;
    public static final int SAFETY_MARGIN = 16384;
    public static final int MIN_HEAP_SIZE = 65536;
    public static final int PAGE_SIZE = 4096;

    /** Tamaño asumido para un Alloc cuyo tamaño depende de datos de ejecución (new int[n]). */
    public static final int UNKNOWN_ALLOC_BYTES = 1024;
    public static final int CONCAT_BYTES = 64;
    public static final int INT_TO_STR_BYTES = 16;
    public static final int DOUBLE_TO_STR_BYTES = 32;

    /**
     * Tamaño del segmento .data estático de RARS (0x10010000 - 0x1003ffff) en su configuración por
     * defecto. Verificar contra la versión de RARS en uso.
     */
    public static final int DATA_SEGMENT_BYTES = 0x30000;

    private static final Pattern TEMP = Pattern.compile("v\\d+");

    private HeapEstimator() {
    }

    /** El bloque pedido no cabe en el segmento .data de RARS. */
    public static final class HeapLimitExceededException extends RuntimeException {
        public HeapLimitExceededException(String message) {
            super(message);
        }
    }

    /**
     * Estimación ideal del heap, sin considerar el límite de RARS.
     *
     * @return bytes del bloque estático, múltiplo de PAGE_SIZE y al menos MIN_HEAP_SIZE
     */
    public static int estimate(List<Instruction> instructions) {
        List<Instruction> ins = instructions == null ? List.of() : instructions;
        Map<String, Long> constants = propagateConstants(ins);
        boolean[] inLoop = markLoops(ins);

        long outsideLoops = 0;
        long insideLoops = 0;
        for (int i = 0; i < ins.size(); i++) {
            long bytes = bytesOf(ins.get(i), constants);
            if (bytes == 0) continue;
            if (inLoop[i]) insideLoops += bytes; else outsideLoops += bytes;
        }

        long total = outsideLoops + insideLoops * MAX_LOOP_ITERATIONS + SAFETY_MARGIN;
        long finalBytes = Math.max(alignUp(total, PAGE_SIZE), MIN_HEAP_SIZE);

        return (int) Math.min(finalBytes, Integer.MAX_VALUE);
    }

    /**
     * Ajusta la estimación a lo que cabe en el segmento .data junto con el resto de datos
     * (strings, vtables, doubles). Si no cabe se recorta al máximo disponible (múltiplo de PAGE_SIZE)
     * y se avisa por stderr.
     *
     * @throws HeapLimitExceededException solo si no queda ni una página para el heap
     */
    static int fitToDataSegment(int desiredBytes, long otherDataBytes) {
        long available = (DATA_SEGMENT_BYTES - otherDataBytes) / PAGE_SIZE * PAGE_SIZE;
        if (available < PAGE_SIZE) {
            throw new HeapLimitExceededException(String.format(
                    "Strings, vtables y doubles ocupan %d bytes y no dejan espacio para el heap "
                            + "en el segmento .data de RARS (%d bytes).", otherDataBytes, DATA_SEGMENT_BYTES));
        }
        if (desiredBytes <= available) return desiredBytes;
        System.err.printf("[HeapEstimator] Heap estimado de %d bytes no cabe en .data (quedan %d); "
                        + "se reservan %d. Si el programa agota el heap abortará con \"Out of memory\".%n",
                desiredBytes, available, available);
        return (int) available;
    }


    // Bytes que una instrucción reserva del heap en una ejecución

    private static long bytesOf(Instruction ins, Map<String, Long> constants) {
        if (ins instanceof Alloc a) {
            Long size = constants.get(a.getSize());
            if (size == null || size < 0) return UNKNOWN_ALLOC_BYTES;
            return alignUp(size, 8);
        }
        if (ins instanceof Call c) {
            return switch (c.getTarget()) {
                case "__concat" -> CONCAT_BYTES;
                case "__int_to_str" -> INT_TO_STR_BYTES;
                case "__double_to_str" -> DOUBLE_TO_STR_BYTES;
                default -> 0;
            };
        }
        return 0;
    }


    // Bucles

    /**
     * inLoop[k] es true si la instrucción k está entre un Label L (índice i) y el último salto
     * (Jump/BranchTrue/BranchFalse) con destino L cuyo índice j > i, ambos inclusive.
     */
    private static boolean[] markLoops(List<Instruction> ins) {
        int n = ins.size();
        Map<String, Integer> labelIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (ins.get(i) instanceof Label l) labelIndex.putIfAbsent(l.getName(), i);
        }

        // Para cada Label, el índice del último salto hacia atrás que lo tiene como destino
        Map<Integer, Integer> lastBackJump = new HashMap<>();
        for (int j = 0; j < n; j++) {
            String target = jumpTarget(ins.get(j));
            if (target == null) continue;
            Integer i = labelIndex.get(target);
            if (i != null && j > i) lastBackJump.merge(i, j, Math::max);
        }

        // Array de diferencias para marcar los rangos en O(n)
        int[] delta = new int[n + 1];
        for (Map.Entry<Integer, Integer> e : lastBackJump.entrySet()) {
            delta[e.getKey()]++;
            delta[e.getValue() + 1]--;
        }
        boolean[] inLoop = new boolean[n];
        int depth = 0;
        for (int k = 0; k < n; k++) {
            depth += delta[k];
            inLoop[k] = depth > 0;
        }
        return inLoop;
    }

    private static String jumpTarget(Instruction ins) {
        if (ins instanceof Jump j) return j.getTarget();
        if (ins instanceof BranchTrue b) return b.getTarget();
        if (ins instanceof BranchFalse b) return b.getTarget();
        return null;
    }


    // Propagación de constantes

    /**
     * Valor de los temporales (v<N>) que valen siempre lo mismo: los definidos exactamente una vez,
     * por un ConstInt o por aritmética entera / MoveInt sobre otras constantes.
     * Los tamaños de arrays con longitud fija salen de aquí.
     */
    private static Map<String, Long> propagateConstants(List<Instruction> ins) {
        Map<String, Integer> definitions = new HashMap<>();
        for (Instruction i : ins) {
            String res = resultOf(i);
            if (res != null && TEMP.matcher(res).matches()) definitions.merge(res, 1, Integer::sum);
        }

        Map<String, Long> values = new HashMap<>();
        for (Instruction i : ins) {
            String res = resultOf(i);
            if (res == null || definitions.getOrDefault(res, 0) != 1) continue;

            Long v = null;
            if (i instanceof ConstInt c) {
                v = (long) c.getValue();
            } else if (i instanceof MoveInt m) {
                v = values.get(m.getSource());
            } else if (i instanceof NegInt n) {
                Long a = values.get(n.getOperand());
                if (a != null) v = -a;
            } else if (i instanceof AddInt a) {
                v = binary(values, a.getLeft(), a.getRight(), Long::sum);
            } else if (i instanceof SubInt s) {
                v = binary(values, s.getLeft(), s.getRight(), (x, y) -> x - y);
            } else if (i instanceof MulInt m) {
                v = binary(values, m.getLeft(), m.getRight(), (x, y) -> x * y);
            } else if (i instanceof DivInt d) {
                Long r = values.get(d.getRight());
                if (r != null && r != 0) v = binary(values, d.getLeft(), d.getRight(), (x, y) -> x / y);
            }
            if (v != null && v >= Integer.MIN_VALUE && v <= Integer.MAX_VALUE) values.put(res, v);
        }
        return values;
    }

    private static Long binary(Map<String, Long> values, String l, String r,
                               java.util.function.BinaryOperator<Long> op) {
        Long a = values.get(l);
        Long b = values.get(r);
        return (a == null || b == null) ? null : op.apply(a, b);
    }

    /** Destino que escribe la instrucción, si tiene getResult() (todas las que definen un valor). */
    private static String resultOf(Instruction ins) {
        try {
            Method m = ins.getClass().getMethod("getResult");
            return m.invoke(ins) instanceof String s ? s : null;
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    private static long alignUp(long v, long align) {
        return (v + align - 1) / align * align;
    }
}