package me.maxish0t.mod.common.capability.level;

public interface ILevel
{
    String getPlayerContent();

    float getLevel();

    void setLevel(float value);

    void addLevel(float value);

    float getMaxLevel();
}
