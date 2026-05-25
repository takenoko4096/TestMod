package com.gmail.takenokoii78.testmod

import io.github.takenoko4096.noctiluca.text.RgbColor
import net.minecraft.util.StringRepresentable

enum class NamedColor(val rgb: RgbColor) : StringRepresentable {
    RED(RgbColor.Companion.RED),
    GOLD(RgbColor.Companion.GOLD),
    YELLOW(RgbColor.Companion.YELLOW),
    GREEN(RgbColor.Companion.GREEN),
    DARK_RED(RgbColor.Companion.DARK_RED),
    DARK_GREEN(RgbColor.Companion.DARK_GREEN),
    DARK_PURPLE(RgbColor.Companion.DARK_PURPLE),
    DARK_AQUA(RgbColor.Companion.DARK_AQUA),
    DARK_BLUE(RgbColor.Companion.DARK_BLUE),
    LIGHT_PURPLE(RgbColor.Companion.LIGHT_PURPLE),
    GRAY(RgbColor.Companion.GRAY),
    DARK_GRAY(RgbColor.Companion.DARK_GRAY),
    WHITE(RgbColor.Companion.WHITE),
    BLACK(RgbColor.Companion.BLACK),
    BLUE(RgbColor.Companion.BLUE),
    MATERIAL_EMERALD(RgbColor.Companion.MATERIAL_EMERALD),
    MATERIAL_DIAMOND(RgbColor.Companion.MATERIAL_DIAMOND),
    MATERIAL_REDSTONE(RgbColor.Companion.MATERIAL_REDSTONE),
    MATERIAL_LAPIS(RgbColor.Companion.MATERIAL_LAPIS),
    MATERIAL_AMETHYST(RgbColor.Companion.MATERIAL_AMETHYST),
    MATERIAL_IRON(RgbColor.Companion.MATERIAL_IRON),
    MATERIAL_NETHERITE(RgbColor.Companion.MATERIAL_NETHERITE),
    MATERIAL_COPPER(RgbColor.Companion.MATERIAL_COPPER),
    MATERIAL_QUARTZ(RgbColor.Companion.MATERIAL_QUARTZ),
    MATERIAL_GOLD(RgbColor.Companion.MATERIAL_GOLD),
    MATERIAL_RESIN(RgbColor.Companion.MATERIAL_RESIN);

    override fun getSerializedName(): String {
        return rgb.toString()
    }
}