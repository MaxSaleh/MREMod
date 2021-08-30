package me.maxish0t.mod.common.content.gathering.mining;

import me.maxish0t.mod.common.capability.level.CapabilityLevelHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.OreBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MiningEvents {

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();

        double max, min;
        if (event.getState().getBlock() instanceof OreBlock) {
            max = 0.01;
            min = 0.04;
        } else {
            max = 0.1; // 0.009
            min = 0.1; // 0.005
        }

        CapabilityLevelHandler.addLevel(player, (float) (Math.random() * (max - min) + min));
    }
}
