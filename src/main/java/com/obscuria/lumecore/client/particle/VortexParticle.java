
package com.obscuria.lumecore.client.particle;

import com.obscuria.lumecore.LumecoreUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class VortexParticle extends TextureSheetParticle {
	public static Provider provider(SpriteSet spriteSet) {
		return new Provider(spriteSet);
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public Provider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		public Particle createParticle(@NotNull SimpleParticleType typeIn, @NotNull ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new VortexParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
		}
	}

	private final SpriteSet spriteSet;

	private float angularVelocity;
	private final float angularAcceleration;

	protected VortexParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
		super(world, x, y, z);
		this.spriteSet = spriteSet;
		this.setSize(0.2f, 0.2f);
		this.quadSize *= 1.7f;
		this.lifetime = (int) LumecoreUtils.randomRange(40, 20);
		this.gravity = -0.04f;
		this.xd = vx;
		this.yd = vy;
		this.zd = vz;
		this.hasPhysics = true;
		this.angularVelocity = 0.01f;
		this.angularAcceleration = 0.01f;
		this.setSpriteFromAge(spriteSet);
	}

	@Override
	public @NotNull ParticleRenderType getRenderType() {
		return ParticleRenderType.NO_RENDER;
	}

	@Override
	public void tick() {
		super.tick();
		this.angularVelocity += this.angularAcceleration;
		this.xd = Math.cos(this.age / 4F) * angularVelocity;
		this.zd = Math.sin(this.age / 4F) * angularVelocity;
		if (!this.removed) {
			this.setSpriteFromAge(this.spriteSet);
			this.level.addParticle(ParticleTypes.ASH, this.x, this.y, this.z, 0, 0, 0);
		}
	}
}
