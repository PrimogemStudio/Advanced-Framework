package com.primogemstudio.advancedfmk

import com.primogemstudio.advancedfmk.AdvancedFramework.Companion.MOD_ID
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag

object Items {
    fun register() {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "test_item"), object:
            Item(Properties().rarity(Rarity.EPIC)) {
            override fun appendHoverText(
                stack: ItemStack,
                context: TooltipContext,
                tooltipComponents: MutableList<Component>,
                tooltipFlag: TooltipFlag
            ) {
                tooltipComponents.add(Component.literal("启动!"))
            }})
    }
}
