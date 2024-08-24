package com.primogemstudio.advancedfmk.mmd.entity

import com.primogemstudio.advancedfmk.live2d.Live2DModel
import com.primogemstudio.advancedfmk.network.Live2DEntityAddPacket
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class Live2DEntity(entityType: EntityType<out Entity>, level: Level) : Entity(entityType, level) {
    @Environment(EnvType.CLIENT)
    var modelName: String = ""
    @Environment(EnvType.CLIENT)
    var modelPath: String = ""
    @Environment(EnvType.CLIENT)
    var model: Live2DModel? = null

    @Environment(EnvType.CLIENT)
    fun setModel(modelName: String, modelPath: String) {
        this.modelName = modelName
        this.modelPath = modelPath

        model = Live2DModel(modelName, modelPath)
    }

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {}

    override fun readAdditionalSaveData(compound: CompoundTag) {
        modelName = compound.getString("ModelName")
        modelPath = compound.getString("ModelPath")
    }

    override fun addAdditionalSaveData(compound: CompoundTag) {
        compound.putString("ModelName", modelName)
        compound.putString("ModelPath", modelPath)
    }

    @Suppress("UNCHECKED_CAST")
    override fun getAddEntityPacket(serverEntity: ServerEntity): Packet<ClientGamePacketListener> {
        return ServerPlayNetworking.createS2CPacket(
            Live2DEntityAddPacket(
                this, serverEntity, modelName, modelPath
            )
        ) as Packet<ClientGamePacketListener>
    }
}