package net.azo.zoeysadditions.entity.client;

import com.google.common.collect.Maps;
import net.azo.zoeysadditions.ZoeysAdditions;
import net.azo.zoeysadditions.entity.custom.LadyBeetleEntity;
import net.azo.zoeysadditions.entity.custom.variant.LadyBeetleVariant;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class LadyBeetleRenderer extends GeoEntityRenderer<LadyBeetleEntity> {
    public static final Map<LadyBeetleVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(LadyBeetleVariant.class), (map) -> {
                map.put(LadyBeetleVariant.ONE_DOT,
                        new Identifier(ZoeysAdditions.MOD_ID, "textures/entity/ladybeetle/ladybeetle_1.png"));
                map.put(LadyBeetleVariant.TWO_DOT,
                        new Identifier(ZoeysAdditions.MOD_ID, "textures/entity/ladybeetle/ladybeetle_2.png"));
                map.put(LadyBeetleVariant.THREE_DOT,
                        new Identifier(ZoeysAdditions.MOD_ID, "textures/entity/ladybeetle/ladybeetle_3.png"));
                map.put(LadyBeetleVariant.FOUR_DOT,
                        new Identifier(ZoeysAdditions.MOD_ID, "textures/entity/ladybeetle/ladybeetle_4.png"));
                map.put(LadyBeetleVariant.FIVE_DOT,
                        new Identifier(ZoeysAdditions.MOD_ID, "textures/entity/ladybeetle/ladybeetle_5.png"));
                map.put(LadyBeetleVariant.SIX_DOT,
                        new Identifier(ZoeysAdditions.MOD_ID, "textures/entity/ladybeetle/ladybeetle_6.png"));
                map.put(LadyBeetleVariant.TRANS,
                        new Identifier(ZoeysAdditions.MOD_ID, "textures/entity/ladybeetle/ladybeetle_trans.png"));
                map.put(LadyBeetleVariant.GOLD,
                        new Identifier(ZoeysAdditions.MOD_ID, "textures/entity/ladybeetle/ladybeetle_gold.png"));
            });
    public LadyBeetleRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new LadyBeetleModel());
        this.shadowRadius = 0.2f;
    }

    @Override
    public Identifier getTextureResource(LadyBeetleEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public RenderLayer getRenderType(LadyBeetleEntity animatable, float partialTick, MatrixStack poseStack,
                                     @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer,
                                     int packedLight, Identifier texture) {

        poseStack.scale(0.5f,0.5f,0.5f);
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
