package com.primogemstudio.advancedfmk.kui

import com.primogemstudio.advancedfmk.kui.elements.GroupElement
import com.primogemstudio.advancedfmk.kui.elements.RectangleElement
import com.primogemstudio.advancedfmk.kui.elements.TextElement
import com.primogemstudio.advancedfmk.kui.pipe.PostShaderFilter
import net.minecraft.resources.ResourceLocation
import org.joml.Vector2f
import org.joml.Vector4f
import org.ladysnake.satin.api.managed.ShaderEffectManager

class KUITest {
    val elem = GroupElement(
        listOf(
            RectangleElement(
                Vector2f(0f, 0f),
                Vector2f(100f, 100f),
                Vector4f(1f, 1f, 1f, 0.25f),
                20f,
                0f,
                0.006f,
                ResourceLocation.parse("advancedfmk:ui/textures/microsoft.png"),
                PostShaderFilter(
                    ShaderEffectManager.getInstance()
                        .manage(ResourceLocation.withDefaultNamespace("shaders/filter/gaussian_blur.json"))
                )
            ),
            TextElement(
                Vector2f(0f, 0f),
                "测试！Hello world from UI compositor!",
                Vector4f(1f),
                9
            )
        )
    )

    fun render(gd: GlobalData) {
        elem.render(gd)
    }
}