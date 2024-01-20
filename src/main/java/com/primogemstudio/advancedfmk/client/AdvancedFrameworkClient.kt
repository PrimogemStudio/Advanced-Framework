package com.primogemstudio.advancedfmk.client

import com.mojang.brigadier.arguments.BoolArgumentType.bool
import com.mojang.brigadier.arguments.BoolArgumentType.getBool
import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.string
import com.mojang.brigadier.context.CommandContext
import com.primogemstudio.advancedfmk.mmd.Loader
import com.primogemstudio.advancedfmk.mmd.entity.TestEntityRenderer
import com.primogemstudio.advancedfmk.render.Shaders
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.network.chat.*
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter


class AdvancedFrameworkClient : ClientModInitializer {
    override fun onInitializeClient() {
        Shaders.init()
        ClientCommandRegistrationCallback.EVENT.register { dis, cxt ->
            dis.register(
                literal("advancedfmk:loadmdl")
                    .then(argument("path", string()).executes { c ->
                        try {
                            val st = System.currentTimeMillis()
                            val fdst = File(getString(c, "path"))
                            TestEntityRenderer.switchModel(Loader.load(fdst.absoluteFile.parent, fdst.name).second)
                            val ed = System.currentTimeMillis()
                            println("Model loading complete, time: ${ed - st} ms")
                        }
                        catch (e: Throwable) {
                            e.fullMsg(c)
                            return@executes 1
                        }
                        0
                    })
            )

            dis.register(
                literal("advancedfmk:pipeline")
                    .then(argument("vanilla", bool()).executes { c ->
                        try {
                            TestEntityRenderer.switchPipeline(getBool(c, "vanilla"))
                        }
                        catch (e: Throwable) {
                            e.fullMsg(c)
                            return@executes 1
                        }
                        0
                    })
            )
        }
    }
}

fun Throwable.fullMsg(c: CommandContext<FabricClientCommandSource>) {
    val sw = StringWriter()
    val pw = PrintWriter(sw)
    this.printStackTrace(pw)
    c.source.client.gui.chat.addMessage(Component.literal(sw.toString()).withStyle(Style.EMPTY.withColor(0xEF5767)))
}