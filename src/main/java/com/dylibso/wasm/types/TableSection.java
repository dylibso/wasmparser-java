package com.dylibso.wasm.types;

import java.util.ArrayList;
import java.util.List;

public class TableSection extends Section {
    private List<Table> tables;

    public TableSection(long id, long size) {
       super(id, size);
       this.tables = new ArrayList<>();
    }

    public void addTable(Table t) {
        this.tables.add(t);
    }

    public List<Table> getTables() {
        return tables;
    }
}
