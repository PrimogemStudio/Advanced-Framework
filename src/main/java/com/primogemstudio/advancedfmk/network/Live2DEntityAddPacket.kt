package com.primogemstudio.advancedfmk.network

import com.primogemstudio.advancedfmk.client.MOD_ID
import com.primogemstudio.advancedfmk.mmd.entity.Live2DEntity
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerEntity

class Live2DEntityAddPacket(val packet: ClientboundAddEntityPacket, val modelName: String, val modelPath: String) : CustomPacketPayload {
    companion object {
        val TYPE = CustomPacketPayload.Type<Live2DEntityAddPacket>(
            ResourceLocation.fromNamespaceAndPath(
                MOD_ID, "live2d_entity_add"
            )
        )
        val CODEC = StreamCodec.ofMember(Live2DEntityAddPacket::write, ::Live2DEntityAddPacket)!!
    }

    fun write(buf: RegistryFriendlyByteBuf) {
        packet.write(buf)
        buf.writeUtf(modelName)
        buf.writeUtf(modelPath)
    }

    constructor(buf: RegistryFriendlyByteBuf) : this(ClientboundAddEntityPacket.STREAM_CODEC.decode(buf), buf.readUtf(), buf.readUtf())
    constructor(entity: Live2DEntity, serverEntity: ServerEntity, str: String, str2: String) : this(
        ClientboundAddEntityPacket(entity, serverEntity), str, str2
    )

    override fun type(): CustomPacketPayload.Type<out Live2DEntityAddPacket> {
        return TYPE
    }

    object Handler : ClientPlayNetworking.PlayPayloadHandler<Live2DEntityAddPacket> {
        override fun receive(pk: Live2DEntityAddPacket, context: ClientPlayNetworking.Context) {
            with(context.client()) {
                execute {
                    pk.packet.handle(context.player().connection)
                    val te = level!!.getEntity(pk.packet.id) as Live2DEntity
                    te.setModel(pk.modelName, pk.modelPath)
                }
            }
        }
    }
}