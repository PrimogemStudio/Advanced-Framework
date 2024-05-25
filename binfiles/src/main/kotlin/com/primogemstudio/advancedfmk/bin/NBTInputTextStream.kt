package com.primogemstudio.advancedfmk.bin

import java.io.DataInputStream
import java.io.InputStream
import java.io.PrintStream

class NBTInputTextStream(`in`: InputStream, private val out: PrintStream): DataInputStream(`in`) {
    private var availableEnds = 0

    @OptIn(ExperimentalStdlibApi::class)
    fun readByteTag(check: Boolean = true) {
        if (check && readByte() != 0x01.toByte()) throw IllegalStateException("Invalid byte tag")
        out.println("${"    ".repeat(availableEnds)}${readUTF()} -> 0x${readByte().toHexString()}")
    }
    fun readShortTag(check: Boolean = true) {
        if (check && readByte() != 0x02.toByte()) throw IllegalStateException("Invalid short tag")
        out.println("${"    ".repeat(availableEnds)}${readUTF()} -> ${readShort()}")
    }
    fun readIntTag(check: Boolean = true) {
        if (check && readByte() != 0x03.toByte()) throw IllegalStateException("Invalid int tag")
        out.println("${"    ".repeat(availableEnds)}${readUTF()} -> ${readInt()}")
    }
    fun readLongTag(check: Boolean = true) {
        if (check && readByte() != 0x04.toByte()) throw IllegalStateException("Invalid long tag")
        out.println("${"    ".repeat(availableEnds)}${readUTF()} -> ${readLong()}")
    }
    fun readFloatTag(check: Boolean = true) {
        if (check && readByte() != 0x05.toByte()) throw IllegalStateException("Invalid float tag")
        out.println("${"    ".repeat(availableEnds)}${readUTF()} -> ${readFloat()}")
    }
    fun readDoubleTag(check: Boolean = true) {
        if (check && readByte() != 0x06.toByte()) throw IllegalStateException("Invalid double tag")
        out.println("${"    ".repeat(availableEnds)}${readUTF()} -> ${readDouble()}")
    }
    @OptIn(ExperimentalStdlibApi::class)
    fun readByteArrayTag(check: Boolean = true) {
        if (check && readByte() != 0x07.toByte()) throw IllegalStateException("Invalid byte array tag")
        out.print("${"    ".repeat(availableEnds)}${readUTF()} -> byte[")
        val siz = readInt()
        for (i in 0 ..< siz) out.print("0x${readByte().toHexString()}, ")
        out.println("]")
    }
    fun readStringTag(check: Boolean = true) {
        if (check && readByte() != 0x08.toByte()) throw IllegalStateException("Invalid string tag")
        out.println("${"    ".repeat(availableEnds)}${readUTF()} -> ${readUTF()}")
    }
    @OptIn(ExperimentalStdlibApi::class)
    fun readListTag(check: Boolean = true, header: Boolean = true) {
        if (header) {
            if (check && readByte() != 0x09.toByte()) throw IllegalStateException("Invalid list tag")
            out.print("${"    ".repeat(availableEnds)}${readUTF()} -> ")
        }

        val tagtype = readByte().toInt()
        when (tagtype) {
            0x00 -> out.println("End[")
            0x01 -> out.println("Byte[")
            0x02 -> out.println("Short[")
            0x03 -> out.println("Int[")
            0x04 -> out.println("Long[")
            0x05 -> out.println("Float[")
            0x06 -> out.println("Double[")
            0x07 -> out.println("ByteArray[")
            0x08 -> out.println("String[")
            0x09 -> out.println("List[")
            0x0a -> out.println("Compound[")
            0x0b -> out.println("IntArray[")
            0x0c -> out.println("LongArray[")
        }

        val size = readInt()
        for (i in 0 ..< size) {
            out.print("    ".repeat(availableEnds + 1))
            when (tagtype) {
                0x00 -> out.print("")
                0x01 -> out.print("0x${readByte().toHexString()}")
                0x02 -> out.print("${readShort()}")
                0x03 -> out.print("${readInt()}")
                0x04 -> out.print("${readLong()}")
                0x05 -> out.print("${readFloat()}")
                0x06 -> out.print("${readDouble()}")
                0x07 -> out.print("${{
                    var r = "byte["
                    val siz = readInt()
                    for (j in 0 ..< siz) r += "0x${readByte().toHexString()}"
                    "$r]"
                }}")
                0x08 -> out.print(readUTF())
                0x09 -> readListTag(header = false)
                0x0a -> readCompoundTag(header = false)
                0x0b -> out.print("${{
                    var r = "byte["
                    val siz = readInt()
                    for (k in 0 ..< siz) r += "${readInt()}"
                    "$r]"
                }}")
                0x0c -> out.print("${{
                    var r = "byte["
                    val siz = readInt()
                    for (l in 0 ..< siz) r += "${readLong()}"
                    "$r]"
                }}")
            }
            out.println(", ")
        }
        out.println("${"    ".repeat(availableEnds)}]")
    }
    fun readCompoundTag(check: Boolean = true, header: Boolean = true) {
        if (header) {
            if (check && readByte() != 0x0a.toByte()) throw IllegalStateException("Invalid compound tag")
            out.println("${"    ".repeat(availableEnds)}\"${readUTF()}\" -> {")

        }
        availableEnds++

        var ended = false
        while (!ended) {
            when (readByte().toInt()) {
                0x00 -> ended = true
                0x01 -> readByteTag(false)
                0x02 -> readShortTag(false)
                0x03 -> readIntTag(false)
                0x04 -> readLongTag(false)
                0x05 -> readFloatTag(false)
                0x06 -> readDoubleTag(false)
                0x07 -> readByteArrayTag(false)
                0x08 -> readStringTag(false)
                0x09 -> readListTag(false)
                0x0a -> readCompoundTag(false)
                0x0b -> readIntArrayTag(false)
                0x0c -> readLongArrayTag(false)
            }
        }

        availableEnds--

        // forwardBytes()
    }

    fun readIntArrayTag(check: Boolean = true) {
        if (check && readByte() != 0x0b.toByte()) throw IllegalStateException("Invalid int array tag")
        out.print("${"    ".repeat(availableEnds)}${readUTF()} -> int[")
        val siz = readInt()
        for (i in 0 ..< siz) out.print("${readInt()}, ")
        out.println("]")
    }

    fun readLongArrayTag(check: Boolean = true) {
        if (check && readByte() != 0x0c.toByte()) throw IllegalStateException("Invalid long array tag")
        out.print("${"    ".repeat(availableEnds)}${readUTF()} -> long[")
        val siz = readInt()
        for (i in 0 ..< siz) out.print("${readLong()}, ")
        out.println("]")
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun forwardBytes() {
        readNBytes(16).forEach { print("0x${it.toHexString()} ") }
    }
}
