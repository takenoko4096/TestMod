package com.gmail.takenokoii78.testmod

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.ItemModelGenerators
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

object TestModClientDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()
        pack.addProvider { output: FabricDataOutput -> TestModelProvider(output) }
        pack.addProvider { output: FabricDataOutput, registryLookup: CompletableFuture<HolderLookup.Provider> -> TestLanguageProvider(output, registryLookup) }
    }

    class TestModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
        override fun generateBlockStateModels(blockModelGenerators: BlockModelGenerators) {
            blockModelGenerators.createTrivialCube(BlockRegistrar.WHITE_LEAVES)
            blockModelGenerators.createTrivialCube(BlockRegistrar.METAL_BLOCK)
        }

        override fun generateItemModels(itemModelGenerators: ItemModelGenerators) {

        }

        override fun getName(): String = "TestModelProvider"
    }

    class TestLanguageProvider(output: FabricDataOutput, registryLookup: CompletableFuture<HolderLookup.Provider>) : FabricLanguageProvider(output, "ja_jp", registryLookup) {
        override fun generateTranslations(holderLookupProvider: HolderLookup.Provider, translationBuilder: TranslationBuilder) {
            translationBuilder.add(BlockRegistrar.WHITE_LEAVES, "白めの葉っぱ")
            translationBuilder.add(BlockRegistrar.METAL_BLOCK, "謎金属ブロック")
        }
    }
}
