package com.primogemstudio.advancedfmk.flutter;

public final class Utils {
    public static String getStackTraceString(Throwable throwable) {
        var sb = new StringBuilder();
        for (var element : throwable.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}
