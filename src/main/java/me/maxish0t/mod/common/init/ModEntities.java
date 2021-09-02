package me.maxish0t.mod.common.init;

import me.maxish0t.mod.common.entity.projectile.ThrownTnt;
import me.maxish0t.mod.utilities.ModReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ModReference.MOD_ID);

    public static RegistryObject<EntityType<ThrownTnt>> THROWN_TNT = ENTITIES.register("thrown_tnt", () -> EntityType.Builder.<ThrownTnt>of(ThrownTnt::new, MobCategory.MISC)
            .sized(0.25F, 0.25F)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build("mre:thrown_tnt"));

    public static void register() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ENTITIES.register(eventBus);
    }
}
