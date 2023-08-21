package com.dylibso.wasm.types;

import java.util.ArrayList;
import java.util.List;

public class TypeSection extends Section {
    private List<FunctionType> types;

    public TypeSection(long id, long size) {
       super(id, size);
       this.types = new ArrayList<>();
    }

    public void addType(FunctionType t) {
        this.types.add(t);
    }

    public List<FunctionType> getTypes() {
        return types;
    }
}
