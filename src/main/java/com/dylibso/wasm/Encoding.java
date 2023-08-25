package com.dylibso.wasm;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class Encoding {

    /**
     * Reads an unsigned long from {@code byteBuffer}.
     */
    public static long readSignedLeb128(ByteBuffer byteBuffer) {
        long result = 0;
        int shift = 0;

        while (true) {
            byte b = byteBuffer.get();
            result |= (long) (b & 0x7F) << shift;
            shift += 7;

            if ((b & 0x80) == 0) {
                if ((shift < 64) && ((b & 0x40) != 0))
                    result |= -1L << shift;
                break;
            }
        }

        return result;
    }

    /**
     * Reads an unsigned integer from {@code byteBuffer}.
     */
    public static long readUnsignedLeb128(ByteBuffer byteBuffer) {
        long result = 0;

        for (int i = 0; ; i++) {
            final int b = byteBuffer.get();
            final long low7Bits = b & 0x7f;
            final boolean done = (b & 0x80) == 0;

            // The first 9 groups of 7 bits are guaranteed to fit (9 * 7 = 63 bits).
            // That leaves room for only the low-order bit from the 10th group (which has index 9)
            if (i == 9 && (b & 0xfe) != 0) {
                // TODO fix and put exception back
                System.out.println("Warning: Value is larger than 64 bits");
                //throw new ArithmeticException("Value is larger than 64-bits");
                return result;
            }

            result |= low7Bits << (7 * i);
            if (done) {
                return result;
            }
        }
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

    public static double longToDouble(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(x);
        buffer.rewind();
        return buffer.getDouble();
    }
}
