package net.azo.zoeysadditions.block.custom;

import net.azo.zoeysadditions.block.ModBlocks;
import net.azo.zoeysadditions.block.entity.*;
import net.azo.zoeysadditions.screen.SoyaSeparatorScreenHandler;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SoyaSeparatorBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public SoyaSeparatorBlock(Settings settings) {
        super(settings);
    }


    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }


    /* COMPARATOR STUFF */
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return SoyaSeparatorScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }


    /* BLOCK ENTITY STUFF */
    @Override
    public BlockRenderType getRenderType(BlockState state){
        return BlockRenderType.MODEL;
    }

    //When I am destroyed, spew items everywhere
    @Override
    public void onStateReplaced(BlockState state,World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SoyaSeparatorBlockEntity){
                ItemScatterer.spawn(world, pos, (SoyaSeparatorBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    //When I am used, create a screen handler
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        if (state.getBlock() == ModBlocks.SOYA_SEPARATOR_COPPER){
            return new SoyaSeparatorCopperBlockEntity(pos, state);
        } else if (state.getBlock() == ModBlocks.SOYA_SEPARATOR_IRON){
            return new SoyaSeparatorIronBlockEntity(pos, state);
        } else if (state.getBlock() == ModBlocks.SOYA_SEPARATOR_GOLD){
            return new SoyaSeparatorGoldBlockEntity(pos, state);
        } else {
            return new SoyaSeparatorNetheriteBlockEntity(pos, state);
        }

    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (state.getBlock() == ModBlocks.SOYA_SEPARATOR_COPPER){
            return checkType(type, ModBlockEntities.SOYA_SEPARATOR_COPPER, SoyaSeparatorBlockEntity::tick);
        } else if (state.getBlock() == ModBlocks.SOYA_SEPARATOR_IRON){
            return checkType(type, ModBlockEntities.SOYA_SEPARATOR_IRON, SoyaSeparatorBlockEntity::tick);
        } else if (state.getBlock() == ModBlocks.SOYA_SEPARATOR_GOLD){
            return checkType(type, ModBlockEntities.SOYA_SEPARATOR_GOLD, SoyaSeparatorBlockEntity::tick);
        } else {
            return checkType(type, ModBlockEntities.SOYA_SEPARATOR_NETHERITE, SoyaSeparatorBlockEntity::tick);
        }
    }
}