package com.primogemstudio.advancedfmk.flutter;

import org.lwjgl.system.Callback;

public abstract class BoolCallback extends Callback implements BoolCallbackI {
    protected BoolCallback() {
        super(CIF);
    }

    protected BoolCallback(long functionPointer) {
        super(functionPointer);
    }

    public static BoolCallback create(BoolCallbackI callback) {
        return callback instanceof BoolCallback bc ? bc : new Container(callback.address(), callback);
    }

    private static class Container extends BoolCallback {
        private final BoolCallbackI delegate;

        Container(long functionPointer, BoolCallbackI delegate) {
            super(functionPointer);
            this.delegate = delegate;
        }

        @Override
        public boolean invoke(long instance) {
            return delegate.invoke(instance);
        }
    }
}
