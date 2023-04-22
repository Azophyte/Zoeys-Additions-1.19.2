package net.azo.zoeysadditions.block.entity;

import net.azo.zoeysadditions.audio.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class JigglyCakeBlockEntity extends BlockEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean isJiggling = false;

    public JigglyCakeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.JIGGLY_CAKE, pos, state);

    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>
                (this, "controller", 0, this::predicate));
    }


    public void jiggle(){
        this.getWorld().playSound(null, this.getPos(), ModSounds.SOYA_SEPARATOR_FINISHES,
                SoundCategory.BLOCKS, 0.3f, 1f);
        isJiggling = true;
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (isJiggling){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("jiggle", true));
            isJiggling = false;
        }
        return PlayState.CONTINUE;
    }


    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
