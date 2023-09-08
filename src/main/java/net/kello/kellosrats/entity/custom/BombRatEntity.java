package net.kello.kellosrats.entity.custom;

import net.kello.kellosrats.entity.ModEntities;
import net.kello.kellosrats.item.ModItems;
import net.kello.kellosrats.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class BombRatEntity extends TamableAnimal implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public BombRatEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    private static final Ingredient FOOD = Ingredient.of(Items.NETHER_STAR);
    protected SoundEvent getAmbientSound() {return ModSounds.RAT_SQUEAK.get();}
    protected SoundEvent getDeathSound() {
        return ModSounds.RAT_DEATH.get();
    }
    protected SoundEvent getHurtSound(DamageSource p_28306_) {
        return ModSounds.RAT_HURT.get();
    }
    private int explosionRadius = 10;

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 0.8F);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 32D)
                .add(Attributes.ATTACK_DAMAGE, 20f)
                .add(Attributes.ATTACK_SPEED, 1f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f).build();
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(6, new LeapAtTargetGoal(this, 0.6F));
        this.goalSelector.addGoal(7, new FollowOwnerGoal(this, 1.0D, 10.0F, 5.0F, true));
        this.goalSelector.addGoal(8, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public boolean doHurtTarget(Entity p_32892_) {
        boolean flag = super.doHurtTarget(p_32892_);
        if (flag && this.getMainHandItem().isEmpty() && p_32892_ instanceof LivingEntity) {
            float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            ((LivingEntity)p_32892_).addEffect(new MobEffectInstance(MobEffects.WITHER, 6000 * (int)f), this);
            this.explodeRat();
        }
        return flag;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
        return ModEntities.UNSTABLE_RAT.get().create(level);
    }

    private void explodeRat() {
        if (!this.level().isClientSide) {
            this.dead = true;
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius, Level.ExplosionInteraction.MOB);
            this.discard();
        }

    }

    public void setTame(boolean p_30443_) {
        super.setTame(p_30443_);
        if (p_30443_) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(4.0D);
            this.setHealth(4.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(2.0D);
        }

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(16.0D);
    }

    private <T extends GeoAnimatable> PlayState attackPredirate(AnimationState<T> tAnimationState) {
        if(this.swinging && tAnimationState.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            tAnimationState.getController().forceAnimationReset();
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bomb_rat.attack", Animation.LoopType.PLAY_ONCE));
            this.swinging = false;
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predirate));
        controllerRegistrar.add(new AnimationController<>(this, "attackController", 0, this::attackPredirate));
    }


    public InteractionResult mobInteract(Player p_28153_, InteractionHand p_28154_) {
        ItemStack itemstack = p_28153_.getItemInHand(p_28154_);
        Item item = itemstack.getItem();
        if (this.level().isClientSide) {
            if (this.isTame() && this.isOwnedBy(p_28153_)) {
                return InteractionResult.SUCCESS;
            } else {
                return !this.isFood(itemstack) || !(this.getHealth() < this.getMaxHealth()) && this.isTame() ? InteractionResult.PASS : InteractionResult.SUCCESS;
            }
        } else {
            if (this.isTame()) {
                if (this.isOwnedBy(p_28153_)) {
                    if (!(item instanceof DyeItem)) {
                        if (item.isEdible() && this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                            this.heal((float)itemstack.getFoodProperties(this).getNutrition());
                            this.usePlayerItem(p_28153_, p_28154_, itemstack);
                            return InteractionResult.CONSUME;
                        }

                        InteractionResult interactionresult = super.mobInteract(p_28153_, p_28154_);
                        if (!interactionresult.consumesAction() || this.isBaby()) {
                            this.setOrderedToSit(!this.isOrderedToSit());
                        }

                        return interactionresult;
                    }
                }
            } else if (this.isFood(itemstack)) {
                this.usePlayerItem(p_28153_, p_28154_, itemstack);
                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_28153_)) {
                    this.tame(p_28153_);
                    this.setOrderedToSit(true);
                    this.level().broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte)6);
                }

                this.setPersistenceRequired();
                return InteractionResult.CONSUME;
            }

            InteractionResult interactionresult1 = super.mobInteract(p_28153_, p_28154_);
            if (interactionresult1.consumesAction()) {
                this.setPersistenceRequired();
            }

            return interactionresult1;
        }
    }

    protected void dropCustomDeathLoot(DamageSource p_34291_, int p_34292_, boolean p_34293_) {
        super.dropCustomDeathLoot(p_34291_, p_34292_, p_34293_);
        Entity entity = p_34291_.getEntity();
        if (entity instanceof Creeper creeper) {
            if (creeper.canDropMobsSkull()) {
                ItemStack itemstack = this.getRat();
                if (!itemstack.isEmpty()) {
                    creeper.increaseDroppedSkulls();
                    this.spawnAtLocation(itemstack);
                }
            }
        }
    }

    protected ItemStack getRat() {
        return new ItemStack(ModItems.BOMB_RAT.get());
    }

    public boolean isFood(ItemStack p_28177_) {
        return FOOD.test(p_28177_);
    }

    private <T extends GeoAnimatable> PlayState predirate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bomb_rat.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bomb_rat.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
