package me.maxish0t.mod.utilities;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderUtil {

    /**
     * Renders a texture.
     * @param poseStack
     * @param x
     * @param y
     * @param z
     * @param width
     * @param height
     * @param srcX
     * @param srcY
     * @param srcWidth
     * @param srcHeight
     * @param textureWidth
     * @param textureHeight
     */
    public static void drawTexture(PoseStack poseStack, ResourceLocation image, int x, int y, int z, int width, int height, float srcX, float srcY, float srcWidth, float srcHeight, float textureWidth, float textureHeight) {
        //Minecraft.getInstance().getTextureManager().getTexture(image);
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
}
