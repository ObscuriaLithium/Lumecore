package com.obscuria.lumecore.world.entities;

import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.LumecoreUtils;
import com.obscuria.lumecore.network.SwarmInfectionMessage;
import com.obscuria.lumecore.registry.LumecoreEntities;
import com.obscuria.lumecore.registry.LumecoreSounds;
import com.obscuria.lumecore.system.MansionMonster;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

@MansionMonster(wing = MansionMonster.Wing.INFESTED)
public class SwarmEntity extends Monster {

    public float hiveScaleTarget = 1F;
    public float swarmScale = 1F;
    public float swarmScaleO = 1F;

    public SwarmEntity(PlayMessages.SpawnEntity message, Level level) {
        this(LumecoreEntities.SWARM.get(), level);
    }

    public SwarmEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level world) {
        return new FlyingPathNavigation(this, world);
    }

    @Override protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1, 20) {
            @Override
            protected Vec3 getPosition() {
                RandomSource random = SwarmEntity.this.getRandom();
                double dir_x = SwarmEntity.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
                double dir_y = SwarmEntity.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
                double dir_z = SwarmEntity.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
                return new Vec3(dir_x, dir_y, dir_z);
            }
        });
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (this.level.isClientSide) this.swarmScale = 1.6F;
        return false;
    }

    @Override
    public void tick() {
        if (this.level.isClientSide) {
            this.swarmScaleO = this.swarmScale;
            this.swarmScale += (this.hiveScaleTarget - this.swarmScale) * 0.2F;
            this.level.addParticle(ParticleTypes.ASH,
                    LumecoreUtils.randomRange(this.getX(), 0.8D),
                    LumecoreUtils.randomRange(this.getY() + 0.45D, 0.8D),
                    LumecoreUtils.randomRange(this.getZ(), 0.8D),
                    0, 0, 0);
        }
        this.setNoGravity(true);
        if (this.isOnGround() || this.isInWater()) this.setDeltaMovement(this.getDeltaMovement().add(0, 0.005, 0));
        else this.setDeltaMovement(this.getDeltaMovement().add(0, -0.001, 0));
        super.tick();
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if (entity instanceof Player player && !player.isCreative()) {
            if (this.level.isClientSide) System.out.println(1);
            if (!this.level.isClientSide) {
                LumecoreUtils.applyAshFever(player, 800);
                if (this.level instanceof ServerLevel serverLevel) for (int i = 0; i < 100; i++) {
                    serverLevel.sendParticles(ParticleTypes.ASH,
                            LumecoreUtils.randomRange(this.getX(), 2D),
                            LumecoreUtils.randomRange(this.getY() + 0.45D, 2D),
                            LumecoreUtils.randomRange(this.getZ(), 2D),
                            1, 0, 0, 0, 0);
                }
            }
            if (player instanceof ServerPlayer serverPlayer) LumecoreMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new SwarmInfectionMessage(0));
            this.discard();
            return true;
        }
        return false;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return LumecoreSounds.SWARM_AMBIENT.get();
    }

    @Override
    public boolean causeFallDamage(float l, float d, @NotNull DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.FLYING_SPEED, 0.4)
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2.0);
    }
}
