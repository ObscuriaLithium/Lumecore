package com.obscuria.lumecore.world.entities;

import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.world.blocks.ILightSource;
import com.obscuria.lumecore.world.entities.ai.AttackTargetGoal;
import com.obscuria.lumecore.world.entities.ai.BlockSearchGoal;
import com.obscuria.lumecore.world.entities.ai.IGoalLimiter;
import com.obscuria.lumecore.world.entities.ai.attack.SimpleMeleeAttack;
import com.obscuria.obscureapi.animations.HekateProvider;
import com.obscuria.obscureapi.animations.IHekateProvider;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AshenWitchEntity extends Monster implements IMansionEntity, IHekateProvider, IGoalLimiter {

    private final HekateProvider ANIMATIONS = new HekateProvider(this);

    private boolean canUseGoals = true;

    public AshenWitchEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
    }

    public AshenWitchEntity(PlayMessages.SpawnEntity message, Level level) {
        this(LumecoreMod.Entities.ASHEN_WITCH.get(), level);
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag data) {
        super.readAdditionalSaveData(data);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag data) {
        super.addAdditionalSaveData(data);
    }

    @Override
    public HekateProvider getHekateProvider() {
        return this.ANIMATIONS;
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor world, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason,
                                        @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        final AttributeInstance movementSpeed = this.getAttribute(Attributes.MOVEMENT_SPEED);
        final AttributeInstance criticalHit = this.getAttribute(ObscureAPIAttributes.CRITICAL_HIT.get());
        if (movementSpeed != null) movementSpeed.addPermanentModifier(new AttributeModifier(
                "Random spawn bonus", this.getRandom().triangle(0.25D, 0.25D), AttributeModifier.Operation.MULTIPLY_BASE));
        if (criticalHit != null) criticalHit.setBaseValue(0.1D);
        return super.finalizeSpawn(world, difficulty, reason, data, tag);
    }

    @Override
    public void aiStep() {
        this.canUseGoals = true;
        super.aiStep();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(3, new AttackTargetGoal(this, 1.0D, false,
                new SimpleMeleeAttack("attack", 30, 9, 40, 20, 4, 3.5),
                new SimpleMeleeAttack("piercingAttack", 30, 9, 200, 40, 5, 4)));
        this.goalSelector.addGoal(4, new CollectAshGoal(this));
        //this.goalSelector.addGoal(5, new AshenWitchEntity.PutOutLightGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8) {
            @Override public boolean canUse() {
                if (this.mob instanceof IGoalLimiter goalLimiter && !goalLimiter.canUseGoal()) return false;
                return super.canUse(); }});
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    @Override
    public boolean canUseGoal() {
        return this.canUseGoals;
    }

    @Override
    public void stopGoals() {
        this.canUseGoals = false;
    }

    private static class CollectAshGoal extends BlockSearchGoal {

        private int cooldown;

        private CollectAshGoal(PathfinderMob mob) {
            super(mob, List.of(SnowLayerBlock.class));}

        @Override
        public boolean canUse() {
            this.cooldown = Math.max(this.cooldown - 1, 0);
            if (this.cooldown > 0) return false;
            this.cooldown = 20;
            if (this.MOB.getTarget() != null || (this.MOB instanceof IGoalLimiter goalLimiter && !goalLimiter.canUseGoal()))
                return false;
            final BlockPos targetPos = search(this.MOB.blockPosition().below(), 5, 2);
            return !(targetPos == null);
        }

        @Override
        public boolean canContinueToUse() {
            if (this.MOB.getTarget() != null || (this.MOB instanceof IGoalLimiter goalLimiter && !goalLimiter.canUseGoal())) return false;
            final BlockPos targetPos = search(this.MOB.blockPosition().below(), 5, 2); if (targetPos == null) return false;

            final BlockPos mobPos = this.MOB.blockPosition();
            if (this.MOB.getNavigation().getTargetPos() != targetPos.above() || this.MOB.getNavigation().isDone())
                this.MOB.getNavigation().moveTo(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ(), 1);
            if (this.MOB.getX() >= targetPos.getX() + 0.5 - 1 && this.MOB.getX() <= targetPos.getX() + 0.5 + 1 &&
                    this.MOB.getZ() >= targetPos.getZ() + 0.5 - 1 && this.MOB.getZ() <= targetPos.getZ() + 0.5 + 1 &&
                    mobPos.getY() >= targetPos.getY() - 1 && mobPos.getY() <= targetPos.getY() + 1)
                this.MOB.getLevel().destroyBlock(targetPos, true);
            if (this.MOB instanceof IGoalLimiter goalLimiter) goalLimiter.stopGoals();
            return true;
        }
    }

    private static class PutOutLightGoal extends BlockSearchGoal {

        private PutOutLightGoal(PathfinderMob mob) {
            super(mob, List.of(ILightSource.class));}

        @Override
        public boolean canUse() {
            if (this.MOB.getTarget() != null || (this.MOB instanceof IGoalLimiter goalLimiter && !goalLimiter.canUseGoal()))
                return false;
            final BlockPos lightPos = search(this.MOB.blockPosition(), 3, 3);
            if (lightPos == null) return false;
            final BlockState state = this.MOB.getLevel().getBlockState(lightPos);

            final BlockPos mobPos = this.MOB.blockPosition();
            this.MOB.getNavigation().moveTo(lightPos.getX(), lightPos.getY(), lightPos.getZ(), 1);
            if (mobPos.getX() >= lightPos.getX() - 1 && mobPos.getX() <= lightPos.getX() + 1 &&
                    mobPos.getZ() >= lightPos.getZ() - 1 && mobPos.getZ() <= lightPos.getZ() + 1 &&
                    mobPos.getY() >= lightPos.getY() - 2 && mobPos.getY() <= lightPos.getY() + 1)
                if (state.getBlock() instanceof ILightSource lightSource)
                    lightSource.setLit(state, lightPos, this.MOB.getLevel(), false);
            if (this.MOB instanceof IGoalLimiter goalLimiter) goalLimiter.stopGoals();
            return true;
        }

        @Override
        protected boolean isFit(BlockState state, BlockPos pos) {
            return state.getBlock() instanceof ILightSource source && source.isLit(state) && super.isFit(state, pos);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
        builder = builder.add(Attributes.MAX_HEALTH, 30);
        builder = builder.add(Attributes.ARMOR, 1);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.2);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.5);
        return builder;
    }

}
