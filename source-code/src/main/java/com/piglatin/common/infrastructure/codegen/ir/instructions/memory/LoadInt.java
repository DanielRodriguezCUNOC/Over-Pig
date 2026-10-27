package com.piglatin.common.infrastructure.codegen.ir.instructions.memory;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadInt extends Instruction {

    private final String result, base;
    private final int offset;

    public LoadInt(String result, String base, int offset) {
        this.result = result;
        this.base = base;
        this.offset = offset;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitLoadInt(this);
    }

    @Override
    public String toC3D() {
        return result + " = mem[" + base + " + " + offset + "]";
    }
}
