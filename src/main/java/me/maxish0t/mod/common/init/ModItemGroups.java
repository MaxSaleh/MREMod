package me.maxish0t.mod.common.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {
    public static final CreativeModeTab MRE_MOD_ITEMS = (new CreativeModeTab("mre.items") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.MRE_PICKAXE.get());
        }
    });
}
