package com.primogemstudio.advancedfmk.client

import com.mojang.brigadier.arguments.BoolArgumentType.bool
import com.mojang.brigadier.arguments.BoolArgumentType.getBool
import com.mojang.brigadier.context.CommandContext
import com.primogemstudio.advancedfmk.AdvancedFramework.Companion.MOD_ID
import com.primogemstudio.advancedfmk.mmd.SabaNative
import com.primogemstudio.advancedfmk.mmd.entity.TestEntity
import com.primogemstudio.advancedfmk.mmd.entity.TestEntityRenderer
import com.primogemstudio.advancedfmk.network.UpdatePacket
import com.primogemstudio.advancedfmk.util.NativeFileDialog
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.resources.ResourceLocation
import java.io.PrintWriter
import java.io.StringWriter


class AdvancedFrameworkClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(ResourceLocation(MOD_ID, "update"), UpdatePacket())
        TestEntity.registerPacket()
        ClientCommandRegistrationCallback.EVENT.register { dis, _ ->
            dis.register(literal("advancedfmk").then(
                literal("pipeline").then(argument(
                    "vanilla", bool()
                ).executes { c ->
                    try {
                        TestEntityRenderer.switchPipeline(getBool(c, "vanilla"))
                    } catch (e: Throwable) {
                        e.fullMsg(c)
                        return@executes 1
                    }
                    0
                })
            ).then(
                literal("compat").then(argument(
                    "enable", bool()
                ).executes { c ->
                    try {
                        TestEntityRenderer.switchCompatibility(getBool(c, "enable"))
                    } catch (e: Throwable) {
                        e.fullMsg(c)
                        return@executes 1
                    }
                    0
                })
            ).then(literal("flush").executes { UpdatePacket.flush();0 }).then(literal("open").executes {
                val model = NativeFileDialog.openFileDialog("打开", "D:/", arrayOf("*.pmx"), "PMX Model")
                it.source.player.connection.sendCommand(
                    "summon ${MOD_ID}:test_entity ~ ~ ~ {Model:\"${
                        model?.absolutePath?.replace(
                            '\\', '/'
                        )
                    }\"}"
                )
                0
            }).then(literal("animation").then(literal("load").executes {
                val model = NativeFileDialog.openFileDialog("打开", "D:/", arrayOf("*.vmd"), "VMD File")
                it.source.world.entitiesForRendering().forEach { e ->
                    if (e is TestEntity && e.model != null) {
                        e.model!!.animation.add(model)
                        e.model!!.animation.setupAnimation()
                    }
                }
                0
            }).then(literal("clear").executes {
                it.source.world.entitiesForRendering().forEach { e ->
                    if (e is TestEntity && e.model != null) {
                        e.model!!.animation.clear()
                        e.model!!.animation.setupAnimation()
                    }
                }
                0
            })).then(literal("opt").then(literal("render_model").then(argument("enable", bool()).executes { c ->
                TestEntityRenderer.render_model = getBool(c, "enable")
                0
            })).then(literal("render_bone_link").then(argument("enable", bool()).executes { c ->
                TestEntityRenderer.render_bone_link = getBool(c, "enable")
                0
            })).then(literal("render_bone_parent").then(argument("enable", bool()).executes { c ->
                TestEntityRenderer.render_bone_parent = getBool(c, "enable")
                0
            })).then(literal("render_normals").then(argument("enable", bool()).executes { c ->
                TestEntityRenderer.render_normals = getBool(c, "enable")
                0
            })))
            )
        }
        SabaNative.init()
    }
}

fun Throwable.fullMsg(c: CommandContext<FabricClientCommandSource>) {
    val sw = StringWriter()
    val pw = PrintWriter(sw)
    this.printStackTrace(pw)
    c.source.client.gui.chat.addMessage(Component.literal(sw.toString()).withStyle(Style.EMPTY.withColor(0xEF5767)))
}