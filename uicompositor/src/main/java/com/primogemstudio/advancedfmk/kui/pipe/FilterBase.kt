package com.primogemstudio.advancedfmk.kui.pipe

import com.primogemstudio.advancedfmk.kui.GlobalData

interface FilterBase {
    fun init()
    fun apply(data: GlobalData)
    fun arg(key: String, a: Int)
    fun arg(key: String, a: Int, b: Int)
    fun arg(key: String, a: Int, b: Int, c: Int)
    fun arg(key: String, a: Int, b: Int, c: Int, d: Int)
    fun arg(key: String, a: Float)
    fun arg(key: String, a: Float, b: Float)
    fun arg(key: String, a: Float, b: Float, c: Float)
    fun arg(key: String, a: Float, b: Float, c: Float, d: Float)
    fun arg(key: String, aa: Float, ab: Float, ac: Float, ad: Float, ba: Float, bb: Float, bc: Float, bd: Float, ca: Float, cb: Float, cc: Float, cd: Float, da: Float, db: Float, dc: Float, dd: Float)

    var args: MutableMap<String, Any>
}