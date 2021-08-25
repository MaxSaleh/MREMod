package me.maxish0t.mod.server.packets;

import me.maxish0t.mod.client.gui.components.toasts.MREToast;
import me.maxish0t.mod.server.packets.mining.PickaxeSpeedPacket;
import me.maxish0t.mod.utilities.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class SendToastPacket {
    public static int id;

    public SendToastPacket() { }

    public SendToastPacket(int id) {
        this.id = id;
    }

    public static void encode(final SendToastPacket msg, final FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.id);
    }

    public static SendToastPacket decode(final FriendlyByteBuf packetBuffer) {
        id = packetBuffer.readInt();
        return new SendToastPacket(id);
    }

    // Client Side
    public static void handle(final SendToastPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context context = contextSupplier.get();
        final Minecraft minecraft = Minecraft.getInstance();

        context.enqueueWork(() -> {
            final Player player = context.getSender();

            if (player != null) {

                // Mining Level Ability Unlocked
                if (SendToastPacket.id == 1) {
                    MREToast mreToast = new MREToast(ModUtil.renderColoredText("&5&lABILITY UNLOCKED!"),
                            ModUtil.renderColoredText("&bMining Speed &1+&b5%"));

                    if (minecraft.getToasts().getToast(MREToast.class, mreToast.getToken()) == null) {
                        minecraft.getToasts().addToast(mreToast);
                    }
                }

                // Mining Level Ability Level Hit
                if (SendToastPacket.id == 2) {
                    MREToast mreToast = new MREToast(ModUtil.renderColoredText("&5&lPICKAXE SPEED"),
                            ModUtil.renderColoredText("&bIncreased to &1+&b" + PickaxeSpeedPacket.increasePercentage + "%"));

                    if (minecraft.getToasts().getToast(MREToast.class, mreToast.getToken()) == null) {
                        minecraft.getToasts().addToast(mreToast);
                    }
                }

                // Mining Double Block Drops
                if (SendToastPacket.id == 3) {
                    MREToast mreToast = new MREToast(ModUtil.renderColoredText("&5&lABILITY UNLOCKED!"),
                            ModUtil.renderColoredText("&125% &bof ore doubling"));

                    if (minecraft.getToasts().getToast(MREToast.class, mreToast.getToken()) == null) {
                        minecraft.getToasts().addToast(mreToast);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
