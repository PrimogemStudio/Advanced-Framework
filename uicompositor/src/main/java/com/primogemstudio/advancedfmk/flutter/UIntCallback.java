package com.primogemstudio.advancedfmk.flutter;

import org.lwjgl.system.Callback;

public abstract class UIntCallback extends Callback implements UIntCallbackI {
    protected UIntCallback() {
        super(CIF);
    }

    protected UIntCallback(long functionPointer) {
        super(functionPointer);
    }

    public static UIntCallback create(UIntCallbackI callback) {
        return callback instanceof UIntCallback bc ? bc : new Container(callback.address(), callback);
    }

    private static class Container extends UIntCallback {
        private final UIntCallbackI delegate;

        Container(long functionPointer, UIntCallbackI delegate) {
            super(functionPointer);
            this.delegate = delegate;
        }

        @Override
        public int invoke(long instance) {
            return delegate.invoke(instance);
        }
    }
}
