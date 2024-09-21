package com.primogemstudio.advancedfmk.live2d;

import com.primogemstudio.advancedfmk.interfaces.AccessFromNative;

import java.io.File;
import java.lang.ref.Cleaner;

public class Live2DModel implements AutoCloseable {
    public static String MotionGroupIdle = "Idle";
    public static String MotionGroupTapBody = "TapBody";
    public static String HitAreaNameHead = "Head";
    public static String HitAreaNameBody = "Body";

    @AccessFromNative
    private long ptr;
    private final Cleaner.Cleanable cleaner;

    public Live2DModel(String name, String path) {
        if (!new File(path + name + ".model3.json").exists())
            throw new IllegalStateException("File not found with name=" + name + ", path=" + path);
        load(name, path);
        var _ptr = ptr;
        cleaner = Live2DNative.cleaner.register(this, () -> release(_ptr));
    }

    @Override
    public void close() {
        if (cleaner != null) cleaner.clean();
    }

    private native void load(String name, String path);

    public native void update(int width, int height);

    public native void startMotion(String group, int no, int priority);

    public native void setExpression(String id);

    public native int getMotionCount(String group);

    public native String[] getExpressions();

    public native boolean hitTest(String part, float x, float y);

    public native void setDragging(float x, float y);

    private static native void release(long ptr);
}
