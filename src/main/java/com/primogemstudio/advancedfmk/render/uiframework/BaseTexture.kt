package com.primogemstudio.advancedfmk.render.uiframework

import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.platform.TextureUtil
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.server.packs.resources.ResourceManager

class BaseTexture(val img: NativeImage): AbstractTexture() {
    override fun load(resourceManager: ResourceManager) {
        if (!RenderSystem.isOnRenderThreadOrInit()) RenderSystem.recordRenderCall { upload(img) }
        else upload(img)
    }

    private fun upload(image: NativeImage) {
        TextureUtil.prepareImage(getId(), image.width, image.height)
        image.upload(0, 0, 0, true)
    }
}