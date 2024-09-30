package com.primogemstudio.advancedfmk.flutter;

import java.nio.ByteBuffer;

public interface BinaryMessageHandler {
    void onMessage(ByteBuffer message, BinaryReply reply);
}
