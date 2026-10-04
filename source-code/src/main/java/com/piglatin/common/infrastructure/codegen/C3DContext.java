package com.piglatin.common.infrastructure.codegen;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class C3DContext {
    private int tempCounter = 0;
    private int labelCounter = 0;
    private final List<Quadruple> quadruples = new ArrayList<>();

    public String newTemp() {
        return "t" + (tempCounter++);
    }

    public String newLabel() {
        return "L" + (labelCounter++);
    }

    public void emit(String op, String arg1, String arg2, String result) {
        quadruples.add(new Quadruple(op, arg1, arg2, result));
    }

    public void emitLabel(String label) {
        quadruples.add(new Quadruple("LABEL", label, null, null));
    }

    public void emitGoto(String label) {
        quadruples.add(new Quadruple("GOTO", null, null, label));
    }

    public void emitIfTrue(String condition, String label) {
        quadruples.add(new Quadruple("IF_TRUE", condition, null, label));
    }

    public void emitIfFalse(String condition, String label) {
        quadruples.add(new Quadruple("IF_FALSE", condition, null, label));
    }
}