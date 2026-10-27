package com.piglatin.common.infrastructure.codegen.ir.instructions.constants;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstInt extends Instruction {

    private final String result;
    private final int value;

    public ConstInt(String result, int value) {
        this.result = result;
        this.value = value;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitConstInt(this);
    }

    @Override
    public String toC3D() {
        return result + " = " + value;
    }
}
