package net.azo.zoeysadditions.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class SoyaSeparatorNetheriteBlockEntity extends SoyaSeparatorBlockEntity{
    public SoyaSeparatorNetheriteBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOYA_SEPARATOR_NETHERITE, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.zoeysadditions.soya_separator_netherite");
    }
}
