package me.maxish0t.mod;

import me.maxish0t.mod.client.gui.overlay.MiningAbilities;
import me.maxish0t.mod.client.handler.KeyInputHandler;
import me.maxish0t.mod.common.commands.ResetDataCommand;
import me.maxish0t.mod.common.commands.SetLevelCommand;
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
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        MinecraftForge.EVENT_BUS.register(new MiningAbilities());
        MinecraftForge.EVENT_BUS.register(new MiningEvents());
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        ResetDataCommand.register(event.getDispatcher());
        SetLevelCommand.register(event.getDispatcher());
    }
}
