package net.azo.zoeysadditions;

import net.azo.zoeysadditions.block.ModBlocks;
import net.azo.zoeysadditions.item.ModItems;
import net.azo.zoeysadditions.world.feature.ModConfiguredFeatures;
import net.azo.zoeysadditions.world.gen.ModOreGeneration;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZoeysAdditions implements ModInitializer {
	public static final String MOD_ID = "zoeysadditions";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {
		ModConfiguredFeatures.registerConfiguredFeatures();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModOreGeneration.generateOres();
	}
}
