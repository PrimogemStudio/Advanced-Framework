package com.primogemstudio.advancedfmk.network

import com.primogemstudio.advancedfmk.client.MOD_ID
import com.primogemstudio.advancedfmk.mmd.entity.TestEntity
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.resources.ResourceLocation

class TestEntityAddPacket(val packet: ClientboundAddEntityPacket, val path: String) : CustomPacketPayload {
    companion object {
        val TYPE = CustomPacketPayload.Type<TestEntityAddPacket>(ResourceLocation(MOD_ID, "test_entity_add"))
        val CODEC = StreamCodec.ofMember(TestEntityAddPacket::write, ::TestEntityAddPacket)!!
    }

    fun write(buf: RegistryFriendlyByteBuf) {
        packet.write(buf)
        buf.writeUtf(path)
    }

    constructor(buf: RegistryFriendlyByteBuf) : this(ClientboundAddEntityPacket.STREAM_CODEC.decode(buf), buf.readUtf())
    constructor(entity: TestEntity, path: String) : this(ClientboundAddEntityPacket(entity), path)

    override fun type(): CustomPacketPayload.Type<out TestEntityAddPacket> {
        return TYPE
    }

    object Handler : ClientPlayNetworking.PlayPayloadHandler<TestEntityAddPacket> {
        override fun receive(pk: TestEntityAddPacket, context: ClientPlayNetworking.Context) {
            with(context.client()) {
                execute {
                    pk.packet.handle(context.player().connection)
                    val te = level!!.getEntity(pk.packet.id) as TestEntity
                    te.setModel(pk.path)
                }
            }
        }
    }
}