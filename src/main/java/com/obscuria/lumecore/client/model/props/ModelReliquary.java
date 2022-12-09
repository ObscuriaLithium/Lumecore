package com.obscuria.lumecore.client.model.props;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.world.entities.props.ReliquaryEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ModelReliquary<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation
            (LumecoreMod.MODID, "model_reliquary"), "main");

    private final ModelPart main, bottom, top;

    public ModelReliquary(ModelPart root) {
        this.main = root.getChild("main");
        this.bottom = main.getChild("bottom");
        this.top = main.getChild("top");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition top = main.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 13).addBox(-5.0F, -3.0F, -8.0F, 10.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -1.0F, -9.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 18).addBox(-4.0F, -2.0F, -7.0F, 8.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(36, 24).addBox(-4.0F, -2.0F, -1.0F, 8.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(36, 26).addBox(-4.0F, -2.0F, -7.0F, 8.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(38, 22).addBox(4.0F, -2.0F, -7.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(38, 24).addBox(-4.0F, -2.0F, -7.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 4.0F));

        PartDefinition bottom = main.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -2.0F, -4.0F, 10.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-4.0F, -2.0F, 3.0F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(36, 4).addBox(-4.0F, -2.0F, -3.0F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(36, 2).addBox(-4.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(36, 6).addBox(4.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(36, 12).addBox(-4.0F, 2.0F, -3.0F, 8.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void translate(PoseStack pose) {
        this.main.translateAndRotate(pose);
        this.bottom.translateAndRotate(pose);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof ReliquaryEntity reliquary) {
            this.top.xRot = (float) Math.toRadians(reliquary.isOpened() ?
                    -70 * Mth.lerp(Minecraft.getInstance().getFrameTime(), reliquary.animationLerp, reliquary.animation) :
                    -70 + 70 * Mth.lerp(Minecraft.getInstance().getFrameTime(), reliquary.animationLerp, reliquary.animation));
            this.main.zRot = (float) Math.cos(ageInTicks) * 0.3F * Mth.lerp(Minecraft.getInstance().getFrameTime(), reliquary.hurtLerp, reliquary.hurt);
        }
    }
}
