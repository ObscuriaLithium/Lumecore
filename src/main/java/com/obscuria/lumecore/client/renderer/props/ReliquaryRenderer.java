package com.obscuria.lumecore.client.renderer.props;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.client.model.props.ModelReliquary;
import com.obscuria.lumecore.world.entities.props.ReliquaryEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ReliquaryRenderer extends EntityRenderer<ReliquaryEntity> {
    private final ModelReliquary<ReliquaryEntity> MODEL;

    public ReliquaryRenderer(EntityRendererProvider.Context context) {
        super(context);
        MODEL = new ModelReliquary<>(context.bakeLayer(ModelReliquary.LAYER));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ReliquaryEntity entity) {
        return new ResourceLocation(LumecoreMod.MODID,"textures/entity/reliquary.png");
    }

    public void render(@NotNull ReliquaryEntity entity, float headYaw, float limbSwing, PoseStack pose, MultiBufferSource buffer, int i) {
        pose.pushPose();
        pose.translate(0.0D, 1.5, 0.0D);
        pose.mulPose(Vector3f.YP.rotationDegrees(180.0F - headYaw));

        ResourceLocation resourcelocation = getTextureLocation(entity);
        ModelReliquary<ReliquaryEntity> model = MODEL;

        pose.scale(-1.0F, -1.0F, 1.0F);
        pose.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        model.setupAnim(entity, limbSwing, 0.0F,
                Mth.lerp(Minecraft.getInstance().getFrameTime(), entity.ticksLerp, entity.ticks), 0.0F, 0.0F);
        VertexConsumer vertexconsumer = buffer.getBuffer(model.renderType(resourcelocation));
        model.renderToBuffer(pose, vertexconsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        super.render(entity, headYaw, limbSwing, pose, buffer, i);

        final ItemStack stack = entity.getItem();
        if (!stack.isEmpty() && Minecraft.getInstance().player != null) {
            final int count = Math.min(5, stack.getCount());
            pose.pushPose();
            model.translate(pose);
            pose.scale(0.5F, 0.5F, 0.5F);
            pose.mulPose(Vector3f.XP.rotationDegrees(-35.0F));
            pose.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            pose.translate(0, 0.2, 0.09);
            for (int loop = 0; loop < count; loop++) {
                Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(Minecraft.getInstance().player, stack,
                        ItemTransforms.TransformType.FIXED, false, pose, buffer, i);
                pose.translate(0, -0.05, -0.065);
            }
            pose.popPose();
        }

        pose.popPose();
    }
}

