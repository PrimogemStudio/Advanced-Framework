package com.primogemstudio.advancedfmk.flutter;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardMessageCodec implements MessageCodec {
    public static final StandardMessageCodec INSTANCE = new StandardMessageCodec();
    private static final Charset UTF8 = StandardCharsets.UTF_8;
    private static final byte NULL = 0;
    private static final byte TRUE = 1;
    private static final byte FALSE = 2;
    private static final byte INT = 3;
    private static final byte LONG = 4;
    private static final byte BIGINT = 5;
    private static final byte DOUBLE = 6;
    private static final byte STRING = 7;
    private static final byte BYTE_ARRAY = 8;
    private static final byte INT_ARRAY = 9;
    private static final byte LONG_ARRAY = 10;
    private static final byte DOUBLE_ARRAY = 11;
    private static final byte LIST = 12;
    private static final byte MAP = 13;
    private static final byte FLOAT_ARRAY = 14;

    private StandardMessageCodec() {
    }

    @Override
    public ByteBuffer encode(Object message) {
        if (message == null) return null;
        var stream = new ExposedByteArrayOutputStream();
        writeValue(stream, message);
        var buffer = ByteBuffer.allocateDirect(stream.size());
        buffer.put(stream.buffer(), 0, stream.size());
        return buffer;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T decode(ByteBuffer message) {
        if (message == null) return null;
        message.order(ByteOrder.nativeOrder());
        var value = readValue(message);
        if (message.hasRemaining()) throw new IllegalArgumentException("Message corrupted");
        return (T) value;
    }

    protected void writeValue(ByteArrayOutputStream stream, Object value) {
        switch (value) {
            case null -> stream.write(NULL);
            case Boolean b -> stream.write(b ? TRUE : FALSE);
            case Number number -> {
                if (value instanceof Integer || value instanceof Short || value instanceof Byte) {
                    stream.write(INT);
                    writeInt(stream, number.intValue());
                } else if (value instanceof Long l) {
                    stream.write(LONG);
                    writeLong(stream, l);
                } else if (value instanceof Float || value instanceof Double) {
                    stream.write(DOUBLE);
                    writeAlignment(stream, 8);
                    writeDouble(stream, number.doubleValue());
                } else if (value instanceof BigInteger bi) {
                    stream.write(BIGINT);
                    writeBytes(stream, bi.toString(16).getBytes(UTF8));
                } else {
                    throw new IllegalArgumentException("Unsupported Number type: " + value.getClass());
                }
            }
            case CharSequence charSequence -> {
                stream.write(STRING);
                writeBytes(stream, charSequence.toString().getBytes(UTF8));
            }
            case byte[] bytes -> {
                stream.write(BYTE_ARRAY);
                writeBytes(stream, bytes);
            }
            case int[] array -> {
                stream.write(INT_ARRAY);
                writeSize(stream, array.length);
                writeAlignment(stream, 4);
                for (final int n : array) {
                    writeInt(stream, n);
                }
            }
            case long[] array -> {
                stream.write(LONG_ARRAY);
                writeSize(stream, array.length);
                writeAlignment(stream, 8);
                for (final long n : array) {
                    writeLong(stream, n);
                }
            }
            case double[] array -> {
                stream.write(DOUBLE_ARRAY);
                writeSize(stream, array.length);
                writeAlignment(stream, 8);
                for (final double d : array) {
                    writeDouble(stream, d);
                }
            }
            case List<?> list -> {
                stream.write(LIST);
                writeSize(stream, list.size());
                for (final Object o : list) {
                    writeValue(stream, o);
                }
            }
            case Map<?, ?> map -> {
                stream.write(MAP);
                writeSize(stream, map.size());
                for (final Map.Entry<?, ?> entry : map.entrySet()) {
                    writeValue(stream, entry.getKey());
                    writeValue(stream, entry.getValue());
                }
            }
            case float[] array -> {
                stream.write(FLOAT_ARRAY);
                writeSize(stream, array.length);
                writeAlignment(stream, 4);
                for (final float f : array) {
                    writeFloat(stream, f);
                }
            }
            default ->
                    throw new IllegalArgumentException("Unsupported value: '" + value + "' of type '" + value.getClass() + "'");
        }
    }

    protected static void writeSize(ByteArrayOutputStream stream, int value) {
        if (value < 254) {
            stream.write(value);
        } else if (value <= 0xffff) {
            stream.write(254);
            writeChar(stream, value);
        } else {
            stream.write(255);
            writeInt(stream, value);
        }
    }

    protected static void writeChar(ByteArrayOutputStream stream, int value) {
        stream.write(value);
        stream.write(value >>> 8);
    }

    protected static void writeInt(ByteArrayOutputStream stream, int value) {
        stream.write(value);
        stream.write(value >>> 8);
        stream.write(value >>> 16);
        stream.write(value >>> 24);
    }

    protected static void writeLong(ByteArrayOutputStream stream, long value) {
        stream.write((byte) value);
        stream.write((byte) (value >>> 8));
        stream.write((byte) (value >>> 16));
        stream.write((byte) (value >>> 24));
        stream.write((byte) (value >>> 32));
        stream.write((byte) (value >>> 40));
        stream.write((byte) (value >>> 48));
        stream.write((byte) (value >>> 56));
    }

    protected static void writeFloat(ByteArrayOutputStream stream, float value) {
        writeInt(stream, Float.floatToIntBits(value));
    }

    protected static void writeDouble(ByteArrayOutputStream stream, double value) {
        writeLong(stream, Double.doubleToLongBits(value));
    }

    protected static void writeBytes(ByteArrayOutputStream stream, byte[] bytes) {
        writeSize(stream, bytes.length);
        stream.write(bytes, 0, bytes.length);
    }

    protected static void writeAlignment(ByteArrayOutputStream stream, int alignment) {
        var mod = stream.size() % alignment;
        if (mod != 0) {
            for (int i = 0; i < alignment - mod; i++) {
                stream.write(0);
            }
        }
    }

    protected static int readSize(ByteBuffer buffer) {
        if (!buffer.hasRemaining()) {
            throw new IllegalArgumentException("Message corrupted");
        }
        final int value = buffer.get() & 0xff;
        if (value < 254) {
            return value;
        } else if (value == 254) {
            return buffer.getChar();
        } else {
            return buffer.getInt();
        }
    }

    protected static byte[] readBytes(ByteBuffer buffer) {
        final int length = readSize(buffer);
        final byte[] bytes = new byte[length];
        buffer.get(bytes);
        return bytes;
    }

    protected static void readAlignment(ByteBuffer buffer, int alignment) {
        final int mod = buffer.position() % alignment;
        if (mod != 0) {
            buffer.position(buffer.position() + alignment - mod);
        }
    }

    protected final Object readValue(ByteBuffer buffer) {
        if (!buffer.hasRemaining()) {
            throw new IllegalArgumentException("Message corrupted");
        }
        final byte type = buffer.get();
        return readValueOfType(type, buffer);
    }

    protected Object readValueOfType(byte type, ByteBuffer buffer) {
        final Object result;
        switch (type) {
            case NULL:
                result = null;
                break;
            case TRUE:
                result = true;
                break;
            case FALSE:
                result = false;
                break;
            case INT:
                result = buffer.getInt();
                break;
            case LONG:
                result = buffer.getLong();
                break;
            case BIGINT: {
                final byte[] hex = readBytes(buffer);
                result = new BigInteger(new String(hex, UTF8), 16);
                break;
            }
            case DOUBLE:
                readAlignment(buffer, 8);
                result = buffer.getDouble();
                break;
            case STRING: {
                final byte[] bytes = readBytes(buffer);
                result = new String(bytes, UTF8);
                break;
            }
            case BYTE_ARRAY: {
                result = readBytes(buffer);
                break;
            }
            case INT_ARRAY: {
                final int length = readSize(buffer);
                final int[] array = new int[length];
                readAlignment(buffer, 4);
                buffer.asIntBuffer().get(array);
                result = array;
                buffer.position(buffer.position() + 4 * length);
                break;
            }
            case LONG_ARRAY: {
                final int length = readSize(buffer);
                final long[] array = new long[length];
                readAlignment(buffer, 8);
                buffer.asLongBuffer().get(array);
                result = array;
                buffer.position(buffer.position() + 8 * length);
                break;
            }
            case DOUBLE_ARRAY: {
                final int length = readSize(buffer);
                final double[] array = new double[length];
                readAlignment(buffer, 8);
                buffer.asDoubleBuffer().get(array);
                result = array;
                buffer.position(buffer.position() + 8 * length);
                break;
            }
            case LIST: {
                final int size = readSize(buffer);
                final List<Object> list = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    list.add(readValue(buffer));
                }
                result = list;
                break;
            }
            case MAP: {
                final int size = readSize(buffer);
                final Map<Object, Object> map = new HashMap<>();
                for (int i = 0; i < size; i++) {
                    map.put(readValue(buffer), readValue(buffer));
                }
                result = map;
                break;
            }
            case FLOAT_ARRAY: {
                final int length = readSize(buffer);
                final float[] array = new float[length];
                readAlignment(buffer, 4);
                buffer.asFloatBuffer().get(array);
                result = array;
                buffer.position(buffer.position() + 4 * length);
                break;
            }
            default:
                throw new IllegalArgumentException("Message corrupted");
        }
        return result;
    }

    static final class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
        byte[] buffer() {
            return buf;
        }
    }
}
