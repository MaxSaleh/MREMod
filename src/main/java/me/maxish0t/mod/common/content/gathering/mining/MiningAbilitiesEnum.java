package me.maxish0t.mod.common.content.gathering.mining;

import com.google.gson.annotations.SerializedName;

public enum MiningAbilitiesEnum {
    @SerializedName("mining_amount") MINING_AMOUNT("mining_amount", "Mining Amount"),
    @SerializedName("mining_speed") MINING_SPEED("mining_speed", "Mining Speed"),
    @SerializedName("mining_double_drops") MINING_DOUBLE_DROPS("mining_double_drops", "Double Drops");

    public String typeName;
    public String name;

    MiningAbilitiesEnum(String typeName, String name)
    {
        this.typeName = typeName;
        this.name = name;
    }
}
