package me.maxish0t.mod.common.handlers;

import me.maxish0t.mod.server.ModNetwork;
import me.maxish0t.mod.server.packets.AbilityCoolDownPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.server.ServerLifecycleHooks;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ServerTickHandler {
    public static ConcurrentHashMap<UUID, Integer> playerBlastMiningCoolDown = new ConcurrentHashMap<UUID, Integer>();
    public static ServerPlayer serverPlayer;
    public static int blastMining = 0;

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

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

                    serverPlayer = server.getPlayerList().getPlayer(uuid);
                }

                if (serverPlayer != null) {
                    ModNetwork.CHANNEL.sendTo(new AbilityCoolDownPacket(), serverPlayer.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
                }

                break;
            }
            case END -> {
                break;
            }
        }
    }
}
