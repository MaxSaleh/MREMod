package me.maxish0t.mod.client.gui.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import me.maxish0t.mod.client.gui.components.toasts.MREToast;
import me.maxish0t.mod.common.content.gathering.mining.MiningEvents;
import me.maxish0t.mod.server.ModNetwork;
import me.maxish0t.mod.server.packets.SendToastPacket;
import me.maxish0t.mod.server.packets.mining.BlockBreakAmountPacket;
import me.maxish0t.mod.server.packets.mining.DoubleDropsPacket;
import me.maxish0t.mod.server.packets.mining.PickaxeSpeedPacket;
import me.maxish0t.mod.utilities.ModUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

public class LevelAmounts {
    private boolean hasShown = false;

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Player player = (Player) minecraft.getCameraEntity();
            PoseStack poseStack = event.getMatrixStack();

            int height = minecraft.getWindow().getGuiScaledHeight();
            int width = minecraft.getWindow().getGuiScaledWidth();

            CompoundTag entityData = player.getPersistentData();
            CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
            entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

            if (!(minecraft.screen instanceof DeathScreen)) {
                if (player.getMainHandItem().getItem() instanceof PickaxeItem ||
                        player.getOffhandItem().getItem() instanceof PickaxeItem) {
                    minecraft.font.draw(poseStack, ChatFormatting.BLUE + "Mining Level: " +
                            BlockBreakAmountPacket.amount, 5.0F, 5.0F, 0xffffff);

                    int blockBreakPercentage = (int) PickaxeSpeedPacket.increasePercentage;
                    minecraft.font.draw(poseStack, ChatFormatting.BLUE + "Abilities:", 5.0F, 20.0F, 0xffffff);

                    if (PickaxeSpeedPacket.increasePercentage != 0) {
                        minecraft.font.draw(poseStack, ChatFormatting.RED + "- " +
                                ChatFormatting.BLUE + "Mining Speed" + ": " + ChatFormatting.GREEN + "+" +
                                ChatFormatting.BLUE + blockBreakPercentage + "%", 5.0F, 30.0F, 0xffffff);
                    }

                    if (DoubleDropsPacket.canDoubleDrop) {
                        minecraft.font.draw(poseStack, ChatFormatting.RED + "- " +
                                ChatFormatting.BLUE + "25% Double Block Drops", 5.0F, 40.0F, 0xffffff);
                    }
                }
            }
        }
    }
}
