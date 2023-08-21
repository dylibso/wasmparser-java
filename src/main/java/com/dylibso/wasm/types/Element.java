package com.dylibso.wasm.types;

import java.util.List;

public class Element {
    private long tableIndex;
    private List<Byte> expr;
    private List<Long> funcIndices;

    public Element(long tableIndex, List<Byte> expr, List<Long> funcIndices) {
        this.tableIndex = tableIndex;
        this.expr = expr;
        this.funcIndices = funcIndices;
    }

    public List<Long> getFuncIndices() {
        return funcIndices;
    }

    public List<Byte> getExpr() {
        return expr;
    }

    public long getTableIndex() {
        return tableIndex;
    }
}
