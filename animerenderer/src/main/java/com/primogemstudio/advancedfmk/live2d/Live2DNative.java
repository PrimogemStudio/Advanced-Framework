package com.primogemstudio.advancedfmk.live2d;

import com.primogemstudio.advancedfmk.client.NativeLibLoader;

import java.lang.ref.Cleaner;

public class Live2DNative {
    static Cleaner cleaner = Cleaner.create();
    static {
        NativeLibLoader.INSTANCE.loadLib("live2d");
    }
    public static native void init(long handle);
}
