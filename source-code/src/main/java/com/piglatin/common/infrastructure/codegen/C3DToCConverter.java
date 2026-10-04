package com.piglatin.common.infrastructure.codegen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class C3DToCConverter {

    /**
     * Requiere GCC con extensiones GNU (&&label, goto *ptr):
     *     gcc -std=gnu99 programa.c -o programa
     */
    public static String convertToC(C3DContext ctx) {
        StringBuilder c = new StringBuilder();

        //  Cabecera
        c.append("#include <stdio.h>\n");
        c.append("#include <stdlib.h>\n");
        c.append("#include <string.h>\n\n");
        c.append("// Codigo generado por Paboomi :3\n");
        c.append("// Requiere gcc -std=gnu99 \n\n");

        //  Memoria
        c.append("double Stack[100000];\n");
        c.append("double Heap[100000];\n");
        c.append("double SP = 0;\n");
        c.append("double FB = 0;\n");
        c.append("double H = 1;\n");
        c.append("void*  RET_STACK[100000];\n");
        c.append("int    RSP = 0;\n\n");

        //Temps globale
        if (ctx.getTempCounter() > 0) {
            c.append("double ");
            for (int i = 0; i < ctx.getTempCounter(); i++) {
                c.append("t").append(i);
                if (i < ctx.getTempCounter() - 1) c.append(", ");
            }
            c.append(";\n\n");
        }

        // Helpers
        appendHelpers(c);

        //  Detección de funciones
        List<Quadruple> qs = ctx.getQuadruples();

        Set<String> callTargets = new HashSet<>();
        for (Quadruple q : qs) {
            if ("CALL".equals(q.getOp()) && q.getArg1() != null) {
                callTargets.add(q.getArg1());
            }
        }

        List<int[]> funcRanges = new ArrayList<>();
        Set<Integer> inFunc = new HashSet<>();
        for (int i = 0; i < qs.size(); i++) {
            Quadruple q = qs.get(i);
            if ("LABEL".equals(q.getOp())
                    && q.getArg1() != null
                    && callTargets.contains(q.getArg1())) {
                int start = i;
                int end = qs.size() - 1;
                for (int j = i + 1; j < qs.size(); j++) {
                    Quadruple qj = qs.get(j);
                    if ("LABEL".equals(qj.getOp())
                            && qj.getArg1() != null
                            && callTargets.contains(qj.getArg1())) {
                        end = j - 1;
                        break;
                    }
                }
                funcRanges.add(new int[]{start, end});
                for (int k = start; k <= end; k++) inFunc.add(k);
                i = end;
            }
        }

        // main()
        c.append("int main() {\n");
        c.append("  setvbuf(stdout, NULL, _IONBF, 0);\n");

        // Lógica global
        for (int i = 0; i < qs.size(); i++) {
            if (inFunc.contains(i)) continue;
            c.append(emitQuad(qs.get(i), "  "));
        }

        // Saltar por encima de los cuerpos de funciones
        c.append("  goto __end_main;\n\n");

        // Cuerpos de funciones
        for (int[] r : funcRanges) {
            for (int i = r[0]; i <= r[1]; i++) {
                c.append(emitQuad(qs.get(i), "  "));
            }
            c.append("\n");
        }

        c.append("__end_main:\n");
        c.append("  return 0;\n");
        c.append("}\n");

        return c.toString();
    }
    private static String emitQuad(Quadruple q, String indent) {
        String op = q.getOp();
        if (op == null) return "";

        switch (op) {
            //  Control de flujo
            case "LABEL":
                return indent + q.getArg1() + ":\n";
            case "GOTO":
                return indent + "goto " + q.getResult() + ";\n";
            case "IF_TRUE":
                return indent + "if (" + q.getArg1() + " == 1) goto " + q.getResult() + ";\n";
            case "IF_FALSE":
                return indent + "if (" + q.getArg1() + " == 0) goto " + q.getResult() + ";\n";

            // Convención de llamada
            case "PARAM":
                return indent + "Stack[(int)SP] = " + q.getArg1() + "; SP = SP + 1;\n";

            case "CALL": {
                String func = q.getArg1();
                String retLabel = q.getResult();
                StringBuilder sb = new StringBuilder();
                sb.append(indent)
                        .append("RET_STACK[RSP] = &&").append(retLabel).append("; RSP = RSP + 1;\n");
                sb.append(indent)
                        .append("Stack[(int)SP] = SP; SP = SP + 1;\n");   // guardar SP_llamador
                sb.append(indent)
                        .append("Stack[(int)SP] = FB; SP = SP + 1;\n");   // guardar FB anterior
                sb.append(indent)
                        .append("FB = SP;\n");
                sb.append(indent)
                        .append("goto ").append(func).append(";\n");
                return sb.toString();
            }

            case "RET": {
                String val = (q.getArg1() != null && !q.getArg1().isEmpty())
                        ? q.getArg1() : "0";
                StringBuilder sb = new StringBuilder();
                sb.append(indent).append("{ double __fb = FB;\n");
                sb.append(indent).append("  double __ret = ").append(val).append(";\n");
                sb.append(indent).append("  FB = Stack[(int)(__fb - 1)];\n");
                sb.append(indent).append("  SP = Stack[(int)(__fb - 2)];\n");
                sb.append(indent).append("  Stack[(int)SP] = __ret;\n");
                sb.append(indent).append("  RSP = RSP - 1;\n");
                sb.append(indent).append("  goto *RET_STACK[RSP]; }\n");
                return sb.toString();
            }

            case "LOAD_RET":
                return indent + q.getResult() + " = Stack[(int)SP]; SP = SP + 1;\n";

            //  I/O
            case "READ":
                return indent + "scanf(\"%lf\", &" + q.getResult() + ");\n";
            case "PRINT":
                return indent + "__print_value(" + q.getArg1() + ", \"" + safeType(q.getArg2()) + "\", 0);\n";
            case "PRINTLN":
                return indent + "__print_value(" + q.getArg1() + ", \"" + safeType(q.getArg2()) + "\", 1);\n";
            case "PRINTS":
                return indent + "__print_heap_str(" + q.getArg1() + ", 0);\n";
            case "PRINTSLN":
                return indent + "__print_heap_str(" + q.getArg1() + ", 1);\n";

            //  Conversiones / strings
            case "TO_STR":
                return indent + q.getResult() + " = __to_str(" + q.getArg1() + ", \"" + safeType(q.getArg2()) + "\");\n";
            case "CONCAT":
                return indent + q.getResult() + " = __concat(" + q.getArg1() + ", " + q.getArg2() + ");\n";

            //  Asignación y aritmética
            case "=":
                return indent + q.getResult() + " = " + q.getArg1() + ";\n";

            default: {
                String s = q.toString();
                return s.isEmpty() ? "" : indent + s + ";\n";
            }
        }
    }

    private static String safeType(String t) {
        return (t == null || t.isEmpty()) ? "double" : t;
    }

    private static void appendHelpers(StringBuilder sb) {
        sb.append("double __to_str(double v, const char* type) {\n");
        sb.append("  char buf[64];\n");
        sb.append("  if (strcmp(type, \"int\") == 0) sprintf(buf, \"%d\", (int)v);\n");
        sb.append("  else if (strcmp(type, \"char\") == 0) sprintf(buf, \"%c\", (int)v);\n");
        sb.append("  else if (strcmp(type, \"boolean\") == 0) sprintf(buf, \"%s\", v != 0 ? \"true\" : \"false\");\n");
        sb.append("  else sprintf(buf, \"%g\", v);\n");
        sb.append("  double start = H;\n");
        sb.append("  for (int i = 0; buf[i] != '\\0'; i++) { Heap[(int)H] = (double)buf[i]; H = H + 1; }\n");
        sb.append("  Heap[(int)H] = 0.0; H = H + 1;\n");
        sb.append("  return start;\n");
        sb.append("}\n\n");

        sb.append("double __concat(double a, double b) {\n");
        sb.append("  double start = H;\n");
        sb.append("  int i = (int)a;\n");
        sb.append("  while (Heap[i] != 0.0) { Heap[(int)H] = Heap[i]; H = H + 1; i++; }\n");
        sb.append("  int j = (int)b;\n");
        sb.append("  while (Heap[j] != 0.0) { Heap[(int)H] = Heap[j]; H = H + 1; j++; }\n");
        sb.append("  Heap[(int)H] = 0.0; H = H + 1;\n");
        sb.append("  return start;\n");
        sb.append("}\n\n");

        sb.append("void __print_heap_str(double addr, int nl) {\n");
        sb.append("  int i = (int)addr;\n");
        sb.append("  while (Heap[i] != 0.0) { putchar((int)Heap[i]); i++; }\n");
        sb.append("  if (nl) putchar('\\n');\n");
        sb.append("}\n\n");

        sb.append("void __print_value(double v, const char* type, int nl) {\n");
        sb.append("  if (strcmp(type, \"int\") == 0) printf(\"%d\", (int)v);\n");
        sb.append("  else if (strcmp(type, \"char\") == 0) printf(\"%c\", (int)v);\n");
        sb.append("  else if (strcmp(type, \"boolean\") == 0) printf(\"%s\", v != 0 ? \"true\" : \"false\");\n");
        sb.append("  else printf(\"%g\", v);\n");
        sb.append("  if (nl) printf(\"\\n\");\n");
        sb.append("}\n\n");

        sb.append("int __streq(double a, double b) {\n");
        sb.append("  int i = (int)a, j = (int)b;\n");
        sb.append("  while (Heap[i] != 0.0 && Heap[j] != 0.0) {\n");
        sb.append("    if (Heap[i] != Heap[j]) return 0;\n");
        sb.append("    i++; j++;\n");
        sb.append("  }\n");
        sb.append("  return Heap[i] == Heap[j];\n");
        sb.append("}\n\n");
    }
}