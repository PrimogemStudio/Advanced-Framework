package com.primogemstudio.advancedfmk.kui.pipe

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import com.primogemstudio.advancedfmk.kui.GlobalData
import net.minecraft.client.Minecraft
import net.minecraft.client.Minecraft.ON_OSX
import net.minecraft.client.renderer.GameRenderer

class ClipFilter: FilterBase {
    override var args: MutableMap<String, Any> = mutableMapOf()
    override fun init() {
        val f = { s: String -> args[s] as Int }
        COMPOSE_FRAME.resize(f("width"), f("height"), ON_OSX)
        COMPOSE_FRAME.clear(ON_OSX)
        COMPOSE_FRAME.bindWrite(true)
    }

    override fun apply(data: GlobalData) {
        val f = { s: String -> (args[s] as Int).toFloat() }
        Minecraft.getInstance().mainRenderTarget.bindWrite(true)
        val buff = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX)
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderTexture(0, COMPOSE_FRAME.colorTextureId)
        buff.addVertex(f("x"), f("y"), 0f).setUv(0f, 0f)
        buff.addVertex(f("x"), f("y") + f("height"), 0f).setUv(0f, 1f)
        buff.addVertex(f("x") + f("width"), f("y") + f("height"), 0f).setUv(1f, 1f)
        buff.addVertex(f("x") + f("width"), f("y"), 0f).setUv(1f, 0f)

        RenderSystem.disableBlend()
        RenderSystem.disableCull()
        BufferUploader.drawWithShader(buff.buildOrThrow())
    }

    override fun arg(key: String, a: Int) {
        when (key) {
            "width" -> args[key] = a
            "height" -> args[key] = a
            "x" -> args[key] = a
            "y" -> args[key] = a
        }
    }
    override fun arg(key: String, a: Int, b: Int) {}
    override fun arg(key: String, a: Int, b: Int, c: Int) {}
    override fun arg(key: String, a: Int, b: Int, c: Int, d: Int) {}
    override fun arg(key: String, a: Float) {}
    override fun arg(key: String, a: Float, b: Float) {}
    override fun arg(key: String, a: Float, b: Float, c: Float) {}
    override fun arg(key: String, a: Float, b: Float, c: Float, d: Float) {}
    override fun arg(
        key: String,
        aa: Float,
        ab: Float,
        ac: Float,
        ad: Float,
        ba: Float,
        bb: Float,
        bc: Float,
        bd: Float,
        ca: Float,
        cb: Float,
        cc: Float,
        cd: Float,
        da: Float,
        db: Float,
        dc: Float,
        dd: Float
    ) {}
}