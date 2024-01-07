package com.primogemstudio.mmdbase.abstraction

interface ITextureManager {
    fun register(prefix: String)
    fun release()
}