package com.dylibso.wasm.types;

import java.util.ArrayList;
import java.util.List;

public class DataSection extends Section {
    private List<DataSegment> dataSegments;

    public DataSection(long id, long size) {
       super(id, size);
       this.dataSegments = new ArrayList<>();
    }

    public void addDataSegment(DataSegment d) {
        this.dataSegments.add(d);
    }

    public List<DataSegment> getDataSegments() {
        return dataSegments;
    }
}
