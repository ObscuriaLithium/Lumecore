package com.obscuria.lumecore.registry;

import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.world.entities.AshenWitchEntity;
import com.obscuria.lumecore.world.entities.SwarmEntity;
import com.obscuria.lumecore.world.entities.props.LyingItemEntity;
import com.obscuria.lumecore.world.entities.props.ReliquaryEntity;
import com.obscuria.lumecore.world.entities.props.MansionCoreEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LumecoreEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LumecoreMod.MODID);

    public static final RegistryObject<EntityType<ReliquaryEntity>> RELIQUARY = register("reliquary",
            EntityType.Builder.<ReliquaryEntity>of(ReliquaryEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(false)
                    .setTrackingRange(64).setUpdateInterval(3).fireImmune().sized(0.5f, 0.5f));
    public static final RegistryObject<EntityType<LyingItemEntity>> LYING_ITEM = register("lying_item",
            EntityType.Builder.<LyingItemEntity>of(LyingItemEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(false)
                    .setTrackingRange(64).setUpdateInterval(3).fireImmune().sized(0.5f, 0.1f));

    public static final RegistryObject<EntityType<AshenWitchEntity>> ASHEN_WITCH = register("ashen_witch",
            EntityType.Builder.<AshenWitchEntity>of(AshenWitchEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64).setUpdateInterval(3).fireImmune().sized(0.6f, 1.7f));
    public static final RegistryObject<EntityType<SwarmEntity>> SWARM = register("swarm",
            EntityType.Builder.<SwarmEntity>of(SwarmEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64).setUpdateInterval(3).sized(0.9f, 0.9f));

    public static final RegistryObject<EntityType<MansionCoreEntity>> MANSION_CORE = register("mansion_core",
            EntityType.Builder.<MansionCoreEntity>of(MansionCoreEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(false)
                    .setTrackingRange(64).setUpdateInterval(3).fireImmune().sized(0.3f, 0.3f));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> entityTypeBuilder) {
        return REGISTRY.register(name, () -> entityTypeBuilder.build(name));
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ASHEN_WITCH.get(), AshenWitchEntity.createAttributes().build());
        event.put(SWARM.get(), SwarmEntity.createAttributes().build());
    }
}
