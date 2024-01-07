package com.primogemstudio.advancedfmk.mmd.entity

import com.primogemstudio.advancedfmk.AdvancedFramework.MOD_ID
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory

object Entities {
    fun register() {
        val entity = Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation(MOD_ID, "test_entity"),
            EntityType.Builder.of(
                { entityType, level -> TestEntity(entityType, level) },
                MobCategory.MISC
            ).build("test_entity"))
        if (FabricLoader.getInstance().environmentType == EnvType.CLIENT) EntityRendererRegistry.register(entity) { TestEntityRenderer(it) }
    }
}