package me.maxish0t.mod.common.capability.level;

import me.maxish0t.mod.server.ModNetwork;
import me.maxish0t.mod.server.packets.UpdateLevelPacket;
import me.maxish0t.mod.utilities.ModReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmllegacy.network.NetworkDirection;

public class CapabilityLevelHandler {

    @CapabilityInject(ILevel.class)
    public static Capability<ILevel> CAPABILITY_LEVEL = null;

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(ILevel.class);
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(ModReference.MODID, "mining"), new LevelContainerProvider(new LevelContainer("mining")));
        }
    }

    @SubscribeEvent
    public void onPlayerJoinWorld(final EntityJoinWorldEvent event) {
        if (event.getWorld().isClientSide || !(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        float currentLevel = player.getCapability(CapabilityLevelHandler.CAPABILITY_LEVEL, null).map(levels -> {
            return levels.getLevel();
        }).orElse(0F);

        if (player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ModNetwork.CHANNEL.sendTo(new UpdateLevelPacket(currentLevel), serverPlayer.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void setLevel(Player player, float value) {
        player.getCapability(CapabilityLevelHandler.CAPABILITY_LEVEL, null).ifPresent(levels -> {
            levels.setLevel(value);
        });

        float currentLevel = player.getCapability(CapabilityLevelHandler.CAPABILITY_LEVEL, null).map(levels -> {
            return levels.getLevel();
        }).orElse(0F);

        if (player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ModNetwork.CHANNEL.sendTo(new UpdateLevelPacket(currentLevel), serverPlayer.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void addLevel(Player player, float value) {
        player.getCapability(CapabilityLevelHandler.CAPABILITY_LEVEL, null).ifPresent(levels -> {
            levels.addLevel(value);
        });

        float currentLevel = player.getCapability(CapabilityLevelHandler.CAPABILITY_LEVEL, null).map(levels -> {
            return levels.getLevel();
        }).orElse(0F);

        if (player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ModNetwork.CHANNEL.sendTo(new UpdateLevelPacket(currentLevel), serverPlayer.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static float getLevelClient() {
        return UpdateLevelPacket.level;
    }
}
