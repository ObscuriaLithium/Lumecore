package com.obscuria.lumecore.world.entities.props;

import com.obscuria.lumecore.registry.LumecoreEntities;
import com.obscuria.lumecore.registry.LumecoreItems;
import com.obscuria.lumecore.system.MansionProps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

@MansionProps
public class LyingItemEntity extends Entity {

    protected static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.defineId(LyingItemEntity.class, EntityDataSerializers.ITEM_STACK);

    public LyingItemEntity(PlayMessages.SpawnEntity message, Level level) {
        this(LumecoreEntities.RELIQUARY.get(), level);
    }

    public LyingItemEntity(EntityType<?> type, Level level) {
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
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag data) {
        this.entityData.set(ITEM, data.get("Item") instanceof CompoundTag item ? ItemStack.of(item) : ItemStack.EMPTY);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag data) {
        data.put("Item", this.entityData.get(ITEM).serializeNBT());
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

    public ItemStack getItem() {
        return this.entityData.get(ITEM);
    }

    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        if (player.getItemInHand(hand).getItem() == LumecoreItems.DEBUG_TOOL.get()) {
            this.setYRot(getYRot() + 22.5F);
        } else if (this.entityData.get(ITEM).getItem() == Items.AIR) {
            this.entityData.set(ITEM, player.getItemInHand(hand));
            player.setItemInHand(hand, ItemStack.EMPTY);
        } else {
            if (player.addItem(this.entityData.get(ITEM))) this.kill();
        }
        return InteractionResult.SUCCESS;
    }
}

