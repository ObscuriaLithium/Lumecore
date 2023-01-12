package com.obscuria.lumecore.world.entities.props;

import com.obscuria.lumecore.LumecoreUtils;
import com.obscuria.lumecore.registry.LumecoreEntities;
import com.obscuria.lumecore.registry.LumecoreItems;
import com.obscuria.lumecore.world.MansionParts;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

public class MansionCoreEntity extends Entity {

    private static final EntityDataAccessor<CompoundTag> RULES = SynchedEntityData.defineId(MansionCoreEntity.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<Integer> WING_STATE_1 = SynchedEntityData.defineId(MansionCoreEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> WING_STATE_2 = SynchedEntityData.defineId(MansionCoreEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> WING_STATE_3 = SynchedEntityData.defineId(MansionCoreEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> WING_STATE_4 = SynchedEntityData.defineId(MansionCoreEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> WINGS_UPDATE = SynchedEntityData.defineId(MansionCoreEntity.class, EntityDataSerializers.INT);

    private final MansionParts.Wings WINGS = new MansionParts.Wings();

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
        this.getEntityData().define(WING_STATE_1, 0);
        this.getEntityData().define(WING_STATE_2, 0);
        this.getEntityData().define(WING_STATE_3, 0);
        this.getEntityData().define(WING_STATE_4, 0);
        this.getEntityData().define(WINGS_UPDATE, 0);
        final CompoundTag rules = new CompoundTag();
        rules.putBoolean("canBuild", false);
        rules.putBoolean("suppressExplosions", true);
        rules.putBoolean("reduceHealth", true);
        rules.putBoolean("equipmentDecay", true);
        rules.putBoolean("foodDecay", true);
        rules.putBoolean("wingsUpdate", true);
        this.getEntityData().define(RULES, rules);
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag data) {
        final CompoundTag wings = data.getCompound("Wings");
        final CompoundTag rules = data.getCompound("Rules");
        if (!rules.isEmpty()) this.getEntityData().set(RULES, rules);
        this.getEntityData().set(WINGS_UPDATE, wings.getInt("ticks"));
        this.getEntityData().set(WING_STATE_1, wings.getInt("1"));
        this.getEntityData().set(WING_STATE_2, wings.getInt("2"));
        this.getEntityData().set(WING_STATE_3, wings.getInt("3"));
        this.getEntityData().set(WING_STATE_4, wings.getInt("4"));
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag data) {
        final CompoundTag wings = new CompoundTag();
        wings.putInt("ticks", this.getEntityData().get(WINGS_UPDATE));
        wings.putInt("1", this.getEntityData().get(WING_STATE_1));
        wings.putInt("2", this.getEntityData().get(WING_STATE_2));
        wings.putInt("3", this.getEntityData().get(WING_STATE_3));
        wings.putInt("4", this.getEntityData().get(WING_STATE_4));
        data.put("Wings", wings);
        data.put("Rules", this.getEntityData().get(RULES));
    }

    @Override
    public void tick() {
        if (!this.WINGS.isDefined()) this.createWings();
        this.WINGS.defineStates(
                this.getEntityData().get(WING_STATE_1),
                this.getEntityData().get(WING_STATE_2),
                this.getEntityData().get(WING_STATE_3),
                this.getEntityData().get(WING_STATE_4));
        if (this.tickCount % 10 == 0) this.level.getEntitiesOfClass(Player.class, new AABB(this.blockPosition(), this.blockPosition())
                .inflate(MansionParts.SIZE)).forEach(LumecoreUtils::applyPhantomChains);
        this.updateWingsState();
        this.WINGS.tick(this.tickCount, this.level.isClientSide, this);
        super.tick();
    }

    private void createWings() {
        this.WINGS.define(
                MansionParts.createWing(
                        this.blockPosition().offset(-47, 18, -24),
                        this.blockPosition().offset(-26, 1, 24)),
                MansionParts.createWing(
                        this.blockPosition().offset(-24, 18, -47),
                        this.blockPosition().offset(24, 1, -26)),
                MansionParts.createWing(
                        this.blockPosition().offset(26, 18, -24),
                        this.blockPosition().offset(47, 1, 24)),
                MansionParts.createWing(
                        this.blockPosition().offset(-24, 18, 26),
                        this.blockPosition().offset(24, 1, 47)));
    }

    private void updateWingsState() {
        if (this.level.isClientSide) return;
        if (this.getRuleWingsUpdate()) this.getEntityData().set(WINGS_UPDATE, this.getEntityData().get(WINGS_UPDATE) - 1);
        if (this.getEntityData().get(WINGS_UPDATE) <= 0) {
            this.getEntityData().set(WINGS_UPDATE, (int) LumecoreUtils.randomRange(18000, 6000));
            this.getEntityData().set(WING_STATE_1, Math.max(0, this.getEntityData().get(WING_STATE_1) - 1));
            this.getEntityData().set(WING_STATE_2, Math.max(0, this.getEntityData().get(WING_STATE_2) - 1));
            this.getEntityData().set(WING_STATE_3, Math.max(0, this.getEntityData().get(WING_STATE_3) - 1));
            this.getEntityData().set(WING_STATE_4, Math.max(0, this.getEntityData().get(WING_STATE_4) - 1));
            final int counts = 1 + (this.random.nextBoolean() && this.random.nextBoolean() ? 1 : 0);
            for (int i = 0; i < counts; i++) this.setWingState(this.random.nextInt(1, 5), 2);
        }
    }

    public MansionParts.Wings getWings() {
        return this.WINGS;
    }

    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        if (player.getItemInHand(hand).getItem() == LumecoreItems.DEBUG_TOOL.get()) {
            discard();
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public boolean getRuleCanBuild() {
        return this.getRule("canBuild");
    }
    public boolean getRuleSuppressExplosions() {
        return this.getRule("suppressExplosions");
    }
    public boolean getRuleReduceHealth() {
        return this.getRule("reduceHealth");
    }
    public boolean getRuleEquipmentDecay() {
        return this.getRule("equipmentDecay");
    }
    public boolean getRuleFoodDecay() {
        return this.getRule("foodDecay");
    }
    public boolean getRuleWingsUpdate() {
        return this.getRule("wingsUpdate");
    }

    public boolean getRule(String rule) {
        return this.getEntityData().get(RULES).getBoolean(rule);
    }

    public void setRule(String rule, boolean flag) {
        final CompoundTag rules = this.getEntityData().get(RULES);
        rules.putBoolean(rule, flag);
        this.getEntityData().set(RULES, rules);
    }

    public int getWingsUpdateTick() {
        return this.getEntityData().get(WINGS_UPDATE);
    }
    public void setWingsUpdateTick(int tick) {
        this.getEntityData().set(WINGS_UPDATE, tick);
    }

    public MansionParts.WingState getWingState(int index) {
        return switch (index) {
            default -> MansionParts.WingState.getState(this.getEntityData().get(WING_STATE_1));
            case 2 -> MansionParts.WingState.getState(this.getEntityData().get(WING_STATE_2));
            case 3 -> MansionParts.WingState.getState(this.getEntityData().get(WING_STATE_3));
            case 4 -> MansionParts.WingState.getState(this.getEntityData().get(WING_STATE_4));
        };
    }

    public void setWingState(int index, int state) {
        switch (index) {
            default -> this.getEntityData().set(WING_STATE_1, Math.min(2, Math.max(0, state)));
            case 2 -> this.getEntityData().set(WING_STATE_2, Math.min(2, Math.max(0, state)));
            case 3 -> this.getEntityData().set(WING_STATE_3, Math.min(2, Math.max(0, state)));
            case 4 -> this.getEntityData().set(WING_STATE_4, Math.min(2, Math.max(0, state)));
        };
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
