package net.azo.zoeysadditions.entity.client;

import net.azo.zoeysadditions.ZoeysAdditions;
import net.azo.zoeysadditions.entity.custom.LadyBeetleEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LadyBeetleModel extends AnimatedGeoModel <LadyBeetleEntity> {
    @Override
    public Identifier getModelResource(LadyBeetleEntity object) {
        return new Identifier(ZoeysAdditions.MOD_ID, "geo/ladybeetle.geo.json");
    }

    @Override
    public Identifier getTextureResource(LadyBeetleEntity object) {
        return new Identifier(ZoeysAdditions.MOD_ID, "textures/entity/ladybeetle/ladybeetle_5.png");
    }

    @Override
    public Identifier getAnimationResource(LadyBeetleEntity animatable) {
        return new Identifier(ZoeysAdditions.MOD_ID, "animations/ladybeetle.animation.json");
    }
}
