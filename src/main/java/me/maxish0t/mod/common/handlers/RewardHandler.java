package me.maxish0t.mod.common.handlers;

import me.maxish0t.mod.server.ModNetwork;
import me.maxish0t.mod.server.packets.mining.DoubleDropsPacket;
import me.maxish0t.mod.server.packets.mining.PickaxeSpeedPacket;
import me.maxish0t.mod.utilities.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Arrays;
import java.util.List;

public class RewardHandler {
    private List<Integer> blockRewardNumbers = Arrays.asList(5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60);

    @SubscribeEvent
    public void rewardPlayer(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();

        CompoundTag entityData = player.getPersistentData();
        CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
        entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

        Block blocks = event.getState().getBlock();

        if (player.getMainHandItem().getItem() instanceof PickaxeItem ||
                player.getOffhandItem().getItem() instanceof PickaxeItem) {
            if (blocks instanceof OreBlock || blocks instanceof RedStoneOreBlock) {
                for (int i = 0; i < blockRewardNumbers.size(); i++) {
                    int amount = persistedData.getInt("block_break_data");
                    int breakSpeedPercentage = persistedData.getInt("pickaxe_speed_percentage");

                    if (amount == blockRewardNumbers.get(i)) {
                        breakSpeedPercentage = breakSpeedPercentage + 5;

                        if (breakSpeedPercentage != 5) {
                            String abilityMessage = ModUtil.renderColoredText(
                                    "&5&lPICKAXE SPEED " + "&9has been increased to " + breakSpeedPercentage + "%");
                            player.sendMessage(new TextComponent(abilityMessage), player.getUUID());
                        } else {
                            String unlockedAbility = ModUtil.renderColoredText(
                                    "&5&lNEW ABILITY UNLOCKED! " + "&9Pickaxe speed increased by 5%");
                            player.sendMessage(new TextComponent(unlockedAbility), player.getUUID());
                        }

                        persistedData.putInt("pickaxe_speed_percentage", breakSpeedPercentage);
                        ModNetwork.CHANNEL.sendToServer(new PickaxeSpeedPacket(breakSpeedPercentage));
                    }
                }
            }
        }
    }

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
                int breakSpeedPercentage = persistedData.getInt("pickaxe_speed_percentage");
                float getOrigSpeed = event.getOriginalSpeed();

                event.setNewSpeed(getOrigSpeed + breakSpeedPercentage);
            }
        }
    }

    @SubscribeEvent
    public void doubleBlockDrop(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();

        CompoundTag entityData = player.getPersistentData();
        CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
        entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

        Block blocks = event.getState().getBlock();

        if (player.getMainHandItem().getItem() instanceof PickaxeItem ||
                player.getOffhandItem().getItem() instanceof PickaxeItem) {
            if (blocks instanceof OreBlock || blocks instanceof RedStoneOreBlock) {
                if (persistedData.getInt("block_break_data") == 50) {
                    persistedData.putBoolean("block_double_drops", true);

                    String unlockedAbility = ModUtil.renderColoredText(
                            "&5&lNEW ABILITY UNLOCKED! " + "&925% of an ore double dropping.");
                    player.sendMessage(new TextComponent(unlockedAbility), player.getUUID());

                    ModNetwork.CHANNEL.sendToServer(new DoubleDropsPacket(true));

                } else if (persistedData.getBoolean("block_double_drops")) {
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

    public static Boolean percentChance(double chance) {
        return Math.random() <= chance;
    }
}
