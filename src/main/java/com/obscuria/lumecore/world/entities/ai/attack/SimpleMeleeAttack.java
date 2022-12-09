package com.obscuria.lumecore.world.entities.ai.attack;

import com.obscuria.obscureapi.animations.HekateProvider;
import com.obscuria.obscureapi.animations.IHekateProvider;
import net.minecraft.world.entity.LivingEntity;

public class SimpleMeleeAttack extends Attack {

    public final double START_DISTANCE;
    public final double HURT_DISTANCE;

    public SimpleMeleeAttack(String name, int ticks, int hurtTick, int reload, int reloadRandom, double startDistance, double hurtDistance) {
        super(name, ticks, hurtTick, reload, reloadRandom);
        this.START_DISTANCE = startDistance;
        this.HURT_DISTANCE = hurtDistance;
    }

    @Override
    public boolean use(LivingEntity entity, LivingEntity target, double distance) {
        if (entity instanceof IHekateProvider iHekateProvider) {
            final HekateProvider provider = iHekateProvider.getHekateProvider();
            if (this.reload == 0 && !provider.isPlaying(this.NAME) && distance < this.START_DISTANCE) {
                provider.play(this.NAME, this.TICKS);
                this.reload = this.RELOAD + entity.getRandom().nextInt(this.RELOAD_RANDOM);
                return true;
            }
        }
        return false;
    }

    @Override
    public void tick(LivingEntity entity, LivingEntity target, double distance) {
        super.tick(entity, target, distance);
        if (entity instanceof IHekateProvider iHekateProvider) {
            final HekateProvider provider = iHekateProvider.getHekateProvider();
            if (provider.getTick(this.NAME) == this.HURT_TICK && distance <= this.HURT_DISTANCE)
                this.doHurt(target, entity);
        }
    }

    public void doHurt(LivingEntity target, LivingEntity entity) {
        entity.doHurtTarget(target);
    }
}
