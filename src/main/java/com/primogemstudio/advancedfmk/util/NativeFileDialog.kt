package com.primogemstudio.advancedfmk.util

import org.lwjgl.system.MemoryStack
import org.lwjgl.util.tinyfd.TinyFileDialogs
import java.io.File

object NativeFileDialog {
    fun openFileDialog(
        title: String,
        defaultPath: String,
        filters: Array<String>,
        filterDescription: String
    ): File? {
        var defaultPath = defaultPath
        var result: String?
        defaultPath = if (System.getProperty("os.name").startsWith("Windows")) {
            defaultPath.replace("/", "\\")
        }
        else {
            defaultPath.replace("\\", "/")
        }
        if (!filters.isNullOrEmpty()) {
            MemoryStack.stackPush().use { stack ->
                val pointerBuffer = stack.mallocPointer(filters.size)
                filters.forEach { pointerBuffer.put(stack.UTF8(it)) }
                pointerBuffer.flip()
                result = TinyFileDialogs.tinyfd_openFileDialog(title, defaultPath, pointerBuffer, filterDescription, false)
            }
        }
        else {
            result = TinyFileDialogs.tinyfd_openFileDialog(title, defaultPath, null, filterDescription, false)
        }
        return if (result != null) File(result) else null
    }
}