package com.primogemstudio.advancedfmk.util;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import java.io.File;

public class NativeFileDialog {
    public static File openFileDialog(String title, String defaultPath, String[] filters, String filterDescription) {
        String result;
        if (System.getProperty("os.name").startsWith("Windows")) {
            defaultPath = defaultPath.replace("/", "\\");
        } else {
            defaultPath = defaultPath.replace("\\", "/");
        }
        if (filters != null && filters.length > 0) {
            try (var stack = MemoryStack.stackPush()) {
                var pointerBuffer = stack.mallocPointer(filters.length);
                for (String filterPattern : filters) {
                    pointerBuffer.put(stack.UTF8(filterPattern));
                }
                pointerBuffer.flip();
                result = TinyFileDialogs.tinyfd_openFileDialog(title, defaultPath, pointerBuffer, filterDescription, false);
            }
        } else {
            result = TinyFileDialogs.tinyfd_openFileDialog(title, defaultPath, null, filterDescription, false);
        }
        return result != null ? new File(result) : null;
    }
}
