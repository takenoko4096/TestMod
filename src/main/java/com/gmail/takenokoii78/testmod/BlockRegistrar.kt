package com.gmail.takenokoii78.testmod

import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour

object BlockRegistrar {
    private val registration: MutableMap<String, Block> = mutableMapOf()

    fun register(id: String, factory: (s: BlockBehaviour.Properties) -> Block, blockSettings: BlockBehaviour.Properties, itemSettings: Item.Properties): Block {
        val identifier = Identifier.fromNamespaceAndPath(TestMod.NAMESPACE, id)
        val registryKey = ResourceKey.create(Registries.BLOCK, identifier)

        val block = Blocks.register(registryKey, factory, blockSettings)
        Items.registerBlock(block, itemSettings)
        registration[id] = block
        return block
    }

    fun get(id: String): Block {
        return registration[id] ?: throw IllegalArgumentException("Invalid Identifier")
    }

    val WHITE_LEAVES: Block = register(
        id = "white_leaves",
        factory = { s ->
            return@register Block(s)
        },
        blockSettings = BlockBehaviour.Properties.of()
            .noOcclusion()
            .strength(4.0f)
            .sound(SoundType.GRASS),
        itemSettings = Item.Properties()
            .overrideDescription("block." + TestMod.NAMESPACE + ".white_leaves")
    )
}
