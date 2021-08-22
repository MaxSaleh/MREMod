package me.maxish0t.mod.server.packets.mining;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class DoubleDropsPacket {
    public static boolean canDoubleDrop;

    public DoubleDropsPacket() { }

    public DoubleDropsPacket(boolean canDoubleDrop) {
        this.canDoubleDrop = canDoubleDrop;
    }

    public static void encode(final DoubleDropsPacket msg, final FriendlyByteBuf packetBuffer) {
        packetBuffer.writeBoolean(msg.canDoubleDrop);
    }

    public static DoubleDropsPacket decode(final FriendlyByteBuf packetBuffer) {
        canDoubleDrop = packetBuffer.readBoolean();
        return new DoubleDropsPacket(canDoubleDrop);
    }

    public static void handle(final DoubleDropsPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            final ServerPlayer player = context.getSender();

            if (player != null) {

            }
        });
        context.setPacketHandled(true);
    }
}
