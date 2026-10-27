package com.piglatin.common.infrastructure.codegen.ir.instructions.constants;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;

@Getter
public class ConstBool extends Instruction {

    private final String result;
    private final boolean value;

    public ConstBool(String result, boolean value) {
        this.result = result;
        this.value = value;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitConstBool(this);
    }

    @Override
    public String toC3D() {
        return result + " = " + (value ? "1" : "0");
    }
}
