package me.maxish0t.mod.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.maxish0t.mod.common.commands.utils.CommandUtils;
import me.maxish0t.mod.common.content.PlayerContent;
import me.maxish0t.mod.server.ModNetwork;
import me.maxish0t.mod.server.packets.mining.BlockBreakAmountPacket;
import me.maxish0t.mod.utilities.StringFunctions;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class SetLevelCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mre").requires((iCommandSender) -> iCommandSender.hasPermission(2))
                .executes((command) -> {
                    CommandSourceStack source = command.getSource();
                    CommandUtils.showUsage(source);
                    return 1;
                })
                .then(Commands.literal("setlevel").executes((command) -> {
                    CommandSourceStack source = command.getSource();
                    CommandUtils.showUsage(source);
                    return 1;
                })
                        .then(Commands.literal("mining").executes((command) -> {
                            CommandSourceStack source = command.getSource();
                            CommandUtils.showUsage(source);
                            return 1;
                        })
                                .then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes((command) -> {
                                    setLevel(command, IntegerArgumentType.getInteger(command, "amount"));
                                    return 1;
                                })))));
    }

    private static void setLevel(CommandContext<CommandSourceStack> command, int count) {
        CommandSourceStack source = command.getSource();
        Player player;

        try {
            player = source.getPlayerOrException();

            if (player != null) {
                CompoundTag entityData = player.getPersistentData();
                CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
                entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

                persistedData.putInt("block_break_data", count);
                ModNetwork.CHANNEL.sendToServer(new BlockBreakAmountPacket(persistedData.getInt("block_break_data")));
            }
        }
        catch (CommandSyntaxException ex) {
            StringFunctions.sendMessage(source, "This command can only be executed as a player in-game.", ChatFormatting.RED);
        }
    }
}
