package com.obscuria.lumecore.world.entities.ai;

import java.util.EnumSet;
import java.util.List;

import com.obscuria.lumecore.world.entities.ai.attack.Attack;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

public class AttackTargetGoal extends Goal {
    protected final PathfinderMob mob;

    protected final List<Attack> attacks;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private Path path;
    private int update;
    private long lastCanUseCheck;

    public int attackTick = 0;

    public AttackTargetGoal(PathfinderMob mob, double speed, boolean alwaysSees, Attack... attacks) {
        this.mob = mob;
        this.attacks = List.of(attacks);
        this.speedModifier = speed;
        this.followingTargetEvenIfNotSeen = alwaysSees;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        long i = this.mob.level.getGameTime();
        if (i - this.lastCanUseCheck < 20L) return false;

        this.lastCanUseCheck = i;
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) return false;
        this.path = this.mob.getNavigation().createPath(target, 0);
        return true;
    }

    public boolean canContinueToUse() {
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) return false;
        return !(target instanceof Player) || !target.isSpectator() && !((Player)target).isCreative();
    }

    public void start() {
        this.mob.getNavigation().moveTo(this.path, this.speedModifier);
        this.mob.setAggressive(true);
        this.update = 0;
    }

    public void stop() {
        LivingEntity target = this.mob.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) this.mob.setTarget(null);
        this.mob.setAggressive(false);
        this.mob.getNavigation().stop();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        final LivingEntity target = this.mob.getTarget();
        if (target != null) {
            this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            double distanceToSqr = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
            this.update = Math.max(this.update - 1, 0);
            this.attackTick = Math.max(this.attackTick - 1, 0);
            if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(target)) && this.update <= 0) {
                this.update = 4 + this.mob.getRandom().nextInt(7);
                if (distanceToSqr > 1024.0D) {
                    this.update += 10;
                } else if (distanceToSqr > 256.0D) {
                    this.update += 5;
                }
                if (!this.mob.getNavigation().moveTo(target, this.speedModifier)) {
                    this.update += 15;
                }
                this.update = this.adjustedTickDelay(this.update);
            }
            final double distance = this.mob.getPosition(1F).distanceTo(target.getPosition(1F));
            attacks.forEach(attack -> { if (this.attackTick == 0 && attack.use(this.mob, target, distance))
                    this.attackTick = attack.TICKS + 1; });
            attacks.forEach(attack -> attack.tick(this.mob, target, distance));
        }
    }
}
