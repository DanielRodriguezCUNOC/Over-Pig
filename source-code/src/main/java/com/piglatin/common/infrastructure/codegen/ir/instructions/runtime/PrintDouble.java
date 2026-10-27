package com.piglatin.common.infrastructure.codegen.ir.instructions.runtime;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrintDouble extends Instruction {

    private final String value;

    public PrintDouble(String value) {
        this.value = value;
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitPrintDouble(this);
    }

    @Override
    public String toC3D() {
        return "print_double " + value;
    }
}