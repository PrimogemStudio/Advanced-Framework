package com.primogemstudio.advancedfmk.render.shape

import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import com.primogemstudio.advancedfmk.AdvancedFramework.Companion.MOD_ID
import com.primogemstudio.advancedfmk.render.RenderResource
import com.primogemstudio.advancedfmk.render.Shaders
import com.primogemstudio.advancedfmk.render.uiframework.BaseTexture
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

class RoundedRectangleTex(x: Float, y: Float, w: Float, h: Float, message: Component?, texture: AbstractTexture): RoundedRectangle(x, y, w, h, message) {
    companion object {
        private var texIdx = 0
    }
    val r: ResourceLocation = ResourceLocation(MOD_ID, "tex$texIdx")
    init {
        texIdx++
        Minecraft.getInstance().textureManager.register(r, texture)
    }
    override fun render(res: RenderResource?) {
        RenderSystem.setShaderTexture(0, r)
        RenderSystem.setShader { Shaders.ROUNDED_RECT_TEX }
        Shaders.ROUNDED_RECT_TEX.getUniform("Resolution")!!.set(floatArrayOf(res!!.width.toFloat(), res.height.toFloat()))
        Shaders.ROUNDED_RECT_TEX.getUniform("Center")!!.set(floatArrayOf(center.x, center.y))
        Shaders.ROUNDED_RECT_TEX.getUniform("Radius")!!.set(radius)
        Shaders.ROUNDED_RECT_TEX.getUniform("Thickness")!!.set(thickness)
        Shaders.ROUNDED_RECT_TEX.getUniform("SmoothEdge")!!.set(smoothedge)
        Shaders.ROUNDED_RECT_TEX.getUniform("Size")!!.set(floatArrayOf(size.x, size.y))
        val x1 = center.x - size.x / 2
        val x2 = center.x + size.x / 2
        val y1 = center.y - size.y / 2
        val y2 = center.y + size.y / 2
        val buff = Tesselator.getInstance().builder
        buff.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX)
        buff.vertex(matrix, x1, y1, 0f).color(color.x, color.y, color.z, color.w).uv(0f, 0f).endVertex()
        buff.vertex(matrix, x1, y2, 0f).color(color.x, color.y, color.z, color.w).uv(0f, 1f).endVertex()
        buff.vertex(matrix, x2, y2, 0f).color(color.x, color.y, color.z, color.w).uv(1f, 1f).endVertex()
        buff.vertex(matrix, x2, y1, 0f).color(color.x, color.y, color.z, color.w).uv(1f, 0f).endVertex()
        RenderSystem.enableBlend()
        BufferUploader.drawWithShader(buff.end())
        RenderSystem.disableBlend()
    }
}