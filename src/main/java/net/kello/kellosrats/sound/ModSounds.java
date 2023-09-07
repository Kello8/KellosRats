package net.kello.kellosrats.sound;

import net.kello.kellosrats.KellosRats;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, KellosRats.MOD_ID);

    public static final RegistryObject<SoundEvent> RAT_SQUEAK = registerSoundEvent("rat_squeak");
    public static final RegistryObject<SoundEvent> RAT_HURT = registerSoundEvent("rat_hurt");
    public static final RegistryObject<SoundEvent> RAT_DEATH = registerSoundEvent("rat_death");


    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(KellosRats.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}