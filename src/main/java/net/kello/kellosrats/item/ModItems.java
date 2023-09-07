package net.kello.kellosrats.item;

import net.kello.kellosrats.KellosRats;
import net.kello.kellosrats.entity.ModEntities;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, KellosRats.MOD_ID);

    public static final RegistryObject<Item> RAT = ITEMS.register("rat",
            () -> new ForgeSpawnEggItem(ModEntities.RAT, 0x614023, 0x8f5d31,
                        new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> RAT_TAIL = ITEMS.register("rat_tail",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAT_FUR = ITEMS.register("rat_fur",
            () -> new Item(new Item.Properties()));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}