package com.gmail.takenokoii78.testmod

import io.github.takenoko4096.starlight.StarlightModInitializer
import net.minecraft.world.level.block.SoundType

object TestMod : StarlightModInitializer("testmod") {
    override fun onInitialize() {
        BlockRegistrar // load

        val prismarineLamp = blockRegistry.register("prismarine_lamp") {
            val lit = "lit"

            val info = customBehaviour {
                val properties = blockStates {
                    booleanProperty(lit) {
                        defaultValue = false
                    }
                }

                val litProperty = properties.boolean(lit)

                events {
                    onInteract {
                        val value = blockState.getValue(litProperty)
                        level.setBlockAndUpdate(blockPos, blockState.setValue(litProperty, !value))
                    }
                }
            }

            val litProperty = info.properties.boolean(lit)

            blockProperties {
                destroyTime = 0.5f
                sound = SoundType.METAL
                requiresCorrectToolForDrops = true
                lightLevel {
                    if (it.getValue(litProperty)) 15 else 0
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
                    val on = blockModels.cubeAll(blockDefaultTexturePath underscore "on") {
                        suffix = "on"
                    }

                    block {
                        variants(litProperty) {
                            off.toBlockVariant().useWhen(false)
                            on.toBlockVariant().useWhen(true)
                        }
                    }

                    item {
                        off.useAsItemModel()
                    }
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
                    val model = blockModels.cubeAll(blockDefaultTexturePath)

                    block {
                        variants {
                            model.toBlockVariant().use()
                        }
                    }
                }

                tint {
                    color = 0x1fff80
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

        val testSword = itemRegistry.register("test_sword") {
            itemProperties {
                translationKeyAuto()

                components {
                    maxStackSize(1)

                    maxDamage(32)
                    damage(0)

                    attributeModifiers {
                        attackSpeed {
                            slot {
                                weapon.mainhand()
                            }

                            operation {
                                addValue()
                            }

                            value = -3.5
                        }

                        attackDamage {
                            slot {
                                weapon.mainhand()
                            }

                            operation {
                                addValue()
                            }

                            value = 6.5
                        }
                    }

                    enchantable {
                        enchantmentAptitude = 3
                    }
                }
            }

            translation {
                jaJp = "テストアイテム"
            }

            rendering {
                model {
                    val model = itemModels.handheld(itemDefaultTexturePath)

                    handling {
                        use(model)
                    }
                }
            }
        }
    }

    const val NAMESPACE: String = "testmod"
}
