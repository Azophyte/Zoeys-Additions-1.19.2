package net.azo.zoeysadditions.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.azo.zoeysadditions.ZoeysAdditions;
import net.azo.zoeysadditions.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
    public static BlockEntityType<SoyaSeparatorBlockEntity> SOYA_SEPARATOR;

    public static void registerBlockEntities() {
        SOYA_SEPARATOR = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(ZoeysAdditions.MOD_ID, "soya_separator"),
                FabricBlockEntityTypeBuilder.create(SoyaSeparatorBlockEntity::new,
                        ModBlocks.SOYA_SEPARATOR_BLOCK).build(null));
    }
}