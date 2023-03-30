package net.azo.zoeysadditions;

import net.azo.zoeysadditions.block.ModBlocks;
import net.azo.zoeysadditions.entity.ModEntities;
import net.azo.zoeysadditions.entity.client.LadyBeetleRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;

public class ZoeysAdditionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RASPBERRY_BUSH_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOYA_CROP, RenderLayer.getCutout());
        EntityRendererRegistry.register(ModEntities.LADY_BEETLE, LadyBeetleRenderer::new);

        //Colour Registry Stuff
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return FoliageColors.getColor(0.5, 1.0);
            }
            return BiomeColors.getGrassColor(world, pos);
        }, ModBlocks.RASPBERRY_BUSH_BLOCK);
    }
}
