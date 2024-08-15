package com.primogemstudio.advancedfmk.kui.yaml.jvm

import com.ibm.icu.impl.ClassLoaderUtil
import com.primogemstudio.advancedfmk.kui.yaml.*
import com.primogemstudio.advancedfmk.kui.yaml.ComponentType.*
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldNode
import org.objectweb.asm.tree.MethodNode
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.random.Random

fun MethodNode.aconst_null() = visitInsn(ACONST_NULL)
fun MethodNode.aload0() = visitVarInsn(ALOAD, 0)
fun MethodNode.aload1() = visitVarInsn(ALOAD, 1)
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

class YamlCompiler(val root: UIRoot): ClassLoader(ClassLoaderUtil.getClassLoader()) {
    fun build(): Any {
        val cn = ClassNode()
        val cnn = root.className.replace("\$random", Random.nextInt().toString())
        cn.access = ACC_PUBLIC or ACC_FINAL
        cn.version = V21
        cn.name = cnn.replace(".", "/")
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

        mn.aload0()
        when (root.component?.type) {
            GROUP -> buildGroupElement(mn, root.component as GroupComponent, root.rootName)
            TEXT -> buildTextElement(mn, root.component as TextComponent, root.rootName)
            RECTANGLE -> buildRectangleElement(mn, root.component as RectangleComponent, root.rootName)
            null -> throw NullPointerException()
            GEOMETRY_LINE -> buildGeometryLineElement(mn, root.component as GeometryLineComponent, root.rootName)
        }
        mn.visitFieldInsn(PUTFIELD, cnn.replace(".", "/"), "internal", "Lcom/primogemstudio/advancedfmk/kui/elements/UIElement;")

        mn.return_()

        mn.visitEnd()
        cn.methods.add(mn)

        val cw = ClassWriter(ClassWriter.COMPUTE_MAXS)
        cn.accept(cw)

        Files.write(Path("TestUI.class"), cw.toByteArray())
        val c = defineClass(cnn, cw.toByteArray())

        val cs = c.getConstructor()
        val ins = cs.newInstance()
        return c.getField("internal").get(ins)
    }

    private fun buildGeometryLineElement(mn: MethodNode, c: GeometryLineComponent, n: String) {
        mn.new("com/primogemstudio/advancedfmk/kui/elements/GeometryLineElement")
        mn.dup()

        mn.ldc(n)

        mn.ldc(c.width!!)

        mn.new("org/joml/Vector4f")
        mn.dup()
        mn.ldc(c.color!![0])
        mn.ldc(c.color!![1])
        mn.ldc(c.color!![2])
        mn.ldc(c.color!![3])
        mn.invokespecial( "org/joml/Vector4f", "<init>", "(FFFF)V")

        if (c.filter != null) {
            when (c.filter!!["type"]) {
                "post" -> {
                    mn.new("com/primogemstudio/advancedfmk/kui/pipe/PostShaderFilter")
                    mn.dup()
                    mn.visitMethodInsn(
                        INVOKESTATIC,
                        "org/ladysnake/satin/api/managed/ShaderEffectManager",
                        "getInstance",
                        "()Lorg/ladysnake/satin/api/managed/ShaderEffectManager;",
                        true
                    )
                    mn.ldc(c.filter!!["location"] as String)
                    mn.visitMethodInsn(
                        INVOKESTATIC,
                        "net/minecraft/resources/ResourceLocation",
                        "parse",
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

                    mn.invokespecial(
                        "com/primogemstudio/advancedfmk/kui/pipe/PostShaderFilter",
                        "<init>",
                        "(Lorg/ladysnake/satin/api/managed/ManagedShaderEffect;)V"
                    )
                    mn.checkcast("com/primogemstudio/advancedfmk/kui/pipe/FilterBase")
                }
                else -> mn.aconst_null()
            }
        }
        else mn.aconst_null()

        mn.invokespecial( "com/primogemstudio/advancedfmk/kui/elements/GeometryLineElement", "<init>", "(Ljava/lang/String;FLorg/joml/Vector4f;Lcom/primogemstudio/advancedfmk/kui/pipe/FilterBase;)V")
    }

    private fun buildGroupElement(mn: MethodNode, c: GroupComponent, n: String) {
        mn.new("com/primogemstudio/advancedfmk/kui/elements/GroupElement")
        mn.dup()

        mn.ldc(n)

        mn.ldc(c.components?.size?: 0)

        mn.anewarray("com/primogemstudio/advancedfmk/kui/elements/RealElement")
        mn.astore1()
        mn.aload1()

        var i = 0
        c.components?.forEach { (t, u) ->
            mn.ldc(i)
            i++
            when (u?.type) {
                TEXT -> buildTextElement(mn, u as TextComponent, t)
                GROUP -> buildGroupElement(mn, u as GroupComponent, t)
                RECTANGLE -> buildRectangleElement(mn, u as RectangleComponent, t)
                null -> throw NullPointerException()
                GEOMETRY_LINE -> buildGeometryLineElement(mn, u as GeometryLineComponent, t)
            }
            mn.aastore()
            mn.aload1()
        }
        mn.visitMethodInsn(INVOKESTATIC, "kotlin/collections/CollectionsKt", "listOf", "([Ljava/lang/Object;)Ljava/util/List;", false)
        mn.invokespecial( "com/primogemstudio/advancedfmk/kui/elements/GroupElement", "<init>", "(Ljava/lang/String;Ljava/util/List;)V")
    }

    private fun buildRectangleElement(mn: MethodNode, c: RectangleComponent, n: String) {
        mn.new("com/primogemstudio/advancedfmk/kui/elements/RectangleElement")
        mn.dup()

        mn.ldc(n)

        mn.new("org/joml/Vector2f")
        mn.dup()
        mn.ldc(c.pos!![0])
        mn.ldc(c.pos!![1])
        mn.invokespecial( "org/joml/Vector2f", "<init>", "(FF)V")

        mn.new("org/joml/Vector2f")
        mn.dup()
        mn.ldc(c.size!![0])
        mn.ldc(c.size!![1])
        mn.invokespecial( "org/joml/Vector2f", "<init>", "(FF)V")

        mn.new("org/joml/Vector4f")
        mn.dup()
        mn.ldc(c.color!![0])
        mn.ldc(c.color!![1])
        mn.ldc(c.color!![2])
        mn.ldc(c.color!![3])
        mn.invokespecial( "org/joml/Vector4f", "<init>", "(FFFF)V")

        mn.ldc(c.radius?: 0f)
        mn.ldc(c.thickness?: 0f)
        mn.ldc(c.smoothedge?: 0f)

        mn.new("org/joml/Vector4f")
        mn.dup()
        mn.ldc(c.textureUV?.get(0)?: 0f)
        mn.ldc(c.textureUV?.get(1)?: 1f)
        mn.ldc(c.textureUV?.get(2)?: 0f)
        mn.ldc(c.textureUV?.get(3)?: 1f)
        mn.invokespecial( "org/joml/Vector4f", "<init>", "(FFFF)V")

        if (c.texture != null) {
            mn.ldc(c.texture!!)
            mn.visitMethodInsn(
                INVOKESTATIC,
                "net/minecraft/resources/ResourceLocation",
                "parse",
                "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;",
                false
            )
        } else mn.aconst_null()

        if (c.filter != null) {
            when (c.filter!!["type"]) {
                "post" -> {
                    mn.new("com/primogemstudio/advancedfmk/kui/pipe/PostShaderFilter")
                    mn.dup()
                    mn.visitMethodInsn(
                        INVOKESTATIC,
                        "org/ladysnake/satin/api/managed/ShaderEffectManager",
                        "getInstance",
                        "()Lorg/ladysnake/satin/api/managed/ShaderEffectManager;",
                        true
                    )
                    mn.ldc(c.filter!!["location"] as String)
                    mn.visitMethodInsn(
                        INVOKESTATIC,
                        "net/minecraft/resources/ResourceLocation",
                        "parse",
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

                    mn.invokespecial(
                        "com/primogemstudio/advancedfmk/kui/pipe/PostShaderFilter",
                        "<init>",
                        "(Lorg/ladysnake/satin/api/managed/ManagedShaderEffect;)V"
                    )
                    mn.checkcast("com/primogemstudio/advancedfmk/kui/pipe/FilterBase")
                }
                else -> mn.aconst_null()
            }
        }
        else mn.aconst_null()

        mn.invokespecial(
            "com/primogemstudio/advancedfmk/kui/elements/RectangleElement",
            "<init>",
            "(Ljava/lang/String;Lorg/joml/Vector2f;Lorg/joml/Vector2f;Lorg/joml/Vector4f;FFFLorg/joml/Vector4f;Lnet/minecraft/resources/ResourceLocation;Lcom/primogemstudio/advancedfmk/kui/pipe/FilterBase;)V"
        )
    }

    private fun buildTextElement(mn: MethodNode, c: TextComponent, n: String) {
        mn.new("com/primogemstudio/advancedfmk/kui/elements/TextElement")
        mn.dup()
        mn.ldc(n)

        mn.new("org/joml/Vector2f")
        mn.dup()
        mn.ldc(c.pos!![0])
        mn.ldc(c.pos!![1])
        mn.invokespecial( "org/joml/Vector2f", "<init>", "(FF)V")

        mn.ldc(c.text?: "<null>")

        mn.new("org/joml/Vector4f")
        mn.dup()
        mn.ldc(c.color!![0])
        mn.ldc(c.color!![1])
        mn.ldc(c.color!![2])
        mn.ldc(c.color!![3])
        mn.invokespecial( "org/joml/Vector4f", "<init>", "(FFFF)V")

        mn.ldc(c.textsize?: 12)
        mn.ldc(c.vanilla?: false)

        mn.invokespecial( "com/primogemstudio/advancedfmk/kui/elements/TextElement", "<init>", "(Ljava/lang/String;Lorg/joml/Vector2f;Ljava/lang/String;Lorg/joml/Vector4f;IZ)V")
    }

    private fun defineClass(name: String, b: ByteArray): Class<*> {
        return super.defineClass(name, b, 0, b.size)
    }
}