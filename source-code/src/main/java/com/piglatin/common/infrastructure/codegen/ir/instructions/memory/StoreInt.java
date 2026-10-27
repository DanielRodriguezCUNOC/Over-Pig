package com.piglatin.common.infrastructure.codegen.ir.instructions.memory;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreInt extends Instruction {

    private final String base, source;
    private final int offset;

    public StoreInt(String base, int offset, String source) {
        this.base = base;
        this.offset = offset;
        this.source = source;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitStoreInt(this);
    }

    @Override
    public String toC3D() {
        return "mem_int[" + base + " + " + offset + "] = " + source;
    }
}