package com.primogemstudio.advancedfmk.render.uiframework

import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.primogemstudio.advancedfmk.render.uiframework.ui.UICompound
import com.primogemstudio.advancedfmk.render.uiframework.ui.UIRect
import net.minecraft.resources.ResourceLocation
import org.joml.Vector4f
import java.lang.reflect.Type
import java.util.function.Function
import java.util.regex.Pattern

typealias ValueFetcher = Function<Map<String, Float>, Float>

class RLGsonParser: TypeAdapter<ResourceLocation>() {
    override fun write(p0: JsonWriter?, p1: ResourceLocation?) { p0?.value("$p1") }
    override fun read(p0: JsonReader?): ResourceLocation = ResourceLocation(p0?.nextString()!!)
}
class Vector4fGsonParser: TypeAdapter<Vector4f>() {
    override fun write(p0: JsonWriter?, p1: Vector4f?) {
        p0?.beginArray()
            ?.value(p1?.get(0))
            ?.value(p1?.get(1))
            ?.value(p1?.get(2))
            ?.value(p1?.get(3))
        ?.endArray()
    }
    override fun read(p0: JsonReader?): Vector4f {
        val v = Vector4f()
        p0!!.beginArray()
        var i = 0
        val lst = FloatArray(4)
        while (p0.peek() != JsonToken.END_ARRAY) {
            lst[i] = p0.nextDouble().toFloat()
            i++
            if (i >= 4) break
        }
        p0.endArray()
        v.set(lst)
        return v
    }
}
class ValueGsonParser: JsonDeserializer<Function<Map<String, Float>, Float>> {
    private val pattern = Pattern.compile("(?<func>.*)\\((?<a>.*),(?<b>.*)\\)")
    override fun deserialize(p0: JsonElement?, p1: Type?, p2: JsonDeserializationContext?): Function<Map<String, Float>, Float> {
        return Function { w ->
            if (p0!!.isJsonPrimitive) return@Function p0.asFloat
            val matcher = pattern.matcher(p0.asJsonObject["calc"].asString)
            if (!matcher.find()) 0f
            else {
                val func = matcher.group("func")
                val f = { i: String -> i.let { w[it]?: try { it.toFloat() } catch (e: Exception) { 0 } }.toFloat() }
                val v0 = f(matcher.group("a"))
                val v1 = f(matcher.group("b"))
                return@Function when (func) {
                    "fetch_pos" -> (v0 - v1) / 2
                    else -> 0f
                }
            }
        }
    }
}

operator fun <T, R> Function<T, R>.invoke(a: T): R = this.apply(a)
object Compositor {
    fun parseNew(json: String): UICompound {
        with(GsonBuilder()
            .registerTypeAdapter(ResourceLocation::class.java, RLGsonParser())
            .registerTypeAdapter(Vector4f::class.java, Vector4fGsonParser())
            .registerTypeAdapter(Function::class.java, ValueGsonParser())
            .create()) {
            val a = fromJson(json, Map::class.java)
            val comp = (a["components"] as Map<*, *>).mapKeys { ResourceLocation(it.key.toString()) }
            return UICompound(comp.mapValues {
                when ((it.value as Map<*, *>)["type"]) {
                    "advancedfmk:rectangle" -> fromJson(toJson(it.value), UIRect::class.java)
                    "advancedfmk:compound" -> parseNew(toJson(it.value))
                    else -> UICompound()
                }
            }, ResourceLocation(a["topComponent"].toString()))
        }
    }
}