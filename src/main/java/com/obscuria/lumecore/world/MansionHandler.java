package com.obscuria.lumecore.world;

import com.obscuria.lumecore.LumecoreUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class MansionHandler {

    public static MansionWing createWing(BlockPos pos1, BlockPos pos2) {
        return new MansionWing(new AABB(pos1, pos2));
    }

    public static class MansionWing {
        private final AABB AREA;

        public MansionWing(AABB area) {
            this.AREA = area;
        }

        public void spawnInfectionParticles(Level level) {
            final Vec3 center = this.AREA.getCenter();
            for (int i = 0; i < this.AREA.getSize() * 5; i++) level.addParticle(ParticleTypes.ASH,
                    LumecoreUtils.randomRange(center.x + 0.5, this.AREA.getXsize() / 2),
                    LumecoreUtils.randomRange(center.y + 0.5, this.AREA.getYsize() / 2),
                    LumecoreUtils.randomRange(center.z + 0.5, this.AREA.getZsize() / 2),
                    0, 0, 0);
        }

        public AABB getArea() {
            return this.AREA;
        }
    }
}
