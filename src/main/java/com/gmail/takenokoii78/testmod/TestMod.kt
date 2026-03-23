package com.gmail.takenokoii78.testmod

import io.github.takenoko4096.starlight.StarlightModInitializer
import net.minecraft.world.level.block.SoundType

object TestMod : StarlightModInitializer() {
    override val identifier: String = "testmod"

    override fun onInitialize() {
        BlockRegistrar // load

        val prismarineLamp = blockRegistry.register("prismarine_lamp") {
            val info = customBehaviour {
                val props = blockStates {
                    booleanProperty("luminance") {
                        defaultValue = false
                    }
                }

                events {
                    onInteract {
                        val property = props.boolean("luminance")
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
                    if (it.getValue(info.properties.boolean("luminance"))) 15 else 0
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
                blockModel {
                    val unlit = models.cubeAll(defaultTexturePath)
                    val lit = models.cubeAll(defaultTexturePath.suffixed("on")) {
                        suffix = "on"
                    }

                    variants(info.properties.boolean("luminance")) {
                        unlit.toVariant()
                        unlit.toVariant().useWhen(false)
                        lit.toVariant().useWhen(true)
                    }

                    itemModel = unlit
                }

                chunkSectionLayer {
                    solid()
                }
            }
        }

        val testBlock = blockRegistry.register("test_block") {
            blockProperties {
                occlusion = false
            }

            rendering {
                blockModel {
                    trivialCube()
                }

                chunkSectionLayer {
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
