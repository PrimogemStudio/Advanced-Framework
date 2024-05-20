package com.primogemstudio.jsengine

import javax.script.Bindings
import javax.script.Invocable
import javax.script.ScriptContext
import javax.script.ScriptEngineManager


class JavascriptWrapper {
    companion object {
        @JvmStatic
        fun main(arr: Array<String>) {
            System.setProperty("polyglot.engine.WarnInterpreterOnly", "false")

            val engineManager = ScriptEngineManager()
            val jsEngine = engineManager.getEngineByName("graal.js")
            val funcCall = jsEngine as Invocable

            val bindings: Bindings = jsEngine.getBindings(ScriptContext.ENGINE_SCOPE)
            bindings.put("polyglot.js.allowHostAccess", true)
            bindings.put("polyglot.js.allowHostClassLookup", true)

            jsEngine.eval(JavascriptWrapper::class.java.classLoader.getResource("test.js")?.readText() ?: "")

            val jsonObject = mutableMapOf(Pair("a", "abc"))

            val res = funcCall.invokeFunction("App")
            println("invoke param: $jsonObject")
            println("invoke result type: " + res.javaClass.name)
            println("invoke result: $res")
        }
    }
}