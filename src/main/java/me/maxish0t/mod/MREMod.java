package me.maxish0t.mod;

import me.maxish0t.mod.client.gui.overlay.MiningOverlay;
import me.maxish0t.mod.client.handler.KeyInputHandler;
import me.maxish0t.mod.common.capability.ModCapabilities;
import me.maxish0t.mod.common.capability.level.CapabilityLevelHandler;
import me.maxish0t.mod.common.commands.ResetDataCommand;
import me.maxish0t.mod.common.commands.SetLevelCommand;
import me.maxish0t.mod.common.content.gathering.mining.MiningAbilitiesEvents;
import me.maxish0t.mod.common.content.gathering.mining.MiningEvents;
import me.maxish0t.mod.server.ModNetwork;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("mre")
public class MREMod {

    public MREMod() {
        ModNetwork.register();
        MinecraftForge.EVENT_BUS.register(this);

        // Key Input
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());

        // Overlays
        MinecraftForge.EVENT_BUS.register(new MiningOverlay());

        // Mining Player Content
        MinecraftForge.EVENT_BUS.register(new MiningEvents());
        MinecraftForge.EVENT_BUS.register(new MiningAbilitiesEvents());

        // Capabilities
        MinecraftForge.EVENT_BUS.register(new ModCapabilities());
        MinecraftForge.EVENT_BUS.register(new CapabilityLevelHandler());
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        ResetDataCommand.register(event.getDispatcher());
        SetLevelCommand.register(event.getDispatcher());
    }
}
