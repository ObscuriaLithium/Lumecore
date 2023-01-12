package com.obscuria.lumecore.world;

import com.obscuria.lumecore.LumecoreClient;
import com.obscuria.lumecore.LumecoreUtils;
import com.obscuria.lumecore.world.entities.FromHealthyWing;
import com.obscuria.lumecore.world.entities.FromInfestedWing;
import com.obscuria.lumecore.world.entities.FromRegeneratingWing;
import com.obscuria.lumecore.world.entities.props.MansionCoreEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class MansionParts {

    public static final int SIZE = 80;

    public static Wing createWing(BlockPos pos1, BlockPos pos2) {
        return new Wing(new AABB(pos1, pos2));
    }

    public static class Wing {
        private final AABB AREA;
        private WingState STATE;

        public Wing(AABB area) {
            this.AREA = area;
        }

        public void spawnInfectionParticles(Level level) {
            final Vec3 center = this.AREA.getCenter();
            for (int i = 0; i < this.AREA.getSize() * 7; i++) {
                final double x = LumecoreUtils.randomRange(center.x + 0.5, this.AREA.getXsize() / 2);
                final double y = LumecoreUtils.randomRange(center.y + 0.5, this.AREA.getYsize() / 2);
                final double z = LumecoreUtils.randomRange(center.z + 0.5, this.AREA.getZsize() / 2);
                if (LumecoreClient.getDistanceToPlayer(x, y, z) <= 8) level.addParticle(ParticleTypes.ASH, x, y, z, 0, 0, 0);
            }
        }

        public WingState getState() {
            return STATE;
        }

        public void setState(WingState state) {
            this.STATE = state;
        }

        public AABB getArea() {
            return this.AREA;
        }
    }

    public static class Wings {
        private Wing WING_1;
        private Wing WING_2;
        private Wing WING_3;
        private Wing WING_4;

        public Wings() {}

        public void define(Wing wing1, Wing wing2, Wing wing3, Wing wing4) {
            this.WING_1 = wing1;
            this.WING_2 = wing2;
            this.WING_3 = wing3;
            this.WING_4 = wing4;
        }

        public void defineStates(int state1, int state2, int state3, int state4) {
            if (!this.isDefined()) return;
            this.WING_1.setState(WingState.getState(state1));
            this.WING_2.setState(WingState.getState(state2));
            this.WING_3.setState(WingState.getState(state3));
            this.WING_4.setState(WingState.getState(state4));
        }

        public boolean isDefined() {
            return WING_1 != null && WING_2 != null && WING_3 != null && WING_4 != null;
        }

        public Wing getWing(int index) {
            return switch (index) {
                case 0 -> WING_1;
                case 1 -> WING_2;
                case 2 -> WING_3;
                default -> WING_4;
            };
        }

        public void forEachWing(Consumer<Wing> consumer) {
            if (!this.isDefined()) return;
            consumer.accept(this.WING_1);
            consumer.accept(this.WING_2);
            consumer.accept(this.WING_3);
            consumer.accept(this.WING_4);
        }

        public List<Wing> getAll() {
            return isDefined() ? List.of(WING_1, WING_2, WING_3, WING_4) : new ArrayList<>();
        }

        public void tick(int tickCount, boolean clientSide, MansionCoreEntity core) {
            if (tickCount % 100 == 0 && !clientSide) forEachWing(wing -> core.level.getEntitiesOfClass(Monster.class, wing.getArea()).forEach(monster -> {
                    if (wing.getState() == WingState.HEALTHY && !(monster instanceof FromHealthyWing) && LumecoreUtils.canBeDespawned(monster)) monster.discard();
                    if (wing.getState() == WingState.REGENERATING && !(monster instanceof FromRegeneratingWing) && LumecoreUtils.canBeDespawned(monster)) monster.discard();
                    if (wing.getState() == WingState.INFESTED && !(monster instanceof FromInfestedWing) && LumecoreUtils.canBeDespawned(monster)) monster.discard();
                }));
            if (clientSide) forEachWing(wing -> {
                if (wing.getState() == WingState.INFESTED) wing.spawnInfectionParticles(core.level);
            });
        }
    }

    public enum WingState {
        HEALTHY("healthy", 0, '2'),
        REGENERATING("regenerating", 1,'6'),
        INFESTED("infested", 2, 'c');

        private final String NAME;
        private final int INDEX;
        private final char COLOR;

        WingState(String name, int index, char color) {
            this.NAME = name;
            this.INDEX = index;
            this.COLOR = color;
        }

        public String getName() {
            return this.NAME;
        }

        public int getId() {
            return this.INDEX;
        }

        public String getColor() {
            return "§" + this.COLOR;
        }

        public static WingState getState(int index) {
            return switch (index) {
                case 2 -> INFESTED;
                case 1 -> REGENERATING;
                default -> HEALTHY;
            };
        }
    }
}
