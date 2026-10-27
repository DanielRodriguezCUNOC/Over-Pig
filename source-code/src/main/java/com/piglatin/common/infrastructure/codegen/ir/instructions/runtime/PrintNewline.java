package com.piglatin.common.infrastructure.codegen.ir.instructions.runtime;

import com.piglatin.common.infrastructure.codegen.ir.Instruction;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrintNewline extends Instruction {

    public PrintNewline() {
    }

    @Override
    public <R> R accept(InstructionVisitor<R> visitor) {
        return visitor.visitPrintNewline(this);
    }

    @Override
    public String toC3D() {
        return "print_newline";
    }
}