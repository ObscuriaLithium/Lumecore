package com.obscuria.lumecore.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.obscuria.lumecore.world.entities.LyingItemEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LyingItemRenderer extends EntityRenderer<LyingItemEntity> {

    protected LyingItemRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull LyingItemEntity entity) {
        return new ResourceLocation("lumecore:textures/entities/blank.png");
    }

    public void render(LyingItemEntity entity, float headYaw, float limbSwing, PoseStack pose, MultiBufferSource source, int i) {
        super.render(entity, headYaw, limbSwing, pose, source, i);
        final ItemStack stack = entity.getItem();
        if (!stack.isEmpty() && Minecraft.getInstance().player != null) {
            pose.pushPose();
            pose.scale(0.66F, 0.66F, 0.66F);
            pose.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            pose.mulPose(Vector3f.ZP.rotationDegrees(0.0F + headYaw));
            pose.translate(0, 0, 0.035);
            Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(Minecraft.getInstance().player, stack,
                    ItemTransforms.TransformType.FIXED, false, pose, source, i);
            pose.popPose();
        }
    }
}

