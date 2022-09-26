package net.azo.zoeysadditions.item;

import net.azo.zoeysadditions.ZoeysAdditions;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup ZOEYSADDITIONS = FabricItemGroupBuilder.build(
            new Identifier(ZoeysAdditions.MOD_ID, "zoeysadditions"), () -> new ItemStack(ModItems.RASPBERRY_RHODOLITE));


}
