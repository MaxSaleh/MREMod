package me.maxish0t.mod.server.packets;

import me.maxish0t.mod.common.handlers.ServerTickHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityCoolDownPacket {
    public static int blastMiningCoolDownLeft;

    public AbilityCoolDownPacket() { }

    public static void encode(final AbilityCoolDownPacket msg, final FriendlyByteBuf packetBuffer) {
    }

    public static AbilityCoolDownPacket decode(final FriendlyByteBuf packetBuffer) {
        return new AbilityCoolDownPacket();
    }

    public static void handle(final AbilityCoolDownPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            final Player player = context.getSender();

            if (player != null && player instanceof ServerPlayer) {
                ServerPlayer serverPlayer = (ServerPlayer) player;

                if (ServerTickHandler.playerBlastMiningCoolDown.containsKey(serverPlayer.getUUID())) {
                    blastMiningCoolDownLeft = ServerTickHandler.playerBlastMiningCoolDown.get(serverPlayer.getUUID());
                }
            }
        });
        context.setPacketHandled(true);
    }
}
