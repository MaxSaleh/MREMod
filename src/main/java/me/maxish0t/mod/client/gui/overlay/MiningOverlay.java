package me.maxish0t.mod.client.gui.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import me.maxish0t.mod.client.gui.overlay.util.OverlayAbilitiesUtil;
import me.maxish0t.mod.client.gui.util.RenderUtil;
import me.maxish0t.mod.client.handler.KeyInputHandler;
import me.maxish0t.mod.common.capability.level.CapabilityLevelHandler;
import me.maxish0t.mod.common.capability.level.ILevel;
import me.maxish0t.mod.server.packets.UpdateLevelPacket;
import me.maxish0t.mod.server.packets.mining.BlockBreakAmountPacket;
import me.maxish0t.mod.server.packets.mining.DoubleDropsPacket;
import me.maxish0t.mod.server.packets.mining.PickaxeSpeedPacket;
import me.maxish0t.mod.utilities.ModReference;
import me.maxish0t.mod.utilities.ModUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MiningOverlay {

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

                    if (!KeyInputHandler.showMiningOverlay) { // TODO to true
                        RenderUtil.drawText(poseStack, ChatFormatting.BLUE + "Mining Level: " +
                                CapabilityLevelHandler.getLevelClient(), 40.0F, 5.0F);

                        ResourceLocation image = new ResourceLocation(ModReference.MODID, "textures/icons/gathering/mining/pickaxe.png");
                        RenderUtil.drawTexture(poseStack, image, 5, 5, 0, 32, 32, 32, 32,
                                32, 32, 32, 32);

                        // Super Breaker Ability
                        OverlayAbilitiesUtil.renderSuperBreakerBox("Super Breaker", "super_breaker", poseStack, 5D, 50D);

                        // Double Ores Ability
                        OverlayAbilitiesUtil.renderDoubleOresBox("Double Ores", "double_ores", poseStack, 5D, 140D);

                    } else {
                        RenderUtil.drawText(poseStack, ChatFormatting.RED + "Hold V To Open Abilities Menu For Item",
                                5.0F, 5.0F);
                    }
                }
            }
        }
    }
}
