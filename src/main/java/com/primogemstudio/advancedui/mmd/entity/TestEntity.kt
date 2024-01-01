package com.primogemstudio.advancedui.mmd.entity

import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobType
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.level.Level

class TestEntity(entityType: EntityType<out Entity>, level: Level) : Entity(entityType, level) {
    override fun defineSynchedData() {

    }

    override fun readAdditionalSaveData(compound: CompoundTag) {

    }

    override fun addAdditionalSaveData(compound: CompoundTag) {

    }

    override fun getAddEntityPacket(): Packet<ClientGamePacketListener> {
        return ClientboundAddEntityPacket(this)
    }
}