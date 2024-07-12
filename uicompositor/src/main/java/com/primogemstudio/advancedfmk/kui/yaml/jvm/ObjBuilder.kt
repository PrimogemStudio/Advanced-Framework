package com.primogemstudio.advancedfmk.kui.yaml.jvm

import com.primogemstudio.advancedfmk.kui.yaml.UIRoot
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldNode
import org.objectweb.asm.tree.MethodNode
import java.io.File

fun MethodNode.aload0() = visitVarInsn(ALOAD, 0)
fun MethodNode.aload1() = visitVarInsn(ALOAD, 1)
fun MethodNode.astore1() = visitVarInsn(ASTORE, 1)
fun MethodNode.aastore() = visitInsn(AASTORE)
fun MethodNode.dup() = visitInsn(DUP)
fun MethodNode.iconst0() = visitInsn(ICONST_0)
fun MethodNode.iconst1() = visitInsn(ICONST_1)
fun MethodNode.iconst2() = visitInsn(ICONST_2)
fun MethodNode.fconst0() = visitInsn(FCONST_0)
fun MethodNode.fconst1() = visitInsn(FCONST_1)
fun MethodNode.ldc(v: Any) = visitLdcInsn(v)
fun MethodNode.return_() = visitInsn(RETURN)
fun MethodNode.new(s: String) = visitTypeInsn(NEW, s)
fun MethodNode.checkcast(s: String) = visitTypeInsn(CHECKCAST, s)
fun MethodNode.anewarray(s: String) = visitTypeInsn(ANEWARRAY, s)
fun MethodNode.invokespecial(s: String, s2: String, s3: String) = visitMethodInsn(INVOKESPECIAL, s, s2, s3, false)

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
        // Super init
        mn.aload0()
        mn.invokespecial("java/lang/Object", "<init>", "()V")

        // stdout
        mn.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        mn.ldc("Test")
        mn.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)

        // Push stack (this)
        mn.aload0()
        // New instance
        mn.new("com/primogemstudio/advancedfmk/kui/elements/GroupElement")
        mn.dup()
        // arg 1
        mn.ldc("main")
        // arg 2 - array length
        mn.iconst2()
        // arg 2 - array
        mn.anewarray("com/primogemstudio/advancedfmk/kui/elements/RealElement")
        // ???
        mn.astore1()
        mn.aload1()
        run {
            // array[0] construct
            mn.iconst0()

            // New instance
            mn.new("com/primogemstudio/advancedfmk/kui/elements/RectangleElement")
            mn.dup()
            // arg 1
            mn.ldc("test")

            // arg 2
            mn.new("org/joml/Vector2f")
            mn.dup()
            mn.fconst0()
            mn.fconst0()
            mn.invokespecial( "org/joml/Vector2f", "<init>", "(FF)V")

            // arg 3
            mn.new("org/joml/Vector2f")
            mn.dup()
            mn.ldc(100f)
            mn.ldc(100f)
            mn.invokespecial( "org/joml/Vector2f", "<init>", "(FF)V")

            // arg 4
            mn.new("org/joml/Vector4f")
            mn.dup()
            mn.fconst1()
            mn.fconst1()
            mn.fconst1()
            mn.ldc(0.25f)
            mn.invokespecial( "org/joml/Vector4f", "<init>", "(FFFF)V")

            // arg 5
            mn.ldc(20f)
            // arg 6
            mn.fconst0()
            // arg 7
            mn.ldc(0.006f)
            // arg 8
            mn.ldc("advancedfmk:ui/textures/microsoft.png")

            // arg 9
            mn.visitMethodInsn(
                INVOKESTATIC,
                "net/minecraft/resources/ResourceLocation",
                "parse",
                "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;",
                false
            )

            mn.new("com/primogemstudio/advancedfmk/kui/pipe/PostShaderFilter")
            mn.dup()
            mn.visitMethodInsn(
                INVOKESTATIC,
                "org/ladysnake/satin/api/managed/ShaderEffectManager",
                "getInstance",
                "()Lorg/ladysnake/satin/api/managed/ShaderEffectManager;",
                true
            )
            mn.ldc("shaders/filter/gaussian_blur.json")
            mn.visitMethodInsn(
                INVOKESTATIC,
                "net/minecraft/resources/ResourceLocation",
                "withDefaultNamespace",
                "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;",
                false
            )
            mn.visitMethodInsn(
                INVOKEINTERFACE,
                "org/ladysnake/satin/api/managed/ShaderEffectManager",
                "manage",
                "(Lnet/minecraft/resources/ResourceLocation;)Lorg/ladysnake/satin/api/managed/ManagedShaderEffect;",
                true
            )

            mn.visitMethodInsn(
                INVOKESPECIAL,
                "com/primogemstudio/advancedfmk/kui/pipe/PostShaderFilter",
                "<init>",
                "(Lorg/ladysnake/satin/api/managed/ManagedShaderEffect;)V",
                false
            )
            mn.checkcast("com/primogemstudio/advancedfmk/kui/pipe/FilterBase")
            mn.visitMethodInsn(
                INVOKESPECIAL,
                "com/primogemstudio/advancedfmk/kui/elements/RectangleElement",
                "<init>",
                "(Ljava/lang/String;Lorg/joml/Vector2f;Lorg/joml/Vector2f;Lorg/joml/Vector4f;FFFLnet/minecraft/resources/ResourceLocation;Lcom/primogemstudio/advancedfmk/kui/pipe/FilterBase;)V",
                false
            )
            // store array[0]
            mn.aastore()
            mn.aload1()
        }

        run {
            // array[1]
            mn.iconst1()
            // New instance
            mn.new("com/primogemstudio/advancedfmk/kui/elements/TextElement")
            mn.dup()
            // arg 1
            mn.ldc("test")
            // arg 2
            mn.new("org/joml/Vector2f")
            mn.dup()
            mn.fconst0()
            mn.fconst0()
            mn.invokespecial( "org/joml/Vector2f", "<init>", "(FF)V")
            // arg3
            mn.ldc("测试！Hello world from UI compositor!")
            // arg4
            mn.new("org/joml/Vector4f")
            mn.dup()
            mn.fconst1()
            mn.invokespecial( "org/joml/Vector4f", "<init>", "(F)V")
            // arg 5
            mn.ldc(9)
            // arg 6
            mn.ldc(false)


            mn.invokespecial( "com/primogemstudio/advancedfmk/kui/elements/TextElement", "<init>", "(Ljava/lang/String;Lorg/joml/Vector2f;Ljava/lang/String;Lorg/joml/Vector4f;IZ)V")
            mn.aastore()
            mn.aload1()
        }
        // build list
        mn.visitMethodInsn(INVOKESTATIC, "kotlin/collections/CollectionsKt", "listOf", "([Ljava/lang/Object;)Ljava/util/List;", false)
        // final init
        mn.invokespecial( "com/primogemstudio/advancedfmk/kui/elements/GroupElement", "<init>", "(Ljava/lang/String;Ljava/util/List;)V")
        // save to variable
        mn.visitFieldInsn(PUTFIELD, root.className.replace(".", "/"), "internal", "Lcom/primogemstudio/advancedfmk/kui/elements/UIElement;")
        mn.return_()

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