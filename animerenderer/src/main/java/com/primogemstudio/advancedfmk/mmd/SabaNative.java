package com.primogemstudio.advancedfmk.mmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.lang.ref.Cleaner;
import java.util.Objects;

public final class SabaNative {
    private static boolean available = true;
    private static Logger logger = LogManager.getLogger(SabaNative.class);
    static {
        try {
            var arch = System.getProperty("os.arch");
            var path = "/natives/saba/";
            var os = System.getProperty("os.name");
            var name = "";
            if (arch.equals("aarch64")) {
                if (!os.contains("Mac OS")) throw new RuntimeException("Unsupported your system on ARM");
            } else if (arch.equals("amd64")) {
                if (os.contains("Mac OS")) throw new RuntimeException("Unsupported Mac OS on x86");
            }
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
        } catch (Throwable e) {
            available = false;
            logger.warn("advancedfmk-animerenderer doesn't support this platform! MMD renderer is unavailable");
        }
    }

    public static boolean getAvailable() {
        return available;
    }

    static final Cleaner cleaner = Cleaner.create();

    public static void init() {
    }
}
