package me.maxish0t.mod.common.handlers;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ServerTickHandler {
    public static ConcurrentHashMap<UUID, Integer> playerBlastMiningCoolDown = new ConcurrentHashMap<UUID, Integer>();
    public static int blastMining = 0;

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        switch (event.phase) {
            case START -> {

                // Blast Mining CoolDown
                for (UUID uuid : playerBlastMiningCoolDown.keySet()) {
                    blastMining += 1;
                    int value = playerBlastMiningCoolDown.get(uuid) - 1;

                    if (value <= 0) {
                        playerBlastMiningCoolDown.remove(uuid);
                    } else {
                        playerBlastMiningCoolDown.replace(uuid, value);
                    }
                }
                break;
            }
            case END -> {
                break;
            }
        }
    }
}
