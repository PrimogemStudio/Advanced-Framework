package com.primogemstudio.advancedfmk.flutter;

import static com.primogemstudio.advancedfmk.flutter.Events.instances;
import static com.primogemstudio.advancedfmk.flutter.PointerPhase.*;
import static com.primogemstudio.advancedfmk.flutter.SignalKind.*;

public class MouseEvent {
    public static boolean onMouseButton(int phase, double x, double y) {
        for (var i : instances) {
            if (i.hitTest(x, y)) {
                FlutterNative.sendPointerEvent(i.handle, phase, x - i.rect.left, y - i.rect.top, None, 0, 0, 0);
                if (phase == PointerPhase.kDown) i.pressed = true;
                else if (phase == kUp) i.pressed = false;
                return true;
            }
        }
        return false;
    }

    public static boolean onMouseMove(double x, double y) {
        for (var i : instances) {
            if (i.hitTest(x, y)) {
                FlutterNative.sendPointerEvent(i.handle, i.pressed ? kMove : kHover, x - i.rect.left, y - i.rect.top, None, 0, 0, 0);
                return true;
            }
        }
        return false;
    }

    public static boolean onMouseScroll(double x, double y, double dx, double dy) {
        for (var i : instances) {
            if (i.hitTest(x, y)) {
                FlutterNative.sendPointerEvent(i.handle, i.pressed ? kUp : kHover, x - i.rect.left, y - i.rect.top, Scroll, dx * 20, -dy * 20, 0);
                return true;
            }
        }
        return false;
    }
}
