package com.piglatin.common.infrastructure.codegen.ir.instructions.constants;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;

@Getter
public class ConstNull extends Instruction {

    private final String result;

    public ConstNull(String result) {
        this.result = result;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitConstNull(this);
    }

    @Override
    public String toC3D() {
        return result + " = 0";
    }
}
