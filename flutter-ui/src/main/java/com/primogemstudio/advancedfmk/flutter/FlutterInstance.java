package com.primogemstudio.advancedfmk.flutter;

public class FlutterInstance implements AutoCloseable {
    public final long handle;
    public final Rect rect;
    public int width, height;
    public boolean pressed;

    public FlutterInstance(String assets, Rect rect, int width, int height) {
        handle = FlutterNative.createInstance(assets);
        this.rect = rect;
        this.width = width;
        this.height = height;
        FlutterNative.sendMetricsEvent(handle, rect.right - rect.left, rect.bottom - rect.top, 0);
        Events.register(this);
    }

    public boolean hitTest(double x, double y) {
        return x > rect.left && x < rect.right && y > rect.top && y < rect.bottom;
    }

    public void pollEvents() {
        FlutterNative.pollEvents(handle);
    }

    public int getTexture() {
        return FlutterNative.getTexture(handle);
    }

    @Override
    public void close() {
        Events.unregister(this);
        FlutterNative.destroyInstance(handle);
    }
}
