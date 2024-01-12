package com.primogemstudio.advancedfmk.mmd.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import com.primogemstudio.advancedfmk.AdvancedFramework.Companion.MOD_ID
import com.primogemstudio.advancedfmk.mmd.Loader
import com.primogemstudio.advancedfmk.mmd.renderer.CustomRenderType
import com.primogemstudio.mmdbase.io.PMXFile
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation
import org.joml.Matrix4f

class TestEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<TestEntity>(context) {
    companion object {
        private val model = Loader.load().second
        private val renderType = CustomRenderType.mmd(ResourceLocation(MOD_ID, "mmd_lumine"))
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
        model.m_faces.forEach { f ->
            f.m_vertices.forEach { buf.pmxVertex(poseStack.last().pose(), model, it).endVertex() }
        }
        poseStack.popPose()
    }
}

@Suppress("NOTHING_TO_INLINE")
private inline fun VertexConsumer.pmxVertex(mat: Matrix4f, m: PMXFile, i: Int): VertexConsumer {
    val v = m.m_vertices[i].m_position
    val uv = m.m_vertices[i].m_uv
    return this.vertex(mat, v.x, v.y, v.z).uv(uv.x, uv.y)
}