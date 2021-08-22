package me.maxish0t.mod.utilities;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;


public class StringFunctions {
    public static void sendMessage(CommandSourceStack source, String m, ChatFormatting colour) {
        sendMessage(source, m, colour, false);
    }
    public static void sendMessage(Player player, String m, ChatFormatting colour) {
        sendMessage(player, m, colour, false);
    }
    public static void sendMessage(CommandSourceStack source, String m, ChatFormatting colour, boolean emptyline) {
        sendMessage(source, m, colour, emptyline, "");
    }
    public static void sendMessage(Player player, String m, ChatFormatting colour, boolean emptyline) {
        sendMessage(player, m, colour, emptyline, "");
    }
    public static void sendMessage(CommandSourceStack source, String m, ChatFormatting colour, String url) {
        sendMessage(source, m, colour, false, url);
    }
    public static void sendMessage(Player player, String m, ChatFormatting colour, String url) {
        sendMessage(player, m, colour, false, url);
    }

    public static void sendMessage(CommandSourceStack source, String m, ChatFormatting colour, boolean emptyline, String url) {
        if (m == "") {
            return;
        }

        if (emptyline) {
            source.sendSuccess(new TextComponent(""), false);
        }

        TextComponent message = new TextComponent(m);
        message.withStyle(colour);
        if (m.contains("http") || url != "") {
            if (url == "") {
                for (String word : m.split(" ")) {
                    if (word.contains("http")) {
                        url = word;
                        break;
                    }
                }
            }

            if (url != "") {
                Style clickstyle = message.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
                message.withStyle(clickstyle);
            }
        }
        source.sendSuccess(message, false);
    }

    public static void sendMessage(Player player, String m, ChatFormatting colour, boolean emptyline, String url) {
        if (m == "") {
            return;
        }

        if (emptyline) {
            player.sendMessage(new TextComponent(""), player.getUUID());
        }

        TextComponent message = new TextComponent(m);
        message.withStyle(colour);
        if (m.contains("http") || url != "") {
            if (url == "") {
                for (String word : m.split(" ")) {
                    if (word.contains("http")) {
                        url = word;
                        break;
                    }
                }
            }

            if (url != "") {
                Style clickstyle = message.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
                message.withStyle(clickstyle);
            }
        }
        player.sendMessage(message, player.getUUID());
    }
}
