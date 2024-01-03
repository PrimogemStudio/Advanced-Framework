package com.primogemstudio.advancedui.mmd.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.primogemstudio.advancedui.mmd.Loader
import com.primogemstudio.advancedui.mmd.io.PMXFile
import com.primogemstudio.advancedui.mmd.renderer.RendererConstants
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation
import org.joml.Matrix4f

class TestEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<TestEntity>(context) {
    companion object {
        private val model = Loader.load().second
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
        poseStack.pushPose()
        val buf = buffer.getBuffer(RendererConstants.MMD_DBG)
        val pose = poseStack.last()
        model.m_faces.forEach {
            it.m_vertices.forEach { i ->
                buf.mvertex(pose.pose(), model, i).color(1f, 1f, 1f, 1f).endVertex()
            }
        }
        /*buf.vertex(pose.pose(), 0f, 1f, 0f).color(1f, 0f, 0f, 0.5f).endVertex()
        buf.vertex(pose.pose(), 1f, -1f, 0f).color(0f, 1f, 0f, 0.5f).endVertex()
        buf.vertex(pose.pose(), -1f, -1f, 0f).color(0f, 0f, 1f, 0.5f).endVertex()*/
        poseStack.popPose()
    }
}

fun VertexConsumer.mvertex(mat: Matrix4f, m: PMXFile, i: Int): VertexConsumer {
    val v = m.m_vertices[i].m_position
    return this.vertex(mat, v.x, v.y, v.z)
}