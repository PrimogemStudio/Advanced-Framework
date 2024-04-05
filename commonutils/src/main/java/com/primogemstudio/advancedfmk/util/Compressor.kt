package com.primogemstudio.advancedfmk.util

import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.nio.ByteBuffer
import java.util.zip.Deflater
import java.util.zip.DeflaterOutputStream
import java.util.zip.Inflater

object Compressor {
    @Suppress("DuplicatedCode")
    fun encode(data: ByteArray): ByteArray {
        val bo = ByteArrayOutputStream()
        val deflater = Deflater(Deflater.BEST_COMPRESSION)
        deflater.setInput(data)
        deflater.finish()
        val buffer = ByteArray(1024)
        while (!deflater.finished()) {
            val len = deflater.deflate(buffer)
            bo.write(buffer, 0, len)
        }
        bo.close()
        return bo.toByteArray()
    }

    @Suppress("DuplicatedCode")
    fun encode(data: ByteBuffer): ByteArray {
        val bo = ByteArrayOutputStream()
        val deflater = Deflater(Deflater.BEST_COMPRESSION)
        deflater.setInput(data)
        deflater.finish()
        val buffer = ByteArray(1024)
        while (!deflater.finished()) {
            val len = deflater.deflate(buffer)
            bo.write(buffer, 0, len)
        }
        bo.close()
        return bo.toByteArray()
    }

    fun encode(output: OutputStream, level: Int): DeflaterOutputStream {
        return DeflaterOutputStream(output, Deflater(level))
    }

    @Suppress("DuplicatedCode")
    fun decode(data: ByteArray): ByteArray {
        val inflater = Inflater()
        inflater.setInput(data)
        val bo = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        while (!inflater.finished()) {
            val len = inflater.inflate(buffer)
            bo.write(buffer, 0, len)
        }
        bo.close()
        return bo.toByteArray()
    }

    @Suppress("DuplicatedCode")
    fun decode(data: ByteBuffer): ByteArray {
        val inflater = Inflater()
        inflater.setInput(data)
        val bo = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        while (!inflater.finished()) {
            val len = inflater.inflate(buffer)
            bo.write(buffer, 0, len)
        }
        bo.close()
        return bo.toByteArray()
    }
}