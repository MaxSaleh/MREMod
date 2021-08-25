package me.maxish0t.mod.client.gui.components.toasts;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.maxish0t.mod.server.packets.mining.BlockBreakAmountPacket;
import me.maxish0t.mod.utilities.ModUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MREToast implements Toast {
    private boolean playedSound;
    private String title;
    private String subTitle;

    public MREToast(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    @Override
    public Visibility render(PoseStack poseStack, ToastComponent toastComponent, long p_94898_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        toastComponent.blit(poseStack, 0, 0, 0, 0, this.width(), this.height());

        toastComponent.getMinecraft().font.draw(poseStack, title, 30.0F, 7.0F, 1 | -16777216);
        toastComponent.getMinecraft().font.draw(poseStack, subTitle, 30.0F, 18.0F, -1);

        if (!this.playedSound && p_94898_ > 0L) {
            this.playedSound = true;
            toastComponent.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F));
        }

        toastComponent.getMinecraft().getItemRenderer().renderAndDecorateFakeItem(new ItemStack(Items.DIAMOND_PICKAXE), 8, 8);

        return p_94898_ >= 5000L ? Visibility.HIDE : Visibility.SHOW;
    }
}
