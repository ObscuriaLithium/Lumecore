package com.obscuria.lumecore.client;

import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.client.renderer.AshenWitchRenderer;
import com.obscuria.lumecore.client.renderer.props.LyingItemRenderer;
import com.obscuria.lumecore.client.renderer.props.ReliquaryRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModRenderers {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(LumecoreMod.Entities.RELIQUARY.get(), ReliquaryRenderer::new);
        event.registerEntityRenderer(LumecoreMod.Entities.LYING_ITEM.get(), LyingItemRenderer::new);

        event.registerEntityRenderer(LumecoreMod.Entities.ASHEN_WITCH.get(), AshenWitchRenderer::new);
    }
}
