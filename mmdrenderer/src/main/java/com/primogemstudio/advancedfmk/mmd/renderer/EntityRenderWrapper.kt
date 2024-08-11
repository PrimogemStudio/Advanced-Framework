package com.primogemstudio.advancedfmk.mmd.renderer

import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import com.primogemstudio.advancedfmk.interfaces.BufferBuilderExt
import com.primogemstudio.advancedfmk.mmd.PMXModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import org.joml.Vector2i
import java.nio.ByteBuffer
import java.nio.ByteOrder

class EntityRenderWrapper(val model: PMXModel) {
    val renderType: RenderType = CustomRenderType.saba(model.textureManager.id)

    companion object {
        private val constant_buffer: ByteBuffer = ByteBuffer.allocateDirect(128).order(ByteOrder.nativeOrder())
    }

    fun render(
        entityYaw: Float, poseStack: PoseStack, buffer: MultiBufferSource, packedLight: Int
    ) {
        val vc = buffer.getBuffer(renderType)
        val buf = vc as BufferBuilder
        buf as BufferBuilderExt
        buf.setPMXModel(model)
        poseStack.pushPose()
        poseStack.scale(0.1f, 0.1f, 0.1f)
        poseStack.mulPose(Axis.YN.rotationDegrees(entityYaw))
        with(poseStack.last()) {
            pose().get(0, constant_buffer)
            normal().get(64, constant_buffer)
            Vector2i(OverlayTexture.NO_OVERLAY, packedLight).get(100, constant_buffer)
            constant_buffer.putInt(108, buf.padding())
        }
        model.updateAnimation()
        model.render(buf.resize(model.vertexCount), constant_buffer)
        poseStack.popPose()
    }

    fun render(
        entityYaw: Float, poseStack: PoseStack, buf: BufferBuilder, packedLight: Int
    ) {
        buf as BufferBuilderExt
        buf.setPMXModel(model)
        poseStack.pushPose()
        poseStack.scale(0.1f, 0.1f, 0.1f)
        poseStack.mulPose(Axis.YN.rotationDegrees(entityYaw))
        with(poseStack.last()) {
            pose().get(0, constant_buffer)
            normal().get(64, constant_buffer)
            Vector2i(OverlayTexture.NO_OVERLAY, packedLight).get(100, constant_buffer)
            constant_buffer.putInt(108, buf.padding())
        }
        model.updateAnimation()
        model.render(buf.resize(model.vertexCount), constant_buffer)
        poseStack.popPose()
    }
}