package com.gmail.takenokoii78.testmod

import net.fabricmc.api.ModInitializer

object TestMod : ModInitializer {
    override fun onInitialize() {
        BlockRegistrar.WHITE_LEAVES // load
    }

    const val NAMESPACE: String = "testmod"
}
