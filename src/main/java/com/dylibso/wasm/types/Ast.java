package com.dylibso.wasm.types;

import com.dylibso.wasm.Parser;

import java.util.Stack;

public class Ast {
    private CodeBlock root;
    private Stack<CodeBlock> stack;
    //private List<Instruction> instructions;

    public Ast() {
        this.root = new CodeBlock(BlockType.BLOCK);
        this.stack = new Stack<>();
        this.stack.push(root);
    }

    public CodeBlock getRoot() {
        return root;
    }

    public void addInstruction(Instruction i) {
        var current = peek();
        switch (i.getOpcode()) {
            case BLOCK -> {
                current.addInstruction(i);
                var next = new CodeBlock(BlockType.BLOCK);
                i.setCodeBlock(next);
                push(next);
            }
            case LOOP -> {
                current.addInstruction(i);
                var next = new CodeBlock(BlockType.LOOP);
                i.setCodeBlock(next);
                push(next);
            }
            case END -> {
                current.addInstruction(i);
                pop();
            }
            default -> current.addInstruction(i);
        }
    }

    private CodeBlock push(CodeBlock block) {
        return this.stack.push(block);
    }

    private CodeBlock peek() {
        return this.stack.peek();
    }

    private void pop() {
        this.stack.pop();
    }

    public void print() {
        printAst(root, 0);
    }

    private void printAst(CodeBlock block, int depth) {
        for (var i : block.getInstructions()) {
            System.out.println("0x" + Integer.toHexString(i.getAddress()) + " | " + "\t".repeat(depth+1) + i.toString());
            if (i.getCodeBlock() != null) {
                printAst(i.getCodeBlock(), depth + 1);
            }
        }
    }


}
