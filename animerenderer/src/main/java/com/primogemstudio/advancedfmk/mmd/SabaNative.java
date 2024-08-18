package com.primogemstudio.advancedfmk.mmd;

import com.primogemstudio.advancedfmk.client.NativeLibLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.lang.ref.Cleaner;
import java.util.Objects;
import java.lang.ref.Cleaner;

public final class SabaNative {
    static Cleaner cleaner = Cleaner.create();
    public static void init() {
        NativeLibLoader.INSTANCE.loadLib("saba");
    }
}
