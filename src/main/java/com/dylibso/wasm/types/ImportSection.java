package com.dylibso.wasm.types;

import java.util.ArrayList;
import java.util.List;

public class ImportSection extends Section {
    private List<Import> imports;

    public ImportSection(long id, long size) {
       super(id, size);
       this.imports = new ArrayList<>();
    }

    public void addImport(Import i) {
        this.imports.add(i);
    }

    public List<Import> getImports() {
        return imports;
    }
}
