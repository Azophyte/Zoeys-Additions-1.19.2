package net.azo.zoeysadditions.entity;

import net.azo.zoeysadditions.ZoeysAdditions;
import net.azo.zoeysadditions.entity.custom.LadyBeetleEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {
        public static final EntityType<LadyBeetleEntity> LADY_BEETLE = Registry.register(
                Registry.ENTITY_TYPE, new Identifier(ZoeysAdditions.MOD_ID, "ladybeetle"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, LadyBeetleEntity::new)
                        .dimensions(EntityDimensions.fixed(0.45f, 0.15f)).build());

    }
