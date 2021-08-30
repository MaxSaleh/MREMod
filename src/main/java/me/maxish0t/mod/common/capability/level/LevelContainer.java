package me.maxish0t.mod.common.capability.level;

import net.minecraft.nbt.CompoundTag;

public class LevelContainer implements ILevel {
    private float level;
    private String playerContentName;

    public LevelContainer(String playerContentName) {
        this.level = this.getMaxLevel();
        this.playerContentName = playerContentName;
    }

    @Override
    public String getPlayerContent() {
        return this.playerContentName;
    }

    @Override
    public float getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(float value) {
        this.level = Math.max(0, Math.min(value, this.getMaxLevel()));
    }

    @Override
    public void addLevel(float value) {
        this.setLevel(this.getLevel() + value);
    }

    @Override
    public float getMaxLevel() {
        return 100;
    }

    public CompoundTag serializeNBT() {
        final CompoundTag compoundTag = new CompoundTag();
        compoundTag.putFloat(playerContentName + "_level", this.level);
        return compoundTag;
    }

    public void deserializeNBT(final CompoundTag compoundTag) {
        this.level = compoundTag.getFloat(playerContentName + "_level");
    }
}
