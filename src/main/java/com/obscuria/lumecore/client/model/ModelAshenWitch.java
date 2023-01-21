package com.obscuria.lumecore.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.obscureapi.animations.HekateLib;
import com.obscuria.obscureapi.client.animations.AnimatedPart;
import com.obscuria.obscureapi.client.animations.KeyFrame;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ModelAshenWitch<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            new ResourceLocation(LumecoreMod.MODID, "ashen_witch"), "main");
    private final ModelPart main, body_top, body_top2, head, hair1, hair2, hair3, hair4, hair5, hair6, cloth1_1, cloth1_2, cloth1_3, cloth1_4,
            cloth1_5, cloth1_6, cloth2_1, cloth2_2, cloth2_3, cloth2_4, cloth2_5, cloth2_6, cloth2_7, left_booby, right_booby, left_arm,
            left_arm_bottom, right_arm, right_arm_bottom, broom, body_bottom, left_leg, left_leg_bottom, right_leg, right_leg_bottom;

    public ModelAshenWitch(ModelPart root) {
        this.main = root.getChild("main");
        this.body_top = main.getChild("body_top");
        this.body_top2 = body_top.getChild("body_top2");
        this.head = body_top2.getChild("head");
        this.hair1 = head.getChild("hair1");
        this.hair2 = hair1.getChild("hair2");
        this.hair3 = hair2.getChild("hair3");
        this.hair4 = hair3.getChild("hair4");
        this.hair5 = hair4.getChild("hair5");
        this.hair6 = hair5.getChild("hair6");
        this.cloth1_1 = body_top2.getChild("cloth1_1");
        this.cloth1_2 = cloth1_1.getChild("cloth1_2");
        this.cloth1_3 = cloth1_2.getChild("cloth1_3");
        this.cloth1_4 = cloth1_3.getChild("cloth1_4");
        this.cloth1_5 = cloth1_4.getChild("cloth1_5");
        this.cloth1_6 = cloth1_5.getChild("cloth1_6");
        this.cloth2_1 = body_top2.getChild("cloth2_1");
        this.cloth2_2 = cloth2_1.getChild("cloth2_2");
        this.cloth2_3 = cloth2_2.getChild("cloth2_3");
        this.cloth2_4 = cloth2_3.getChild("cloth2_4");
        this.cloth2_5 = cloth2_4.getChild("cloth2_5");
        this.cloth2_6 = cloth2_5.getChild("cloth2_6");
        this.cloth2_7 = cloth2_6.getChild("cloth2_7");
        this.left_booby = body_top2.getChild("left_booby");
        this.right_booby = body_top2.getChild("right_booby");
        this.left_arm = body_top2.getChild("left_arm");
        this.left_arm_bottom = left_arm.getChild("left_arm_bottom");
        this.right_arm = body_top2.getChild("right_arm");
        this.right_arm_bottom = right_arm.getChild("right_arm_bottom");
        this.broom = right_arm_bottom.getChild("broom");
        this.body_bottom = main.getChild("body_bottom");
        this.left_leg = body_bottom.getChild("left_leg");
        this.left_leg_bottom = left_leg.getChild("left_leg_bottom");
        this.right_leg = body_bottom.getChild("right_leg");
        this.right_leg_bottom = right_leg.getChild("right_leg_bottom");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(),
                PartPose.offset(0.0F, 12.0F, 150.0F));
        PartDefinition body_top = main.addOrReplaceChild("body_top", CubeListBuilder.create().texOffs(29, 13)
                        .addBox(-3.0F, -5.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.01F)),
                PartPose.offset(0.0F, -2.0F, -150.0F));
        PartDefinition body_top2 = body_top.addOrReplaceChild("body_top2", CubeListBuilder.create().texOffs(0, 16)
                        .addBox(-4.0F, -6.0F, -2.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -4.0F, 0.0F));
        PartDefinition head = body_top2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 48)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.1F)),
                PartPose.offset(0.0F, -6.0F, 0.0F));
        PartDefinition hair1 = head.addOrReplaceChild("hair1", CubeListBuilder.create().texOffs(32, 52)
                        .addBox(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -4.0F, 4.0F, -0.1745F, 0.0F, 0.0F));
        PartDefinition hair2 = hair1.addOrReplaceChild("hair2", CubeListBuilder.create().texOffs(56, 49)
                        .addBox(-4.0F, -4.0F, -2.0F, 8.0F, 8.0F, 7.0F, new CubeDeformation(-0.3F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, -0.2182F, 0.0F, 0.0F));
        PartDefinition hair3 = hair2.addOrReplaceChild("hair3", CubeListBuilder.create().texOffs(56, 34)
                        .addBox(-4.0F, -4.0F, -2.0F, 8.0F, 8.0F, 7.0F, new CubeDeformation(-0.7F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, -0.2618F, 0.0F, 0.0F));
        PartDefinition hair4 = hair3.addOrReplaceChild("hair4", CubeListBuilder.create().texOffs(56, 49)
                        .addBox(-4.0F, -4.0F, -3.0F, 8.0F, 8.0F, 7.0F, new CubeDeformation(-1.2F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, -0.2618F, 0.0F, 0.0F));
        PartDefinition hair5 = hair4.addOrReplaceChild("hair5", CubeListBuilder.create().texOffs(56, 34)
                        .addBox(-4.0F, -4.0F, -3.0F, 8.0F, 8.0F, 7.0F, new CubeDeformation(-1.7F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.2182F, 0.0F, 0.0F));
        PartDefinition hair6 = hair5.addOrReplaceChild("hair6", CubeListBuilder.create().texOffs(56, 49)
                        .addBox(-4.0F, -4.0F, -3.0F, 8.0F, 8.0F, 7.0F, new CubeDeformation(-2.2F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, -0.1745F, 0.0F, 0.0F));
        PartDefinition cloth1_1 = body_top2.addOrReplaceChild("cloth1_1", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-1.5F, 0.0F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.75F, -4.0F, 2.25F));
        PartDefinition cloth1_2 = cloth1_1.addOrReplaceChild("cloth1_2", CubeListBuilder.create().texOffs(52, 0)
                .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition cloth1_3 = cloth1_2.addOrReplaceChild("cloth1_3", CubeListBuilder.create().texOffs(52, 3)
                .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition cloth1_4 = cloth1_3.addOrReplaceChild("cloth1_4", CubeListBuilder.create().texOffs(52, 6)
                .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition cloth1_5 = cloth1_4.addOrReplaceChild("cloth1_5", CubeListBuilder.create().texOffs(52, 9)
                .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition cloth1_6 = cloth1_5.addOrReplaceChild("cloth1_6", CubeListBuilder.create().texOffs(52, 12)
                        .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition cloth2_1 = body_top2.addOrReplaceChild("cloth2_1", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-1.5F, 0.0F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(2.25F, -4.0F, 2.25F));
        PartDefinition cloth2_2 = cloth2_1.addOrReplaceChild("cloth2_2", CubeListBuilder.create().texOffs(52, 0)
                .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition cloth2_3 = cloth2_2.addOrReplaceChild("cloth2_3", CubeListBuilder.create().texOffs(52, 0)
                .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition cloth2_4 = cloth2_3.addOrReplaceChild("cloth2_4", CubeListBuilder.create().texOffs(52, 3)
                .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition cloth2_5 = cloth2_4.addOrReplaceChild("cloth2_5", CubeListBuilder.create().texOffs(52, 6)
                .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition cloth2_6 = cloth2_5.addOrReplaceChild("cloth2_6", CubeListBuilder.create().texOffs(52, 9)
                .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition cloth2_7 = cloth2_6.addOrReplaceChild("cloth2_7", CubeListBuilder.create().texOffs(52, 12)
                        .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 3.0F, 0.0F));
        PartDefinition left_booby = body_top2.addOrReplaceChild("left_booby", CubeListBuilder.create(),
                PartPose.offset(2.0F, -3.0F, -0.25F));
        PartDefinition left_boobyF = left_booby.addOrReplaceChild("left_boobyF", CubeListBuilder.create().texOffs(24, 0)
                        .addBox(-1.9696F, -2.0F, -0.9027F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, -0.5303F, -0.151F, 0.0879F));
        PartDefinition right_booby = body_top2.addOrReplaceChild("right_booby", CubeListBuilder.create(),
                PartPose.offset(-2.0F, -3.0F, -0.25F));
        PartDefinition right_boobyF = right_booby.addOrReplaceChild("right_boobyF", CubeListBuilder.create().texOffs(32, 28)
                        .addBox(-4.0F, -2.0F, -1.25F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(2.0F, 0.0F, -2.0F, -0.5303F, 0.151F, -0.0879F));
        PartDefinition left_arm = body_top2.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 19)
                        .addBox(-1.0F, -1.5F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(5.0F, -4.5F, 0.0F));
        PartDefinition left_arm_bottom = left_arm.addOrReplaceChild("left_arm_bottom", CubeListBuilder.create().texOffs(0, 37)
                        .addBox(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.01F)),
                PartPose.offset(0.5F, 4.5F, 0.0F));
        PartDefinition right_arm = body_top2.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(41, 36)
                        .addBox(-2.0F, -1.5F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.0F, -4.5F, 0.0F));
        PartDefinition right_arm_bottom = right_arm.addOrReplaceChild("right_arm_bottom", CubeListBuilder.create().texOffs(29, 36)
                        .addBox(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.01F)),
                PartPose.offset(-0.5F, 4.5F, 0.0F));
        PartDefinition broom = right_arm_bottom.addOrReplaceChild("broom",
                CubeListBuilder.create().texOffs(66, 0)
                        .addBox(-0.5F, -0.5F, -15.5F, 1.0F, 1.0F, 30.0F, new CubeDeformation(0.0F)).texOffs(111, 26)
                        .addBox(-1.5F, -1.5F, -16.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(72, 18)
                        .addBox(-2.5F, -2.5F, -23.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(72, 6)
                        .addBox(-2.5F, -2.5F, -25.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(-0.2F)).texOffs(98, 23)
                        .addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.2F)).texOffs(72, 18)
                        .addBox(-2.5F, -2.5F, -26.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(-0.5F)).texOffs(72, 6)
                        .addBox(-2.5F, -2.5F, -27.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(-1.0F)).texOffs(72, 18)
                        .addBox(-2.5F, -2.5F, -28.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(-1.4F)),
                PartPose.offset(0.0F, 4.5F, 0.0F));
        PartDefinition body_bottom = main.addOrReplaceChild("body_bottom", CubeListBuilder.create().texOffs(20, 22)
                        .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)),
                PartPose.offset(0.0F, -2.0F, -150.0F));
        PartDefinition left_leg = body_bottom.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(40, 0)
                        .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(2.0F, 2.0F, 0.0F));
        PartDefinition left_leg_bottom = left_leg.addOrReplaceChild("left_leg_bottom", CubeListBuilder.create().texOffs(16, 28)
                        .addBox(-2.0F, -1.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.01F)),
                PartPose.offset(0.0F, 6.0F, 0.0F));
        PartDefinition right_leg = body_bottom.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(12, 39)
                        .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, 2.0F, 0.0F));
        PartDefinition right_leg_bottom = right_leg.addOrReplaceChild("right_leg_bottom", CubeListBuilder.create().texOffs(0, 26)
                        .addBox(-4.0F, -1.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.011F)),
                PartPose.offset(2.0F, 6.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        final float idle = HekateLib.mod.idle(limbSwingAmount, 3F);
        final float move = HekateLib.mod.move(limbSwingAmount, 3F);
        final float s1 = 0.1F;
        final float s2 = 1.2F;

        HekateLib.render.tick(entity);
        HekateLib.render.prepare(main, body_top, body_top2, head, hair1, hair2, hair3, hair4, hair5, hair6, cloth1_1, cloth1_2, cloth1_3, cloth1_4,
                cloth1_5, cloth1_6, cloth2_1, cloth2_2, cloth2_3, cloth2_4, cloth2_5, cloth2_6, cloth2_7, left_booby, right_booby, left_arm,
                left_arm_bottom, right_arm, right_arm_bottom, broom, body_bottom, left_leg, left_leg_bottom, right_leg, right_leg_bottom);

        HekateLib.i(main, 0.2F, -0.2F, 0, 0, 0, 0, s1, 0F, ageInTicks, idle);
        HekateLib.i(body_top, 2F, -5F, 0, 0, 0, 0, s1, -0.2F, ageInTicks, idle);
        HekateLib.i(body_top2, 4F, -10F, 0, 0, 0, 0, s1, -0.3F, ageInTicks, idle);
        HekateLib.i(body_bottom, -5F, 10F, 0, 0, 0, 0, s1, 0F, ageInTicks, idle);
        HekateLib.i(right_leg, 5F, 1.5F, 0, -10F, 0, 7.5F, s1, 0F, ageInTicks, idle);
        HekateLib.i(right_leg_bottom, 6F, -25F, 0, 0, 0, 0, s1, 0F, ageInTicks, idle);
        HekateLib.i(left_leg, 5F, 1.5F, 0, 10F, 0, -7.5F, s1, 0F, ageInTicks, idle);
        HekateLib.i(left_leg_bottom, 6F, -25F, 0, 0, 0, 0, s1, 0F, ageInTicks, idle);
        HekateLib.i(right_arm, 4F, -1.5F, 0, -10F, 0, 7.5F, s1, 0F, ageInTicks, idle);
        HekateLib.i(right_arm_bottom, 0, 85F, 0, 0, 0, 0, s1, 0.2F, ageInTicks, idle);
        HekateLib.i(broom, 0, 30F, 0, 0, 0, 0, s1, 0.2F, ageInTicks, idle);
        HekateLib.i(left_arm, 30F, 0, 0, 0, 0, -5, s1 / 2F, 0F, ageInTicks, idle);
        HekateLib.i(left_arm_bottom, 25F, 35F, 0, 0, 0, 0, s1 / 2F, 0.2F, ageInTicks, idle);

        HekateLib.m(main, 0.5F, 0, 0, 0, 0, 0, s2, -0.1F, limbSwing, move);
        HekateLib.m(body_top, 4F, -8F, 0, 0, 0, 0, s2, -0.2F, limbSwing, move);
        HekateLib.m(body_top2, 5F, -10F, 0, 0, 0, 0, s2, -0.3F, limbSwing, move);
        HekateLib.m(right_leg, 45F, 15F, 0, 0, 0, 0, s2 / 2F, 0F, limbSwing, move);
        HekateLib.m(right_leg_bottom, -35F, -45F, 0, 0, 0, 0, s2 / 2F, 0.2F, limbSwing, move);
        HekateLib.m(left_leg, -45F, 15F, 0, 0, 0, 0, s2 / 2F, 0F, limbSwing, move);
        HekateLib.m(left_leg_bottom, 35F, -45F, 0, 0, 0, 0, s2 / 2F, 0.2F, limbSwing, move);
        HekateLib.m(right_arm, 0, 60F, 3F, -27F, 0, 7F, s2, 0F, limbSwing, move);
        HekateLib.m(right_arm_bottom, 0, 85F, 0, 0, 0, 0, s2, 0.2F, limbSwing, move);
        HekateLib.m(broom, 0, 30F, 0, 0, 0, 0, s2, 0.2F, limbSwing, move);
        HekateLib.m(left_arm, 30F, 0, 0, 0, 0, -5, s2 / 2F, 0F, limbSwing, move);
        HekateLib.m(left_arm_bottom, 25F, 35F, 0, 0, 0, 0, s2 / 2F, 0.2F, limbSwing, move);

        HekateLib.render.animation(entity, "attack", ageInTicks,
                new KeyFrame(30, 10, 4F, 20F,
                        new AnimatedPart(body_top, -10F, 17.5F, 0),
                        new AnimatedPart(body_top2, -7.5F, 42.5F, 0),
                        new AnimatedPart(head, 0, -55F, 0),
                        new AnimatedPart(right_arm, 110F, 25F, 70F),
                        new AnimatedPart(right_arm_bottom, 20F, 0, 0),
                        new AnimatedPart(broom, -17.5F, 0, 0),
                        new AnimatedPart(left_arm, 65F, 17F, -7F),
                        new AnimatedPart(left_arm_bottom, 95F, 0, 0)),
                new KeyFrame(10, 0, 20F, 6F,
                        new AnimatedPart(body_top, 7F, -13F, -2.5F),
                        new AnimatedPart(body_top2, 3F, -8F, -2F),
                        new AnimatedPart(head, -27.5F, 25F, 0),
                        new AnimatedPart(right_arm, 11F, 11F, 50F),
                        new AnimatedPart(right_arm_bottom, 15F, 0, 0),
                        new AnimatedPart(broom, -82.5F, 0, 0),
                        new AnimatedPart(left_arm, 5F, -11F, -17F),
                        new AnimatedPart(left_arm_bottom, 105F, 0, 0)));

        HekateLib.render.animation(entity, "piercingAttack", ageInTicks,
                new KeyFrame(30, 10, 4F, 20F,
                        new AnimatedPart(body_top, -13F, -18F, -1F),
                        new AnimatedPart(body_top2, -13F, -20F, 0),
                        new AnimatedPart(head, 14, 40F, 0),
                        new AnimatedPart(right_arm, -14F, 28F, 43F),
                        new AnimatedPart(right_arm_bottom, 40F, 0, 0),
                        new AnimatedPart(broom, 32.5F, 0, 0),
                        new AnimatedPart(left_arm, 70F, -21F, -40F),
                        new AnimatedPart(left_arm_bottom, 62.5F, 0, 0)),
                new KeyFrame(10, 0, 20F, 6F,
                        new AnimatedPart(body_top, 2.5F, 17.5F, 0),
                        new AnimatedPart(body_top2, 3F, 15F, 0),
                        new AnimatedPart(head, -6F, -36F, 0),
                        new AnimatedPart(right_arm, 41F, -17F, 40F),
                        new AnimatedPart(right_arm_bottom, 30F, 0, 0),
                        new AnimatedPart(broom, -82.5F, 0, 0),
                        new AnimatedPart(left_arm, -47F, -12F, -30F),
                        new AnimatedPart(left_arm_bottom, 62.5F, 0, 0)));
    }
}
