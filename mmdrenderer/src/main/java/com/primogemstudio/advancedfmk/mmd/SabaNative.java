package com.primogemstudio.advancedfmk.mmd;

import java.io.FileOutputStream;
import java.lang.ref.Cleaner;
import java.util.Objects;

public final class SabaNative {
    static {
        var arch = System.getProperty("os.arch");
        var path = "";
        var os = System.getProperty("os.name");
        var name = "";
        if (arch.equals("aarch64")) {
            if (os.contains("Windows")) throw new RuntimeException("Unsupported Windows on ARM");
            path = "/natives/arm/";
        } else if (arch.equals("amd64")) {
            if (os.contains("Mac OS")) throw new RuntimeException("Unsupported Mac OS on x86");
            path = "/natives/";
        }
        if (path.isEmpty()) throw new RuntimeException("Unsupported CPU architecture: " + arch);
        if (os.contains("Windows")) path += name = "saba-native.dll";
        else if (os.contains("Linux")) path += name = "libsaba-native.so";
        else if (os.contains("Mac OS")) path += name = "libsaba-native.dylib";
        if (path.endsWith("/")) throw new RuntimeException("Unsupported system: " + os);
        var lib = System.getProperty("java.io.tmpdir") + "/" + name;
        try {
            try (var fo = new FileOutputStream(lib); var in = SabaNative.class.getResourceAsStream(path)) {
                fo.write(Objects.requireNonNull(in).readAllBytes());
            }
            System.load(lib);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static final Cleaner cleaner = Cleaner.create();

    public static void init() {
    }
}
