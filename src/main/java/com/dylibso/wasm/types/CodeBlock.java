package com.dylibso.wasm.types;

import java.util.ArrayList;
import java.util.List;

public class CodeBlock {
    private BlockType type;
    private List<Instruction> instructions;

    public CodeBlock(BlockType type) {
        this.type = type;
        this.instructions = new ArrayList<>();
    }

    public void addInstruction(Instruction i) {
        this.instructions.add(i);
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }
}
