package net.azo.zoeysadditions.block.entity;

import net.azo.zoeysadditions.ModSounds;
import net.azo.zoeysadditions.block.ModBlocks;
import net.azo.zoeysadditions.item.ModItems;
import net.azo.zoeysadditions.screen.SoyaSeparatorScreenHandler;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


import static org.apache.commons.lang3.RandomUtils.nextInt;

public class SoyaSeparatorBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
private final DefaultedList<ItemStack> inventory =
        DefaultedList.ofSize(4, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;




    public SoyaSeparatorBlockEntity(BlockEntityType blockEntity, BlockPos pos, BlockState state) {
        super(blockEntity, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0: return SoyaSeparatorBlockEntity.this.progress;
                    case 1: return SoyaSeparatorBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: SoyaSeparatorBlockEntity.this.progress = value; break;
                    case 1: SoyaSeparatorBlockEntity.this.maxProgress = value; break;
                }
            }

            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    public Text getDisplayName() {
        return Text.translatable("container.zoeysadditions.soya_separator");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new SoyaSeparatorScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("soya_separator.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("soya_separator.progress");
    }

    private void resetProgress() {
        this.progress = 0;
    }


    public static void tick(World world, BlockPos blockPos, BlockState blockState, SoyaSeparatorBlockEntity entity) {
        if (world.isClient()){
            return;
        }
        if(hasRecipe(entity) && entity.progress == 0) {
            entity.removeStack(0, 1);
            entity.progress++;
            markDirty(world, blockPos, blockState);
        } else if (entity.progress == 1){
            world.playSound(null, entity.getPos(), ModSounds.SOYA_SEPARATOR_AMBIENT,
                    SoundCategory.BLOCKS, 0.3f, 1f);
            entity.progress++;

        } else if (entity.progress >= 2 && entity.progress < entity.maxProgress) {
            entity.progress++;
            markDirty(world, blockPos, blockState);
        } else {
            if(entity.progress >= entity.maxProgress) {

                if ((entity.getStack(2).isOf(ModItems.SOYA_MILK) || entity.getStack(2).isEmpty())
                && (entity.getStack(3).isOf(ModItems.HEAVY_CREAM) || entity.getStack(3).isEmpty())
                && entity.getStack(2).getCount() < 64 && entity.getStack(3).getCount() < 64
                && !(entity.getStack(1).isEmpty())){
                    //IF Slot one is either empty or contains less than a stack of soya milk
                    //AND slot two is either empty or contains less than a stack of heavy cream
                    //AND there is a bottle available:
                    craftItem(entity, blockState);
                    entity.resetProgress();
                    if(!hasRecipe(entity)){ //Plays this sound once the process is finished :)
                        world.playSound(null, entity.getPos(), ModSounds.SOYA_SEPARATOR_FINISHES,
                                SoundCategory.BLOCKS, 0.5f, 1f);
                    }
                    markDirty(world, blockPos, blockState);
                }
            }
        }
    }

    private static void craftItem(SoyaSeparatorBlockEntity entity, BlockState blockState){
        if (getRandom(blockState) < 6){
            entity.removeStack(1, 1);
            entity.setStack(2, new ItemStack(ModItems.SOYA_MILK,
                    entity.getStack(2).getCount() + 1));

        } else if (getRandom(blockState) < 12){
            entity.removeStack(1, 1);
            entity.setStack(3, new ItemStack(ModItems.HEAVY_CREAM,
                    entity.getStack(3).getCount() + 1));

        } else {
            entity.removeStack(1, 1);
        }

    }

    private static int getRandom(BlockState blockState) {
        if (blockState.getBlock() == ModBlocks.SOYA_SEPARATOR_COPPER) {
            return nextInt(0, 30); //Chance of success: 40%
        } else if (blockState.getBlock() == ModBlocks.SOYA_SEPARATOR_IRON) {
            return nextInt(0, 20); //Chance of success: 60%
        } else if (blockState.getBlock() == ModBlocks.SOYA_SEPARATOR_GOLD) {
            return nextInt(0, 15); //Chance of success: 80%
        } else {
            return nextInt(0, 12); //Chance of failure: 0%
        }
    }
    private static boolean hasRecipe(SoyaSeparatorBlockEntity entity) {
        boolean hasSoyaBeansInFirstSlot = entity.getStack(0).getItem() == ModItems.SOYA_BEANS;
        boolean hasGlassBottleInSecondSlot = entity.getStack(1).getItem() == Items.GLASS_BOTTLE;
        return hasSoyaBeansInFirstSlot && hasGlassBottleInSecondSlot;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {
        return inventory.getStack(2).getItem() == output || inventory.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
    }

    /* HOPPER STUFF */
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return (slot == 0 && stack.isOf(ModItems.SOYA_BEANS))
                || (slot == 1 && stack.isOf(Items.GLASS_BOTTLE));
    }

    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return (slot == 2 && stack.isOf(ModItems.SOYA_MILK))
                || (slot == 3 && stack.isOf(ModItems.HEAVY_CREAM));
    }
}
