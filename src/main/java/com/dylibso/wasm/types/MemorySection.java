package com.dylibso.wasm.types;

import java.util.ArrayList;
import java.util.List;

public class MemorySection extends Section {
    private List<Memory> memories;

    public MemorySection(long id, long size) {
       super(id, size);
       this.memories = new ArrayList<>();
    }

    public void addMemory(Memory m) {
        this.memories.add(m);
    }

    public List<Memory> getMemories() {
        return memories;
    }
}
