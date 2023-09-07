package net.kello.kellosrats.entity;

import net.kello.kellosrats.KellosRats;
import net.kello.kellosrats.entity.custom.RatEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, KellosRats.MOD_ID);

    public static final RegistryObject<EntityType<RatEntity>> RAT =
            ENTITY_TYPES.register("rat",
                    () -> EntityType.Builder.of(RatEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 0.3f)
                            .build(new ResourceLocation(KellosRats.MOD_ID, "rat").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}