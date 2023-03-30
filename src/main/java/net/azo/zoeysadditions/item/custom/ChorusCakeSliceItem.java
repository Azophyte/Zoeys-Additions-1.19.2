package net.azo.zoeysadditions.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Optional;

public class ChorusCakeSliceItem extends Item {

    public ChorusCakeSliceItem(Settings settings) {
        super(settings);
    }
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        if (user instanceof PlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) user; //casts user to serverPlayerEntity

            ServerWorld serverWorld = ((ServerWorld)world).getServer().getWorld(serverPlayerEntity.getSpawnPointDimension());
            BlockPos spawnPoint = serverPlayerEntity.getSpawnPointPosition();
            if (spawnPoint != null){

                float spawnAngle = serverPlayerEntity.getSpawnAngle();
                assert serverWorld != null;
                Optional<Vec3d> optRespawnPosition = PlayerEntity.findRespawnPosition(serverWorld, spawnPoint, spawnAngle,true, true );
                if (optRespawnPosition.isPresent()){
                    Vec3d respawnPosition = optRespawnPosition.get();
                    serverPlayerEntity.teleport(serverWorld, respawnPosition.x, respawnPosition.y, respawnPosition.z,
                            serverPlayerEntity.getSpawnAngle(),0);
                    world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 15f, 0f);
                }

            } else {
                spawnPoint = world.getSpawnPos();
                serverPlayerEntity.teleport(serverWorld, spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ(),
                        world.getSpawnAngle(),0);
                world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 15f, 0f);
            }
        }
        return itemStack;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.literal("How could something so alien remind you of something so close...?").formatted(Formatting.LIGHT_PURPLE));
            tooltip.add(Text.literal(""));
            tooltip.add(Text.literal("Teleports you to your spawn point.").formatted(Formatting.GREEN));
            tooltip.add(Text.literal("If no spawn point is set, this may teleport you into a wall.").formatted(Formatting.DARK_RED));
        } else {
            tooltip.add(Text.literal("Press Shift for more info").formatted(Formatting.LIGHT_PURPLE));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    public int getMaxUseTime(ItemStack stack) {
        return 15;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    public SoundEvent getEatSound() {
        return SoundEvents.BLOCK_WOOL_BREAK;
    }

    //Does the whole eating animation
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        world.addParticle(ParticleTypes.PORTAL, user.getX(), user.getY() + 0.5, user.getZ(), 1, 2, 1);
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

}
