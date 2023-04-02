package net.azo.zoeysadditions.screen;

import net.azo.zoeysadditions.block.entity.SoyaSeparatorBlockEntity;
import net.azo.zoeysadditions.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;


public class SoyaSeparatorScreenHandler extends ScreenHandler {

    private Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public SoyaSeparatorScreenHandler(int syncID, PlayerInventory inventory){
        this(syncID, inventory, new SimpleInventory(4),
                new ArrayPropertyDelegate(2));
    }

    public SoyaSeparatorScreenHandler(int syncId, PlayerInventory playerInventory,
                             Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlers.SOYA_SEPARATOR_SCREEN_HANDLER, syncId);
        checkSize(inventory, 3);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = delegate;

        this.addSlot(new restrictedInputSlot(inventory, 0, 66, 19, ModItems.SOYA_BEANS)); //Slot 0 (Soya Beans Input)
        this.addSlot(new restrictedInputSlot(inventory, 1, 94, 19, Items.GLASS_BOTTLE)); //Slot 1 (Glass Bottles Input)
        this.addSlot(new outputSlot(inventory, 2, 66, 65)); //Slot 2 (Soya Milk Output)
        this.addSlot(new outputSlot(inventory, 3, 94, 65)); //Slot 3 (Heavy Cream Output)

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(delegate);
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);  // Max Progress
        int progressArrowSize = 29; // This is the width in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 98 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 156));
        }
    }

    static class restrictedInputSlot extends Slot {
        private Item item;
        public restrictedInputSlot(Inventory inventory, int i, int j, int k, Item acceptedItem) {
            super(inventory, i, j, k);
            item = acceptedItem;
        }
        public boolean canInsert(ItemStack stack) {
            return matches(stack);
        }

        public int getMaxItemCount() {
            return item.getMaxCount();
        }

        public boolean matches(ItemStack stack) {
            return stack.isOf(item);
        }
    }

    static class outputSlot extends Slot {

        public outputSlot(Inventory inventory, int i, int j, int k) {
            super(inventory, i, j, k);
        }
        public boolean canInsert(ItemStack stack) {
            return false;
        }
        public int getMaxItemCount() {
            return 64;
        }
    }


    public static int calculateComparatorOutput(@Nullable BlockEntity entity) {

        int slot2Count;
        int slot3Count;

        SoyaSeparatorBlockEntity seperatorEntity = (SoyaSeparatorBlockEntity) entity;
        DefaultedList<ItemStack> inventory = seperatorEntity.getItems();


        if (inventory.get(2).isEmpty()){slot2Count = 0;}
        else {slot2Count = inventory.get(2).getCount();}

        if (inventory.get(3).isEmpty()){slot3Count = 0;}
        else {slot3Count = inventory.get(3).getCount();}

        int totalCount = slot2Count + slot3Count;

        //The formula is y = (15x - 112) / 127
        //This creates a straight line that passes through the points (1,1) and (128,16)

        return ((14*totalCount) + 113)/127;


    }
}


