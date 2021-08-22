package me.maxish0t.mod.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.maxish0t.mod.common.handlers.PlayerData;
import me.maxish0t.mod.utilities.StringFunctions;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;

public class ResetDataCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mre").requires((iCommandSender) -> iCommandSender.hasPermission(2))
                .executes((command) -> {
                    CommandSourceStack source = command.getSource();
                    showUsage(source);
                    return 1;
                })
        .then(Commands.literal("resetall").executes((command) -> {
            resetAllData(command);
            StringFunctions.sendMessage(command.getSource(), "Player Stats has been reset!", ChatFormatting.GREEN);
            return 1;
        })));
    }

    private static void resetAllData(CommandContext<CommandSourceStack> command) {
        CommandSourceStack source = command.getSource();
        Player player;

        try {
            player = source.getPlayerOrException();

            if (player != null) {
                PlayerData.resetAllData(player);
            }
        }
        catch (CommandSyntaxException ex) {
            StringFunctions.sendMessage(source, "This command can only be executed as a player in-game.", ChatFormatting.RED);
        }
    }

    private static void showUsage(CommandSourceStack source) {
        StringFunctions.sendMessage(source, "Commands Usage:", ChatFormatting.BLUE, true);
        StringFunctions.sendMessage(source, " /mre resetall", ChatFormatting.DARK_GREEN);
        StringFunctions.sendMessage(source, "  Resets all your player stats.", ChatFormatting.DARK_GRAY);
    }
}
