package net.azo.zoeysadditions.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryEntry<PlacedFeature> BENITOITE_ORE_PLACED = PlacedFeatures.register("benitoite_ore_placed",
            ModConfiguredFeatures.BENITOITE_ORE, modifiersWithCount(8, HeightRangePlacementModifier.uniform(YOffset.aboveBottom(8),
                    YOffset.aboveBottom(96))));
    public static final RegistryEntry<PlacedFeature> RASPBERRY_RHODOLITE_ORE_PLACED = PlacedFeatures.register("raspberry_rhodolite_ore_placed",
            ModConfiguredFeatures.RASPBERRY_RHODOLITE_ORE, modifiersWithCount(8, HeightRangePlacementModifier.uniform(YOffset.aboveBottom(8),
                    YOffset.aboveBottom(96))));

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }

    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }
}
