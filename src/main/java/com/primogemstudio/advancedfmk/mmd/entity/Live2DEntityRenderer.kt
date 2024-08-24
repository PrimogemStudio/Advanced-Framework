package com.primogemstudio.advancedfmk.mmd.entity

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation
import com.primogemstudio.advancedfmk.live2d.target
import com.primogemstudio.advancedfmk.live2d.renderType
import net.minecraft.client.Minecraft

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
            val matrix = poseStack.last().pose()
            buff.addVertex(matrix, 0f, 0f, 0f).setUv(0f, 0f)
            buff.addVertex(matrix, 1f, 0f, 0f).setUv(1f, 0f)
            buff.addVertex(matrix, 1f, 1f, 0f).setUv(1f, 1f)
            buff.addVertex(matrix, 0f, 1f, 0f).setUv(0f, 1f)
        }
    }
}