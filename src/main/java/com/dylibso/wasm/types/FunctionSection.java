package com.dylibso.wasm.types;

import java.util.ArrayList;
import java.util.List;

public class FunctionSection extends Section {
    private List<Integer> typeIndices;

    public FunctionSection(long id, long size) {
       super(id, size);
       this.typeIndices = new ArrayList<>();
    }

    public void addTypeIndex(Integer i) {
        this.typeIndices.add(i);
    }

    public List<Integer> getTypeIndices() {
        return typeIndices;
    }
}
