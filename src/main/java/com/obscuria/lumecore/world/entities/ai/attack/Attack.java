package com.obscuria.lumecore.world.entities.ai.attack;

import net.minecraft.world.entity.LivingEntity;

public abstract class Attack {
    public final String NAME;
    public final int TICKS;
    public final int HURT_TICK;
    public final int RELOAD;
    public final int RELOAD_RANDOM;

    public int reload = 0;

    public Attack(String name, int ticks, int hurtTick, int reload, int reloadRandom) {
        this.NAME = name;
        this.TICKS = ticks;
        this.HURT_TICK = hurtTick;
        this.RELOAD = reload;
        this.RELOAD_RANDOM = reloadRandom;
    }

    public boolean use(LivingEntity entity, LivingEntity target, double distance) {
        return false;
    }

    public void tick(LivingEntity entity, LivingEntity target, double distance) {
        reload = Math.max(reload - 1, 0);
    }
}
