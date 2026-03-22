package com.gmail.takenokoii78.testmod

import io.github.takenoko4096.starlight.client.datagen.StarlightDataGenerator
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.ItemModelGenerators

object TestModClientDataGenerator : StarlightDataGenerator(TestMod) {
    override fun onInitialize(pack: FabricDataGenerator.Pack) {
        pack.addProvider { output: FabricDataOutput -> TestModelProvider(output) }
        // Upd
    }

    class TestModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
        override fun generateBlockStateModels(blockModelGenerators: BlockModelGenerators) {
            blockModelGenerators.createTrivialCube(BlockRegistrar.WHITE_LEAVES)
            blockModelGenerators.createTrivialCube(BlockRegistrar.METAL_BLOCK)
        }

        override fun generateItemModels(itemModelGenerators: ItemModelGenerators) {}

        override fun getName(): String = "TestModelProvider"
    }
}
