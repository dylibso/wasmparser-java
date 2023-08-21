package com.dylibso.wasm.types;

import java.util.Arrays;
import java.util.List;

public class Instruction {
    private int address;
    private OpCode opcode;
    private List<Long> operands;
    private CodeBlock block;

    public Instruction(int address, OpCode opcode, List<Long> operands) {
        this.address = address;
        this.opcode = opcode;
        this.operands = operands;
    }

    public OpCode getOpcode() {
        return opcode;
    }

    public List<Long> getOperands() {
        return operands;
    }

    public void setCodeBlock(CodeBlock block) {
        this.block = block;
    }

    public CodeBlock getCodeBlock() {
        return block;
    }

    public String toString() {
        if (operands.size() > 0) {
            return opcode + " " + operands;
        } else {
            return opcode.toString();
        }
    }

    public int getAddress() {
        return address;
    }
}
