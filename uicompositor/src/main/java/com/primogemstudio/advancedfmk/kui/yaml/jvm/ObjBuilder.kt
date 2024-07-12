package com.primogemstudio.advancedfmk.kui.yaml.jvm

import com.primogemstudio.advancedfmk.kui.yaml.UIRoot
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldNode
import org.objectweb.asm.tree.MethodNode
import java.io.File

class ObjBuilder(val root: UIRoot): ClassLoader() {
    fun build() {
        val cn = ClassNode()
        cn.access = ACC_PUBLIC or ACC_FINAL
        cn.version = V21
        cn.name = root.className.replace(".", "/")
        cn.superName = "java/lang/Object"
        cn.sourceFile = "<yaml>"

        cn.fields.add(FieldNode(ACC_PUBLIC or ACC_FINAL, "internal", "Lcom/primogemstudio/advancedfmk/kui/elements/UIElement;", null, null))

        val mn = MethodNode(
            ACC_PUBLIC,
            "<init>", "()V", null, null
        )
        mn.visitCode()
        mn.visitVarInsn(ALOAD, 0)
        mn.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)

        mn.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        mn.visitLdcInsn("Test")
        mn.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)

        mn.visitVarInsn(ALOAD, 0)
        mn.visitTypeInsn(NEW, "com/primogemstudio/advancedfmk/kui/elements/GroupElement")
        mn.visitInsn(DUP)
        mn.visitLdcInsn("main")
        mn.visitInsn(ICONST_2)
        mn.visitTypeInsn(ANEWARRAY, "com/primogemstudio/advancedfmk/kui/elements/RealElement")
        mn.visitVarInsn(ASTORE, 1)
        mn.visitVarInsn(ALOAD, 1)
        mn.visitInsn(ICONST_0)
        mn.visitTypeInsn(NEW, "com/primogemstudio/advancedfmk/kui/elements/RectangleElement")
        mn.visitInsn(DUP)
        mn.visitLdcInsn("test")

        mn.visitTypeInsn(NEW, "org/joml/Vector2f")
        mn.visitInsn(DUP)
        mn.visitInsn(FCONST_0)
        mn.visitInsn(FCONST_0)
        mn.visitMethodInsn(INVOKESPECIAL, "org/joml/Vector2f", "<init>", "(FF)V", false)

        mn.visitTypeInsn(NEW, "org/joml/Vector2f")
        mn.visitInsn(DUP)
        mn.visitLdcInsn(100f)
        mn.visitLdcInsn(100f)
        mn.visitMethodInsn(INVOKESPECIAL, "org/joml/Vector2f", "<init>", "(FF)V", false)

        mn.visitTypeInsn(NEW, "org/joml/Vector4f")
        mn.visitInsn(DUP)
        mn.visitInsn(FCONST_1)
        mn.visitInsn(FCONST_1)
        mn.visitInsn(FCONST_1)
        mn.visitLdcInsn(0.25f)
        mn.visitMethodInsn(INVOKESPECIAL, "org/joml/Vector4f", "<init>", "(FFFF)V", false)

        mn.visitLdcInsn(20f)
        mn.visitInsn(FCONST_0)

        mn.visitLdcInsn(0.006f)
        mn.visitLdcInsn("advancedfmk:ui/textures/microsoft.png")
        mn.visitMethodInsn(INVOKESTATIC, "net/minecraft/resources/ResourceLocation", "parse", "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;", false)

        mn.visitTypeInsn(NEW, "com/primogemstudio/advancedfmk/kui/pipe/PostShaderFilter")
        mn.visitInsn(DUP)
        mn.visitMethodInsn(INVOKESTATIC, "org/ladysnake/satin/api/managed/ShaderEffectManager", "getInstance", "()Lorg/ladysnake/satin/api/managed/ShaderEffectManager;", true)
        mn.visitLdcInsn("shaders/filter/gaussian_blur.json")
        mn.visitMethodInsn(INVOKESTATIC, "net/minecraft/resources/ResourceLocation", "withDefaultNamespace", "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;", false)
        mn.visitMethodInsn(INVOKEINTERFACE, "org/ladysnake/satin/api/managed/ShaderEffectManager", "manage", "(Lnet/minecraft/resources/ResourceLocation;)Lorg/ladysnake/satin/api/managed/ManagedShaderEffect;", true)

        mn.visitMethodInsn(INVOKESPECIAL, "com/primogemstudio/advancedfmk/kui/pipe/PostShaderFilter", "<init>", "(Lorg/ladysnake/satin/api/managed/ManagedShaderEffect;)V", false)
        mn.visitTypeInsn(CHECKCAST, "com/primogemstudio/advancedfmk/kui/pipe/FilterBase")
        mn.visitMethodInsn(INVOKESPECIAL, "com/primogemstudio/advancedfmk/kui/elements/RectangleElement", "<init>", "(Ljava/lang/String;Lorg/joml/Vector2f;Lorg/joml/Vector2f;Lorg/joml/Vector4f;FFFLnet/minecraft/resources/ResourceLocation;Lcom/primogemstudio/advancedfmk/kui/pipe/FilterBase;)V", false)
        mn.visitInsn(AASTORE)
        mn.visitVarInsn(ALOAD, 1)
        mn.visitInsn(ICONST_1)

        mn.visitTypeInsn(NEW, "com/primogemstudio/advancedfmk/kui/elements/TextElement")
        mn.visitInsn(DUP)
        mn.visitLdcInsn("test")
        mn.visitTypeInsn(NEW, "org/joml/Vector2f")
        mn.visitInsn(DUP)
        mn.visitInsn(FCONST_0)
        mn.visitInsn(FCONST_0)
        mn.visitMethodInsn(INVOKESPECIAL, "org/joml/Vector2f", "<init>", "(FF)V", false)

        mn.visitLdcInsn("测试！Hello world from UI compositor!")
        mn.visitTypeInsn(NEW, "org/joml/Vector4f")
        mn.visitInsn(DUP)
        mn.visitInsn(FCONST_1)
        mn.visitMethodInsn(INVOKESPECIAL, "org/joml/Vector4f", "<init>", "(F)V", false)

        mn.visitIntInsn(BIPUSH, 9)
        mn.visitInsn(ICONST_0)
        mn.visitIntInsn(BIPUSH, 32)
        mn.visitInsn(ACONST_NULL)

        mn.visitMethodInsn(INVOKESPECIAL, "com/primogemstudio/advancedfmk/kui/elements/TextElement", "<init>", "(Ljava/lang/String;Lorg/joml/Vector2f;Ljava/lang/String;Lorg/joml/Vector4f;IZILkotlin/jvm/internal/DefaultConstructorMarker;)V", false)
        mn.visitInsn(AASTORE)

        mn.visitVarInsn(ALOAD, 1)
        mn.visitMethodInsn(INVOKESTATIC, "kotlin/collections/CollectionsKt", "listOf", "([Ljava/lang/Object;)Ljava/util/List;", false)
        mn.visitMethodInsn(INVOKESPECIAL, "com/primogemstudio/advancedfmk/kui/elements/GroupElement", "<init>", "(Ljava/lang/String;Ljava/util/List;)V", false)

        mn.visitFieldInsn(PUTFIELD, root.className.replace(".", "/"), "internal", "Lcom/primogemstudio/advancedfmk/kui/elements/UIElement;")

        mn.visitInsn(RETURN)

        mn.visitEnd()
        cn.methods.add(mn)

        val cw = ClassWriter(ClassWriter.COMPUTE_MAXS)
        cn.accept(cw)

        File("run/TestUI.class").writeBytes(cw.toByteArray())
        val c = defineClass(root.className, cw.toByteArray())

        val cs = c.getConstructor()
        val ins = cs.newInstance()
        println(c.getField("internal").get(ins))
    }

    private fun defineClass(name: String, b: ByteArray): Class<*> {
        return super.defineClass(name, b, 0, b.size)
    }
}