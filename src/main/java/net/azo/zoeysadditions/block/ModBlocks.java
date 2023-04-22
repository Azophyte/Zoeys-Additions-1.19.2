package net.azo.zoeysadditions.block;

import net.azo.zoeysadditions.ZoeysAdditions;
import net.azo.zoeysadditions.block.custom.*;
import net.azo.zoeysadditions.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block RASPBERRY_RHODOLITE_BLOCK = registerBlock("raspberry_rhodolite_block",
            new Block(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).hardness(5f).requiresTool()), ModItemGroup.ZOEYSADDITIONS);

    public static final Block RASPBERRY_RHODOLITE_ORE = registerBlock("raspberry_rhodolite_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).hardness(2.5f).requiresTool(),
        UniformIntProvider.create(10, 20)), ModItemGroup.ZOEYSADDITIONS);

    public static final Block DEEPSLATE_RASPBERRY_RHODOLITE_ORE = registerBlock("deepslate_raspberry_rhodolite_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.DEEPSLATE).hardness(4f).requiresTool(),
                    UniformIntProvider.create(10, 20)), ModItemGroup.ZOEYSADDITIONS);


    public static final Block BENITOITE_BLOCK = registerBlock("benitoite_block",
            new Block(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).hardness(5f).requiresTool()), ModItemGroup.ZOEYSADDITIONS);

    public static final Block BENITOITE_ORE = registerBlock("benitoite_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).hardness(2.5f).requiresTool(),
                    UniformIntProvider.create(10, 20)), ModItemGroup.ZOEYSADDITIONS);

    public static final Block DEEPSLATE_BENITOITE_ORE = registerBlock("deepslate_benitoite_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.DEEPSLATE).hardness(4f).requiresTool(),
                    UniformIntProvider.create(10, 20)), ModItemGroup.ZOEYSADDITIONS);

    public static final Block RASPBERRY_BUSH_BLOCK = registerBlockWithoutItem("raspberry_bush",
            new RaspberryBushBlock(FabricBlockSettings.copyOf(Blocks.SWEET_BERRY_BUSH)));

    public static final Block SOYA_CROP = registerBlockWithoutItem("soya_crop",
            new SoyaCrop(FabricBlockSettings.copyOf(Blocks.POTATOES)));

    /* Soya Separators */
    public static final Block SOYA_SEPARATOR_COPPER = registerBlock("soya_separator_copper",
            new SoyaSeparatorBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE)
                    .hardness(1.8f).requiresTool()), ModItemGroup.ZOEYSADDITIONS);

    public static final Block SOYA_SEPARATOR_IRON = registerBlock("soya_separator_iron",
            new SoyaSeparatorBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE)
                    .hardness(1.8f).requiresTool()), ModItemGroup.ZOEYSADDITIONS);

    public static final Block SOYA_SEPARATOR_GOLD = registerBlock("soya_separator_gold",
            new SoyaSeparatorBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE)
                    .hardness(1.8f).requiresTool()), ModItemGroup.ZOEYSADDITIONS);

    public static final Block SOYA_SEPARATOR_NETHERITE = registerBlock("soya_separator_netherite",
            new SoyaSeparatorBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE)
                    .hardness(1.8f).requiresTool()), ModItemGroup.ZOEYSADDITIONS);

    public static final Block CHORUS_CAKE_BLOCK = registerBlock("chorus_cake",
            new CakeBlock((FabricBlockSettings.of(Material.CAKE).sounds(BlockSoundGroup.WOOL))),
            ModItemGroup.ZOEYSADDITIONS);

    public static final Block BLAZING_BAMBOO_BLOCK = registerBlock("blazing_bamboo",
            new BlazingBambooBlock(FabricBlockSettings.of(Material.BAMBOO)
                    .sounds(BlockSoundGroup.BAMBOO).luminance(3)),
            ModItemGroup.ZOEYSADDITIONS);

    public static final Block BAMBOO_BREWING_STAND = registerBlock("bamboo_brewing_stand",
            new BambooBrewingStandBlock(FabricBlockSettings.of(Material.METAL)
    .requiresTool().strength(0.5F).luminance((state) -> 1).nonOpaque()), ModItemGroup.ZOEYSADDITIONS);

    public static final Block JIGGLY_CAKE_BLOCK = registerBlock("jiggly_cake",
            new JigglyCakeBlock(FabricBlockSettings.of(Material.CAKE)
                    .sounds(BlockSoundGroup.WOOL)
                    .strength(0.5F).nonOpaque()), ModItemGroup.ZOEYSADDITIONS);
    private static Block registerBlock(String name, Block block, ItemGroup tab) {
        registerBlockItem(name, block, tab);
        return Registry.register(Registry.BLOCK, new Identifier(ZoeysAdditions.MOD_ID, name), block);
    }
    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(ZoeysAdditions.MOD_ID, name), block);

    };
    private static Item registerBlockItem(String name, Block block, ItemGroup tab) {
        return Registry.register(Registry.ITEM, new Identifier(ZoeysAdditions.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(tab)));
    }
    public static void registerModBlocks() {
        ZoeysAdditions.LOGGER.debug("Registering ModBlocks for " + ZoeysAdditions.MOD_ID);
    }
}
