package com.primogemstudio.advancedfmk.render.uiframework

import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import glm_.vec4.Vec4
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import java.lang.reflect.Type
import java.util.function.Consumer
import java.util.function.Function
import java.util.regex.Pattern

typealias ValueFetcher = Function<Map<String, Float>, Float>

class RLGsonParser: TypeAdapter<ResourceLocation>() {
    override fun write(p0: JsonWriter?, p1: ResourceLocation?) { p0?.value("$p1") }
    override fun read(p0: JsonReader?): ResourceLocation = ResourceLocation(p0?.nextString()!!)
}
class Vec4GsonParser: TypeAdapter<Vec4>() {
    override fun write(p0: JsonWriter?, p1: Vec4?) {
        p0?.beginArray()
            ?.value(p1?.get(0))
            ?.value(p1?.get(1))
            ?.value(p1?.get(2))
            ?.value(p1?.get(3))
        ?.endArray()
    }
    override fun read(p0: JsonReader?): Vec4 {
        val v = Vec4()
        p0!!.beginArray()
        var i = 0
        while (p0.peek() != JsonToken.END_ARRAY) {
            v[i] = p0.nextDouble().toFloat()
            i++
        }
        p0.endArray()
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
    fun parse(json: String): Map<ResourceLocation, CompositeObject> {
        with(GsonBuilder()
            .registerTypeAdapter(ResourceLocation::class.java, RLGsonParser())
            .registerTypeAdapter(Vec4::class.java, Vec4GsonParser())
            .registerTypeAdapter(Function::class.java, ValueGsonParser())
            .create()) {
            val a = fromJson(json, Map::class.java).mapKeys { ResourceLocation(it.key.toString()) }
            return a.mapValues { fromJson(toJson(it.value), CompositeObject::class.java) }
        }
    }
}

data class CompositeObject(
    var type: ResourceLocation,
    var thickness: Float = 0f,
    var smoothedge: Float = 0.0005f,
    var radius: Float = 25f,
    var color: Vec4 = Vec4(0f),
    var filter: CompositeFilter? = null,
    var location: Map<String, ValueFetcher>
) {
    data class CompositeFilter(
        var type: ResourceLocation,
        var args: Map<String, Any> = mutableMapOf()
    )
}

