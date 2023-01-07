package com.obscuria.lumecore.registry;

import com.obscuria.lumecore.LumecoreMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LumecoreSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LumecoreMod.MODID);

    public static final RegistryObject<SoundEvent> HEARTBEAT = REGISTRY.register("effect.heartbeat",
            () -> new SoundEvent(new ResourceLocation(LumecoreMod.MODID, "effect.heartbeat")));
    public static final RegistryObject<SoundEvent> SWARM_AMBIENT = REGISTRY.register("entity.swarm.ambient",
            () -> new SoundEvent(new ResourceLocation(LumecoreMod.MODID, "entity.swarm.ambient")));
    public static final RegistryObject<SoundEvent> SWARM_INFECTION = REGISTRY.register("entity.swarm.infection",
            () -> new SoundEvent(new ResourceLocation(LumecoreMod.MODID, "entity.swarm.infection")));
}
