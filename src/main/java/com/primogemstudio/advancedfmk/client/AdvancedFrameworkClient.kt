package com.primogemstudio.advancedfmk.client

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.primogemstudio.advancedfmk.AdvancedFramework.Companion.MOD_ID
import com.primogemstudio.advancedfmk.mmd.SabaNative
import com.primogemstudio.advancedfmk.mmd.entity.TestEntity
import com.primogemstudio.advancedfmk.network.Live2DEntityAddPacket
import com.primogemstudio.advancedfmk.network.TestEntityAddPacket
import com.primogemstudio.advancedfmk.network.UpdatePacket
import com.primogemstudio.advancedfmk.util.NativeFileDialog
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

class AdvancedFrameworkClient : ClientModInitializer {
    override fun onInitializeClient() {
        PayloadTypeRegistry.playS2C().register(UpdatePacket.TYPE, UpdatePacket.CODEC)
        PayloadTypeRegistry.playS2C().register(TestEntityAddPacket.TYPE, TestEntityAddPacket.CODEC)
        PayloadTypeRegistry.playS2C().register(Live2DEntityAddPacket.TYPE, Live2DEntityAddPacket.CODEC)
        ClientPlayNetworking.registerGlobalReceiver(UpdatePacket.TYPE, UpdatePacket.Handler)
        ClientPlayNetworking.registerGlobalReceiver(TestEntityAddPacket.TYPE, TestEntityAddPacket.Handler)
        ClientPlayNetworking.registerGlobalReceiver(Live2DEntityAddPacket.TYPE, Live2DEntityAddPacket.Handler)
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
                            if (e is TestEntity && e.wrap != null) {
                                e.wrap!!.model.animation.add(model)
                                e.wrap!!.model.animation.setupAnimation()
                            }
                        }
                        0
                    }.then(argument("path", StringArgumentType.string()).executes {
                        it.source.world.entitiesForRendering().forEach { e ->
                            if (e is TestEntity && e.wrap != null) {
                                e.wrap!!.model.animation.add(File(StringArgumentType.getString(it, "path")))
                                e.wrap!!.model.animation.setupAnimation()
                            }
                        }
                        0
                    })).then(literal("clear").executes {
                        it.source.world.entitiesForRendering().forEach { e ->
                            if (e is TestEntity && e.wrap != null) {
                                e.wrap!!.model.animation.clear()
                                e.wrap!!.model.animation.setupAnimation()
                            }
                        }
                        0
                    })).then(literal("attach").executes {
                        val model = NativeFileDialog.openFileDialog(
                            "打开",
                            "D:/",
                            arrayOf("*.dll", "*.so", "*.dylib"),
                            "Dynamic lib files"
                        )
                        model ?: return@executes 0
                        try {
                            System.load(model.absolutePath)
                        }
                        catch (e: Exception) {
                            e.fullMsg(it)
                        }
                        0
                    }).then(literal("load_live2d").then(argument("name", StringArgumentType.string()).then(argument("path", StringArgumentType.string()).executes {
                        it.source.player.connection.sendCommand(
                            "summon ${MOD_ID}:live2d_entity ~ ~ ~ {ModelName:\"${StringArgumentType.getString(it, "name")}\",ModelPath:\"${StringArgumentType.getString(it, "path")}\"}"
                        )
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