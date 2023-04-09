package net.azo.zoeysadditions.block.custom;

import net.azo.zoeysadditions.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.apache.logging.log4j.core.jmx.Server;

public class BlazingBambooBlock extends Block {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(6, 0, 6, 10, 16, 10);
    public BlazingBambooBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(BLAZING, false));
    }

    public static final BooleanProperty BLAZING = BooleanProperty.of("blazing");
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BLAZING);
    }
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }

    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isIn(BlockTags.NYLIUM)
                || world.getBlockState(pos.down()).isOf(Blocks.NETHERRACK)
                || world.getBlockState(pos.down()).isOf(Blocks.GRAVEL)
                || world.getBlockState(pos.down()).isOf(ModBlocks.BLAZING_BAMBOO_BLOCK);

    }

    public void blazeSequence(BlockState state, ServerWorld world, BlockPos pos){
        world.setBlockState(pos, this.getDefaultState().with(BLAZING, true));
        if (world.getBlockState(pos.down()).isOf(ModBlocks.BLAZING_BAMBOO_BLOCK)){

        }
    }
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

}
