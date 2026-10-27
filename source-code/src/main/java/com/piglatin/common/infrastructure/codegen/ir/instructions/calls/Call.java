package com.piglatin.common.infrastructure.codegen.ir.instructions.calls;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Call extends Instruction {

    private final String target;
    private final int nInt, nDouble;

    public Call(String target, int nInt, int nDouble) {
        this.target = target;
        this.nInt = nInt;
        this.nDouble = nDouble;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitCall(this);
    }

    @Override
    public String toC3D() {
        return "call " + target + ", " + nInt + ", " + nDouble;
    }
}
