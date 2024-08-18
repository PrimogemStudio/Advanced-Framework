package com.primogemstudio.advancedfmk.live2d;

import com.primogemstudio.advancedfmk.client.NativeLibLoader;

import java.lang.ref.Cleaner;

public class Live2DNative {
    static Cleaner cleaner = Cleaner.create();
    public static void init() {
        NativeLibLoader.INSTANCE.loadLib("live2d");
    }
}
