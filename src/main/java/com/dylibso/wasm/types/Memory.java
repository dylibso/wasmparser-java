package com.dylibso.wasm.types;

public class Memory {
    private long limitMin;
    private Long limitMax;

    public Memory(long limitMin, Long limitMax) {
        this.limitMin = limitMin;
        this.limitMax = limitMax;
    }

    public long getLimitMin() {
        return limitMin;
    }

    public Long getLimitMax() {
        return limitMax;
    }
}
