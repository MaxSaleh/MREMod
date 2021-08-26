package me.maxish0t.mod.client.handler;

import me.maxish0t.mod.client.input.KeyEntry;
import me.maxish0t.mod.client.input.KeyType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

import java.util.ArrayList;

public class KeyInputHandler {
    private ArrayList<KeyEntry> keyEntries;

    public static boolean showMiningOverlay = false;

    public KeyInputHandler() {
        keyEntries = new ArrayList<>();
        keyEntries.add(new KeyEntry(KeyType.AbilitiesMenu));

        for (KeyEntry keyEntry : keyEntries) {
            ClientRegistry.registerKeyBinding(keyEntry.keyBinding);
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        for (KeyEntry keyEntry : keyEntries) {
            if (keyEntry.keyBinding.isDown()) {
                handleKeyInput(keyEntry.keyType);
                break;
            }
        }
    }

    private void handleKeyInput(KeyType keyType) {
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
            }
        }
    }
}
