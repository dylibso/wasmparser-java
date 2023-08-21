package com.dylibso.wasm.types;

import java.util.ArrayList;
import java.util.List;

public class CodeSection extends Section {
    private List<FunctionBody> functionBodies;

    public CodeSection(long id, long size) {
       super(id, size);
       this.functionBodies = new ArrayList<>();
    }

    public void addFunctionBody(FunctionBody f) {
        this.functionBodies.add(f);
    }

    public List<FunctionBody> getFunctionBodies() { return functionBodies; }
}
