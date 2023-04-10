package net.azo.zoeysadditions.block.custom;

import net.azo.zoeysadditions.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class SoyaCrop extends CropBlock {
    private static final VoxelShape SHAPE_0 = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 4.0, 10.0);
    private static final VoxelShape SHAPE_1 = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 8.0, 11.0);
    private static final VoxelShape SHAPE_2 = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 12.0, 12.0);
    private static final VoxelShape SHAPE_3 = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 14.0, 13.0);
    public SoyaCrop(AbstractBlock.Settings settings) {
        super(settings);
    }

    protected ItemConvertible getSeedsItem() {
        return ModItems.SOYA_BEANS;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(AGE) / 2) {
            case 0 -> SHAPE_0;
            case 1 -> SHAPE_1;
            case 2 -> SHAPE_2;
            default -> SHAPE_3;
        };
    }
}
