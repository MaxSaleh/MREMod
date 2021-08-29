package me.maxish0t.mod.common.commands.utils;

import me.maxish0t.mod.utilities.StringFunctions;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;

public class CommandUtils {

    public static void showUsage(CommandSourceStack source) {
        StringFunctions.sendMessage(source, "Commands Usage:", ChatFormatting.BLUE, true);
        StringFunctions.sendMessage(source, " /mre resetall", ChatFormatting.DARK_GREEN);
        StringFunctions.sendMessage(source, "  Resets all your player stats.", ChatFormatting.DARK_GRAY);
        StringFunctions.sendMessage(source, " /mre setlevel <content> <amount>", ChatFormatting.DARK_GREEN);
        StringFunctions.sendMessage(source, "  Sets a certain content to a certain level.", ChatFormatting.DARK_GRAY);
    }
}
