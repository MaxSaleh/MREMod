package me.maxish0t.mod.server.packets.mining;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class SuperBreakerNeededPacket {
    public static int amount;

    public SuperBreakerNeededPacket() { }

    public SuperBreakerNeededPacket(int amount) {
        this.amount = amount;
    }

    public static void encode(final SuperBreakerNeededPacket msg, final FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.amount);
    }

    public static SuperBreakerNeededPacket decode(final FriendlyByteBuf packetBuffer) {
        amount = packetBuffer.readInt();
        return new SuperBreakerNeededPacket(amount);
    }

    public static void handle(final SuperBreakerNeededPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            final ServerPlayer player = context.getSender();

            if (player != null) {

            }
        });
        context.setPacketHandled(true);
    }
}
