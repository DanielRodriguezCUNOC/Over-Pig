package com.piglatin.common.infrastructure.codegen.ir.instructions.calls;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadRetDouble extends Instruction {

    private final String result;

    public LoadRetDouble(String result) {
        this.result = result;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitLoadRetDouble(this);
    }

    @Override
    public String toC3D() {
        return result + " = load_ret_double";
    }
}