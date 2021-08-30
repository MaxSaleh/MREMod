package me.maxish0t.mod.server;

import me.maxish0t.mod.server.packets.SendToastPacket;
import me.maxish0t.mod.server.packets.UpdateLevelPacket;
import me.maxish0t.mod.server.packets.mining.*;
import me.maxish0t.mod.utilities.ModReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class ModNetwork {

    private static final String NETWORK_PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ModReference.MOD_ID, "main"),
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
        CHANNEL.registerMessage(networkId++,
                SendToastPacket.class,
                SendToastPacket::encode,
                SendToastPacket::decode,
                SendToastPacket::handle
        );
        CHANNEL.registerMessage(networkId++,
                SuperBreakerNeededPacket.class,
                SuperBreakerNeededPacket::encode,
                SuperBreakerNeededPacket::decode,
                SuperBreakerNeededPacket::handle
        );
        CHANNEL.registerMessage(networkId++,
                UpdateLevelPacket.class,
                UpdateLevelPacket::encode,
                UpdateLevelPacket::decode,
                UpdateLevelPacket::handle
        );
        CHANNEL.registerMessage(networkId++,
                MiningAbilitiesPacket.class,
                MiningAbilitiesPacket::encode,
                MiningAbilitiesPacket::decode,
                MiningAbilitiesPacket::handle
        );
    }
}
