package com.primogemstudio.advancedfmk.tests

import com.primogemstudio.advancedfmk.flutter.BoolCallback
import com.primogemstudio.advancedfmk.flutter.FlutterNative
import com.primogemstudio.advancedfmk.flutter.PointerPhase.*
import com.primogemstudio.advancedfmk.flutter.RendererConfig
import com.primogemstudio.advancedfmk.flutter.UIntCallback
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFW.Functions.*

var flutterInstance: Long = 0

fun main() {
    glfwInit()
    FlutterNative.init(GetKeyName, GetClipboardString, SetClipboardString, GetProcAddress)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
    val window = glfwCreateWindow(800, 600, "Flutter", 0, 0)
    val config = RendererConfig()
    config.makeCurrent = BoolCallback.create {
        glfwMakeContextCurrent(window)
        true
    }
    config.present = BoolCallback.create {
        glfwSwapBuffers(window)
        true
    }
    config.clearCurrent = BoolCallback.create {
        glfwMakeContextCurrent(0)
        true
    }
    config.fbo = UIntCallback.create { 0 }
    flutterInstance = FlutterNative.createInstance("F:/c++/glfw-flutter/app", config)
    val width = intArrayOf(0)
    val height = intArrayOf(0)
    glfwGetFramebufferSize(window, width, height)
    FlutterNative.setPixelRatio(flutterInstance, width[0].toDouble() / 800)
    FlutterNative.sendMetricsEvent(flutterInstance, 800, 600, 0)

    glfwSetWindowSizeCallback(window) { _, w, h ->
        FlutterNative.sendMetricsEvent(flutterInstance, w, h, 0)
    }
    glfwSetKeyCallback(window) { _, key, scancode, action, mods ->
        if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
            glfwSetWindowShouldClose(window, true)
        }
        FlutterNative.sendKeyEvent(flutterInstance, window, key, scancode, action, mods);
    }
    glfwSetCharCallback(window) { _, codepoint ->
        FlutterNative.sendCharEvent(flutterInstance, window, codepoint)
    }
    glfwSetMouseButtonCallback(window) { _, button, action, _ ->
        if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS) {
            val x = doubleArrayOf(0.0)
            val y = doubleArrayOf(0.0)
            glfwGetCursorPos(window, x, y)
            FlutterNative.sendPosEvent(flutterInstance, kDown, x[0], y[0], 0)
            glfwSetCursorPosCallback(window) { _, x1, y1 ->
                FlutterNative.sendPosEvent(flutterInstance, kMove, x1, y1, 0)
            }
        }

        if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_RELEASE) {
            val x = doubleArrayOf(0.0)
            val y = doubleArrayOf(0.0)
            glfwGetCursorPos(window, x, y)
            FlutterNative.sendPosEvent(flutterInstance, kUp, x[0], y[0], 0)
            glfwSetCursorPosCallback(window, null)
        }
    }
    while (!glfwWindowShouldClose(window)) {
        glfwPollEvents()
    }

    FlutterNative.destroyInstance(flutterInstance)
    glfwDestroyWindow(window)
    glfwTerminate()
}