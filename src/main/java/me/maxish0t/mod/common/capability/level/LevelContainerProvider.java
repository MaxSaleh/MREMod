package me.maxish0t.mod.common.capability.level;

import me.maxish0t.mod.common.capability.ModCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LevelContainerProvider implements INBTSerializable<CompoundTag>, ICapabilityProvider {

    private final LevelContainer container;

    public LevelContainerProvider(final LevelContainer levelContainer) {
        this.container = levelContainer;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityLevelHandler.CAPABILITY_LEVEL) {
            return (LazyOptional<T>)LazyOptional.of(() -> this.container);
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.container.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.container.deserializeNBT(nbt);
    }
}
