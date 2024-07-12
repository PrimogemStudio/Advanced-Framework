package com.primogemstudio.advancedfmk.kui.yaml

import org.yaml.snakeyaml.Yaml

object YamlParser {
    val parser = Yaml()
    fun parse(s: String): UIRoot {
        val i = parser.loadAs(s, Map::class.java)
        return UIRoot(i["class"] as String, parse(i["root"] as Map<*, *>))
    }

    @Suppress("UNCHECKED_CAST")
    private fun parse(i: Map<*, *>): Component? = when (i["type"]) {
        "group" -> {
            val r = (i["components"] as Map<String, *>).mapValues { parse(it.value as Map<*, *>) }
            GroupComponent(r)
        }

        "text" -> parser.loadAs(parser.dump(i.toMutableMap().apply { this["type"] = null }), TextComponent::class.java)
        "rect" -> parser.loadAs(
            parser.dump(i.toMutableMap().apply { this["type"] = null }),
            RectangleComponent::class.java
        )

        else -> null
    }
}