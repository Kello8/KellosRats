package net.kello.kellosrats.entity.client;

import net.kello.kellosrats.KellosRats;
import net.kello.kellosrats.entity.custom.UnstableRatEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class UnstableRatModel extends GeoModel<UnstableRatEntity> {
    @Override
    public ResourceLocation getModelResource(UnstableRatEntity animatable) {
        return new ResourceLocation(KellosRats
                .MOD_ID, "geo/unstable_rat.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(UnstableRatEntity animatable) {
        return new ResourceLocation(KellosRats.MOD_ID, "textures/entity/unstable_rat.png");
    }

    @Override
    public ResourceLocation getAnimationResource(UnstableRatEntity animatable) {
        return new ResourceLocation(KellosRats.MOD_ID, "animations/unstable_rat.animation.json");
    }

    @Override
    public void setCustomAnimations(UnstableRatEntity animatable, long instanceId, AnimationState<UnstableRatEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}