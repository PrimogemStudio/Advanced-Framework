package com.primogemstudio.advancedui.mmd.renderer

import net.minecraft.client.Minecraft
import org.lwjgl.opengl.GL46C
import java.io.FileInputStream


object ShaderProvider {
    private var isInited = false
    private var program = 0
    private val vertexPath: String =
        Minecraft.getInstance().gameDirectory.getAbsolutePath() + "/Shader/MMDShader.vsh"
    private val fragPath: String =
        Minecraft.getInstance().gameDirectory.getAbsolutePath() + "/Shader/MMDShader.fsh"

    fun Init() {
        if (!isInited) {
            try {
                val vertexShader = GL46C.glCreateShader(GL46C.GL_VERTEX_SHADER)
                FileInputStream(vertexPath).use { vertexSource ->
                    GL46C.glShaderSource(
                        vertexShader,
                        String(vertexSource.readAllBytes())
                    )
                }
                val fragShader = GL46C.glCreateShader(GL46C.GL_FRAGMENT_SHADER)
                FileInputStream(fragPath).use { fragSource ->
                    GL46C.glShaderSource(
                        fragShader,
                        String(fragSource.readAllBytes())
                    )
                }
                GL46C.glCompileShader(vertexShader)
                if (GL46C.glGetShaderi(vertexShader, GL46C.GL_COMPILE_STATUS) == GL46C.GL_FALSE) {
                    val log = GL46C.glGetShaderInfoLog(vertexShader, 8192).trim { it <= ' ' }
                    println("Failed to compile shader $log")
                    GL46C.glDeleteShader(vertexShader)
                }
                GL46C.glCompileShader(fragShader)
                if (GL46C.glGetShaderi(fragShader, GL46C.GL_COMPILE_STATUS) == GL46C.GL_FALSE) {
                    val log = GL46C.glGetShaderInfoLog(fragShader, 8192).trim { it <= ' ' }
                    println("Failed to compile shader $log")
                    GL46C.glDeleteShader(fragShader)
                }
                program = GL46C.glCreateProgram()
                GL46C.glAttachShader(program, vertexShader)
                GL46C.glAttachShader(program, fragShader)
                GL46C.glLinkProgram(program)
                if (GL46C.glGetProgrami(program, GL46C.GL_LINK_STATUS) == GL46C.GL_FALSE) {
                    val log = GL46C.glGetProgramInfoLog(program, 8192)
                    println("Failed to link shader program\n$log")
                    GL46C.glDeleteProgram(program)
                    program = 0
                }
                println("MMD Shader Initialize finished")
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
            isInited = true
        }
    }

    fun getProgram(): Int {
        if (program <= 0) throw Error("Call Shader before init")
        return program
    }
}