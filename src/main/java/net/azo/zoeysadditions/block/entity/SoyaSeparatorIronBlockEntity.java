package net.azo.zoeysadditions.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class SoyaSeparatorIronBlockEntity extends SoyaSeparatorBlockEntity{
    public SoyaSeparatorIronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOYA_SEPARATOR_IRON, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.zoeysadditions.soya_separator_iron");
    }
}
