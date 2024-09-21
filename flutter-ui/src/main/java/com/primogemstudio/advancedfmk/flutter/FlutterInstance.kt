package com.primogemstudio.advancedfmk.flutter

import com.mojang.blaze3d.pipeline.TextureTarget
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import com.primogemstudio.advancedfmk.flutter.Shaders.POST_BLUR
import net.minecraft.client.Minecraft
import java.lang.ref.Cleaner

class FlutterInstance(assets: String?, val rect: FlutterRect, var width: Int, var height: Int): AutoCloseable {
    companion object {
        val ITARGET = TextureTarget(1, 1, false, false).apply {
            setClearColor(0f, 0f, 0f, 0f)
            clear(false)
        }
    }
    private val cleaner: Cleaner.Cleanable
    val handle: Long = FlutterNative.createInstance(assets)
    var pressed: Boolean = false

    init {
        sendSizeEvent()
        FlutterEvents.register(this)
        val _handle = handle
        cleaner = FlutterNative.cleaner.register(this) {
            FlutterNative.destroyInstance(
                _handle
            )
        }
    }

    fun hitTest(x: Double, y: Double): Boolean {
        return x > rect.left && x < rect.right && y > rect.top && y < rect.bottom
    }

    fun sendSizeEvent() {
        FlutterNative.sendMetricsEvent(handle, rect.right - rect.left, rect.bottom - rect.top, 0)
    }

    fun resize(width: Int, height: Int) {
        this.width = width
        this.height = height
        FlutterViewEvent.resize(Minecraft.getInstance().window.width, Minecraft.getInstance().window.height)
    }

    fun pollEvents() {
        FlutterNative.pollEvents(handle)
    }

    val texture: Int
        get() = FlutterNative.getTexture(handle)

    fun renderToScreen() {
        ITARGET.clear(false)
        ITARGET.bindWrite(true)

        val x = rect.left
        val y = Minecraft.getInstance().window.height - rect.bottom
        val width = rect.right - rect.left
        val height = rect.bottom - rect.top
        val w = Minecraft.getInstance().window.width
        val h = Minecraft.getInstance().window.height
        GlStateManager._colorMask(true, true, true, true)
        GlStateManager._disableDepthTest()
        GlStateManager._depthMask(false)
        GlStateManager._viewport(x, y, width, height)
        GlStateManager._enableBlend()
        val shader = Shaders.BLIT_NO_FLIP
        shader.getUniform("PositionOffset")!![(x / w).toFloat()] = (y / h).toFloat()
        shader.setSampler("DiffuseSampler", texture)
        shader.apply()
        val buff = RenderSystem.renderThreadTesselator().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLIT_SCREEN)
        buff.addVertex(0.0f, 0.0f, 0.0f)
        buff.addVertex(1.0f, 0.0f, 0.0f)
        buff.addVertex(1.0f, 1.0f, 0.0f)
        buff.addVertex(0.0f, 1.0f, 0.0f)
        BufferUploader.draw(buff.buildOrThrow())
        shader.clear()
        GlStateManager._disableBlend()
        GlStateManager._depthMask(true)
        GlStateManager._colorMask(true, true, true, true)

        POST_BLUR.setSamplerUniform("InputSampler", ITARGET)
        POST_BLUR.render(0f)
        // -Dorg.lwjgl.glfw.libname=/usr/lib/libglfw.so
    }

    override fun close() {
        FlutterEvents.unregister(this)
        cleaner.clean()
    }
}