package com.primogemstudio.advancedfmk.flutter

object FlutterKeyEvent {
    fun onKey(window: Long, key: Int, scanCode: Int, action: Int, modifiers: Int) {
        FlutterEvents.instances.forEach { i: FlutterInstance ->
            FlutterNative.sendKeyEvent(
                i.handle,
                window,
                key,
                scanCode,
                action,
                modifiers
            )
        }
    }

    fun onChar(window: Long, code: Int) {
        FlutterEvents.instances.forEach { i: FlutterInstance ->
            FlutterNative.sendCharEvent(
                i.handle,
                window,
                code
            )
        }
    }
}
