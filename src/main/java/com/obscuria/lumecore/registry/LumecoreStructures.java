package com.obscuria.lumecore.registry;

import com.mojang.serialization.Codec;
import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.world.features.MansionStructure;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class LumecoreStructures {
    public static final DeferredRegister<StructureType<?>> REGISTRY = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, LumecoreMod.MODID);

    public static final RegistryObject<StructureType<MansionStructure>> MANSION = REGISTRY.register("mansion", () -> register(MansionStructure.CODEC));

    private static <T extends Structure> StructureType<T> register(Codec<T> structureCodec) {
        return () -> structureCodec;
    }
}
