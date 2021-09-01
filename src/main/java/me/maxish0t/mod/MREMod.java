package me.maxish0t.mod;

import me.maxish0t.mod.client.handler.KeyInputHandler;
import me.maxish0t.mod.common.capability.ModCapabilities;
import me.maxish0t.mod.common.capability.level.CapabilityLevelHandler;
import me.maxish0t.mod.common.commands.ResetDataCommand;
import me.maxish0t.mod.common.commands.SetLevelCommand;
import me.maxish0t.mod.common.content.gathering.mining.CommonMiningEvents;
import me.maxish0t.mod.common.content.gathering.mining.MiningEvents;
import me.maxish0t.mod.common.entity.RegisterEntities;
import me.maxish0t.mod.common.handlers.ServerTickHandler;
import me.maxish0t.mod.common.init.ModItems;
import me.maxish0t.mod.server.ModNetwork;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("mre")
public class MREMod {

    public MREMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        // Get an instance of the mod event bus
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModNetwork.register();
        MinecraftForge.EVENT_BUS.register(this);

        // Mining Player Content
        MinecraftForge.EVENT_BUS.register(new MiningEvents());
        MinecraftForge.EVENT_BUS.register(new CommonMiningEvents());

        // Capabilities
        MinecraftForge.EVENT_BUS.register(new ModCapabilities());
        MinecraftForge.EVENT_BUS.register(new CapabilityLevelHandler());

        // Server Side Stuff
        MinecraftForge.EVENT_BUS.register(new ServerTickHandler());
        RegisterEntities.register();
        ModItems.register();
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        ResetDataCommand.register(event.getDispatcher());
        SetLevelCommand.register(event.getDispatcher());
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        new KeyInputHandler();
        MinecraftForge.EVENT_BUS.register(KeyInputHandler.class);
    }
}
