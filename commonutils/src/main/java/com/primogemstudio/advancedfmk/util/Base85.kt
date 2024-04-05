package com.primogemstudio.advancedfmk.util

import java.math.BigDecimal
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.regex.Pattern

object Base85 {
    private const val ASCII_SHIFT = 33

    private val BASE85_POW = intArrayOf(1, 85, 85 * 85, 85 * 85 * 85, 85 * 85 * 85 * 85)

    private val REMOVE_WHITESPACE: Pattern = Pattern.compile("\\s+")

    fun encode(payload: ByteArray): String {
        val buff = Compressor.encode(payload)
        val stringBuff = StringBuilder(buff.size * 5 / 4)
        val chunk = ByteArray(4)
        var chunkIndex = 0
        for (currByte in buff) {
            chunk[chunkIndex++] = currByte

            if (chunkIndex == 4) {
                val value = byteToInt(chunk)
                if (value == 0) {
                    stringBuff.append('z')
                } else {
                    stringBuff.append(encodeChunk(value))
                }
                Arrays.fill(chunk, 0.toByte())
                chunkIndex = 0
            }
        }

        if (chunkIndex > 0) {
            val numPadded = chunk.size - chunkIndex
            Arrays.fill(chunk, chunkIndex, chunk.size, 0.toByte())
            val value = byteToInt(chunk)
            val encodedChunk = encodeChunk(value)
            for (i in 0 until encodedChunk.size - numPadded) {
                stringBuff.append(encodedChunk[i])
            }
        }

        return stringBuff.toString()
    }

    private fun encodeChunk(value: Int): CharArray {
        var longValue = value.toLong() and 0x00000000ffffffffL
        val encodedChunk = CharArray(5)
        for (i in encodedChunk.indices) {
            encodedChunk[i] = Char(((longValue / BASE85_POW[4 - i]) + ASCII_SHIFT).toUShort())
            longValue %= BASE85_POW[4 - i]
        }
        return encodedChunk
    }

    fun decode(str: String): ByteArray {
        val inputLength = str.length
        val zCount = str.chars().filter { c: Int -> c == 'z'.code }.count()
        val uncompressedZLength = BigDecimal.valueOf(zCount).multiply(BigDecimal.valueOf(4))
        val uncompressedNonZLength = BigDecimal.valueOf(inputLength - zCount).multiply(BigDecimal.valueOf(4)).divide(
            BigDecimal.valueOf(5)
        )
        val uncompressedLength = uncompressedZLength.add(uncompressedNonZLength)
        val buff = ByteBuffer.allocate(uncompressedLength.toInt())
        val ns = REMOVE_WHITESPACE.matcher(str).replaceAll("")
        val payload = ns.toByteArray(StandardCharsets.US_ASCII)
        val chunk = ByteArray(5)
        var chunkIndex = 0
        for (currByte in payload) {
            if (currByte == 'z'.code.toByte()) {
                require(chunkIndex <= 0) { "The payload is not base 85 encoded." }
                chunk[chunkIndex++] = '!'.code.toByte()
                chunk[chunkIndex++] = '!'.code.toByte()
                chunk[chunkIndex++] = '!'.code.toByte()
                chunk[chunkIndex++] = '!'.code.toByte()
                chunk[chunkIndex++] = '!'.code.toByte()
            } else {
                chunk[chunkIndex++] = currByte
            }
            if (chunkIndex == 5) {
                buff.put(decodeChunk(chunk))
                Arrays.fill(chunk, 0.toByte())
                chunkIndex = 0
            }
        }

        if (chunkIndex > 0) {
            val numPadded = chunk.size - chunkIndex
            Arrays.fill(chunk, chunkIndex, chunk.size, 'u'.code.toByte())
            val paddedDecode = decodeChunk(chunk)
            for (i in 0 until paddedDecode.size - numPadded) {
                buff.put(paddedDecode[i])
            }
        }

        buff.flip()
        return Compressor.decode(buff)
    }

    private fun decodeChunk(chunk: ByteArray): ByteArray {
        require(chunk.size == 5) { "You can only decode chunks of size 5." }
        var value = 0
        value += (chunk[0] - ASCII_SHIFT) * BASE85_POW[4]
        value += (chunk[1] - ASCII_SHIFT) * BASE85_POW[3]
        value += (chunk[2] - ASCII_SHIFT) * BASE85_POW[2]
        value += (chunk[3] - ASCII_SHIFT) * BASE85_POW[1]
        value += (chunk[4] - ASCII_SHIFT) * BASE85_POW[0]
        return intToByte(value)
    }

    private fun byteToInt(value: ByteArray): Int {
        require(value.size == 4) { "You cannot create an int without exactly 4 bytes." }
        return ByteBuffer.wrap(value).getInt()
    }

    private fun intToByte(value: Int): ByteArray {
        return byteArrayOf(
            (value ushr 24).toByte(), (value ushr 16).toByte(), (value ushr 8).toByte(), value.toByte()
        )
    }
}