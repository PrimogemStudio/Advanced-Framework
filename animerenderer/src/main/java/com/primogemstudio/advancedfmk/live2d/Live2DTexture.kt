package com.primogemstudio.advancedfmk.live2d

import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.platform.TextureUtil
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.server.packs.resources.ResourceManager

class Live2DTexture(private val texture: NativeImage): AbstractTexture() {
    override fun load(resourceManager: ResourceManager) {
        if (!RenderSystem.isOnRenderThreadOrInit()) RenderSystem.recordRenderCall { upload(texture) }
        else upload(texture)
    }

    private fun upload(image: NativeImage) {
        TextureUtil.prepareImage(getId(), image.width, image.height)
        image.upload(0, 0, 0, true)
    }
}