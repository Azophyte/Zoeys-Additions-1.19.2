package net.azo.zoeysadditions.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.azo.zoeysadditions.ZoeysAdditions;
import net.azo.zoeysadditions.block.ModBlocks;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class ModConfiguredFeatures {

    public static final List<OreFeatureConfig.Target> OVERWORLD_BENITOITE_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModBlocks.BENITOITE_ORE.getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_BENITOITE_ORE.getDefaultState()));

    public static final List<OreFeatureConfig.Target> OVERWORLD_RASPBERRY_RHODOLITE_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModBlocks.RASPBERRY_RHODOLITE_ORE.getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_RASPBERRY_RHODOLITE_ORE.getDefaultState()));

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> BENITOITE_ORE =
            ConfiguredFeatures.register("benitoite_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_BENITOITE_ORES, 3));

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> RASPBERRY_RHODOLITE_ORE =
            ConfiguredFeatures.register("raspberry_rhodolite_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_RASPBERRY_RHODOLITE_ORES, 3));
    public static void registerConfiguredFeatures() {
        ZoeysAdditions.LOGGER.debug("Registering the ModConfiguredFeatures for " + ZoeysAdditions.MOD_ID);

    }
}
