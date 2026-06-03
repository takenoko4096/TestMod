package com.gmail.takenokoii78.testmod

import io.github.takenoko4096.noctiluca.NoctilucaModInitializer
import io.github.takenoko4096.noctiluca.ServerContainer
import io.github.takenoko4096.noctiluca.portal.PortalType
import io.github.takenoko4096.noctiluca.text.RgbColor
import io.github.takenoko4096.noctiluca.util.sound.PlaySound
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType

object TestModInitializer : NoctilucaModInitializer("testmod") {
    override fun onInitialize() {
        val whiteLeaves = blockRegistry.register("white_leaves") {
            blockProperties {
                occlusion = false
                sound = SoundType.GRASS
                destroyTime = 0.5f
                explosionResistance = 0.5f
            }

            withItem()

            model {
                val model = blockModels.cubeAll(blockDefaultTexturePath)

                block {
                    always(model.toVariant())
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

            model {}

            translation {
                enUs = "Metal Block"
                jaJp = "謎金属ブロック"
            }

            withSlab()
            withStairs()
            withFence()
            withWall()
            withFenceGate()
        }

        val prismarineLamp = blockRegistry.register("prismarine_lamp") {
            val lit = "lit"

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

            model {
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

        val testBlock = blockRegistry.register("test_block") {
            blockProperties {
                occlusion = false
            }

            model {
                val model = blockModels.cubeAll(blockDefaultTexturePath)

                block {
                    always(model.toVariant())
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

            model {
                val model = itemModels.handheld(itemDefaultTexturePath)

                handling {
                    use(model)
                }
            }
        }

        val tickClock = itemRegistry.register("tick_clock") {
            itemProperties {
                components {
                    maxStackSize(1)
                }
            }

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

            translation {
                enUs = "Tick Clock"
                jaJp = "ティッククロック"
            }

            model {
                val model = itemModels.generated(itemDefaultTexturePath)

                handling {
                    use(model)
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

        val namedColorArgument = commandRegistry.registerEnumArgumentType<NamedColor>("named_color")

        commandRegistry.register("color") {
            "rainbow" {
                "text"(greedyString()) {
                    executes {
                        val text = "text"[String::class]
                        context.successful {
                            rainbow {
                                text(text)
                            }
                        }
                    }
                }
            }

            "gradient" {
                "color1"(namedColorArgument) {
                    "color2"(namedColorArgument) {
                        "color3"(namedColorArgument) {
                            "color4"(namedColorArgument) {
                                "text"(greedyString()) {
                                    executes {
                                        val text = "text"<String>()
                                        val c1 = "color1"<NamedColor>().rgb
                                        val c2 = "color2"<NamedColor>().rgb
                                        val c3 = "color3"<NamedColor>().rgb
                                        val c4 = "color4"<NamedColor>().rgb
                                        context.successful {
                                            gradient(c1, c2, c3, c4) {
                                                text(text)
                                            }
                                        }
                                    }
                                }
                            }

                            "text"(greedyString()) {
                                executes {
                                    val text = "text"<String>()
                                    val c1 = "color1"<NamedColor>().rgb
                                    val c2 = "color2"<NamedColor>().rgb
                                    val c3 = "color3"<NamedColor>().rgb
                                    context.successful {
                                        gradient(c1, c2, c3) {
                                            text(text)
                                        }
                                    }
                                }
                            }
                        }

                        "text"(greedyString()) {
                            executes {
                                val text = "text"<String>()
                                val c1 = "color1"<NamedColor>().rgb
                                val c2 = "color2"<NamedColor>().rgb
                                context.successful {
                                    gradient(c1, c2) {
                                        text(text)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        debugger("foo") {
            context.successful { text("foo!") }
        }

        PortalType.register(
            identifierOf("redstone"),
            Blocks.REDSTONE_BLOCK,
            RgbColor.DARK_RED.withAlpha(255),
            Items.REDSTONE_TORCH
        )

        PortalType.register(
            identifierOf("end"),
            Blocks.END_STONE_BRICKS,
            RgbColor.LIGHT_PURPLE.withAlpha(255),
            Items.ENDER_EYE
        )

        PortalType.register(
            identifierOf("book"),
            Blocks.BOOKSHELF,
            RgbColor.GOLD.withAlpha(255),
            Items.BOOK
        )
    }

    override fun onServerStart(data: ServerContainer) {
        logger.info("server started")
    }
}
