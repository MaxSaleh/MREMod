package me.maxish0t.mod.server;

import me.maxish0t.mod.server.packets.SendToastPacket;
import me.maxish0t.mod.server.packets.UpdateLevelPacket;
import me.maxish0t.mod.server.packets.mining.*;
import me.maxish0t.mod.utilities.ModReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

import java.util.Optional;

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

        /**
        CHANNEL.registerMessage(networkId++,
                SendToastPacket.class,
                SendToastPacket::encode,
                SendToastPacket::decode,
                SendToastPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );**/
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
                MiningAbilitiesPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
    }
}
