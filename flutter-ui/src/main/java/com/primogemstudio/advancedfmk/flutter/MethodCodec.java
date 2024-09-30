package com.primogemstudio.advancedfmk.flutter;

import java.nio.ByteBuffer;

public interface MethodCodec {
    ByteBuffer encode(MethodCall methodCall);

    MethodCall decode(ByteBuffer methodCall);

    ByteBuffer success(Object result);

    ByteBuffer error(String errorCode, String errorMessage, Object errorDetails);

    ByteBuffer errorWithStacktrace(String errorCode, String errorMessage, Object errorDetails, String errorStacktrace);

    <T> T decodeEnvelope(ByteBuffer envelope);
}
