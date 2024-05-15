package com.primogemstudio.advancedfmk.bin

import java.io.DataOutputStream
import java.io.OutputStream

class NBTOutputStream(out: OutputStream): DataOutputStream(out) {
    fun writeEndTag() = writeByte(0x00)
    fun writeByteTag(s: String, b: Byte) {
        writeByte(0x01)
        writeUTF(s)
        writeByte(b.toInt())
    }
    fun writeShortTag(s: String, sh: Short) {
        writeByte(0x02)
        writeUTF(s)
        writeShort(sh.toInt())
    }
    fun writeIntTag(s: String, i: Int) {
        writeByte(0x03)
        writeUTF(s)
        writeInt(i)
    }
    fun writeLongTag(s: String, l: Long) {
        writeByte(0x04)
        writeUTF(s)
        writeLong(l)
    }
    fun writeFloatTag(s: String, f: Float) {
        writeByte(0x05)
        writeUTF(s)
        writeFloat(f)
    }
    fun writeDoubleTag(s: String, d: Double) {
        writeByte(0x06)
        writeUTF(s)
        writeDouble(d)
    }
    fun writeByteArray(s: String, br: ByteArray) {
        writeByte(0x07)
        writeUTF(s)
        writeInt(br.size)
        br.forEach { writeByte(it.toInt()) }
    }
    fun writeString(s: String, st: String) {
        writeByte(0x08)
        writeUTF(s)
        writeUTF(st)
    }
    fun writeListTag(s: String, l: List<Any>, withpre: Boolean = true) {
        if (withpre) {
            writeByte(0x09)
            writeUTF(s)
        }

        if (l.isEmpty()) {
            writeByte(0x01)
            writeInt(0)
            return
        }

        writeByte(when (l[0]) {
            is Byte -> 0x01
            is Short -> 0x02
            is Int -> 0x03
            is Long -> 0x04
            is Float -> 0x05
            is Double -> 0x06
            is ByteArray -> 0x07
            is String -> 0x08
            is List<*> -> 0x09
            is Map<*, *> -> 0x0a
            is IntArray -> 0x0b
            is LongArray -> 0x0c
            else -> 0x00
        })
        writeInt(l.size)

        l.forEach {
            @Suppress("UNCHECKED_CAST")
            when (it) {
                is Byte -> writeByte(it.toInt())
                is Short -> writeShort(it.toInt())
                is Int -> writeInt(it)
                is Long -> writeLong(it)
                is Float -> writeFloat(it)
                is Double -> writeDouble(it)
                is ByteArray -> {
                    writeInt(it.size)
                    it.forEach { b -> writeByte(b.toInt()) }
                }
                is String -> writeUTF(it)
                is List<*> -> writeListTag("", it as List<Any>, false)
                is Map<*, *> -> {
                    (it as Map<String, Any>).forEach { k, v ->
                        when (v) {
                            is Byte -> writeByteTag(k, v)
                            is Short -> writeShortTag(k, v)
                            is Int -> writeIntTag(k, v)
                            is Long -> writeLongTag(k, v)
                            is Float -> writeFloatTag(k, v)
                            is Double -> writeDoubleTag(k, v)
                            is ByteArray -> writeByteArray(k, v)
                            is String -> writeString(k, v)
                            is List<*> -> writeListTag(k, v as List<Any>)
                            is Map<*, *> -> writeCompoundTag(k, v as Map<String, Any>)
                            is IntArray -> writeIntArray(k, v)
                            is LongArray -> writeLongArray(k, v)
                        }
                    }
                    writeEndTag()
                }
                is IntArray -> {
                    writeInt(it.size)
                    it.forEach { b -> writeInt(b) }
                }
                is LongArray -> {
                    writeInt(it.size)
                    it.forEach { b -> writeLong(b) }
                }
                else -> writeEndTag()
            }
        }
    }
    fun writeCompoundTag(s: String, c: Map<String, Any>) {
        writeByte(0x0a)
        writeUTF(s)

        @Suppress("UNCHECKED_CAST")
        c.forEach { k, v ->
            when (v) {
                is Byte -> writeByteTag(k, v)
                is Short -> writeShortTag(k, v)
                is Int -> writeIntTag(k, v)
                is Long -> writeLongTag(k, v)
                is Float -> writeFloatTag(k, v)
                is Double -> writeDoubleTag(k, v)
                is ByteArray -> writeByteArray(k, v)
                is String -> writeString(k, v)
                is List<*> -> writeListTag(k, v as List<Any>)
                is Map<*, *> -> writeCompoundTag(k, v as Map<String, Any>)
                is IntArray -> writeIntArray(k, v)
                is LongArray -> writeLongArray(k, v)
            }
        }
        writeEndTag()
    }

    fun writeIntArray(s: String, ir: IntArray) {
        writeByte(0x0b)
        writeUTF(s)
        writeInt(ir.size)
        ir.forEach { writeInt(it) }
    }
    fun writeLongArray(s: String, lr: LongArray) {
        writeByte(0x0c)
        writeUTF(s)
        writeInt(lr.size)
        lr.forEach { writeLong(it) }
    }
}