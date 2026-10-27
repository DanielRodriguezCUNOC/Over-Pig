package com.piglatin.common.infrastructure.codegen.ir.instructions.calls;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnInt extends Instruction {

    private final String value;

    public ReturnInt(String value) {
        this.value = value;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitReturnInt(this);
    }

    @Override
    public String toC3D() {
        return "return_int " + value;
    }
}