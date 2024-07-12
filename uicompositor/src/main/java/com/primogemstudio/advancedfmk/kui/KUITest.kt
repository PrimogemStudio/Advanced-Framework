package com.primogemstudio.advancedfmk.kui

import com.primogemstudio.advancedfmk.kui.elements.GroupElement
import com.primogemstudio.advancedfmk.kui.elements.RectangleElement
import com.primogemstudio.advancedfmk.kui.elements.TextElement
import com.primogemstudio.advancedfmk.kui.pipe.PostShaderFilter
import com.primogemstudio.advancedfmk.kui.yaml.YamlParser
import com.primogemstudio.advancedfmk.kui.yaml.jvm.ObjBuilder
import net.minecraft.resources.ResourceLocation
import org.joml.Vector2f
import org.joml.Vector4f
import org.ladysnake.satin.api.managed.ShaderEffectManager

class KUITest {
    val elem = GroupElement(
        "main",
        listOf(
            GroupElement(
                "test0",
                listOf(
                    RectangleElement(
                        "test1",
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
                    )
                )
            ),
            TextElement(
                "test",
                Vector2f(0f, 0f),
                "测试！Hello world from UI compositor!",
                Vector4f(1f),
                9
            )
        )
    )
}

fun main() {
    val r0 = YamlParser.parse(
        String(
            KUITest::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/test.yaml")!!.readAllBytes()
        )
    )
    ObjBuilder(r0).build()
}