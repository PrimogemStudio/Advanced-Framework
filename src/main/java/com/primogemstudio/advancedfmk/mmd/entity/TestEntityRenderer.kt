package com.primogemstudio.advancedfmk.mmd.entity

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation

class TestEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<TestEntity>(context) {
    override fun getTextureLocation(entity: TestEntity): ResourceLocation {
        return ResourceLocation.withDefaultNamespace("")
    }

    override fun render(
        entity: TestEntity,
        entityYaw: Float,
        partialTick: Float,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int
    ) {
        if (entity.wrap == null) return
        entity.wrap!!.render(entityYaw, poseStack, buffer, packedLight)
    }
}