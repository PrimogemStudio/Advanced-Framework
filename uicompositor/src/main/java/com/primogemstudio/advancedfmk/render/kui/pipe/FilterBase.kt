package com.primogemstudio.advancedfmk.render.kui.pipe

import com.primogemstudio.advancedfmk.render.kui.GlobalData

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
}