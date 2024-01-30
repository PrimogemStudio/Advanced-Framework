package com.primogemstudio.advancedfmk.mmd.entity

import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.BufferVertexConsumer.normalIntValue
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.joml.Matrix3f
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f

class TestEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<TestEntity>(context) {
    companion object {
        var enable_pipeline = true
        private val logger = LogManager.getLogger(Companion)
        fun switchPipeline(vanilla: Boolean) {
            enable_pipeline = !vanilla
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
        if (entity.model == null) return
        if (entity.enable_pipeline != enable_pipeline) entity.reinitRenderLayer()
        val buf = buffer.getBuffer(entity.renderType!!)
        poseStack.pushPose()
        poseStack.scale(0.1f, 0.1f, 0.1f)
        poseStack.mulPose(Axis.YN.rotationDegrees(entityYaw))
        val pstk = poseStack.last().pose()
        val nom = poseStack.last().normal()
        val processed = entity.getProcessed()
        if (!enable_pipeline) for (i in processed.indices) {
            val r = processed[i]
            buf.directCommit(pstk, nom, r[0], r[1], r[2], r[3], r[4], packedLight, r[5], r[6], r[7])
        }
        else for (i in processed.indices) {
            val r = processed[i]
            buf.directCommit(pstk, r[0], r[1], r[2], r[3], r[4], packedLight)
        }
        poseStack.popPose()
    }
}

fun VertexConsumer.directCommit(mat: Matrix4f, x: Float, y: Float, z: Float, u: Float, v: Float, light: Int) {
    with(this as BufferBuilder) {
        val p = mat.transform(Vector4f(x, y, z, 1.0f))
        putFloat(0, p.x)
        putFloat(4, p.y)
        putFloat(8, p.z)
        nextElement()
        putFloat(0, u)
        putFloat(4, v)
        nextElement()
        putShort(0, (light and 0xFFFF).toShort())
        putShort(2, (light shr 16 and 0xFFFF).toShort())
        nextElement()
        endVertex()
    }
}

fun VertexConsumer.directCommit(
    mat: Matrix4f,
    nom: Matrix3f,
    x: Float,
    y: Float,
    z: Float,
    u: Float,
    v: Float,
    light: Int,
    nx: Float,
    ny: Float,
    nz: Float
) {
    with(this as BufferBuilder) {
        val p = mat.transform(Vector4f(x, y, z, 1.0f))
        val n = nom.transform(Vector3f(nx, ny, nz))
        this.vertex(p.x, p.y, p.z, 1f, 1f, 1f, 1f, u, v, OverlayTexture.NO_OVERLAY, light, n.x, n.y, n.z)
        /*putFloat(0, p.x)
        putFloat(4, p.y)
        putFloat(8, p.z)
        nextElement()
        putByte(0, 0xFF.toByte())
        putByte(1, 0xFF.toByte())
        putByte(2, 0xFF.toByte())
        putByte(3, 0xFF.toByte())
        nextElement()
        putFloat(0, u)
        putFloat(4, v)
        nextElement()
        putShort(0, 0)
        putShort(0, 10)
        nextElement()
        putShort(0, (light and 0xFFFF).toShort())
        putShort(2, (light shr 16 and 0xFFFF).toShort())
        nextElement()
        putByte(0, normalIntValue(n.x))
        putByte(1, normalIntValue(n.y))
        putByte(2, normalIntValue(n.z))
        nextElement()
        endVertex()*/
    }
}