package com.primogemstudio.advancedfmk.flutter;

import org.lwjgl.system.Callback;

public abstract class ProcResolver extends Callback implements ProcResolverI {
    protected ProcResolver() {
        super(CIF);
    }

    protected ProcResolver(long functionPointer) {
        super(functionPointer);
    }

    public static ProcResolver create(ProcResolverI callback) {
        return callback instanceof ProcResolver pr ? pr : new Container(callback.address(), callback);
    }

    private static class Container extends ProcResolver {
        private final ProcResolverI delegate;

        Container(long functionPointer, ProcResolverI delegate) {
            super(functionPointer);
            this.delegate = delegate;
        }

        @Override
        public long invoke(long instance, long name) {
            return delegate.invoke(instance, name);
        }
    }
}
