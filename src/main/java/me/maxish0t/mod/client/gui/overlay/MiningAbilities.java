package me.maxish0t.mod.client.gui.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import me.maxish0t.mod.client.gui.util.RenderUtil;
import me.maxish0t.mod.client.handler.KeyInputHandler;
import me.maxish0t.mod.server.packets.mining.BlockBreakAmountPacket;
import me.maxish0t.mod.server.packets.mining.DoubleDropsPacket;
import me.maxish0t.mod.server.packets.mining.PickaxeSpeedPacket;
import me.maxish0t.mod.utilities.ModReference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MiningAbilities {

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = (Player) minecraft.getCameraEntity();
        PoseStack poseStack = event.getMatrixStack();

        int height = minecraft.getWindow().getGuiScaledHeight();
        int width = minecraft.getWindow().getGuiScaledWidth();

        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {

            CompoundTag entityData = player.getPersistentData();
            CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
            entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

            if (!(minecraft.screen instanceof DeathScreen)) {
                if (player.getMainHandItem().getItem() instanceof PickaxeItem ||
                        player.getOffhandItem().getItem() instanceof PickaxeItem) {

                    if (KeyInputHandler.showMiningOverlay) {
                        minecraft.font.draw(poseStack, ChatFormatting.BLUE + "Mining Level: " +
                                BlockBreakAmountPacket.amount, 40.0F, 5.0F, 0xffffff);

                        int blockBreakPercentage = PickaxeSpeedPacket.increasePercentage;
                        minecraft.font.draw(poseStack, ChatFormatting.BLUE + "Abilities:", 40.0F, 20.0F, 0xffffff);

                        if (PickaxeSpeedPacket.increasePercentage != 0) {
                            minecraft.font.draw(poseStack, ChatFormatting.RED + "- " +
                                    ChatFormatting.BLUE + "Mining Speed" + ": " + ChatFormatting.GREEN + "+" +
                                    ChatFormatting.BLUE + blockBreakPercentage + "%", 40.0F, 30.0F, 0xffffff);
                        }

                        if (DoubleDropsPacket.canDoubleDrop) {
                            minecraft.font.draw(poseStack, ChatFormatting.RED + "- " +
                                    ChatFormatting.BLUE + "25% Double Block Drops", 40.0F, 40.0F, 0xffffff);
                        }

                        ResourceLocation image = new ResourceLocation(ModReference.MODID, "textures/icons/gathering/mining.png");
                        RenderUtil.drawTexture(poseStack, image, 5, 5, 0, 32, 32, 32, 32,
                                32, 32, 32, 32);
                    } else {
                        minecraft.font.draw(poseStack, ChatFormatting.RED + "Hold V To Open Abilities Menu For Item",
                                5.0F, 5.0F, 0xffffff);
                    }
                }
            }
        }
    }
}
