package net.azo.zoeysadditions.entity.custom;
import net.azo.zoeysadditions.entity.custom.variant.LadyBeetleVariant;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.task.FollowMobTask;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;
import net.minecraft.util.math.Vec3d;

import static org.apache.commons.lang3.RandomUtils.nextInt;

public class LadyBeetleEntity extends AnimalEntity implements IAnimatable, Flutterer {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public LadyBeetleEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, false);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 5.00)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, -10f)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.3f);
    }


    @Override
    protected void initGoals(){
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 7.0F));
        this.goalSelector.add(3, new LookAtEntityGoal(this, LadyBeetleEntity.class, 1.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new FollowMobGoal(this, 1.2f, 3, 15));
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            if (!isInAir()){
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ladybeetle.scurry"));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ladybeetle.flying"));
            }
            return PlayState.CONTINUE;
        }


        if (isSongPlaying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ladybeetle.dance"));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ladybeetle.idle"));
        return PlayState.CONTINUE;


    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean isInAir() {
            return !this.onGround;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BEE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BEE_DEATH;
    }

    @Override
    protected void initDataTracker(){
        super.initDataTracker();
        this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 1);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
                                 SpawnReason spawnReason, @Nullable EntityData entityData,
                                 @Nullable NbtCompound entityNbt) {
        setVariant(randomLadyBeetleVariant());
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }


    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }


    /* VARIANTS */
    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
            DataTracker.registerData(LadyBeetleEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
        }

    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        if (name != null && name.getString().equals("Zoey")) {
            this.setVariant(LadyBeetleVariant.TRANS);
        }
    }
    public LadyBeetleVariant getVariant() {return LadyBeetleVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(LadyBeetleVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    private LadyBeetleVariant randomLadyBeetleVariant() {
        int variantSeed = nextInt(0, 100);
        if (0 <= variantSeed && variantSeed <= 2) {
                return LadyBeetleVariant.GOLD;
            } else if (3 <= variantSeed && variantSeed <= 18) {
                return LadyBeetleVariant.ONE_DOT;
            } else if (19 <= variantSeed && variantSeed <= 34) {
                return LadyBeetleVariant.TWO_DOT;
            } else if (35 <= variantSeed && variantSeed <= 50) {
                return LadyBeetleVariant.THREE_DOT;
            } else if (51 <= variantSeed && variantSeed <= 66) {
                return LadyBeetleVariant.FOUR_DOT;
            } else if (67 <= variantSeed && variantSeed <= 82) {
                return LadyBeetleVariant.FIVE_DOT;
            } else if (83 <= variantSeed && variantSeed <= 98) {
                return LadyBeetleVariant.SIX_DOT;
            } else {
                return LadyBeetleVariant.TRANS;
            }
        }

    /* DANCING MECHANISM */
    private boolean songPlaying; //When this is true, the ladybeetle should stop trying to move for a while.
    @Nullable
    private BlockPos songSource;
    public void tickMovement() {
        if (this.songSource == null || !this.songSource.isWithinDistance(this.getPos(), 8) || !this.world.getBlockState(this.songSource).isOf(Blocks.JUKEBOX)) {
            this.songPlaying = false;
            this.songSource = null;
        }
        Vec3d vec3d = this.getVelocity();
        if (!this.onGround && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
        }

        //This is really clunky but shouldn't be too big of an issue, will see if I can fix later
        //If anybody else sees this, the reason this is done is that jigsaw blocks don't save variant data (mc bug)
        //To have the correct variants spawn where they should, they'll be saved with these names instead.
        //If I can figure out how these entities are created by jigsaws, it should be possible to fix this.
        if (this.getCustomName() != null/* && this.getCustomName().toString().charAt(0) == '§'*/){
            if (this.getCustomName().getString().equals("§setVariant_gold")) {
                this.setVariant(LadyBeetleVariant.GOLD);
                this.setCustomName(null);
            } else if (this.getCustomName().getString().equals("§setVariant_1")) {
                this.setVariant(LadyBeetleVariant.ONE_DOT);
                this.setCustomName(null);
            } else if (this.getCustomName().getString().equals("§setVariant_2")) {
                this.setVariant(LadyBeetleVariant.TWO_DOT);
                this.setCustomName(null);
            } else if (this.getCustomName().getString().equals("§setVariant_3")) {
                this.setVariant(LadyBeetleVariant.THREE_DOT);
                this.setCustomName(null);
            } else if (this.getCustomName().getString().equals("§setVariant_4")) {
                this.setVariant(LadyBeetleVariant.FOUR_DOT);
                this.setCustomName(null);
            } else if (this.getCustomName().getString().equals("§setVariant_5")) {
                this.setVariant(LadyBeetleVariant.FIVE_DOT);
                this.setCustomName(null);
            } else if (this.getCustomName().getString().equals("§setVariant_6")) {
                this.setVariant(LadyBeetleVariant.SIX_DOT);
                this.setCustomName(null);
            } else if (this.getCustomName().getString().equals("§setVariant_trans")) {
                this.setVariant(LadyBeetleVariant.TRANS);
                this.setCustomName(null);
            }
        }
        super.tickMovement();
    }
    public void setNearbySongPlaying(BlockPos songPosition, boolean playing) {
        this.songSource = songPosition;
        this.songPlaying = playing;
    }

    public boolean isSongPlaying() {
        return this.songPlaying;
    }

}