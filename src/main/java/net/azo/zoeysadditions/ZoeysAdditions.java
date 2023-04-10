package net.azo.zoeysadditions;

import net.azo.zoeysadditions.audio.ModSounds;
import net.azo.zoeysadditions.block.ModBlocks;
import net.azo.zoeysadditions.block.entity.ModBlockEntities;
import net.azo.zoeysadditions.entity.ModEntities;
import net.azo.zoeysadditions.entity.custom.LadyBeetleEntity;
import net.azo.zoeysadditions.item.ModItems;
import net.azo.zoeysadditions.screen.ModScreenHandlers;
import net.azo.zoeysadditions.util.ModLootTableModifiers;
import net.azo.zoeysadditions.world.feature.ModConfiguredFeatures;
import net.azo.zoeysadditions.world.gen.ModOreGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class ZoeysAdditions implements ModInitializer {
	public static final String MOD_ID = "zoeysadditions";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {
		ModConfiguredFeatures.registerConfiguredFeatures();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModOreGeneration.generateOres();
		ModLootTableModifiers.modifyLootTables();
		GeckoLib.initialize();
		FabricDefaultAttributeRegistry.register(ModEntities.LADY_BEETLE, LadyBeetleEntity.setAttributes());
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerAllScreenHandlers();
		ModSounds.registerModSounds();
	}
}
