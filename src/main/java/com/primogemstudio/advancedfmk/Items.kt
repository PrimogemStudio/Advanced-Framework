package com.primogemstudio.advancedfmk

import com.primogemstudio.advancedfmk.AdvancedFramework.Companion.MOD_ID
import com.primogemstudio.advancedfmk.screen.TestSnakeDualScreen
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.*
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.TransparentBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.phys.BlockHitResult

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
                tooltipComponents.add(Component.literal("启动!").withColor(0xFFFF00))
            }})
        Blocks.GLASS
        object: TransparentBlock(
            Properties.of().instrument(NoteBlockInstrument.HAT).strength(0.3f).sound(
                SoundType.GLASS
            ).noOcclusion()
                .isValidSpawn { state: BlockState, blockGetter: BlockGetter, pos: BlockPos, entity: EntityType<*> ->
                    Blocks.never(
                        state,
                        blockGetter,
                        pos,
                        entity
                    )
                }.isRedstoneConductor { state: BlockState, blockGetter: BlockGetter, pos: BlockPos ->
                    Blocks.never(
                        state,
                        blockGetter,
                        pos
                    )
                }.isSuffocating { state: BlockState, blockGetter: BlockGetter, pos: BlockPos ->
                    Blocks.never(
                        state,
                        blockGetter,
                        pos
                    )
                }.isViewBlocking { state: BlockState, blockGetter: BlockGetter, pos: BlockPos ->
                Blocks.never(
                    state,
                    blockGetter,
                    pos
                )
            }) {
            override fun useWithoutItem(
                state: BlockState,
                level: Level,
                pos: BlockPos,
                player: Player,
                hitResult: BlockHitResult
            ): InteractionResult {
                if (level.isClientSide) {
                    Minecraft.getInstance().setScreen(TestSnakeDualScreen())
                    return InteractionResult.SUCCESS
                }
                else return InteractionResult.SUCCESS
            }
        }.apply {
            Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, "test_block"), this)
            Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "test_block_item"), BlockItem(this, Item.Properties()))
        }

        Blocks.GLASS
    }
}
