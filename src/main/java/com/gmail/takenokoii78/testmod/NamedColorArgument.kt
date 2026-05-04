package com.gmail.takenokoii78.testmod

import io.github.takenoko4096.starlight.text.RgbColor
import net.minecraft.util.StringRepresentable

enum class NamedColorArgument(val rgb: RgbColor) : StringRepresentable {
    RED(RgbColor.RED),
    GOLD(RgbColor.GOLD),
    YELLOW(RgbColor.YELLOW),
    GREEN(RgbColor.GREEN),
    DARK_RED(RgbColor.DARK_RED),
    DARK_GREEN(RgbColor.DARK_GREEN),
    DARK_PURPLE(RgbColor.DARK_PURPLE),
    DARK_AQUA(RgbColor.DARK_AQUA),
    DARK_BLUE(RgbColor.DARK_BLUE),
    LIGHT_PURPLE(RgbColor.LIGHT_PURPLE),
    GRAY(RgbColor.GRAY),
    DARK_GRAY(RgbColor.DARK_GRAY),
    WHITE(RgbColor.WHITE),
    BLACK(RgbColor.BLACK),
    BLUE(RgbColor.BLUE),
    MATERIAL_EMERALD(RgbColor.MATERIAL_EMERALD),
    MATERIAL_DIAMOND(RgbColor.MATERIAL_DIAMOND),
    MATERIAL_REDSTONE(RgbColor.MATERIAL_REDSTONE),
    MATERIAL_LAPIS(RgbColor.MATERIAL_LAPIS),
    MATERIAL_AMETHYST(RgbColor.MATERIAL_AMETHYST),
    MATERIAL_IRON(RgbColor.MATERIAL_IRON),
    MATERIAL_NETHERITE(RgbColor.MATERIAL_NETHERITE),
    MATERIAL_COPPER(RgbColor.MATERIAL_COPPER),
    MATERIAL_QUARTZ(RgbColor.MATERIAL_QUARTZ),
    MATERIAL_GOLD(RgbColor.MATERIAL_GOLD),
    MATERIAL_RESIN(RgbColor.MATERIAL_RESIN);

    override fun getSerializedName(): String {
        return rgb.toString()
    }
}
