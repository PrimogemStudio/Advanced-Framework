package com.primogemstudio.advancedfmk.client

import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.string
import com.primogemstudio.advancedfmk.mmd.Loader
import com.primogemstudio.advancedfmk.mmd.entity.TestEntityRenderer
import com.primogemstudio.advancedfmk.render.Shaders
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.Commands.argument
import net.minecraft.commands.Commands.literal
import java.io.File

class AdvancedFrameworkClient : ClientModInitializer {
    override fun onInitializeClient() {
        Shaders.init()

        CommandRegistrationCallback.EVENT.register { dis, cxt, sel ->
            dis.register(literal("advancedfmk:loadmdl")
                .then(argument("path", string()).executes {
                    val fdst = File(getString(it, "path"))
                    TestEntityRenderer.model = Loader.load(fdst.absoluteFile.parent, fdst.name).second
                    0
                })
            )
        }
    }
}
