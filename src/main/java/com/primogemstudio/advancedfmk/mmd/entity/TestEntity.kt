package com.primogemstudio.advancedfmk.mmd.entity

import com.primogemstudio.advancedfmk.AdvancedFramework.Companion.MOD_ID
import com.primogemstudio.advancedfmk.mmd.PMXModel
import com.primogemstudio.advancedfmk.mmd.renderer.EntityRenderWrapper
import com.primogemstudio.advancedfmk.network.UpdatePacket
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

class TestEntity(entityType: EntityType<out Entity>, level: Level) : Entity(entityType, level) {
    @Environment(EnvType.CLIENT)
    @JvmField
    var wrap: EntityRenderWrapper? = null

    @Environment(EnvType.CLIENT)
    private var processed: Array<FloatArray>? = null
    private var mp = ""

    private var modelName = ""

    @Environment(EnvType.CLIENT)
    fun setModel(path: String) {
        if (Files.exists(Path(path))) {
            val file = File(path)
            modelName = file.name.replace(Regex("[^a-zA-Z0-9]+"), "_")
            wrap = EntityRenderWrapper(PMXModel(file))
            processed = null
        }
    }

    override fun defineSynchedData() {
    }

    override fun readAdditionalSaveData(compound: CompoundTag) {
        val path = compound.getString("Model")
        mp = path
    }

    override fun addAdditionalSaveData(compound: CompoundTag) {
        compound.putString("Model", mp)
    }

    fun updateModel(path: String) {
        mp = path
        UpdatePacket.send(this, path)
    }

    companion object {
        @Environment(EnvType.CLIENT)
        fun registerPacket() {
            ClientPlayNetworking.registerGlobalReceiver(
                ResourceLocation(
                    MOD_ID, "test_entity_add"
                )
            ) { client, handler, buf, _ ->
                val p = ClientboundAddEntityPacket(buf)
                val path = buf.readUtf()
                client.execute {
                    p.handle(handler)
                    val te = client.level!!.getEntity(p.id) as TestEntity
                    te.setModel(path)
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getAddEntityPacket(): Packet<ClientGamePacketListener> {
        val buf = PacketByteBufs.create()
        ClientboundAddEntityPacket(this).write(buf)
        buf.writeUtf(mp)
        return ServerPlayNetworking.createS2CPacket(
            ResourceLocation(MOD_ID, "test_entity_add"), buf
        ) as Packet<ClientGamePacketListener>
    }
}