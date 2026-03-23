package com.gmail.takenokoii78.testmod

import io.github.takenoko4096.starlight.StarlightModInitializer
import net.minecraft.world.level.block.SoundType

object TestMod : StarlightModInitializer() {
    override val identifier: String = "testmod"

    override fun onInitialize() {
        BlockRegistrar // load

        val prismarineLamp = blockRegistry.register("prismarine_lamp") {
            val litProperty = "lit"

            val info = customBehaviour {
                val properties = blockStates {
                    booleanProperty(litProperty) {
                        defaultValue = false
                    }
                }

                events {
                    onInteract {
                        properties.boolean(litProperty)
                        val property = properties.boolean(litProperty)
                        val value = blockState.getValue(property)
                        level.setBlockAndUpdate(blockPos, blockState.setValue(property, !value))
                    }
                }
            }

            blockProperties {
                destroyTime = 0.5f
                sound = SoundType.METAL
                requiresCorrectToolForDrops = true
                lightLevel {
                    if (it.getValue(info.properties.boolean(litProperty))) 15 else 0
                }
            }

            itemProperties {
                translationKeyAuto()
            }

            translation {
                jaJp = "プリズマリンランプ"
                enUs = "Prismarine Lamp"
            }

            rendering {
                models {
                    val off = blockModels.cubeAll(blockDefaultTexturePath)
                    val on = blockModels.cubeAll(blockDefaultTexturePath.suffixed("on")) {
                        suffix = "on"
                    }

                    block {
                        variants(info.properties.boolean(litProperty)) {
                            off.toVariant().useWhen(false)
                            on.toVariant().useWhen(true)
                        }
                    }

                    item {
                        off.useAsItemModel()
                    }
                }

                tint {
                    // provide { pos, state, getter -> 0 }
                }

                layer {
                    solid()
                }
            }
        }

        val testBlock = blockRegistry.register("test_block") {
            blockProperties {
                occlusion = false
            }

            rendering {
                models {
                    block {
                        trivialCube()
                    }
                }

                layer {
                    translucent()
                }
            }

            itemProperties {
                translationKeyAuto()
            }

            translation {
                jaJp = "テストブロック"
                enUs = "Test Block"
            }
        }

        translationRegistry.register(BlockRegistrar.WHITE_LEAVES.descriptionId) {
            enUs = "White Leaves"
            jaJp = "白めの葉っぱ"
        }

        translationRegistry.register(BlockRegistrar.METAL_BLOCK.descriptionId) {
            enUs = "Metal Block"
            jaJp = "謎金属ブロック"
        }
    }

    const val NAMESPACE: String = "testmod"
}
