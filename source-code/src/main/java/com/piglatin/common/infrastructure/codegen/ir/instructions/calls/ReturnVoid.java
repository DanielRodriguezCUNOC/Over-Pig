package com.piglatin.common.infrastructure.codegen.ir.instructions.calls;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnVoid extends Instruction {

    public ReturnVoid() {
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitReturnVoid(this);
    }

    @Override
    public String toC3D() {
        return "return";
    }
}