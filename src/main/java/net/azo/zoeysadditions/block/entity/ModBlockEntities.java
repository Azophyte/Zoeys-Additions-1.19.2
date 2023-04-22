package net.azo.zoeysadditions.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.azo.zoeysadditions.ZoeysAdditions;
import net.azo.zoeysadditions.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
    public static BlockEntityType<SoyaSeparatorCopperBlockEntity> SOYA_SEPARATOR_COPPER;
    public static BlockEntityType<SoyaSeparatorIronBlockEntity> SOYA_SEPARATOR_IRON;
    public static BlockEntityType<SoyaSeparatorGoldBlockEntity> SOYA_SEPARATOR_GOLD;
    public static BlockEntityType<SoyaSeparatorNetheriteBlockEntity> SOYA_SEPARATOR_NETHERITE;
    public static BlockEntityType<BambooBrewingStandBlockEntity> BAMBOO_BREWING_STAND;

    public static BlockEntityType<JigglyCakeBlockEntity> JIGGLY_CAKE;

    public static void registerBlockEntities() {
        SOYA_SEPARATOR_COPPER = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(ZoeysAdditions.MOD_ID, "soya_separator_copper"),
                FabricBlockEntityTypeBuilder.create(SoyaSeparatorCopperBlockEntity::new,
                        ModBlocks.SOYA_SEPARATOR_COPPER).build(null));

        SOYA_SEPARATOR_IRON = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(ZoeysAdditions.MOD_ID, "soya_separator_iron"),
                FabricBlockEntityTypeBuilder.create(SoyaSeparatorIronBlockEntity::new,
                        ModBlocks.SOYA_SEPARATOR_IRON).build(null));

        SOYA_SEPARATOR_GOLD = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(ZoeysAdditions.MOD_ID, "soya_separator_gold"),
                FabricBlockEntityTypeBuilder.create(SoyaSeparatorGoldBlockEntity::new,
                        ModBlocks.SOYA_SEPARATOR_GOLD).build(null));

        SOYA_SEPARATOR_NETHERITE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(ZoeysAdditions.MOD_ID, "soya_separator_netherite"),
                FabricBlockEntityTypeBuilder.create(SoyaSeparatorNetheriteBlockEntity::new,
                        ModBlocks.SOYA_SEPARATOR_NETHERITE).build(null));

        BAMBOO_BREWING_STAND = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(ZoeysAdditions.MOD_ID, "bamboo_blaze_stand"),
                FabricBlockEntityTypeBuilder.create(BambooBrewingStandBlockEntity::new,
                        ModBlocks.BAMBOO_BREWING_STAND).build(null));

        JIGGLY_CAKE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(ZoeysAdditions.MOD_ID, "jiggly_cake"),
                FabricBlockEntityTypeBuilder.create(JigglyCakeBlockEntity::new,
                        ModBlocks.JIGGLY_CAKE_BLOCK).build(null));


    }
}