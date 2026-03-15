package com.gmail.takenokoii78.testmod.mixin

import net.minecraft.server.MinecraftServer
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

// https://github.com/SpongePowered/Mixin/issues/245
@Mixin(MinecraftServer::class)
class ExampleMixin {
    @Inject(at = [At("HEAD")], method = ["loadLevel"])
    private fun init(info: CallbackInfo) {
        // This code is injected into the start of MinecraftServer.loadLevel()V
    }
}
