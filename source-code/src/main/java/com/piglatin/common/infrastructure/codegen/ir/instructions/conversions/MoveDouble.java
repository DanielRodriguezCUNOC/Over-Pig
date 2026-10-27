package com.piglatin.common.infrastructure.codegen.ir.instructions.conversions;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;

@Getter
public class MoveDouble extends Instruction {

    private final String result;
    private final String source;

    public MoveDouble(String result, String source) {
        this.result = result;
        this.source = source;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitMoveDouble(this);
    }

    @Override
    public String toC3D() {
        return result + " = " + source;
    }
}
