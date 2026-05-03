package com.gmail.takenokoii78.testmod

import io.github.takenoko4096.starlight.DataDrivenStarlight
import io.github.takenoko4096.starlight.StarlightModInitializer
import io.github.takenoko4096.starlight.util.sound.PlaySound
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
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

            withItem()

            rendering {
                models {
                    val model = blockModels.cubeAll(blockDefaultTexturePath)

                    block {
                        always(model.toVariant())
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

           withItem()

            rendering {
                models {
                    val model = blockModels.cubeAll(blockDefaultTexturePath)

                    block {
                        always(model.toVariant())
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

            withItem()

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
                            case(false, off.toVariant())
                            case(true, on.toVariant())
                        }
                    }

                    item {
                        handling {
                            use(off)
                        }
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
                        always(model.toVariant())
                    }
                }

                color {
                    default {
                        0x1fff80
                    }
                }
            }

            withItem()

            translation {
                jaJp = "テストブロック"
                enUs = "Test Block"
            }
        }

        val testSword = itemRegistry.register("test_sword") {
            itemProperties {
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

        val tickClock = itemRegistry.register("tick_clock") {
            itemProperties {
                components {
                    maxStackSize(1)
                }
            }

            customBehaviour {
                events {
                    onUse {
                        val server = level.server ?: return@onUse

                        val manager = level.tickRateManager()

                        if (manager.isFrozen) {
                            for (player in server.playerList.players) {
                                PlaySound.playSound(player, SoundEvents.WITHER_SPAWN, 5f, 2f)
                            }
                        }
                        else {
                            for (player in server.playerList.players) {
                                PlaySound.playSound(player, SoundEvents.ANVIL_USE, 5f, 2f)
                            }
                        }

                        manager.isFrozen = !manager.isFrozen

                        for (player in server.playerList.players) {
                            player.addEffect(MobEffectInstance(MobEffects.BLINDNESS, 20, 0, false, false))
                        }
                    }
                }
            }

            translation {
                enUs = "Tick Clock"
                jaJp = "ティッククロック"
            }

            rendering {
                model {
                    val model = itemModels.generated(itemDefaultTexturePath)

                    handling {
                        use(model)
                    }
                }
            }
        }

        creativeModeTabRegistry.register("test") {
            translation {
                enUs = "Test MOD"
                jaJp = "テストMOD"
            }
            icon(testSword)
            items {
                item(testSword)
                block(testBlock)
                block(metalBlock)
                block(whiteLeaves)
                block(prismarineLamp)
                item(tickClock)
            }
        }

        commandRegistry.register("rainbow") {
            "text"(greedyString()) {
                executes {
                    val text = "text"[String::class]
                    context.successful {
                        rainbow(text)
                    }
                }
            }
        }
    }

    override fun onServerStart(data: DataDrivenStarlight) {
        logger.info("server started")
    }
}
