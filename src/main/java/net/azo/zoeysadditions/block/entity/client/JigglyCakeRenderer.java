package net.azo.zoeysadditions.block.entity.client;

import net.azo.zoeysadditions.block.entity.JigglyCakeBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class JigglyCakeRenderer extends GeoBlockRenderer<JigglyCakeBlockEntity> {
    public JigglyCakeRenderer(BlockEntityRendererFactory.Context context) {
        super(new JigglyCakeModel());
    }

    @Override
    public RenderLayer getRenderType(JigglyCakeBlockEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
