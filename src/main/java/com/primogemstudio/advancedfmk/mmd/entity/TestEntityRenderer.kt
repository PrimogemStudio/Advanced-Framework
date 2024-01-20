package com.primogemstudio.advancedfmk.mmd.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import com.primogemstudio.advancedfmk.AdvancedFramework.Companion.MOD_ID
import com.primogemstudio.advancedfmk.mmd.Loader
import com.primogemstudio.advancedfmk.mmd.renderer.CustomRenderType
import com.primogemstudio.mmdbase.io.PMXFile
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation

class TestEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<TestEntity>(context) {
    companion object {
        private var enable_pipeline = true
        private var model = Loader.load("E:\\mmd\\furina", "furina.pmx").second
        private var renderType = CustomRenderType.mmd(ResourceLocation(MOD_ID, "mmd_lumine"), enable_pipeline)
        private var processed: Array<FloatArray>? = null

        fun switchModel(m: PMXFile) {
            model = m
            processed = null
        }

        fun switchPipeline(vanilla: Boolean) {
            enable_pipeline = !vanilla
            renderType = CustomRenderType.mmd(ResourceLocation(MOD_ID, "mmd_lumine"), enable_pipeline)
        }

        private fun rebuildBuffer() {
            processed = Array(model.m_faces.size * 3) { floatArrayOf() }
            for (i in model.m_faces.indices) {
                for (it in model.m_faces[i].m_vertices.indices) {
                    val vtx = model.m_vertices[model.m_faces[i].m_vertices[it]]
                    val v = vtx.m_position
                    val uv = vtx.m_uv
                    processed!![i * 3 + it] = floatArrayOf(v.x, v.y, v.z, uv.x, uv.y, v.x / 16, v.y / 16, v.z / 16)
                }
            }
        }
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
        poseStack.mulPose(Axis.YN.rotationDegrees(entityYaw))
        val pstk = poseStack.last().pose()
        val nom = poseStack.last().normal()
        val clr = 0xFFFFFFFF.toInt()
        if (processed == null) rebuildBuffer()
        if (!enable_pipeline) for (i in processed!!.indices) {
            val r = processed!![i]
            buf.vertex(pstk, r[0], r[1], r[2])
                .color(clr)
                .uv(r[3], r[4])
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(nom, r[5], r[6], r[7])
                .endVertex()
        }
        else for (i in processed!!.indices) {
            val r = processed!![i]
            buf.vertex(pstk, r[0], r[1], r[2])
                .uv(r[3], r[4])
                .uv2(packedLight)
                .endVertex()
        }
        poseStack.popPose()
    }
}