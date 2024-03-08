package com.primogemstudio.advancedfmk

import com.primogemstudio.advancedfmk.render.uiframework.Compositor
import com.primogemstudio.advancedfmk.render.uiframework.ui.UICompound
import org.luaj.vm2.LoadState
import org.luaj.vm2.LuaDouble
import org.luaj.vm2.LuaInteger
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.TwoArgFunction
import org.luaj.vm2.lib.jse.JsePlatform
import org.luaj.vm2.luajc.LuaJC
import java.io.InputStreamReader
import java.util.stream.Collectors


fun loadNew(): UICompound {
    return Compositor.parseNew(
        InputStreamReader(Compositor::class.java.classLoader.getResourceAsStream("assets/advancedfmk/ui/starrail_chat_v1.json")).readLines().stream().collect(Collectors.joining("\n"))
    )
}

fun main() {
    val luastr = "uibase_test_func(5,4)"
    val globals = JsePlatform.standardGlobals()
    globals["uibase_test_func"] = object: TwoArgFunction() {
        override fun call(arg1: LuaValue, arg2: LuaValue): LuaValue = arg1.sub(arg2).div(2)
    }
    // LuaC.install(globals)
    LuaJC.install(globals)
    LoadState.install(globals)
    val chunk = globals.load("return $luastr")
    println(chunk().arg1().tonumber())
}