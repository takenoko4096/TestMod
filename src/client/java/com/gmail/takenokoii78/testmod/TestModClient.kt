package com.gmail.takenokoii78.testmod

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap
import net.minecraft.client.renderer.chunk.ChunkSectionLayer

object TestModClient : ClientModInitializer {
    override fun onInitializeClient() {
        BlockRenderLayerMap.putBlock(BlockRegistrar.WHITE_LEAVES, ChunkSectionLayer.CUTOUT)
    }
}
