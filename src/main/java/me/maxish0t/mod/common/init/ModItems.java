package me.maxish0t.mod.common.init;

import me.maxish0t.mod.common.item.MPickaxeItem;
import me.maxish0t.mod.utilities.ModReference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModReference.MOD_ID);

    // Pickaxe
    public static RegistryObject<Item> MRE_PICKAXE = ITEMS.register("mre_pickaxe", () -> new MPickaxeItem(
            Tiers.DIAMOND, 1, -2.8F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));

    public static void register() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(eventBus);
    }
}
