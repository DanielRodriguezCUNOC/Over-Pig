package com.piglatin.common.infrastructure.codegen.ir.instructions.runtime;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Alloc extends Instruction {

    private final String result, size;

    public Alloc(String result, String size) {
        this.result = result;
        this.size = size;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitAlloc(this);
    }

    @Override
    public String toC3D() {
        return result + " = alloc " + size;
    }
}