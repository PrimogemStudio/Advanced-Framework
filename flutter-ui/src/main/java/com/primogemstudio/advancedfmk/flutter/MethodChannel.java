package com.primogemstudio.advancedfmk.flutter;

import java.nio.ByteBuffer;

import static com.primogemstudio.advancedfmk.flutter.Utils.getStackTraceString;

public class MethodChannel {
    private final long instance;
    private final String name;
    private final MethodCodec codec;

    public MethodChannel(FlutterInstance instance, String name) {
        this(instance, name, StandardMethodCodec.INSTANCE);
    }

    public MethodChannel(long instance, String name) {
        this(instance, name, StandardMethodCodec.INSTANCE);
    }

    public MethodChannel(FlutterInstance instance, String name, MethodCodec codec) {
        this.instance = instance.getHandle();
        this.name = name;
        this.codec = codec;
    }

    public MethodChannel(long instance, String name, MethodCodec codec) {
        this.instance = instance;
        this.name = name;
        this.codec = codec;
    }

    public void invoke(String method, Object arguments) {
        FlutterNative.sendPlatformMessage(instance, name, codec.encode(new MethodCall(method, arguments)), null);
    }

    public void invoke(String method, Object arguments, Result callback) {
        FlutterNative.sendPlatformMessage(instance, name, codec.encode(new MethodCall(method, arguments)), new ResultHandler(callback));
    }

    public void setHandler(MethodCallHandler handler) {
        FlutterNative.setMessageHandler(instance, name, handler == null ? null : new IncomingMethodCallHandler(handler));
    }

    public interface Result {
        void success(Object result);

        void error(String errorCode, String errorMessage, Object errorDetails);

        void notImplemented();
    }

    public interface MethodCallHandler {
        void call(MethodCall call, Result result);
    }

    private final class ResultHandler implements BinaryReply {
        private final Result callback;

        ResultHandler(Result callback) {
            this.callback = callback;
        }

        @Override
        public void reply(ByteBuffer reply) {
            if (reply == null) {
                callback.notImplemented();
            } else {
                try {
                    callback.success(codec.decodeEnvelope(reply));
                } catch (FlutterException e) {
                    callback.error(e.code, e.getMessage(), e.details);
                }
            }
        }
    }


    private final class IncomingMethodCallHandler implements BinaryMessageHandler {
        private final MethodCallHandler handler;

        IncomingMethodCallHandler(MethodCallHandler handler) {
            this.handler = handler;
        }

        @Override
        public void onMessage(ByteBuffer message, BinaryReply reply) {
            var call = codec.decode(message);
            try {
                handler.call(call, new Result() {
                    @Override
                    public void success(Object result) {
                        reply.reply(codec.success(result));
                    }

                    @Override
                    public void error(String errorCode, String errorMessage, Object errorDetails) {
                        reply.reply(codec.error(errorCode, errorMessage, errorDetails));
                    }

                    @Override
                    public void notImplemented() {
                        reply.reply(null);
                    }
                });
            } catch (RuntimeException e) {
                reply.reply(codec.errorWithStacktrace("error", e.getMessage(), null, getStackTraceString(e)));
            }
        }
    }
}
