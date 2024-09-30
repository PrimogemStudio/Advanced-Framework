package com.primogemstudio.advancedfmk.flutter;

import java.nio.ByteBuffer;

public class BasicMessageChannel<T> {
    private final long instance;
    private final String name;
    private final MessageCodec codec;

    public BasicMessageChannel(FlutterInstance instance, String name) {
        this(instance, name, StandardMessageCodec.INSTANCE);
    }

    public BasicMessageChannel(long instance, String name) {
        this(instance, name, StandardMessageCodec.INSTANCE);
    }

    public BasicMessageChannel(FlutterInstance instance, String name, MessageCodec codec) {
        this.instance = instance.getHandle();
        this.name = name;
        this.codec = codec;
    }

    public BasicMessageChannel(long instance, String name, MessageCodec codec) {
        this.instance = instance;
        this.name = name;
        this.codec = codec;
    }

    public void send(T message) {
        FlutterNative.sendPlatformMessage(instance, name, codec.encode(message), null);
    }

    public void send(T message, Reply<T> reply) {
        FlutterNative.sendPlatformMessage(instance, name, codec.encode(message), data -> reply.reply(codec.decode(data)));
    }

    public void setHandler(MessageHandler<T> handler) {
        FlutterNative.setMessageHandler(instance, name, handler == null ? null : new IncomingMessageHandler(handler));
    }

    public interface Reply<T> {
        void reply(T message);
    }

    public interface MessageHandler<T> {
        void onMessage(T message, Reply<T> reply);
    }

    private final class IncomingMessageHandler implements BinaryMessageHandler {
        private final MessageHandler<T> handler;

        private IncomingMessageHandler(MessageHandler<T> handler) {
            this.handler = handler;
        }

        @Override
        public void onMessage(ByteBuffer message, BinaryReply callback) {
            try {
                handler.onMessage(codec.decode(message), data -> callback.reply(codec.encode(data)));
            } catch (RuntimeException e) {
                callback.reply(null);
            }
        }
    }
}
