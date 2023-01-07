package com.obscuria.lumecore.world.entities.props;

import com.obscuria.lumecore.LumecoreUtils;
import com.obscuria.lumecore.registry.LumecoreEntities;
import com.obscuria.lumecore.registry.LumecoreItems;
import com.obscuria.lumecore.world.MansionHandler;
import com.obscuria.lumecore.world.entities.DespawnProtection;
import com.obscuria.lumecore.world.entities.FromInfestedWing;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MansionCoreEntity extends Entity {

    private final List<MansionHandler.MansionWing> WINGS = new ArrayList<>();

    public MansionCoreEntity(PlayMessages.SpawnEntity message, Level level) {
        this(LumecoreEntities.MANSION_CORE.get(), level);
    }

    public MansionCoreEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag data) {
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag data) {
    }

    @Override
    public void tick() {
        this.coreTick();
        if (this.level.isClientSide) this.WINGS.forEach(wing -> wing.spawnInfectionParticles(this.level));
        super.tick();
    }

    public void coreTick() {
        if (this.tickCount % 10 == 0) {
            if (this.WINGS.isEmpty()) createWings();
            this.level.getEntitiesOfClass(Player.class, new AABB(this.blockPosition(), this.blockPosition()).inflate(60)).forEach(LumecoreUtils::applyPhantomChains);
        }
        if (this.tickCount % 100 == 0) {
            checkMonstersInWings();
        }
    }

    private void createWings() {
        this.WINGS.clear();
        this.WINGS.add(MansionHandler.createWing(
                this.blockPosition().offset(-47, 18, -24),
                this.blockPosition().offset(-26, 1, 24)));
    }

    private void checkMonstersInWings() {
        if (this.level.isClientSide) return;
        this.WINGS.forEach(wing -> this.level.getEntitiesOfClass(Monster.class, wing.getArea()).forEach(monster -> {
            if (!(monster instanceof FromInfestedWing) && canBeDespawned(monster)) monster.discard();
        }));
    }

    private boolean canBeDespawned(Monster monster) {
        return !(monster instanceof DespawnProtection) && !(monster.getTarget() instanceof Player);
    }

    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        if (player.getItemInHand(hand).getItem() == LumecoreItems.DEBUG_TOOL.get()) {
            discard();
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }
}
