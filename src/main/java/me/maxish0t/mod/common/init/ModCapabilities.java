package me.maxish0t.mod.common.init;

import me.maxish0t.mod.common.capability.level.CapabilityLevelHandler;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModCapabilities {

    @SubscribeEvent
    public void registerCapability(RegisterCapabilitiesEvent event) {
        CapabilityLevelHandler.register(event);
    }
}
