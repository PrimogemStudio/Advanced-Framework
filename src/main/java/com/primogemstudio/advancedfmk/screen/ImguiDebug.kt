package com.primogemstudio.advancedfmk.screen

import glm_.vec2.Vec2
import imgui.Cond
import imgui.ImGui

object ImguiDebug {
    var RadiusSize = floatArrayOf(1f, 1f, 1f, 1f)
    fun init() { ImGui.io.fonts.addFontFromFileTTF("/usr/share/fonts/StarRailFont.ttf", 14f)!! }

    fun render(imgui: ImGui) {
        imgui.begin("Advvanced Framework")
        imgui.setWindowSize(Vec2(320, 240), Cond.Once)
        imgui.text("UI Adjustment")
        imgui.slider4("Radius Size", RadiusSize, 0f, 1f)
    }
}