package com.obscuria.lumecore;

import com.obscuria.lumecore.registry.LumecoreMobEffects;
import com.obscuria.lumecore.system.DespawnProtection;
import com.obscuria.lumecore.system.MansionImmunity;
import com.obscuria.lumecore.system.MansionMonster;
import com.obscuria.lumecore.world.MansionParts;
import com.obscuria.lumecore.world.entities.props.MansionCoreEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public final class LumecoreUtils {

    public static double randomRange(double base, double range) {
        return base + Math.cos(6.3D * Math.random()) * (range * Math.random());
    }

    public static void decay(LivingEntity entity, float percent, Iterator<ItemStack> items, @Nullable SoundEvent sound) {
        while (items.hasNext()) {
            final ItemStack stack = items.next();
            if (!isImmune(stack) && (stack.getItem() instanceof TieredItem || stack.getItem() instanceof ArmorItem)) {
                final int amount = (int) Math.ceil(stack.getMaxDamage() * percent);
                stack.hurtAndBreak(amount, entity, livingEntity -> {if (sound != null) livingEntity.level.playSound(null,
                        livingEntity, sound, SoundSource.PLAYERS, 1f, 0.9F + 0.2F * (float) Math.random());});
            }
        }
    }

    public static void giveImmunity(Iterator<ItemStack> items) {
        while (items.hasNext()) {
            final ItemStack stack = items.next();
            if (!stack.getItem().getClass().isAnnotationPresent(MansionImmunity.class)) stack.getOrCreateTag().putBoolean("ImmuneToMansion", true);
        }
    }

    public static boolean isImmune(ItemStack stack) {
        return stack.getItem().getClass().isAnnotationPresent(MansionImmunity.class) || (stack.getTag() != null && stack.getTag().getBoolean("ImmuneToMansion"));
    }

    public static void applyPhantomChains(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(LumecoreMobEffects.PHANTOM_CHAINS.get(), 215, 0, true, false, false));
    }

    public static void applyAshFever(LivingEntity entity, int ticks) {
        if (entity.level.isClientSide) return;
        if (entity.hasEffect(LumecoreMobEffects.IMMUNITY.get())) return;
        final int totalTicks = entity.hasEffect(LumecoreMobEffects.ASH_FEVER.get()) ? entity.getEffect(LumecoreMobEffects.ASH_FEVER.get()).getDuration() + ticks : ticks;
        entity.addEffect(new MobEffectInstance(LumecoreMobEffects.ASH_FEVER.get(), totalTicks, 0, true, false, true));
        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, totalTicks, 0, false, false, false));
        entity.addEffect(new MobEffectInstance(MobEffects.WITHER, totalTicks, 0, false, false, false));
        entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, totalTicks, 0, false, false, false));
    }

    public static void applyImmunity(LivingEntity entity, int ticks) {
        if (entity.level.isClientSide) return;
        if (entity.hasEffect(LumecoreMobEffects.ASH_FEVER.get())) {
            entity.removeEffect(LumecoreMobEffects.ASH_FEVER.get());
            entity.removeEffect(MobEffects.DARKNESS);
            entity.removeEffect(MobEffects.WITHER);
            entity.removeEffect(MobEffects.HUNGER);
        }
        final int totalTicks = entity.hasEffect(LumecoreMobEffects.IMMUNITY.get()) ? entity.getEffect(LumecoreMobEffects.IMMUNITY.get()).getDuration() + ticks : ticks;
        entity.addEffect(new MobEffectInstance(LumecoreMobEffects.IMMUNITY.get(), totalTicks, 0, true, false, true));
    }

    public static boolean canBeDespawned(Monster monster) {
        return !monster.getClass().isAnnotationPresent(DespawnProtection.class) && !(monster.getTarget() instanceof Player);
    }

    public static MansionMonster.Wing getOriginWing(Monster monster) {
        return monster.getClass().isAnnotationPresent(MansionMonster.class) ? monster.getClass().getAnnotation(MansionMonster.class).wing() : MansionMonster.Wing.NONE;
    }

    public static class Location {

        public static boolean isInMansion(LivingEntity entity) {
            return !getCores(entity).isEmpty();
        }

        public static boolean isInWing(LivingEntity entity) {
            return getWingState(entity) != null;
        }

        public static boolean isInWing(LivingEntity entity, MansionParts.WingState state) {
            return getWingState(entity) == state;
        }

        public static List<MansionCoreEntity> getCores(LivingEntity entity) {
            return entity.getLevel().getEntitiesOfClass(MansionCoreEntity.class, new AABB(entity.blockPosition()).inflate(MansionParts.SIZE));
        }

        public static @Nullable MansionCoreEntity getCore(LivingEntity entity) {
            final List<MansionCoreEntity> cores = entity.getLevel().getEntitiesOfClass(MansionCoreEntity.class, new AABB(entity.blockPosition()).inflate(MansionParts.SIZE));
            return cores.isEmpty() ? null : cores.get(0);
        }

        public static @Nullable MansionParts.WingState getWingState(LivingEntity entity) {
            final MansionCoreEntity core = getCore(entity);
            if (core == null) return null;
            for (MansionParts.Wing wing : core.getWings().getAll())
                if (wing.getArea().contains(entity.getX(), entity.getY(), entity.getZ())) return wing.getState();
            return null;
        }
    }
}
