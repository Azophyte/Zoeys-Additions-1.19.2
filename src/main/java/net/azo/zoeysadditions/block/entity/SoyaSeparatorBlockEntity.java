package net.azo.zoeysadditions.block.entity;

import net.azo.zoeysadditions.item.ModItems;
import net.azo.zoeysadditions.screen.SoyaSeparatorScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


import static org.apache.commons.lang3.RandomUtils.nextInt;

public class SoyaSeparatorBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
private final DefaultedList<ItemStack> inventory =
        DefaultedList.ofSize(4, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;



    public SoyaSeparatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOYA_SEPARATOR, pos, state);
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

    @Override
    public Text getDisplayName() {
        return Text.literal("Soya Separator");
        //MAKE ME TRANSLATABLE!!!
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

        } else if (entity.progress >= 1 && entity.progress < entity.maxProgress) {
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
                    craftItem(entity);
                    entity.resetProgress();
                    markDirty(world, blockPos, blockState);
                }
            }
        }
    }

    private static void craftItem(SoyaSeparatorBlockEntity entity) {
        /*SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }*/
            //entity.removeStack(0, 1);

        switch (nextInt(0, 3)) { //Each option has a 33% chance
            case 0 -> { //Take glass bottle from slot 1, add soya milk to slot 2, play brewing stand sound
                entity.removeStack(1, 1);
                entity.setStack(2, new ItemStack(ModItems.SOYA_MILK,
                        entity.getStack(2).getCount() + 1));
                entity.world.playSound(null, entity.getPos(), SoundEvents.BLOCK_BREWING_STAND_BREW,
                        SoundCategory.PLAYERS, 1f, 1.5f);
            }
            case 1 -> { //Take glass bottle from slot 1, add heavy cream to slot 3, play brewing stand sound
                entity.removeStack(1, 1);
                entity.setStack(3, new ItemStack(ModItems.HEAVY_CREAM,
                        entity.getStack(3).getCount() + 1));
                entity.world.playSound(null, entity.getPos(), SoundEvents.BLOCK_BREWING_STAND_BREW,
                        SoundCategory.PLAYERS, 1f, 0.5f);
            }
            default -> //Play fire extinguishing sound
                    entity.world.playSound(null, entity.getPos(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE,
                            SoundCategory.PLAYERS, 1f, 1f);
        }

        }

    private static boolean hasRecipe(SoyaSeparatorBlockEntity entity) {
        /*SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }*/

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
}
