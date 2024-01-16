package com.primogemstudio.advancedfmk.mmd.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import com.primogemstudio.advancedfmk.AdvancedFramework.Companion.MOD_ID
import com.primogemstudio.advancedfmk.mmd.Loader
import com.primogemstudio.advancedfmk.mmd.renderer.CustomRenderType
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation

class TestEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<TestEntity>(context) {
    companion object {
        private const val enable_pipeline = false
        private val model = Loader.load().second
        private val renderType = CustomRenderType.mmd(ResourceLocation(MOD_ID, "mmd_lumine"), enable_pipeline)
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
        val buf = buffer.getBuffer(renderType)
        poseStack.pushPose()
        poseStack.scale(0.1f, 0.1f, 0.1f)
        poseStack.mulPose(Axis.YN.rotationDegrees(entity.tickCount % 360 * 2f))
        val pstk = poseStack.last().pose()
        val nom = poseStack.last().normal()
        model.m_faces.forEach { f ->
            f.m_vertices.forEach {
                val v = model.m_vertices[it].m_position
                val uv = model.m_vertices[it].m_uv
                buf.vertex(pstk, v.x, v.y, v.z)
                    .apply { if (!enable_pipeline) this.color(0xFFFFFFFF.toInt()) }
                    .uv(uv.x, uv.y)
                    .apply { if (!enable_pipeline) this.overlayCoords(OverlayTexture.NO_OVERLAY) }
                    .uv2(packedLight)
                    .apply { if (!enable_pipeline) this.normal(nom, v.x / 16, v.y / 16, v.z / 16) }
                    .endVertex()
            }
        }
        poseStack.popPose()
    }
}