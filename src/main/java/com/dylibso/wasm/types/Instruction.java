package com.dylibso.wasm.types;

import java.util.Arrays;
import java.util.List;

public class Instruction {
    private OpCode opcode;
    private List<Long> operands;

    public Instruction(OpCode opcode, List<Long> operands) {
        this.opcode = opcode;
        this.operands = operands;
    }

    public OpCode getOpcode() {
        return opcode;
    }

    public List<Long> getOperands() {
        return operands;
    }

    public String toString() {
        if (operands.size() > 0) {
            return opcode + " " + operands;
        } else {
            return opcode.toString();
        }
    }
}
