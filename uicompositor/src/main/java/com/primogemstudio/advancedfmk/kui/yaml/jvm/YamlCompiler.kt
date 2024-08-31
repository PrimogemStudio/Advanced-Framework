package com.primogemstudio.advancedfmk.kui.yaml.jvm

import com.ibm.icu.impl.ClassLoaderUtil
import com.primogemstudio.advancedfmk.kui.elements.*
import com.primogemstudio.advancedfmk.kui.pipe.ClipFilter
import com.primogemstudio.advancedfmk.kui.pipe.FilterBase
import com.primogemstudio.advancedfmk.kui.pipe.PostShaderFilter
import com.primogemstudio.advancedfmk.kui.yaml.*
import com.primogemstudio.advancedfmk.kui.yaml.ComponentType.*
import net.minecraft.resources.ResourceLocation
import org.joml.*
import org.ladysnake.satin.api.managed.ManagedShaderEffect
import org.ladysnake.satin.api.managed.ShaderEffectManager
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldNode
import org.objectweb.asm.tree.MethodNode
import java.nio.file.Files
import java.nio.file.Path
import kotlin.random.Random
import kotlin.reflect.KClass

fun createResourceLocation(s: String): ResourceLocation = ResourceLocation.parse(s)
fun parseShaderArgs(i: String): MutableMap<String, Any> {
    val target: MutableMap<String, Any> = mutableMapOf()
    if (i == "") return target
    i.split(";").forEach {
        val n = it.split(":")

        val argname = n[0]
        val dtype = n[1].split("(")[0]
        val args = n[1].split("(")[1].replace(")", "").split(",")

        if (dtype != "float" && dtype != "int") return@forEach

        val f = { s: String -> s.toFloat() }

        when (args.size) {
            1 -> target[argname] = if (dtype == "float") args[0].toFloat() else args[0].toInt()
            2 -> target[argname] = if (dtype == "float") Vector2f(args.map { i -> i.toFloat() }.toTypedArray().toFloatArray()) else Vector2i(args.map { i -> i.toInt() }.toTypedArray().toIntArray())
            3 -> target[argname] = if (dtype == "float") Vector3f(args.map { i -> i.toFloat() }.toTypedArray().toFloatArray()) else Vector3i(args.map { i -> i.toInt() }.toTypedArray().toIntArray())
            4 -> target[argname] = if (dtype == "float") Vector4f(args.map { i -> i.toFloat() }.toTypedArray().toFloatArray()) else Vector4i(args.map { i -> i.toInt() }.toTypedArray().toIntArray())
            16 -> if (dtype == "float") target[argname] = Matrix4f(
                f(args[0]), f(args[1]), f(args[2]), f(args[3]),
                f(args[4]), f(args[5]), f(args[6]), f(args[7]),
                f(args[8]), f(args[9]), f(args[10]), f(args[11]),
                f(args[12]), f(args[13]), f(args[14]), f(args[14]),
            )
        }
    }
    return target
}

class YamlCompiler(val root: UIRoot): ClassLoader(ClassLoaderUtil.getClassLoader()) {
    fun <T : Any> build(cls: KClass<T>): T = build(cls.java)
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> build(cls: Class<T>): T {
        val cn = ClassNode()
        val cnn = root.className.replace("\$random", Random.nextInt().toString())
        cn.access = ACC_PUBLIC or ACC_FINAL
        cn.version = V21
        cn.name = sig(cnn)
        cn.superName = sig(Object::class)
        cn.sourceFile = "<yaml>"

        cn.fields.add(FieldNode(ACC_PUBLIC or ACC_FINAL, "internal", sigt(UIElement::class), null, null))

        val mn = MethodNode(
            ACC_PUBLIC,
            INIT, sigf(Nothing::class), null, null
        )
        mn.visitCode()

        mn.aload0()
        mn.invokespecial(sig(Object::class), INIT, sigf(Nothing::class))

        mn.aload0()
        when (root.component?.type) {
            GROUP -> buildGroupElement(mn, root.component as GroupComponent, root.rootName)
            TEXT -> buildTextElement(mn, root.component as TextComponent, root.rootName)
            RECTANGLE -> buildRectangleElement(mn, root.component as RectangleComponent, root.rootName)
            null -> throw NullPointerException()
            GEOMETRY_LINE -> buildGeometryLineElement(mn, root.component as GeometryLineComponent, root.rootName)
            LIVE2D -> buildLive2DElement(mn, root.component as Live2DComponent, root.rootName)
        }
        mn.visitFieldInsn(PUTFIELD, sig(cnn), "internal", sigt(UIElement::class))

        mn.return_()

        mn.visitEnd()
        cn.methods.add(mn)

        val cw = ClassWriter(ClassWriter.COMPUTE_MAXS)
        cn.accept(cw)
        val c = defineClass(cnn, cw.toByteArray())

        Files.write(Path.of("$cnn.class"), cw.toByteArray())

        val cs = c.getConstructor()
        val ins = cs.newInstance()
        return c.getField("internal").get(ins) as T
    }

    private fun buildGeometryLineElement(mn: MethodNode, c: GeometryLineComponent, n: String) {
        mn.new(sig(GeometryLineElement::class))
        mn.dup()

        mn.ldc(n)

        mn.ldc(c.width!!)

        mn.new(sig(Vector4f::class))
        mn.dup()
        mn.ldc(c.color!![0])
        mn.ldc(c.color!![1])
        mn.ldc(c.color!![2])
        mn.ldc(c.color!![3])
        mn.invokespecial(sig(Vector4f::class), INIT, sigf(Nothing::class, Float::class, Float::class, Float::class, Float::class))

        mn.ldc(c.points?.size?: 0)
        mn.anewarray(sig(Vector2f::class))
        mn.astore1()
        mn.aload1()
        var i = 0
        c.points!!.forEach {
            mn.ldc(i)
            i++

            mn.new(sig(Vector2f::class))
            mn.dup()
            mn.ldc(it[0].toFloat())
            mn.ldc(it[1].toFloat())
            mn.invokespecial(sig(Vector2f::class), INIT, sigf(Nothing::class, Float::class, Float::class))

            mn.aastore()
            mn.aload1()
        }
        mn.visitMethodInsn(INVOKESTATIC, KT_KOLLECTIONS, "mutableListOf", sigf(List::class, Array<Any>::class), false)

        buildFilter(mn, c.filter)

        mn.invokespecial(sig(GeometryLineElement::class), INIT, sigf(Nothing::class, String::class, Float::class, Vector4f::class, List::class, FilterBase::class))
    }

    private fun buildGroupElement(mn: MethodNode, c: GroupComponent, n: String) {
        mn.new(sig(GroupElement::class))
        mn.dup()

        mn.ldc(n)

        mn.ldc(c.components?.size?: 0)
        mn.anewarray(sig(RealElement::class))
        mn.astore0()
        mn.aload0()

        var i = 0
        c.components?.forEach { (t, u) ->
            mn.ldc(i)
            i++
            when (u?.type) {
                TEXT -> buildTextElement(mn, u as TextComponent, t)
                GROUP -> buildGroupElement(mn, u as GroupComponent, t)
                RECTANGLE -> buildRectangleElement(mn, u as RectangleComponent, t)
                GEOMETRY_LINE -> buildGeometryLineElement(mn, u as GeometryLineComponent, t)
                LIVE2D -> buildLive2DElement(mn, u as Live2DComponent, t)
                null -> throw NullPointerException()
            }
            mn.aastore()
            mn.aload0()
        }
        mn.visitMethodInsn(INVOKESTATIC, KT_KOLLECTIONS, "mutableListOf", sigf(List::class, Array<Any>::class), false)
        mn.invokespecial(sig(GroupElement::class), INIT, sigf(Nothing::class, String::class, List::class))
    }

    private fun buildLive2DElement(mn: MethodNode, c: Live2DComponent, n: String) {
        mn.new(sig(Live2DElement::class))
        mn.dup()

        mn.ldc(n)

        mn.new(sig(Vector2f::class))
        mn.dup()
        mn.ldc(c.pos!![0])
        mn.ldc(c.pos!![1])
        mn.invokespecial(sig(Vector2f::class), INIT, sigf(Nothing::class, Float::class, Float::class))

        mn.new(sig(Vector2f::class))
        mn.dup()
        mn.ldc(c.size!![0])
        mn.ldc(c.size!![1])
        mn.invokespecial(sig(Vector2f::class), INIT, sigf(Nothing::class, Float::class, Float::class))

        mn.new(sig(Vector4f::class))
        mn.dup()
        mn.ldc(c.color!![0])
        mn.ldc(c.color!![1])
        mn.ldc(c.color!![2])
        mn.ldc(c.color!![3])
        mn.invokespecial(sig(Vector4f::class), INIT, sigf(Nothing::class, Float::class, Float::class, Float::class, Float::class))

        mn.ldc(c.radius?: 0f)
        mn.ldc(c.thickness?: 0f)
        mn.ldc(c.smoothedge?: 0f)

        mn.new(sig(Vector4f::class))
        mn.dup()
        mn.ldc(c.textureUV?.get(0)?: 0f)
        mn.ldc(c.textureUV?.get(1)?: 1f)
        mn.ldc(c.textureUV?.get(2)?: 0f)
        mn.ldc(c.textureUV?.get(3)?: 1f)
        mn.invokespecial(sig(Vector4f::class), INIT, sigf(Nothing::class, Float::class, Float::class, Float::class, Float::class))

        mn.new(sig(Pair::class))
        mn.dup()
        mn.ldc(c.modelName!!)
        mn.ldc(c.modelPath!!)
        mn.invokespecial(sig(Pair::class), INIT, sigf(Nothing::class, Any::class, Any::class))

        buildFilter(mn, c.filter)

        mn.invokespecial(
            sig(Live2DElement::class),
            INIT,
            sigf(Nothing::class, String::class, Vector2f::class, Vector2f::class, Vector4f::class, Float::class, Float::class, Float::class, Vector4f::class, Pair::class, FilterBase::class)
        )
    }

    private fun buildRectangleElement(mn: MethodNode, c: RectangleComponent, n: String) {
        mn.new(sig(RectangleElement::class))
        mn.dup()

        mn.ldc(n)

        mn.new(sig(Vector2f::class))
        mn.dup()
        mn.ldc(c.pos!![0])
        mn.ldc(c.pos!![1])
        mn.invokespecial(sig(Vector2f::class), INIT, sigf(Nothing::class, Float::class, Float::class))

        mn.new(sig(Vector2f::class))
        mn.dup()
        mn.ldc(c.size!![0])
        mn.ldc(c.size!![1])
        mn.invokespecial(sig(Vector2f::class), INIT, sigf(Nothing::class, Float::class, Float::class))

        mn.new(sig(Vector4f::class))
        mn.dup()
        mn.ldc(c.color!![0])
        mn.ldc(c.color!![1])
        mn.ldc(c.color!![2])
        mn.ldc(c.color!![3])
        mn.invokespecial(sig(Vector4f::class), INIT, sigf(Nothing::class, Float::class, Float::class, Float::class, Float::class))

        mn.ldc(c.radius?: 0f)
        mn.ldc(c.thickness?: 0f)
        mn.ldc(c.smoothedge?: 0f)

        mn.new(sig(Vector4f::class))
        mn.dup()
        mn.ldc(c.textureUV?.get(0)?: 0f)
        mn.ldc(c.textureUV?.get(1)?: 1f)
        mn.ldc(c.textureUV?.get(2)?: 0f)
        mn.ldc(c.textureUV?.get(3)?: 1f)
        mn.invokespecial(sig(Vector4f::class), INIT, sigf(Nothing::class, Float::class, Float::class, Float::class, Float::class))

        if (c.texture != null) {
            mn.ldc(c.texture!!)
            mn.visitMethodInsn(
                INVOKESTATIC,
                "com/primogemstudio/advancedfmk/kui/yaml/jvm/YamlCompilerKt",
                "createResourceLocation",
                sigf(ResourceLocation::class, String::class),
                false
            )
        } else mn.aconst_null()

        buildFilter(mn, c.filter)

        mn.invokespecial(
            sig(RectangleElement::class),
            INIT,
            sigf(Nothing::class, String::class, Vector2f::class, Vector2f::class, Vector4f::class, Float::class, Float::class, Float::class, Vector4f::class, ResourceLocation::class, FilterBase::class)
        )
    }

    private fun buildTextElement(mn: MethodNode, c: TextComponent, n: String) {
        mn.new(sig(TextElement::class))
        mn.dup()
        mn.ldc(n)

        mn.new(sig(Vector2f::class))
        mn.dup()
        mn.ldc(c.pos!![0])
        mn.ldc(c.pos!![1])
        mn.invokespecial(sig(Vector2f::class), INIT, sigf(Nothing::class, Float::class, Float::class))

        mn.ldc(c.text?: "<null>")

        mn.new(sig(Vector4f::class))
        mn.dup()
        mn.ldc(c.color!![0])
        mn.ldc(c.color!![1])
        mn.ldc(c.color!![2])
        mn.ldc(c.color!![3])
        mn.invokespecial(sig(Vector4f::class), INIT, sigf(Nothing::class, Float::class, Float::class, Float::class, Float::class))

        mn.ldc(c.textsize?: 12)
        mn.ldc(c.vanilla?: false)

        mn.invokespecial(sig(TextElement::class), INIT, sigf(Nothing::class, String::class, Vector2f::class, String::class, Vector4f::class, Int::class, Boolean::class))
    }

    private fun buildFilter(mn: MethodNode, f: Map<String, String>?) {
        if (f != null) {
            when (f["type"]) {
                "clip" -> {
                    mn.new(sig(ClipFilter::class))
                    mn.invokespecial(sig(ClipFilter::class), INIT, sigf(Nothing::class))
                    mn.checkcast(sig(FilterBase::class))
                }
                "post" -> {
                    mn.new(sig(PostShaderFilter::class))
                    mn.dup()
                    mn.visitMethodInsn(
                        INVOKESTATIC,
                        sig(ShaderEffectManager::class),
                        "getInstance",
                        sigf(ShaderEffectManager::class),
                        true
                    )
                    mn.ldc(f["location"] as String)
                    mn.visitMethodInsn(
                        INVOKESTATIC,
                        "com/primogemstudio/advancedfmk/kui/yaml/jvm/YamlCompilerKt",
                        "createResourceLocation",
                        sigf(ResourceLocation::class, String::class),
                        false
                    )

                    mn.visitMethodInsn(
                        INVOKEINTERFACE,
                        sig(ShaderEffectManager::class),
                        "manage",
                        sigf(ManagedShaderEffect::class, ResourceLocation::class),
                        true
                    )

                    mn.ldc(f["args"] as String)
                    mn.visitMethodInsn(
                        INVOKESTATIC,
                        "com/primogemstudio/advancedfmk/kui/yaml/jvm/YamlCompilerKt",
                        "parseShaderArgs",
                        sigf(Map::class, String::class),
                        false
                    )

                    mn.invokespecial(
                        sig(PostShaderFilter::class),
                        INIT,
                        sigf(Nothing::class, ManagedShaderEffect::class, MutableMap::class)
                    )
                    mn.checkcast(sig(FilterBase::class))
                }
                else -> mn.aconst_null()
            }
        }
        else mn.aconst_null()
    }

    private fun defineClass(name: String, b: ByteArray): Class<*> {
        return super.defineClass(name, b, 0, b.size)
    }
}