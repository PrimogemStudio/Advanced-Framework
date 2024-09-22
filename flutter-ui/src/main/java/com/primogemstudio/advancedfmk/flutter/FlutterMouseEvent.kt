package com.primogemstudio.advancedfmk.flutter

import com.primogemstudio.advancedfmk.flutter.FlutterPointerPhase.kDown
import com.primogemstudio.advancedfmk.flutter.FlutterPointerPhase.kHover
import com.primogemstudio.advancedfmk.flutter.FlutterPointerPhase.kMove
import com.primogemstudio.advancedfmk.flutter.FlutterPointerPhase.kUp

object FlutterMouseEvent {
    fun onMouseButton(phase: Int, x: Double, y: Double): Boolean {
        for (i in FlutterEvents.instances) {
            if (i.hitTest(x, y)) {
                FlutterNative.sendPointerEvent(
                    i.handle,
                    phase,
                    x - i.rect.left,
                    y - i.rect.top,
                    FlutterSignalKind.None,
                    0.0,
                    0.0,
                    0
                )
                if (phase == kDown) i.pressed = true
                else if (phase == kUp) i.pressed = false
                return true
            }
        }
        return false
    }

    fun onMouseMove(x: Double, y: Double): Boolean {
        for (i in FlutterEvents.instances) {
            if (i.hitTest(x, y)) {
                FlutterNative.sendPointerEvent(
                    i.handle,
                    if (i.pressed) kMove else kHover,
                    x - i.rect.left,
                    y - i.rect.top,
                    FlutterSignalKind.None,
                    0.0,
                    0.0,
                    0
                )
                return true
            }
        }
        return false
    }

    fun onMouseScroll(x: Double, y: Double, dx: Double, dy: Double): Boolean {
        for (i in FlutterEvents.instances) {
            if (i.hitTest(x, y)) {
                FlutterNative.sendPointerEvent(
                    i.handle,
                    if (i.pressed) kUp else kHover,
                    x - i.rect.left,
                    y - i.rect.top,
                    FlutterSignalKind.Scroll,
                    dx * 20,
                    -dy * 20,
                    0
                )
                return true
            }
        }
        return false
    }
}
