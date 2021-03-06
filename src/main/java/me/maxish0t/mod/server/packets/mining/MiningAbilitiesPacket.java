package me.maxish0t.mod.server.packets.mining;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class MiningAbilitiesPacket {

    public static int idAbility;
    public static boolean unlocked;

    public static boolean unlockedSuperBreaker;
    public static boolean unlockedDoubleOres;
    public static boolean unlockedBlastMining;

    public MiningAbilitiesPacket() { }

    public MiningAbilitiesPacket(int idAbility, boolean unlocked) {
        this.idAbility = idAbility;
        this.unlocked = unlocked;

        if (idAbility == 1) {
            if (unlocked) {
                unlockedSuperBreaker = true;
            } else {
                unlockedSuperBreaker = false;
            }
        } else if (idAbility == 2) {
            if (unlocked) {
                unlockedDoubleOres = true;
            } else {
                unlockedDoubleOres = false;
            }
        } else if (idAbility == 3) {
            if (unlocked) {
                unlockedBlastMining = true;
            } else {
                unlockedBlastMining = false;
            }
        }
    }

    public static void encode(final MiningAbilitiesPacket msg, final FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.idAbility);
        packetBuffer.writeBoolean(msg.unlocked);

        packetBuffer.writeBoolean(msg.unlockedSuperBreaker);
        packetBuffer.writeBoolean(msg.unlockedDoubleOres);
    }

    public static MiningAbilitiesPacket decode(final FriendlyByteBuf packetBuffer) {
        idAbility = packetBuffer.readInt();
        unlocked = packetBuffer.readBoolean();

        unlockedSuperBreaker = packetBuffer.readBoolean();
        unlockedDoubleOres = packetBuffer.readBoolean();

        return new MiningAbilitiesPacket(idAbility, unlocked);
    }

    public static void handle(final MiningAbilitiesPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {

        });
        context.setPacketHandled(true);
    }
}
