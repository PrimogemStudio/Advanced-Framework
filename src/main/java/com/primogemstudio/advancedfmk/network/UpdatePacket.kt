package com.primogemstudio.advancedfmk.network

import com.primogemstudio.advancedfmk.client.MOD_ID
import com.primogemstudio.advancedfmk.mmd.entity.TestEntity
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.client.Minecraft
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player

class UpdatePacket(private val id: Int, private val path: String) : CustomPacketPayload {
    companion object {
        private val map = Int2ObjectOpenHashMap<String>()
        val TYPE = CustomPacketPayload.Type<UpdatePacket>(ResourceLocation(MOD_ID, "update"))
        val CODEC = StreamCodec.ofMember(UpdatePacket::write, ::UpdatePacket)!!

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
                with(level.getEntity(it.intKey)) {
                    if (this is TestEntity) setModel(it.value)
                }
            }
            map.clear()
        }

        fun send(entity: TestEntity, path: String?) {
            entity.level().players().forEach { p: Player? ->
                ServerPlayNetworking.send(
                    p as ServerPlayer?, UpdatePacket(entity.id, path ?: "")
                )
            }
        }
    }

    fun write(buf: FriendlyByteBuf) {
        buf.writeInt(id)
        buf.writeUtf(path)
    }

    constructor(buf: FriendlyByteBuf) : this(buf.readInt(), buf.readUtf())


    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return TYPE
    }

    object Handler : ClientPlayNetworking.PlayPayloadHandler<UpdatePacket> {
        override fun receive(pk: UpdatePacket, context: ClientPlayNetworking.Context) {
            map[pk.id] = pk.path
        }
    }
}