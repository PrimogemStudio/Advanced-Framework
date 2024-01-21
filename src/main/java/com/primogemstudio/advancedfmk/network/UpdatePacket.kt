package com.primogemstudio.advancedfmk.network

import com.primogemstudio.advancedfmk.AdvancedFramework
import com.primogemstudio.advancedfmk.mmd.entity.TestEntity
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientPacketListener
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player

class UpdatePacket: ClientPlayNetworking.PlayChannelHandler {
    companion object {
        private val map = Int2ObjectOpenHashMap<String>()
        init {
            ClientEntityEvents.ENTITY_LOAD.register { ent, _ ->
                if (ent is TestEntity && map.containsKey(ent.id)) {
                    ent.setModel(map[ent.id])
                    map.remove(ent.id)
                }
            }
        }

        fun flush() {
            val level = Minecraft.getInstance().level ?: return
            map.int2ObjectEntrySet().forEach {
                with (level.getEntity(it.intKey)) {
                    if (this is TestEntity) setModel(it.value)
                }
            }
            map.clear()
        }

        fun send(entity: TestEntity, path: String?) {
            val buf = PacketByteBufs.create()
            buf.writeInt(entity.id)
            buf.writeUtf(path?: "")
            entity.level().players().forEach { p: Player? ->
                ServerPlayNetworking.send(
                    p as ServerPlayer?,
                    ResourceLocation(AdvancedFramework.MOD_ID, "update"),
                    buf
                )
            }
        }
    }

    override fun receive(
        client: Minecraft?,
        handler: ClientPacketListener?,
        buf: FriendlyByteBuf?,
        responseSender: PacketSender?
    ) {
        val id = buf?.readInt()
        val path = buf?.readUtf()
        map[id] = path
    }
}