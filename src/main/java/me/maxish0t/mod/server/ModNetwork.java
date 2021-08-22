package me.maxish0t.mod.server;

import me.maxish0t.mod.server.packets.mining.BlockBreakAmountPacket;
import me.maxish0t.mod.server.packets.mining.DoubleDropsPacket;
import me.maxish0t.mod.server.packets.mining.PickaxeSpeedPacket;
import me.maxish0t.mod.utilities.ModReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class ModNetwork {

    private static final String NETWORK_PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ModReference.MODID, "main"),
            () -> NETWORK_PROTOCOL_VERSION,
            NETWORK_PROTOCOL_VERSION::equals,
            NETWORK_PROTOCOL_VERSION::equals
    );

    public static void register() {
        int networkId = 0;
        // Client -> Server

        /**
         * Mining Packets
         */
        CHANNEL.registerMessage(networkId++,
                BlockBreakAmountPacket.class,
                BlockBreakAmountPacket::encode,
                BlockBreakAmountPacket::decode,
                BlockBreakAmountPacket::handle
        );
        CHANNEL.registerMessage(networkId++,
                DoubleDropsPacket.class,
                DoubleDropsPacket::encode,
                DoubleDropsPacket::decode,
                DoubleDropsPacket::handle
        );
        CHANNEL.registerMessage(networkId++,
                PickaxeSpeedPacket.class,
                PickaxeSpeedPacket::encode,
                PickaxeSpeedPacket::decode,
                PickaxeSpeedPacket::handle
        );
    }
}
