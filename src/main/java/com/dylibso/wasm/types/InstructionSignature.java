package com.dylibso.wasm.types;

public class InstructionSignature {
    private WasmEncoding[] operands;

    public InstructionSignature(WasmEncoding[] operands) {
        this.operands = operands;
    }
}
