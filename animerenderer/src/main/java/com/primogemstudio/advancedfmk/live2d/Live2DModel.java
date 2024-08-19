package com.primogemstudio.advancedfmk.live2d;

import com.primogemstudio.advancedfmk.interfaces.AccessFromNative;

import java.lang.ref.Cleaner;
import java.util.Random;

public class Live2DModel implements AutoCloseable {
    private static Random random = new Random();
    @AccessFromNative
    private long ptr;
    private Cleaner.Cleanable cleaner;

    public Live2DModel(String name, String path) {
        load(name, path);
        long ptr_i = ptr;

        cleaner = Live2DNative.cleaner.register(this, () -> release(ptr_i));
    }

    @Override
    public void close() {
        if (cleaner != null) cleaner.clean();
    }

    private native void load(String name, String path);

    public native void update(int width, int height);

    private static native void release(long ptr);
}
