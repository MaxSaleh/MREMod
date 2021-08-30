package me.maxish0t.mod.utilities;

import net.minecraft.ChatFormatting;

public class ModUtil {

    /**
     * Easier way to make code shorter by using characters in the strings for colored text.
     * @param text
     * @return
     */
    public static String renderColoredText(String text) {
        text = text.replace("&e", ChatFormatting.YELLOW.toString());
        text = text.replace("&b", ChatFormatting.BLUE.toString());
        text = text.replace("&l", ChatFormatting.BOLD.toString());
        text = text.replace("&a", ChatFormatting.AQUA.toString());
        text = text.replace("&u", ChatFormatting.BLACK.toString());
        text = text.replace("&d", ChatFormatting.DARK_AQUA.toString());
        text = text.replace("&y", ChatFormatting.DARK_BLUE.toString());
        text = text.replace("&w", ChatFormatting.DARK_GRAY.toString());
        text = text.replace("&c", ChatFormatting.DARK_GREEN.toString());
        text = text.replace("&x", ChatFormatting.DARK_PURPLE.toString());
        text = text.replace("&t", ChatFormatting.DARK_RED.toString());
        text = text.replace("&i", ChatFormatting.GOLD.toString());
        text = text.replace("&a", ChatFormatting.GRAY.toString());
        text = text.replace("&1", ChatFormatting.GREEN.toString());
        text = text.replace("&2", ChatFormatting.ITALIC.toString());
        text = text.replace("&3", ChatFormatting.LIGHT_PURPLE.toString());
        text = text.replace("&4", ChatFormatting.OBFUSCATED.toString());
        text = text.replace("&5", ChatFormatting.RED.toString());
        text = text.replace("&6", ChatFormatting.RESET.toString());
        text = text.replace("&7", ChatFormatting.STRIKETHROUGH.toString());
        text = text.replace("&8", ChatFormatting.UNDERLINE.toString());
        text = text.replace("&9", ChatFormatting.WHITE.toString());
        return text;
    }

    /**
     * Increase a number by a percentage.
     */
    public static float increaseByPercentage(float originalValue, float percentage) {
        float result = (originalValue / 100) * percentage;
        return result;
    }

    public static double calculatePercentageDouble(final double input, final double max) {
        return input * 100.0f / max;
    }

    public static float calculatePercentageFloat(final float input, final float max) {
        return input * 100.0f / max;
    }
}
