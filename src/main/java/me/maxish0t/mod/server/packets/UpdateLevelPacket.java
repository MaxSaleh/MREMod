package me.maxish0t.mod.server.packets;

import me.maxish0t.mod.common.capability.level.CapabilityLevelHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateLevelPacket {
    public static float level;

    public UpdateLevelPacket() { }

    public UpdateLevelPacket(float level) {
        this.level = level;
    }

    public static void encode(final UpdateLevelPacket msg, final FriendlyByteBuf packetBuffer) {
        packetBuffer.writeFloat(msg.level);
    }

    public static UpdateLevelPacket decode(final FriendlyByteBuf packetBuffer) {
        level = packetBuffer.readFloat();
        return new UpdateLevelPacket(level);
    }

    public static void handle(final UpdateLevelPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            final Player player = context.getSender();

            if (player != null) {
                player.getCapability(CapabilityLevelHandler.CAPABILITY_LEVEL, null).ifPresent(levels -> {
                    levels.setLevel(msg.level);
                });
            }
        });
        context.setPacketHandled(true);
    }
}
