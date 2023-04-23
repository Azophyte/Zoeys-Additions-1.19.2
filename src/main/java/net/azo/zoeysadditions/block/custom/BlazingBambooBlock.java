package net.azo.zoeysadditions.block.custom;

import net.azo.zoeysadditions.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class BlazingBambooBlock extends Block implements Fertilizable{
    protected static final VoxelShape PILEUS_SHAPE_BOTTOM = Block.createCuboidShape(6, 0, 6, 10, 5, 10);
    protected static final VoxelShape PILEUS_SHAPE_MIDDLE = Block.createCuboidShape(3, 5, 3, 13, 7, 13);
    protected static final VoxelShape PILEUS_SHAPE_TOP = Block.createCuboidShape(5, 7, 5, 11, 9, 11);

    protected static final VoxelShape CULM_SHAPE = Block.createCuboidShape(6, 0, 6, 10, 16, 10);
    protected static final VoxelShape PILEUS_SHAPE = VoxelShapes.union(PILEUS_SHAPE_BOTTOM, PILEUS_SHAPE_MIDDLE, PILEUS_SHAPE_TOP);

    public static final BooleanProperty IS_TOP;
    public static final BooleanProperty IS_BLAZING;

    public BlazingBambooBlock(AbstractBlock.Settings settings) {
        super(settings/*.offsetType(OffsetType.XYZ)*/);
        this.setDefaultState((this.stateManager.getDefaultState()).with(IS_TOP, false).with(IS_BLAZING, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(IS_TOP, IS_BLAZING);
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }


    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        if (state.get(IS_TOP)){return PILEUS_SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);}
        return CULM_SHAPE;
    }


    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getOutlineShape(state, world, pos, context);
    }

    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return (world.getBlockState(pos.down()).isOf(Blocks.SOUL_SOIL)
        || (world.getBlockState(pos.down()).isOf(ModBlocks.BLAZING_BAMBOO_BLOCK)
                && !world.getBlockState(pos.down()).get(IS_TOP)));
    }

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return (hasPileus(world, pos) && !state.get(IS_BLAZING));
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state){
        return (hasPileus(world, pos) && !state.get(IS_BLAZING));
    }

    public boolean hasRandomTicks(BlockState state) {
        return state.get(IS_TOP);
    }
    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int topBlock = countBambooAbove(world, pos);
        if (world.isAir(pos.up(topBlock))){
            return;
        }
        world.setBlockState(pos.up(topBlock), this.getDefaultState());
        if ((countBambooTotal(world, pos) >= 13 && world.random.nextInt(2) == 0) || countBambooTotal(world, pos) >= 17){
            world.setBlockState(pos.up(topBlock+1), this.getDefaultState().with(IS_TOP, true).with(IS_BLAZING, true));
        } else {
            world.setBlockState(pos.up(topBlock+1), this.getDefaultState().with(IS_TOP, true));
        }

    }

    protected int countBambooAbove(BlockView world, BlockPos pos) {
        int i;
        for(i = 0; i < 16 && world.getBlockState(pos.up(i + 1)).isOf(ModBlocks.BLAZING_BAMBOO_BLOCK); ++i) {
        }
        return i;
    }

    protected int countBambooBelow(BlockView world, BlockPos pos) {
        int i;
        for(i = 0; i < 16 && world.getBlockState(pos.down(i + 1)).isOf(ModBlocks.BLAZING_BAMBOO_BLOCK); ++i) {
        }
        return i;
    }

    protected int countBambooTotal(BlockView world, BlockPos pos){
        return 1 + countBambooBelow(world, pos) + countBambooAbove(world, pos);
    }

    protected boolean hasPileus(BlockView world, BlockPos pos){
        return world.getBlockState(pos.up(countBambooAbove(world, pos))).equals(this.getDefaultState().with(IS_TOP, true));
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBlockState(pos.up()).isOf(ModBlocks.BLAZING_BAMBOO_BLOCK)
                && world.getBlockState(pos.up()).get(IS_BLAZING)){
            world.setBlockState(pos, this.getDefaultState().with(IS_BLAZING, true));
        }

        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1);
        }
        if (world.getBlockState(pos.up()).isOf(ModBlocks.BLAZING_BAMBOO_BLOCK)
                && world.getBlockState(pos.up()).get(IS_BLAZING)){
            world.createAndScheduleBlockTick(pos, this, 1);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        return player.getMainHandStack().getItem() instanceof SwordItem ? 1.0F : super.calcBlockBreakingDelta(state, player, world, pos);
    }

    static {
        IS_TOP = BooleanProperty.of("is_top");
        IS_BLAZING = BooleanProperty.of("is_blazing");
    }
}
