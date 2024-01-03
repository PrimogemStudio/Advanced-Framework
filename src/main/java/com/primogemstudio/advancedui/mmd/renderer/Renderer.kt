package com.primogemstudio.advancedui.mmd.renderer

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderType.CompositeState


object RendererConstants {
    val MMD_DBG: RenderType.CompositeRenderType = RenderType.create(
        "mmd_dbg",
        DefaultVertexFormat.POSITION_COLOR,
        VertexFormat.Mode.TRIANGLES,
        131072,
        false,
        true,
        CompositeState.builder().setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
            .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
            .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY).createCompositeState(false)
    )
}