package com.piglatin.common.infrastructure.codegen.ir.instructions.arithmetic;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NegDouble extends Instruction {

    private final String result, operand;

    public NegDouble(String result, String operand) {
        this.result = result;
        this.operand = operand;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitNegDouble(this);
    }

    @Override
    public String toC3D() {
        return result + " = -" + operand;
    }
}