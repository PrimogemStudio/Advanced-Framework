package com.primogemstudio.advancedfmk.mmd

import java.io.FileOutputStream
import java.lang.ref.Cleaner
import java.util.*


object SabaNative {
    init {
        val arch = System.getProperty("os.arch")
        var path = ""
        val os = System.getProperty("os.name")
        var name = ""
        if (arch == "aarch64") path = "/natives/arm/" else if (arch == "amd64") path = "/natives/"
        if (path.isEmpty()) throw RuntimeException("Unsupported CPU architecture: $arch")
        if (os.contains("Windows")) {
            name = "saba-native.dll"
            path += name
        } else if (os.contains("Linux")) {
            name = "libsaba-native.so"
            path += name
        } else if (os.contains("Mac OS")) {
            name = "libsaba-native.dylib"
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
    }

    val cleaner = Cleaner.create()
    fun releaseWrap(cls: Class<*>?, ptr: Long) {
        if (StackWalker.getInstance().callerClass != PMXModel::class.java) throw IllegalStateException("invalid access")
        release(cls, ptr)
    }
    private external fun release(cls: Class<*>?, ptr: Long)
    fun init() {}
}

