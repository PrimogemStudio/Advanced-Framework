package com.primogemstudio.advancedfmk.live2d

import com.mojang.blaze3d.pipeline.TextureTarget
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderType.CompositeState

val target = TextureTarget(1920, 1080, false, false)
class CustomTextureStateShard: RenderStateShard.EmptyTextureStateShard(Runnable {
    RenderSystem.setShaderTexture(0, target.colorTextureId)
    RenderSystem.disableCull()
}, Runnable {
    RenderSystem.enableCull()
})
val renderType = RenderType.create(
    "live2d_deferred",
    DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
    VertexFormat.Mode.QUADS,
    0x200,
    false,
    false,
    CompositeState.builder().setShaderState(RenderStateShard.POSITION_COLOR_TEX_LIGHTMAP_SHADER)
        .setTextureState(CustomTextureStateShard())
        .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
        .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
        .createCompositeState(true)
)