package com.primogemstudio.advancedfmk.kui.yaml.jvm

import com.primogemstudio.advancedfmk.kui.yaml.UIRoot
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode

class ObjBuilder(val root: UIRoot): ClassLoader() {
    fun build() {
        val cn = ClassNode()
        cn.access = ACC_PUBLIC
        cn.version = V21
        cn.name = root.className.replace(".", "/")
        cn.superName = "java/lang/Object"
        cn.sourceFile = "<yaml>"

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

        val c = defineClass(root.className, cw.toByteArray())

        val r = c.getMethod("test")
        val cs = c.getConstructor()
        val ins = cs.newInstance()
        r.invoke(ins)
    }

    private fun defineClass(name: String, b: ByteArray): Class<*> {
        return super.defineClass(name, b, 0, b.size)
    }
}