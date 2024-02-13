package com.primogemstudio.advancedfmk.mmd.renderer

import com.google.common.collect.ImmutableMap
import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.platform.TextureUtil
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import com.mojang.blaze3d.vertex.VertexFormatElement
import com.primogemstudio.advancedfmk.AdvancedFramework.Companion.MOD_ID
import com.primogemstudio.advancedfmk.interfaces.AccessFromNative
import com.primogemstudio.advancedfmk.render.Shaders
import com.primogemstudio.mmdbase.abstraction.ITextureManager
import glm_.vec2.Vec2
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderType.CompositeState
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.util.Mth
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.ceil
import kotlin.math.sqrt

class MMDTextureAtlas(tes: List<NativeImage>) : AbstractTexture() {
    private var texture: NativeImage
    private val map = HashMap<Int, Range>()

    @JvmRecord
    data class Range(
        val left: Int = 0, val top: Int = 0, val right: Int = 0, val bottom: Int = 0
    )

    private class Area(private val size: Int) {
        class Rect(val img: NativeImage) {
            var x = 0
            var y = 0
            var width = 0
            var height = 0

            fun containsX(other: Int): Boolean {
                return other >= x && other <= x + width
            }

            fun containsY(other: Int): Boolean {
                return other >= y && other <= y + height
            }
        }

        private val rects = arrayListOf<Rect>()

        private fun valid(x: Int, y: Int, img: NativeImage): Boolean {
            return x <= size && y <= size && x + img.width <= size && y + img.height <= size
        }

        fun join(img: NativeImage): Boolean {
            var x = 0
            var y = 0
            for (r in rects) {
                if (r.height == size && r.containsX(x)) {
                    x = r.x + r.width
                }
                if (r.width == size && r.containsY(y)) {
                    y = r.y + r.height
                }
                if (r.containsX(x) && r.containsY(y)) {
                    x = r.x + r.width
                    y = r.y
                    if (valid(x, y, img)) continue
                    x = r.x
                    y = r.y + r.height
                    if (!valid(x, y, img)) return false
                }
            }
            if (valid(x, y, img)) {
                val r1 = Rect(img)
                r1.x = x
                r1.y = y
                r1.width = img.width
                r1.height = img.height
                rects.add(r1)
                return true
            }
            return false
        }

        fun load(img: NativeImage, i: Int, j: Int, tes: List<NativeImage>, map: HashMap<Int, Range>) {
            val x = i * size
            val y = j * size
            rects.forEach {
                map[tes.indexOf(it.img)] = Range(x + it.x, y + it.y, x + it.x + it.width, y + it.y + it.height)
                it.img.copyRect(img, 0, 0, x + it.x, y + it.y, it.width, it.height, false, false)
            }
        }
    }

    init {
        var a = 0
        tes.forEach {
            if (it.width > a) a = it.width
            if (it.height > a) a = it.height
        }

        a = Integer.highestOneBit((a - 1) shl 1)
        val tmp = tes.sortedBy { -it.width * it.height }
        val ars = arrayListOf(Area(a))
        tmp.forEach {
            val la = ars.last()
            if (!la.join(it)) {
                ars.add(Area(a))
                ars.last().join(it)
            }
        }
        val wh = NativeImage(4, 4, false)
        wh.fillRect(0, 0, 4, 4, 0xffffffff.toInt())
        var flag = false
        for (it in ars) {
            if (it.join(wh)) {
                flag = true
                break
            }
        }
        if (!flag) {
            ars.add(Area(a))
            ars.last().join(wh)
        }
        val b = ceil(sqrt(ars.size.toDouble())).toInt()
        texture = NativeImage(a * b, a * b, false)
        l@ for (i in 0 until b) {
            for (j in 0 until b) {
                val k = i * b + j
                if (k >= ars.size) break@l
                val ar = ars[k]
                ar.load(texture, i, j, tes, map)
            }
        }
    }

    override fun load(resourceManager: ResourceManager) {
        if (!RenderSystem.isOnRenderThreadOrInit()) RenderSystem.recordRenderCall { upload(texture) }
        else upload(texture)
    }

    private fun upload(image: NativeImage) {
        TextureUtil.prepareImage(getId(), image.width, image.height)
        image.upload(0, 0, 0, true)
    }

    fun mapping(uv: Vec2, ti: Int) {
        val r = map[ti]!!
        uv.x = Mth.lerp(uv.x.toDouble(), r.left.toDouble(), r.right.toDouble()).toFloat() / texture.width
        uv.y = Mth.lerp(uv.y.toDouble(), r.top.toDouble(), r.bottom.toDouble()).toFloat() / texture.height
    }

    @AccessFromNative
    private val buff = ByteBuffer.allocateDirect(8).order(ByteOrder.LITTLE_ENDIAN)

    @AccessFromNative
    fun mapping(ti: Int) {
        val r = map[ti]!!
        val x = buff.getFloat(0).toDouble()
        val y = buff.getFloat(4).toDouble()
        buff.putFloat(0, Mth.lerp(x, r.left.toDouble(), r.right.toDouble()).toFloat() / texture.width)
        buff.putFloat(4, Mth.lerp(y, r.bottom.toDouble(), r.top.toDouble()).toFloat() / texture.height)
    }
}

class TextureManager(private val texture: MMDTextureAtlas) : ITextureManager {
    var id = ResourceLocation(MOD_ID, "")

    override fun register(prefix: String) {
        id = ResourceLocation(MOD_ID, prefix)
        Minecraft.getInstance().textureManager.register(id, texture)
    }

    override fun release() {
        Minecraft.getInstance().textureManager.release(id)
    }
}

object CustomRenderType {
    val ENTITY = VertexFormat(
        ImmutableMap.builder<String, VertexFormatElement>().put("Position", DefaultVertexFormat.ELEMENT_POSITION)
            .put("UV0", DefaultVertexFormat.ELEMENT_UV0).put("UV2", DefaultVertexFormat.ELEMENT_UV2).build()
    )
    val SHADER = ShaderStateShard { Shaders.MMD_SHADER }
    fun mmd(id: ResourceLocation, enable_direct: Boolean = false): RenderType {
        return RenderType.create(
            "mmd_dbg_$id",
            if (enable_direct) ENTITY else DefaultVertexFormat.NEW_ENTITY,
            VertexFormat.Mode.TRIANGLES,
            0x200000,
            false,
            true,
            CompositeState.builder()
                .setShaderState(if (enable_direct) SHADER else RenderStateShard.RENDERTYPE_ENTITY_CUTOUT_SHADER)
                .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                .setTextureState(RenderStateShard.TextureStateShard(id, false, false))
                .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                .setLightmapState(RenderStateShard.LIGHTMAP).createCompositeState(true)
        )
    }

    fun saba(id: ResourceLocation): RenderType {
        return RenderType.create(
            "mmd_dbg_saba",
            DefaultVertexFormat.POSITION_COLOR_TEX,
            VertexFormat.Mode.TRIANGLES,
            0x200000,
            false,
            true,
            CompositeState.builder().setShaderState(RenderStateShard.POSITION_COLOR_TEX_SHADER)
                .setTextureState(RenderStateShard.TextureStateShard(id, false, false)).createCompositeState(true)
        )
    }
}