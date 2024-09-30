package com.primogemstudio.advancedfmk.flutter;

import com.primogemstudio.advancedfmk.interfaces.AccessFromNative;

import java.nio.ByteBuffer;

@FunctionalInterface
public interface BinaryReply {
    void reply(ByteBuffer data);
}

@AccessFromNative
class IncomingBinaryReply implements BinaryReply {
    private final long reply;

    private IncomingBinaryReply(long reply) {
        this.reply = reply;
    }

    @Override
    public void reply(ByteBuffer data) {
        FlutterNative.sendMessageResponse(reply, data);
    }
}