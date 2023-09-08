package net.kello.kellosrats.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kello.kellosrats.KellosRats;
import net.kello.kellosrats.entity.custom.UnstableRatEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class UnstableRatRenderer extends GeoEntityRenderer<UnstableRatEntity> {
    public UnstableRatRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new UnstableRatModel());
    }

    @Override
    public ResourceLocation getTextureLocation(UnstableRatEntity animatable) {
        return new ResourceLocation(KellosRats.MOD_ID, "textures/entity/rat.png");
    }

    @Override
    public void render(UnstableRatEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}