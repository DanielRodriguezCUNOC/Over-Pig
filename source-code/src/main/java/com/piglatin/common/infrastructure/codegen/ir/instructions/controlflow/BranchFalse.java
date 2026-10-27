package com.piglatin.common.infrastructure.codegen.ir.instructions.controlflow;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchFalse extends Instruction {

    private final String condition, target;

    public BranchFalse(String condition, String target) {
        this.condition = condition;
        this.target = target;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitBranchFalse(this);
    }

    @Override
    public String toC3D() {
        return "if (" + condition + " == 0) goto " + target;
    }
}