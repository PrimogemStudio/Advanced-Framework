package com.primogemstudio.advancedfmk.live2d;

import com.primogemstudio.advancedfmk.interfaces.AccessFromNative;

import java.lang.ref.Cleaner;

public class Live2DModel implements AutoCloseable {
    @AccessFromNative
    private long ptr;
    private Cleaner.Cleanable cleaner;
    public Live2DModel(String name, String path) {
        load(name, path);
        long ptr_i = ptr;
        cleaner = Live2DNative.cleaner.register(this, () -> {
            release(ptr_i);
        });
    }

    @Override
    public void close() {
        if (cleaner != null) cleaner.clean();
    }

    private native void load(String name, String path);
    private static native void release(long ptr);
}
