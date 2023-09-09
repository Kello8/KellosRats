package net.kello.kellosrats.event;

import net.kello.kellosrats.KellosRats;
import net.kello.kellosrats.entity.ModEntities;
import net.kello.kellosrats.entity.custom.BombRatEntity;
import net.kello.kellosrats.entity.custom.RatEntity;
import net.kello.kellosrats.entity.custom.SnowRatEntity;
import net.kello.kellosrats.entity.custom.UnstableRatEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KellosRats.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.RAT.get(), RatEntity.setAttributes());
        event.put(ModEntities.SNOW_RAT.get(), SnowRatEntity.setAttributes());
        event.put(ModEntities.UNSTABLE_RAT.get(), UnstableRatEntity.setAttributes());
        event.put(ModEntities.BOMB_RAT.get(), BombRatEntity.setAttributes());
    }

    @SubscribeEvent
    public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.RAT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.SNOW_RAT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}