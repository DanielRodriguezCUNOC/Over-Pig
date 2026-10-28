package com.piglatin.common.infrastructure.codegen.riscv;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Estado y operaciones de emisión de ensamblador RISC-V
 *
 * Modelo:
 *
 *   Los temporales del visitor viven en slots de 8 bytes del frame,
 *       debajo de los locales del visitor. Lo que no sea v<dígitos> es un registro físico.
 *   Scratch enteros: x5, x6, x7 (x7 solo como registro de dirección). Scratch double: f0, f1.
 *
 *
 * Ciclo de vida del estado:
 *
 *   Por conversión (una instancia nueva por convert()): pool de constantes double.
 *       Persiste entre funciones y entre pasadas para que las etiquetas  __dbl_N sean estables.
 *   Por función : función actual, frame,
 *       mapa de slots y zona de spills. El mapa de slots persiste entre las dos pasadas de una misma
 *       función: así los offsets de los temporales son idénticos en ambas.
 *   Por pasada: salida, contadores de argumentos y retorno precargado.
 */
public final class RiscVTranslationContext {

    /** Etiqueta del punto de entrada del programa. */
    static final String MAIN = "main";

    //* Registros scratch
    static final String S0 = "x5";
    static final String S1 = "x6";
    static final String S2 = "x7";
    static final String F0 = "f0";
    static final String F1 = "f1";

    /** Heap pointer: próximo byte libre del bloque estático */
    static final String HEAP_PTR = "s11";
    /** Fin del bloque estático para el bounds check. */
    static final String HEAP_END = "s10";
    /** Rutina de runtime.asm que imprime "Out of memory" y sale con código 10. */
    static final String OOM_LABEL = "__oom_error";

    private static final int SLOT = 8;
    private static final int FRAME_ALIGN = 16;
    private static final int SAVED_AREA = 8;
    private static final int MAX_ARG_REGS = 8;

    private static final Pattern TEMP = Pattern.compile("v\\d+");
    private static final Pattern LEGACY_TEMP = Pattern.compile("t(\\d+)");

    //* Por conversión: pool de constantes double (bits -> label)
    private final Map<Long, String> doublePool = new LinkedHashMap<>();

    //* Por conversión: contador de etiquetas de bounds check. No se reinicia entre funciones ni
    //* pasadas, así que las etiquetas __heap_ok_N son únicas en todo el archivo.
    private int heapCheckCounter;

    // Por función
    private final Map<String, Integer> slots = new LinkedHashMap<>();
    private boolean inMain;
    private boolean hasFrame;
    private int frameBytes;     //! N: tamaño de frame que pidió el visitor
    private int localBytes;     //! N - 8: bytes de locales (desde sp hasta fp)
    private int spillBytes;     //! zona de spills añadida

    // Por pasada
    private StringBuilder out = new StringBuilder();
    private int intArgs;
    private int dblArgs;
    private boolean retPreloaded;


    // Ciclo de vida: función y pasadas


    /**
     * Comienza una nueva función: descarta frame, slots y spills de la anterior.
     * La función se considera sin frame hasta que se llame a configureFrame.
     */
    void beginFunction(boolean inMain) {
        this.inMain = inMain;
        this.hasFrame = false;
        this.frameBytes = 0;
        this.localBytes = 0;
        this.spillBytes = 0;
        this.slots.clear();
    }

    /** Declara el frame pedido por el prólogo del visitor (N bytes, incluida el área de fp/ra). */
    void configureFrame(int frameBytes) {
        this.hasFrame = true;
        this.frameBytes = frameBytes;
        this.localBytes = frameBytes - SAVED_AREA;
    }

    /**
     * Comienza una pasada de traducción: descarta la salida, los contadores de argumentos y el
     * retorno precargado. Conserva slots, spills y pool de doubles.
     */
    void startPass() {
        this.out = new StringBuilder();
        this.intArgs = 0;
        this.dblArgs = 0;
        this.retPreloaded = false;
    }

    /**
     * Fija la zona de spills a partir de los temporales descubiertos hasta ahora
     */
    int allocateSpillArea() {
        spillBytes = roundUp(slots.size() * SLOT, FRAME_ALIGN);
        return slots.size();
    }

    int slotCount() {
        return slots.size();
    }

    /** Tamaño real del frame: el pedido por el visitor más la zona de spills. */
    int totalFrameBytes() {
        return frameBytes + spillBytes;
    }

    /** Ensamblador emitido en la pasada actual. */
    String assembly() {
        return out.toString();
    }

    /** Vista de solo lectura del pool de constantes double (bits -> label), en orden de alta. */
    Map<Long, String> doubleConstants() {
        return Collections.unmodifiableMap(doublePool);
    }


    // Estado de retorno y llamadas


    boolean isInMain() {
        return inMain;
    }

    /** Indica que el valor de retorno ya se cargó en a0/fa0 antes del epílogo. */
    void markReturnPreloaded() {
        retPreloaded = true;
    }

    /** Devuelve si el retorno estaba precargado y limpia la marca. */
    boolean consumeReturnPreloaded() {
        boolean preloaded = retPreloaded;
        retPreloaded = false;
        return preloaded;
    }

    /** Carga un argumento entero en el siguiente registro a0-a7. */
    void loadIntArgument(String op) {
        if (intArgs >= MAX_ARG_REGS) throw new IllegalStateException("Más de 8 ParamInt");
        loadInt("a" + intArgs++, op);
    }

    /** Carga un argumento double en el siguiente registro fa0-fa7. */
    void loadDoubleArgument(String op) {
        if (dblArgs >= MAX_ARG_REGS) throw new IllegalStateException("Más de 8 ParamDouble");
        loadDouble("fa" + dblArgs++, op);
    }

    /** Valida que los ParamInt/ParamDouble acumulados coinciden con la llamada y reinicia los contadores. */
    void checkCall(String target, int nInt, int nDouble) {
        if (intArgs != nInt || dblArgs != nDouble) {
            throw new IllegalStateException("Llamada a " + target + " declara (" + nInt + " int, " + nDouble
                    + " double) pero hubo " + intArgs + " ParamInt y " + dblArgs + " ParamDouble");
        }
        intArgs = 0;
        dblArgs = 0;
    }


    // Emisión


    void line(String s) {
        out.append("    ").append(s).append('\n');
    }

    void label(String name) {
        out.append(name).append(":\n");
    }

    void comment(Instruction ins) {
        out.append("    # ").append(ins.toC3D().replace('\n', ' ')).append('\n');
    }

    void addImm(String rd, String rs, int imm) {
        if (fits12(imm)) {
            line("addi " + rd + ", " + rs + ", " + imm);
        } else {
            line("li " + S2 + ", " + imm);
            line("add " + rd + ", " + rs + ", " + S2);
        }
    }

    /** lw/sw/fld/fsd/lbu/sb con soporte de offsets fuera de rango de 12 bits. */
    void mem(String op, String reg, String base, int off) {
        if (fits12(off)) {
            line(op + " " + reg + ", " + off + "(" + base + ")");
        } else {
            line("li " + S2 + ", " + off);
            line("add " + S2 + ", " + S2 + ", " + base);
            line(op + " " + reg + ", 0(" + S2 + ")");
        }
    }

    /**
     * Bump allocator inline sobre el bloque estático. Sin syscalls.
     *
     * resultTemp = dirección alineada a 8 del bloque reservado; sizeOp = bytes pedidos (temporal o
     * registro). Usa solo los scratch S0 (tamaño) y S1 (restante / resultado), más s10/s11.
     *
     *   s11 = (s11 + 7) & -8                 heap pointer alineado a 8
     *   S0  = size
     *   S1  = s10 - s11                      bytes libres
     *   if (S0 <= S1 sin signo) sigue       si no: j __oom_error
     *   S0  = (S0 + 7) & -8                  tamaño alineado a 8
     *   S1  = s11 ; s11 += S0
     *   resultTemp = S1
     *
     * El chequeo se hace contra el tamaño sin alinear y restando, no sumando a s11: así un tamaño
     * negativo o enorme no desborda 32 bits y pasa el chequeo. Como s10 - s11 es múltiplo de 8,
     * si size <= libres entonces alinear(size) <= libres. El "j" al error evita el alcance limitado
     * (+-4 KB) de un branch hacia runtime.asm, que está al final del archivo.
     */
    void bumpAllocate(String resultTemp, String sizeOp) {
        String ok = "__heap_ok_" + (heapCheckCounter++);
        line("addi " + HEAP_PTR + ", " + HEAP_PTR + ", 7");
        line("andi " + HEAP_PTR + ", " + HEAP_PTR + ", -8");
        loadInt(S0, sizeOp);
        line("sub " + S1 + ", " + HEAP_END + ", " + HEAP_PTR);
        line("bleu " + S0 + ", " + S1 + ", " + ok);
        line("j " + OOM_LABEL);
        label(ok);
        line("addi " + S0 + ", " + S0 + ", 7");
        line("andi " + S0 + ", " + S0 + ", -8");
        line("mv " + S1 + ", " + HEAP_PTR);
        line("add " + HEAP_PTR + ", " + HEAP_PTR + ", " + S0);
        si(resultTemp, S1);
    }

    void syscall(int number) {
        line("li a7, " + number);
        line("ecall");
    }


    // Operandos: temporales (slots) y registros físicos


    static boolean isTemp(String op) {
        return op != null && TEMP.matcher(op).matches();
    }

    private String phys(String op) {
        if (op == null || op.isBlank()) {
            throw new IllegalStateException("Operando vacío en C3D");
        }
        Matcher m = LEGACY_TEMP.matcher(op);
        if (m.matches() && Integer.parseInt(m.group(1)) > 6) {
            throw new IllegalStateException("El operando '" + op + "' parece un temporal con prefijo 't' "
                    + "(C3DContext.newTemp). Debe llamarse 'v<N>' para distinguirlo de los registros t0-t6.");
        }
        return op.trim();
    }

    /** Offset respecto a fp del slot del temporal; lo registra la primera vez que se pide. */
    private int slotOff(String temp) {
        if (!hasFrame) {
            throw new IllegalStateException("El temporal " + temp + " se usa en una función sin prólogo reconocible");
        }
        Integer idx = slots.get(temp);
        if (idx == null) {
            idx = slots.size();
            slots.put(temp, idx);
        }
        return -(localBytes + SLOT * (idx + 1));
    }

    // enteros
    String ri(String op, String scratch) {
        if (isTemp(op)) {
            mem("lw", scratch, "fp", slotOff(op));
            return scratch;
        }
        return phys(op);
    }

    String wi(String op, String scratch) {
        return isTemp(op) ? scratch : phys(op);
    }

    void si(String op, String reg) {
        if (isTemp(op)) {
            mem("sw", reg, "fp", slotOff(op));
        } else if (!phys(op).equals(reg)) {
            line("mv " + phys(op) + ", " + reg);
        }
    }

    void loadInt(String reg, String op) {
        if (isTemp(op)) {
            mem("lw", reg, "fp", slotOff(op));
        } else if (!phys(op).equals(reg)) {
            line("mv " + reg + ", " + phys(op));
        }
    }

    // doubles
    String rd(String op, String scratch) {
        if (isTemp(op)) {
            mem("fld", scratch, "fp", slotOff(op));
            return scratch;
        }
        return phys(op);
    }

    String wd(String op, String scratch) {
        return isTemp(op) ? scratch : phys(op);
    }

    void sd(String op, String reg) {
        if (isTemp(op)) {
            mem("fsd", reg, "fp", slotOff(op));
        } else if (!phys(op).equals(reg)) {
            line("fmv.d " + phys(op) + ", " + reg);
        }
    }

    void loadDouble(String reg, String op) {
        if (isTemp(op)) {
            mem("fld", reg, "fp", slotOff(op));
        } else if (!phys(op).equals(reg)) {
            line("fmv.d " + reg + ", " + phys(op));
        }
    }

    void move(String dst, String src, boolean dbl) {
        String load = dbl ? "fld" : "lw";
        String store = dbl ? "fsd" : "sw";
        String mv = dbl ? "fmv.d" : "mv";
        String scratch = dbl ? F0 : S0;
        boolean td = isTemp(dst);
        boolean ts = isTemp(src);
        if (td && ts) {
            mem(load, scratch, "fp", slotOff(src));
            mem(store, scratch, "fp", slotOff(dst));
        } else if (td) {
            mem(store, phys(src), "fp", slotOff(dst));
        } else if (ts) {
            mem(load, phys(dst), "fp", slotOff(src));
        } else if (!phys(dst).equals(phys(src))) {
            line(mv + " " + phys(dst) + ", " + phys(src));
        }
    }

    void binInt(String mnemonic, String res, String l, String r) {
        String a = ri(l, S0);
        String b = ri(r, S1);
        String d = wi(res, S0);
        line(mnemonic + " " + d + ", " + a + ", " + b);
        si(res, d);
    }

    void binDouble(String mnemonic, String res, String l, String r) {
        String a = rd(l, F0);
        String b = rd(r, F1);
        String d = wd(res, F0);
        line(mnemonic + " " + d + ", " + a + ", " + b);
        sd(res, d);
    }

    /** Comparación entera; "swap" intercambia operandos, "invert" niega el resultado. */
    void cmpInt(String res, String l, String r, String slt, boolean swap, boolean invert) {
        String a = ri(l, S0);
        String b = ri(r, S1);
        String d = wi(res, S0);
        line(slt + " " + d + ", " + (swap ? b : a) + ", " + (swap ? a : b));
        if (invert) line("xori " + d + ", " + d + ", 1");
        si(res, d);
    }

    void cmpDouble(String res, String l, String r, String op, boolean swap, boolean invert) {
        String a = rd(l, F0);
        String b = rd(r, F1);
        String d = wi(res, S0);
        line(op + " " + d + ", " + (swap ? b : a) + ", " + (swap ? a : b));
        if (invert) line("xori " + d + ", " + d + ", 1");
        si(res, d);
    }

    void loadImm(String dst, long value) {
        String d = wi(dst, S0);
        line("li " + d + ", " + value);
        si(dst, d);
    }

    void mask(String res, String src) {
        String s = ri(src, S0);
        String d = wi(res, S0);
        line("andi " + d + ", " + s + ", 255");
        si(res, d);
    }

    void loadMem(String op, String res, String base, int off, boolean dbl) {
        String b = ri(base, S1);
        String d = dbl ? wd(res, F0) : wi(res, S0);
        mem(op, d, b, off);
        if (dbl) sd(res, d); else si(res, d);
    }

    void storeMem(String op, String src, String base, int off, boolean dbl) {
        String s = dbl ? rd(src, F0) : ri(src, S0);
        String b = ri(base, S1);
        mem(op, s, b, off);
    }

    /** Etiqueta de la constante en .data; la registra la primera vez */
    String doubleLabel(double v) {
        long bits = Double.doubleToRawLongBits(v);
        String label = doublePool.get(bits);
        if (label == null) {
            label = "__dbl_" + doublePool.size();
            doublePool.put(bits, label);
        }
        return label;
    }

    private static boolean fits12(int v) {
        return v >= -2048 && v <= 2047;
    }

    private static int roundUp(int v, int align) {
        return (v + align - 1) / align * align;
    }
}