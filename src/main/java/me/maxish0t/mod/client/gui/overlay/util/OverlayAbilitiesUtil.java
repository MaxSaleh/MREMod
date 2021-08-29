package me.maxish0t.mod.client.gui.overlay.util;

import com.mojang.blaze3d.vertex.PoseStack;
import me.maxish0t.mod.client.gui.util.RenderUtil;
import me.maxish0t.mod.common.content.gathering.mining.MiningEvents;
import me.maxish0t.mod.server.packets.mining.BlockBreakAmountPacket;
import me.maxish0t.mod.server.packets.mining.PickaxeSpeedPacket;
import me.maxish0t.mod.server.packets.mining.SuperBreakerNeededPacket;
import me.maxish0t.mod.utilities.ModReference;
import me.maxish0t.mod.utilities.ModUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;

public class OverlayAbilitiesUtil {

    public static void renderSuperBreakerBox(String abilityName, String abilityDescription, String abilityIconName, PoseStack poseStack, double posX, double posY, boolean isUnlocked) {
        ResourceLocation resourceLocation = new ResourceLocation(ModReference.MODID, "textures/icons/gathering/mining/" + abilityIconName + ".png");
        int blockBreakPercentage = PickaxeSpeedPacket.increasePercentage;
        int amountNeededSuperBreaker = SuperBreakerNeededPacket.amount + 200;
        int rectWidth = 100, rectHeight = 60;

        PoseStack poseOne = poseStack;
        poseOne.pushPose();
        poseOne.translate(posX, posY, 0D);
        RenderUtil.drawRectWithOutline(poseOne, rectWidth,  rectHeight, 0, 0, 1426063360, 587202559, 1);
        poseOne.popPose();

        PoseStack poseTwo = poseStack;
        poseTwo.pushPose();
        poseTwo.translate(posX, posY, 0D);
        RenderUtil.drawRectWithOutline(poseTwo, rectWidth / 3,  rectHeight / 2 + 3, 0, 0, 1426063360, 587202559, 1);
        poseTwo.popPose();

        RenderUtil.drawTexture(poseStack, resourceLocation, (int)posX + 3, (int)posY + 3, 0, 27, 27, 32, 32, 32, 32, 32, 32);

        RenderUtil.drawTextScaled(poseStack, ChatFormatting.RED + abilityName, (float)posX + 60, (float)posY + 27, 0.7F);

        if (BlockBreakAmountPacket.amount >= 200) {
            RenderUtil.drawTextScaled(poseStack, ModUtil.renderColoredText("&1&lUNLOCKED"), (float)posX + 55, (float)posY + 30, 0.8F);
        } else {
            RenderUtil.drawTextScaled(poseStack, ModUtil.renderColoredText("&t&lLOCKED"), (float)posX + 63, (float)posY + 30, 0.8F);
        }

        RenderUtil.drawTextScaled(poseStack, ChatFormatting.BLUE + "Mining Speed" + " " + ChatFormatting.GREEN + "+" + ChatFormatting.BLUE + blockBreakPercentage + "%", (float)posX + 51, (float)posY + 60, 0.7F);

        PoseStack statsBar = poseStack;
        statsBar.pushPose();
        statsBar.translate(posX, posY + 50, 0D);
        RenderUtil.drawRectangle(statsBar, Math.round(ModUtil.calculatePercentage(BlockBreakAmountPacket.amount, amountNeededSuperBreaker)),  8, 2, 2, 1006063360);
        RenderUtil.drawRectWithOutline(statsBar, 100,  10, 0, 0, 1426063360, 587202559, 1);
        statsBar.popPose();

        RenderUtil.drawTextScaled(poseStack, ChatFormatting.YELLOW + "" + BlockBreakAmountPacket.amount + ChatFormatting.WHITE + "/" + ChatFormatting.YELLOW + amountNeededSuperBreaker + " " + ChatFormatting.UNDERLINE + "ores left", (float)posX + 17, (float)posY + 64, 0.8F);
    }
}
