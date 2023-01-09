package com.obscuria.lumecore.world;

import com.obscuria.lumecore.LumecoreUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;

public final class LumecoreEvents {

    public static void blockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof LivingEntity entity &&
                LumecoreUtils.Location.isInMansion(entity) && !LumecoreUtils.canBuildInMansion(entity))
            event.setCanceled(true);
    }

    public static void blockBroken(BlockEvent.BreakEvent event) {
        if (LumecoreUtils.Location.isInMansion(event.getPlayer()) && !LumecoreUtils.canBuildInMansion(event.getPlayer()))
            event.setCanceled(true);
    }

    public static void itemUsed(LivingEntityUseItemEvent.Start event) {
        if (LumecoreUtils.Location.isInMansion(event.getEntity()) && (event.getItem().getFoodProperties(event.getEntity()) != null ||
        event.getItem().getItem() == Items.MILK_BUCKET) && !LumecoreUtils.isImmune(event.getItem())) {
            final LivingEntity entity = event.getEntity();
            if (event.getEntity().getLevel() instanceof ServerLevel level) {
                level.playSound(null, entity, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 1F, 1F);
                level.playSound(null, entity, SoundEvents.WEEPING_VINES_FALL, SoundSource.PLAYERS, 1F, Mth.triangleWave(0.9F, 0.2F));
                level.sendParticles(ParticleTypes.SMOKE, entity.getX(), entity.getEyeY(), entity.getZ(), 10, 0.2, 0.2, 0.2, 0);
                level.sendParticles(ParticleTypes.ASH, entity.getX(), entity.getEyeY(), entity.getZ(), 20, 1, 1, 1, 0);
            }
            event.getItem().shrink(1);
            event.setCanceled(true);
        }
    }

    public static void explosion(ExplosionEvent.Start event ) {
        event.setCanceled(true);
    }
}
