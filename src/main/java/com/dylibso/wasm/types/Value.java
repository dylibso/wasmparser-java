package com.dylibso.wasm.types;

import java.nio.ByteBuffer;

public class Value {
    private final ValueType type;
    private final byte[] data;
    public static Value TRUE;
    public static Value FALSE;
    static {
        TRUE = Value.i32(1);
        FALSE = Value.i32(0);
    }

    public static Value i32(long data) {
        return new Value(ValueType.I32, data);
    }

    public static Value i64(long data) {
        return new Value(ValueType.I64, data);
    }

    public ValueType getType() { return this.type; }

    public byte[] getData() { return this.data; }

    public Value(ValueType type, byte[] data) {
        this.type = type;
        this.data = data;
    }

    public Value(ValueType type, int data) {
        this.type = type;
        this.data = ByteBuffer.allocate(4).putInt(data).array();
    }

    public Value(ValueType type, long data) {
        this.type = type;
        switch (type) {
            case I32, F32 -> {
                this.data = new byte[4];
                this.data[0] = (byte) (data >> 24);
                this.data[1] = (byte) (data >> 16);
                this.data[2] = (byte) (data >> 8);
                this.data[3] = (byte) data;
            }
            case I64, F64 -> {
                this.data = new byte[8];
                this.data[0] = (byte) (data >> 56);
                this.data[1] = (byte) (data >> 48);
                this.data[2] = (byte) (data >> 40);
                this.data[3] = (byte) (data >> 32);
                this.data[4] = (byte) (data >> 24);
                this.data[5] = (byte) (data >> 16);
                this.data[6] = (byte) (data >> 8);
                this.data[7] = (byte) data;
            }
            default -> this.data = new byte[]{};
        }
    }

    // TODO memoize these
    public int asInt() {
        return ByteBuffer.wrap(this.data).getInt();
    }

    // The unsigned representation of the int, stored in a long
    // so there are enough bits
    public long asUInt() {
        return ByteBuffer.wrap(this.data).getInt() & 0xFFFFFFFFL;
    }

    // TODO memoize these
    public long asLong() {
        return ByteBuffer.wrap(this.data).getLong();
    }

    // TODO memoize these
    public byte asByte() {
        return this.data[this.data.length - 1]; // this the right byte?
    }

    public String toString() {
        switch (this.type) {
            case I32 -> {
                return this.asInt() + "@i32";
            }
            case I64 -> {
                return this.asLong() + "@i64";
            }
            default -> {
                throw new RuntimeException("TODO handle float");
            }
        }
    }
}

