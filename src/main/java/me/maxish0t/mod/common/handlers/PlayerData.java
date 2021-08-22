package me.maxish0t.mod.common.handlers;

import me.maxish0t.mod.server.ModNetwork;
import me.maxish0t.mod.server.packets.mining.BlockBreakAmountPacket;
import me.maxish0t.mod.server.packets.mining.DoubleDropsPacket;
import me.maxish0t.mod.server.packets.mining.PickaxeSpeedPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerData {

    @SubscribeEvent
    public void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
        setPlayerBlockBreakAmount(event.getPlayer(), loadPlayerBlockBreakAmountFromNBT(event.getPlayer()));
        event.getPlayer().getPersistentData().putInt("pickaxe_speed_percentage", event.getPlayer().getPersistentData().getInt("pickaxe_speed_percentage"));
    }

    @SubscribeEvent
    public void onPlayerLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
        savePlayerBlockBreakAmountToNBT(event.getPlayer());
        event.getPlayer().getPersistentData().putInt("pickaxe_speed_percentage",
                event.getPlayer().getPersistentData().getInt("pickaxe_speed_percentage"));
    }

    @SubscribeEvent
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) { }

    /**
     * Block Break Related
     */
    public static int getPlayerBlockBreakAmount(Player player) {
        return player.getPersistentData().getInt("block_break_data");
    }

    public static void setPlayerBlockBreakAmount(Player player, int value) {
        player.getPersistentData().putInt("block_break_data", value);
    }

    public static void addPlayerBlockBreakAmount(Player player, int value) {
        player.getPersistentData().putInt("block_break_data", player.getPersistentData().getInt("block_break_data") + value);
    }

    public static void savePlayerBlockBreakAmountToNBT(Player player) {
        player.getPersistentData().putInt("block_break_data", getPlayerBlockBreakAmount(player));
    }

    public static int loadPlayerBlockBreakAmountFromNBT(Player player) {
        return player.getPersistentData().getInt("block_break_data");
    }

    /**
     * Resets all player data
     */
    public static void resetAllData(Player player) {
        CompoundTag entityData = player.getPersistentData();
        CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
        entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

        persistedData.putInt("block_break_data", 0);
        ModNetwork.CHANNEL.sendToServer(new BlockBreakAmountPacket(0));
        persistedData.putInt("pickaxe_speed_percentage", 0);
        ModNetwork.CHANNEL.sendToServer(new PickaxeSpeedPacket(0));
        persistedData.putBoolean("block_double_drops", false);
        ModNetwork.CHANNEL.sendToServer(new DoubleDropsPacket(false));
    }
}
