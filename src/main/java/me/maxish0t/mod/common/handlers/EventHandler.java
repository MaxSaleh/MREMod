package me.maxish0t.mod.common.handlers;

import me.maxish0t.mod.server.ModNetwork;
import me.maxish0t.mod.server.packets.mining.BlockBreakAmountPacket;
import me.maxish0t.mod.server.packets.mining.DoubleDropsPacket;
import me.maxish0t.mod.server.packets.mining.PickaxeSpeedPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

public class EventHandler {

    @SubscribeEvent
    public void logIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();

        CompoundTag entityData = player.getPersistentData();
        CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
        entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

        ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new BlockBreakAmountPacket(
                persistedData.getInt("block_break_data")));
        ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new PickaxeSpeedPacket(
                persistedData.getInt("pickaxe_speed_percentage")));
        ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new DoubleDropsPacket(
                persistedData.getBoolean("block_double_drops")));
    }

    @SubscribeEvent
    public void blockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Block blocks = event.getState().getBlock();

        if (player.getMainHandItem().getItem() instanceof PickaxeItem ||
        player.getOffhandItem().getItem() instanceof PickaxeItem) {
            if (blocks instanceof OreBlock || blocks instanceof RedStoneOreBlock) {
                CompoundTag entityData = player.getPersistentData();
                CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
                entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

                addBlockBreak(player, 1);
                ModNetwork.CHANNEL.sendToServer(new BlockBreakAmountPacket(persistedData.getInt("block_break_data")));
            }
        }
    }

    public void addBlockBreak(Player player, int value) {
        CompoundTag entityData = player.getPersistentData();
        CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
        entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

        persistedData.putInt("block_break_data", persistedData.getInt("block_break_data") + value);
    }
}
