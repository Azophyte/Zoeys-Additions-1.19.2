package net.azo.zoeysadditions.entity.client;

import net.azo.zoeysadditions.ZoeysAdditions;
import net.azo.zoeysadditions.entity.custom.LadyBeetleEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LadyBeetleRenderer extends GeoEntityRenderer<LadyBeetleEntity> {

    public LadyBeetleRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new LadyBeetleModel());
        this.shadowRadius = 0.2f;
    }

    @Override
    public Identifier getTextureResource(LadyBeetleEntity animatable) {
        return new Identifier(ZoeysAdditions.MOD_ID, "textures/entity/ladybeetle/ladybeetle_5.png");
    }

    @Override
    public RenderLayer getRenderType(LadyBeetleEntity animatable, float partialTick, MatrixStack poseStack,
                                     @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer,
                                     int packedLight, Identifier texture) {

        poseStack.scale(1f,1f,1f);
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
