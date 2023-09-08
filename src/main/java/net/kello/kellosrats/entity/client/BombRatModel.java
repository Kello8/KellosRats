package net.kello.kellosrats.entity.client;

import net.kello.kellosrats.KellosRats;
import net.kello.kellosrats.entity.custom.BombRatEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BombRatModel extends GeoModel<BombRatEntity> {
    @Override
    public ResourceLocation getModelResource(BombRatEntity animatable) {
        return new ResourceLocation(KellosRats
                .MOD_ID, "geo/bomb_rat.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BombRatEntity animatable) {
        return new ResourceLocation(KellosRats.MOD_ID, "textures/entity/bomb_rat.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BombRatEntity animatable) {
        return new ResourceLocation(KellosRats.MOD_ID, "animations/bomb_rat.animation.json");
    }

    @Override
    public void setCustomAnimations(BombRatEntity animatable, long instanceId, AnimationState<BombRatEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}