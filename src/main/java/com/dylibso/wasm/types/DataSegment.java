package com.dylibso.wasm.types;

import java.util.List;

public class DataSegment {
    private long idx;
    private List<Byte> offset;
    private byte[] data;

    public DataSegment(long idx, List<Byte> offset, byte[] data) {
        this.idx = idx;
        this.offset = offset;
        this.data = data;
    }

    public long getIdx() {
        return idx;
    }

    public List<Byte> getOffset() {
        return offset;
    }

    public byte[] getData() {
        return data;
    }
}
