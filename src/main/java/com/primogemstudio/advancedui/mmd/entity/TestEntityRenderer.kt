package com.primogemstudio.advancedui.mmd.entity

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation

class TestEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<TestEntity>(context) {
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
        val buf = buffer.getBuffer(RenderType.debugFilledBox())
        val pose = poseStack.last()
        buf.vertex(pose.pose(), 0f, 1f, 0f).color(1f, 0f, 0f, 1f).endVertex()
        buf.vertex(pose.pose(), 1f, -1f, 0f).color(0f, 1f, 0f, 1f).endVertex()
        buf.vertex(pose.pose(), -1f, -1f, 0f).color(0f, 0f, 1f, 1f).endVertex()
    }
}