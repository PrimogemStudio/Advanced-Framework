package com.primogemstudio.advancedfmk.client

import com.primogemstudio.advancedfmk.mmd.SabaNative
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.FileOutputStream
import java.util.*

object NativeLibLoader {
    private var logger: Logger = LogManager.getLogger(
        NativeLibLoader::class.java
    )
    var available = mutableMapOf<String, Boolean>()

    fun loadLib(libname: String) {
        if (available(libname)) return;
        try {
            val arch = System.getProperty("os.arch")
            var path = "/natives/$libname/"
            val os = System.getProperty("os.name")
            var name = ""
            if (arch == "aarch64") {
                if (!os.contains("Mac OS")) throw RuntimeException("Unsupported your system on ARM")
            }
            else if (arch == "amd64") {
                if (os.contains("Mac OS")) throw RuntimeException("Unsupported Mac OS on x86")
            }
            if (os.contains("Windows")) {
                name = "$libname-native.dll"
                path += name
            }
            else if (os.contains("Linux")) {
                name = "lib$libname-native.so"
                path += name
            }
            else if (os.contains("Mac OS")) {
                name = "lib$libname-native.dylib"
                path += name
            }
            if (path.endsWith("/")) throw RuntimeException("Unsupported system: $os")
            val lib = System.getProperty("java.io.tmpdir") + "/" + name
            try {
                FileOutputStream(lib).use { fo ->
                    SabaNative::class.java.getResourceAsStream(path).use { `in` ->
                        fo.write(Objects.requireNonNull(`in`).readAllBytes())
                    }
                }
                System.load(lib)
            }
            catch (e: Throwable) {
                throw RuntimeException(e)
            }
            available[libname] = true
        }
        catch (e: Throwable) {
            available[libname] = false
            logger.warn("advancedfmk-animerenderer doesn't support this platform! $libname lib is unavailable")
        }
    }
}
