# =============================================================================
# runtime.asm  --  Runtime en RISC-V (RV32I + M + F + D) para RARS
# =============================================================================
# Biblioteca de rutinas invocadas por el codigo generado via `call`.
# NO contiene `main`: el codigo generado aporta el suyo.
#
# Convencion (psABI simplificada):
#   - Argumentos en a0-a7 / fa0-fa7, retorno en a0 / fa0.
#   - s11 = heap pointer (proximo byte libre), s10 = fin del heap.
#     Los escribe __init_heap; las rutinas que reservan (__concat,
#     __int_to_str, __double_to_str) avanzan s11 (bump allocator).
#   - Ninguna rutina modifica otros registros s*/fs*, ni usa la pila,
#     ni llama a otras rutinas (por eso no necesitan guardar ra).
#   - a0-a7, t0-t6, ft0-ft11 se usan libremente (caller-saved).
#
# Uso:
#   1. Al inicio del programa generado:  call __init_heap
#   2. Despues:  call __concat / __streq / __int_to_str /
#                     __double_to_str / __print_newline
#   3. Para ensamblar junto al codigo generado en RARS:
#        - agregar `.include "runtime.asm"` en el archivo generado, o
#        - Settings > "Assemble all files in directory".
#
# Notas:
#   - NO hay sbrk (syscall 9). El heap es un bloque estatico que el
#     codigo generado reserva en .data: __heap_base (.space N) y
#     __heap_end (justo despues). Tambien define __oom_msg.
#     Este archivo depende de esas tres etiquetas.
#   - Bump allocator: cada reserva alinea s11 a 8, comprueba que cabe
#     (size <= s10 - s11, sin signo), entrega s11 y lo avanza size
#     alineado a 8. Si no cabe salta a __oom_error. No hay free.
#   - __double_to_str TRUNCA a 2 decimales (ver detalles en la rutina).
# =============================================================================

        .text

        .globl __init_heap
        .globl __oom_error
        .globl __concat
        .globl __streq
        .globl __int_to_str
        .globl __double_to_str
        .globl __print_newline

# -----------------------------------------------------------------------------
# __init_heap()
#   s11 = __heap_base (proximo byte libre), s10 = __heap_end (limite).
#   Llamar UNA sola vez al inicio. Modifica s10 y s11 (intencional).
# -----------------------------------------------------------------------------
__init_heap:
        la      s11, __heap_base
        la      s10, __heap_end
        ret

# -----------------------------------------------------------------------------
# __oom_error
#   Heap agotado: imprime "Out of memory\n" y termina con codigo de salida 10.
#   No retorna. Se alcanza con `j`, nunca con call.
# -----------------------------------------------------------------------------
__oom_error:
        la      a0, __oom_msg
        li      a7, 4               # print string
        ecall
        li      a0, 10              # codigo de salida
        li      a7, 93              # exit2 (exit con codigo)
        ecall

# -----------------------------------------------------------------------------
# __concat(a0 = str1, a1 = str2) -> a0 = nuevo string
#   Reserva strlen(str1)+strlen(str2)+1 bytes del heap estatico y copia ambos.
# -----------------------------------------------------------------------------
__concat:
        mv      t0, a0              # t0 = str1
        mv      t1, a1              # t1 = str2

        # --- len1 ---
        mv      t3, t0
__concat_len1:
        lbu     t4, 0(t3)
        beqz    t4, __concat_len1_end
        addi    t3, t3, 1
        j       __concat_len1
__concat_len1_end:
        sub     t5, t3, t0          # t5 = len1

        # --- len2 ---
        mv      t3, t1
__concat_len2:
        lbu     t4, 0(t3)
        beqz    t4, __concat_len2_end
        addi    t3, t3, 1
        j       __concat_len2
__concat_len2_end:
        sub     t6, t3, t1          # t6 = len2

        # --- reservar len1 + len2 + 1 (bump sobre s11) ---
        add     a0, t5, t6
        addi    a0, a0, 1           # a0 = bytes pedidos
        addi    s11, s11, 7
        andi    s11, s11, -8        # s11 alineado a 8
        sub     a2, s10, s11        # a2 = bytes libres
        bleu    a0, a2, __concat_ok
        j       __oom_error
__concat_ok:
        addi    a0, a0, 7
        andi    a0, a0, -8          # tamano alineado a 8
        mv      t2, s11             # t2 = inicio del resultado
        add     s11, s11, a0
        mv      t3, t2              # t3 = cursor de escritura

        # --- copiar str1 ---
__concat_cp1:
        lbu     t4, 0(t0)
        beqz    t4, __concat_cp1_end
        sb      t4, 0(t3)
        addi    t0, t0, 1
        addi    t3, t3, 1
        j       __concat_cp1
__concat_cp1_end:

        # --- copiar str2 ---
__concat_cp2:
        lbu     t4, 0(t1)
        beqz    t4, __concat_cp2_end
        sb      t4, 0(t3)
        addi    t1, t1, 1
        addi    t3, t3, 1
        j       __concat_cp2
__concat_cp2_end:
        sb      zero, 0(t3)         # terminador

        mv      a0, t2
        ret

# -----------------------------------------------------------------------------
# __streq(a0 = str1, a1 = str2) -> a0 = 1 si iguales, 0 si no
# -----------------------------------------------------------------------------
__streq:
        # --- null handling (RAM-1) ---
        beqz    a0, __streq_a_null
        beqz    a1, __streq_ne       # a0 != null, a1 == null
        j       __streq_loop
__streq_a_null:
        beqz    a1, __streq_eq       # ambos null -> iguales
        j       __streq_ne           # a0 == null, a1 != null

__streq_loop:
        lbu     t0, 0(a0)
        lbu     t1, 0(a1)
        bne     t0, t1, __streq_ne
        beqz    t0, __streq_eq
        addi    a0, a0, 1
        addi    a1, a1, 1
        j       __streq_loop
__streq_eq:
        li      a0, 1
        ret
__streq_ne:
        li      a0, 0
        ret
# -----------------------------------------------------------------------------
# __int_to_str(a0 = int) -> a0 = puntero a string decimal (en el heap estatico)
#   Maneja 0, negativos e INT_MIN (la magnitud se trata como sin signo).
# -----------------------------------------------------------------------------
__int_to_str:
        mv      t0, a0              # t0 = magnitud (sin signo)
        li      t1, 0               # t1 = 1 si negativo
        bgez    t0, __its_pos
        neg     t0, t0              # INT_MIN queda 0x80000000 = 2^31 sin signo
        li      t1, 1
__its_pos:
        li      t6, 10

        # --- contar digitos (t2) ---
        li      t2, 0
        mv      t3, t0
__its_count:
        divu    t3, t3, t6
        addi    t2, t2, 1
        bnez    t3, __its_count

        # --- reservar signo + digitos + '\0' (bump sobre s11) ---
        add     a0, t2, t1
        addi    a0, a0, 1           # a0 = bytes pedidos
        addi    s11, s11, 7
        andi    s11, s11, -8        # s11 alineado a 8
        sub     a2, s10, s11        # a2 = bytes libres
        bleu    a0, a2, __its_ok
        j       __oom_error
__its_ok:
        addi    a0, a0, 7
        andi    a0, a0, -8          # tamano alineado a 8
        mv      t5, s11             # t5 = inicio
        add     s11, s11, a0

        add     t4, t5, t1
        add     t4, t4, t2          # t4 = posicion del '\0'
        sb      zero, 0(t4)

        # --- escribir digitos de atras hacia adelante ---
__its_fill:
        addi    t4, t4, -1
        remu    t3, t0, t6
        addi    t3, t3, 48          # '0' + digito
        sb      t3, 0(t4)
        divu    t0, t0, t6
        bnez    t0, __its_fill

        beqz    t1, __its_done
        li      t3, 45              # '-'
        sb      t3, 0(t5)
__its_done:
        mv      a0, t5
        ret

# -----------------------------------------------------------------------------
# __double_to_str(fa0 = double) -> a0 = puntero a "entero.dd" (en el heap estatico)
#   - 2 decimales, TRUNCADOS (no redondeados): 3.14159 -> "3.14", 3.999 -> "3.99".
#   - Negativos con prefijo '-'. Si el resultado es 0.00 no se pone signo.
#   - Se suma un epsilon de 1e-9 a la fraccion*100 antes de truncar para
#     compensar el error binario (ej. 0.29*100 = 28.999999999999996 -> 29).
#   - Limite: parte entera dentro del rango de int32 (|x| < 2^31).
#     No maneja NaN ni infinito.
# -----------------------------------------------------------------------------
__double_to_str:
        # --- signo ---
        fcvt.d.w ft5, zero
        flt.d   t1, fa0, ft5        # t1 = 1 si fa0 < 0
        fabs.d  ft0, fa0            # ft0 = |x|

        # --- parte entera (truncada hacia 0) ---
        fcvt.w.d t0, ft0, rtz       # t0 = ip
        fcvt.d.w ft1, t0
        fsub.d  ft2, ft0, ft1       # ft2 = fraccion en [0,1)

        # --- decimales: trunc(frac*100 + 1e-9) ---
        li      t2, 100
        fcvt.d.w ft3, t2
        fmul.d  ft2, ft2, ft3
        li      t2, 1
        fcvt.d.w ft4, t2
        li      t2, 1000000000
        fcvt.d.w ft6, t2
        fdiv.d  ft4, ft4, ft6       # ft4 = 1e-9
        fadd.d  ft2, ft2, ft4
        fcvt.w.d t2, ft2, rtz       # t2 = dec
        li      t3, 99
        ble     t2, t3, __d2s_dec_ok
        mv      t2, t3              # tope de seguridad
__d2s_dec_ok:

        # --- sin signo si el valor truncado es 0.00 ---
        or      t3, t0, t2
        bnez    t3, __d2s_keep
        li      t1, 0
__d2s_keep:
        li      t6, 10

        # --- contar digitos de la parte entera (t4) ---
        li      t4, 0
        mv      t3, t0
__d2s_count:
        divu    t3, t3, t6
        addi    t4, t4, 1
        bnez    t3, __d2s_count

        # --- reservar signo + digitos + '.' + 2 decimales + '\0' (bump sobre s11) ---
        add     a0, t4, t1
        addi    a0, a0, 4           # a0 = bytes pedidos
        addi    s11, s11, 7
        andi    s11, s11, -8        # s11 alineado a 8
        sub     a2, s10, s11        # a2 = bytes libres
        bleu    a0, a2, __d2s_ok
        j       __oom_error
__d2s_ok:
        addi    a0, a0, 7
        andi    a0, a0, -8          # tamano alineado a 8
        mv      t5, s11             # t5 = inicio
        add     s11, s11, a0
        mv      t3, t5              # t3 = cursor

        beqz    t1, __d2s_nosign
        li      a2, 45              # '-'
        sb      a2, 0(t3)
        addi    t3, t3, 1
__d2s_nosign:
        add     a3, t3, t4          # a3 = posicion del '.'
        li      a2, 46
        sb      a2, 0(a3)

        divu    a2, t2, t6          # decena
        remu    a4, t2, t6          # unidad
        addi    a2, a2, 48
        addi    a4, a4, 48
        sb      a2, 1(a3)
        sb      a4, 2(a3)
        sb      zero, 3(a3)

        # --- digitos de la parte entera, de atras hacia adelante ---
        mv      a4, a3
__d2s_fill:
        addi    a4, a4, -1
        remu    a2, t0, t6
        addi    a2, a2, 48
        sb      a2, 0(a4)
        divu    t0, t0, t6
        bnez    t0, __d2s_fill

        mv      a0, t5
        ret

# -----------------------------------------------------------------------------
# __print_newline() -> void
#   Imprime '\n' con syscall 11. Clobbera a0 y a7.
# -----------------------------------------------------------------------------
__print_newline:
        li      a0, 10
        li      a7, 11
        ecall
        ret