package com.obscuria.lumecore.world.entities;

import com.obscuria.lumecore.LumecoreMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

public class ReliquaryEntity extends Entity {

    protected static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.defineId(ReliquaryEntity.class, EntityDataSerializers.ITEM_STACK);
    protected static final EntityDataAccessor<Boolean> OPENED = SynchedEntityData.defineId(ReliquaryEntity.class, EntityDataSerializers.BOOLEAN);
    public float animation = 1;
    public float animationLerp = 1;
    public float hurt = 0;
    public float hurtLerp = 0;
    public float ticks = 0;
    public float ticksLerp = 0;

    public ReliquaryEntity(PlayMessages.SpawnEntity message, Level level) {
        this(LumecoreMod.Entities.RELIQUARY.get(), level);
    }
    public ReliquaryEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.blocksBuilding = true;
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void defineSynchedData() {
        this.entityData.define(ITEM, ItemStack.EMPTY);
        this.entityData.define(OPENED, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag data) {
        this.entityData.set(ITEM, data.get("Item") instanceof CompoundTag item ? ItemStack.of(item) : ItemStack.EMPTY);
        this.entityData.set(OPENED, data.getBoolean("Opened"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag data) {
        data.put("Item", this.entityData.get(ITEM).serializeNBT());
        data.putBoolean("Opened", this.entityData.get(OPENED));
    }

    @Override
    public void tick() {
        this.ticksLerp = this.ticks;
        this.ticks++;
        if (this.level.isClientSide) {
            this.animationLerp = this.animation;
            if (this.isOpened()) {
                this.animation = this.animation + (1 - this.animation) * 0.25F;
            } else {
                this.animation = Math.min(1, this.animation + (this.animation + 0.01F) * 2F);
            }
        }
        this.hurtLerp = this.hurt;
        if (this.hurt > 0) this.hurt = Math.max(0, this.hurt - 0.03F);
        super.tick();
    }

    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        if (player.getItemInHand(hand).getItem() == LumecoreMod.Items.DEBUG_TOOL.get()) {
            this.setYRot(getYRot() + 22.5F);
            return InteractionResult.SUCCESS;
        } else if (this.entityData.get(OPENED)) {
            if (player.isShiftKeyDown()) {
                this.entityData.set(OPENED, false);
                this.animation = 0;
                this.animationLerp = 0;
            } else {
                if (this.getItem().getItem() == Items.AIR) {
                    this.entityData.set(ITEM, player.getItemInHand(hand));
                    player.setItemInHand(hand, ItemStack.EMPTY);
                } else {
                    player.addItem(this.entityData.get(ITEM).copy());
                    this.entityData.set(ITEM, ItemStack.EMPTY);
                }
            }
            return InteractionResult.SUCCESS;
        } else {
            this.entityData.set(OPENED, true);
            this.animation = 0;
            this.animationLerp = 0;
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (this.hurt > 1) this.kill();
        if (this.getItem().isEmpty()) {
            this.hurt += 0.5F;
            return true;
        }
        return false;
    }

    public boolean isOpened() {
        return this.entityData.get(OPENED);
    }

    public ItemStack getItem() {
        return this.entityData.get(ITEM);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    public boolean isNoGravity() {
        return false;
    }
}

