package com.primogemstudio.advancedfmk.mmd.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.primogemstudio.advancedfmk.mmd.renderer.EntityRenderWrapper
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation

class TestEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<TestEntity>(context) {
    val wrap = EntityRenderWrapper(null)
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
        else if (wrap.model == null) {
            wrap.model = entity.model
            wrap.updateRenderType()
        }
        wrap.render(entityYaw, poseStack, buffer, packedLight)
    }
}