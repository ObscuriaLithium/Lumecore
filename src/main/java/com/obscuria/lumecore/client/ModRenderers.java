package com.obscuria.lumecore.client;

import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.client.renderer.AshenWitchRenderer;
import com.obscuria.lumecore.client.renderer.HiveRenderer;
import com.obscuria.lumecore.client.renderer.props.LyingItemRenderer;
import com.obscuria.lumecore.client.renderer.props.ReliquaryRenderer;
import com.obscuria.lumecore.registry.LumecoreEntities;
import com.obscuria.lumecore.world.entities.props.MansionCoreEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModRenderers {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(LumecoreEntities.RELIQUARY.get(), ReliquaryRenderer::new);
        event.registerEntityRenderer(LumecoreEntities.LYING_ITEM.get(), LyingItemRenderer::new);

        event.registerEntityRenderer(LumecoreEntities.ASHEN_WITCH.get(), AshenWitchRenderer::new);
        event.registerEntityRenderer(LumecoreEntities.SWARM.get(), HiveRenderer::new);

        event.registerEntityRenderer(LumecoreEntities.MANSION_CORE.get(), context -> new EntityRenderer<>(context) {
            @Override public @NotNull ResourceLocation getTextureLocation(@NotNull MansionCoreEntity mansionHandler) {
                return new ResourceLocation(LumecoreMod.MODID, "textures/misc/blank.png");}});
    }
}

