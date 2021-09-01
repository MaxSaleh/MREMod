package me.maxish0t.mod.common.content.gathering.mining;

import me.maxish0t.mod.server.packets.mining.MiningAbilitiesPacket;
import me.maxish0t.mod.utilities.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonMiningEvents {

    /**
     * Super Breaker Ability
     */
    @SubscribeEvent
    public void onBlockBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        float originalSpeed = event.getOriginalSpeed();

        if (MiningAbilitiesPacket.unlockedSuperBreaker) {
            if (player.getMainHandItem().getItem() instanceof PickaxeItem ||
                    player.getOffhandItem().getItem() instanceof PickaxeItem) {
                event.setNewSpeed(ModUtil.increaseByPercentage(originalSpeed, 20F));
            }
        } else {
            event.setNewSpeed(originalSpeed);
        }
    }

    /**
     * Double Ores Ability
     */
    @SubscribeEvent
    public void onBlockDoubleDrop(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        BlockPos blockPos = event.getPos();

        if (MiningAbilitiesPacket.unlockedDoubleOres) {
            if (ModUtil.percentChance(0.15)) {
                if (player.level instanceof ServerLevel) {
                    ServerLevel serverLevel = (ServerLevel) player.level;

                    serverLevel.sendParticles(ParticleTypes.SMALL_FLAME, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 30, 0.2D, 0.2D, 0.2D, 0.2D);

                    Position position = new Position() {
                        @Override
                        public double x() {
                            return event.getPos().getX();
                        }

                        @Override
                        public double y() {
                            return event.getPos().getY();
                        }

                        @Override
                        public double z() {
                            return event.getPos().getZ();
                        }
                    };

                    DefaultDispenseItemBehavior.spawnItem(serverLevel, new ItemStack(event.getState().getBlock()), 6, player.getDirection(), position);
                }
            }
        }
    }
}
