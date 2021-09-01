package me.maxish0t.mod.common.item;

import me.maxish0t.mod.common.init.ModItemGroups;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

import java.util.Collection;
import java.util.Collections;

public class MPickaxeItem extends PickaxeItem {

    public MPickaxeItem(Tier tier, int p_42962_, float p_42963_, Properties properties) {
        super(tier, p_42962_, p_42963_, properties);
    }

    @Override
    public Collection<CreativeModeTab> getCreativeTabs() {
        return Collections.singletonList(ModItemGroups.MRE_MOD_ITEMS);
    }
}
