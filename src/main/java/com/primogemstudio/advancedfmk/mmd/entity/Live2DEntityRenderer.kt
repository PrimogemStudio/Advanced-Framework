package com.primogemstudio.advancedfmk.mmd.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import com.primogemstudio.advancedfmk.live2d.renderType
import com.primogemstudio.advancedfmk.live2d.target
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation

class Live2DEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<Live2DEntity>(context) {
    override fun getTextureLocation(entity: Live2DEntity): ResourceLocation {
        return ResourceLocation.withDefaultNamespace("")
    }

    override fun render(
        entity: Live2DEntity,
        entityYaw: Float,
        partialTick: Float,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int
    ) {
        if (entity.model == null) return
        else {
            target.clear(false)
            target.bindWrite(true)
            entity.model!!.update(1920, 1080)
            Minecraft.getInstance().mainRenderTarget.bindWrite(true)

            val buff = buffer.getBuffer(renderType)
            poseStack.pushPose()
            poseStack.mulPose(Axis.YN.rotationDegrees(entityYaw))
            val matrix = poseStack.last().pose()
            buff.addVertex(matrix, -1f, 0f, 0f)
                .setColor(1f, 1f, 1f, 1f)
                .setUv(0f, 0f)
                .setLight(packedLight)
            buff.addVertex(matrix, 0.920f, 0f, 0f)
                .setColor(1f, 1f, 1f, 1f)
                .setUv(1f, 0f)
                .setLight(packedLight)
            buff.addVertex(matrix, 0.920f, 1.080f, 0f)
                .setColor(1f, 1f, 1f, 1f)
                .setUv(1f, 1f)
                .setLight(packedLight)
            buff.addVertex(matrix, -1f, 1.080f, 0f)
                .setColor(1f, 1f, 1f, 1f)
                .setUv(0f, 1f)
                .setLight(packedLight)
            poseStack.popPose()
        }
    }
}