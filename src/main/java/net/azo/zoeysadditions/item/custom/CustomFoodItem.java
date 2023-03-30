package net.azo.zoeysadditions.item.custom;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class CustomFoodItem extends Item {

    public enum clearableEffects{
        ALL,
        POISON,
        NONE
    }

    private static UseAction itemUseAction;
    private Item itemReturned;
    private static SoundEvent itemSoundEvent;
    private static int itemUseTime;
    private static clearableEffects userEffectsCleared;

    public CustomFoodItem(Settings settings, UseAction useAction, Item returnedItem,
                          SoundEvent soundEvent, int useTime, clearableEffects clearedEffects) {
        super(settings);
        itemUseAction = useAction;
        itemReturned = returnedItem;
        itemSoundEvent = soundEvent;
        itemUseTime = useTime;
        userEffectsCleared = clearedEffects;
    }












    //When the item is consumed:
    //Increment item use stat for player
    //Remove the right effects (e.g. if a player drinks soya milk, all effects are removed)
    //Give the player the returned item (e.g. if a player drinks tea, they get an empty bottle)
    //That's literally all this does.
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        if (!world.isClient) {
            if (userEffectsCleared == clearableEffects.POISON){
                user.removeStatusEffect(StatusEffects.POISON);
            } else if (userEffectsCleared == clearableEffects.ALL){
                user.clearStatusEffects();
            }
        }
        if (stack.isEmpty()) {
            return new ItemStack(itemReturned);
        } else {
            if (user instanceof PlayerEntity && !((PlayerEntity)user).getAbilities().creativeMode) {
                ItemStack itemStack = new ItemStack(itemReturned);
                PlayerEntity playerEntity = (PlayerEntity)user;
                if (!playerEntity.getInventory().insertStack(itemStack)) {
                    playerEntity.dropItem(itemStack, false);
                }
            }
            return stack;
        }
    }

    public int getMaxUseTime(ItemStack stack) {
        return itemUseTime;
    }

    public UseAction getUseAction(ItemStack stack) {
        return itemUseAction;
    }

    public SoundEvent getDrinkSound() {
        return itemSoundEvent;
    }

    public SoundEvent getEatSound() {
        return itemSoundEvent;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

}
