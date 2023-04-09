package net.azo.zoeysadditions.block.custom;

import net.azo.zoeysadditions.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
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

import static net.azo.zoeysadditions.block.custom.BlazingBambooBlock.BLAZING;

public class BlazingBambooDrupeBlock extends Block implements Fertilizable {
    public BlazingBambooDrupeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(RIPE, false));
    }

    protected static final VoxelShape SHAPE = Block.createCuboidShape(4, 0, 4, 12, 8, 12);

    public static final BooleanProperty RIPE = BooleanProperty.of("ripe");
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(RIPE);
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (world.isAir(pos.up())){
            if (this.countBambooBelow(world,pos) >= 16){
                world.setBlockState(pos, this.getDefaultState().with(RIPE, true));
            } else {
                world.setBlockState(pos.up(), this.getDefaultState());
                world.setBlockState(pos, ModBlocks.BLAZING_BAMBOO_BLOCK.getDefaultState());
            }
        }
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return !world.getBlockState(pos).get(RIPE);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public boolean hasRandomTicks(BlockState state) {
        //return !state.get(RIPE);
        return true;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.get(RIPE)){ //if not ripe, grow
            this.grow(world, random, pos, state);
        } else {
            blazeSequence(world, pos);
        }
    }

    public void blazeSequence(ServerWorld world, BlockPos pos) {
        int numBamboo = this.countBambooBelow(world, pos); //16
        for (int i = 0; (numBamboo - i) == 0; i++){ // Repeat this until there is no more bamboo to edit
            if (world.getBlockState(pos.down(i)).isOf(ModBlocks.BLAZING_BAMBOO_BLOCK)){
                world.setBlockState(pos.down(i), getDefaultState().with(BLAZING, true));
            }
        }
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

    protected int countBambooBelow(BlockView world, BlockPos pos) {
        int i;
        for(i = 0; i < 16 && world.getBlockState(pos.down(i + 1)).isOf(ModBlocks.BLAZING_BAMBOO_BLOCK); ++i) {
        }

        return i;
    }

    static {
        //STAGE = Properties.STAGE;
    }
}
