package com.primogemstudio.advancedfmk.mmd.renderer

import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import com.primogemstudio.advancedfmk.interfaces.BufferBuilderExt
import com.primogemstudio.advancedfmk.interfaces.SodiumBufferBuilderExt
import com.primogemstudio.advancedfmk.mmd.PMXModel
import me.jellysquid.mods.sodium.client.render.vertex.buffer.SodiumBufferBuilder
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import org.joml.Vector2i
import java.nio.ByteBuffer
import java.nio.ByteOrder

class EntityRenderWrapper(val model: PMXModel) {
    private val renderType: RenderType = CustomRenderType.saba(model.textureManager.id)

    companion object {
        private val constant_buffer: ByteBuffer = ByteBuffer.allocateDirect(128).order(ByteOrder.nativeOrder())
    }

    @Suppress("KotlinConstantConditions")
    fun render(
        entityYaw: Float, poseStack: PoseStack, buffer: MultiBufferSource, packedLight: Int
    ) {
        val vc = buffer.getBuffer(renderType)
        val buf = try {
            if (vc is SodiumBufferBuilder) vc.originalBufferBuilder
            else vc as BufferBuilder
        } catch (e: NoClassDefFoundError) {
            vc as BufferBuilder
        }
        buf as BufferBuilderExt
        poseStack.pushPose()
        poseStack.scale(0.1f, 0.1f, 0.1f)
        poseStack.mulPose(Axis.YN.rotationDegrees(entityYaw))
        with(poseStack.last()) {
            pose().get(0, constant_buffer)
            normal().get(64, constant_buffer)
            Vector2i(OverlayTexture.NO_OVERLAY, packedLight).get(100, constant_buffer)
            constant_buffer.putInt(108, buf.padding())
        }
        buf.vertices = model.vertexCount
        buf.resize()
        model.updateAnimation()
        model.render(buf.buffer, constant_buffer)
        if (buf.padding() != 0) {
            buf.vertices = 0
            vc as SodiumBufferBuilderExt
            vc.mark()
            for (i in 0 until model.vertexCount) vc.endVertex()
            vc.unmark()
        }
        buf.submit()
        poseStack.popPose()
    }
}