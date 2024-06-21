package com.primogemstudio.advancedfmk.client

import net.fabricmc.api.ClientModInitializer
import org.lwjgl.util.harfbuzz.HarfBuzz.*

class AdvancedFrameworkFontEngineClient : ClientModInitializer {
    override fun onInitializeClient() {
    }
}

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val pth = "/home/coder2/Downloads/e22538b031e7794b26df4517a6b0c479.ttf"

    val blob = hb_blob_create_from_file_or_fail(pth)
    val blength = hb_blob_get_length(blob)
    println("Font length: $blength")

    val face = hb_face_create(blob, 0)
    hb_blob_destroy(blob)

    val upem = hb_face_get_upem(face)
    val font = hb_font_create(face)
    hb_font_set_scale(font, upem, upem)

    val buffer = hb_buffer_create()
    hb_buffer_add_utf8(buffer, "إنه اختبار", 0, -1)
    hb_buffer_guess_segment_properties(buffer)

    hb_shape(font, buffer, null)
    val count = hb_buffer_get_length(buffer)
    val infos = hb_buffer_get_glyph_infos(buffer)

    for (i in 0 ..< count) {
        val info = infos?.get(i)
        println("cluster ${info?.cluster()}\t\t\tglyph 0x${info?.codepoint()?.toHexString()}")
    }
    hb_buffer_destroy(buffer)
    hb_font_destroy(font)
    hb_face_destroy(face)
}