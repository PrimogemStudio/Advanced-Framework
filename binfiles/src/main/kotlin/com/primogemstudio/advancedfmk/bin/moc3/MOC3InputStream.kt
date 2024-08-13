package com.primogemstudio.advancedfmk.bin.moc3

import java.io.DataInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class MOC3InputStream(`in`: InputStream): DataInputStream(`in`) {
    val size = available()

    fun parseInt(be: Boolean): Int {
        if (be) return readInt()
        else {
            val buff = ByteBuffer.allocate(4)
            buff.putInt(readInt())
            buff.order(ByteOrder.LITTLE_ENDIAN)
            return buff.getInt(0)
        }
    }

    fun parseHeader(): MOC3Header {
        return MOC3Header(
            String(readNBytes(4)),
            MOC3Header.Version.get(readByte()),
            readByte() != 0.toByte()
        ).apply {
            if (magic != "MOC3") throw IllegalStateException("Magic wrong")
            if (version == null) throw IllegalStateException("Unrecognized version")

            this@MOC3InputStream.skipNBytes(58)
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun test() {
        println("Current offset: ${size - available()}")
        readNBytes(16).forEach {
            print("0x" + it.toHexString() + " ")
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun parse() {
        val header = parseHeader()
        println(header)
        println(parseInt(header.bigEndian))
        println(parseInt(header.bigEndian))
        println(parseInt(header.bigEndian).toHexString())
        test()
    }
}