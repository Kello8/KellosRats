package net.kello.kellosrats.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kello.kellosrats.KellosRats;
import net.kello.kellosrats.entity.custom.SnowRatEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SnowRatRenderer extends GeoEntityRenderer<SnowRatEntity> {
    public SnowRatRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SnowRatModel());
    }

    @Override
    public ResourceLocation getTextureLocation(SnowRatEntity animatable) {
        return new ResourceLocation(KellosRats.MOD_ID, "textures/entity/snow_rat.png");
    }

    @Override
    public void render(SnowRatEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.8f, 0.8f, 0.8f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}