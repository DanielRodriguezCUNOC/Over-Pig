package com.piglatin.common.infrastructure.codegen.ir;

import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;

public abstract class Instruction {

    public abstract <R> R accept(InstructionVisitor<R> visitor);
    public abstract String toC3D();
}
