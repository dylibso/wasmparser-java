package com.dylibso.wasm.types;

import java.util.ArrayList;
import java.util.List;

public class GlobalSection extends Section {
    private List<Global> globals;

    public GlobalSection(long id, long size) {
       super(id, size);
       this.globals = new ArrayList<>();
    }

    public void addGlobal(Global g) {
        this.globals.add(g);
    }

    public List<Global> getGlobals() {
        return globals;
    }
}
