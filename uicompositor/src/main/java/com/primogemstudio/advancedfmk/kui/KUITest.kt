package com.primogemstudio.advancedfmk.kui

import com.primogemstudio.advancedfmk.kui.elements.GroupElement
import com.primogemstudio.advancedfmk.kui.elements.RectangleElement
import com.primogemstudio.advancedfmk.kui.elements.TextElement
import com.primogemstudio.advancedfmk.kui.pipe.PostShaderFilter
import com.primogemstudio.advancedfmk.kui.qml.parser.QMLLexer
import com.primogemstudio.advancedfmk.kui.qml.parser.QMLParser
import net.minecraft.resources.ResourceLocation
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.joml.Vector2f
import org.joml.Vector4f
import org.ladysnake.satin.api.managed.ShaderEffectManager
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode

class KUITest {
    val elem = GroupElement(
        listOf(
            RectangleElement(
                Vector2f(0f, 0f),
                Vector2f(100f, 100f),
                Vector4f(1f, 1f, 1f, 0.25f),
                20f,
                0f,
                0.006f,
                ResourceLocation.parse("advancedfmk:ui/textures/microsoft.png"),
                PostShaderFilter(
                    ShaderEffectManager.getInstance()
                        .manage(ResourceLocation.withDefaultNamespace("shaders/filter/gaussian_blur.json"))
                )
            ),
            TextElement(
                Vector2f(0f, 0f),
                "测试！Hello world from UI compositor!",
                Vector4f(1f),
                9
            )
        )
    )
}

fun main() {
    val parser =
        QMLParser(CommonTokenStream(QMLLexer(CharStreams.fromString("import QtQuick 2.0\nRectangle { color = \"cyan\" }"))))
    println(parser.program().toStringTree())

    val cn = ClassNode()
    cn.access = ACC_PUBLIC
    cn.version = V21
    cn.name = "com/primogemstudio/advancedfmk/kui/Dyn"
    cn.superName = "java/lang/Object"
    cn.sourceFile = "test.qml"

    val mnc = MethodNode(
        ACC_PUBLIC,
        "<init>", "()V", null, null
    )
    mnc.visitCode()
    mnc.visitVarInsn(ALOAD, 0)
    mnc.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)
    mnc.visitInsn(RETURN)
    mnc.visitEnd()
    cn.methods.add(mnc)

    val mn = MethodNode(
        ACC_PUBLIC,
        "test", "()V", null, arrayOf("java/lang/Exception")
    )
    mn.visitCode()
    mn.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
    mn.visitLdcInsn("Test")
    mn.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)

    mn.visitTypeInsn(NEW, "java/lang/Exception")
    mn.visitInsn(DUP)
    mn.visitLdcInsn("This is a generated exception")
    mn.visitMethodInsn(INVOKESPECIAL, "java/lang/Exception", "<init>", "(Ljava/lang/String;)V", false)
    mn.visitInsn(ATHROW)

    mn.visitEnd()
    cn.methods.add(mn)

    val cw = ClassWriter(ClassWriter.COMPUTE_MAXS)
    cn.accept(cw)

    val cld = MyClassLoader()
    val c = cld.defineClass("com.primogemstudio.advancedfmk.kui.Dyn", cw.toByteArray())

    val r = c.getMethod("test")
    val cs = c.getConstructor()
    val ins = cs.newInstance()
    r.invoke(ins)
}

class MyClassLoader : ClassLoader() {
    fun defineClass(name: String, b: ByteArray): Class<*> {
        return super.defineClass(name, b, 0, b.size)
    }
}