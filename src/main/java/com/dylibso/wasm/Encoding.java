package com.dylibso.wasm;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class Encoding {
    /**
     * Reads an unsigned integer from {@code byteBuffer}.
     */
    public static long readUnsignedLeb128(ByteBuffer byteBuffer) {
        long result = 0;
        int shift = 0;
        while (true) {
            if (byteBuffer.remaining() == 0) {
                throw new IllegalArgumentException("ULEB128 reached the end of the buffer");
            }

            byte b = byteBuffer.get();
            result |= (long) (b & 0x7F) << shift;

            if ((b & 0x80) == 0) {
                break;
            }

            shift += 7;
        }

        return result;
    }

    // Computes the number of bytes required to encode a given value as LEB128
    public static int computeLeb128Size(int value) {
        int size = 0;
        do {
            size++;
            value >>>= 7;
        } while (value != 0);
        return size;
    }

    public static float longToFloat(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt((int)x);
        buffer.rewind();
        return buffer.getFloat();
    }

    public static float bytesToFloat(byte[] data) {
        return ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    public static double longToDouble(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(x);
        buffer.rewind();
        return buffer.getDouble();
    }

    public static double bytesToDouble(byte[] data) {
        return ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getDouble();
    }
}
