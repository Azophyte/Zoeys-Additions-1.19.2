package net.azo.zoeysadditions.block.custom;

import net.azo.zoeysadditions.block.entity.BambooBrewingStandBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class BambooBrewingStandBlock extends BrewingStandBlock {
    public BambooBrewingStandBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BambooBrewingStandBlockEntity(pos, state);
    }
}
