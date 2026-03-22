package com.gmail.takenokoii78.testmod

import io.github.takenoko4096.starlight.client.StarlightClient
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap
import net.fabricmc.fabric.impl.client.rendering.BlockRenderLayerMapImpl
import net.minecraft.client.renderer.chunk.ChunkSectionLayer

object TestModClient : StarlightClient(TestMod) {
    override fun onInitialize() {
        BlockRenderLayerMap.putBlock(BlockRegistrar.WHITE_LEAVES, ChunkSectionLayer.CUTOUT)
    }
}
