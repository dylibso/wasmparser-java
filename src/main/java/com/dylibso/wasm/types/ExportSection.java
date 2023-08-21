package com.dylibso.wasm.types;

import java.util.ArrayList;
import java.util.List;

public class ExportSection extends Section {
    private List<Export> exports;

    public ExportSection(long id, long size) {
       super(id, size);
       this.exports = new ArrayList<>();
    }

    public void addExport(Export e) {
        this.exports.add(e);
    }

    public List<Export> getExports() {
        return exports;
    }
}
