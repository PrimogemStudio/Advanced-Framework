package com.primogemstudio.advancedui.mmd.renderer

import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.platform.TextureUtil
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import com.primogemstudio.advancedui.AdvancedUI.MOD_ID
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderType.CompositeState
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.util.Tuple
import java.io.File

class MMDTexture(private val file: File?) : AbstractTexture() {
    override fun load(resourceManager: ResourceManager) {
        val img = file?.let { NativeImage.read(it.inputStream()) } ?: NativeImage(1, 1, true)
        if (!RenderSystem.isOnRenderThreadOrInit()) RenderSystem.recordRenderCall { upload(img) }
        else upload(img)
    }

    private fun upload(image: NativeImage) {
        TextureUtil.prepareImage(getId(), image.width, image.height)
        image.upload(0, 0, 0, true)
    }
}

class TextureManager {
    val ranges = HashMap<Int, Tuple<Int, Int>>()
    val textures = HashMap<Int, MMDTexture>()
    val ids = HashMap<Int, ResourceLocation>()

    fun register(prefix: String) {
        textures.forEach {
            val id = ResourceLocation(MOD_ID, "${prefix}_${it.key}")
            ids[it.key] = id
            Minecraft.getInstance().textureManager.register(id, it.value)
        }
    }

    fun release() {
        ids.forEach { Minecraft.getInstance().textureManager.release(it.value) }
        ids.clear()
    }
}

object CustomRenderType {
    private val cache = mutableMapOf<ResourceLocation, RenderType>()
    fun mmd(id: ResourceLocation): RenderType {
        if (!cache.containsKey(id)) {
            cache[id] = RenderType.create(
                "mmd_dbg_$id",
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.TRIANGLES,
                0x200000,
                false,
                true,
                CompositeState.builder().setShaderState(RenderStateShard.POSITION_TEX_SHADER)
                    .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                    .setTextureState(RenderStateShard.TextureStateShard(id, false, false))
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY).createCompositeState(false)
            )
        }
        return cache[id]?: RenderType.cutout()
    }
}