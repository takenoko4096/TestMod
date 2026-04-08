package com.gmail.takenokoii78.testmod

import com.gmail.takenokoii78.mojangson.MojangsonParser
import com.gmail.takenokoii78.mojangson.MojangsonSerializer
import io.github.takenoko4096.starlight.DataDrivenStarlight
import io.github.takenoko4096.starlight.StarlightModInitializer
import io.github.takenoko4096.starlight.util.nbt.NbtSerializer
import io.github.takenoko4096.starlight.util.text.component
import net.minecraft.world.level.block.SoundType

object TestModInitializer : StarlightModInitializer("testmod") {
    override fun onInitialize() {
        val whiteLeaves = blockRegistry.register("white_leaves") {
            blockProperties {
                occlusion = false
                sound = SoundType.GRASS
                destroyTime = 0.5f
                explosionResistance = 0.5f
            }

            itemProperties {
                translationKeyAuto()
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
            }

            translation {
                enUs = "White Leaves"
                jaJp = "白めの葉っぱ"
            }
        }

        val metalBlock = blockRegistry.register("metal_block") {
            blockProperties {
                destroyTime = 4.0f
                explosionResistance = 4.0f
                sound = SoundType.METAL
            }

            itemProperties {
                translationKeyAuto()
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
            }

            translation {
                enUs = "Metal Block"
                jaJp = "謎金属ブロック"
            }
        }

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
                    defaultColor {
                        0x1fff80
                    }
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

        val testSword = itemRegistry.register("test_sword") {
            itemProperties {
                translationKeyAuto()

                components {
                    by(templates.sword(32, 6.5))

                    enchantable {
                        enchantmentAptitude = 3
                    }
                }
            }

            translation {
                jaJp = "テストソード"
                enUs = "Test Sword"
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

        val c = MojangsonParser.compound("""
        {
            key: value,
            ints: [I; 1, 2, 3, 4],
            strs: [a, b, "\"str'ing\""],
            nested: {
                0: 1,
                list: [a, 0d, 5L, true]
            }
        }
        """)

        commandRegistry.register("foo") {
            "raw" {
                executes {
                    context.source.sendSystemMessage(component {
                        text(MojangsonSerializer.serialize(c))
                    })
                }
            }

            "component" {
                executes {
                    context.source.sendSystemMessage(NbtSerializer.serialize(c))
                }
            }
        }
    }

    override fun onServerStart(data: DataDrivenStarlight) {
        logger.info("server started")
    }
}
