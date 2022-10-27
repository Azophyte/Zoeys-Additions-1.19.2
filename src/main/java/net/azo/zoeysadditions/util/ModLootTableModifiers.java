package net.azo.zoeysadditions.util;

import net.azo.zoeysadditions.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {
    private static final Identifier JUNGLE_LEAVES_ID
            = new Identifier("minecraft", "blocks/jungle_leaves");

    public static void modifyLootTables() {

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(JUNGLE_LEAVES_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.035f)) //Drops from roughly one in 29 blocks (~twice per tree)
                        .with(ItemEntry.builder(ModItems.JUNGLE_TWIG))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            if(JUNGLE_LEAVES_ID.equals(id)) { //MAKE JUNGLE LEAVES RARELY DROP GILDED CINNAMON
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.0007f)) //Drops from roughly one in fifty trees
                        .with(ItemEntry.builder(ModItems.GILDED_CINNAMON_STICK))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            //TODO: Maybe make cinnamon sticks drop from burning leaves?

        });
    }
}
