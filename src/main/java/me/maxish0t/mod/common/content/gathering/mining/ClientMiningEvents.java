package me.maxish0t.mod.common.content.gathering.mining;

import me.maxish0t.mod.client.gui.components.toasts.MREToast;
import me.maxish0t.mod.server.ModNetwork;
import me.maxish0t.mod.server.packets.UpdateLevelPacket;
import me.maxish0t.mod.server.packets.mining.MiningAbilitiesPacket;
import me.maxish0t.mod.utilities.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "mre", value = Dist.CLIENT)
public class ClientMiningEvents {

    public static final double SUPER_BREAKER_AMOUNT = 5;
    public static final double DOUBLE_ORES_AMOUNT = 10;

    public static boolean unlockedSuperBreaker = false;
    public static boolean unlockedDoubleOres = false;

    /**
     * Ability checks
     */
    @SubscribeEvent
    public static void onPlayerEvent(TickEvent.PlayerTickEvent event) { // TODO create a packet for the booleans its going to cause server crashes
        final Minecraft minecraft = Minecraft.getInstance();
        float miningLevel = UpdateLevelPacket.level;

        MREToast miningSpeedToast = new MREToast(ModUtil.renderColoredText("&5&lABILITY UNLOCKED!"), ModUtil.renderColoredText("&bMining Speed &1+&b5%"));
        MREToast doubleOresToast = new MREToast(ModUtil.renderColoredText("&5&lABILITY UNLOCKED!"), ModUtil.renderColoredText("&165% &bof ore doubling"));

        // Super Breaker Ability
        if (miningLevel >= SUPER_BREAKER_AMOUNT) {
            if (minecraft.getToasts().getToast(MREToast.class, miningSpeedToast.getToken()) == null && !unlockedSuperBreaker) {
                minecraft.getToasts().addToast(miningSpeedToast);
            }

            unlockedSuperBreaker = true;
            ModNetwork.CHANNEL.sendToServer(new MiningAbilitiesPacket(1, true));
        } else {
            unlockedSuperBreaker = false;
            ModNetwork.CHANNEL.sendToServer(new MiningAbilitiesPacket(1, false));
        }

        // Double Ores Ability
        if (miningLevel >= DOUBLE_ORES_AMOUNT) {
            if (minecraft.getToasts().getToast(MREToast.class, doubleOresToast.getToken()) == null && !unlockedDoubleOres) {
                minecraft.getToasts().addToast(doubleOresToast);
            }

            unlockedDoubleOres = true;
            ModNetwork.CHANNEL.sendToServer(new MiningAbilitiesPacket(2, true));
        } else {
            unlockedDoubleOres = false;
            ModNetwork.CHANNEL.sendToServer(new MiningAbilitiesPacket(2, false));
        }
    }
}