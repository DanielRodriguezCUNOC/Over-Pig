package com.piglatin.common.infrastructure.codegen.ir;

import com.piglatin.common.infrastructure.codegen.ir.instructions.arithmetic.AddInt;
import com.piglatin.common.infrastructure.codegen.ir.instructions.constants.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.conversions.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.controlflow.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.calls.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.comparisons.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.runtime.*;
import com.piglatin.common.infrastructure.codegen.ir.instructions.memory.*;

import java.util.List;

public class C3DEmitter {

    public static final String SP = "sp";
    public static final String FP = "fp";
    public static final String RA = "ra";
    public static final int WORD = 4;
    public static final int DOUBLE_WORD = 8;
    public static final int SAVED_AREA = 8;
    public static final int FRAME_ALIGNMENT = 16;
    public static final int MAX_REG_ARGS = 8;
    public static final int THIS_SLOT = -4;
    public static final int ARRAY_LENGTH_OFFSET = 0;
    public static final int OBJECT_ALIGNMENT = 8;

    private final C3DContext ctx;

    public C3DEmitter(C3DContext ctx) {
        this.ctx = ctx;
    }

    public record ParamSlot(int offset, boolean isDouble) {}

    public void emitLogicalAnd(String result, String left, String right) {
        String lShort = ctx.newLabel();
        String lEnd = ctx.newLabel();

        ctx.emit(new BranchFalse(left, lShort));
        ctx.emit(new MoveInt(result, right));
        ctx.emit(new Jump(lEnd));
        ctx.emit(new Label(lShort));
        ctx.emit(new ConstInt(result, 0));
        ctx.emit(new Label(lEnd));
    }

    public void emitLogicalOr(String result, String left, String right) {
        String lTrue = ctx.newLabel();
        String lEnd = ctx.newLabel();

        ctx.emit(new BranchTrue(left, lTrue));
        ctx.emit(new MoveInt(result, right));
        ctx.emit(new Jump(lEnd));
        ctx.emit(new Label(lTrue));
        ctx.emit(new ConstInt(result, 1));
        ctx.emit(new Label(lEnd));
    }

    public void emitLogicalNot(String result, String operand) {
        String zero = ctx.newTemp();

        ctx.emit(new ConstInt(zero, 0));
        ctx.emit(new EqInt(result, operand, zero));
    }

    public void emitConcat(String result, String a, String b) {
        ctx.emit(new ParamInt(a));
        ctx.emit(new ParamInt(b));
        ctx.emit(new Call("__concat", 2, 0));
        ctx.emit(new LoadRetInt(result));
    }

    public void emitToStr(String result, String value, Type type) {
        if (type == Type.INT) {
            ctx.emit(new ParamInt(value));
            ctx.emit(new Call("__int_to_str", 1, 0));
            ctx.emit(new LoadRetInt(result));
        } else if (type == Type.DOUBLE) {
            ctx.emit(new ParamDouble(value));
            ctx.emit(new Call("__double_to_str", 0, 1));
            ctx.emit(new LoadRetInt(result));
        } else {
            throw new IllegalArgumentException(
                    "emitToStr solo soporta INT y DOUBLE, recibido: " + type
            );
        }
    }

    public void emitStreq(String result, String a, String b) {
        ctx.emit(new ParamInt(a));
        ctx.emit(new ParamInt(b));
        ctx.emit(new Call("__streq", 2, 0));
        ctx.emit(new LoadRetInt(result));
    }

    public void emitPrint(String value, Type type) {
        switch (type) {
            case INT:
            case BOOL:
            case CHAR:
                ctx.emit(new PrintInt(value));
                break;
            case DOUBLE:
                ctx.emit(new PrintDouble(value));
                break;
            case STRING:
                ctx.emit(new PrintString(value));
                break;
            case VOID:
            case NULL:
            case OBJECT:
                break;
        }
    }

    public void emitPrintln(String value, Type type) {
        emitPrint(value, type);
        ctx.emit(new PrintNewline());
    }

    public void emitReadInto(String base, int offset, Type type) {
        String t = ctx.newTemp();

        if (type == Type.INT) {
            ctx.emit(new ReadInt(t));
            ctx.emit(new StoreInt(base, offset, t));
        } else if (type == Type.DOUBLE) {
            ctx.emit(new ReadDouble(t));
            ctx.emit(new StoreDouble(base, offset, t));
        } else {
            throw new IllegalArgumentException(
                    "emitReadInto solo soporta INT y DOUBLE, recibido: " + type
            );
        }
    }

    public void emitParam(String value, Type type) {
        if (type == Type.DOUBLE) {
            ctx.emit(new ParamDouble(value));
        } else {
            ctx.emit(new ParamInt(value));
        }
    }

    public void emitCallVoid(String target, int nInt, int nDouble) {
        ctx.emit(new Call(target, nInt, nDouble));
    }

    public void emitCallInt(String result, String target, int nInt, int nDouble) {
        ctx.emit(new Call(target, nInt, nDouble));
        ctx.emit(new LoadRetInt(result));
    }

    public void emitCallDouble(String result, String target, int nInt, int nDouble) {
        ctx.emit(new Call(target, nInt, nDouble));
        ctx.emit(new LoadRetDouble(result));
    }

    public void emitAllocObject(String result, int size, String vtableLabel) {
        ctx.emit(new Alloc(result, newConstInt(size)));
        String vtable = ctx.newTemp();
        ctx.emit(new ConstString(vtable, vtableLabel));
        ctx.emit(new StoreInt(result, 0, vtable));
    }

    public void emitVirtualCallVoid(
            String obj,
            int vtableOffset,
            int nInt,
            int nDouble
    ) {
        ctx.emit(new CallIndirect(loadVirtualTarget(obj, vtableOffset), nInt, nDouble));
    }

    public void emitVirtualCallInt(
            String result,
            String obj,
            int vtableOffset,
            int nInt,
            int nDouble
    ) {
        emitVirtualCallVoid(obj, vtableOffset, nInt, nDouble);
        ctx.emit(new LoadRetInt(result));
    }

    public void emitVirtualCallDouble(
            String result,
            String obj,
            int vtableOffset,
            int nInt,
            int nDouble
    ) {
        emitVirtualCallVoid(obj, vtableOffset, nInt, nDouble);
        ctx.emit(new LoadRetDouble(result));
    }

    private String loadVirtualTarget(String obj, int vtableOffset) {
        String vtable = ctx.newTemp();
        ctx.emit(new LoadInt(vtable, obj, 0));
        String method = ctx.newTemp();
        ctx.emit(new LoadInt(method, vtable, vtableOffset));
        return method;
    }

    public void emitPrologue(int frameSize) {
        String delta = newConstInt(-frameSize);
        ctx.emit(new AddInt(SP, SP, delta));
        ctx.emit(new StoreInt(SP, frameSize - WORD, FP));
        ctx.emit(new StoreInt(SP, frameSize - SAVED_AREA, RA));
        String fpOffset = newConstInt(frameSize - SAVED_AREA);
        ctx.emit(new AddInt(FP, SP, fpOffset));
    }

    public void emitEpilogue() {
        ctx.emit(new LoadInt(RA, FP, 0));
        String callerFp = ctx.newTemp();
        ctx.emit(new LoadInt(callerFp, FP, WORD));
        String saved = newConstInt(SAVED_AREA);
        ctx.emit(new AddInt(SP, FP, saved));
        ctx.emit(new MoveInt(FP, callerFp));
    }

    public void spillArguments(boolean hasThis, List<ParamSlot> params) {
        int intReg = 0;
        int doubleReg = 0;
        if (hasThis) {
            requireRegister(intReg);
            ctx.emit(new StoreInt(FP, THIS_SLOT, "a" + intReg++));
        }
        for (ParamSlot param : params) {
            if (param.isDouble()) {
                requireRegister(doubleReg);
                ctx.emit(new StoreDouble(FP, param.offset(), "fa" + doubleReg++));
            } else {
                requireRegister(intReg);
                ctx.emit(new StoreInt(FP, param.offset(), "a" + intReg++));
            }
        }
    }

    private void requireRegister(int index) {
        if (index >= MAX_REG_ARGS) {
            throw new IllegalStateException("Más de " + MAX_REG_ARGS + " argumentos del mismo tipo no está soportado.");
        }
    }

    private String newConstInt(int value) {
        String result = ctx.newTemp();
        ctx.emit(new ConstInt(result, value));
        return result;
    }
}