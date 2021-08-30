package me.maxish0t.mod.common.content.gathering.mining;

import me.maxish0t.mod.client.gui.components.toasts.MREToast;
import me.maxish0t.mod.common.capability.level.CapabilityLevelHandler;
import me.maxish0t.mod.server.ModNetwork;
import me.maxish0t.mod.server.packets.SendToastPacket;
import me.maxish0t.mod.server.packets.UpdateLevelPacket;
import me.maxish0t.mod.server.packets.mining.MiningAbilitiesPacket;
import me.maxish0t.mod.utilities.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmllegacy.network.NetworkDirection;

public class MiningAbilitiesEvents {

    public static final double SUPER_BREAKER_AMOUNT = 5;
    public static final double DOUBLE_ORES_AMOUNT = 10;

    public static boolean unlockedSuperBreaker = false;
    public static boolean unlockedDoubleOres = false;

    /**
     * Ability checks
     */
    @SubscribeEvent
    public void onPlayerEvent(TickEvent.PlayerTickEvent event) { // TODO create a packet for the booleans its going to cause server crashes
        final Minecraft minecraft = Minecraft.getInstance();
        float miningLevel = UpdateLevelPacket.level;

        MREToast miningSpeedToast = new MREToast(ModUtil.renderColoredText("&5&lABILITY UNLOCKED!"), ModUtil.renderColoredText("&bMining Speed &1+&b5%"));
        MREToast doubleOresToast = new MREToast(ModUtil.renderColoredText("&5&lABILITY UNLOCKED!"), ModUtil.renderColoredText("&125% &bof ore doubling"));

        // Super Breaker Ability
        if (miningLevel >= SUPER_BREAKER_AMOUNT) {
            if (minecraft.getToasts().getToast(MREToast.class, miningSpeedToast.getToken()) == null && !unlockedSuperBreaker) {
                minecraft.getToasts().addToast(miningSpeedToast);
            }

            unlockedSuperBreaker = true;
        } else {
            unlockedSuperBreaker = false;
        }

        // Double Ores Ability
        if (miningLevel >= DOUBLE_ORES_AMOUNT) {
            if (minecraft.getToasts().getToast(MREToast.class, doubleOresToast.getToken()) == null && !unlockedDoubleOres) {
                minecraft.getToasts().addToast(doubleOresToast);
            }

            unlockedDoubleOres = true;
        } else {
            unlockedDoubleOres = false;
        }
    }

    /**
     * Super Breaker Ability
     */
    @SubscribeEvent
    public void onBlockBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        float originalSpeed = event.getOriginalSpeed();

        if (unlockedSuperBreaker) {
            if (player instanceof ServerPlayer) {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                ModNetwork.CHANNEL.sendTo(new MiningAbilitiesPacket(1, true), serverPlayer.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
            }

            System.out.println("WORKS!");

            if (player.getMainHandItem().getItem() instanceof PickaxeItem ||
                    player.getOffhandItem().getItem() instanceof PickaxeItem) {
                event.setNewSpeed(originalSpeed + 200F);
            }
        } else {
            event.setNewSpeed(originalSpeed);

            if (player instanceof ServerPlayer) {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                ModNetwork.CHANNEL.sendTo(new MiningAbilitiesPacket(1, false), serverPlayer.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }

    /**
     * Double Ores Ability
     */
    @SubscribeEvent
    public void onBlockDoubleDrop(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Block blockBroken = event.getState().getBlock();
        BlockPos blockPos = event.getPos();
        ItemStack itemStack = new ItemStack(blockBroken.asItem());

        if (unlockedDoubleOres) {
            if (player instanceof ServerPlayer) {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                ModNetwork.CHANNEL.sendTo(new MiningAbilitiesPacket(2, true), serverPlayer.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
            }

            if (ModUtil.percentChance(0.25)) {
                if (player.level instanceof ServerLevel) {
                    ServerLevel serverLevel = (ServerLevel) player.level;
                    serverLevel.sendParticles(ParticleTypes.SMALL_FLAME, blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), 15, 0.2D, 0.2D, 0.2D, 0.0D);
                }

                String unlockedAbility = ModUtil.renderColoredText("&9You have been rewarded with a extra " + blockBroken.getName().getString() + ".");
                player.sendMessage(new TextComponent(unlockedAbility), player.getUUID());
                player.addItem(itemStack);
            }
        } else {
            if (player instanceof ServerPlayer) {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                ModNetwork.CHANNEL.sendTo(new MiningAbilitiesPacket(2, false), serverPlayer.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }
}
