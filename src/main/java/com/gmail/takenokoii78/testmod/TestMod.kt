package com.gmail.takenokoii78.testmod

import io.github.takenoko4096.starlight.StarlightModInitializer
import net.minecraft.world.level.block.SoundType

object TestMod : StarlightModInitializer() {
    override val identifier: String = "testmod"

    override fun onInitialize() {
        BlockRegistrar // load

        val prismarineLamp = blockRegistry.register("prismarine_lamp") {
            val info = customBehaviour {
                blockStates {
                    booleanProperty("luminance") {
                        defaultValue = false
                    }
                }
            }

            blockProperties {
                sound = SoundType.METAL
                requiresCorrectToolForDrops = true
                lightLevel {
                    if (it.getValue(info.properties.boolean("luminance"))) 15 else 0
                }
            }

            itemProperties {
                translationKeyAuto()
            }

            translation {
                jaJp = "プリズマリンランプ"
            }

            rendering {
                blockModel {
                    val unlit = models.cubeAll(defaultTexturePath) {}
                    val lit = models.cubeAll(defaultTexturePath.suffixed("on")) {
                        suffix="on"
                    }

                    propertyVariants(info.properties.boolean("luminance")) {
                        unlit.plain().useWhen(false)
                        lit.plain().useWhen(true)
                    }

                    unlit.setToDefaultItemModel()
                }

                chunkSectionLayer {
                    solid()
                }
            }
        }
    }

    const val NAMESPACE: String = "testmod"
}
