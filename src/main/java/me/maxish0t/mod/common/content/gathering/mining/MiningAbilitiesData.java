package me.maxish0t.mod.common.content.gathering.mining;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class MiningAbilitiesData {

    /**
     * Creates the abilities.
     */
    public void createAbilitiesInt(MiningAbilitiesEnum ability, Player player) {
        getPersistentData(player).putInt(ability.name(), 0);
    }
    public void createAbilitiesBoolean(MiningAbilitiesEnum ability, Player player) {
        getPersistentData(player).putBoolean(ability.name(), false);
    }

    /**
     * Sets the value of a certain ability.
     */
    public void setAbilitiesInt(MiningAbilitiesEnum ability, Player player, int value) {
        getPersistentData(player).putInt(ability.name(), getPersistentData(player).getInt(ability.toString()) + value);
    }
    public void setAbilitiesBoolean(MiningAbilitiesEnum ability, Player player, boolean value) {
        getPersistentData(player).putBoolean(ability.name(), value);
    }

    /**
     * Gets the value of a certain ability.
     */
    public int getAbilitiesInt(MiningAbilitiesEnum ability, Player player) {
        return getPersistentData(player).getInt(ability.name());
    }
    public boolean getAbilitiesBoolean(MiningAbilitiesEnum ability, Player player) {
        return getPersistentData(player).getBoolean(ability.name());
    }

    /**
     * Gets the correct player data.
     */
    public CompoundTag getPersistentData(Player player) {
        CompoundTag entityData = player.getPersistentData();
        CompoundTag persistedData = entityData.getCompound(Player.PERSISTED_NBT_TAG);
        entityData.put(Player.PERSISTED_NBT_TAG, persistedData);

        return persistedData;
    }
}
