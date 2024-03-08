package com.primogemstudio.advancedfmk.render.uiframework

import org.luaj.vm2.Globals
import org.luaj.vm2.LoadState
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.TwoArgFunction
import org.luaj.vm2.lib.jse.JsePlatform
import org.luaj.vm2.luajc.LuaJC

object LuaVM {
    var globals: Globals = JsePlatform.standardGlobals()
    init {
        globals["fetch_pos"] = object: TwoArgFunction() {
            override fun call(arg1: LuaValue, arg2: LuaValue): LuaValue = arg1.sub(arg2).div(2)
        }
        // LuaC.install(globals)
        // LuaJC.install(globals)
        LoadState.install(globals)
    }

    fun init() {}
}