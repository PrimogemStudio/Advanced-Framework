package com.primogemstudio.advancedfmk.mmd

import com.mojang.blaze3d.platform.NativeImage
import com.primogemstudio.advancedfmk.mmd.renderer.MMDTextureAtlas
import com.primogemstudio.advancedfmk.mmd.renderer.TextureManager
import com.primogemstudio.mmdbase.io.ModelDataInputStream
import com.primogemstudio.mmdbase.io.PMXFile
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.nio.file.Path
import kotlin.io.path.pathString

object Loader {
    @JvmStatic
    @Throws(FileNotFoundException::class)
    fun load(root: String, filename: String, name: String): PMXFile {
        val model = ModelDataInputStream(FileInputStream(Path.of(root, filename).pathString))
        val pmx = model.readPMXFile()
        model.close()
        var sum = 0
        val tex = MMDTextureAtlas(pmx.m_textures.map { NativeImage.read(File(root, it).inputStream()) })
        pmx.textureManager = TextureManager(tex)
        val vertex_cache = Array(pmx.m_vertices.size) { false }
        pmx.m_materials.forEach {
            val tmp = it.m_numFaceVertices / 3
            for (i in sum until sum + tmp) {
                pmx.m_faces[i].m_vertices.forEach { vi ->
                    if (!vertex_cache[vi]) {
                        tex.mapping(pmx.m_vertices[vi].m_uv, it.m_textureIndex)
                        vertex_cache[vi] = true
                    }
                }
            }
            sum += tmp
        }
        pmx.textureManager!!.register("mmd_$name")
        return pmx
    }
}