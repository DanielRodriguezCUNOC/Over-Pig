package com.piglatin.common.infrastructure.codegen.ir.instructions.memory;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDouble extends Instruction {

    private final String base, source;
    private final int offset;

    public StoreDouble(String base, int offset, String source) {
        this.base = base;
        this.offset = offset;
        this.source = source;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitStoreDouble(this);
    }

    @Override
    public String toC3D() {
        return "mem_double[" + base + " + " + offset + "] = " + source;
    }
}