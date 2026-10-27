package com.piglatin.common.infrastructure.codegen.ir.visitor;

import com.piglatin.common.infrastructure.codegen.ir.instructions.constants.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.conversions.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.arithmetic.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.comparisons.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.controlflow.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.memory.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.calls.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.runtime.*;

public interface InstructionVisitor<R> {
    // Constants
    R visitConstInt(ConstInt i);
    R visitConstDouble(ConstDouble i);
    R visitConstBool(ConstBool i);
    R visitConstString(ConstString i);
    R visitConstNull(ConstNull i);

    // Conversions
    R visitMoveInt(MoveInt i);
    R visitMoveDouble(MoveDouble i);
    R visitIntToDouble(IntToDouble i);
    R visitDoubleToInt(DoubleToInt i);
    R visitIntToByte(IntToByte i);
    R visitByteToInt(ByteToInt i);

    // Arithmetic integers
    R visitAddInt(AddInt i);
    R visitSubInt(SubInt i);
    R visitMulInt(MulInt i);
    R visitDivInt(DivInt i);
    R visitRemInt(RemInt i);
    R visitNegInt(NegInt i);

    // Arithmetic double
    R visitAddDouble(AddDouble i);
    R visitSubDouble(SubDouble i);
    R visitMulDouble(MulDouble i);
    R visitDivDouble(DivDouble i);
    R visitNegDouble(NegDouble i);

    // Comparisons integer
    R visitEqInt(EqInt i);
    R visitNeInt(NeInt i);
    R visitLtInt(LtInt i);
    R visitLeInt(LeInt i);
    R visitGtInt(GtInt i);
    R visitGeInt(GeInt i);

    // Comparisons double
    R visitEqDouble(EqDouble i);
    R visitNeDouble(NeDouble i);
    R visitLtDouble(LtDouble i);
    R visitLeDouble(LeDouble i);
    R visitGtDouble(GtDouble i);
    R visitGeDouble(GeDouble i);

    // Comparisons string
    R visitStrEq(StrEq i);

    // Control flow
    R visitLabel(Label i);
    R visitJump(Jump i);
    R visitBranchTrue(BranchTrue i);
    R visitBranchFalse(BranchFalse i);

    // Memory
    R visitLoadInt(LoadInt i);
    R visitLoadDouble(LoadDouble i);
    R visitLoadByte(LoadByte i);
    R visitStoreInt(StoreInt i);
    R visitStoreDouble(StoreDouble i);
    R visitStoreByte(StoreByte i);

    // Calls
    R visitParamInt(ParamInt i);
    R visitParamDouble(ParamDouble i);
    R visitCall(Call i);
    R visitCallIndirect(CallIndirect i);
    R visitReturnVoid(ReturnVoid i);
    R visitReturnInt(ReturnInt i);
    R visitReturnDouble(ReturnDouble i);
    R visitLoadRetInt(LoadRetInt i);
    R visitLoadRetDouble(LoadRetDouble i);

    // Runtime
    R visitPrintInt(PrintInt i);
    R visitPrintDouble(PrintDouble i);
    R visitPrintString(PrintString i);
    R visitPrintNewline(PrintNewline i);
    R visitReadInt(ReadInt i);
    R visitReadDouble(ReadDouble i);
    R visitAlloc(Alloc i);
}