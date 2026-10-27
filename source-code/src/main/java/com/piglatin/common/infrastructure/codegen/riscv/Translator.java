package com.piglatin.common.infrastructure.codegen.riscv;

import com.piglatin.common.infrastructure.codegen.ir.instructions.arithmetic.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.calls.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.comparisons.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.constants.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.controlflow.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.conversions.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.memory.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.runtime.*;
import com.piglatin.common.infrastructure.codegen.ir.visitor.InstructionVisitor;

import java.util.Objects;

import static com.piglatin.common.infrastructure.codegen.riscv.RiscVTranslationContext.F0;
import static com.piglatin.common.infrastructure.codegen.riscv.RiscVTranslationContext.MAIN;
import static com.piglatin.common.infrastructure.codegen.riscv.RiscVTranslationContext.S0;
import static com.piglatin.common.infrastructure.codegen.riscv.RiscVTranslationContext.S1;
import static com.piglatin.common.infrastructure.codegen.riscv.RiscVTranslationContext.S2;

/**
 * Traduce una instrucción C3D a su equivalente RISC-V.
 */
public final class Translator implements InstructionVisitor<Void> {

    private final RiscVTranslationContext context;

    public Translator(RiscVTranslationContext context) {
        this.context = Objects.requireNonNull(context, "context");
    }

    // Constantes
    @Override public Void visitConstInt(ConstInt i) { context.loadImm(i.getResult(), i.getValue()); return null; }
    @Override public Void visitConstBool(ConstBool i) { context.loadImm(i.getResult(), i.isValue() ? 1 : 0); return null; }
    @Override public Void visitConstNull(ConstNull i) { context.loadImm(i.getResult(), 0); return null; }

    @Override public Void visitConstDouble(ConstDouble i) {
        String d = context.wd(i.getResult(), F0);
        context.line("la " + S2 + ", " + context.doubleLabel(i.getValue()));
        context.line("fld " + d + ", 0(" + S2 + ")");
        context.sd(i.getResult(), d);
        return null;
    }

    @Override public Void visitConstString(ConstString i) {
        String d = context.wi(i.getResult(), S0);
        context.line("la " + d + ", " + i.getLabel());
        context.si(i.getResult(), d);
        return null;
    }

    // Conversiones
    @Override public Void visitMoveInt(MoveInt i) { context.move(i.getResult(), i.getSource(), false); return null; }
    @Override public Void visitMoveDouble(MoveDouble i) { context.move(i.getResult(), i.getSource(), true); return null; }

    @Override public Void visitIntToDouble(IntToDouble i) {
        String s = context.ri(i.getSource(), S0);
        String d = context.wd(i.getResult(), F0);
        context.line("fcvt.d.w " + d + ", " + s);
        context.sd(i.getResult(), d);
        return null;
    }

    @Override public Void visitDoubleToInt(DoubleToInt i) {
        String s = context.rd(i.getSource(), F0);
        String d = context.wi(i.getResult(), S0);
        context.line("fcvt.w.d " + d + ", " + s + ", rtz");   // trunca hacia 0 (semántica de cast)
        context.si(i.getResult(), d);
        return null;
    }

    @Override public Void visitIntToByte(IntToByte i) { context.mask(i.getResult(), i.getSource()); return null; }
    @Override public Void visitByteToInt(ByteToInt i) { context.mask(i.getResult(), i.getSource()); return null; }

    // Aritmética entera
    @Override public Void visitAddInt(AddInt i) { context.binInt("add", i.getResult(), i.getLeft(), i.getRight()); return null; }
    @Override public Void visitSubInt(SubInt i) { context.binInt("sub", i.getResult(), i.getLeft(), i.getRight()); return null; }
    @Override public Void visitMulInt(MulInt i) { context.binInt("mul", i.getResult(), i.getLeft(), i.getRight()); return null; }
    @Override public Void visitDivInt(DivInt i) { context.binInt("div", i.getResult(), i.getLeft(), i.getRight()); return null; }
    @Override public Void visitRemInt(RemInt i) { context.binInt("rem", i.getResult(), i.getLeft(), i.getRight()); return null; }

    @Override public Void visitNegInt(NegInt i) {
        String s = context.ri(i.getOperand(), S0);
        String d = context.wi(i.getResult(), S0);
        context.line("neg " + d + ", " + s);
        context.si(i.getResult(), d);
        return null;
    }

    // Aritmética double
    @Override public Void visitAddDouble(AddDouble i) { context.binDouble("fadd.d", i.getResult(), i.getLeft(), i.getRight()); return null; }
    @Override public Void visitSubDouble(SubDouble i) { context.binDouble("fsub.d", i.getResult(), i.getLeft(), i.getRight()); return null; }
    @Override public Void visitMulDouble(MulDouble i) { context.binDouble("fmul.d", i.getResult(), i.getLeft(), i.getRight()); return null; }
    @Override public Void visitDivDouble(DivDouble i) { context.binDouble("fdiv.d", i.getResult(), i.getLeft(), i.getRight()); return null; }

    @Override public Void visitNegDouble(NegDouble i) {
        String s = context.rd(i.getOperand(), F0);
        String d = context.wd(i.getResult(), F0);
        context.line("fneg.d " + d + ", " + s);
        context.sd(i.getResult(), d);
        return null;
    }

    // Comparaciones enteras
    @Override public Void visitEqInt(EqInt i) {
        String a = context.ri(i.getLeft(), S0);
        String b = context.ri(i.getRight(), S1);
        String d = context.wi(i.getResult(), S0);
        context.line("sub " + d + ", " + a + ", " + b);
        context.line("seqz " + d + ", " + d);
        context.si(i.getResult(), d);
        return null;
    }

    @Override public Void visitNeInt(NeInt i) {
        String a = context.ri(i.getLeft(), S0);
        String b = context.ri(i.getRight(), S1);
        String d = context.wi(i.getResult(), S0);
        context.line("sub " + d + ", " + a + ", " + b);
        context.line("snez " + d + ", " + d);
        context.si(i.getResult(), d);
        return null;
    }

    @Override public Void visitLtInt(LtInt i) { context.cmpInt(i.getResult(), i.getLeft(), i.getRight(), "slt", false, false); return null; }
    @Override public Void visitLeInt(LeInt i) { context.cmpInt(i.getResult(), i.getLeft(), i.getRight(), "slt", true, true); return null; }
    @Override public Void visitGtInt(GtInt i) { context.cmpInt(i.getResult(), i.getLeft(), i.getRight(), "slt", true, false); return null; }
    @Override public Void visitGeInt(GeInt i) { context.cmpInt(i.getResult(), i.getLeft(), i.getRight(), "slt", false, true); return null; }

    // Comparaciones double (Le/Ge con fle.d)
    @Override public Void visitEqDouble(EqDouble i) { context.cmpDouble(i.getResult(), i.getLeft(), i.getRight(), "feq.d", false, false); return null; }
    @Override public Void visitNeDouble(NeDouble i) { context.cmpDouble(i.getResult(), i.getLeft(), i.getRight(), "feq.d", false, true); return null; }
    @Override public Void visitLtDouble(LtDouble i) { context.cmpDouble(i.getResult(), i.getLeft(), i.getRight(), "flt.d", false, false); return null; }
    @Override public Void visitLeDouble(LeDouble i) { context.cmpDouble(i.getResult(), i.getLeft(), i.getRight(), "fle.d", false, false); return null; }
    @Override public Void visitGtDouble(GtDouble i) { context.cmpDouble(i.getResult(), i.getLeft(), i.getRight(), "flt.d", true, false); return null; }
    @Override public Void visitGeDouble(GeDouble i) { context.cmpDouble(i.getResult(), i.getLeft(), i.getRight(), "fle.d", true, false); return null; }

    @Override public Void visitStrEq(StrEq i) {
        String a = context.ri(i.getLeft(), S0);
        String b = context.ri(i.getRight(), S1);
        context.line("mv a0, " + a);
        context.line("mv a1, " + b);
        context.line("jal __streq");
        context.si(i.getResult(), "a0");
        return null;
    }

    // Control de flujo
    @Override public Void visitLabel(Label i) {
        context.label(i.getName());
        if (MAIN.equals(i.getName())) {
            context.line("jal __init_heap");
        }
        return null;
    }

    @Override public Void visitJump(Jump i) { context.line("j " + i.getTarget()); return null; }

    @Override public Void visitBranchTrue(BranchTrue i) {
        context.line("bnez " + context.ri(i.getCondition(), S0) + ", " + i.getTarget());
        return null;
    }

    @Override public Void visitBranchFalse(BranchFalse i) {
        context.line("beqz " + context.ri(i.getCondition(), S0) + ", " + i.getTarget());
        return null;
    }

    // Memoria
    @Override public Void visitLoadInt(LoadInt i) { context.loadMem("lw", i.getResult(), i.getBase(), i.getOffset(), false); return null; }
    @Override public Void visitLoadByte(LoadByte i) { context.loadMem("lbu", i.getResult(), i.getBase(), i.getOffset(), false); return null; }
    @Override public Void visitLoadDouble(LoadDouble i) { context.loadMem("fld", i.getResult(), i.getBase(), i.getOffset(), true); return null; }

    @Override public Void visitStoreInt(StoreInt i) { context.storeMem("sw", i.getSource(), i.getBase(), i.getOffset(), false); return null; }
    @Override public Void visitStoreByte(StoreByte i) { context.storeMem("sb", i.getSource(), i.getBase(), i.getOffset(), false); return null; }
    @Override public Void visitStoreDouble(StoreDouble i) { context.storeMem("fsd", i.getSource(), i.getBase(), i.getOffset(), true); return null; }

    // Llamadas
    @Override public Void visitParamInt(ParamInt i) { context.loadIntArgument(i.getValue()); return null; }
    @Override public Void visitParamDouble(ParamDouble i) { context.loadDoubleArgument(i.getValue()); return null; }

    @Override public Void visitCall(Call i) {
        context.checkCall(i.getTarget(), i.getNInt(), i.getNDouble());
        context.line("jal " + i.getTarget());
        return null;
    }

    @Override public Void visitCallIndirect(CallIndirect i) {
        context.checkCall(i.getTarget(), i.getNInt(), i.getNDouble());
        String t = context.ri(i.getTarget(), S0);
        context.line("jalr ra, " + t + ", 0");
        return null;
    }

    @Override public Void visitReturnVoid(ReturnVoid i) {
        if (context.isInMain()) {
            context.syscall(10);
        } else {
            context.line("ret");
        }
        return null;
    }

    @Override public Void visitReturnInt(ReturnInt i) {
        if (!context.consumeReturnPreloaded()) context.loadInt("a0", i.getValue());
        context.line("ret");
        return null;
    }

    @Override public Void visitReturnDouble(ReturnDouble i) {
        if (!context.consumeReturnPreloaded()) context.loadDouble("fa0", i.getValue());
        context.line("ret");
        return null;
    }

    @Override public Void visitLoadRetInt(LoadRetInt i) { context.si(i.getResult(), "a0"); return null; }
    @Override public Void visitLoadRetDouble(LoadRetDouble i) { context.sd(i.getResult(), "fa0"); return null; }

    // Runtime (syscalls de RARS)
    @Override public Void visitPrintInt(PrintInt i) { context.loadInt("a0", i.getValue()); context.syscall(1); return null; }
    @Override public Void visitPrintDouble(PrintDouble i) { context.loadDouble("fa0", i.getValue()); context.syscall(3); return null; }
    @Override public Void visitPrintString(PrintString i) { context.loadInt("a0", i.getValue()); context.syscall(4); return null; }

    @Override public Void visitPrintNewline(PrintNewline i) {
        context.line("li a0, 10");
        context.syscall(11);
        return null;
    }

    @Override public Void visitReadInt(ReadInt i) { context.syscall(5); context.si(i.getResult(), "a0"); return null; }
    @Override public Void visitReadDouble(ReadDouble i) { context.syscall(7); context.sd(i.getResult(), "fa0"); return null; }

    @Override public Void visitAlloc(Alloc i) {
        context.loadInt("a0", i.getSize());
        context.syscall(9);
        context.si(i.getResult(), "a0");
        return null;
    }
}