package com.primogemstudio.advancedfmk.flutter;

import org.lwjgl.system.NativeType;

public final class FlutterNative {
    static {
        System.load("D:/engine/src/out/host_release/flutter_minecraft.dll");
    }

    public static native void init(long f1, long f2, long f3, long f4);

    public static native long createInstance(String assets);

    public static native void destroyInstance(long instance);

    public static native void sendPosEvent(long instance, @NativeType("FlutterPointerPhase") int phase, double x, double y, long view);

    public static native void sendKeyEvent(long instance, @NativeType("GLFWwindow *") long window, int key, int scancode, int action, int mods);

    public static native void sendCharEvent(long instance, @NativeType("GLFWwindow *") long window, int code);

    public static native void sendMetricsEvent(long instance, int width, int height, long view);

    public static native void pollEvents(long instance);

    public static native int getTexture(long instance);
}
