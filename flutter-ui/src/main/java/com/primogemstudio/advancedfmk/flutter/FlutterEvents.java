package com.primogemstudio.advancedfmk.flutter;

import java.util.HashSet;

public class FlutterEvents {
    static final HashSet<FlutterInstance> instances = new HashSet<>();

    public static void register(FlutterInstance instance) {
        instances.add(instance);
    }

    public static void unregister(FlutterInstance instance) {
        instances.remove(instance);
    }
}
