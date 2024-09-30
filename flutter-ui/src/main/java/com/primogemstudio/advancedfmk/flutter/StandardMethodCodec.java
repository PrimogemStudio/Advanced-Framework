package com.primogemstudio.advancedfmk.flutter;

import com.primogemstudio.advancedfmk.flutter.StandardMessageCodec.ExposedByteArrayOutputStream;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.primogemstudio.advancedfmk.flutter.Utils.getStackTraceString;

public class StandardMethodCodec implements MethodCodec {
    public static final StandardMethodCodec INSTANCE = new StandardMethodCodec(StandardMessageCodec.INSTANCE);
    private final StandardMessageCodec messageCodec;

    StandardMethodCodec(StandardMessageCodec messageCodec) {
        this.messageCodec = messageCodec;
    }

    @Override
    public ByteBuffer encode(MethodCall methodCall) {
        var stream = new ExposedByteArrayOutputStream();
        messageCodec.writeValue(stream, methodCall.method);
        messageCodec.writeValue(stream, methodCall.arguments);
        var buffer = ByteBuffer.allocateDirect(stream.size());
        buffer.put(stream.buffer(), 0, stream.size());
        return buffer;
    }

    @Override
    public MethodCall decode(ByteBuffer methodCall) {
        methodCall.order(ByteOrder.nativeOrder());
        var method = messageCodec.readValue(methodCall);
        var arguments = messageCodec.readValue(methodCall);
        if (method instanceof String md && !methodCall.hasRemaining()) return new MethodCall(md, arguments);
        throw new IllegalArgumentException("Method call corrupted");
    }

    @Override
    public ByteBuffer success(Object result) {
        var stream = new ExposedByteArrayOutputStream();
        stream.write(0);
        messageCodec.writeValue(stream, result);
        var buffer = ByteBuffer.allocateDirect(stream.size());
        buffer.put(stream.buffer(), 0, stream.size());
        return buffer;
    }

    @Override
    public ByteBuffer error(String errorCode, String errorMessage, Object errorDetails) {
        var stream = new ExposedByteArrayOutputStream();
        error(errorCode, errorMessage, errorDetails, stream);
        var buffer = ByteBuffer.allocateDirect(stream.size());
        buffer.put(stream.buffer(), 0, stream.size());
        return buffer;
    }

    @Override
    public ByteBuffer errorWithStacktrace(String errorCode, String errorMessage, Object errorDetails, String errorStacktrace) {
        var stream = new ExposedByteArrayOutputStream();
        error(errorCode, errorMessage, errorDetails, stream);
        messageCodec.writeValue(stream, errorStacktrace);
        var buffer = ByteBuffer.allocateDirect(stream.size());
        buffer.put(stream.buffer(), 0, stream.size());
        return buffer;
    }

    private void error(String errorCode, String errorMessage, Object errorDetails, ExposedByteArrayOutputStream stream) {
        stream.write(1);
        messageCodec.writeValue(stream, errorCode);
        messageCodec.writeValue(stream, errorMessage);
        if (errorDetails instanceof Throwable t) messageCodec.writeValue(stream, getStackTraceString(t));
        else messageCodec.writeValue(stream, errorDetails);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T decodeEnvelope(ByteBuffer envelope) {
        envelope.order(ByteOrder.nativeOrder());
        var flag = envelope.get();
        switch (flag) {
            case 0: {
                var result = messageCodec.readValue(envelope);
                if (!envelope.hasRemaining()) return (T) result;
            }
            case 1: {
                var code = messageCodec.readValue(envelope);
                var message = messageCodec.readValue(envelope);
                var details = messageCodec.readValue(envelope);
                if (code instanceof String && (message == null || message instanceof String) && !envelope.hasRemaining()) {
                    throw new FlutterException((String) code, (String) message, details);
                }
            }
        }
        throw new IllegalArgumentException("Envelope corrupted");
    }
}
