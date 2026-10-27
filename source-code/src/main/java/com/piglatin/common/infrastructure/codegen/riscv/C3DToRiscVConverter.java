package com.piglatin.common.infrastructure.codegen.riscv;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.instructions.arithmetic.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.calls.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.constants.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.controlflow.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.conversions.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.memory.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Traduce C3D a ensamblador RISC-V para RARS.
 *
 * Responsabilidades de esta clase: analizar las instrucciones (etiquetas, funciones, prólogos),
 * delimitar cada función, coordinar las dos pasadas de traducción, emitir el prólogo con el frame real
 * y generar las secciones .data / .text. La traducción instrucción a instrucción la hace
 *  Translator, que se instancia con un RiscVTranslationContext que mantiene el estado de la traducción.
 *
 * Modelo:
 *  - Los temporales del visitor ("v0", "v1", ...) viven en slots de 8 bytes del frame,
 *    debajo de los locales del visitor. Todo lo que no sea "v<digitos>" es un registro físico.
 *  - Scratch enteros: x5, x6, x7 (x7 solo como registro de dirección). Scratch double: f0, f1.
 *  - El prólogo emitido por C3DEmitter.emitPrologue se reconoce y se reemplaza por uno
 *    con el frame ampliado con la zona de spills.
 */
public class C3DToRiscVConverter {

    private static final int PROLOGUE_LENGTH = 6;
    private static final String MAIN = RiscVTranslationContext.MAIN;

    private final List<Instruction> instructions;
    private final Map<String, String> stringLiterals;
    private final Map<String, List<String>> vtables;

    public C3DToRiscVConverter(List<Instruction> instructions,
                               Map<String, String> stringLiterals,
                               Map<String, List<String>> vtables) {
        this.instructions = instructions == null ? List.of() : new ArrayList<>(instructions);
        this.stringLiterals = stringLiterals == null ? Map.of() : stringLiterals;
        this.vtables = vtables == null ? Map.of() : vtables;
    }

    public String convert() {
        //* Contexto nuevo por conversión: pool de doubles vacío, sin estado de conversiones anteriores
        RiscVTranslationContext context = new RiscVTranslationContext();

        Set<String> definedLabels = new HashSet<>();
        Set<String> callTargets = new TreeSet<>();
        Set<String> functionLabels = new HashSet<>();
        functionLabels.add(MAIN);
        for (Instruction ins : instructions) {
            if (ins instanceof Label l) {
                definedLabels.add(l.getName());
            } else if (ins instanceof Call c) {
                callTargets.add(c.getTarget());
                functionLabels.add(c.getTarget());
            }
        }
        for (List<String> methods : vtables.values()) {
            //* se llaman con CALL_INDIRECT
            functionLabels.addAll(methods);
        }

        //  .text
        StringBuilder text = new StringBuilder();
        List<Integer> bounds = new ArrayList<>();
        for (int i = 0; i < instructions.size(); i++) {
            if (instructions.get(i) instanceof Label l && functionLabels.contains(l.getName())) {
                bounds.add(i);
            }
        }
        if (bounds.isEmpty() || bounds.get(0) != 0) {
            bounds.add(0, 0);
        }
        bounds.add(instructions.size());
        for (int b = 0; b < bounds.size() - 1; b++) {
            int start = bounds.get(b);
            int end = bounds.get(b + 1);
            if (start == end) continue;
            text.append(translateFunction(context, start, end));
        }

        //  ensamblado final
        StringBuilder sb = new StringBuilder();
        sb.append("# Generado por Clare :p\n");
        sb.append("        .data\n");
        sb.append("# Strings\n");
        for (Map.Entry<String, String> e : stringLiterals.entrySet()) {
            sb.append(e.getKey()).append(": ").append(stringDirective(e.getValue())).append('\n');
        }
        if (!vtables.isEmpty()) {
            sb.append("# Vtables\n");
            sb.append("        .align 2\n");
            for (Map.Entry<String, List<String>> e : vtables.entrySet()) {
                sb.append("vtable_").append(e.getKey()).append(":\n");
                if (e.getValue().isEmpty()) {
                    sb.append("    .word 0\n");
                }
                for (String method : e.getValue()) {
                    sb.append("    .word ").append(method).append('\n');
                }
            }
        }
        Map<Long, String> doublePool = context.doubleConstants();
        if (!doublePool.isEmpty()) {
            sb.append("# Constantes double\n");
            sb.append("        .align 3\n");
            for (Map.Entry<Long, String> e : doublePool.entrySet()) {
                long bits = e.getKey();
                sb.append(e.getValue()).append(": .word ")
                        .append(String.format("0x%08x, 0x%08x", (int) bits, (int) (bits >>> 32)))
                        .append("    # ").append(Double.longBitsToDouble(bits)).append('\n');
            }
        }

        sb.append("\n        .text\n");
        if (definedLabels.contains(MAIN)) {
            sb.append("        .globl main\n");
        }
        for (String target : callTargets) {
            if (definedLabels.contains(target) && !target.equals(MAIN)) {
                sb.append("        .globl ").append(target).append('\n');
            }
        }
        if (definedLabels.contains(MAIN)) {
            //* Funciona tanto si RARS arranca en "main" como si arranca en la primera instrucción de .text
            sb.append("    j main\n");
        }
        sb.append(text);
        sb.append("\n# Runtime (al final para que la ejecución no empiece dentro de él)\n");
        sb.append(".include \"runtime.asm\"\n");
        return sb.toString();
    }

    private String translateFunction(RiscVTranslationContext context, int start, int end) {
        Instruction first = instructions.get(start);
        boolean inMain = first instanceof Label l && MAIN.equals(l.getName());

        int prologue = findPrologue(start, end);
        context.beginFunction(inMain);
        if (prologue >= 0) {
            ConstInt size = (ConstInt) instructions.get(prologue);
            context.configureFrame(-size.getValue());
        }

        //* Descubrir los temporales que se van a usar y cuántos slots de spill se necesitan
        translateRange(context, start, end, prologue);

        int expected = context.allocateSpillArea();

        // Salida con el frame y los mismos slots
        translateRange(context, start, end, prologue);
        if (context.slotCount() != expected) {
            throw new IllegalStateException("Inconsistencia interna al asignar spills");
        }
        return context.assembly();
    }

    /** Una pasada de traducción sobre [start, end); empieza con salida, argumentos y retorno limpios. */
    private void translateRange(RiscVTranslationContext context, int start, int end, int prologue) {
        context.startPass();
        Translator translator = new Translator(context);
        for (int i = start; i < end; ) {
            if (i == prologue) {
                translatePrologue(context, i);
                i += PROLOGUE_LENGTH;
                continue;
            }
            Instruction ins = instructions.get(i);
            if (isEpilogueStart(ins)) {
                preloadReturnValue(context, i, end);
            }
            context.comment(ins);
            ins.accept(translator);
            i++;
        }
    }

    /**
     * Reconoce lo que emite C3DEmitter.emitPrologue:
     *   vA = -N ; sp = sp + vA ; mem[sp+N-4] = fp ; mem[sp+N-8] = ra ; vB = N-8 ; fp = sp + vB
     */
    private int findPrologue(int start, int end) {
        for (int i = start; i + 1 < end; i++) {
            if (!(instructions.get(i) instanceof ConstInt c) || c.getValue() >= 0) continue;
            if (!(instructions.get(i + 1) instanceof AddInt a)
                    || !"sp".equals(a.getResult()) || !"sp".equals(a.getLeft())
                    || !c.getResult().equals(a.getRight())) continue;

            int n = -c.getValue();
            boolean ok = i + PROLOGUE_LENGTH <= end
                    && instructions.get(i + 2) instanceof StoreInt s1
                    && "sp".equals(s1.getBase()) && "fp".equals(s1.getSource()) && s1.getOffset() == n - 4
                    && instructions.get(i + 3) instanceof StoreInt s2
                    && "sp".equals(s2.getBase()) && "ra".equals(s2.getSource()) && s2.getOffset() == n - 8
                    && instructions.get(i + 4) instanceof ConstInt c2 && c2.getValue() == n - 8
                    && instructions.get(i + 5) instanceof AddInt a2
                    && "fp".equals(a2.getResult()) && "sp".equals(a2.getLeft())
                    && c2.getResult().equals(a2.getRight());
            if (!ok) {
                throw new IllegalStateException("Prólogo no reconocido cerca de: " + instructions.get(i).toC3D());
            }
            return i;
        }
        return -1;
    }

    private void translatePrologue(RiscVTranslationContext context, int i) {
        for (int k = 0; k < PROLOGUE_LENGTH; k++) {
            context.comment(instructions.get(i + k));
        }
        int n = context.totalFrameBytes();
        context.addImm("sp", "sp", -n);
        context.mem("sw", "fp", "sp", n - 4);
        context.mem("sw", "ra", "sp", n - 8);
        context.addImm("fp", "sp", n - 8);
    }

    private boolean isEpilogueStart(Instruction ins) {
        return ins instanceof LoadInt l && "ra".equals(l.getResult())
                && "fp".equals(l.getBase()) && l.getOffset() == 0;
    }

    /**
     * Si tras el epílogo viene ReturnInt/ReturnDouble con un temporal, ese temporal está en
     * fp-offset, pero el epílogo restaura fp. Se carga el valor en a0/fa0 antes del epílogo.
     */
    private void preloadReturnValue(RiscVTranslationContext context, int i, int end) {
        for (int j = i + 1; j < Math.min(end, i + 8); j++) {
            if (instructions.get(j) instanceof MoveInt m && "fp".equals(m.getResult())) {
                if (j + 1 >= end) return;
                Instruction next = instructions.get(j + 1);
                if (next instanceof ReturnInt r && RiscVTranslationContext.isTemp(r.getValue())) {
                    context.line("# (valor de retorno cargado antes del epílogo)");
                    context.loadInt("a0", r.getValue());
                    context.markReturnPreloaded();
                } else if (next instanceof ReturnDouble r && RiscVTranslationContext.isTemp(r.getValue())) {
                    context.line("# (valor de retorno cargado antes del epílogo)");
                    context.loadDouble("fa0", r.getValue());
                    context.markReturnPreloaded();
                }
                return;
            }
        }
    }

    private static String stringDirective(String text) {
        boolean plain = true;
        for (int k = 0; k < text.length(); k++) {
            char c = text.charAt(k);
            if (c < 0x20 || c > 0x7E) {
                plain = false;
                break;
            }
        }
        if (plain) {
            return ".asciz \"" + text.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
        }
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder(".byte ");
        for (byte b : bytes) {
            sb.append(b & 0xFF).append(", ");
        }
        sb.append("0");
        return sb.toString();
    }
}