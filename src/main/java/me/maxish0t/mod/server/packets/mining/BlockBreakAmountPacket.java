package me.maxish0t.mod.server.packets.mining;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class BlockBreakAmountPacket {
    public static int amount;

    public BlockBreakAmountPacket() { }

    public BlockBreakAmountPacket(int amount) {
        this.amount = amount;
    }

    public static void encode(final BlockBreakAmountPacket msg, final FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.amount);
    }

    public static BlockBreakAmountPacket decode(final FriendlyByteBuf packetBuffer) {
        amount = packetBuffer.readInt();
        return new BlockBreakAmountPacket(amount);
    }

    public static void handle(final BlockBreakAmountPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            final ServerPlayer player = context.getSender();

            if (player != null) {

            }
        });
        context.setPacketHandled(true);
    }
}
