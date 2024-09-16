package com.primogemstudio.advancedfmk.flutter;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.libffi.FFICIF;

import static org.lwjgl.system.APIUtil.apiClosureRet;
import static org.lwjgl.system.APIUtil.apiCreateCIF;
import static org.lwjgl.system.MemoryUtil.memGetAddress;
import static org.lwjgl.system.libffi.LibFFI.*;

public interface BoolCallbackI extends CallbackI {
    FFICIF CIF = apiCreateCIF(FFI_DEFAULT_ABI, ffi_type_uint8, ffi_type_pointer);

    @Override
    @NotNull
    default FFICIF getCallInterface() {
        return CIF;
    }

    @Override
    default void callback(long ret, long args) {
        apiClosureRet(ret, invoke(memGetAddress(memGetAddress(args))));
    }

    boolean invoke(long instance);
}
