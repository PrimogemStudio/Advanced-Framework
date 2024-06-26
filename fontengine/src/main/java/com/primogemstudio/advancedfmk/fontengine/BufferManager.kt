package com.primogemstudio.advancedfmk.fontengine

import com.mojang.blaze3d.pipeline.TextureTarget
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import com.primogemstudio.advancedfmk.fontengine.Shaders.TEXT_BLUR
import net.minecraft.Util
import net.minecraft.Util.OS
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.GameRenderer

object BufferManager {
    val fontInternal = TextureTarget(1, 1, true, Util.getPlatform() == OS.OSX).apply { setClearColor(1f, 1f, 1f, 0f) }
    var sizew = 0
    var sizeh = 0

    fun updateBufferColor(color: Int) {
        fontInternal.setClearColor(
            color.shr(16).and(0xff).toFloat() / 255f,
            color.shr(8).and(0xff).toFloat() / 255f,
            color.and(0xff).toFloat() / 255f,
            color.shr(24).and(0xff).toFloat() / 255f
        )
    }

    inline fun renderText(call: (VertexConsumer, PoseStack) -> Unit, graphics: GuiGraphics, partialTick: Float) {
        fontInternal.clear(Util.getPlatform() === OS.OSX)
        if (sizew != Minecraft.getInstance().window.width || sizeh != Minecraft.getInstance().window.height) {
            sizew = Minecraft.getInstance().window.width
            sizeh = Minecraft.getInstance().window.height
            fontInternal.resize(
                sizew * 4,
                sizeh * 4,
                Util.getPlatform() === OS.OSX
            )
        }
        fontInternal.bindWrite(true)
        RenderSystem.setShader { GameRenderer.getPositionColorShader() }
        val buff = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR)
        val scale = Minecraft.getInstance().window.guiScale.toFloat()
        val poseStack: PoseStack = graphics.pose()
        poseStack.pushPose()
        poseStack.scale(1 / scale, 1 / scale, 1f)
        poseStack.popPose()
        call(buff, poseStack)
        RenderSystem.enableBlend()
        RenderSystem.disableCull()
        BufferUploader.drawWithShader(buff.buildOrThrow())
        RenderSystem.enableCull()
        RenderSystem.disableBlend()
        TEXT_BLUR.setSamplerUniform("BaseLayer", fontInternal)
        TEXT_BLUR.render(partialTick)
    }
}