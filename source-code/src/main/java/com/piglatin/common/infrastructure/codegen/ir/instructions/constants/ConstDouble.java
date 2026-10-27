package com.piglatin.common.infrastructure.codegen.ir.instructions.constants;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;

@Getter
public class ConstDouble extends Instruction {

    private final String result;
    private final double value;

    public ConstDouble(String result, double value) {
        this.result = result;
        this.value = value;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitConstDouble(this);
    }

    @Override
    public String toC3D() {
        return result + " = " + value;
    }
}
