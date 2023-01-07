package com.obscuria.lumecore.client;

import com.obscuria.lumecore.client.model.ModelAshenWitch;
import com.obscuria.lumecore.client.model.ModelHive;
import com.obscuria.lumecore.client.model.props.ModelReliquary;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ModModels {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelReliquary.LAYER, ModelReliquary::createBodyLayer);
        event.registerLayerDefinition(ModelAshenWitch.LAYER, ModelAshenWitch::createBodyLayer);
        event.registerLayerDefinition(ModelHive.LAYER, ModelHive::createBodyLayer);
    }
}
