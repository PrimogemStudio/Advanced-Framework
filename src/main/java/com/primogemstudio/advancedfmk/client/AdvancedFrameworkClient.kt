package com.primogemstudio.advancedfmk.client

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.primogemstudio.advancedfmk.AdvancedFramework.Companion.MOD_ID
import com.primogemstudio.advancedfmk.mmd.SabaNative
import com.primogemstudio.advancedfmk.mmd.entity.TestEntity
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
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter


class AdvancedFrameworkClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(ResourceLocation(MOD_ID, "update"), UpdatePacket())
        TestEntity.registerPacket()
        ClientCommandRegistrationCallback.EVENT.register { dis, _ ->
            dis.register(
                literal("advancedfmk").then(literal("flush").executes { UpdatePacket.flush();0 })
                    .then(literal("open").executes {
                        val model = NativeFileDialog.openFileDialog("打开", "D:/", arrayOf("*.pmx"), "PMX Model")
                        it.source.player.connection.sendCommand(
                            "summon ${MOD_ID}:test_entity ~ ~ ~ {Model:\"${
                                model?.absolutePath?.replace(
                                    '\\', '/'
                                )
                            }\"}"
                        )
                        0
                    }.then(argument("path", StringArgumentType.string()).executes {
                        it.source.player.connection.sendCommand(
                            "summon ${MOD_ID}:test_entity ~ ~ ~ {Model:\"${
                                File(StringArgumentType.getString(it, "path")).absolutePath.replace(
                                    '\\', '/'
                                )
                            }\"}"
                        )
                        0
                    })).then(literal("animation").then(literal("load").executes {
                        val model = NativeFileDialog.openFileDialog("打开", "D:/", arrayOf("*.vmd"), "VMD File")
                        model ?: return@executes 0
                        it.source.world.entitiesForRendering().forEach { e ->
                            if (e is TestEntity && e.model != null) {
                                e.model!!.animation.add(model)
                                e.model!!.animation.setupAnimation()
                            }
                        }
                        0
                    }.then(argument("path", StringArgumentType.string()).executes {
                        it.source.world.entitiesForRendering().forEach { e ->
                            if (e is TestEntity && e.model != null) {
                                e.model!!.animation.add(File(StringArgumentType.getString(it, "path")))
                                e.model!!.animation.setupAnimation()
                            }
                        }
                        0
                    })).then(literal("clear").executes {
                        it.source.world.entitiesForRendering().forEach { e ->
                            if (e is TestEntity && e.model != null) {
                                e.model!!.animation.clear()
                                e.model!!.animation.setupAnimation()
                            }
                        }
                        0
                    })).then(literal("attach").executes {
                        val model = NativeFileDialog.openFileDialog("打开", "D:/", arrayOf("*.vmd"), "VMD File")
                        model ?: return@executes 0
                        try {
                            System.load(model.absolutePath)
                        } catch (e: Exception) {
                            e.fullMsg(it)
                        }
                        0
                    })
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