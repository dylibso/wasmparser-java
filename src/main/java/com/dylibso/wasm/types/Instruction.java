package com.dylibso.wasm.types;

import java.util.Arrays;
import java.util.List;

public class Instruction {
    private int address;
    private OpCode opcode;
    private List<Long> operands;

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

    public String toString() {
        var addr = "0x" + Integer.toHexString(address);
        if (operands.size() > 0) {
            return addr + " " + opcode + " " + operands;
        } else {
            return addr + " " + opcode.toString();
        }
    }
}
