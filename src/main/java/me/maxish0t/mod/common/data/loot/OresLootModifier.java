package me.maxish0t.mod.common.data.loot;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class OresLootModifier extends LootModifier {

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected OresLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (generatedLoot.isEmpty()) {
            ItemStack itemStack = new ItemStack(Items.DIAMOND);
            generatedLoot.add(itemStack);
        }

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<OresLootModifier> {

        @Override
        public OresLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] lootItemConditions) {
            return new OresLootModifier(lootItemConditions);
        }

        @Override
        public JsonObject write(OresLootModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
