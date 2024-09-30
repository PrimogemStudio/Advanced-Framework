package com.primogemstudio.advancedfmk.flutter;

import java.nio.ByteBuffer;

public interface MessageCodec {
    ByteBuffer encode(Object message);

    <T> T decode(ByteBuffer data);
}
