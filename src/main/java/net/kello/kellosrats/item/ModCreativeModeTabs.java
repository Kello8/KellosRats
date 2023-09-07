package net.kello.kellosrats.item;

import net.kello.kellosrats.KellosRats;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, KellosRats.MOD_ID);
    public static RegistryObject<CreativeModeTab> KELLOS_RATS = CREATIVE_MODE_TABS.register("kellos_rats", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RAT.get()))
                    .title(Component.translatable("creativemodetab.kellos_rats")).build());

        public static void register(IEventBus eventBus) {
            CREATIVE_MODE_TABS.register(eventBus);
    }
}