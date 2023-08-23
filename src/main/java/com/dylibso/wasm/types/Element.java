package com.dylibso.wasm.types;

import java.util.List;

public class Element {
    private long tableIndex;
    private Instruction[] expr;
    private List<Long> funcIndices;

    public Element(long tableIndex, Instruction[] expr, List<Long> funcIndices) {
        this.tableIndex = tableIndex;
        this.expr = expr;
        this.funcIndices = funcIndices;
    }

    public List<Long> getFuncIndices() {
        return funcIndices;
    }

    public Instruction[] getExpr() {
        return expr;
    }

    public long getTableIndex() {
        return tableIndex;
    }
}
