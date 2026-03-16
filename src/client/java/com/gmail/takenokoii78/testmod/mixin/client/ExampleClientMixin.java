package com.gmail.takenokoii78.testmod.mixin.client;

import net.minecraft.client.Minecraft;
import org.jspecify.annotations.NullMarked;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// https://github.com/SpongePowered/Mixin/issues/245
@Mixin(Minecraft.class)
@NullMarked
public abstract class ExampleClientMixin {
    @Inject(at = {@At("HEAD")}, method = {"run"})
    private void init(CallbackInfo info) {
        // This code is injected into the start of Minecraft.run()V
    }
}
