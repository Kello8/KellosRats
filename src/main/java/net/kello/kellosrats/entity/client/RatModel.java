package net.kello.kellosrats.entity.client;

import net.kello.kellosrats.KellosRats;
import net.kello.kellosrats.entity.custom.RatEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class RatModel extends GeoModel<RatEntity> {
    @Override
    public ResourceLocation getModelResource(RatEntity animatable) {
        return new ResourceLocation(KellosRats
                .MOD_ID, "geo/rat.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RatEntity animatable) {
        return new ResourceLocation(KellosRats.MOD_ID, "textures/entity/rat.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RatEntity animatable) {
        return new ResourceLocation(KellosRats.MOD_ID, "animations/rat.animation.json");
    }

    @Override
    public void setCustomAnimations(RatEntity animatable, long instanceId, AnimationState<RatEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}