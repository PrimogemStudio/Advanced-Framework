package com.primogemstudio.advancedfmk.flutter;

import static com.primogemstudio.advancedfmk.flutter.Events.instances;

public class KeyEvent {
    public static void onKey(long window, int key, int scanCode, int action, int modifiers) {
        instances.forEach(i -> FlutterNative.sendKeyEvent(i.handle, window, key, scanCode, action, modifiers));
    }

    public static void onChar(long window, int code) {
        instances.forEach(i -> FlutterNative.sendCharEvent(i.handle, window, code));
    }
}
