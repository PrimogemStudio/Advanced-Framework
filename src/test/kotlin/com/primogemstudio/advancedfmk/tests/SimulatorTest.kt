package com.primogemstudio.advancedfmk.tests

import com.primogemstudio.advancedfmk.flutter.FlutterNative
import com.primogemstudio.advancedfmk.flutter.PointerPhase.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFW.Functions.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL30.*

var flutterInstance: Long = 0
var vertexShader: Int = 0
var pixelShader: Int = 0
var shaderProgram: Int = 0

fun createShader() {
    shaderProgram = glCreateProgram()
    vertexShader = glCreateShader(GL_VERTEX_SHADER)
    pixelShader = glCreateShader(GL_FRAGMENT_SHADER)
    glShaderSource(
        vertexShader, """
#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoord;

out vec2 TexCoord;

void main()
{
    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);
    TexCoord = aTexCoord;
}"""
    )
    glShaderSource(
        pixelShader, """
#version 330 core

uniform sampler2D tex;

in vec2 TexCoord;
out vec4 FragColor;

void main()
{
    FragColor = texture(tex,TexCoord);
}"""
    )
    glCompileShader(vertexShader)
    glCompileShader(pixelShader)
    glAttachShader(shaderProgram, vertexShader)
    glAttachShader(shaderProgram, pixelShader)
    glLinkProgram(shaderProgram)
}

var VAO: Int = 0
var VBO: Int = 0
var IBO: Int = 0

fun initResources() {
    val vertices = floatArrayOf(
        1.0f,
        1.0f,
        0.0f,
        1.0f,
        0.0f,

        1.0f,
        -1.0f,
        0.0f,
        1.0f,
        1.0f,

        -1.0f,
        -1.0f,
        0.0f,
        0.0f,
        1.0f,

        -1.0f,
        1.0f,
        0.0f,
        0.0f,
        0.0f
    )
    val indices = intArrayOf(
        0, 1, 3, 1, 2, 3
    )
    VBO = glGenBuffers()
    IBO = glGenBuffers()
    VAO = glGenVertexArrays()
    glBindVertexArray(VAO)
    glBindBuffer(GL_ARRAY_BUFFER, VBO)
    glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO)
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * 4, 0)
    glEnableVertexAttribArray(0)
    glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * 4, 3 * 4)
    glEnableVertexAttribArray(1)
    glBindBuffer(GL_ARRAY_BUFFER, 0)
    glBindVertexArray(0)
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
}

fun main() {
    glfwInit()
    FlutterNative.init(GetKeyName, GetClipboardString, SetClipboardString, GetProcAddress)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
    glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GL_TRUE)
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)
    glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, GLFW_TRUE)
    val window = glfwCreateWindow(800, 600, "Flutter", 0, 0)
    glfwMakeContextCurrent(window)
    GL.createCapabilities()
    initResources()
    createShader()
    flutterInstance = FlutterNative.createInstance("f:/c++/glfw-flutter/app")
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
        FlutterNative.pollEvents(flutterInstance)
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f)
        glClear(GL_COLOR_BUFFER_BIT)
        glUseProgram(shaderProgram)
        glBindVertexArray(VAO)
        glBindTexture(GL_TEXTURE_2D, FlutterNative.getTexture(flutterInstance))
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0)
        glfwSwapBuffers(window)
        glfwPollEvents()
    }

    FlutterNative.destroyInstance(flutterInstance)
    glfwDestroyWindow(window)
    glfwTerminate()
}