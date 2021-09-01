package me.maxish0t.mod.client.handler;

import me.maxish0t.mod.client.input.KeyEntry;
import me.maxish0t.mod.client.input.KeyType;
import me.maxish0t.mod.common.content.gathering.mining.ClientMiningEvents;
import me.maxish0t.mod.server.ModNetwork;
import me.maxish0t.mod.server.packets.mining.BlastMiningPacket;
import me.maxish0t.mod.utilities.ModReference;
import net.java.games.input.Controller;
import net.java.games.input.Mouse;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmlclient.registry.ClientRegistry;
import net.minecraftforge.fmllegacy.network.NetworkDirection;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = "mre", value = Dist.CLIENT)
public class KeyInputHandler {
    private static ArrayList<KeyEntry> keyEntries;

    public static boolean showMiningOverlay = false;

    public KeyInputHandler() {
        keyEntries = new ArrayList<>();
        keyEntries.add(new KeyEntry(KeyType.AbilitiesMenu));
        keyEntries.add(new KeyEntry(KeyType.BlastMining));

        for (KeyEntry keyEntry : keyEntries) {
            ClientRegistry.registerKeyBinding(keyEntry.keyBinding);
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        for (KeyEntry keyEntry : keyEntries) {
            if (keyEntry.keyBinding.isDown()) {
                handleKeyInput(keyEntry.keyType);
                break;
            } else {
                showMiningOverlay = false;
            }
        }
    }

    private static void handleKeyInput(KeyType keyType) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player != null) {
            LocalPlayer clientPlayer = minecraft.player;

            switch (keyType) {
                case AbilitiesMenu -> {
                    if (clientPlayer.getMainHandItem().getItem() instanceof PickaxeItem ||
                            clientPlayer.getOffhandItem().getItem() instanceof PickaxeItem) {
                        showMiningOverlay = true;
                    } else {
                        clientPlayer.sendMessage(new TextComponent(ChatFormatting.RED +
                                "You are not holding a correct item for me to bring up any information."), clientPlayer.getUUID());
                    }
                    break;
                }
                case BlastMining -> {
                    if (ClientMiningEvents.unlockedBlastMining) {
                        if(minecraft.mouseHandler.isRightPressed()) {
                            ModNetwork.CHANNEL.sendTo(new BlastMiningPacket(), minecraft.getConnection().getConnection(), NetworkDirection.PLAY_TO_SERVER);
                        }
                    }
                    break;
                }
            }
        }
    }
}
