
package com.obscuria.lumecore.registry;

import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.world.effects.AshFeverMobEffect;
import com.obscuria.lumecore.world.effects.ImmunityMobEffect;
import com.obscuria.lumecore.world.effects.PhantomChainsMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LumecoreMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, LumecoreMod.MODID);
	public static final RegistryObject<PhantomChainsMobEffect> PHANTOM_CHAINS = REGISTRY.register("phantom_chains", PhantomChainsMobEffect::new);
	public static final RegistryObject<AshFeverMobEffect> ASH_FEVER = REGISTRY.register("ash_fever", AshFeverMobEffect::new);
	public static final RegistryObject<ImmunityMobEffect> IMMUNITY = REGISTRY.register("immunity", ImmunityMobEffect::new);
}
