package net.azo.zoeysadditions;

import net.azo.zoeysadditions.entity.ModEntities;
import net.azo.zoeysadditions.entity.client.LadyBeetleRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ZoeysAdditionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(ModEntities.LADY_BEETLE, LadyBeetleRenderer::new);

    }
}
