package net.azo.zoeysadditions.item;

import net.azo.zoeysadditions.ZoeysAdditions;
import net.azo.zoeysadditions.item.custom.CandyAppleItem;
import net.azo.zoeysadditions.item.custom.TeaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.minecraft.entity.effect.StatusEffects.*;


public class ModItems {
    //THE CINNAMON BUN :O
    public static final Item CINNAMON_BUN = registerItem("cinnamon_bun",
            new Item(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .food(new FoodComponent.Builder().hunger(5).saturationModifier(3)
                            .statusEffect(new StatusEffectInstance(REGENERATION, 150, 0), 100).build())));

    public static final Item CANDY_APPLE = registerItem("candy_apple",
            new CandyAppleItem(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .food(new FoodComponent.Builder().hunger(7).saturationModifier(2).build())));

    public static final Item RASPBERRY_TEA = registerItem("raspberry_tea",
            new TeaItem(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(1)
                            .statusEffect(new StatusEffectInstance(REGENERATION, 150, 0), 100).build())));

    public static final Item HERBAL_TEA = registerItem("herbal_tea",
            new TeaItem(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .food(new FoodComponent.Builder().hunger(3).saturationModifier(2)
                            .statusEffect(new StatusEffectInstance(REGENERATION, 150, 0), 100).build())));

    public static final Item GILDED_TEA = registerItem("gilded_tea",
            new TeaItem(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .food(new FoodComponent.Builder().hunger(5).saturationModifier(3)
                            .statusEffect(new StatusEffectInstance(REGENERATION, 600, 0), 100)
                            .statusEffect(new StatusEffectInstance(RESISTANCE, 600, 0), 100)
                            .statusEffect(new StatusEffectInstance(SPEED, 600, 1), 100)
                            .statusEffect(new StatusEffectInstance(ABSORPTION, 600, 0), 100)
                            .build())));

    /*public static final Item CANDY_APPLE = registerItem("candy_apple",
            new CandyAppleItem(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(3)
                            .build())));*/

    //TO DO: FIGURE OUT HOW TO ADD TOOLTIPS
    //TO DO: ADD LOVE EFFECT

    public static final Item RASPBERRY_RHODOLITE = registerItem("raspberry_rhodolite",
            new Item(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS).fireproof()));

    public static final Item BENITOITE = registerItem("benitoite",
            new Item(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS).fireproof()));

    public static final Item CINNAMON_STICK = registerItem("cinnamon_stick",
            new Item(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)));

    public static final Item GILDED_CINNAMON_STICK = registerItem("gilded_cinnamon_stick",
            new Item(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)));

    public static final Item JUNGLE_TWIG = registerItem("jungle_twig",
            new Item(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(ZoeysAdditions.MOD_ID, name), item);

    }
    public static void registerModItems(){
        ZoeysAdditions.LOGGER.debug("Registering Mod Items for " + ZoeysAdditions.MOD_ID);

    }
}
