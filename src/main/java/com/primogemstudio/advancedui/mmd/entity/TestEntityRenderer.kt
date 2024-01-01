package com.primogemstudio.advancedui.mmd.entity

import com.mojang.blaze3d.vertex.*
import com.mojang.math.Axis
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.block.BlockRenderDispatcher
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.TntMinecartRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Blocks

class TestEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<TestEntity>(context) {
    private val blockRenderer: BlockRenderDispatcher
    override fun getTextureLocation(entity: TestEntity): ResourceLocation {
        TODO("Not yet implemented")
    }

    init {
        blockRenderer = context.blockRenderDispatcher
    }

    override fun render(
        entity: TestEntity,
        entityYaw: Float,
        partialTick: Float,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int
    ) {
        poseStack.pushPose()
        poseStack.translate(0.0f, 0.5f, 0.0f)

        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0f))
        poseStack.translate(-0.5f, -0.5f, 0.5f)
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0f))
        this.blockRenderer.renderSingleBlock(
            Blocks.DIAMOND_ORE.defaultBlockState(),
            poseStack,
            buffer,
            packedLight,
            OverlayTexture.NO_OVERLAY
        )
        poseStack.popPose()
    }
}