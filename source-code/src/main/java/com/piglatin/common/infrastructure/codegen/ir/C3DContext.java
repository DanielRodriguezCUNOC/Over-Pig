package com.piglatin.common.infrastructure.codegen.ir;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class C3DContext {
    private int tempCounter = 0;
    private int labelCounter = 0;
    private final List<Instruction> instructions = new ArrayList<>();

    public String newTemp() {
        return "v" + (tempCounter++);
    }

    public String newLabel() {
        return "L" + (labelCounter++);
    }

    public void emit(Instruction instruction) {instructions.add(instruction);}

}
