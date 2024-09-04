package com.primogemstudio.advancedfmk.screen

import glm_.vec2.Vec2
import imgui.Cond
import imgui.ImGui
import imgui.api.slider

object ImguiDebug {
    private var Radius = 0
    fun init() { ImGui.io.fonts.addFontFromFileTTF("/usr/share/fonts/StarRailFont.ttf", 14f)!! }

    fun render(imgui: ImGui) {
        imgui.begin("Minecraft")
        imgui.setWindowSize(Vec2(320, 240), Cond.Once)
        imgui.text("Hello Minecraft!")
        imgui.slider("Radius", ::Radius, 1, 100)
    }
}