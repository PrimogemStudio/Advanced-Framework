package com.primogemstudio.advancedfmk.kui.yaml.jvm

import net.fabricmc.loader.api.FabricLoader
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.MethodNode
import kotlin.reflect.KClass

fun String.toNType(): String = this
    .replace("char", "C")
    .replace("byte", "B")
    .replace("short", "S")
    .replace("int", "I")
    .replace("long", "J")
    .replace("double", "D")
    .replace("float", "F")
    .replace("boolean", "Z")
    .replace("java.lang.Void", "V")
    .replace(".", "/")

private val intypes = listOf("C", "B", "S", "I", "J", "D", "F", "Z", "V")

fun sigt(cls: KClass<*>): String = sig(cls).let { if (it.startsWith("[") || intypes.indexOf(it) >= 0) it else "L${it};" }
fun sig(cls: KClass<*>): String = sig(cls.java.name)
fun sig(s: String): String = s.toNType()
fun sigf(ret: KClass<*>, vararg args: KClass<*>): String = "(${args.joinToString("") { sigt(it) }})${sigt(ret)}"

fun sigfr(cls: KClass<*>, n: String, sig: String): String = FabricLoader.getInstance().mappingResolver.mapMethodName("", cls.java.name, n, sig)

val INIT = "<init>"
val KT_KOLLECTIONS = "kotlin/collections/CollectionsKt"

fun MethodNode.aconst_null() = visitInsn(ACONST_NULL)
fun MethodNode.aload0() = visitVarInsn(ALOAD, 0)
fun MethodNode.aload1() = visitVarInsn(ALOAD, 1)
fun MethodNode.astore0() = visitVarInsn(ASTORE, 0)
fun MethodNode.astore1() = visitVarInsn(ASTORE, 1)
fun MethodNode.aastore() = visitInsn(AASTORE)
fun MethodNode.dup() = visitInsn(DUP)
fun MethodNode.iconst0() = visitInsn(ICONST_0)
fun MethodNode.iconst1() = visitInsn(ICONST_1)
fun MethodNode.iconst2() = visitInsn(ICONST_2)
fun MethodNode.iconst3() = visitInsn(ICONST_3)
fun MethodNode.iconst4() = visitInsn(ICONST_4)
fun MethodNode.iconst5() = visitInsn(ICONST_5)
fun MethodNode.fconst0() = visitInsn(FCONST_0)
fun MethodNode.fconst1() = visitInsn(FCONST_1)
fun MethodNode.fconst2() = visitInsn(FCONST_2)
fun MethodNode.dconst0() = visitInsn(DCONST_0)
fun MethodNode.dconst1() = visitInsn(DCONST_1)
fun MethodNode.ldc(v: Any) {
    when (v) {
        0 -> iconst0()
        1 -> iconst1()
        2-> iconst2()
        3 -> iconst3()
        4 -> iconst4()
        5 -> iconst5()
        0f -> fconst0()
        1f -> fconst1()
        2f -> fconst2()
        0.0 -> dconst0()
        1.0 -> dconst1()
        else -> visitLdcInsn(v)
    }
}
fun MethodNode.return_() = visitInsn(RETURN)
fun MethodNode.new(s: String) = visitTypeInsn(NEW, s)
fun MethodNode.checkcast(s: String) = visitTypeInsn(CHECKCAST, s)
fun MethodNode.anewarray(s: String) = visitTypeInsn(ANEWARRAY, s)
fun MethodNode.invokespecial(s: String, s2: String, s3: String) = visitMethodInsn(INVOKESPECIAL, s, s2, s3, false)