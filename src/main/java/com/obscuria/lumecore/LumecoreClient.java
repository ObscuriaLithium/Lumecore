package com.obscuria.lumecore;

import com.obscuria.lumecore.registry.LumecoreMobEffects;
import com.obscuria.lumecore.registry.LumecoreSounds;
import com.obscuria.obscureapi.utils.TextHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public final class LumecoreClient {

    private static int ashFeverTick = 0;

    public static double getDistanceToPlayer(double x, double y, double z) {
        return Minecraft.getInstance().player == null ? 0 : Minecraft.getInstance().player.getPosition(1F).distanceTo(new Vec3(x, y, z));
    }

    public static void playSound(SoundEvent sound, float volume, float pitch) {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forLocalAmbience(sound, volume, pitch));
    }

    @Mod.EventBusSubscriber(modid = LumecoreMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
    public static class ModEvents {
    }

    @Mod.EventBusSubscriber(value = {Dist.CLIENT})
    public static class ForgeEvents {

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void tooltipEvent(ItemTooltipEvent event) {
            if (LumecoreUtils.isImmune(event.getItemStack()))
                event.getToolTip().add(1, TextHelper.component("§6Immune to Mansion"));
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void playerTick(TickEvent.ClientTickEvent event) {
            if (Minecraft.getInstance().player == null) return;
            final Player player = Minecraft.getInstance().player;

            if (ashFeverTick > 0 && !Minecraft.getInstance().isPaused()) ashFeverTick--;
            if (ashFeverTick <= 0 && player.hasEffect(LumecoreMobEffects.ASH_FEVER.get())) {
                player.level.playLocalSound(player.getX(), player.getY(), player.getZ(), LumecoreSounds.HEARTBEAT.get(),
                        SoundSource.AMBIENT, 1F, 1F, false);
                ashFeverTick = (int) (20 * 18.8);
            }
        }
    }
}
