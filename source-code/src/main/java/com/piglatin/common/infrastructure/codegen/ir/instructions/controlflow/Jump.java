package com.piglatin.common.infrastructure.codegen.ir.instructions.controlflow;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Jump extends Instruction {

    private final String target;

    public Jump(String target) {
        this.target = target;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitJump(this);
    }

    @Override
    public String toC3D() {
        return "goto " + target;
    }
}