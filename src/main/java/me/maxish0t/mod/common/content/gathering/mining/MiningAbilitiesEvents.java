package me.maxish0t.mod.common.content.gathering.mining;

import me.maxish0t.mod.common.capability.level.CapabilityLevelHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MiningAbilitiesEvents {

    private final double SUPER_BREAKER_AMOUNT = 50;
    private final double DOUBLE_ORES_AMOUNT = 75;

    private boolean unlockedSuperBreaker = false;
    private boolean unlockedDoubleOres = false;

    /**
     * Ability checks
     */
    @SubscribeEvent
    public void onPlayerEvent(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        float miningLevel = player.getCapability(CapabilityLevelHandler.CAPABILITY_LEVEL, null).map(levels -> {
            return levels.getLevel();
        }).orElse(0F);

        // Super Breaker Ability
        if (miningLevel >= SUPER_BREAKER_AMOUNT) {
            unlockedSuperBreaker = true;
        } else {
            unlockedSuperBreaker = false;
        }

        // Double Ores Ability
        if (miningLevel >= DOUBLE_ORES_AMOUNT) {
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

        if (unlockedSuperBreaker) {
            if (player.getMainHandItem().getItem() instanceof PickaxeItem ||
                    player.getOffhandItem().getItem() instanceof PickaxeItem) {
                float originalSpeed = event.getOriginalSpeed();
                event.setNewSpeed(originalSpeed + 200F);
            }
        }
    }
}
