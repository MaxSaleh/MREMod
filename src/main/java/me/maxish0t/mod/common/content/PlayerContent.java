package me.maxish0t.mod.common.content;

import me.maxish0t.mod.server.ModNetwork;
import me.maxish0t.mod.server.packets.mining.BlockBreakAmountPacket;
import me.maxish0t.mod.server.packets.mining.DoubleDropsPacket;
import me.maxish0t.mod.server.packets.mining.PickaxeSpeedPacket;
import me.maxish0t.mod.server.packets.mining.SuperBreakerNeededPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class PlayerContent {
    /**
     * Resets all player data
     */
    public static void resetAllData(Player player) {
        CompoundTag entityData = player.getPersistentData();
        CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
        entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

        persistedData.putInt("block_break_data", 0);
        ModNetwork.CHANNEL.sendToServer(new BlockBreakAmountPacket(0));

        persistedData.putInt("block_mining_speed", 0);
        ModNetwork.CHANNEL.sendToServer(new PickaxeSpeedPacket(0));

        persistedData.putBoolean("mining_double_drops", false);
        ModNetwork.CHANNEL.sendToServer(new DoubleDropsPacket(false));

        persistedData.putInt("super_breaker_block_break_needed", 0);
        ModNetwork.CHANNEL.sendToServer(new SuperBreakerNeededPacket(0));
    }
}
