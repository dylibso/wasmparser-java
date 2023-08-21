package com.dylibso.wasm.types;

import java.util.List;

public class FunctionBody {
    private List<Value> locals;
    private List<Instruction> instructions;

    public FunctionBody(List<Value> locals, List<Instruction> instructions) {
        this.locals = locals;
        this.instructions = instructions;
    }

    public List<Value> getLocals() {
        return locals;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }
}
