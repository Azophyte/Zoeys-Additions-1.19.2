package net.azo.zoeysadditions.block.custom;

import net.azo.zoeysadditions.block.ModBlocks;
import net.azo.zoeysadditions.entity.ModEntities;
import net.azo.zoeysadditions.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.GameEvent.Emitter;
import static net.minecraft.block.enums.DoubleBlockHalf.LOWER;
import static net.minecraft.block.enums.DoubleBlockHalf.UPPER;

public class RaspberryBushBlock extends Block implements Fertilizable {
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final IntProperty AGE = Properties.AGE_4;
    private static final VoxelShape SHAPE_0 = Block.createCuboidShape(4.0, 0.0, 3.0, 12.0, 8.0, 12.0);;
    private static final VoxelShape SHAPE_1 = Block.createCuboidShape(3.0, 0.0, 2.0, 13.0, 10.0, 13.0);;
    private static final VoxelShape SHAPE_2 = Block.createCuboidShape(2.0, 0.0, 1.0, 14.0, 14.0, 14.0);;
    private static final VoxelShape SHAPE_3 = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);;

    public RaspberryBushBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(HALF, LOWER).with(AGE, 0));
    }

    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(ModItems.RASPBERRY);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(AGE)) {
            case 0 -> SHAPE_0;
            case 1 -> SHAPE_1;
            case 2 -> SHAPE_2;
            default -> SHAPE_3;
        };
    }

    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 5;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(AGE);
        if (i < 4 && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            grow(world, random, pos, state);
        }
    }

    //If no block below, I break
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState blockBelow = world.getBlockState(pos.down());
        //If I am no longer above a suitable block, break
        if (!((blockBelow.isIn(BlockTags.DIRT) || blockBelow.isOf(this) || blockBelow.isOf(Blocks.STRUCTURE_BLOCK) || blockBelow.isOf(Blocks.JIGSAW)))){
            world.breakBlock(pos, true);
        }
    }

    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        BlockState blockBelow = world.getBlockState(pos.down());
        if (blockBelow.isOf(ModBlocks.RASPBERRY_BUSH_BLOCK)){
            world.breakBlock(pos.down(), true);
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        world.createAndScheduleBlockTick(pos, this, 1);
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);}

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        return blockState.isIn(BlockTags.DIRT);}

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE && entity.getType() != ModEntities.LADY_BEETLE)  {
            entity.slowMovement(state, new Vec3d(0.800000011920929, 0.75, 0.800000011920929));
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        builder.add(HALF);}

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 4;}

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return state.get(AGE) < 4;}

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int myAge = world.getBlockState(pos).get(AGE);
        int aboveAge = -1;
        DoubleBlockHalf myHalf = world.getBlockState(pos).get(HALF);

        if (world.getBlockState(pos.up()).isOf(ModBlocks.RASPBERRY_BUSH_BLOCK)){
            aboveAge = world.getBlockState(pos.up()).get(AGE);
        }

        if (myHalf == UPPER){ //If I am a top half, I grow on every chance
            world.setBlockState(pos, state.with(AGE, myAge +1));
        } else if (myAge == 2){ //If I am age 2, I advance to age 3 and spawn a top block above me
            world.setBlockState(pos.up(), this.getDefaultState().with(HALF, UPPER).with(AGE, 0));
            world.setBlockState(pos, state.with(AGE, 3));
        } else if (myAge == 3 && aboveAge <3){ //If I am age 3 and the block above me is not, I advance the top block instead
            world.setBlockState(pos.up(), this.getDefaultState().with(HALF, UPPER).with(AGE, aboveAge + 1));
        } else { //If all other conditions fail, my age advances.
            world.setBlockState(pos, state.with(AGE, myAge + 1));
        }
    }

    //This code is copy/pasted from the sweet berry bush code with some adjustments.
    // I don't know how it works, but it does work, so I'm not touching it :)
    //It allows you to pick raspberries from the bush with right-click

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos ){
        return true;
    }
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = state.get(AGE);
        boolean bl = i == 3;
        if (!bl && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        } else if (i == 4) {
            int j = 1 + world.random.nextInt(2);
            dropStack(world, pos, new ItemStack(ModItems.RASPBERRY, j + (bl ? 1 : 0)));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            BlockState blockState = state.with(AGE, 3);
            world.setBlockState(pos, blockState, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, Emitter.of(player, blockState));
            return ActionResult.success(world.isClient);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }
}
