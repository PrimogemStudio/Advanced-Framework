package com.primogemstudio.advancedfmk.mmd.entity

import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.BufferVertexConsumer.normalIntValue
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import com.primogemstudio.advancedfmk.interfaces.BufferBuilderExt
import com.primogemstudio.advancedfmk.interfaces.SodiumBufferBuilderExt
import com.primogemstudio.advancedfmk.mmd.renderer.CustomRenderType
import glm_.vec3.Vec3
import me.jellysquid.mods.sodium.client.render.vertex.buffer.SodiumBufferBuilder
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import org.joml.*
import java.nio.ByteBuffer
import java.nio.ByteOrder

class TestEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<TestEntity>(context) {
    companion object {
        val constant_buffer: ByteBuffer = ByteBuffer.allocateDirect(128).order(ByteOrder.nativeOrder())
    }

    override fun getTextureLocation(entity: TestEntity): ResourceLocation {
        return ResourceLocation("")
    }

    @Suppress("KotlinConstantConditions")
    override fun render(
        entity: TestEntity,
        entityYaw: Float,
        partialTick: Float,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int
    ) {
        if (entity.model == null) return
        val vc = buffer.getBuffer(CustomRenderType.saba(entity.model!!.textureManager.id))
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
        buf.vertices = entity.model!!.vertexCount
        buf.resize()
        entity.model!!.updateAnimation()
        entity.model!!.render(buf.buffer, constant_buffer)
        if (buf.padding() != 0) {
            buf.vertices = 0
            vc as SodiumBufferBuilderExt
            vc.mark()
            for (i in 0 until entity.model!!.vertexCount) vc.endVertex()
            vc.unmark()
        }
        buf.submit()
        poseStack.popPose()
    }
}