package net.azo.zoeysadditions.item;

import net.azo.zoeysadditions.ZoeysAdditions;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import static net.minecraft.entity.effect.StatusEffects.REGENERATION;


public class ModItems {
    //THE CINNAMON BUN :O
    public static final Item CINNAMON_BUN = registerItem("cinnamon_bun",
            new Item(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS)
                    .food(new FoodComponent.Builder().hunger(7).saturationModifier(4)
                            .statusEffect(new StatusEffectInstance(REGENERATION, 300, 0), 100).build())));
    //TO DO: FIGURE OUT HOW TO ADD TOOLTIPS
    //TO DO: ADD LOVE EFFECT

    public static final Item RASPBERRY_RHODOLITE = registerItem("raspberry_rhodolite",
            new Item(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS).fireproof()));
    //TO DO: ASK ELO FOR GEM DESIGN, CREATE TEXTURE

    public static final Item BENITOITE = registerItem("benitoite",
            new Item(new FabricItemSettings().group(ModItemGroup.ZOEYSADDITIONS).fireproof()));
    //TO DO: ASK ELO FOR GEM DESIGN, CREATE TEXTURE

    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(ZoeysAdditions.MOD_ID, name), item);

    }
    public static void registerModItems(){
        ZoeysAdditions.LOGGER.debug("Registering Mod Items for " + ZoeysAdditions.MOD_ID);

    }
}
