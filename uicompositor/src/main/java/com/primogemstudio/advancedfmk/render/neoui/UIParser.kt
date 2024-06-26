package com.primogemstudio.advancedfmk.render.neoui

import org.yaml.snakeyaml.Yaml

class UIParser {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val t = Yaml().load<Map<String, Any>>(UIParser::class.java.classLoader.getResourceAsStream("test.yaml"))
            println(t)
        }
    }
}