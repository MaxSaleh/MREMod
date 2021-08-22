package me.maxish0t.mod.common.data.loot;

import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class StackAddLootModifier extends LootModifier {
    private final Item newItem;
    private final int stackMin;
    private final int stackMax;

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected StackAddLootModifier(LootItemCondition[] conditionsIn, Item replace, int stackMin, int stackMax) {
        super(conditionsIn);
        this.newItem = replace;
        this.stackMin = stackMin;
        this.stackMax = stackMax;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        // Get size of stack
        int finalMax = stackMax + context.getLootingModifier();
        int count = context.getLevel().random.nextInt(finalMax - stackMin + 1) + stackMin;
        // Add stack of new item if the size is bigger than 0
        if (count > 0)
            generatedLoot.add(new ItemStack(newItem, count));

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<StackAddLootModifier> {
        @Override
        public StackAddLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            Item add = ForgeRegistries.ITEMS.getValue(new ResourceLocation((JsonUtils.getStringOr("item", object, "item"))));
            int min = JsonUtils.getIntOr("min", object, 1);
            int max = JsonUtils.getIntOr("max", object, 1);
            return new StackAddLootModifier(ailootcondition, add, min, max);        }

        @Override
        public JsonObject write(StackAddLootModifier instance) {
            return null;
        }
    }
}
