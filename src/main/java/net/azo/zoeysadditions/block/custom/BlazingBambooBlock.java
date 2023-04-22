package net.azo.zoeysadditions.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class BlazingBambooBlock extends PlantBlock {
    protected static final VoxelShape PILEUS_SHAPE_BOTTOM = Block.createCuboidShape(6, 0, 6, 10, 5, 10);
    protected static final VoxelShape PILEUS_SHAPE_MIDDLE = Block.createCuboidShape(3, 5, 3, 13, 7, 13);
    protected static final VoxelShape PILEUS_SHAPE_TOP = Block.createCuboidShape(5, 7, 5, 11, 9, 11);

    protected static final VoxelShape CULM_SHAPE = Block.createCuboidShape(6, 0, 6, 10, 16, 10);
    protected static final VoxelShape PILEUS_SHAPE = VoxelShapes.union(PILEUS_SHAPE_BOTTOM, PILEUS_SHAPE_MIDDLE, PILEUS_SHAPE_TOP);

    public static final BooleanProperty IS_TOP;

    public BlazingBambooBlock(Settings settings) {
        super(settings);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(IS_TOP);
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(IS_TOP)){return PILEUS_SHAPE;}
        return CULM_SHAPE;
    }

    static {
        IS_TOP = BooleanProperty.of("is_top");
    }
}
