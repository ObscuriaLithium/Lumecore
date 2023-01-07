package com.obscuria.lumecore.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.world.entities.SwarmEntity;
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

import java.util.List;

public class ModelHive<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(LumecoreMod.MODID, "hive"), "main");
	private final ModelPart main, side1, side2, side3, side4, side5, side6, side7, side8, side9, side10;

	public ModelHive(ModelPart root) {
		this.main = root.getChild("main");
		this.side1 = main.getChild("side1");
		this.side2 = main.getChild("side2");
		this.side3 = main.getChild("side3");
		this.side4 = main.getChild("side4");
		this.side5 = main.getChild("side5");
		this.side6 = main.getChild("side6");
		this.side7 = main.getChild("side7");
		this.side8 = main.getChild("side8");
		this.side9 = main.getChild("side9");
		this.side10 = main.getChild("side10");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		main.addOrReplaceChild("side1", CubeListBuilder.create().texOffs(0, 0)
						.addBox(8.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -8.0F, 0.0F));
		main.addOrReplaceChild("side2", CubeListBuilder.create().texOffs(0, 0)
						.addBox(8.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -8.0F, 0.0F));
		main.addOrReplaceChild("side3", CubeListBuilder.create().texOffs(0, 0)
						.addBox(8.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -8.0F, 0.0F));
		main.addOrReplaceChild("side4", CubeListBuilder.create().texOffs(0, 0)
						.addBox(8.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -8.0F, 0.0F));
		main.addOrReplaceChild("side5", CubeListBuilder.create().texOffs(0, 0)
						.addBox(8.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -8.0F, 0.0F));
		main.addOrReplaceChild("side6", CubeListBuilder.create().texOffs(0, 0)
						.addBox(8.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -8.0F, 0.0F));
		main.addOrReplaceChild("side7", CubeListBuilder.create().texOffs(0, 0)
						.addBox(8.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -8.0F, 0.0F));
		main.addOrReplaceChild("side8", CubeListBuilder.create().texOffs(0, 0)
						.addBox(8.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -8.0F, 0.0F));
		main.addOrReplaceChild("side9", CubeListBuilder.create().texOffs(0, 0)
						.addBox(8.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -8.0F, 0.0F));
		main.addOrReplaceChild("side10", CubeListBuilder.create().texOffs(0, 0)
						.addBox(8.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -8.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		final List<ModelPart> parts = List.of(side1, side2, side3, side4, side5, side6, side7, side8, side9, side10);
		final float scale = entity instanceof SwarmEntity hive ? Mth.lerp(Minecraft.getInstance().getFrameTime(), hive.swarmScaleO, hive.swarmScale) : 1F;
		float mod = 0.6F;
		float total = 1;
		for (ModelPart part : parts) {
			part.setRotation(total * 36, ageInTicks * (0.4F * mod), ageInTicks * (0.2F * mod));
			part.xScale = scale; part.yScale = scale; part.zScale = scale;
			total++; mod += 0.1F;
		}
	}
}