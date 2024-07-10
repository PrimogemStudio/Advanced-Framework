package com.primogemstudio.advancedfmk.kui

import com.primogemstudio.advancedfmk.kui.elements.GroupElement
import com.primogemstudio.advancedfmk.kui.elements.RectangleElement
import com.primogemstudio.advancedfmk.kui.elements.TextElement
import com.primogemstudio.advancedfmk.kui.pipe.PostShaderFilter
import net.minecraft.resources.ResourceLocation
import org.joml.Vector2f
import org.joml.Vector4f
import org.ladysnake.satin.api.managed.ShaderEffectManager
import java.io.FileReader
import java.util.function.Predicate
import javax.script.Invocable
import javax.script.ScriptContext
import javax.script.ScriptEngineManager

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
}

fun main() {
    System.setProperty("polyglot.engine.WarnInterpreterOnly", "false")
    val engineManager = ScriptEngineManager()
    val jsEngine = engineManager.getEngineByName("graal.js")
    val funcCall = jsEngine as Invocable

    val bindings = jsEngine.getBindings(ScriptContext.ENGINE_SCOPE)
    bindings["polyglot.js.allowHostAccess"] = true
    bindings["polyglot.js.allowHostClassLookup"] = Predicate<String> { true }

    val jsResource = KUITest::class.java.classLoader.getResource("assets/advancedfmk/ui/ui.js")
    jsEngine.eval(FileReader(jsResource?.path ?: ""))

    val res = funcCall.invokeFunction("f", mutableMapOf(Pair("a", "abc")))
    res as Map<*, *>
    println((res["msg"] as Map<*, *>)["a"])
}