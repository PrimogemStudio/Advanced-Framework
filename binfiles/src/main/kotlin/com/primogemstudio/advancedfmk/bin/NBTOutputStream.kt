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