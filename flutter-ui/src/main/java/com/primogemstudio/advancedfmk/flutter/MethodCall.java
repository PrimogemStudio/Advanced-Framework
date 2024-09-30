package com.primogemstudio.advancedfmk.flutter;

import com.google.gson.JsonObject;

import java.util.Map;

public class MethodCall {
    public final String method;
    public final Object arguments;

    public MethodCall(String method, Object arguments) {
        this.method = method;
        this.arguments = arguments;
    }

    @SuppressWarnings("unchecked")
    public <T> T arguments() {
        return (T) arguments;
    }

    @SuppressWarnings("unchecked")
    public <T> T argument(String key) {
        return switch (arguments) {
            case null -> null;
            case Map<?, ?> map -> (T) map.get(key);
            case JsonObject json -> (T) json.get(key);
            default -> throw new ClassCastException();
        };
    }

    public boolean hasArgument(String key) {
        return switch (arguments) {
            case null -> false;
            case Map<?, ?> map -> map.containsKey(key);
            case JsonObject json -> json.has(key);
            default -> throw new ClassCastException();
        };
    }
}
