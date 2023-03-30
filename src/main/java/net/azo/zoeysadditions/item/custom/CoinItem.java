package net.azo.zoeysadditions.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CoinItem extends Item {
    private static Text customToolTip;

    public CoinItem(Settings settings, Text toolTip) {
        super(settings);
        customToolTip = toolTip;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(customToolTip);
        } else {
            tooltip.add(Text.literal("Press Shift for more info").formatted(Formatting.LIGHT_PURPLE));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
