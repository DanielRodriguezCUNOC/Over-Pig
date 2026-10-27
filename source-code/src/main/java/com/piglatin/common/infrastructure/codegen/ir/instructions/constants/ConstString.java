package com.piglatin.common.infrastructure.codegen.ir.instructions.constants;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;

@Getter
public class ConstString extends Instruction {

    private final String result;
    private final String label;

    public ConstString(String result, String label) {
        this.result = result;
        this.label = label;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitConstString(this);
    }

    @Override
    public String toC3D() {
        return result + " = " + label;
    }
}
