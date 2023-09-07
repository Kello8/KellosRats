package net.kello.kellosrats;

import com.mojang.logging.LogUtils;
import net.kello.kellosrats.entity.ModEntities;
import net.kello.kellosrats.entity.client.RatRenderer;
import net.kello.kellosrats.item.ModCreativeModeTabs;
import net.kello.kellosrats.item.ModItems;
import net.kello.kellosrats.sound.ModSounds;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(KellosRats.MOD_ID)
public class KellosRats {
        public static final String MOD_ID = "kellosrats";
        public static final Logger LOGGER = LogUtils.getLogger();

        public KellosRats() {
            IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

            modEventBus.addListener(this::commonSetup);

            ModCreativeModeTabs.register(modEventBus);
            ModItems.register(modEventBus);

            ModEntities.register(modEventBus);
            ModSounds.register(modEventBus);

            GeckoLib.initialize();

            MinecraftForge.EVENT_BUS.register(this);
            modEventBus.addListener(this::addCreative);
        }

        private void commonSetup(final FMLCommonSetupEvent event) {

        }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTab() == ModCreativeModeTabs.KELLOS_RATS.get()) {
            event.accept(ModItems.RAT);
            event.accept(ModItems.RAT_TAIL);
            event.accept(ModItems.RAT_FUR);
        }
    }

        @SubscribeEvent
        public void onServerStarting(ServerStartingEvent event) {

        }

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public static class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.RAT.get(), RatRenderer::new);
    }
}
}