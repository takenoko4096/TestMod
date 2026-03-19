package com.gmail.takenokoii78.testmod

import net.fabricmc.api.ModInitializer

object TestMod : ModInitializer {
    override fun onInitialize() {
        BlockRegistrar // load
    }

    const val NAMESPACE: String = "testmod"
}
