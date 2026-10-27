package com.piglatin.common.infrastructure.codegen.ir.instructions.arithmetic;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubDouble extends Instruction {

    private final String result, left, right;

    public SubDouble(String result, String left, String right) {
        this.result = result;
        this.left = left;
        this.right = right;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitSubDouble(this);
    }

    @Override
    public String toC3D() {
        return result + " = " + left + " - " + right;
    }
}