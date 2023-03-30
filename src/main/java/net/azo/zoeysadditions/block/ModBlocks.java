package net.azo.zoeysadditions.block;

import net.azo.zoeysadditions.ZoeysAdditions;
import net.azo.zoeysadditions.block.custom.RaspberryBushBlock;
import net.azo.zoeysadditions.block.custom.SoyaCrop;
import net.azo.zoeysadditions.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    //RASPBERRY RHODOLITE BLOCKS

    public static final Block RASPBERRY_RHODOLITE_BLOCK = registerBlock("raspberry_rhodolite_block",
            new Block(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(4f).requiresTool()), ModItemGroup.ZOEYSADDITIONS);
    //REQUIRES PICKAXE, UPDATE ME
    //I SHOULD HAVE CUSTOM SFX

    public static final Block RASPBERRY_RHODOLITE_ORE = registerBlock("raspberry_rhodolite_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool(),
        UniformIntProvider.create(10, 20)), ModItemGroup.ZOEYSADDITIONS);
    //REQUIRES PICKAXE, UPDATE ME
    //I SHOULD DROP RASPBERRY RHODOLITE

    public static final Block DEEPSLATE_RASPBERRY_RHODOLITE_ORE = registerBlock("deepslate_raspberry_rhodolite_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.DEEPSLATE).strength(4f).requiresTool(),
                    UniformIntProvider.create(10, 20)), ModItemGroup.ZOEYSADDITIONS);
    //REQUIRES PICKAXE, UPDATE ME
    //I SHOULD DROP RASPBERRY RHODOLITE

    //BENITOITE BLOCKS

    public static final Block BENITOITE_BLOCK = registerBlock("benitoite_block",
            new Block(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(4f).requiresTool()), ModItemGroup.ZOEYSADDITIONS);
    //REQUIRES PICKAXE, UPDATE ME
    //I SHOULD HAVE CUSTOM SFX

    public static final Block BENITOITE_ORE = registerBlock("benitoite_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool(),
                    UniformIntProvider.create(10, 20)), ModItemGroup.ZOEYSADDITIONS);
    //REQUIRES PICKAXE, UPDATE ME
    //I SHOULD DROP BENITOITE

    public static final Block DEEPSLATE_BENITOITE_ORE = registerBlock("deepslate_benitoite_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.DEEPSLATE).strength(4f).requiresTool(),
                    UniformIntProvider.create(10, 20)), ModItemGroup.ZOEYSADDITIONS);
    //REQUIRES PICKAXE, UPDATE ME
    //I SHOULD DROP BENITOITE

    public static final Block RASPBERRY_BUSH_BLOCK = registerBlockWithoutItem("raspberry_bush",
            new RaspberryBushBlock(FabricBlockSettings.copyOf(Blocks.SWEET_BERRY_BUSH)));

    public static final Block SOYA_CROP = registerBlockWithoutItem("soya_crop",
            new SoyaCrop(FabricBlockSettings.copyOf(Blocks.POTATOES)));


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
