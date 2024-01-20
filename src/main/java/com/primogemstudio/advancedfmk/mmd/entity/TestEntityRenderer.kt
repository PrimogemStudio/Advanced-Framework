package com.primogemstudio.advancedfmk.mmd.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation

class TestEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<TestEntity>(context) {
    companion object {
        var enable_pipeline = true

        fun switchPipeline(vanilla: Boolean) {
            enable_pipeline = !vanilla
        }
    }

    override fun getTextureLocation(entity: TestEntity): ResourceLocation {
        return ResourceLocation("")
    }

    override fun render(
        entity: TestEntity,
        entityYaw: Float,
        partialTick: Float,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int
    ) {
        if (entity.model == null) return
        val buf = buffer.getBuffer(entity.renderType!!)
        poseStack.pushPose()
        poseStack.scale(0.1f, 0.1f, 0.1f)
        poseStack.mulPose(Axis.YN.rotationDegrees(entityYaw))
        val pstk = poseStack.last().pose()
        val nom = poseStack.last().normal()
        val clr = 0xFFFFFFFF.toInt()
        val processed = entity.getProcessed()
        if (!enable_pipeline) for (i in processed.indices) {
            val r = processed[i]
            buf.vertex(pstk, r[0], r[1], r[2]).color(clr).uv(r[3], r[4]).overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight).normal(nom, r[5], r[6], r[7]).endVertex()
        }
        else for (i in processed.indices) {
            val r = processed[i]
            buf.vertex(pstk, r[0], r[1], r[2]).uv(r[3], r[4]).uv2(packedLight).endVertex()
        }
        poseStack.popPose()
    }
}