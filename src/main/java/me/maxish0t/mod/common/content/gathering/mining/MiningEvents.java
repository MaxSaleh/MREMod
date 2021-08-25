package me.maxish0t.mod.common.content.gathering.mining;

import me.maxish0t.mod.server.ModNetwork;
import me.maxish0t.mod.server.packets.SendToastPacket;
import me.maxish0t.mod.server.packets.mining.BlockBreakAmountPacket;
import me.maxish0t.mod.server.packets.mining.DoubleDropsPacket;
import me.maxish0t.mod.server.packets.mining.PickaxeSpeedPacket;
import me.maxish0t.mod.utilities.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.Arrays;
import java.util.List;

public class MiningEvents {
    public static List<Integer> blockRewardNumbers = Arrays.asList(200, 400, 600, 800, 1000, 1200, 1400, 1600, 1800, 2000, 2200, 2400, 2600, 2800, 3000, 3200, 3400, 3600, 3800, 4000);

    /**
     * Whenever a player logs in this data gets sent to the client for the HUD.
     */
    @SubscribeEvent
    public void logIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();

        CompoundTag entityData = player.getPersistentData();
        CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
        entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

        ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new BlockBreakAmountPacket(
                persistedData.getInt("block_break_data")));
        ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new PickaxeSpeedPacket(
                persistedData.getInt("block_mining_speed")));
        ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new DoubleDropsPacket(
                persistedData.getBoolean("mining_double_drops")));
    }

    /**
     * Whenever a player breaks a block the data gets updated.
     */
    @SubscribeEvent
    public void blockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Block blocks = event.getState().getBlock();

        CompoundTag entityData = player.getPersistentData();
        CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
        entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

        if (player.getMainHandItem().getItem() instanceof PickaxeItem ||
                player.getOffhandItem().getItem() instanceof PickaxeItem) {
            if (blocks instanceof OreBlock || blocks instanceof RedStoneOreBlock) {
                int amount = persistedData.getInt("block_break_data");
                int addAmount = amount + 1;
                persistedData.putInt("block_break_data", addAmount);
                ModNetwork.CHANNEL.sendToServer(new BlockBreakAmountPacket(amount));
            }
        }
    }

    /**
     * Pickaxe Speed Rewarded.
     */
    @SubscribeEvent
    public void rewardPlayer(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Block blocks = event.getState().getBlock();

        CompoundTag entityData = player.getPersistentData();
        CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
        entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

        if (player.getMainHandItem().getItem() instanceof PickaxeItem ||
                player.getOffhandItem().getItem() instanceof PickaxeItem) {
            if (blocks instanceof OreBlock || blocks instanceof RedStoneOreBlock) {
                for (int i = 0; i < blockRewardNumbers.size(); i++) {
                    int amount = persistedData.getInt("block_break_data");
                    int breakSpeedPercentage = persistedData.getInt("block_mining_speed");

                    if (amount == blockRewardNumbers.get(i)) {
                        breakSpeedPercentage = breakSpeedPercentage + 5;
                        persistedData.putInt("block_mining_speed", breakSpeedPercentage);
                        ModNetwork.CHANNEL.sendToServer(new PickaxeSpeedPacket(breakSpeedPercentage));

                        if (amount == 5) {
                            ModNetwork.CHANNEL.sendToServer(new SendToastPacket(1));
                        }

                        ModNetwork.CHANNEL.sendToServer(new SendToastPacket(2));
                    }
                }
            }
        }
    }

    /**
     * Event to change the speed of the pickaxe.
     */
    @SubscribeEvent
    public void blockBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        Block blocks = event.getState().getBlock();

        CompoundTag entityData = player.getPersistentData();
        CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
        entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

        if (player.getMainHandItem().getItem() instanceof PickaxeItem ||
                player.getOffhandItem().getItem() instanceof PickaxeItem) {

            if (blocks instanceof OreBlock || blocks instanceof RedStoneOreBlock) {
                int breakSpeedPercentage = persistedData.getInt("block_mining_speed");
                float getOrigSpeed = event.getOriginalSpeed();
                event.setNewSpeed(getOrigSpeed + breakSpeedPercentage);
            }
        }
    }

    /**
     * Double block drop ability.
     */
    @SubscribeEvent
    public void doubleBlockDrop(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Block blocks = event.getState().getBlock();

        CompoundTag entityData = player.getPersistentData();
        CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
        entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

        if (player.getMainHandItem().getItem() instanceof PickaxeItem ||
                player.getOffhandItem().getItem() instanceof PickaxeItem) {
            if (blocks instanceof OreBlock || blocks instanceof RedStoneOreBlock) {
                if (persistedData.getInt("block_break_data") == 500) {
                    persistedData.putBoolean("mining_double_drops", true);

                    ModNetwork.CHANNEL.sendToServer(new DoubleDropsPacket(true));
                    ModNetwork.CHANNEL.sendToServer(new SendToastPacket(3));

                } else if (persistedData.getBoolean("mining_double_drops")) {
                    Block blockBroken = event.getState().getBlock();
                    BlockPos blockPos = event.getPos();
                    ItemStack itemStack = new ItemStack(blockBroken.asItem());

                    if (percentChance(0.25)) {

                        if (player.level instanceof ServerLevel) {
                            ServerLevel serverLevel = (ServerLevel) player.level;

                            serverLevel.sendParticles(ParticleTypes.SMALL_FLAME, blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), 15, 0.2D, 0.2D, 0.2D, 0.0D);
                        }

                        player.addItem(itemStack);

                        String unlockedAbility = ModUtil.renderColoredText(
                                "&9You have been rewarded with a extra " + blockBroken.getName().getString() + ".");
                        player.sendMessage(new TextComponent(unlockedAbility), player.getUUID());
                    }
                }
            }
        }
    }

    /**
     * Gives a % chance.
     */
    public static Boolean percentChance(double chance) {
        return Math.random() <= chance;
    }
}
