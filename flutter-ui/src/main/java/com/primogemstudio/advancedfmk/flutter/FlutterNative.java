package com.primogemstudio.advancedfmk.flutter;

import org.lwjgl.system.NativeType;

import java.io.FileOutputStream;
import java.lang.ref.Cleaner;
import java.util.Objects;

public final class FlutterNative {
    static {
        var os = System.getProperty("os.name");
        var path = "";
        var name = "";
        if (os.contains("Windows")) {
            path = "/natives/flutter_minecraft.dll";
            name = "flutter_minecraft.dll";
        } else if (os.contains("Linux")) {
            path = "/natives/libflutter_minecraft.so";
            name = "libflutter_minecraft.so";
        } else if (os.contains("Mac")) {
            path = "/natives/libflutter_minecraft.dylib";
            name = "libflutter_minecraft.dylib";
        } else {
            throw new RuntimeException("Unsupported OS: " + os);
        }
        var lib = System.getProperty("java.io.tmpdir") + "/" + name;
        try (var in = FlutterNative.class.getResourceAsStream(path)) {
            try (var out = new FileOutputStream(lib)) {
                out.write(Objects.requireNonNull(in).readAllBytes());
            }
            System.load(lib);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static Cleaner cleaner = Cleaner.create();

    public static native void init(long f1, long f2, long f3, long f4);

    public static native long createInstance(String assets);

    public static native void destroyInstance(long instance);

    public static native void sendPointerEvent(long instance, @NativeType("FlutterPointerPhase") int phase, double x, double y, int signalKind, double scrollDX, double scrollDY, long view);

    public static native void sendKeyEvent(long instance, @NativeType("GLFWwindow *") long window, int key, int scancode, int action, int mods);

    public static native void sendCharEvent(long instance, @NativeType("GLFWwindow *") long window, int code);

    public static native void sendMetricsEvent(long instance, int width, int height, long view);

    public static native void pollEvents(long instance);

    public static native int getTexture(long instance);
}
