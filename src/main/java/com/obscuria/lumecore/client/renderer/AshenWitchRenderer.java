package com.obscuria.lumecore.client.renderer;


import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.client.model.ModelAshenWitch;
import com.obscuria.lumecore.world.entities.AshenWitchEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AshenWitchRenderer extends MobRenderer<AshenWitchEntity, ModelAshenWitch<AshenWitchEntity>> {
    public AshenWitchRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelAshenWitch<>(context.bakeLayer(ModelAshenWitch.LAYER)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AshenWitchEntity entity) {
        return new ResourceLocation(LumecoreMod.MODID, "textures/entity/ashen_witch.png");
    }
}


