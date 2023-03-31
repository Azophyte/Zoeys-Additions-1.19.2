package net.azo.zoeysadditions.item;

import net.azo.zoeysadditions.ZoeysAdditions;
import net.azo.zoeysadditions.block.ModBlocks;
import net.azo.zoeysadditions.entity.ModEntities;
import net.azo.zoeysadditions.item.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import static net.minecraft.entity.effect.StatusEffects.*;


public class ModItems {
    //FOOD ITEMS
    public static final Item RASPBERRY = registerItem("raspberry",
            new AliasedBlockItem(ModBlocks.RASPBERRY_BUSH_BLOCK, new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .food(new FoodComponent.Builder().hunger(1).saturationModifier(1).snack().build())));

    public static final Item CINNAMON_BUN = registerItem("cinnamon_bun",
            new Item(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .food(new FoodComponent.Builder().hunger(5).saturationModifier(1)
                            .statusEffect(new StatusEffectInstance(REGENERATION, 150, 0), 100).build())));

    public static final Item CANDY_APPLE = registerItem("candy_apple",
            new CandyAppleItem(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .food(new FoodComponent.Builder().hunger(7).saturationModifier(1).build())));

    public static final Item RASPBERRY_TEA = registerItem("raspberry_tea",
            new TeaItem(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(1)
                            .statusEffect(new StatusEffectInstance(REGENERATION, 150, 0), 100).build())));

    public static final Item HERBAL_TEA = registerItem("herbal_tea",
            new TeaItem(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .food(new FoodComponent.Builder().hunger(3).saturationModifier(1)
                            .statusEffect(new StatusEffectInstance(REGENERATION, 150, 0), 100).build())));

    public static final Item GILDED_TEA = registerItem("gilded_tea",
            new TeaItem(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .food(new FoodComponent.Builder().hunger(5).saturationModifier(2)
                            .statusEffect(new StatusEffectInstance(REGENERATION, 36000, 0), 100)
                            .statusEffect(new StatusEffectInstance(RESISTANCE, 36000, 0), 100)
                            .statusEffect(new StatusEffectInstance(HASTE, 36000, 0), 100)
                            .statusEffect(new StatusEffectInstance(HEALTH_BOOST, 36000, 0), 100)
                            .build())));

    public static final Item CHORUS_CAKE_SLICE = registerItem("chorus_cake_slice",
            new ChorusCakeSliceItem(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS).maxCount(1)
                    .food(new FoodComponent.Builder().hunger(20).saturationModifier(1)
                            .build())));

    public static final Item SOYA_MILK = registerItem("soya_milk",
            new SoyaMilkItem(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .food(new FoodComponent.Builder().hunger(1).saturationModifier(1).snack().build())));

    public static final Item HEAVY_CREAM = registerItem("heavy_cream",
            new HeavyCreamItem(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .food(new FoodComponent.Builder().hunger(1).saturationModifier(1).snack().build())));

    //MATERIALS
    public static final Item RASPBERRY_RHODOLITE = registerItem("raspberry_rhodolite",
            new Item(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS).fireproof()));

    public static final Item BENITOITE = registerItem("benitoite",
            new Item(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS).fireproof()));

    public static final Item CINNAMON_STICK = registerItem("cinnamon_stick",
            new Item(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)));

    public static final Item SOYA_BEANS = registerItem("soya_beans",
            new AliasedBlockItem(ModBlocks.SOYA_CROP, new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)));

    public static final Item GILDED_CINNAMON_STICK = registerItem("gilded_cinnamon_stick",
            new Item(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)));

    public static final Item JUNGLE_TWIG = registerItem("jungle_twig",
            new Item(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)));

    //MISC
    public static final Item LADY_BEETLE_SPAWN_EGG = registerItem("ladybeetle_spawn_egg",
            new SpawnEggItem(ModEntities.LADY_BEETLE,0xd50027, 0x160003,
                    new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)));

    public static final Item CORVUS_COIN = registerItem("corvus_coin",
            new CoinItem(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .fireproof().maxCount(16).rarity(Rarity.RARE), Text.literal("Crikey, comrade! CorvusCoin™ cost cannot corrode!").formatted(Formatting.BLUE)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(ZoeysAdditions.MOD_ID, name), item);

    }
    public static void registerModItems(){
        ZoeysAdditions.LOGGER.debug("Registering Mod Items for " + ZoeysAdditions.MOD_ID);

    }
}
