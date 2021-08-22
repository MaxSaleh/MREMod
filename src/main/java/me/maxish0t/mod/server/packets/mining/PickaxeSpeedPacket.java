package me.maxish0t.mod.server.packets.mining;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class PickaxeSpeedPacket {
    public static int increasePercentage;

    public PickaxeSpeedPacket() { }

    public PickaxeSpeedPacket(int increasePercentage) {
        this.increasePercentage = increasePercentage;
    }

    public static void encode(final PickaxeSpeedPacket msg, final FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.increasePercentage);
    }

    public static PickaxeSpeedPacket decode(final FriendlyByteBuf packetBuffer) {
        increasePercentage = packetBuffer.readInt();
        return new PickaxeSpeedPacket(increasePercentage);
    }

    public static void handle(final PickaxeSpeedPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            final ServerPlayer player = context.getSender();

            if (player != null) {

            }
        });
        context.setPacketHandled(true);
    }
}
