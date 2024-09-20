package com.primogemstudio.advancedfmk.flutter;

import static com.primogemstudio.advancedfmk.flutter.Events.instances;

public class MouseEvent {
    public static void onMouseButton(int phase, double x, double y) {
        instances.forEach(i -> {
            if (i.hitTest(x, y)) FlutterNative.sendPosEvent(i.handle, phase, x - i.rect.left, y - i.rect.top, 0);
        });
    }
}
