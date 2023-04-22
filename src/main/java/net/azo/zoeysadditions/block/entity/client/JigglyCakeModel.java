package net.azo.zoeysadditions.block.entity.client;

import net.azo.zoeysadditions.ZoeysAdditions;
import net.azo.zoeysadditions.block.custom.JigglyCakeBlock;
import net.azo.zoeysadditions.block.entity.JigglyCakeBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JigglyCakeModel extends AnimatedGeoModel<JigglyCakeBlockEntity> {

    @Override
    public Identifier getModelResource(JigglyCakeBlockEntity object) {
        return new Identifier(ZoeysAdditions.MOD_ID, "geo/jiggly_cake.geo.json");
    }

    @Override
    public Identifier getTextureResource(JigglyCakeBlockEntity object) {
        return new Identifier(ZoeysAdditions.MOD_ID, "textures/block/jiggly_cake.png");
    }

    @Override
    public Identifier getAnimationResource(JigglyCakeBlockEntity animatable) {
        return new Identifier(ZoeysAdditions.MOD_ID, "animations/jiggly_cake.animation.json");
    }
}

