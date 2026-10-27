package com.piglatin.common.infrastructure.codegen.ir.instructions.calls;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParamInt extends Instruction {

    private final String value;

    public ParamInt(String value) {
        this.value = value;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitParamInt(this);
    }

    @Override
    public String toC3D() {
        return "param_int " + value;
    }
}