package com.piglatin.common.infrastructure.codegen.ir.instructions.controlflow;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Label extends Instruction {

    private final String name;

    public Label(String name) {
        this.name = name;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitLabel(this);
    }

    @Override
    public String toC3D() {
        return name + ":";
    }
}
