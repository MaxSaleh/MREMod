package me.maxish0t.mod.client.gui.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderUtil {

    /**
     * Renders a text.
     */
    public static void drawText(PoseStack poseStack, String text, float xPos, float yPos) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.font.draw(poseStack, text, xPos, yPos, 0xffffff);
    }

    /**
     * Renders a scaled text.
     */
    public static void drawTextScaled(PoseStack poseStack, String text, float xPos, float yPos, float givenScale) {
        Minecraft minecraft = Minecraft.getInstance();
        poseStack.pushPose();
        poseStack.scale(givenScale, givenScale, givenScale);
        minecraft.font.draw(poseStack, text, xPos, yPos, 0xffffff);
        poseStack.popPose();
    }

    /**
     * Renders a texture.
     */
    public static void drawTexture(PoseStack poseStack, ResourceLocation image, int x, int y, int z, int width, int height, float srcX, float srcY, float srcWidth, float srcHeight, float textureWidth, float textureHeight) {
        RenderSystem.setShaderTexture(0, image);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        Matrix4f pose = poseStack.last().pose();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(pose, x, y + height, z).uv((srcX / textureWidth), (srcY + srcHeight) / textureHeight).endVertex();
        bufferbuilder.vertex(pose, x + width, y + height, z).uv((srcX + srcWidth) / textureWidth, (srcY + srcHeight) / textureHeight).endVertex();
        bufferbuilder.vertex(pose, x + width, y, z).uv((srcX + srcWidth) / textureWidth, srcY / textureHeight).endVertex();
        bufferbuilder.vertex(pose, x, y, z).uv(srcX / textureWidth, srcY / textureHeight).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
    }

    /**
     * Renders a Rectangle.
     */
    public static void drawRectangle(PoseStack poseStack, int givenPosX, int givenPosY, int givenWidth, int givenHeight, int givenColor) {
        GuiComponent.fill(poseStack, givenPosX, givenPosY, givenWidth, givenHeight, givenColor);
    }

    /**
     * Renders a Rectangle Without A Outline.
     */
    public static void drawRectWithOutline(PoseStack poseStack, int givenPosX, int givenPosY, int givenWidth, int givenHeight, int givenColor, int givenOutlineColor, int outlineThickness) {
        poseStack.pushPose();
        drawRectangle(poseStack, givenPosX - outlineThickness - 1, givenPosY - outlineThickness - 1, givenWidth + outlineThickness * 2, givenHeight + outlineThickness * 2, givenOutlineColor);
        drawRectangle(poseStack, givenPosX, givenPosY, givenWidth, givenHeight, givenColor);
        poseStack.popPose();
    }
}
