package me.maxish0t.mod.common.registry;

import me.maxish0t.mod.common.data.loot.OresLootModifier;
import me.maxish0t.mod.common.data.loot.StackAddLootModifier;
import me.maxish0t.mod.utilities.ModReference;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public class ModLootModifiers {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, ModReference.MODID);

    public static final RegistryObject<GlobalLootModifierSerializer<?>> ORE_LOOT_MODIFIER = LOOT_MODIFIERS.register("ore_loot_modifier", OresLootModifier.Serializer::new);

    // Add a dropped item
    public static final RegistryObject<GlobalLootModifierSerializer<?>> STACK_ADD = LOOT_MODIFIERS.register("stack_add", StackAddLootModifier.Serializer::new);
}
