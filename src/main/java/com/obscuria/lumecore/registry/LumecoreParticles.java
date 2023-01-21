
package com.obscuria.lumecore.registry;

import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.client.particle.VortexParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LumecoreParticles {
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, LumecoreMod.MODID);

	public static final RegistryObject<SimpleParticleType> VORTEX = REGISTRY.register("vortex", () -> new SimpleParticleType(true));

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class Registry {
		@SubscribeEvent
		public static void registerParticles(RegisterParticleProvidersEvent event) {
			event.register(LumecoreParticles.VORTEX.get(), VortexParticle::provider);
		}
	}
}
