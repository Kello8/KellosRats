package net.kello.kellosrats.entity.client;

import net.kello.kellosrats.KellosRats;
import net.kello.kellosrats.entity.custom.SnowRatEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SnowRatModel extends GeoModel<SnowRatEntity> {
    @Override
    public ResourceLocation getModelResource(SnowRatEntity animatable) {
        return new ResourceLocation(KellosRats
                .MOD_ID, "geo/snow_rat.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SnowRatEntity animatable) {
        return new ResourceLocation(KellosRats.MOD_ID, "textures/entity/snow_rat.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SnowRatEntity animatable) {
        return new ResourceLocation(KellosRats.MOD_ID, "animations/snow_rat.animation.json");
    }

    @Override
    public void setCustomAnimations(SnowRatEntity animatable, long instanceId, AnimationState<SnowRatEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}