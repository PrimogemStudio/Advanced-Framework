package com.primogemstudio.advancedui.mmd.io

import com.primogemstudio.advancedui.mmd.io.PMXVertexWeight.*
import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f
import java.io.DataInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.StandardCharsets

class ModelDataInputStream(flow: InputStream) : DataInputStream(flow) {
    fun readLEInt(): Int {
        return ByteBuffer.wrap(readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt()
    }

    fun readLEFloat(): Float {
        return ByteBuffer.wrap(readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat()
    }

    fun readLEShort(): Short {
        return ByteBuffer.wrap(readNBytes(2)).order(ByteOrder.LITTLE_ENDIAN).getShort()
    }

    fun readText(isUtf16: Boolean): String {
        val length = readLEInt()
        return String(readNBytes(length), if (isUtf16) StandardCharsets.UTF_16LE else StandardCharsets.UTF_8)
    }

    private fun readVec2(input: Vector2f) {
        val buf = ByteBuffer.wrap(readNBytes(8)).order(ByteOrder.LITTLE_ENDIAN)
        input.set(buf.getFloat(), buf.getFloat())
    }

    private fun readVec3(input: Vector3f) {
        val buf = ByteBuffer.wrap(readNBytes(12)).order(ByteOrder.LITTLE_ENDIAN)
        input.set(buf.getFloat(), buf.getFloat(), buf.getFloat())
    }

    private fun readVec4(input: Vector4f) {
        val buf = ByteBuffer.wrap(readNBytes(16)).order(ByteOrder.LITTLE_ENDIAN)
        input.set(buf.getFloat(), buf.getFloat(), buf.getFloat(), buf.getFloat())
    }

    private fun readHeader(header: PMXHeader) {
        header.m_magic = String(readNBytes(4))
        header.m_version = readLEFloat()
        header.m_dataSize = readByte()
        header.m_encode = readByte()
        header.m_addUVNum = readByte()
        header.m_vertexIndexSize = readByte()
        header.m_textureIndexSize = readByte()
        header.m_materialIndexSize = readByte()
        header.m_boneIndexSize = readByte()
        header.m_morphIndexSize = readByte()
        header.m_rigidbodyIndexSize = readByte()
    }

    private fun readInfo(info: PMXInfo, isU16: Boolean) {
        info.m_modelName = readText(isU16)
        info.m_englishModelName = readText(isU16)
        info.m_comment = readText(isU16)
        info.m_englishComment = readText(isU16)
    }

    private fun readIndex(index: Array<Int>, offset: Int, size: Int) {
        when (size) {
            1 -> {
                val idx = readByte()
                if (idx != 0xFF.toByte()) {
                    index[offset] = idx.toInt()
                } else {
                    index[offset] = -1
                }
            }

            2 -> {
                val idx = readLEShort()
                if (idx != 0xFFFF.toShort()) {
                    index[offset] = idx.toInt()
                } else {
                    index[offset] = -1
                }
            }

            4 -> index[offset] = readLEInt()
        }
    }

    private fun readVertices(vertices: Array<PMXVertex>, header: PMXHeader) {
        for (vertex in vertices) {
            readVec3(vertex.m_position)
            readVec3(vertex.m_normal)
            readVec2(vertex.m_uv)
            for (i in 0 until header.m_addUVNum) {
                readVec4(vertex.m_addUV[i])
            }
            vertex.m_weightType = PMXVertexWeight.entries[readByte().toInt()]
            when (vertex.m_weightType) {
                BDEF1 -> readIndex(vertex.m_boneIndices, 0, header.m_boneIndexSize.toInt())
                BDEF2 -> {
                    readIndex(vertex.m_boneIndices, 0, header.m_boneIndexSize.toInt())
                    readIndex(vertex.m_boneIndices, 1, header.m_boneIndexSize.toInt())
                    vertex.m_boneWeights.x = readLEFloat()
                }

                BDEF4, QDEF -> {
                    readIndex(vertex.m_boneIndices, 0, header.m_boneIndexSize.toInt())
                    readIndex(vertex.m_boneIndices, 1, header.m_boneIndexSize.toInt())
                    readIndex(vertex.m_boneIndices, 2, header.m_boneIndexSize.toInt())
                    readIndex(vertex.m_boneIndices, 3, header.m_boneIndexSize.toInt())
                    readVec4(vertex.m_boneWeights)
                }

                SDEF -> {
                    readIndex(vertex.m_boneIndices, 0, header.m_boneIndexSize.toInt())
                    readIndex(vertex.m_boneIndices, 1, header.m_boneIndexSize.toInt())
                    vertex.m_boneWeights.x = readLEFloat()
                    readVec3(vertex.m_sdefC)
                    readVec3(vertex.m_sdefR0)
                    readVec3(vertex.m_sdefR1)
                }
            }
            vertex.m_edgeMag = readLEFloat()
        }
    }

    private fun readFaces(vertices: Array<PMXVertex>, header: PMXHeader) {
        println(readLEInt())
    }

    fun readPMXFile(): PMXFile {
        val file = PMXFile()
        readHeader(file.m_header)
        readInfo(file.m_info, file.m_header.m_encode == 0.toByte())
        file.m_vertices = Array(readLEInt()) { PMXVertex() }
        readVertices(file.m_vertices, file.m_header)
        readFaces(file.m_vertices, file.m_header)
        return file
    }
}