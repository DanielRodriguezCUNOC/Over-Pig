package com.piglatin.common.infrastructure.codegen.ir.instructions.runtime;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadDouble extends Instruction {

    private final String result;

    public ReadDouble(String result) {
        this.result = result;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitReadDouble(this);
    }

    @Override
    public String toC3D() {
        return "read_double " + result;
    }
}