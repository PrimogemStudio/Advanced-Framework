package com.primogemstudio.advancedfmk.flutter;

public class FlutterException extends RuntimeException {
    public final String code;
    public final Object details;

    FlutterException(String code, String message, Object details) {
        super(message);
        this.code = code;
        this.details = details;
    }
}
