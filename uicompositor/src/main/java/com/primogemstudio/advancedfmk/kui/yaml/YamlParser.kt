package com.primogemstudio.advancedfmk.kui.yaml

import org.yaml.snakeyaml.Yaml

object YamlParser {
    val parser = Yaml()
    fun parse(s: String): UIRoot {
        val i = parser.loadAs(s, Map::class.java)
        return UIRoot(i["class"] as String, (i["root"] as Map<*, *>)["name"] as String, parse(i["root"] as Map<*, *>))
    }

    @Suppress("UNCHECKED_CAST")
    private fun parse(i: Map<*, *>): Component? = when (i["type"]) {
        "group" -> {
            val r = (i["components"] as Map<String, *>).mapValues { parse(it.value as Map<*, *>) }
            GroupComponent(null, r)
        }

        "text" -> parser.loadAs(parser.dump(i.toMutableMap().apply { this["type"] = null }), TextComponent::class.java).apply { type = ComponentType.TEXT }
        "rect" -> parser.loadAs(
            parser.dump(i.toMutableMap().apply { this["type"] = null }),
            RectangleComponent::class.java
        ).apply { type = ComponentType.RECTANGLE }
        "line" -> parser.loadAs(parser.dump(i.toMutableMap().apply { this["type"] = null }), GeometryLineComponent::class.java).apply { type = ComponentType.GEOMETRY_LINE }
        "live2d" -> parser.loadAs(parser.dump(i.toMutableMap().apply { this["type"] = null }), Live2DComponent::class.java).apply { type = ComponentType.LIVE2D }

        else -> null
    }
}