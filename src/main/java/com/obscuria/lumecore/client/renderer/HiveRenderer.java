package com.obscuria.lumecore.client.renderer;


import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.client.model.ModelHive;
import com.obscuria.lumecore.world.entities.SwarmEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HiveRenderer extends MobRenderer<SwarmEntity, ModelHive<SwarmEntity>> {
    public HiveRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelHive<>(context.bakeLayer(ModelHive.LAYER)), 0f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SwarmEntity entity) {
        return new ResourceLocation(LumecoreMod.MODID, "textures/entity/hive.png");
    }
}


