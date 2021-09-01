package me.maxish0t.mod.client.gui.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import me.maxish0t.mod.client.gui.overlay.util.OverlayAbilitiesUtil;
import me.maxish0t.mod.client.gui.util.RenderUtil;
import me.maxish0t.mod.client.handler.KeyInputHandler;
import me.maxish0t.mod.server.packets.AbilityCoolDownPacket;
import me.maxish0t.mod.server.packets.UpdateLevelPacket;
import me.maxish0t.mod.utilities.ModReference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "mre", value = Dist.CLIENT)
public class MiningOverlay {

    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Post event) {
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
                                UpdateLevelPacket.level, 40.0F, 5.0F);

                        ResourceLocation image = new ResourceLocation(ModReference.MOD_ID, "textures/icons/gathering/mining/pickaxe.png");
                        RenderUtil.drawTexture(poseStack, image, 5, 5, 0, 32, 32, 32, 32,
                                32, 32, 32, 32);

                        // Super Breaker Ability
                        OverlayAbilitiesUtil.renderSuperBreakerBox("Super Breaker", "super_breaker", poseStack, 5D, 40D);

                        // Double Ores Ability
                        OverlayAbilitiesUtil.renderDoubleOresBox("Double Ores", "double_ores", poseStack, 5D, 105D);

                        // Blast Mining Ability
                        OverlayAbilitiesUtil.renderBlastMiningBox("Blast Mining", "blast_mining", poseStack, 5D, 170D);

                    } else {
                        RenderUtil.drawText(poseStack, ChatFormatting.RED + "Hold V To Open Abilities Menu For Item",
                                5.0F, 5.0F);
                    }
                }
            }
        }
    }
}
