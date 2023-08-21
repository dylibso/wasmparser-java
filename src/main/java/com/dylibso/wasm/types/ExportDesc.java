package com.dylibso.wasm.types;

import java.util.HashMap;
import java.util.Map;

public class ExportDesc {
    private final long index;
    private final ExportDescType type;

    public ExportDesc(long index, ExportDescType type) {
        this.index = index;
        this.type = type;
    }

    public long getIndex() {
        return index;
    }

    public ExportDescType getType() {
        return type;
    }
}
