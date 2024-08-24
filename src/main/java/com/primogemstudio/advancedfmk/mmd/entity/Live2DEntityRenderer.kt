package com.primogemstudio.advancedfmk.mmd.entity

import com.mojang.blaze3d.pipeline.TextureTarget
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation

class Live2DEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<Live2DEntity>(context) {
    val target = TextureTarget(1920, 1080, false, false)
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
            target.bindWrite(true)
            entity.model!!.update(1920, 1080)
            Minecraft.getInstance().mainRenderTarget.bindWrite(true)
        }
    }
}