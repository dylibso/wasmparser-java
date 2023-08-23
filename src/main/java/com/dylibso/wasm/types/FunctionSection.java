package com.dylibso.wasm.types;

public class FunctionSection extends Section {
    private int[] typeIndices;

    public FunctionSection(long id, long size, int[] typeIndices) {
       super(id, size);
       this.typeIndices = typeIndices;
    }

    public int[] getTypeIndices() {
        return typeIndices;
    }
}
