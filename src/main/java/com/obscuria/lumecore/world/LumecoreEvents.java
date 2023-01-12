package com.obscuria.lumecore.world;

import com.obscuria.lumecore.LumecoreUtils;
import com.obscuria.lumecore.world.entities.props.MansionCoreEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;

public final class LumecoreEvents {

    public static void blockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            final MansionCoreEntity core = LumecoreUtils.Location.getCore(entity);
            if (core != null && !core.getRuleCanBuild()) event.setCanceled(true);
        }
    }

    public static void blockBroken(BlockEvent.BreakEvent event) {
        final MansionCoreEntity core = LumecoreUtils.Location.getCore(event.getPlayer());
        if (core != null && !core.getRuleCanBuild()) event.setCanceled(true);
    }

    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START || event.player.level.isClientSide) return;
        if (event.player.tickCount % 20 == 0 && LumecoreUtils.Location.isInMansion(event.player)) {
            final MansionCoreEntity core = LumecoreUtils.Location.getCore(event.player);
            if (core != null && core.getRuleEquipmentDecay())
                LumecoreUtils.decay(event.player, 0.01F, event.player.getAllSlots().iterator(), SoundEvents.ITEM_BREAK);
        }
    }

    public static void itemUsed(LivingEntityUseItemEvent.Start event) {
        if (!LumecoreUtils.isImmune(event.getItem()) && (event.getItem().getFoodProperties(event.getEntity()) != null || event.getItem().getItem() == Items.MILK_BUCKET)) {
            final LivingEntity entity = event.getEntity();
            final MansionCoreEntity core = LumecoreUtils.Location.getCore(entity);
            if (core != null && core.getRuleFoodDecay()) {
                if (entity.getLevel() instanceof ServerLevel level) {
                    level.playSound(null, entity, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 1F, 1F);
                    level.playSound(null, entity, SoundEvents.WEEPING_VINES_FALL, SoundSource.PLAYERS, 1F, Mth.triangleWave(0.9F, 0.2F));
                    level.sendParticles(ParticleTypes.SMOKE, entity.getX(), entity.getEyeY(), entity.getZ(), 10, 0.2, 0.2, 0.2, 0);
                    level.sendParticles(ParticleTypes.ASH, entity.getX(), entity.getEyeY(), entity.getZ(), 20, 1, 1, 1, 0);
                }
                event.getItem().shrink(1);
                event.setCanceled(true);
            }
        }
    }

    public static void explosion(ExplosionEvent.Start event ) {
        event.setCanceled(true);
    }
}
